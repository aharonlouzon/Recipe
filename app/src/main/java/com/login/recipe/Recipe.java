package com.login.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;
import com.login.recipe.UserProfile.skillLevel;
import java.util.HashMap;
import java.util.UUID;

public class Recipe implements Serializable {

    public enum recipeType {APPETIZER, SOUP, SALAD, MAIN, DESSERT}

    private String name;
    private int recipeId;
    private recipeType type;
    private skillLevel skillLevel;
    private ArrayList<String> instructions;
    private ArrayList<String> cuisines;
    private ArrayList<byte[]> images;
    private Map<String, String> ingredients;
    private ArrayList<Comment> comments;
    private String author; // email address of the author
    private Date releaseDate;

    public Recipe() {
        this.comments = new ArrayList<>();
        this.cuisines = new ArrayList<>();
        this.images = new ArrayList<>();
        this.ingredients = new HashMap<>();
        this.instructions = new ArrayList<>();
    }

    public Recipe(String name, recipeType type, skillLevel skillLevel, String authorEmail, Date releaseDate) {
        this.name = name;
        this.type = type;
        this.skillLevel = skillLevel;
        this.author = authorEmail;
        this.releaseDate = releaseDate;
        this.comments = new ArrayList<>();
        this.cuisines = new ArrayList<>();
        this.images = new ArrayList<>();
        this.ingredients = new HashMap<>();
        this.instructions = new ArrayList<>();
    }



    public Recipe(String name, recipeType type, skillLevel skillLevel,
                  ArrayList<String> instructions, ArrayList<String> cuisines, ArrayList<byte[]> images,
                  Map<String, String> ingredients, ArrayList<Comment> comments, String authorEmail, Date releaseDate) {
        this.name = name;
        this.type = type;
        this.skillLevel = skillLevel;
        this.instructions = instructions;
        this.cuisines = cuisines;
        this.images = images;
        this.ingredients = ingredients;
        this.comments = comments;
        this.author = authorEmail;
        this.releaseDate = releaseDate;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public skillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(skillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String authorEmail) {
        author = authorEmail;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public void addInstruction(String instruction) {
        this.instructions.add(instruction);
    }

    public ArrayList<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<String> cuisines) {
        this.cuisines = cuisines;
    }

    public void addCuisine(String cuisine) {
        this.cuisines.add(cuisine);
    }

    public ArrayList<byte[]> getImages() {
        return images;
    }

    public void setImages(ArrayList<byte[]> images) {
        this.images = images;
    }

    public void addImage(byte[] image) {
        this.images.add(image);
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(String name, String quantity) {
        this.ingredients.put(name, quantity);
    }

    public recipeType getType() {
        return type;
    }

    public void setType(recipeType type) {
        this.type = type;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

}