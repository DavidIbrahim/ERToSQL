package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERRelationalSchema {
    ArrayList<ERTable> tables;

    public ERRelationalSchema(ArrayList<ERTable> tables) {
        this.tables = tables;
    }

    public ArrayList<ERTable> getTables() {
        return tables;
    }

    public void setTables(ArrayList<ERTable> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "Tables: " + tables.toString();
    }
}
