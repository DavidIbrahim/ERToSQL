package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERRelationship {
    String title;
    ArrayList<ERAttribute> attributes;

    public ArrayList<ERAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<ERAttribute> attributes) {
        this.attributes = attributes;
    }

    boolean isIdentifying = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIdentifying() {
        return isIdentifying;
    }

    public void setIdentifying(boolean identifying) {
        isIdentifying = identifying;
    }
}
