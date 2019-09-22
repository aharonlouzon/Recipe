package com.login.recipe;

import com.login.recipe.Recipe.recipeType;
import com.login.recipe.UserProfile.skillLevel;

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("ALL")
public class DatabaseService {

    private static final String BASE_URL = "http://10.0.2.2:8080/RecipeAppDatabaseService/webresources/";

    /**
     * validate if correct password (on sign-in)
     *
     * @return if correct - the user's profile.
     * @throws Exception   if incorrect username or password - with exception
     *                     message "invalid email or password"
     * @throws IOException if there is a error in connecting to the server
     */
    public UserProfile validateSignIn(String email, String password) throws Exception, IOException {
        String url = BASE_URL + "userProfile/signIn/" + email + "/" + password;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            if (user == null)
                throw new Exception("invalid email or password");
            return user;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * add a new user to the database (on first sign-up)
     *
     * @return the recipe if successfully added.
     * @throws Exception   if invalid input (such as if a profile already exists for
     *                     the given email) with exception message "invalid input"
     * @throws IOException if there is a error in connecting to the server
     */
    public UserProfile addUser(UserProfile user, String password) throws Exception, IOException {
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
            UserProfile result = new Gson().fromJson(reader, UserProfile.class);
            reader.close();
            if (result == null)
                throw new Exception("invalid input");
            else
                return result;
        } catch (IOException e) {
            throw e;
        } finally {
            closeResources(connection, reader, writer);
        }
    }

