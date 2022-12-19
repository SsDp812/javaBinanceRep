

function getHistory(pair){
    fetch('http://localhost:8080/history', {
        method : "GET",
        mode: 'cors',
        headers: {
            'Access-Control-Allow-Origin': 'http://localhost:8080',
            Accept: 'application/json',
            'Content-Type': 'application/json'
        }
      })
  .then(r => r.json())
  .then(history=> {
    let table = document.querySelector('.table');
    for(let i = 0; i < history.length;i++){
        let tr = document.createElement('tr');
        let td1 = document.createElement('td');
        td1.textContent = history[i]["bought"]["type"];
        let td2 = document.createElement('td');
        td2.textContent = history[i]["salable"]["type"];
        let td3 =  document.createElement('td');
        td3.textContent = history[i]["course"]
        let td4 = document.createElement('td');
        td4.textContent = history[i]["bought"]["balance"];
        let td5 = document.createElement('td');
        td5.textContent = history[i]["bought"]["balance"];
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        table.appendChild(tr);
    }
    

  })
}

getHistory()