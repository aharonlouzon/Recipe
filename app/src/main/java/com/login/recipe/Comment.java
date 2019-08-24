package com.login.recipe;

public class Comment {

    private String author;
    private String comment;

    public Comment(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}

