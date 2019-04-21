package com.example.david.ertosql;

import com.example.david.ertosql.ERObjects.ERRelationalSchema;

public class SQLMapper {
    String SQLCode = "";

    public String getSQLCode(){
        return SQLCode;
    }

    public SQLMapper(ERRelationalSchema relationalSchema) {
        //Manipulate the relational schema tables to output SQL code here
    }
}
