package com.example.david.ertosql.ERObjects;

import java.util.HashMap;

public class ERMinMaxRelationship extends ERRelationship {
    boolean isIdentifying = false;
    HashMap<EREntity,ERConstraint> entities;

    public class ERConstraint{
        int min, max;
    }
}
