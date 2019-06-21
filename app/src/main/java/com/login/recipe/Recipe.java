package com.login.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Date;

public class Recipe implements Serializable {

    public enum skillLevel{BEGINNER, INTERMEDIATE, PRO}
    public enum recipeType {APPETIZER, SOUP, SALAD, MAIN, DESSERT}

    private String name;
    private String recipeId;
    private recipeType type;
    private skillLevel skillLevel;
    private ArrayList<String> instructions;
    private ArrayList<String> cuisines;
    private ArrayList<Image> images;
    private Map<String, String> ingredients;
    private ArrayList<Comment> comments;
    private String Author;
    private Date releaseDate;

    public Recipe(String name, String recipeId, recipeType type, skillLevel skillLevel,
                  ArrayList<String> instructions, ArrayList<String> cuisines, ArrayList<Image> images,
                  Map<String, String> ingredients, ArrayList<Comment> comments, String Author, Date releaseDate) {
        this.name = name;
        this.recipeId = recipeId;
        this.type = type;
        this.skillLevel = skillLevel;
        this.instructions = instructions;
        this.cuisines = cuisines;
        this.images = images;
        this.ingredients = ingredients;
        this.comments = comments;
        this.Author = Author;
        this.releaseDate = releaseDate;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public skillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(skillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
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

    public ArrayList<String> getCusines() {
        return cuisines;
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

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
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