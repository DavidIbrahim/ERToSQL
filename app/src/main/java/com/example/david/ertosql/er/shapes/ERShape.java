package com.example.david.ertosql.er.shapes;

public interface ERShape {
    public float calculateDistance(ERShape shape);



    public class ERPoint {
        public int x;
        public int y;
    }
    public class ERShapeWZCenter{
        private ERPoint center;
    }
}
