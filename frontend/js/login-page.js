let loginButton;
let logoutButton;
const url = 'http://localhost:8080/user'
let navbar;
let addRecipePage;
let login;

$(document).ready(()=>{ 
    loginButton = $("#navigation-login")
    logoutButton = $("#navigation-logout").detach();
    addRecipePage = $("#navigation-new-recipe").detach();
    navbar = $("#navbar-list");

});


function loginIntoApp() {
    const loginFormData = $("#login-form").serializeArray();
    let password;

    if(loginFormData[0].name === 'login'){ 
       login = loginFormData[0].value;
       password = loginFormData[1].value; 
    } else {
        login = loginFormData[1].value;
        password = loginFormData[0].value;
    }

    console.log(login);
    $.ajax({
        url: `${url}/login`,
        headers: {
            "Authorization": `Basic ${login}:${password}`
        },
        type: "GET"
    }).done(() => {
        returnHome();
        loginButton.detach();
        navbar.prepend(addRecipePage).append(logoutButton);
    });
}

function logout() {
    $.ajax({
        url: `${url}/logout`,
        type: "POST"
    }).done(()=>{
        logoutButton.detach();
        addRecipePage.detach();
        navbar.append(loginButton);
        returnHome();
    });

}