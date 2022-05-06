/*
Javascript ECMAScript-
we'll havea full week of JS in a while..
for now let's just stick to the very basics:
javascript has close to nothing to do with java
it was named that for marketing reasons

what's different?
1- javascript runs in a browser and java cannot do this easily
2- javascript is interpreted, and not compiled - this means that it runs as-is
and does not need to be compiled like java
3- javascript is loosely typed, unlike java where we need to assign types
    a- this means that functions can also be used as variables and passed around and such
        i- i am using functions/methods interchangeable, but the difference is that
            we only use the word method when the method is tied to an object
4- javascript barely cares about errors
5- javascript does not require a main method (execution just starts from the top of file)
6- console.log instead of print

 */

/*
my first step: grab what we need from the html file
 */

console.log("JS file loaded from html");
let userInput = document.getElementById("userInput");
let addButton = document.getElementById("addButton");
let shoppingButton = document.getElementById("shoppingButton");
let listSection = document.getElementById("listSection");

addButton.onclick = addItem;
shoppingButton.onclick = deleteList;
refreshList();

function addItem(){
    let httprequest = new XMLHttpRequest();
    let inputValue = userInput.value;
    httprequest.onreadystatechange = recieveData;
    httprequest.open("POST", "http://20.62.170.180:5000/grocery");
    httprequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httprequest.send(JSON.stringify({"itemName":inputValue}));

    function recieveData(){
        if(httprequest.readyState == 4){
            if(httprequest.status == 200){
                refreshList();
            }
        }
    }
}
function deleteList(){
    let httprequest = new XMLHttpRequest();
    httprequest.onreadystatechange = recieveData;
    httprequest.open("DELETE", "http://20.62.170.180:5000/grocery");
    httprequest.send();

    function recieveData(){
        if(httprequest.readyState == 4){
            if(httprequest.status == 200){
                listSection.innerText="";
            }
        }
    }
}
function refreshList(){
/*
AJAX: Asynchronous Javascript and XML
this is basically continuing the tradition of the creator of JS
coming up with stupid names
 */
    let httprequest = new XMLHttpRequest();
    httprequest.onreadystatechange = recieveData;
    httprequest.open("GET","http://20.62.170.180:5000/grocery");
    httprequest.send();

    function recieveData(){
        /*
        AJAX ready state has different states
        0: unsent
        1: opened
        2: headers recieved
        3: loading
        4: done
         */
        if(httprequest.readyState == 4){
            //any status in the 200 range means everything is ok!
            if(httprequest.status == 200){
            //    let's update our webpage!
                console.log(httprequest.responseText);
                let response = JSON.parse(httprequest.responseText);
                console.log(response[0]);
                console.log(response[0].itemName);
                let groceryListString = "";
                for(let i = 0; i < response.length; i++){
                    groceryListString += response[i].itemName;
                    groceryListString += "\n";
                }
                listSection.innerText = groceryListString;
            }
        }
    }
}