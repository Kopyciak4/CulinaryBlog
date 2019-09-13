package com.culinaryblog.DAO.recipe;

import com.culinaryblog.domain.recipe.Recipe;
import com.culinaryblog.domain.recipe.RecipeSearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    public List<Recipe> findRecipiesByCriteriaSearch(RecipeSearchCriteria recipeSearchCriteria){
        String sql = "SELECT d.id, d.type, d.main_ingredient, d.country, d.name, r.id, r.preparation_time, r.points, r.additional_info, r.level, r.ingredients, r.preparation, r.photo, r.video_link, r.insertion_date, r.dish_id, r.user_login FROM Recipe as r INNER JOIN Dish as d ON  dish_id = d.id";
        String whereSql = "";

        if(recipeSearchCriteria.getCountry() != null && !recipeSearchCriteria.getCountry().isEmpty()) {
            whereSql += addSqlKeyWords(whereSql," d.COUNTRY = '" + recipeSearchCriteria.getCountry() + "'");
        }
        if(recipeSearchCriteria.getDishType() != null)
            whereSql += addSqlKeyWords(whereSql, "d.TYPE = '" + recipeSearchCriteria.getDishType() + "'");
        if(recipeSearchCriteria.getLevel() != null)
            whereSql += addSqlKeyWords(whereSql, "r.LEVEL = '" + recipeSearchCriteria.getLevel() + "'");
        if(recipeSearchCriteria.getMainIngredient() != null)
            whereSql += addSqlKeyWords(whereSql, "d.MAIN_INGREDIENT = '" + recipeSearchCriteria.getMainIngredient() + "'");
        if(recipeSearchCriteria.getMaxPreparationTime() != null)
            whereSql += addSqlKeyWords(whereSql, "r.PREPARATION_TIME <= '" + recipeSearchCriteria.getMaxPreparationTime() + "'");
        if(recipeSearchCriteria.getMinPreparationTime() != null)
            whereSql += addSqlKeyWords(whereSql, "r.PREPARATION_TIME >= '" + recipeSearchCriteria.getMinPreparationTime() + "'");
        String favouritesSql = "";
        if(recipeSearchCriteria.getFavouriteDishes()){
            favouritesSql += " ORDER BY POINTS DESC LIMIT 2";
        }


        System.out.println("SQL: " + (sql + whereSql + favouritesSql));

        Query query = em.createNativeQuery(sql + whereSql + favouritesSql, Recipe.class);
        List<Recipe> recipes = query.getResultList();
        return recipes;
    }

    private String addSqlKeyWords(String whereSql, String newCondition) {
        return !whereSql.contains(" WHERE ") ? " WHERE " + newCondition : " AND " + newCondition;
    }

}
