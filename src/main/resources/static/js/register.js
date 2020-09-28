
// DNI LENGTH

let dni = document.getElementById("dni");

function dniLength() {
    if (dni.value.length > dni.maxLength)
        dni.value = dni.value.slice(0, dni.maxLength);
    if (dni.value.length < dni.minLength)
        dni.className = "form-control is-invalid"
    if(dni.value.length == dni.maxLength || dni.value.length == dni.minLength)
        dni.className = "form-control is-valid"
}

// EMAIL VALIDATION

let email = document.getElementById("email");

function emailValidation() {
    let reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (reg.test(email.value))
        email.className = "form-control is-valid";
    else
        email.className = "form-control is-invalid";
}

// PASSWORD STRENGTH

let password = document.getElementById("password");

function passwordStrength() {
    let reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    if (reg.test(password.value))
        password.className = "form-control is-valid";
    else
        password.className = "form-control is-invalid";
}

// PASSWORD MATCH

let password2 = document.getElementById("password2");

function passwordMatch () {
    if (password.value == password2.value) {
        password2.className = "form-control is-valid";
        document.getElementById("registerBtn").disabled = false;
    }
    else
        password2.className = "form-control is-invalid";
}
password.addEventListener('keyup', () => {
    if (passwordMatch.value.length != 0) passwordMatch();
})
password2.addEventListener('keyup', passwordMatch);
