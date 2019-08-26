let loginButton;
let logoutButton;
const url = 'http://localhost:8080/user'
let navbar;

$(document).ready(()=>{ 
    loginButton = $("#navigation-login")
    logoutButton = $("#navigation-logout").detach();
    navbar = $("#navbar-list");
});


function loginIntoApp() {
    const loginFormData = $("#login-form").serializeArray();
    let login;
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
    }).done(()=>{
        returnHome();
        loginButton.detach();
        navbar.append(logoutButton);
    });
}

function logout() {
    $.ajax({
        url: `${url}/logout`,
        type: "POST"
    }).done(()=>{
        logoutButton.detach();
        navbar.append(loginButton);
    });

}