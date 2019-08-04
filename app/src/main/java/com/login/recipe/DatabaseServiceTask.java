package com.login.recipe;

import android.os.AsyncTask;
import java.io.IOException;

public class DatabaseServiceTask extends AsyncTask <Object, Integer , Object> {

    String taskName;
    MyApplication app;

    public DatabaseServiceTask(String taskName, MyApplication app) {
        this.app = app;
        this.taskName = taskName;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Object result = null;
        final DatabaseService database = app.getDatabase();

        if (taskName.equals("addUser"))
            // objects[0] is UserProfile to add. objects[1] is new password
            try {
                result = database.addUser((UserProfile) objects[0], (String) objects[1]);
            }
        catch (IOException e) {
                return "error";
        }

        else if (taskName.equals("validateSignIn"))
            // objects[0] is email. objects[1] is password.
            try {
                result = database.validateSignIn((String) objects[0], (String) objects[1]);
            }
            catch (IOException e) {
                return "error";
            }

        else if (taskName.equals("forgotPassword"))
            // objects[0] is email
            try {
                result = database.forgotPassword((String) objects[0]);
            }
            catch (IOException e) {
                return "error";
            }

        else if (taskName.equals("addRecipe"))
            // objects[0] is Recipe to add.
            try {
                result = database.addRecipe((Recipe) objects[0]);
            }
            catch (IOException e) {
                return "error";
            }

        return result;
    }

}
