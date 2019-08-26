package com.culinaryblog.web.recipe;

import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import com.culinaryblog.services.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/recipes")
@RestController
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public void setRecipeService (RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    public void createRecipe(@Valid @RequestBody Recipe recipe){
        recipeService.createRecipe(recipe);
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


}
