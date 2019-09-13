let loginPage;
let recipesListPage;
let registerPage;
let actualPage;
let newRecipePage;
let resultValue;
let recipePost;
let postsElement;
let advancedSearch;
let basicSearch;

$(document).ready(()=>{
    loginPage = $("#login-form-page").detach();
    recipesListPage = $("#recipes-list-page")
    actualPage = recipesListPage;
    registerPage = $("#register-form-page").detach();
    newRecipePage = $("#new-recipe-form-page").detach();
    postsElement = $('#recipe-posts');
    basicSearch = $('#search-box');
    advancedSearch= $('#advanced-search-box').css({
        display : 'none'
    });
    fetchLastThreeAdded();
});

function fetchLastThreeAdded() {
    $.ajax({
        url: 'http://localhost:8080/recipes?quantity=3',
        type: "GET",
        contentType: 'application/json',
    }).done((recipes) => fillRecipesList(recipes));
}

function openAdvancedSearch() {
    $("#simple-search-input").val('');
    console.log(basicSearch);
    basicSearch.css({
        display: 'none'
    });
    advancedSearch.css({
        display: 'block'
    });
    loadRecipesByQuery('');
}

function openBasicSearch() {
    loadRecipesByQuery('');
    basicSearch.css({
        display: 'block'
    });
    advancedSearch.css({
        display: 'none'
    });
    resetForm("#advanced-search-box");
}

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
    console.log(actualPage);
    actualPage.detach();
    $("#page-container").prepend(page);
    actualPage = page;
}

function resetForm(formSelector) { 
    $(formSelector)[0].reset();
}

function createPost(recipe, base64Data) { 
    const recipePost = $(
        `<div class="card mb-4">
        <img class="card-img-top" src="${base64Data ? 'data:image/jpg;base64,' + base64Data : 'http://placehold.it/750x300'}" alt="Card image cap">
        <div class="card-body">
        <h2 class="card-title">${recipe.dish.name}</h2>
        <p class="card-text">${recipe.preparation.substring(0,50)}</p>
        <a class="btn btn-primary" id="read-more-${recipe.id}">Read More &rarr;</a>
        </div>
        <div class="card-footer text-muted">
        Posted on ${new Date(recipe.insertionDate).toDateString()} by
        <a href="#">${recipe.userLogin}</a>
        </div>
    </div>`
    )
    recipePost.find(`#read-more-${recipe.id}`).click(() => {
        goToPost(recipe, base64Data);
    });
    postsElement.append(recipePost);
}

function sortByCategory(dishCategory) {
    $.ajax({
        url: 'http://localhost:8080/recipes/search',
        type: "POST",
        data : JSON.stringify({
            dishType : dishCategory
        }),
        contentType: 'application/json',
    }).done((recipes) => fillRecipesList(recipes));
}

function fillRecipesList(recipes) {
    postsElement.empty();
    let photos = [];
    recipes.forEach((recipe) => {
        photos.push(recipe.photo);
    });

    $.ajax({
        url: 'http://localhost:8080/recipes/photos',
        type: "POST",
        data : JSON.stringify(photos),
        contentType: 'application/json',
        xhr: () => {
            var xhr = new XMLHttpRequest();
            xhr.responseType= 'blob'
            return xhr;
        },
    }).done((recipesPhotos)=>{
        var zip = new JSZip();
        zip.loadAsync(recipesPhotos, {base64:false}).then((zipContent)=>{
            const entryNames = Object.keys(zipContent.files);
            preparePostToCreate(recipes, 0, entryNames, zipContent, zip);
        }); 
    });      
}

const simpleSearch = debounce(() => {
    let value =  $('#simple-search-input').val();
    if (value.length >= 3) {
        loadRecipesByQuery(value);
    }
}, 1000);

function loadRecipesByQuery(value) {
    $.ajax({
        url: `http://localhost:8080/recipes/search?query=${value}`,
        type: "GET",
        contentType: 'application/json',
    }).done((recipes) => fillRecipesList(recipes));
}


// Returns a function, that, as long as it continues to be invoked, will not
// be triggered. The function will be called after it stops being called for
// N milliseconds. If `immediate` is passed, trigger the function on the
// leading edge, instead of the trailing.
function debounce(func, wait, immediate) {
	var timeout;
	return function() {
		var context = this, args = arguments;
		var later = function() {
			timeout = null;
			if (!immediate) func.apply(context, args);
		};
		var callNow = immediate && !timeout;
		clearTimeout(timeout);
		timeout = setTimeout(later, wait);
		if (callNow) func.apply(context, args);
	};
};


function checkNumberRange(event, element) {
    if((event.keyCode < 48 || event.keyCode > 57) || (+(''+ element.value + event.key) > +element.max)) { 
        event.preventDefault();
    }
}

function runAdvancedSearch() {
    const advancedSearchForm = $("#advanced-search-box");
    let formData = new FormData(advancedSearchForm[0]);
    let queryCriteria = {};
    for (var [key, value] of formData.entries()) { 
        queryCriteria[key] = value === '' ? null : value;
    }
    $.ajax({
        url: 'http://localhost:8080/recipes/search',
        type: "POST",
        contentType: 'application/json',
        data: JSON.stringify(queryCriteria)
    }).done((recipes) => fillRecipesList(recipes));

}

function preparePostToCreate(recipes, index, entryNames, zipContent, zip) {
    if (index < recipes.length) {
        const entryName = entryNames.find((entryName) => {
            return recipes[index].id === +(entryName.substring(0, entryName.indexOf('\\'))); 
        })
        if (entryName) {
            const entry = zipContent.files[entryName];
            zip.file(entry.name).async("uint8array").then((imageData) => {
                const base64Data = btoa(new Uint8Array(imageData).reduce(function (data, byte) {
                    return data + String.fromCharCode(byte);
                }, ''));
                createPost(recipes[index], base64Data);
                preparePostToCreate(recipes, index+1, entryNames, zipContent, zip);
            });
        } else {
            createPost(recipes[index]);
            preparePostToCreate(recipes, index+1, entryNames, zipContent, zip);
        }
    }
}





