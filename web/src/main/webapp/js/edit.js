x = 20;

function addPhoneLine2(object) {
    var parent = object.parentNode;

    var divRow = document.createElement("div");
    divRow.className = "w3-cell-row";

    var divCellPhone = document.createElement("div");
    divCellPhone.className = "w3-cell";
    var inputPhone = document.createElement("input");
    inputPhone.className = "w3-input w3-border";
    inputPhone.type = "tel";
    inputPhone.name = "phone" + window.x;
    inputPhone.pattern = "[0-9]+";
    divCellPhone.appendChild(inputPhone);

    var divTypePhone = document.createElement("div");
    divTypePhone.className = "w3-container w3-cell";
    var selectPhoneType = document.createElement("select");
    selectPhoneType.className = "w3-select w3-border";
    selectPhoneType.name = "phone_type" + window.x;
    var homeOption = document.createElement("option");
    homeOption.value = "home";
    homeOption.innerText = "Home";
    var workOption = document.createElement("option");
    workOption.value = "work";
    workOption.innerText = "Work";
    divTypePhone.appendChild(selectPhoneType);
    selectPhoneType.appendChild(homeOption);
    selectPhoneType.appendChild(workOption);

    var divRemoveButton = document.createElement("div");
    divRemoveButton.className = "w3-cell";
    var removeButton = document.createElement("button");
    removeButton.className = "w3-btn w3-blue";
    removeButton.type = "button";
    removeButton.onclick = function () {
        deletePhoneLine(this)
    };
    removeButton.innerText = "-";
    divRemoveButton.appendChild(removeButton);
    divRemoveButton.appendChild(document.createElement("br"));

    parent.removeChild(object);

    parent.appendChild(divRow);
    divRow.appendChild(divCellPhone);
    divRow.appendChild(divTypePhone);
    divRow.appendChild(divRemoveButton);

    parent.appendChild(object);
    window.x = window.x + 1;
}