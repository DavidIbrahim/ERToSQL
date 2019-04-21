package com.example.david.ertosql.ERObjects;

public class EROneToOneRelationship extends ERBinaryRelationship {
    Participation participation;

    public Participation getParticipation() {
        return participation;
    }

    public EROneToOneRelationship(String title, EREntity entity1, EREntity entity2){
        super(entity1,entity2);
        this.title = title;
        this.participation = Participation.PARTIAL_PARTIAL;
    }

    public EROneToOneRelationship(String title, EREntity entity1, EREntity entity2,
                                  EROneToOneRelationship.Participation participation) {
        super(entity1,entity2);
        this.title = title;
        this.participation = participation;
    }

    @Override
    public String toString() {
        return "relationship:" + title +
                " participation:" + participation.toString() +
                " firstEntity:" + entity1.toString() +
                " secondEntity:" + entity2.toString();
    }

    public enum Participation {
        TOTAL_TOTAL, PARTIAL_TOTAL, PARTIAL_PARTIAL
    }
}
