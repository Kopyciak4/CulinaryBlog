package com.culinaryblog.domain.recipe.dish;

import com.culinaryblog.domain.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Dish  {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('DINNER', 'SUPPER', 'BREAKFAST', 'SNACK', 'SALAD', 'SOUP', 'DESERT', 'PRESERVES', 'VEGE', 'LIGHT')")
    @NotNull
    private DishType type;

    @Id
    @NotNull
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @NotNull
    @Column(nullable = false)
    @Size(max=45)
    private String mainIngredient;
    @Size(max=45)
    private String country;
    @NotNull
    @Column(nullable = false)
    @Size(max=100)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dish")
    @JsonIgnoreProperties("dish")
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
