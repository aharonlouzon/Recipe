package com.login.recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends ArrayList<Recipe> {
    private static final long serialVersionUID = 1L;
    public RecipeList() {
        super();
    }
    public RecipeList(List<Recipe> c) {
        super(c);
    }

    public List<Recipe> getRecipes() {
        return this;
    }
    public void setRecipes(List<Recipe> recipes) {
        this.addAll(recipes);
    }
}