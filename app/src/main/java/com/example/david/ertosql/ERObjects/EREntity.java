package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class EREntity {
    String title;
    Type type = Type.strong;
    ArrayList<ERAttribute> keyAttributes;
    ArrayList<ERAttribute> entityAttributes;

    public EREntity(String title, ArrayList<ERAttribute> keyAttributes, ArrayList<ERAttribute> entityAttributes) {
        this.title = title;
        this.keyAttributes = keyAttributes;
        this.entityAttributes = entityAttributes;
    }

    public EREntity(String title, ArrayList<ERAttribute> keyAttributes,
                    ArrayList<ERAttribute> entityAttributes, Type type) {
        this.title = title;
        this.keyAttributes = keyAttributes;
        this.entityAttributes = entityAttributes;
        this.type = type;
    }

    @Override
    public String toString() {
        return "entity:" + title + " type:" + type.toString() +
                " keys:" + keyAttributes.toString() + " attributes:" + entityAttributes.toString();
    }

    enum Type {
        strong, weak
    }
}
