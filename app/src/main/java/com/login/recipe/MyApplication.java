package com.login.recipe;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class MyApplication extends Application {

    private UserProfile user;
    private String newPassword;
    private final DatabaseService database = new DatabaseService();
    private Recipe recipe;
    private String searchByFreeText;
    private String searchByEmail;
    private Recipe.recipeType searchByType;
    private UserProfile.skillLevel searchBySkills;
    private ArrayList<String> searchByCuisine = new ArrayList<>();
    private Bitmap imageShow;
    private UserProfile visitedUser;
    private String userListType;
    private boolean isMyArea = true;
    private boolean isHome = true;
    private UserProfile userListResource;

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
        if (this.isHome)
            return null;
        else
            return searchByFreeText;
    }

    public void setSearchByFreeText(String searchByFreeText) {
        this.searchByFreeText = searchByFreeText;
    }

    public String getSearchByEmail() {

        if (this.isHome)
            return null;
        else
            return searchByEmail;
    }

    public void setSearchByEmail(String searchByEmail) {
        this.searchByEmail = searchByEmail;
    }

    public Recipe.recipeType getSearchByType() {

        if (this.isHome)
            return null;
        else
            return searchByType;
    }

    public void setSearchByType(Recipe.recipeType searchByType) {
        this.searchByType = searchByType;
    }

    public String[] getSearchByCuisine() {
        if (this.isHome || (this.searchByCuisine.size() == 0))
            return null;
        else
            return this.searchByCuisine.toArray(new String[searchByCuisine.size()]);
    }

    public void setSearchByCuisine(String searchByCuisine) {
        this.searchByCuisine.add(searchByCuisine);
    }

    public UserProfile.skillLevel getSearchBySkills() {

        if (this.isHome)
            return getUser().getCookingSkills();
        else
            return searchBySkills;
    }

    public void setSearchBySkills(UserProfile.skillLevel searchBySkills) {
        this.searchBySkills = searchBySkills;
    }

    public void log_out() {
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

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public UserProfile getUserListResource() {
        return userListResource;
    }

    public void setUserListResource(UserProfile userListResource) {
        this.userListResource = userListResource;
    }
}
