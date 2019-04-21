package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERTable {
    String title;
    ArrayList<ERAttribute> columns;
    ArrayList<ERAttribute> primaryKeys;
    ArrayList<ERAttribute> unique;
    ArrayList<ERForeignKey> foreignKeys;

    public ERTable(String title, ArrayList<ERAttribute> columns, ArrayList<ERAttribute> primaryKeys, ArrayList<ERAttribute> unique) {
        this.title = title;
        this.columns = columns;
        this.primaryKeys = primaryKeys;
        this.unique = unique;

    }

    public ERTable(String title, ArrayList<ERAttribute> columns, ArrayList<ERAttribute> primaryKeys) {
        this.title = title;
        this.columns = columns;
        this.primaryKeys = primaryKeys;
    }

    public ERTable(String title, ArrayList<ERAttribute> columns, ArrayList<ERAttribute> primaryKeys, ArrayList<ERAttribute> unique, ArrayList<ERForeignKey> foreignKeys) {
        this.title = title;
        this.columns = columns;
        this.primaryKeys = primaryKeys;
        this.unique = unique;
        this.foreignKeys = foreignKeys;
    }

    @Override
    public String toString() {
        String string = "TableTitle:" + title
                + " Columns:" + columns.toString();
        string+= " PrimaryKeys:" + primaryKeys.toString();
        if(unique!=null)
            string+= " Unique:" + unique.toString();
        if(foreignKeys!=null)
            string+= " ForeignKeys:" + foreignKeys.toString();
        return string;
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

    public ArrayList<ERForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ArrayList<ERForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
}
