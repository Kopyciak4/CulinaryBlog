let article;

$(document).ready(() => {
    article = $("#post-page");
});

function goToPost(recipe, base64Data) {
    // let ingredientsList = recipe.ingredients.split(",");
    // console.log(2323);
    // console.log(ingredientsList);
    
    // let ingredientsListTemplate = "<ul>";

    // ingredientsList.forEach(ingredient => {
    //    ingredientsListTemplate += `<li>${ingredient}</li>`;
    // });
    // ingredientsListTemplate += "</ul>";
    

    

    article.empty();
    const recipePost = $(`
        <div class="col-lg-8">

            <!-- Title -->
            <h1 class="mt-4">${recipe.dish.name}</h1>

            <!-- Author -->
            <p class="lead">
                by
                <a>${recipe.userLogin}</a>
            </p>

            <hr>

            <!-- Date/Time -->
            <p>Posted on ${new Date(recipe.insertionDate).toDateString()}</p>

            <hr>

            <!-- Preview Image -->
            <img class="img-fluid rounded" src="${base64Data ? 'data:image/jpg;base64,' + base64Data : 'http://placehold.it/750x300'}">

            <hr>

            <!-- Post Content -->
            <p>Ingredients: 
                <ul>
                    ${recipe.ingredients.split(",").reduce((previous, next) => previous + '<li>' + next + '</li>', '')}
                </ul>
            </p>

            <p class="lead">${recipe.preparation}</p>

            <blockquote class="blockquote">
                <p class="mb-0">${recipe.additionalInfo}</p>
            </blockquote>

            <p>Preparation time is estimated around: ${recipe.preparationTime} minutes</p>

            <p>Link to the video -> <a target="_blank" href=${recipe.videoLink}>Link</a>
            
            </p>

            <hr>
        </div>
    `);
    article.append(recipePost);
    openPage(article);
}



// $(document).ready(()=>{ 
//     loginButton = $("#navigation-login")
//     logoutButton = $("#navigation-logout").detach();
//     addRecipePage = $("#navigation-new-recipe").detach();
//     navbar = $("#navbar-list");
//     albo czekaj bo w sumie widze ze tu w login tez miales detach dla innych rzeczy chyba? wiec moze sprobuj tu czy zadziala ale tu masz detacha dla logoutta i add recipe wiec jest tak jak mowiles, tutaj musze po prsotu
// });