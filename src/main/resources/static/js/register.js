// DNI LENGTH

let dni = document.getElementById("dni");


// EMAIL VALIDATION

let email = document.getElementById("email");

// PASSWORD STRENGTH

let password = document.getElementById("password");

// PASSWORD MATCH

let passwordMatch = document.getElementById("passwordMatch");

function checkPassword () {

    if (password.value == passwordMatch.value)
        passwordMatch.className = "form-control is-valid";
    else
        passwordMatch.className = "form-control is-invalid";
}

password.addEventListener('keyup', () => {
    if (passwordMatch.value.length != 0) checkPassword();
})

passwordMatch.addEventListener('keyup', checkPassword);