anychart.onDocumentReady(function () {
    let Label =  document.querySelector(".sele1");
    let pair = Label.value;
    let bal1 = document.querySelector('.balanceNumber11');
    let bal2 = document.querySelector('.balanceNumber12');
    let coinLabel = (Label.options[Label.selectedIndex].text)
    let coins = coinLabel.split('/')

    Label.addEventListener('change', function (e) {
        pair = e.target.value;
        updateChart(pair,"notFirst");
        getPriceForlabel(pair);
        coinLabel = (Label.options[Label.selectedIndex].text)
        coins = coinLabel.split('/');
        updateBalances(coins);
    })

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

    const buyBT = document.querySelector('#buy');
    const buyLimitBT = document.querySelector('#buyLimit');
    const buyInputs = document.querySelectorAll('.buyBox .inputCount');
    const buyCountInput = buyInputs[0];
    const buyPriceInput = buyInputs[1];

    const sellBT = document.querySelector('#sell');
    const sellLimitBT = document.querySelector('#sellLimit');
    const sellInputs = document.querySelectorAll('.sellBox .inputCount');
    const sellCountInput = sellInputs[0];
    const sellPriceInput = sellInputs[1];

    buyBT.onclick = () => {
        exchangeCoins(coins[0], coins[1], buyCountInput.value,'BUY');
    }

    sellBT.onclick = () => {
        exchangeCoins(coins[0], coins[1], sellCountInput.value,'SELL');
    }

    buyLimitBT.onclick = () => {
        createLimitOrder(coins[0], coins[1], buyCountInput.value, buyPriceInput.value, 'BUY')
    }

    sellLimitBT.onclick = () => {
        createLimitOrder(coins[0], coins[1], sellCountInput.value, sellPriceInput.value, 'SELL')
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

    function exchangeCoins(baseTicker, quotedTicker, quantity, operation) {
        fetch(`http://localhost:8080/api/trading/exchange?base=${baseTicker}&quoted=${quotedTicker}&quantity=${quantity}&operation=${operation}`, {
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

                updateBalances(coins);
            })
            .catch(error => {
                console.error('Error:', error);
                alert(error.message);
            });
    }

    function createLimitOrder(baseTicker, quotedTicker, quantity, price, operation) {
        fetch('http://localhost:8080/api/trading/limitOrder', {
                method: 'PUT',
                mode: 'cors',
                headers: {
                  'Access-Control-Allow-Origin': 'http://localhost:8080',
                  Accept: 'application/json',
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify({baseTicker: baseTicker, quotedTicker: quotedTicker,
                quantity: quantity, targetCourse: price, operation: operation})
            })
            .then(response => {
                if (!response.ok) {
                  throw new Error(`HTTP error! Status: ${response.status}`);
                }

                return response.json();
            })
            .then(data => console.log(data))
            .catch(error => {
                console.error('Error:', error);
                alert(error.message);
            });
    }
});

function updateBalance(balanceText, coinTicker) {
    fetch('http://localhost:8080/api/balance/' + coinTicker, {
            method : "GET",
            mode: 'cors',
            headers: {
                'Access-Control-Allow-Origin': 'http://localhost:8080',
                Accept: 'application/json',
                mode: 'no-cors',
                'Content-Type': 'application/json'
            }
    })
    .then(response => response.json())
    .then(balance => {
        if (balance['message'] == undefined)
            balanceText.textContent = balance['amount']+ ' ' + coinTicker;
        else
            balanceText.textContent = '0 ' + coinTicker;
    });
}
