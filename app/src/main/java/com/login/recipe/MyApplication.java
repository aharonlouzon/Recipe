package com.login.recipe;

import android.app.Application;

public class MyApplication extends Application {

    private UserProfile user;
    private String newPassword;
    private final DatabaseService database = new DatabaseService();
    private Recipe recipe;
    private String freeTextSearch;

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

    public String getFreeTextSearch() {
        return freeTextSearch;
    }

    public void setFreeTextSearch(String freeTextSearch) {
        this.freeTextSearch = freeTextSearch;
    }
}
