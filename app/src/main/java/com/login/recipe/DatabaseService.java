package com.login.recipe;

import com.login.recipe.Recipe.recipeType;
import com.login.recipe.UserProfile.skillLevel;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseService {

    private static final String BASE_URL = "http://localhost:8080/RecipeAppDatabaseService/webresources/";

    /**
     * validate if correct password (on sign-in)
     * @return if correct - the user's profile. if incorrect username or password - returns null
     */
    public UserProfile validateSignIn(String email, String password) {
        String url = BASE_URL + "userProfile/signIn/" + email + "/" + password;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            reader.close();
            return user;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * add a new user to the database (on first sign-up)
     */
    public String addUser(UserProfile user, String password) {
        String url = BASE_URL + "userProfile/add/" + password;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(user));
            writer.flush();
            writer.close();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            reader.close();
            return response.getMessage();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * delete account
     */
    public String deleteUser(String email) {
        String url = BASE_URL + "userProfile/delete/" + email;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            reader.close();
            return response.getMessage();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get User profile based on a given email
     * @return user profile
     */
    public UserProfile getUser(String email) {
        String url = BASE_URL + "userProfile/getUser/" + email;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            reader.close();
            return user;
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * add a follower to a user
     */
    public String addFollower(String userEmail, String followerEmail) {
        String url = BASE_URL + "userProfile/addFollower/" + userEmail + "/" + followerEmail;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            reader.close();
            return response.getMessage();
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * add a follower to a user
     */
    public String deleteFollower(String userEmail, String followerEmail) {
        String url = BASE_URL + "userProfile/deleteFollower/" + userEmail + "/" + followerEmail;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            return response.getMessage();
        }
        catch(Exception e) {
            return null;
        }
    }
    /**
     * add a new recipe to the database
     * don't need to provide recipeID - automatically assigned by the DB (auto-increment)
     */
    public String addRecipe(Recipe recipe) {
        String url = BASE_URL + "recipe/add";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(recipe));
            writer.flush();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            return response.getMessage();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get a recipe based on its ID
     */
    public Recipe getRecipe(int recipeID) {
        String url = BASE_URL + "recipe/getRecipe/" + recipeID;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            Recipe recipe = new Gson().fromJson(reader, Recipe.class);

            url = BASE_URL + "recipe/getComments/" + recipeID;
            reader = new InputStreamReader(new URL(url).openStream());
            CommentList comments = new Gson().fromJson(reader, CommentList.class);
            recipe.setComments(comments);

            url = BASE_URL + "recipe/getPictures/" + recipeID;
            reader = new InputStreamReader(new URL(url).openStream());
            PictureList pictures = new Gson().fromJson(reader, PictureList.class);
            recipe.setImages(pictures);

            return recipe;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get all the recipes of a specific user
     */
    public RecipeList getUsersRecipes(String email) {
        String url = BASE_URL + "recipe/getUsersRecipes/" + email;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            RecipeList recipes = new Gson().fromJson(reader, RecipeList.class);

            CommentList comments;
            PictureList pictures;
            for (Recipe recipe : recipes) {
                comments = getComments(recipe.getRecipeId());
                recipe.setComments(comments);

                pictures = getPictures(recipe.getRecipeId());
                recipe.setImages(pictures);
            }
            return recipes;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get recipes that meet given search parameters.
     * if a parameter can be anything - set to null
     */
    public RecipeList searchRecipes(skillLevel skill, String cuisine, recipeType type, String authorEmail) {
        String typeString = type.name();
        String skillString = skill.name();
        String url = BASE_URL + "recipe/" + skillString + "/" + cuisine + "/" + typeString + "/" + authorEmail;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            RecipeList results = new Gson().fromJson(reader, RecipeList.class);

            CommentList comments;
            PictureList pictures;
            for (Recipe recipe : results) {
                comments = getComments(recipe.getRecipeId());
                recipe.setComments(comments);

                pictures = getPictures(recipe.getRecipeId());
                recipe.setImages(pictures);
            }

            return results;
        }
        catch (Exception e) {
            return null;
        }
    }

    public String addComment(int recipeID, Comment comment) {
        String url = BASE_URL + "recipe/addComment/" + recipeID;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(comment));
            writer.flush();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            return response.getMessage();
        }
        catch(Exception e) {
            return null;
        }
    }

    public String addPicture(int recipeID, byte[] picture) {
        String url = BASE_URL + "recipe/addPicture/" + recipeID;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(picture));
            writer.flush();

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            return response.getMessage();
        }
        catch(Exception e) {
            return null;
        }
    }

    private CommentList getComments(int recipeID) {
        String url = BASE_URL + "recipe/getComments/" + recipeID;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            CommentList comments = new Gson().fromJson(reader, CommentList.class);
            return comments;
        }
        catch(Exception e) {
            return null;
        }
    }

    private PictureList getPictures(int recipeID) {
        String url = BASE_URL + "recipe/getPictures/" + recipeID;
        try {
            InputStreamReader reader = new InputStreamReader(new URL(url).openStream());
            PictureList pictures = new Gson().fromJson(reader, PictureList.class);
            return pictures;
        }
        catch(Exception e) {
            return null;
        }
    }

    class TextMessage {
        private String message;

        public TextMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}