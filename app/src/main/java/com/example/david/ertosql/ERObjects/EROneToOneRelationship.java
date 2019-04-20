package com.example.david.ertosql.ERObjects;

import java.util.HashMap;

public class EROneToOneRelationship extends ERBinaryRelationship {
    Participation participation;

    public Participation getParticipation() {
        return participation;
    }

    public EROneToOneRelationship(String title, EREntity entity1, EREntity entity2,
                                  EROneToOneRelationship.Participation participation) {
        this.title = title;
        this.entity1 = entity1;
        this.entity2 = entity2;
        this.participation = participation;
    }

//    public void addEntity(EREntity entity, EROneToOneRelationship.Participation participation) {
//        entities.put(entity, participation);
//    }

    @Override
    public String toString() {
        return "relationship:" + title +
                " participation:" + participation.toString() +
                " firstEntity:" + entity1.toString() +
                " secondEntity:" + entity2.toString();
    }

    public enum Participation {
        TOTAL_TOTAL, TOTAL_PARTIAL, PARTIAL_PARTIAL
    }
}
