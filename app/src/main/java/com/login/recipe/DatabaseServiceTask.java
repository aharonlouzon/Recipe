package com.login.recipe;

import android.os.AsyncTask;
import java.io.IOException;

public class DatabaseServiceTask extends AsyncTask <Object, Integer , Object> {

    private String taskName;
    MyApplication app;

    public DatabaseServiceTask(String taskName, MyApplication app) {
        this.app = app;
        this.taskName = taskName;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Object result = null;
        final DatabaseService database = app.getDatabase();

        switch (taskName) {
            case "addUser":
                // objects[0] is UserProfile to add. objects[1] is new password
                try {
                    result = database.addUser((UserProfile) objects[0], (String) objects[1]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "validateSignIn":
                // objects[0] is email. objects[1] is password.
                try {
                    result = database.validateSignIn((String) objects[0], (String) objects[1]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "getUser":
                // objects[0] is email
                try {
                    result = database.getUser((String) objects[0]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "forgotPassword":
                // objects[0] is email
                try {
                    result = database.forgotPassword((String) objects[0]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "changeProfilePic":
                // objects[0] is email. objects[1] is new pic (a byte[]).
                try {
                    result = database.changeProfilePic((byte[]) objects[1], (String) objects[0]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "addFollower":
                // objects[0] is followedEmail. objects[1] is follower email.
                try {
                    result = database.addFollower((String) objects[0], (String) objects[1]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "deleteFollower":
                // objects[0] is followedEmail. objects[1] is follower email.
                try {
                    result = database.deleteFollower((String) objects[0], (String) objects[1]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "addRecipe":
                // objects[0] is Recipe to add.
                try {
                    result = database.addRecipe((Recipe) objects[0]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "getRecipe":
                // objects[0] is ID of Recipe to get.
                try {
                    result = database.getRecipe((int) objects[0]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "getUsersRecipes":
                // objects[0] is User email.
                try {
                    result = database.getUsersRecipes((String) objects[0]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "searchRecipes":
                // objects[0] is skill, objects[1] is cuisine, objects[2] is type, objects[3] is author email, objects[4] is freeText
                try {
                    result = database.searchRecipes((UserProfile.skillLevel) objects[0], (String) objects[1],
                                                    (Recipe.recipeType) objects[2], (String) objects[3], (String) objects[4]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "addComment":
                // objects[0] is recipe ID. objects[1] is Comment
                try {
                    result = database.addComment((int) objects[0], (Comment) objects[1]);
                } catch (IOException e) {
                    return "error";
                }
                break;
            case "addPicture":
                // objects[0] is recipe ID. objects[1] is picture
                try {
                    result = database.addPicture((int) objects[0], (byte[]) objects[1]);
                } catch (IOException e) {
                    return "error";
                }
                break;
        }

        return result;
    }

}
