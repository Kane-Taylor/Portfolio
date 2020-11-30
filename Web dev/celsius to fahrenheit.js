function getOption() {
    return document.getElementById("r1").checked;
}

function validate(inField,showAlert) {
    var x =document.getElementById(inField).value;
    if (x.toString().length>0) {
        if (showAlert&&x=='-') {
            // assume they are starting to type a negative number
            return;
        }
        var regex=/^-?[0-9]+$/;
        if (!x.match(regex)) {
            if (showAlert){
                alert("Must input a number");
            }
            return false;
        }
        if (x>999||x<-999) {
            if (showAlert){
                alert("Number out of range");
                return false;
            }
        }
    }
    return true;
}

function CreateTable() {
    if (!(validate("from",false)&&validate("to",false)))
    {
        alert("Invalid range");
        return false;
    }
    var startFrom = document.getElementById("from").value;
    var stopAt = document.getElementById("to").value;
    // defaults
    var start=0
    var stop=0;
    var step=1;
    if (startFrom.toString().length==0) {
        if (stopAt.toString().length==0) {
            alert("Please input a conversion range");
            return;
        } else {
            stop=parseInt(stopAt);
            start=stop-9;
        }
    } else {
        if (stopAt.toString().length==0) {
            start=parseInt(startFrom);
            stop=start+9;
        } else {
            start=parseInt(startFrom);
            stop=parseInt(stopAt);
            if (stop<start) {
                step=-1;
            }
        }
    }
    var cof=getOption();                          // get conversion direction
    var col = ["Celsius","Fahrenheit"];
    if (!cof) col.reverse();                        // switch col headers
    var table = document.createElement("table");    // create dynamic table
    var tr = table.insertRow(-1);                   // table row
    for (var i = 0; i < col.length; i++) {
        var th = document.createElement("th");      //table header
        th.innerHTML = col[i];
        tr.appendChild(th);
    }
    // generate rows
    for (var i = start; (step==1 && i <= stop)||(step==-1 && i >= stop); i+=step) {
        tr = table.insertRow(-1);
        var tabCell = tr.insertCell(-1);
        var tabCell2 = tr.insertCell(-1);
        // calculate value
        var converted= (cof) ? (i*9 / 5 + 32).toString() : ((i-32) * 5 /9).toString();
        // add columns
        tabCell.innerHTML = i.toString();
        tabCell2.innerHTML = parseFloat(Math.round(converted * 10) / 10).toFixed(1);;
        if (i % 2 == 0) tr.style.backgroundColor = 'pink'; // row striping
    }
    document.getElementById("tab").innerText=cof? "CELSIUS To FAHRENHEIT Conversion Table":"FAHRENHEIT To CELSIUS Conversion Table";
    // add table to container
    var divContainer = document.getElementById("showTable");
    divContainer.innerHTML = "";
    divContainer.appendChild(table);
}