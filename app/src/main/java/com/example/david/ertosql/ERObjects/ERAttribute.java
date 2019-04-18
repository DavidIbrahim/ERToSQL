package com.example.david.ertosql.ERObjects;

public class ERAttribute{
    boolean isMultivalued = false;
    String title;

    public ERAttribute(){
    }

    public ERAttribute(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "attribute:" + title + "\tis Multivalued:" + isMultivalued;
    }
}
