package com.culinaryblog.DAO.recipe;

import com.culinaryblog.domain.recipe.dish.DishType;
import com.culinaryblog.domain.recipe.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, RecipeRepositoryCustom {

    List<Recipe> findByDishTypeContainingOrDishMainIngredientContainingOrDishCountryContainingOrDishNameContaining(DishType type, String ingredient, String country, String name);

    List<Recipe> findByDishMainIngredientContainingOrDishCountryContainingOrDishNameContaining(String ingredient, String country, String name);

    List<Recipe> findByOrderByInsertionDateDesc(Pageable pageable);


}
