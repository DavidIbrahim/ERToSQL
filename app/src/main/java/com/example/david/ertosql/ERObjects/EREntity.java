package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class EREntity {
    String title;
    Type type;
    ArrayList<ERAttribute> uniqueAttributes;
    ArrayList<ERAttribute> entityAttributes;

    public EREntity(String title, ArrayList<ERAttribute> uniqueAttributes, ArrayList<ERAttribute> entityAttributes) {
        this.title = title;
        this.uniqueAttributes = uniqueAttributes;
        this.entityAttributes = entityAttributes;
        this.type = Type.REGULAR;
    }

    public EREntity(String title, ArrayList<ERAttribute> uniqueAttributes,
                    ArrayList<ERAttribute> entityAttributes, Type type) {
        this.title = title;
        this.uniqueAttributes = uniqueAttributes;
        this.entityAttributes = entityAttributes;
        this.type = type;
    }

    public ArrayList<ERAttribute> getKey(){
        //todo: modify keys later
        if (uniqueAttributes.isEmpty()){
            ArrayList<ERAttribute> keys = new ArrayList<>();
            keys.add(new ERAttribute(title+"ID"));
            return keys;
        }
        return uniqueAttributes;
    }

    @Override
    public String toString() {
        return "entity:" + title + " type:" + type.toString() +
                " uniqueAttributes:" + uniqueAttributes.toString() + " entityAttributes:" + entityAttributes.toString();
    }

    enum Type {
        REGULAR, WEAK
    }
}
