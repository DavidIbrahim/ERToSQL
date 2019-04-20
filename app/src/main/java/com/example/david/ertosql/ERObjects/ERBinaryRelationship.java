package com.example.david.ertosql.ERObjects;

public class ERBinaryRelationship extends ERRelationship {
    EREntity entity1;
    EREntity entity2;

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
}
