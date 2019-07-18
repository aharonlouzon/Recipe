package com.login.recipe;

import java.util.ArrayList;
import java.util.List;

public class PictureList extends ArrayList<byte[]> {
    private static final long serialVersionUID = 1L;
    public PictureList() {
        super();
    }
    public PictureList(List<byte[]> c) {
        super(c);
    }

    public List<byte[]> getpicture() {
        return this;
    }
    public void setComments(List<byte[]> pictures) {
        this.addAll(pictures);
    }
}
