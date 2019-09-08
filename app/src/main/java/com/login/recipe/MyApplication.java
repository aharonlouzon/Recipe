package com.login.recipe;

import android.app.Application;
import android.graphics.Bitmap;

public class MyApplication extends Application {

    private UserProfile user;
    private String newPassword;
    private final DatabaseService database = new DatabaseService();
    private Recipe recipe;
    private String searchByFreeText;
    private String searchByEmail;
    private Recipe.recipeType searchByType;
    private UserProfile.skillLevel searchBySkills;
    private String searchByCuisine;
    private Bitmap imageShow;
    private UserProfile visitedUser;
    private String userListType;
    private boolean isMyArea = true;

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public DatabaseService getDatabase() {
        return database;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getSearchByFreeText() {
        return searchByFreeText;
    }

    public void setSearchByFreeText(String searchByFreeText) {
        this.searchByFreeText = searchByFreeText;
    }

    public String getSearchByEmail() {
        return searchByEmail;
    }

    public void setSearchByEmail(String searchByEmail) {
        this.searchByEmail = searchByEmail;
    }

    public Recipe.recipeType getSearchByType() {
        return searchByType;
    }

    public void setSearchByType(Recipe.recipeType searchByType) {
        this.searchByType = searchByType;
    }

    public String getSearchByCuisine() {
        return searchByCuisine;
    }

    public void setSearchByCuisine(String searchByCuisine) {
        this.searchByCuisine = searchByCuisine;
    }

    public UserProfile.skillLevel getSearchBySkills() {
        return searchBySkills;
    }

    public void setSearchBySkills(UserProfile.skillLevel searchBySkills) {
        this.searchBySkills = searchBySkills;
    }

    @SuppressWarnings("unused")
    public void log_out(){
        this.user = null;
        this.newPassword = null;
        this.recipe = null;
    }

    public Bitmap getImageShow() {
        return imageShow;
    }

    public void setImageShow(Bitmap imageShow) {
        this.imageShow = imageShow;
    }

    public UserProfile getVisitedUser() {
        return visitedUser;
    }

    public void setVisitedUser(UserProfile visitedUser) {
        this.visitedUser = visitedUser;
    }

    public String getUserListType() {
        return userListType;
    }

    public void setUserListType(String userListType) {
        this.userListType = userListType;
    }

    public boolean isMyArea() {
        return isMyArea;
    }

    public void setIsMyArea(boolean myArea) {
        this.isMyArea = myArea;
    }
}
