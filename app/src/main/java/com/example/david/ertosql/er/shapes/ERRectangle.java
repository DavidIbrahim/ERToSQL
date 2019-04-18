package com.example.david.ertosql.er.shapes;

public class ERRectangle extends ERShape.ERShapeWZCenter   {


    public ERRectangle(ERPoint center,String text) {
        super(center,text);
    }

    @Override
    public String toString() {
        return "ERRectangle{" +
                "center=" + center +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object rec2) {
        if(this == rec2) return true;//if both of them points the same address in memory

        if(!(rec2 instanceof ERRectangle)) return false; // if "that" is not a People or a childclass

        ERRectangle erRectangle = (ERRectangle) rec2;
        return this.text.equals(erRectangle.text) && this.center.equals(erRectangle.center);
    }
}
