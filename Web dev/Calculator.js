var currentNum = "", // Current number
    storedNum = "", // First number
    operator="", // operator
    haveDot=false, // single dot control
    newNum=true, // new number required
    maxInLength=9, // limit input length
    maxOutLength=8; // output length limit (uses exp notation)

function setResult(num)
{
    document.getElementById("R").textContent = num;
}

function numFunction(num)
{
    /*
    When a digit button is initially pressed or is pressed
    after the use of an operator button or the = button
    or the C button the currently-displayed number should
    be cleared and the digit shown;
    subsequent presses of digit buttons or the . button
    should append to the display.
     */
    if (newNum) { // If a result was displayed, reset number
        haveDot=false;
        newNum=false;
        currentNum = num;
    } else { // Otherwise, add digit to previous number (this is a string!)
        if (currentNum.length<maxInLength) {
            currentNum += num;
        }
    }
    setResult(currentNum); // Display current number
}

function opFunction(op) {
    /*
    When an operator button is pressed the displayed number
    and the selected operator should be stored;
    subsequent presses of operator button or the = button
    should cause a calculation to be performed using the
    stored number, the stored operator and the current number;
    its result should be displayed.
    If the button was an operator,
    the new number and operator should be stored.
     */
    // if op already saved
    if (operator.length>0)
    {
        eqFunction()
    }
    else
    {
        // Pass number to storedNum and save operator
        storedNum = currentNum;
        currentNum = "";
        newNum=true;
    }
    operator = op;
}

function dotFunction() {
    /*
    The . button should be ignored if the displayed number already
     contains a decimal point or if the number currently displayed
     is the result of a calculation;
     numbers such as .35 must be entered as 0.35)
    */
   if (haveDot||newNum)
       return;
   numFunction('.')
   haveDot=true;
}

function clrFunction()
{
    /*
    When the C button is pressed the display should be reset to 0 
    and any stored number and operator should be cleared.
     */
    storedNum = "";
    currentNum = "";
    operator = "";
    haveDot = false;
    newNum = true;
    setResult("0");
}

function eqFunction()
{
    newNum=true;
    var resultNum;
    // Calculate result
    // Convert string input to numbers
    if (storedNum.length == 0 || currentNum.length == 0 || operator.length==0)
        return;
    storedNum = parseFloat(storedNum);
    currentNum = parseFloat(currentNum);
    // Perform stored operation
    switch (operator) {
        case "+":
            resultNum = storedNum + currentNum;
            break;
        case "-":
            resultNum = storedNum - currentNum;
            break;
        case "*":
            resultNum = storedNum * currentNum;
            break;
        case "/":
            resultNum = storedNum / currentNum;
            break;
    }
    // Now reset currentNum & store result - unless invalid !
    if (isFinite(resultNum)) {
        storedNum = resultNum;
        if (resultNum.toString().length>maxOutLength)
        {
            resultNum = resultNum.toPrecision(6);
        }
        currentNum = "";
    }
    else
    {
        // If NaN or Infinity returned
        if (isNaN(resultNum)) {
            resultNum = "NAN";
        } else {
            resultNum = "INF";
        }
        clrFunction();
    }
    // Display result
    setResult(resultNum);
 }
