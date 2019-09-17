package com.login.recipe;

import android.os.AsyncTask;

public class DatabaseServiceTask extends AsyncTask<Object, Integer, Object> {

    private String taskName;
    private MyApplication app;

    public DatabaseServiceTask(String taskName, MyApplication app) {
        this.app = app;
        this.taskName = taskName;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Object result = null;
        final DatabaseService database = app.getDatabase();

        try {
            switch (taskName) {
                case "addUser":
                    // objects[0] is UserProfile to add. objects[1] is new password
                    result = database.addUser((UserProfile) objects[0], (String) objects[1]);
                    break;
                case "validateSignIn":
                    // objects[0] is email. objects[1] is password.
                    result = database.validateSignIn((String) objects[0], (String) objects[1]);
                    break;
                case "getUser":
                    // objects[0] is email
                    result = database.getUser((String) objects[0]);
                    break;
                case "forgotPassword":
                    // objects[0] is email
                    result = database.forgotPassword((String) objects[0]);
                    break;
                case "changeProfilePic":
                    // objects[0] is email. objects[1] is new pic (a byte[]).
                    result = database.changeProfilePic((byte[]) objects[0], (String) objects[1]);
                    break;
                case "updateUserProfile":
                    // objects[0] is email. objects[1] is new password. objects[2] is newFirstName.
                    // objects[3] is newLastName.
                    // objects[4] is newCountry. objects[5] is newCuisines (array). objects[6] is
                    // newSkillLevel (of type skilllevel).
                    result = database.updateUserProfile((String) objects[0], (String) objects[1], (String) objects[2],
                            (String) objects[3], (String) objects[4], (String[]) objects[5],
                            (UserProfile.skillLevel) objects[6]);
                    break;
                case "addFollower":
                    // objects[0] is followedEmail. objects[1] is follower email.
                    result = database.addFollower((String) objects[0], (String) objects[1]);
                    break;
                case "deleteFollower":
                    // objects[0] is followedEmail. objects[1] is follower email.
                    result = database.deleteFollower((String) objects[0], (String) objects[1]);
                    break;
                case "addRecipe":
                    // objects[0] is Recipe to add.
                    result = database.addRecipe((Recipe) objects[0]);
                    break;
                case "getRecipe":
                    // objects[0] is ID of Recipe to get.
                    result = database.getRecipe((int) objects[0]);
                    break;
                case "getUsersRecipes":
                    // objects[0] is User email.
                    result = database.getUsersRecipes((String) objects[0]);
                    break;
                case "getFollowedRecipes":
                    // objects[0] is User email.
                    result = database.getFollowedRecipes((String) objects[0]);
                    break;
                case "searchRecipes":
                    // objects[0] is skill, objects[1] is cuisine array, objects[2] is type,
                    // objects[3] is author email, objects[4] is freeText
                    result = database.searchRecipes((UserProfile.skillLevel) objects[0], (String[]) objects[1],
                            (Recipe.recipeType) objects[2], (String) objects[3], (String) objects[4]);
                    break;
                case "addComment":
                    // objects[0] is recipe ID. objects[1] is Comment
                    result = database.addComment((int) objects[0], (Comment) objects[1]);
                    break;
                case "addPicture":
                    // objects[0] is recipe ID. objects[1] is picture
                    result = database.addPicture((int) objects[0], (byte[]) objects[1]);
                    break;
            }
        } catch (Exception e) {
            return e;
        }

        return result;
    }

}