    /**
     * get User profile based on a given email
     *
     * @return if the user exists - the users profile
     * @throws Exception   if the user doesn't exist. exception message - "invalid
     *                     email"
     * @throws IOException if there is an error connecting to the server
     */
    public UserProfile getUser(String email) throws Exception, IOException {
        String url = BASE_URL + "userProfile/getUser/" + email;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            if (user == null)
                throw new Exception("invalid email");
            return user;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * sends email to user with password
     *
     * @return if successful - "email sent to " + userEmail.
     * @throws Exception   if userEmail does not exist. exception message - "invalid
     *                     email"
     * @throws IOException if error in connection to the server
     */
    public String forgotPassword(String userEmail) throws Exception, IOException {
        String url = BASE_URL + "userProfile/forgotPassword/" + userEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            TextMessage response = new Gson().fromJson(reader, TextMessage.class);
            if (response == null)
                throw new Exception("invalid email");
            else
                return response.getMessage();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * change the profile picture of an existing User
     *
     * @return if successfully added - the userProfile with the added pic.
     * @throws Exception   if no profile exists for the given email. exception
     *                     message - "invalid email"
     * @throws IOException if there is a error in connecting to the server
     */
    public UserProfile changeProfilePic(byte[] picture, String userEmail) throws Exception, IOException {
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
            writer.write(new Gson().toJson(picture));
            writer.flush();

            reader = new InputStreamReader(connection.getInputStream());
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            reader.close();
            if (user == null)
                throw new Exception("invalid email");
            else
                return user;
        } catch (IOException e) {
            throw e;
        } finally {
            closeResources(connection, reader, writer);
        }
    }

    /**
     * change a UserProfiles details. Cannot change email. If not changing a value,
     * set its parameter to null.
     *
     * @return if successful - the userProfile with the changes made.
     * @throws Exception   if userEmail doesn't exist or if invalid input. exception
     *                     message - "invalid email or input"
     * @throws IOException if error in connection to the server
     */
    public UserProfile updateUserProfile(String userEmail, String newPassword, String newFirstName, String newLastName,
                                         String newCountry, String[] newCuisines, skillLevel newSkillLevel) throws Exception, IOException {
        String newSkillString = null;
        if (newSkillLevel != null)
            newSkillString = newSkillLevel.name();
        String newCuisinesString = null;
        if (newCuisines != null && newCuisines.length != 0) {
            newCuisinesString = "";
            for (int i = 0; i < newCuisines.length; i++) {
                if (i == newCuisines.length - 1)
                    newCuisinesString += newCuisines[i];
                else
                    newCuisinesString += newCuisines[i] + "+";
            }
        }

        String url = BASE_URL + "userProfile/updateUserProfile/" + userEmail + "/" + newPassword + "/" + newFirstName
                + "/" + newLastName + "/" + newCountry + "/" + newCuisinesString + "/" + newSkillString;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            if (user == null)
                throw new Exception("invalid email or input");
            else
                return user;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * change a UserProfiles details. Cannot change email. If not changing a value, set its parameter to null.
     * @return if successful - the userProfile with the changes made.
     * @throws Exception if userEmail doesn't exist or if invalid input. exception message - "invalid email or input"
     * @throws IOException if error in connection to the server
     */
    public UserProfile updateUserProfileString(String userEmail, String newPassword, String newFirstName, String newLastName,
                                               String newCountry, String[] newCuisines, skillLevel newSkillLevel) throws Exception, IOException {
        String newSkillString = null;
        if (newSkillLevel != null)
            newSkillString = newSkillLevel.name();
        String newCuisinesString = null;
        if (newCuisines != null && newCuisines.length != 0) {
            newCuisinesString = "";
            for (int i = 0; i < newCuisines.length; i++) {
                if (i == newCuisines.length - 1)
                    newCuisinesString += newCuisines[i];
                else
                    newCuisinesString += newCuisines[i] + "+";
            }
        }

        String url = BASE_URL + "userProfile/updateUserProfile/" + userEmail + "/" + newPassword + "/" + newFirstName +
                "/" + newLastName + "/" + newCountry + "/" + newCuisinesString + "/" + newSkillString;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            if (user == null)
                throw new Exception("invalid email or input");
            else
                return user;
        }
        catch(IOException e) {
            throw e;
        }
    }

    /**
     * add a follower to a user
     *
     * @return if successful - the userProfile with the added follower.
     * @throws Exception   if userEmail or followerEmail doesn't exist. exception
     *                     message - "invalid email"
     * @throws IOException if error in connection to the server
     */
    public UserProfile addFollower(String userEmail, String followerEmail) throws Exception, IOException {
        String url = BASE_URL + "userProfile/addFollower/" + userEmail + "/" + followerEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            if (user == null)
                throw new Exception("invalid email");
            else
                return user;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * delete a follower from a user
     *
     * @return if successful - the userProfile with deleted follower
     * @throws Exception   if userEmail or followerEmail doesn't exist. exception
     *                     message - "invalid email"
     * @throws IOException if error in connection to the server
     */
    public UserProfile deleteFollower(String userEmail, String followerEmail) throws Exception, IOException {
        String url = BASE_URL + "userProfile/deleteFollower/" + userEmail + "/" + followerEmail;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            UserProfile user = new Gson().fromJson(reader, UserProfile.class);
            if (user == null)
                throw new Exception("invalid email");
            else
                return user;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * add a new recipe to the database don't need to provide recipeID -
     * automatically assigned by the DB (auto-increment)
     *
     * @return if successful - the recipe with the assigned recipeID.
     * @throws Exception   if unsuccessful (because of bad input). exception message
     *                     "invalid input"
     * @throws IOException if error in connecting to database
     */
    public Recipe addRecipe(Recipe recipe) throws Exception, IOException {
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
            Recipe responseRecipe = new Gson().fromJson(reader, Recipe.class);
            if (responseRecipe == null)
                throw new Exception("invalid input");
            else
                return responseRecipe;
        } catch (IOException e) {
            throw e;
        } finally {
            closeResources(connection, reader, writer);
        }
    }

    /**
     * get a recipe based on its ID
     *
     * @return if the recipeID exists - returns the recipe.
     * @throws Exception   if recipe doesn't exist. exception message - "invalid
     *                     recipeID"
     * @throws IOException if there is an error connecting to the server
     */
    public Recipe getRecipe(int recipeID) throws Exception, IOException {
        String url = BASE_URL + "recipe/getRecipe/" + recipeID;
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new URL(url).openStream());
            Recipe recipe = new Gson().fromJson(reader, Recipe.class);
            if (recipe == null)
                throw new Exception("invalid recipeID");
            return recipe;
        } catch (IOException e) {
            throw e;
        } finally {
            closeResources(null, reader, null);
        }
    }

