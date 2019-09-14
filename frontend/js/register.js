function register() {
    const registerFormData = $("#register-form").serializeArray();
    let login;
    let password;

    if(registerFormData[0].name === 'login'){ 
        login = registerFormData[0].value;
        password = registerFormData[1].value; 
     } else {
         login = registerFormData[1].value;
         password = registerFormData[0].value;
     }


     $.ajax({
        url : 'http://localhost:8080/user/register',
        type : "POST",
        data : JSON.stringify({login: login, password: password}),
        contentType: 'application/json' 
     }).done(()=>{
         toastr.success("User created");
         
     }).fail(() => {
        toastr.error('Duplicated user');
    });
}

function disableRegisterButtonIfFormEmpty() {
    registerPage.find('#register-button')[0].disabled = $("#register-form input").toArray().some(element => element.required && !element.value);
}