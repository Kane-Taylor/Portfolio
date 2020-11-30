var w;
var counter=0;
var myMap = {}; // use an object as a map !! property names are words and values are counts


function openFile(url) {
    w = window.open();
    w.location = (url.length == 0) ? 'silly.html' : url;
}

function getOption() {
    return  document.getElementById("choice").selectedIndex;
}

function parseText() {
    counter = 0;
    myMap = {};
    parseNode(w.document.body);
    if (counter>0){
        CreateTable();
    }
}

function parseNode(node) {
    if (node.nodeType==Node.ELEMENT_NODE) {
        for (var m = node.firstChild; m!=null; m = m.nextSibling) {
            parseNode(m);
        }
    }
    else if (node.nodeType==Node.TEXT_NODE) {
        updateMap(node.data.toLowerCase());
    }
}

function updateMap(value) {
    var wds = getOption()==0?
                value.replace(/(\r\n|\n|\r)/gm, " ").replace('/ {2,}/',' ').split(' '):
                value.replace(/[^a-zA-Z ]+/g, '').replace('/ {2,}/',' ').split(' ');
    for ( var wd in wds)
    {
        var wrd=wds[wd];
        if (wrd.length>0)
        {
            // if the object has this "property" increment its value otherwise set to 1
            var cnt=myMap[wrd];
            myMap[wrd]=cnt?cnt+1:1;
            counter+=1;
        }
    }
}

function compare(a,b) {
    // sorting on the first value of the objects passed in
    // - i.e. the "property" name which is the word
    if (a[0] < b[0]) {
        return -1;
    }
    if (a[0] > b[0]) {
        return 1;
    }
    return 0;
}

function CreateTable() {
    // defaults
    var col = ["Word","Count"];
    var table = document.createElement("table");    // create dynamic table
    var tr = table.insertRow(-1);                   // table row
    for (var i = 0; i < col.length; i++) {
        var th = document.createElement("th");      //table header
        th.innerHTML = col[i];
        tr.appendChild(th);
    }
    // create a sorted array of the object entry objects
    var sortMap=Object.entries(myMap).sort(compare);
    // loop over this array, and retrieve the property
    // which is the second value of the object in the array
    for (var ob in Object.entries(sortMap) ) {
        tr = table.insertRow(-1);
        var tabCell = tr.insertCell(-1);
        var tabCell2 = tr.insertCell(-1);
        // get the actual "property" kv pair
        var wrd=Object.entries(sortMap)[ob][1];
        // set the property name (word) and value (count)
        tabCell.innerHTML = wrd[0];
        tabCell2.innerHTML = wrd[1];
        if (ob % 2 != 0) tr.style.backgroundColor = 'pink'; // row striping
    }
    // add table to container
    var divContainer = document.getElementById("showTable");
    divContainer.innerHTML = "";
    divContainer.appendChild(table);
    document.getElementById("count").textContent = "Total Words = "+counter;
}

