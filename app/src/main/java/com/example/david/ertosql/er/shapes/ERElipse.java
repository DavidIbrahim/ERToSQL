package com.example.david.ertosql.er.shapes;

public class ERElipse extends ERShape.ERShapeWZCenter {

    private boolean isUnderLined;

    public ERElipse(ERPoint center, String text, boolean isUnderLined) {
        super(center, text);
        this.isUnderLined = isUnderLined;
    }

    public boolean isUnderLined() {
        return isUnderLined;
    }
}
