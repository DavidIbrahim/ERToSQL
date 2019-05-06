package com.example.david.ertosql.er.shapes;

public interface ERShape {
    public double calculateDistance(ERShape shape);



    public class ERPoint {
        public double x;
        public double y;
        private static int x_tolerance;
        private static int y_tolerance;

        public ERPoint(double x, double y) {
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
        // todo kero
        public void set_x (double xx)
        {
            x=xx;
        }
        public void set_y (double yy)
        {
            y=yy;
        }
        public double get_x ()
        {
            return x;
        }
        public double get_y ()
        {
            return y;
        }
        public static void set_tolerance(double width, double height)
        {
            x_tolerance=(int)width/35;
            y_tolerance=(int)height/35;
        }

        public static boolean isclose (ERPoint a, ERPoint b)
        {
            if(((a.get_x() - a.x_tolerance) <= b.get_x()&&b.get_x() <=(a.get_x()+a.x_tolerance))&&((a.get_y() - a.y_tolerance) <= b.get_y()&&b.get_y() <=(a.get_y()+a.y_tolerance)))
            {
                return true;
            }
            else return false;
        }

        public static ERPoint most_x(ERPoint p1,ERPoint p2,ERPoint p3,ERPoint p4)
        {
            if (p1.x>=p2.x&&p1.x>=p3.x&&p1.x>=p4.x)
                return p1;
            else if (p2.x>=p1.x&&p2.x>=p3.x&&p2.x>p4.x)
                return p2;
            else if (p3.x>=p1.x&&p3.x>=p2.x&&p3.x>=p4.x)
                return p3;
            else return p4;
        }
        public static ERPoint least_x(ERPoint p1,ERPoint p2,ERPoint p3,ERPoint p4)
        {
            if (p1.x<=p2.x&&p1.x<=p3.x&&p1.x<=p4.x)
                return p1;
            else if (p2.x<=p1.x&&p2.x<=p3.x&&p2.x<=p4.x)
                return p2;
            else if (p3.x<=p1.x&&p3.x<=p2.x&&p3.x<=p4.x)
                return p3;
            else return p4;
        }
        public static ERPoint least_y(ERPoint p1,ERPoint p2,ERPoint p3,ERPoint p4)
        {
            if (p1.y<=p2.y&&p1.y<=p3.y&&p1.y<=p4.y)
                return p1;
            else if (p2.y<=p1.y&&p2.y<=p3.y&&p2.y<=p4.y)
                return p2;
            else if (p3.y<=p1.y&&p3.y<=p2.y&&p3.x<=p4.y)
                return p3;
            else return p4;
        }
        public static ERPoint most_y(ERPoint p1,ERPoint p2,ERPoint p3,ERPoint p4)
        {
            if (p1.y>=p2.y&&p1.y>=p3.y&&p1.x>=p4.y)
                return p1;
            else if (p2.y>=p1.y&&p2.y>=p3.y&&p2.y>p4.y)
                return p2;
            else if (p3.y>=p1.y&&p3.y>=p2.y&&p3.y>=p4.y)
                return p3;
            else return p4;
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
