package com.login.recipe;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(Recipe.class)
public class RecipeList extends ArrayList<Recipe> {
    private static final long serialVersionUID = 1L;
    public RecipeList() {
        super();
    }
    public RecipeList(List<Recipe> c) {
        super(c);
    }
    @XmlElement(name = "recipe")
    public List<Recipe> getRecipes() {
        return this;
    }
    public void setRecipes(List<Recipe> recipes) {
        this.addAll(recipes);
    }
}