
function readonly(state) {
    let isForm = document.querySelector('form') !== null;
    if (isForm) {
        document.querySelectorAll('input').forEach(
            function (element) {
                if (document.querySelector('#breederId').value !== "") {
                    element.readOnly = state;
                } else {
                    element.readOnly = false;
                }
            }
        )
    }
}

function formToString(innerform){
    let breeder = "";
    let form = document.querySelector('form')
    if (innerform) {
        form = innerform
    }
    form.querySelectorAll("input[type='text'], input[type='hidden']").forEach(
        function(element) {
            breeder += "&"+element.name+"="+element.value;
        }
    )
    breeder = breeder.substring(1);
    return breeder;
}

function clearForm(){
    let form = document.querySelector('#breeder')
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
        let breeder = response;
        console.log("this came in: "+response)
        if (breeder.length > 0) {
            breeder = breeder.replace("Breeder", "").replace("{", "").replace("}", "").replaceAll("(", "").replaceAll(")","").replaceAll("'", "");
            console.log("this I finally made: "+breeder)
            let breederString = "";
            let breederObject = breeder.split(", ").reduce(function (obj, str, index) {
                let strParts = str.split("=");
                if (strParts[0] && strParts[1] && strParts[1] !== "null") {
                    obj[strParts[0].replace(/\s+/g, '')] = strParts[1].trim().replaceAll("'", "");
                }
                return obj;
            }, {});
            for (let breederElement in breederObject) {
                if (!deleted) {
                    document.getElementById(breederElement).value = breederObject[breederElement];
                }
                if (breederElement !== "breederId") {
                    if (breederElement !== "name") {
                        breederString += breederObject[breederElement] + ", ";
                    } else {
                        breederString += breederObject[breederElement] + " ";
                    }
                }
            }
            return breederString;
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
                    xhttp.open("POST", "/breeder/autocomplete", true);
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
                    let breederId = element.form.querySelector("#breederId").value;
                    window.open("/breeder/"+breederId, "_self", "location=no");
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
                    let breederString = responseToString(this.responseText, false);
                    document.getElementById("message").innerHTML = breederString + " zapisano.";

                };
                let breeder = formToString();
                console.log(breeder);
                xhttp.open("POST", "/breeder/edit", true);
                xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhttp.send(breeder);
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
                    let breederString = responseToString(this.responseText, true);
                    console.log(breederString);
                    if (breederString != null) {
                        document.getElementById("message").innerHTML = breederString + " usunięto.";
                    } else {
                        document.getElementById("message").innerHTML = '';
                    }
                    document.querySelectorAll('input').forEach(
                        function (element) {
                            element.readOnly = false;
                        }
                    )
                };
                let breeder = formToString();
                xhttp.open("POST", "/breeder/delete", true);
                xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhttp.send(breeder);
            }
        )
    }
    let isFind = document.querySelector('#find') !== null;
    if (isFind) {
        document.querySelector('#find').addEventListener("click",
            function () {
                let breeder = formToString();
                window.open("/breeder/find?" + breeder, "_self", "location=no");
            }
        )
    }
    let isAddAddress = document.querySelector('#addAddress') !== null;
    if (isAddAddress) {
        document.querySelector('#addAddress').addEventListener("click",
            function () {
                let breeder = formToString();
                window.open("/breeder/addAddress?" + breeder, "_self", "location=no");
            }
        )
    }
    let isNewAddress = document.querySelector('#newAddress') !== null;
    if (isNewAddress) {
        document.querySelector('#newAddress').addEventListener("click",
            function () {
                window.open("/address/", "_self", "location=no");
            }
        )
    }
    let isAddAddressToBreeder = document.querySelector("input[type='button'].addAddressToBreeder") !== null;
    if (isAddAddressToBreeder) {
        document.querySelectorAll("input[type='button'].addAddressToBreeder").forEach(
            function (element) {
                element.addEventListener("click",
                function () {
                    let breederId = element.form.querySelector("input[name='breederId']").value;
                    let addressId =element.form.querySelector("input[name='addressId']").value;

                    let xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        console.log(this.responseText);
                        console.log("success!");
                        window.open("/breeder/"+breederId, "_self", "location='no'");
                    };
                    xhttp.open("POST", "/breeder/add/"+breederId+"/"+addressId, true);
                    //xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                    console.log("sending request! "+breederId+"/"+addressId);
                    xhttp.send()
                    //let site = window.open("/breeder/add/"+breederId+"/"+addressId, "_blank", "location='no'");
                    }
                )
            }
        )
    }
})