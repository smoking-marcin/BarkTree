
function readonly(state) {
    document.querySelectorAll('input').forEach(
        function(element) {
            if (document.querySelector('#addressId').value != "") {
                element.readOnly = state;
            } else {
                element.readOnly = false;
            }
        }
    )
}

function formToString(innerform){
    let address = "";
    let form = document.querySelector('form')
    if (innerform) {
        form = innerform
    }
    form.querySelectorAll("input[type='text'], input[type='hidden']").forEach(
        function(element) {
            address += "&"+element.name+"="+element.value;
        }
    )
    address = address.substring(1);
    return address;
}

function clearForm(){
    let form = document.querySelector('#address')
    form.querySelectorAll('input[type=text]').forEach(
        function(element) {
            element.value = "";
        }
    )
    form.querySelectorAll('input[type=hidden]').forEach(
        function(element) {
            element.value = "";
        }
    )
}

function responseToString(response,deleted) {
    if (response.length > 0) {
        let address = response;
        if (address.length > 0) {
            address = address.replace("Address", "");
            address = address.replace("{", "");
            address = address.replace("}", "");
            let addressString = "";
            let addressObject = address.split(", ").reduce(function (obj, str, index) {
                let strParts = str.split("=");
                if (strParts[0] && strParts[1]) {
                    obj[strParts[0].replace(/\s+/g, '')] = strParts[1].trim().replaceAll("'", "");
                }
                return obj;
            }, {});
            for (let addressElement in addressObject) {
                if (!deleted) {
                    document.getElementById(addressElement).value = addressObject[addressElement];
                }
                if (addressElement !== "addressId") {
                    if (addressElement !== "streetName") {
                        addressString += addressObject[addressElement] + ", ";
                    } else {
                        addressString += addressObject[addressElement] + " ";
                    }
                }
            }
            return addressString;
        }
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll("input[type='text']").forEach(
        function(element) {
            element.addEventListener("input",function() {
                if (element.value.length > 2) {
                    let xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        document.getElementById("message").innerHTML = this.responseText;
                    };
                    xhttp.open("POST", "/address/autocomplete", true);
                    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    xhttp.send("column=" + element.name + "&value=" + element.value);
                }
            })
        }
    );
    readonly(true);
    let isEdit = document.querySelector('#edit') !== null;
    if (isEdit) {
        document.querySelector('#edit').addEventListener("click",
            function () {
                readonly(false);
            }
        )
    }

    let isGoToEdit = document.querySelector("input[type='button'].goToEdit") !== null;
    if (isGoToEdit) {
        document.querySelectorAll("input[type='button'].goToEdit").forEach(
            function (element) {
                element.addEventListener("click",function () {
                    console.log("I got this far!1")
                    let addressId = element.form.querySelector("#addressId").value;
                    console.log("I got this far!"+addressId)
                    window.open("/address/"+addressId, "_self", "location=no");
                    }
                )
            }
        )
    }

    let isSave = document.querySelector('#save') !== null;
    if (isSave) {
        document.querySelector('#save').addEventListener("click",
            function () {
                document.querySelectorAll('input').forEach(
                    function (element) {
                        element.readOnly = true;
                    }
                )
                let xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    let addressString = responseToString(this.responseText, false);
                    document.getElementById("message").innerHTML = addressString + " zapisano.";

                };
                let address = formToString();
                xhttp.open("POST", "/address/edit", true);
                xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhttp.send(address);
            }
        )
    }
    let isDelete = document.querySelector('#delete') !== null;
    if (isDelete) {
        document.querySelector('#delete').addEventListener("click",
            function () {
                readonly(true);
                let xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    clearForm();
                    let addressString = responseToString(this.responseText, true);
                    console.log(addressString);
                    if (addressString != null) {
                        document.getElementById("message").innerHTML = addressString + " usunięto.";
                    } else {
                        document.getElementById("message").innerHTML = '';
                    }
                    document.querySelectorAll('input').forEach(
                        function (element) {
                            element.readOnly = false;
                        }
                    )
                };
                let address = formToString();
                xhttp.open("POST", "/address/delete", true);
                xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhttp.send(address);
            }
        )
    }
    let isFind = document.querySelector('#find') !== null;
    if (isFind) {
        document.querySelector('#find').addEventListener("click",
            function () {
                let address = formToString();
                window.open("/address/find?" + address, "_self", "location=no");
            }
        )
    }
})