package com.login.recipe;

import java.util.ArrayList;
import java.util.List;

public class CommentList extends ArrayList<Comment> {
    private static final long serialVersionUID = 1L;
    public CommentList() {
        super();
    }
    public CommentList(List<Comment> c) {
        super(c);
    }

    public List<Comment> getComments() {
        return this;
    }
    public void setComments(List<Comment> comments) {
        this.addAll(comments);
    }
}