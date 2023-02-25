

let bt = document.querySelector('.testBT');
let sel1 = document.getElementById("sel1");
    let q1 = document.querySelector('#q1');

    let sel2 = document.getElementById("sel2");
    let q2 =  document.querySelector('#q2');

    let sel3 = document.getElementById("sel3");
    let q3 =  document.querySelector('#q3');

    let sel4 = document.getElementById("sel4");
    let q4 =  document.querySelector('#q4');

    let sel5 = document.getElementById("sel5");
    let q5 =  document.querySelector('#q5');

    let sel6 = document.getElementById("sel6");
    let q6 =  document.querySelector('#q6');

    let sel7 = document.getElementById("sel7");
    let q7 =  document.querySelector('#q7');;

    let sel8 = document.getElementById("sel8");
    let q8 =  document.querySelector('#q8');

    let sel9 = document.getElementById("sel9");
    let q9 =  document.querySelector('#q9');

    let sel10 = document.getElementById("sel10");
    let q10 =  document.querySelector('#q10');


bt.addEventListener("click",function(){


    let count = 0;

    if(sel1.value == 1){
        q1.setAttribute('class', 'greenColor');
        count++;
    }else{
        q1.setAttribute('class', 'redColor');
    }

    if(sel2.value == 1){
        q2.setAttribute('class', 'greenColor');
        count++;
    }else{
        q2.setAttribute('class', 'redColor');
    }

    if(sel3.value == 3){
        q3.setAttribute('class', 'greenColor');
        count++;
    }else{
        q3.setAttribute('class', 'redColor');
    }

    if(sel4.value == 4){
        q4.setAttribute('class', 'greenColor');
        count++;
    }else{
        q4.setAttribute('class', 'redColor');
    }

    if(sel5.value == 2){
        q5.setAttribute('class', 'greenColor');
        count++;
    }else{
        q5.setAttribute('class', 'redColor');
    }

    if(sel6.value == 4){
        q6.setAttribute('class', 'greenColor');
        count++;
    }else{
        q6.setAttribute('class', 'redColor');
    }

    if(sel7.value == 1){
        q7.setAttribute('class', 'greenColor');
        count++;
    }else{
        q7.setAttribute('class', 'redColor');
    }

    if(sel8.value == 3){
        q8.setAttribute('class', 'greenColor');
        count++;
    }else{
        q8.setAttribute('class', 'redColor');
    }

    if(sel9.value == 1){
        q9.setAttribute('class', 'greenColor');
        count++;
    }else{
        q9.setAttribute('class', 'redColor');
    }

    if(sel10.value == 3){
        q10.setAttribute('class', 'greenColor');
        count++;
    }else{
        q10.setAttribute('class', 'redColor');
    }

    window.alert(count + '/10 is true!')

})