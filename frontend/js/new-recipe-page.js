function addRecipe() {
    const form = $('#new-recipe-form');
    const serializedForm = $('#new-recipe-form').serializeArray();
    let formData = new FormData();
    formData.append('recipe', new Blob([JSON.stringify({
        dish : {
            name : serializedForm[0].value,
            mainIngredient : serializedForm[1].value,
            country : serializedForm[2].value,
            type : serializedForm[3].value
        },
        level : serializedForm[4].value,
        preparationTime : serializedForm[5].value,
        ingredients : serializedForm[6].value,
        preparation : serializedForm[7].value,
        additionalInfo : serializedForm[8].value,
        videoLink : serializedForm[9].value,
        userLogin : login
    })], {
        type: 'application/json'
    }));
    formData.append('photo', $('#photo')[0].files[0]);
    $.ajax({
        url: 'http://localhost:8080/recipes',
        type: "POST",
        processData: false,
        contentType: false,
        data: formData
    }).done(()=>{
        toastr.success('Post created successfully')
        returnHome();
        fetchLastThreeAdded();
    });
    
}

function disableNewRecipeButtonIfFormEmpty() {
    newRecipePage.find('#new-recipe-button')[0].disabled = hasFormEmptyRequiredInputs();
}

function checkFileExtension(fileInput) {
    const allowedExtensions = ['gif', 'jpg', 'jpeg', 'png', 'pjpeg'];
    const dotIndex = fileInput.value.lastIndexOf('.');
    const hasCorrectData = (!fileInput || dotIndex && allowedExtensions.some(extension => extension === fileInput.value.substring(dotIndex + 1)))
        && !hasFormEmptyRequiredInputs();
    newRecipePage.find('#new-recipe-button')[0].disabled = !hasCorrectData;
}

function hasFormEmptyRequiredInputs() {
    return $("#new-recipe-form input, #new-recipe-form textarea").toArray().some(element => element.required && !element.value);
}