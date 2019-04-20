package com.example.david.ertosql.er.shapes;

public interface ERShape {
    public double calculateDistance(ERShape shape);



    public class ERPoint {
        public int x;
        public int y;

        public ERPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "("+x+','+y+")";
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj) return true;//if both of them points the same address in memory

            if(!(obj instanceof ERPoint)) return false; // if "that" is not a People or a childclass

            return this.x == ((ERPoint) obj).x && this.y == ((ERPoint) obj).y;
        }
    }
    public class ERShapeWZCenter implements ERShape{

        protected ERPoint center;
        protected String text;

        ERShapeWZCenter(ERPoint center, String text) {
            this.center = center;
            this.text = text;
        }

        public ERPoint getCenter() {
            return center;
        }

        public String getText() {
            return text;
        }


        /**
         *
         * @param shape can be divided into types shape wz center or not ...
         * @return the distance from the center of this shape to another shape
         * if the shape doesn't have center then we will let it calculate the distance ...
         */
        @Override
        public double calculateDistance(ERShape shape) {
            // if the shape is line then let the line shape calculates the distance from this to itself
            if(shape instanceof ERLine){
                return shape.calculateDistance(this);
            }
            else if(shape instanceof ERShapeWZCenter){
                ERShapeWZCenter shapeWZCenter = (ERShapeWZCenter) shape;
                return ERShapeUtilities.calculateDistanceBetweenTwoPoints(center,shapeWZCenter.getCenter());
            }

            return 0;
        }
    }
}
