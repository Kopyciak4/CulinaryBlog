
let loginPage;
let recipesListPage;
let registerPage;
let actualPage;

$(document).ready(()=>{
    loginPage = $("#login-form-page").detach();
    recipesListPage = $("#recipes-list-page")
    actualPage = recipesListPage;
    registerPage = $("#register-form-page").detach();
});

function openLoginPage() {
    openPage(loginPage);
    resetForm("#login-form");
}

function returnHome() {
    openPage(recipesListPage);
}

function openRegisterPage() {
    openPage(registerPage);
    resetForm("#register-form");
}

function openPage(page) {
    actualPage.detach();
    $("#page-container").prepend(page);
    actualPage = page;
}

function resetForm(formSelector) { 
    $(formSelector)[0].reset();
}