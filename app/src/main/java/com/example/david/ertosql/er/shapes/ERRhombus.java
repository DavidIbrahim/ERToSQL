package com.example.david.ertosql.er.shapes;

public class ERRhombus extends ERShape.ERShapeWZCenter {
    public ERRhombus(ERPoint center, String text) {
        super(center, text);
    }

    @Override
    public String toString() {
        return "ERRhombus{" +
                "center=" + center +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object rec2) {
        if(this == rec2) return true;//if both of them points the same address in memory

        if(!(rec2 instanceof ERRhombus)) return false; // if "that" is not a People or a childclass

        ERRhombus erRhombus = (ERRhombus) rec2;
        return this.text.equals(erRhombus.text) && this.center.equals(erRhombus.center);
    }
}
