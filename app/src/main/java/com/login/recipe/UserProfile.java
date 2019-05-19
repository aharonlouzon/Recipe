package com.login.recipe;

import java.io.Serializable;
import java.util.ArrayList;

public class UserProfile implements Serializable {
    enum skillLevel{BEGINNER, INTEMEDIATE, PRO}
    private String firstName;
    private String lastName;
    private skillLevel cookingSkills;
    private ArrayList<String> cuisine = new ArrayList<String>();
    private String country;

    public UserProfile(String firstName, String lastName, skillLevel cookingSkills, String country){
        this.firstName = firstName;
        this.lastName = lastName;
        this.cookingSkills = cookingSkills;
        this.country = country;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCookingSkills(skillLevel cookingSkills) {
        this.cookingSkills = cookingSkills;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCuisine(String[] cuisine) {
        for(int i=0; i<cuisine.length; i++)
            this.cuisine.add(cuisine[i]);
    }

    public void addCuisine(String cuisine) {
        this.cuisine.add(cuisine);
    }

    public void removeCuisine(String cuisine) {
        for (String element : this.cuisine) {
            if (element.equals(cuisine)) {
                this.cuisine.remove(cuisine);
                return;
            }
        }
    }
}
