package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERCompositeAttribute extends ERAttribute{
    ArrayList<ERAttribute> childAttributes;

    public ERCompositeAttribute(String title) {
        this.title = title;
        this.childAttributes = new ArrayList<>();
    }

    public ERCompositeAttribute(String title, ArrayList<ERAttribute> childAttributes) {
        this.title = title;
        this.childAttributes = new ArrayList<>(childAttributes);
    }

    public void addAttribute(ERAttribute attribute){
        childAttributes.add(attribute);
    }

    @Override
    public String toString() {
        return "attribute:" + title + childAttributes.toString();
    }
}
