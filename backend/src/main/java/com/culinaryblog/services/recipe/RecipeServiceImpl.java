package com.culinaryblog.services.recipe;

import com.culinaryblog.DAO.recipe.RecipeRepository;
import com.culinaryblog.domain.recipe.dish.DishType;
import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;
import com.culinaryblog.services.file.FileService;
import com.culinaryblog.services.file.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private FileService fileService;

    @Autowired
    public RecipeServiceImpl (RecipeRepository recipeRepository, FileService fileService) {
        this. recipeRepository = recipeRepository;
        this.fileService = fileService;
    }

    @Override
    public void createRecipe(Recipe recipe,  MultipartFile photo){
        recipe.setInsertionDate(new Date());
        Recipe savedRecipe = recipeRepository.save(recipe);
        savedRecipe.setPhoto(fileService.save(photo, savedRecipe.getId()));
        recipeRepository.save(savedRecipe);
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

    @Override
    public byte[] getPhotosForPaths(@RequestBody List<String> recipePhotoList) {
        return this.fileService.loadPhotosByPaths(recipePhotoList);
    }





}
