const itemsArray = localStorage.getItem('tasks') ? JSON.parse(localStorage.getItem('tasks')) : [];

document.querySelector("#enter").addEventListener("click", () => {
    const task = document.querySelector("#task")
    createTask(task)
})

document.querySelector("#task").addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
        const task = document.querySelector("#task")
        createTask(task)
    }
})

function showDate() {
    let date = new Date()
    date = date.toString().split(" ")
    date = date[1] + " " + date[2] + " " + date[3]
    document.querySelector("#date").innerHTML = date
}

function displayTasks() {
    let tasks = ""
    for (let i = 0; i < itemsArray.length; i++) {
        tasks += `<div class="task">
                <div class="inputController">
                  <textArea disabled>${itemsArray[i]}</textArea>
                  <div class="editController">
                    <i class="fa-solid fa-check deleteBtn"></i>
                    <i class="fa-solid fa-pen-to-square editBtn"></i>
                  </div>
                </div>
                <div class="updateController">
                  <button class="saveButton">Save</button>
                  <button class="cancelButton">Cancel</button>
                </div>
              </div>`
    }
    document.querySelector(".todoList").innerHTML = tasks
    deleteListeners()
    editListeners()
    saveListeners()
    cancelListeners()
}

function deleteListeners() {
    let deleteBtn = document.querySelectorAll(".deleteBtn")
    deleteBtn.forEach((dB, i) => {
        dB.addEventListener("click", () => { deleteTask(i) })
    })
}

function editListeners() {
    const editBtn = document.querySelectorAll(".editBtn")
    const updateController = document.querySelectorAll(".updateController")
    const inputs = document.querySelectorAll(".inputController textArea")
    editBtn.forEach((eB, i) => {
        eB.addEventListener("click", () => {
            updateController[i].style.display = "block"
            inputs[i].disabled = false
        })
    })
}

function saveListeners() {
    const saveButton = document.querySelectorAll(".saveButton")
    const inputs = document.querySelectorAll(".inputController textArea")
    saveButton.forEach((sB, i) => {
        sB.addEventListener("click", () => {
            updateTask(inputs[i].value, i)
        })
    })
}

function cancelListeners() {
    const cancelButton = document.querySelectorAll(".cancelButton")
    const updateController = document.querySelectorAll(".updateController")
    const inputs = document.querySelectorAll(".inputController textArea")
    cancelButton.forEach((cB, i) => {
        cB.addEventListener("click", () => {
            updateController[i].style.display = "none"
            inputs[i].disabled = true
            inputs[i].style.border = "none"
            reloadTask(inputs[i].value, i)
        })
    })
}

function createTask(task) {
    itemsArray.push(task.value)
    localStorage.setItem('tasks', JSON.stringify(itemsArray))
    location.reload()
}

function deleteTask(i) {
    itemsArray.splice(i, 1)
    localStorage.setItem('tasks', JSON.stringify(itemsArray))
    location.reload()
}

function updateTask(text, i) {
    itemsArray[i] = text
    localStorage.setItem('tasks', JSON.stringify(itemsArray))
    location.reload()
}

function reloadTask(text, i) {
    itemsArray[i] = text
    localStorage.getItem('tasks', JSON.stringify(itemsArray))
    location.reload()
}

window.onload = function () {
    showDate()
    displayTasks()
};