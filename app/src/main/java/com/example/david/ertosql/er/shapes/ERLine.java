package com.example.david.ertosql.er.shapes;

public class ERLine implements ERShape {
    private ERPoint p1,p2;

    public ERLine(ERPoint p1, ERPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public double calculateDistance(ERShape shape) {
        if(shape instanceof ERShapeWZCenter){
            ERShapeWZCenter shapeWZCenter = (ERShapeWZCenter) shape;
            double d1 = ERShapeUtilities.calculateDistanceBetweenTwoPoints(p1,shapeWZCenter.getCenter());
            double d2 = ERShapeUtilities.calculateDistanceBetweenTwoPoints(p2,shapeWZCenter.getCenter());
            return d1<d2 ? d1 : d2;
        }

        return 0;
    }

    @Override
    public String toString() {
        return "ERLine{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
