package com.culinaryblog.DAO.recipe;

import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepositoryCustom {

    List<Recipe> findRecipiesByCriteriaSearch(RecipeSearchCriteria recipeSearchCriteria);
}
