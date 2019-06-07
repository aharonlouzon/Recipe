package com.login.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Recipe implements Serializable {

    private String name;
    private String recipeId;
    private ArrayList<String> directions = new ArrayList<>();
    private ArrayList<String> kitchenType = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    private Map<String, String> ingredients = new HashMap<String, String>();
    private String Author;
    private long releaseDate;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(String step) {
        this.directions.add(step);
    }

    public ArrayList<String> getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType.add(kitchenType);
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public void setImages(int image) {
        this.images.add(image);
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(String name, String quantity) {
        this.ingredients.put(name, quantity);
    }
}
