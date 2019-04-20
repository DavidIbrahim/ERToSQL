package com.example.david.ertosql.ERObjects;

import java.util.HashMap;

public class ERMinMaxRelationship extends ERBinaryRelationship {
    boolean isIdentifying = false;

    public class ERConstraint{
        int min, max;
    }
}
