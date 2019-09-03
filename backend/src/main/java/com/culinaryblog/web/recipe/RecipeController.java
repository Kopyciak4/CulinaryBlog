package com.culinaryblog.web.recipe;

import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import com.culinaryblog.services.file.FileService;
import com.culinaryblog.services.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/recipes")
@RestController
public class RecipeController {

    private RecipeService recipeService;
    private FileService fileService;

    @Autowired
    public void setRecipeService (RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    public void createRecipe(@Valid @RequestPart Recipe recipe, @RequestPart("photo") MultipartFile photo){
        System.out.println(photo);
        recipeService.createRecipe(recipe, photo);
    }

    @PostMapping("/search")
    public List<Recipe> searchRecipesBySearchCriteria(@RequestBody RecipeSearchCriteria recipeSearchCriteria) {
        return this.recipeService.getRecipiesByCriteriaSearch(recipeSearchCriteria);
    }
    @GetMapping("/search")
    public List<Recipe> searchRecipesByQuery(@RequestParam("query") String query){
        return this.recipeService.getRecipiesByQuery(query);
    }
    @GetMapping(value = "/{id}")
    public Recipe getRecipeById(@PathVariable int id) {
        return this.recipeService.getRecipeById(id);
    }

    @PutMapping("/increase-points/{id}")
    public void increasePoints(@PathVariable int id){
        recipeService.increasePoints(id);
    }

    @PutMapping("/decrease-points/{id}")
    public void decreasePoints(@PathVariable int id){
        recipeService.decreasePoints(id);
    }

    @GetMapping()
    public List<Recipe> getLastAddedRecipes(@RequestParam("quantity") int quantity) {
        return this.recipeService.getLastAddedRecipes(quantity);
    }

    @PostMapping(value = "/photos", produces="application/zip")
    public ResponseEntity<byte[]> getPhotos(@RequestBody List<String> recipePhotoList){
        System.out.println(this.recipeService.getPhotosForPaths(recipePhotoList).length);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=photos.zip").body(this.recipeService.getPhotosForPaths(recipePhotoList));
    }



}
