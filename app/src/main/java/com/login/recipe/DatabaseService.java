package com.login.recipe;
import java.util.Date;


public class DatabaseService {

    public enum skillLevel{BEGINNER, INTERMEDIATE, PRO}
    public enum recipeType {APPETIZER, SOUP, SALAD, MAIN, DESSERT}

    /**
     * validate if correct password (on sign-in)
     * @return true if correct password, false if not
     */
    public boolean validatePassword(String email, String pasword) {
        return true;
    }

    /**
     * add a new user to the database (on first sign-up)
     */
    public void addUser(UserProfile user, String password) {

    }

    /**
     * delete account
     */
    public void deleteUser(String email) {

    }

    /**
     * get User profile based on a given email
     * @return user profile
     */
    public UserProfile getUser(String email) {

    }

    /**
     * add a follower to a user
     */
    public void addFollower(String userEmail, String followerEmail) {

    }

    /**
     * add a follower to a user
     */
    public void deleteFollower(String userEmail, String followerEmail) {

    }
    /**
     * add a new recipe to the database
     * don't need to provide recipeID - automatically assigned by the DB (auto-increment)
     */
    public void addRecipe(Recipe recipe) {

    }

    /**
     * get a recipe based on its ID
     */
    public Recipe getRecipe(int recipeID) {

    }

    /**
     * get recipes that meet given search parameters.
     * if a parameter can be anything - set to null
     */
    public Recipe[] searchRecipes(String skill, String cuisine, String type, String author) {

    }

    /**
     * add a comment to a recipe
     */
    public void addComment(int RecipeID, String comment) {

    }
}
