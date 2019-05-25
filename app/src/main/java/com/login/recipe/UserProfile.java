package com.login.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserProfile implements Serializable {
    enum skillLevel{BEGINNER, INTEMEDIATE, PRO}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public skillLevel getCookingSkills() {
        return cookingSkills;
    }

    public ArrayList<String> getCuisine() {
        return cuisine;
    }

    public String getCountry() {
        return country;
    }

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

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//        out.writeObject(this.firstName);
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException {
//        in.defaultReadObject();
//        this.firstName = (String)in.readObject();
//    }
}
