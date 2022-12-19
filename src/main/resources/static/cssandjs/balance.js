

function getHistory(pair){
    fetch('http://localhost:8080/getBalanceAll', {
        method : "GET",
        mode: 'cors',
        headers: {
            'Access-Control-Allow-Origin': 'http://localhost:8080',
            Accept: 'application/json',
            'Content-Type': 'application/json'
        }
      })
  .then(r => r.json())
  .then(bal=> {
    let table = document.querySelector('.table');
    for(let i = 0; i < bal.length;i++){
        let tr = document.createElement('tr');
        let td1 = document.createElement('td');
        td1.textContent = bal[i]["type"];
        let td2 = document.createElement('td');
        td2.textContent = bal[i]["balance"];
        tr.appendChild(td1);
        tr.appendChild(td2);
        table.appendChild(tr);
    }
    

  })
}

getHistory()