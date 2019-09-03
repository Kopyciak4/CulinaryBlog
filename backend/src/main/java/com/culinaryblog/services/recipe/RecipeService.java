package com.culinaryblog.services.recipe;

import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface RecipeService {

    void createRecipe(Recipe recipe,  MultipartFile photo);

    List<Recipe> getRecipiesByCriteriaSearch(RecipeSearchCriteria recipeSearchCriteria);

    List<Recipe> getRecipiesByQuery(String query);

    List<Recipe> getLastAddedRecipes(int quantity);

    Recipe getRecipeById(int id);

    void increasePoints(int id);

    void decreasePoints(int id);

    byte[] getPhotosForPaths(List<String> recipePhotoList);


}

