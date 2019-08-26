package com.culinaryblog.services.recipe;

import com.culinaryblog.DAO.recipe.RecipeRepository;
import com.culinaryblog.domain.recipe.dish.DishType;
import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl (RecipeRepository recipeRepository) {
        this. recipeRepository = recipeRepository;
    }

    @Override
    public void createRecipe(Recipe recipe){
        recipe.setInsertionDate(new Date());
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipiesByCriteriaSearch(RecipeSearchCriteria recipeSearchCriteria){
       return recipeRepository.findRecipiesByCriteriaSearch(recipeSearchCriteria);
    }

    @Override
    public List<Recipe> getRecipiesByQuery(String query){
        DishType[] types = DishType.values();
        DishType searchedType = null;
        for(DishType type : types) {
            if (type.name().equals(query)) {
                searchedType = type;
            }
        }
        return searchedType != null ? recipeRepository.findByDishTypeContainingOrDishMainIngredientContainingOrDishCountryContainingOrDishNameContaining(searchedType , query, query, query)
                : recipeRepository.findByDishMainIngredientContainingOrDishCountryContainingOrDishNameContaining(query, query, query);
    }

    @Override
    public Recipe getRecipeById(int id){
        return this.recipeRepository.findById(id).get();
    }

    @Override
    public void increasePoints(int id){
        Recipe recipe = this.recipeRepository.getOne(id);
        recipe.setPoints(recipe.getPoints() + 1);
        this.recipeRepository.save(recipe);
    }

    @Override
    public void decreasePoints(int id){
        Recipe recipe = this.recipeRepository.getOne(id);
        recipe.setPoints(recipe.getPoints() - 1);
        this.recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getLastAddedRecipes(int quantity){
        System.out.println(quantity);
        return this.recipeRepository.findByOrderByInsertionDateDesc(PageRequest.of(0, quantity));
    }




}
