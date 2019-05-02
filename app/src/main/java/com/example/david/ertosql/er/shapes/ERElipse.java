package com.example.david.ertosql.er.shapes;

import java.util.Objects;

public class ERElipse extends ERShape.ERShapeWZCenter {


    private boolean isUnderLined;

    public ERElipse(ERPoint center, String text, boolean isUnderLined) {
        super(center, text);
        this.isUnderLined = isUnderLined;
    }

    public boolean isUnderLined() {
        return isUnderLined;
    }

    @Override
    public String toString() {
        return "ERElipse{" +
                "isUnderLined=" + isUnderLined +
                ", center=" + center +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ERElipse)) return false;
        ERElipse erElipse = (ERElipse) o;
        return this.text.equals(erElipse.text) && this.center.equals(erElipse.center)&& isUnderLined() == erElipse.isUnderLined();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isUnderLined());
    }

  /*   @Override
    public boolean equals(Object ell2) {
        if(this == ell2) return true;//if both of them points the same address in memory

        if(!(ell2 instanceof ERElipse)) return false; // if "that" is not a People or a childclass

        ERElipse erElipse = (ERElipse) ell2;
        return this.text.equals(erElipse.text) && this.center.equals(erElipse.center);
    }*/
}
