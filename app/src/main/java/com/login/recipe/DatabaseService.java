package com.login.recipe;

import com.login.recipe.Recipe.recipeType;
import com.login.recipe.UserProfile.skillLevel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DatabaseService {

    private static final String BASE_URI = "http://localhost:8080/RecipeAppDatabaseService/webresources";
    private Client client;

    public DatabaseService() {
        client = ClientBuilder.newClient();
    }

    /**
     * validate if correct password (on sign-in)
     * @return if correct - the user's profile. if incorrect username or password - returns null
     */
    public UserProfile validateSignIn(String email, String password) {
        WebTarget webTarget = client.target(BASE_URI).path("userProfile/signIn/" + email + "/" + password);
        return webTarget.request().get(UserProfile.class);
    }

    /**
     * add a new user to the database (on first sign-up)
     */
    public Response addUser(UserProfile user, String password) {
        WebTarget webTarget = client.target(BASE_URI).path("userProfile/add/" + password);
        Response response = webTarget.request().post(Entity.entity(user,MediaType.APPLICATION_XML));
        return response;
    }

    /**
     * delete account
     */
    public Response deleteUser(String email) {
        WebTarget webTarget = client.target(BASE_URI).path("userProfile/" + email);
        Response response = webTarget.request().delete();
        return response;
    }

    /**
     * get User profile based on a given email
     * @return user profile
     */
    public UserProfile getUser(String email) {
        WebTarget webTarget = client.target(BASE_URI).path("userProfile/" + email);
        return webTarget.request().get(UserProfile.class);
    }

    /**
     * add a follower to a user
     */
    public Response addFollower(String userEmail, String followerEmail) {
        WebTarget webTarget = client.target(BASE_URI).path("userProfile/" + userEmail + "/" + followerEmail);
        Response response = webTarget.request().get();
        return response;
    }

    /**
     * add a follower to a user
     */
    public Response deleteFollower(String userEmail, String followerEmail) {
        WebTarget webTarget = client.target(BASE_URI).path("userProfile/" + userEmail + "/" + followerEmail);
        Response response = webTarget.request().delete();
        return response;
    }
    /**
     * add a new recipe to the database
     * don't need to provide recipeID - automatically assigned by the DB (auto-increment)
     */
    public Response addRecipe(Recipe recipe) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe");
        Response response = webTarget.request().post(Entity.entity(recipe, MediaType.APPLICATION_XML));
        if (response.getStatusInfo().getReasonPhrase().equalsIgnoreCase("Created")) {
            webTarget = client.target(BASE_URI).path("recipe/addPicture");
            for (int i = 0; i < recipe.getImages().size(); i++) {
                response = webTarget.request().post(Entity.entity(recipe.getImages().get(i), MediaType.APPLICATION_XML));
            }
        }
        return response;
    }

    /**
     * get a recipe based on its ID
     */
    public Recipe getRecipe(int recipeID) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe/" + recipeID);
        Recipe recipe = webTarget.request().get(Recipe.class);

        webTarget = client.target(BASE_URI).path("recipe/getComments/" + recipeID);
        CommentList comments = webTarget.request().get(CommentList.class);
        recipe.setComments(comments);

        webTarget = client.target(BASE_URI).path("recipe/getPictures/" + recipeID);
        PictureList pictures = webTarget.request().get(PictureList.class);
        recipe.setImages(pictures);

        return recipe;
    }

    /**
     * get all the recipes of a specific user
     */
    public RecipeList getUsersRecipes(String email) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe/getUsersRecipes/" + email);
        RecipeList results = webTarget.request().get(RecipeList.class);

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

    /**
     * get recipes that meet given search parameters.
     * if a parameter can be anything - set to null
     */
    public RecipeList searchRecipes(skillLevel skill, String cuisine, recipeType type, String authorEmail) {
        String typeString = type.name();
        String skillString = skill.name();
        WebTarget webTarget = client.target(BASE_URI).path("recipe/" + skillString + "/" + cuisine + "/" + typeString + "/" + authorEmail);
        RecipeList results = webTarget.request().get(RecipeList.class);

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

    public Response addComment(int recipeID, Comment comment) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe/addComment/" + recipeID);
        Response response = webTarget.request().post(Entity.entity(comment, MediaType.APPLICATION_XML));
        return response;
    }

    public Response addPicture(int recipeID, byte[] picture) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe/addPicture/" + recipeID);
        Response response = webTarget.request().post(Entity.entity(picture, MediaType.APPLICATION_XML));
        return response;
    }

    private CommentList getComments(int recipeID) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe/getComments/" + recipeID);
        return webTarget.request().get(CommentList.class);
    }

    private PictureList getPictures(int recipeID) {
        WebTarget webTarget = client.target(BASE_URI).path("recipe/getPictures/" + recipeID);
        return webTarget.request().get(PictureList.class);
    }

    public void close() {
        client.close();
    }
}