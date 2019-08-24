package com.login.recipe;

public class Comment {

    private String author;
    private String comment;
    private String authorName;

    public Comment(String author, String comment, String authorName) {
        this.author = author;
        this.comment = comment;
        this.authorName = authorName;

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


    public String getauthorName() {
        return authorName;
    }

    public void setAauthorName(String authorName) {
        this.authorName = authorName;
    }
}

