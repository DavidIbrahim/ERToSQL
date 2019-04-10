package com.example.david.ertosql.er.shapes;

public class ERLine implements ERShape {
    private ERPoint p1,p2;

    @Override
    public float calculateDistance(ERShape shape) {
        return 0;
    }
}
