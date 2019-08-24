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

    private static final String BASE_URL = "http://10.0.2.2:8080/RecipeAppDatabaseService/webresources/";

    /**
     * validate if correct password (on sign-in)
     * @return if correct - the user's profile. if incorrect username or password - returns null
     * @throws IOException if there is a error in connecting to the server
     */
    public UserProfile validateSignIn(String email, String password) throws IOException {
        String url = BASE_URL + "userProfile/signIn/" + email + "/" + password;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            return new Gson().fromJson(reader, UserProfile.class);
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
        } finally {
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
            return new Gson().fromJson(reader, UserProfile.class);
        }
    }

    /**
     * sends email to user with password
     * @return if successful - "email sent to " + userEmail.
     *    if userEmail does not exist - returns null
     * @throws IOException if error in connection to the server
     */
    public String forgotPassword(String userEmail) throws IOException {
        String url = BASE_URL + "userProfile/forgotPassword/" + userEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                return null;
            else
                return response.getMessage();
        }
    }

    /**
     * change the profile picture of an existing User
     * @return if successfully added - "change profile pic for " + userEmail .
     *         if no profile exists for the given email - returns null
     * @throws IOException if there is a error in connecting to the server
     */
    public String changeProfilePic(byte[] picture, String userEmail) throws IOException {
        String url = BASE_URL + "userProfile/changeProfilePic/" + userEmail;
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
            writer.write(new Gson().toJson(new ProfilePic(picture)));
            writer.flush();

            reader = new InputStreamReader(connection.getInputStream());
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            reader.close();
            if (response == null)
                return null;
            else
                return response.getMessage();
        } finally {
            closeResources(connection,reader,writer);
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
        } finally {
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
            return new Gson().fromJson(reader, Recipe.class);
        } finally {
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
            return new Gson().fromJson(reader, RecipeList.class);
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
            return new Gson().fromJson(reader, RecipeList.class);
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
        } finally {
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
        } finally {
            closeResources(connection, reader, writer);
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
        catch(Exception ignored) {

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
