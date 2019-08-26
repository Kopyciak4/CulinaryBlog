package com.culinaryblog.services.recipe;

import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeService {

    void createRecipe(Recipe recipe);

    List<Recipe> getRecipiesByCriteriaSearch(RecipeSearchCriteria recipeSearchCriteria);

    List<Recipe> getRecipiesByQuery(String query);

    List<Recipe> getLastAddedRecipes(int quantity);

    Recipe getRecipeById(int id);

    void increasePoints(int id);

    void decreasePoints(int id);
}

