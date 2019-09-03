let loginPage;
let recipesListPage;
let registerPage;
let actualPage;
let newRecipePage;
let resultValue;
let recipePost;

$(document).ready(()=>{
    zip.workerScriptsPath = "vendor/unzip/";
    loginPage = $("#login-form-page").detach();
    recipesListPage = $("#recipes-list-page")
    actualPage = recipesListPage;
    registerPage = $("#register-form-page").detach();
    newRecipePage = $("#new-recipe-form-page").detach();
    

    $.ajax({
        url: 'http://localhost:8080/recipes?quantity=3',
        type: "GET",
        contentType: 'application/json',
    }).done((recipes) => {
        let photos = [];
        recipes.forEach((recipe) => {
            photos.push(recipe.photo);
        })
        $.ajax({
            url: 'http://localhost:8080/recipes/photos',
            type: "POST",
            data : JSON.stringify(photos),
            contentType: 'application/json',
            xhr: () => {// Seems like the only way to get access to the xhr object
                var xhr = new XMLHttpRequest();
                xhr.responseType= 'blob'
                return xhr;
            },
        }).done((recipesPhotos)=>{
            console.log(recipesPhotos);
            var responseArray = new Uint8Array(recipesPhotos);
            console.log(responseArray);
            console.log(123);
            var blobData = new Blob([responseArray], {
                type: 'application/zip'
            });
            const blob = new Blob([recipesPhotos], {
                type: 'application/zip'
            });

            console.log(blobData);
            console.log(blob);
            zip.createReader(new zip.BlobReader(recipesPhotos), (reader)=> {
                reader.getEntries((entries)=>{
                    console.log(entries);
                    if(entries.length){

                    }
                })
            }, (error) => {
                console.log(error);
            });
        })
        //console.log(recipes);
        let postsElement = $('#recipe-posts');
        recipes.forEach((recipe) => {
            recipePost = $(
                `<div class="card mb-4">
                <img class="card-img-top" src="http://placehold.it/750x300" alt="Card image cap">
                <div class="card-body">
                  <h2 class="card-title">${recipe.dish.name}</h2>
                  <p class="card-text">${recipe.preparation.substring(0,50)}</p>
                  <a href="#" class="btn btn-primary">Read More &rarr;</a>
                </div>
                <div class="card-footer text-muted">
                  Posted on ${new Date(recipe.insertionDate).toDateString()} by
                  <a href="#">${recipe.userLogin}</a>
                </div>
              </div>`
            )
            postsElement.append(recipePost);        
        });     
            
    });

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

function openNewRecipePage() {
    openPage(newRecipePage);
}

function openPage(page) {
    actualPage.detach();
    $("#page-container").prepend(page);
    actualPage = page;
}

function resetForm(formSelector) { 
    $(formSelector)[0].reset();
}

