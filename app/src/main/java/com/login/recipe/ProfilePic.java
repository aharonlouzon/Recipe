package com.login.recipe;

public class ProfilePic {

    private byte[] picture;

    public ProfilePic(byte[] picture) {
        this.picture = picture;
    }

    @SuppressWarnings("unused")
    public byte[] getPicture() {
        return picture;
    }

    @SuppressWarnings("unused")
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


}

