package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERDiagram {
    String title;
    ArrayList<EREntity> entities;
    ArrayList<ERRelationship> relations;

    public ERDiagram(String title) {
        this.title = title;
    }

    public ERDiagram(String title, ArrayList<ERRelationship> relations) {
        this.title = title;
        this.relations = relations;
    }

    public ERDiagram(String title, ERRelationship relationship) {
        this.title = title;
        this.relations = new ArrayList<>();
        this.relations.add(relationship);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<ERRelationship> getRelations() {

        return relations;
    }
}
