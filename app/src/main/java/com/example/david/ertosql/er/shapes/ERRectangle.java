package com.example.david.ertosql.er.shapes;

public class ERRectangle extends ERShape.ERShapeWZCenter implements ERShape {

    private ERPoint center;

    public ERRectangle(ERPoint center) {
        this.center = center;
    }




    /**
     *
     * @param shape
     * @return the distance from the shape to the center of the rectangle
     */ 

    @Override
    public float calculateDistance(ERShape shape) {

       // if the shape is line then calculate the distance from the center of the shape to the start and the end of the line
        // and return the smaller
        if(shape.getClass() == ERLine.class){

        }
        else {

        }
        return 0;
    }
}
