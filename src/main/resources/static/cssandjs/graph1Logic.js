anychart.onDocumentReady(function () {

    let pair = document.querySelector(".sele1").value;
    let bal1 = document.querySelector('.balanceNumber11');
    let bal2 = document.querySelector('.balanceNumber12');
    let Label =  document.querySelector(".sele1");
    let coinLabel = (Label.options[Label.selectedIndex].text)
    let coins = coinLabel.split('/')
    let title1 = ""

    document.querySelector(".sele1").addEventListener('change', function (e) {
        pair = e.target.value;
        updateChart(pair,"notFirst");
        getPriceForlabel(pair);
        coinLabel = (Label.options[Label.selectedIndex].text)
        coins = coinLabel.split('/');
        updateBalances(coins);
    })

    function updateBalance(balanceText, coinTicker) {
        fetch('http://localhost:8080/api/balance/' + coinTicker, {
            method : "GET",
            mode: 'cors',
            headers: {
                'Access-Control-Allow-Origin': 'http://localhost:8080',
                Accept: 'application/json'
            }
        })
        .then(response => response.json())
        .then(balance => {
            if (balance['message'] == undefined)
                balanceText.textContent = balance['amount']+ ' ' + coinTicker;
            else
                balanceText.textContent = '0 ' + coinTicker;
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    function updateBalances(coins) {
        updateBalance(bal1, coins[0]);

        updateBalance(bal2, coins[1]);
    }

    updateBalances(coins);

    let dataCh = []
    var chart1 = anychart.box();
    var upColor = {
        fill: "#22DD22 0.3",
        stroke: "#22DD22",
        medianStroke: "0.5 #22DD22",
        stemStroke: "0.5 #22DD22"
    }
    var downColor = {
        fill: "#ff4040 0.3",
        stroke: "#ff4040",
        medianStroke: "0.5 #ff4040",
        stemStroke: "0.5 #ff4040"
    }



    function updateChart(pair,param){
        fetch('https://api.binance.com/api/v3/uiKlines?symbol=' + pair + '&interval=1m&limit=72', {
            method : "GET",
            headers: {
                Accept: 'application/json;charset=UTF-8'
            }
        })
        .then(r => r.json())
        .then(prices => {
            dataCh = []
            for(let i = 0; i < prices.length;i++){
                let col;
                if (parseFloat(prices[i][4])>parseFloat(prices[i][1]))
                    col = upColor;
                else col = downColor

                let med = (parseFloat(prices[i][4]) + parseFloat(prices[i][1])) / 2;
                const date = new Date(prices[i][0]);
                dataCh.push({x:date.getHours()+':'+(date.getMinutes() > 9 ? '' : '0') +date.getMinutes(),low:parseFloat(prices[i][3]),q1:parseFloat(prices[i][1]),median:parseFloat(med),q3:parseFloat(prices[i][4]),high:parseFloat(prices[i][2]),normal:col});
            }

            if (param!="first") {
                title1= chart1.title(pair);
                chart1.data(dataCh)
            }
            else {
                chart1.background().fill("black");
                series = chart1.box(dataCh);
                title1= chart1.title(pair);
                chart1.interactivity("by-x");
                chart1.container("chartWindow1");
                chart1.labels().fontColor("white");
                chart1.width('100%');
                chart1.height('100%');
                chart1.draw();
            }

        })
    }

    function getPriceForlabel(pair){
        fetch('https://api.binance.com/api/v3/ticker/price?symbol=' + pair, {
            method : "GET",
            headers: {
                Accept: 'application/json;charset=UTF-8'
            }
        })
        .then(r => r.json())
        .then(price => {
            price = price['price'];
            let label = document.querySelector('.priceNumber1');
            let oldValue = parseFloat(label.textContent)
            label.textContent = price;
            if (oldValue > price){
                label.style.color = "rgb(236, 46, 46)";
            }
            else {
                label.style.color = "rgb(37, 210, 37)";
            }
        })
    }

    getPriceForlabel(pair);
    updateChart(pair, "first");

    setInterval(function print(param) {
        getPriceForlabel(pair)
    }, 5000, pair)

    setInterval(function print(param) {
        updateChart(pair, "notFirst")
    }, 5000, pair)

    setInterval(function print(param) {
        updateBalances(coins)
    }, 1000, pair)

    let buyBT = document.querySelector('.b1buy');
    let sellBT = document.querySelector('.b1sell');

    function exchangeCoins(operation) {
        const range = document.querySelector('.range1');
        const number = range.value / 100;
        const count = parseFloat((operation === 'BUY') ? bal2.textContent : bal1.textContent) * number;
        fetch(`http://localhost:8080/api/trading/exchange?base=${coins[0]}&quoted=${coins[1]}&quantity=${count}&operation=${operation}`, {
            method: 'PUT',
            mode: 'cors',
            headers: {
              'Access-Control-Allow-Origin': 'http://localhost:8080',
              Accept: 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(bal => {
            updateBalances(coins);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }

    buyBT.addEventListener('click', function() {
        exchangeCoins('BUY');
    });

    sellBT.addEventListener('click', function() {
        exchangeCoins('SELL');
    });
})
