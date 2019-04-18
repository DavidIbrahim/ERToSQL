package com.example.david.ertosql.ERObjects;

import java.util.HashMap;

public class ERTotalPartialRelationship extends ERRelationship {
    HashMap<EREntity, Participation> entities;

    public ERTotalPartialRelationship(String title) {
        this.title = title;
        entities = new HashMap<>();
    }

    public void addEntity(EREntity entity, ERTotalPartialRelationship.Participation participation) {
        entities.put(entity, participation);
    }

    @Override
    public String toString() {
        return "relationship:" + title + entities.toString();
    }

    enum Participation {
        totalOne, partialOne,
        totalMany, partialMany
    }
}
