package com.example.david.ertosql.er.shapes;

import static com.example.david.ertosql.er.shapes.ERShape.*;

public class ERShapeUtilities {
    public static double calculateDistanceBetweenTwoPoints(ERPoint p1 , ERPoint p2){
        return Math.sqrt(Math.pow(p1.x-p2.x,2)+Math.pow(p1.y-p2.y,2));
    }
}
