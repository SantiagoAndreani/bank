
// PASSWORD MATCH

let password = document.getElementById("password");
let passwordMatch = document.getElementById("passwordMatch");

function checkPassword () {
    passwordMatch.className = password.value == passwordMatch.value ? "form-control is-valid" : "form-control is-invalid";
}

password.addEventListener('keyup', () => {
    if (passwordMatch.value.length != 0) checkPassword();
})

passwordMatch.addEventListener('keyup', checkPassword);