package com.login.recipe;

import com.login.recipe.Recipe.recipeType;
import com.login.recipe.UserProfile.skillLevel;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseService {

    private static final String BASE_URL = "http://localhost:8080/RecipeAppDatabaseService/webresources/";

    /**
     * validate if correct password (on sign-in)
     * @return if correct - the user's profile. if incorrect username or password - returns null
     * @throws IOException if there is a error in connecting to the server
     */
    public UserProfile validateSignIn(String email, String password) throws IOException {
        String url = BASE_URL + "userProfile/signIn/" + email + "/" + password;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            return user;
        }
        catch(IOException e) {
            throw e;
        }
    }

    /**
     * add a new user to the database (on first sign-up)
     * @return if successfully added - "added " + user.getEmail() .
     *         if a profile already exists for the given email - returns null
     * @throws IOException if there is a error in connecting to the server
     */
    public String addUser(UserProfile user, String password) throws IOException {
        String url = BASE_URL + "userProfile/add/" + password;
        HttpURLConnection connection = null;
        OutputStreamWriter writer = null;
        InputStreamReader reader = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(user));
            writer.flush();

            reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            reader.close();
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
        catch(IOException e) {
            throw e;
        }
        finally {
            closeResources(connection,reader,writer);
        }
    }

    /**
     * get User profile based on a given email
     * @return if the user exists - the users profile. if doesn't exist - returns null
     * @throws IOException if there is an error connecting to the server
     */
    public UserProfile getUser(String email) throws IOException {
        String url = BASE_URL + "userProfile/getUser/" + email;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            return user;
        }
        catch(IOException e) {
            throw e;
        }
    }

    /**
     * add a follower to a user
     * @return if successful - "added follower " + followerEmail + " for " + userEmail.
     *    if userEmail or followerEmail doesn't exist - returns null
     * @throws IOException if error in connection to the server
     */
    public String addFollower(String userEmail, String followerEmail) throws IOException {
        String url = BASE_URL + "userProfile/addFollower/" + userEmail + "/" + followerEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
        catch(IOException e) {
            throw e;
        }
    }

    /**
     * delete a follower from a user
     * @return if successful - "deleted follower " + followerEmail + " for " + userEmail.
     *    if userEmail or followerEmail doesn't exist - returns null
     * @throws IOException if error in connection to the server
     */
    public String deleteFollower(String userEmail, String followerEmail) throws IOException {
        String url = BASE_URL + "userProfile/deleteFollower/" + userEmail + "/" + followerEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
        catch(IOException e) {
            throw e;
        }
    }
    /**
     * add a new recipe to the database
     * don't need to provide recipeID - automatically assigned by the DB (auto-increment)
     * @return if successful - "added recipe " + recipe.getName().
     *         if unsuccessful (because of bad input) - returns null
     * @throws IOException if error in connecting to database
     */
    public String addRecipe(Recipe recipe) throws IOException {
        String url = BASE_URL + "recipe/add";
        HttpURLConnection connection = null;
        OutputStreamWriter writer = null;
        InputStreamReader reader = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(recipe));
            writer.flush();

            reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
        catch(IOException e) {
            throw e;
        }
        finally {
            closeResources(connection, reader, writer);
        }
    }

    /**
     * get a recipe based on its ID
     * @return if the recipeID exists - returns the recipe. if doesn't exist - returns null
     * @throws IOException if there is an error connecting to the server
     */
    public Recipe getRecipe(int recipeID) throws IOException {
        String url = BASE_URL + "recipe/getRecipe/" + recipeID;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new URL(url).openStream());
            Recipe recipe = new Gson().fromJson(reader, Recipe.class);

            if (recipe != null) {
                url = BASE_URL + "recipe/getComments/" + recipeID;
                reader = new InputStreamReader(new URL(url).openStream());
                CommentList comments = new Gson().fromJson(reader, CommentList.class);
                recipe.setComments(comments);

                url = BASE_URL + "recipe/getPictures/" + recipeID;
                reader = new InputStreamReader(new URL(url).openStream());
                PictureList pictures = new Gson().fromJson(reader, PictureList.class);
                recipe.setImages(pictures);
            }

            return recipe;
        }
        catch(IOException e) {
            throw e;
        }
        finally {
            closeResources(null,reader,null);
        }
    }

    /**
     * get all the recipes of a specific user
     * @return if the user exists - returns the users recipeList. if doesn't exist - returns null
     * @throws IOException if there is an error connecting to the server
     */
    public RecipeList getUsersRecipes(String email) throws IOException {
        String url = BASE_URL + "recipe/getUsersRecipes/" + email;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            RecipeList recipes = new Gson().fromJson(reader, RecipeList.class);

            if (recipes != null) {
                CommentList comments;
                PictureList pictures;
                for (Recipe recipe : recipes) {
                    comments = getComments(recipe.getRecipeId());
                    recipe.setComments(comments);

                    pictures = getPictures(recipe.getRecipeId());
                    recipe.setImages(pictures);
                }
            }
            return recipes;
        }
        catch(IOException e) {
            throw e;
        }
    }

    /**
     * get recipes that meet given search parameters.
     * if a parameter can be anything - set to null
     * @return list of recipes that meat the given criteria (the list will be empty if none do).
     *         returns null if bad input.
     * @throws IOException if there is an error connecting to the server
     */
    public RecipeList searchRecipes(skillLevel skill, String cuisine, recipeType type, String authorEmail) throws IOException {
        String typeString = type.name();
        String skillString = skill.name();
        String url = BASE_URL + "recipe/" + skillString + "/" + cuisine + "/" + typeString + "/" + authorEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            RecipeList results = new Gson().fromJson(reader, RecipeList.class);

            if (results != null) {
                CommentList comments;
                PictureList pictures;
                for (Recipe recipe : results) {
                    comments = getComments(recipe.getRecipeId());
                    recipe.setComments(comments);

                    pictures = getPictures(recipe.getRecipeId());
                    recipe.setImages(pictures);
                }
            }

            return results;
        }
        catch (IOException e) {
            throw e;
        }
    }

    /**
     * add a comment to a recipe
     * @return if comment successfully added - "added comment to recipe " + recipeID.
     *         if recipeID or comment author doesn't exist - returns null
     * @throws IOException if error connecting to the server
     */
    public String addComment(int recipeID, Comment comment) throws IOException {
        String url = BASE_URL + "recipe/addComment/" + recipeID;
        HttpURLConnection connection = null;
        OutputStreamWriter writer = null;
        InputStreamReader reader = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(comment));
            writer.flush();

            reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
        catch(IOException e) {
            throw e;
        }
        finally {
            closeResources(connection, reader, writer);
        }
    }

    /**
     * add a picture to a recipe
     * @return if picture successfully added - "picture added to recipe " + recipeID.
     *         if recipeID doesn't exist - returns null
     * @throws IOException if error connecting to the server
     */
    public String addPicture(int recipeID, byte[] picture) throws IOException {
        String url = BASE_URL + "recipe/addPicture/" + recipeID;
        HttpURLConnection connection = null;
        OutputStreamWriter writer = null;
        InputStreamReader reader = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Method", "POST");
            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(picture));
            writer.flush();

            reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
        catch(IOException e) {
            throw e;
        }
        finally {
            closeResources(connection, reader, writer);
        }
    }

    private CommentList getComments (int recipeID)throws IOException {
        String url = BASE_URL + "recipe/getComments/" + recipeID;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            CommentList comments = new Gson().fromJson(reader, CommentList.class);
            return comments;
        }
        catch(IOException e) {
            throw e;
        }
    }

    private PictureList getPictures(int recipeID) throws IOException {
        String url = BASE_URL + "recipe/getPictures/" + recipeID;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            PictureList pictures = new Gson().fromJson(reader, PictureList.class);
            return pictures;
        }
        catch(IOException e) {
            throw e;
        }
    }

    private void closeResources(HttpURLConnection connection,InputStreamReader reader, OutputStreamWriter writer) {
        try {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        }
        catch(Exception e) {

        }
    }

    /**
     * Holds text response from the server
     */
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