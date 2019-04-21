package com.example.david.ertosql.ERObjects;

public class ERForeignKey {
    ERAttribute column;
    EREntity reference;

    public ERForeignKey(ERAttribute column, EREntity reference) {
        this.column = column;
        this.reference = reference;
    }
}
