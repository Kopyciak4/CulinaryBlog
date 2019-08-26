package com.culinaryblog.domain.recipe;

import com.culinaryblog.domain.recipe.dish.DishType;

import java.io.Serializable;

public class RecipeSearchCriteria implements Serializable {

    private Integer minPreparationTime;
    private Integer maxPreparationTime;
    private boolean favouriteDishes;
    private String mainIngredient;
    private String country;
    private Integer level;
    private DishType dishType;

    public Integer getMinPreparationTime() {
        return minPreparationTime;
    }

    public void setMinPreparationTime(Integer minPreparationTime) {
        this.minPreparationTime = minPreparationTime;
    }

    public Integer getMaxPreparationTime() {
        return maxPreparationTime;
    }

    public void setMaxPreparationTime(Integer maxPreparationTime) {
        this.maxPreparationTime = maxPreparationTime;
    }

    public boolean getFavouriteDishes() {
        return favouriteDishes;
    }

    public void setFavouriteDishes(boolean favouriteDishes) {
        this.favouriteDishes = favouriteDishes;
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
}
