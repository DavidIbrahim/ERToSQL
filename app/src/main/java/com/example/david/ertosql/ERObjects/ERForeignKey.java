package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERForeignKey {
    ArrayList<ERAttribute> column;
    EREntity reference;

    public ERForeignKey(ArrayList<ERAttribute> column, EREntity reference) {
        this.column = column;
        this.reference = reference;
    }

    public ArrayList<ERAttribute> getColumn() {
        return column;
    }

    public void setColumn(ArrayList<ERAttribute> column) {
        this.column = column;
    }

    public EREntity getReference() {
        return reference;
    }

    public void setReference(EREntity reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        String constraint = "[attribute: (";
        for (int i = 0; i < column.size(); i++){
            constraint += column.get(i).getTitle()+ ",";
        }
        constraint = constraint.substring(0, constraint.length()-1); // remove last ","
        constraint += ")" + ", reference:" + reference.getTitle() + "]";
        return constraint;
    }
}
