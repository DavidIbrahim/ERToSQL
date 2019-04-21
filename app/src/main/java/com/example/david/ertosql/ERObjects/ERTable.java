package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERTable {
    String title;
    ArrayList<ERAttribute> columns;
    ArrayList<ERAttribute> primaryKeys;
    ArrayList<ERForeignKey> foreignKeys;

    @Override
    public String toString() {
        String string = "TableTitle:" + title
                + " Columns:" + columns.toString();
        string+= " PrimaryKeys:" + primaryKeys.toString();
        if(foreignKeys!=null)
            string+= " ForeignKeys:" + foreignKeys.toString();
        return string;
    }

    public ERTable(String title, ArrayList<ERAttribute> columns, ArrayList<ERAttribute> primaryKeys) {
        this.title = title;
        this.columns = columns;
        this.primaryKeys = primaryKeys;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ERAttribute> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(ArrayList<ERAttribute> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public ArrayList<ERAttribute> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<ERAttribute> columns) {
        this.columns = columns;
    }

    public ERTable(String title, ArrayList<ERAttribute> columns, ArrayList<ERAttribute> primaryKeys, ArrayList<ERForeignKey> foreignKeys) {
        this.title = title;
        this.columns = columns;
        this.primaryKeys = primaryKeys;
        this.foreignKeys = foreignKeys;
    }

    public ArrayList<ERForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ArrayList<ERForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
}
