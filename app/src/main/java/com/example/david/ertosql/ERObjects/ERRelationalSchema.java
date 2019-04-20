package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERRelationalSchema {
    String title;
    ArrayList<ERAttribute> candidateKeys;
    ArrayList<ERAttribute> attributes;
    ArrayList<ERAttribute> foreignKeys;

    @Override
    public String toString() {
        return "RSTitle:" + title
                + " CandidateKeys:" + candidateKeys.toString()
                + " Attributes:" + attributes.toString();
                //+ " ForeignKeys:" + foreignKeys.toString();
    }

    public ERRelationalSchema(String title, ArrayList<ERAttribute> candidateKeys) {
        this.title = title;
        this.candidateKeys = candidateKeys;
    }

    public ERRelationalSchema(String title, ArrayList<ERAttribute> candidateKeys, ArrayList<ERAttribute> attributes) {
        this.title = title;
        this.candidateKeys = candidateKeys;
        this.attributes = attributes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ERAttribute> getCandidateKeys() {
        return candidateKeys;
    }

    public void setCandidateKeys(ArrayList<ERAttribute> candidateKeys) {
        this.candidateKeys = candidateKeys;
    }

    public ArrayList<ERAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<ERAttribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<ERAttribute> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ArrayList<ERAttribute> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
}
