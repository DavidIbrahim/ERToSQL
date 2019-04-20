package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class EREntity {
    String title;
    Type type;
    ArrayList<ERAttribute> uniqueAttributes;
    ArrayList<ERAttribute> entityAttributes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<ERAttribute> getUniqueAttributes() {
        return uniqueAttributes;
    }

    public void setUniqueAttributes(ArrayList<ERAttribute> uniqueAttributes) {
        this.uniqueAttributes = uniqueAttributes;
    }

    public ArrayList<ERAttribute> getEntityAttributes() {
        return entityAttributes;
    }

    public void setEntityAttributes(ArrayList<ERAttribute> entityAttributes) {
        this.entityAttributes = entityAttributes;
    }

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
