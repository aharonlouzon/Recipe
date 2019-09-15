package com.login.recipe;

public class Comment {

    private String author;
    private String comment;
    private String authorName;

    Comment(String author, String comment, String authorName) {
        this.author = author;
        this.comment = comment;
        this.authorName = authorName;

    }

    @SuppressWarnings("unused")
    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    String getAuthorName() {
        return authorName;
    }

    @SuppressWarnings("unused")
    public void setAauthorName(String authorName) {
        this.authorName = authorName;
    }
}