    /**
     * get all the recipes of a specific user
     *
     * @return if the user exists - returns the users recipeList.
     * @throws Exception   if user doesn't exist. exception message - "invalid
     *                     email"
     * @throws IOException if there is an error connecting to the server
     */
    public RecipeList getUsersRecipes(String email) throws Exception, IOException {
        String url = BASE_URL + "recipe/getUsersRecipes/" + email;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            RecipeList recipes = new Gson().fromJson(reader, RecipeList.class);
            if (recipes == null)
                throw new Exception("invalid email");
            return recipes;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * get all the recipes of the users followed by a given user
     *
     * @return if the user exists - list of all the recipes of the users followed
     * users.
     * @throws Exception   if user doesn't exist. exception message - "invalid
     *                     email"
     * @throws IOException if there is an error connecting to the server
     */
    public RecipeList getFollowedRecipes(String email) throws Exception, IOException {
        String url = BASE_URL + "recipe/getFollowedRecipes/" + email;
        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            RecipeList recipes = new Gson().fromJson(reader, RecipeList.class);
            if (recipes == null)
                throw new Exception("invalid email");
            return recipes;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * get recipes that meet given search parameters. if a parameter can be anything
     * - set to null
     *
     * @return list of recipes that meet the given criteria (the list will be empty
     * if none do).
     * @throws Exception   if invalid input. exception message - "invalid input"
     * @throws IOException if there is an error connecting to the server
     */
    public RecipeList searchRecipes(skillLevel skill, String[] cuisines, recipeType type, String authorEmail, String freeText) throws Exception, IOException {

        String typeString = null;
        if (type != null)
            typeString = type.name();
        String skillString = null;
        if (skill != null)
            skillString = skill.name();
        String url = BASE_URL + "recipe/" + skillString + "/";
        String cuisinesString = null;
        if (cuisines != null && cuisines.length != 0) {
            cuisinesString = "";
            for (int i = 0; i < cuisines.length; i++) {
                if (i == cuisines.length - 1)
                    cuisinesString += cuisines[i];
                else
                    cuisinesString += cuisines[i] + "+";
            }
        }
        url += cuisinesString + "/" + typeString + "/" + authorEmail + "/" + freeText;

        try (InputStreamReader reader = new InputStreamReader(new URL(url).openStream())) {
            RecipeList results = new Gson().fromJson(reader, RecipeList.class);
            if (results == null)
                throw new Exception("invalid input");
            return results;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * add a comment to a recipe
     *
     * @return if comment successfully added - the recipe with the added comment
     * @throws Exception   if recipeID or comment author doesn't exist. exception
     *                     message - "invalid recipeID or comment author"
     * @throws IOException if error connecting to the server
     */
    public Recipe addComment(int recipeID, Comment comment) throws Exception, IOException {
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
            Recipe recipe = new Gson().fromJson(reader, Recipe.class);
            if (recipe == null)
                throw new Exception("invalid recipeID or comment author");
            else
                return recipe;
        } catch (IOException e) {
            throw e;
        } finally {
            closeResources(connection, reader, writer);
        }
    }

    /**
     * add a picture to a recipe
     *
     * @return if picture successfully added - the recipe with the added picture.
     * @throws Exception   if recipeID doesn't exist. exception message - "invalid
     *                     recipeID"
     * @throws IOException if error connecting to the server
     */
    public Recipe addPicture(int recipeID, byte[] picture) throws Exception, IOException {
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
            Recipe recipe = new Gson().fromJson(reader, Recipe.class);
            if (recipe == null)
                throw new Exception("invalid recipeID");
            else
                return recipe;
        } catch (IOException e) {
            throw e;
        } finally {
            closeResources(connection, reader, writer);
        }
    }

    private void closeResources(HttpURLConnection connection, InputStreamReader reader, OutputStreamWriter writer) {
        try {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        } catch (Exception e) {

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
