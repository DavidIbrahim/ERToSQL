package com.example.david.ertosql.ERObjects;

public class ERBinaryRelationship extends ERRelationship {
    Participation participation;
    EREntity entity1;
    EREntity entity2;

    public ERBinaryRelationship() {
    }

    public EREntity getEntity1() {
        return entity1;
    }

    public void setEntity1(EREntity entity1) {
        this.entity1 = entity1;
    }

    public EREntity getEntity2() {
        return entity2;
    }

    public void setEntity2(EREntity entity2) {
        this.entity2 = entity2;
    }

    public Participation getParticipation() {
        return participation;
    }

    public ERBinaryRelationship(String title, EREntity entity1, EREntity entity2){
        this.entity1 = entity1;
        this.entity2 = entity2;
        this.title = title;
        this.participation = Participation.PARTIAL_PARTIAL;
    }

    public ERBinaryRelationship(String title, EREntity entity1, EREntity entity2,
                                ERBinaryRelationship.Participation participation) {
        this.entity1 = entity1;
        this.entity2 = entity2;
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
        TOTAL_TOTAL, PARTIAL_TOTAL, PARTIAL_PARTIAL, ONE_MANY, MANY_MANY
    }
}
