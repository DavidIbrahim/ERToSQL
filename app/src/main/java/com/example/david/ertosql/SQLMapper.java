package com.example.david.ertosql;

import com.example.david.ertosql.ERObjects.ERAttribute;
import com.example.david.ertosql.ERObjects.ERForeignKey;
import com.example.david.ertosql.ERObjects.ERRelationalSchema;
import com.example.david.ertosql.ERObjects.ERTable;

import java.util.ArrayList;

public class SQLMapper {
    String SQLCode = "";
    String defaultAttributeType = "VARCHAR(255)";

    public String getSQLCode(){
        return SQLCode;
    }

    public SQLMapper(ERRelationalSchema relationalSchema) {
        StringBuilder SQLTables = new StringBuilder("");
        ArrayList<ERTable> tables = relationalSchema.getTables();
        for (int i = 0; i < tables.size(); i++){
            SQLTables.append(getSQLTable(tables.get(i), relationalSchema));
            SQLTables.append("\n");
        }
        SQLCode = SQLTables.toString();
    }
    private String getSQLTable (ERTable table, ERRelationalSchema relationalSchema) {
        ArrayList<ERAttribute> attributes = table.getColumns();
        ArrayList<ERAttribute> primaryKeys = table.getPrimaryKeys();
        ArrayList<ERAttribute> unique = table.getUnique();
        ArrayList<ERForeignKey> foreignKeys = table.getForeignKeys();

        StringBuilder SQLTable = new StringBuilder("CREATE TABLE ");
        SQLTable.append(table.getTitle()+" (");
        // SQL attributes code
        for (int i = 0; i <attributes.size(); i++) {
            SQLTable.append("\n");
            if (primaryKeys.contains(attributes.get(i))) {
                // if attribute is primary key make default type INT and add not null constraint.
                SQLTable.append(attributes.get(i).getTitle() + " " + "INT" +" NOT NULL"+ ",");
            }
            else {
                // if attribute isn't a primary key make default type VARCHAR
                SQLTable.append(attributes.get(i).getTitle() + " " + defaultAttributeType + ",");
            }
        }

        // SQL Primary keys code
        if (primaryKeys.size() > 0) {
            SQLTable.append("\n");
            SQLTable.append("PRIMARY KEY (");
            for (int i = 0; i < primaryKeys.size(); i++){
                SQLTable.append(primaryKeys.get(i).getTitle()).append(",");
            }
            SQLTable.setLength(SQLTable.length()-1); // remove last ","
            SQLTable.append(")");
        }
        SQLTable.append(",");

        // SQL Foreign keys code
        if (foreignKeys.size() > 0) {
            SQLTable.append("\n");
            SQLTable.append("FOREIGN KEY (");
            for (int i = 0; i < foreignKeys.size(); i++){
                ERForeignKey currentForeignKey = foreignKeys.get(i);
                for (int j = 0; j < currentForeignKey.getColumn().size(); j++){
                    SQLTable.append(currentForeignKey.getColumn().get(j).getTitle());
                    SQLTable.append(",");
                }
                SQLTable.setLength(SQLTable.length()-1); // remove last ","
                SQLTable.append(") REFERENCES ");
                String referencedTableTitle = currentForeignKey.getReference().getTitle();
                SQLTable.append(referencedTableTitle);

                // getting the primary key of the referenced table
                for (int j = 0; j < relationalSchema.getTables().size(); j++){
                    String currentTableTitle = relationalSchema.getTables().get(j).getTitle();
                    if (referencedTableTitle.equals(currentTableTitle)) {
                        ArrayList<ERAttribute> referencedTablePrimaryKeys = relationalSchema.getTables().get(j).getPrimaryKeys();
                        SQLTable.append("(");
                        for (int k = 0; k < referencedTablePrimaryKeys.size(); k++){
                            SQLTable.append(referencedTablePrimaryKeys.get(k).getTitle()).append(",");
                        }
                        SQLTable.setLength(SQLTable.length()-1); // remove last ","
                        SQLTable.append("),");
                    }
                    break;
                }
            }
            SQLTable.setLength(SQLTable.length()-1); // remove last ","
            SQLTable.append(",");
        }
        SQLTable.setLength(SQLTable.length()-1); // remove last ","
        SQLTable.append("\n);");
        return SQLTable.toString();
    }

}
