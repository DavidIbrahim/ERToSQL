package com.example.david.ertosql;

import com.example.david.ertosql.ERObjects.ERAttribute;
import com.example.david.ertosql.ERObjects.ERDiagram;
import com.example.david.ertosql.ERObjects.EREntity;
import com.example.david.ertosql.ERObjects.ERForeignKey;
import com.example.david.ertosql.ERObjects.ERBinaryRelationship;
import com.example.david.ertosql.ERObjects.ERTable;
import com.example.david.ertosql.ERObjects.ERRelationship;

import java.util.ArrayList;

public class RSMapper {
    private ArrayList<ERTable> tables;

    public RSMapper(ERDiagram diagram) {
        //todo: separate entities
        this.tables = new ArrayList<>();
        for (ERRelationship erRelationship : diagram.getRelations()) {
            Class erClass = erRelationship.getClass();
            if (erClass.equals(ERBinaryRelationship.class)) {
                ERBinaryRelationship binary = (ERBinaryRelationship) erRelationship;

                switch (binary.getParticipation().toString()) {
                    case "TOTAL_TOTAL":
                        formTotalTotalRelationalSchema(binary);
                        break;

                    case "PARTIAL_TOTAL":
                        formPartialTotalRelationalSchema(binary);
                        break;

                    case "PARTIAL_PARTIAL":
                        formPartialPartialRelationalSchema(binary);
                        break;

                    case "ONE_MANY":
                        formOneToManyRelationalSchema(binary);
                        break;

                    case "MANY_MANY":
                        formManyToManyRelationalSchema(binary);
                        break;
                }
            }
        }
    }

    private void formOneToManyRelationalSchema(ERBinaryRelationship binary) {
        //same conversion
        formPartialTotalRelationalSchema(binary);
    }

    private void formManyToManyRelationalSchema(ERBinaryRelationship oneToOne) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String firstTableTitle = entity1.getTitle();

        ArrayList<ERAttribute> firstTablePrimaryKeys = new ArrayList<>();
        firstTablePrimaryKeys.addAll(entity1.getKey());

        ArrayList<ERAttribute> firstTableUnique = new ArrayList<>(entity1.getUniqueAttributes());
        firstTableUnique.remove(entity1.getKey());

        ArrayList<ERAttribute> firstTableColumns = new ArrayList<>();
        firstTableColumns.addAll(entity1.getUniqueAttributes());
        firstTableColumns.addAll(entity1.getEntityAttributes());

        ERTable table1 = new ERTable(firstTableTitle, firstTableColumns, firstTablePrimaryKeys, firstTableUnique);

        String secondTableTitle = entity2.getTitle();

        ArrayList<ERAttribute> secondTablePrimaryKeys = new ArrayList<>();
        secondTablePrimaryKeys = entity2.getKey();

        ArrayList<ERAttribute> secondTableUnique = new ArrayList<>(entity2.getUniqueAttributes());
        secondTableUnique.remove(entity2.getKey());

        ArrayList<ERAttribute> secondTableColumns = new ArrayList<>();
        secondTableColumns.addAll(entity2.getUniqueAttributes());
        secondTableColumns.addAll(entity2.getEntityAttributes());

        ERTable table2 = new ERTable(secondTableTitle, secondTableColumns, secondTablePrimaryKeys, secondTableUnique);

        String thirdTableTitle = oneToOne.getTitle();

        ArrayList<ERAttribute> thirdTablePrimaryKeys = new ArrayList<>();
        thirdTablePrimaryKeys.addAll(entity1.getKey());
        thirdTablePrimaryKeys.addAll(entity2.getKey());

        ArrayList<ERForeignKey> thirdTableForeignKeys = new ArrayList<>();
        thirdTableForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));
        thirdTableForeignKeys.add(new ERForeignKey(entity2.getKey(),entity2));

        //The schema columns are only its primary keys

        ERTable table3 = new ERTable(thirdTableTitle, thirdTablePrimaryKeys, thirdTablePrimaryKeys);
        table3.setForeignKeys(thirdTableForeignKeys);

        tables.add(table1);
        tables.add(table2);
        tables.add(table3);

    }

    private void formPartialPartialRelationalSchema(ERBinaryRelationship binary) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = binary.getEntity1();
        EREntity entity2 = binary.getEntity2();

        String firstTableTitle = entity1.getTitle();

        ArrayList<ERAttribute> firstTablePrimaryKeys = new ArrayList<>();
        firstTablePrimaryKeys.addAll(entity1.getKey());

        ArrayList<ERAttribute> firstTableUnique = new ArrayList<>(entity1.getUniqueAttributes());
        firstTableUnique.remove(entity1.getKey());

        ArrayList<ERAttribute> firstTableColumns = new ArrayList<>();
        firstTableColumns.addAll(entity1.getUniqueAttributes());
        firstTableColumns.addAll(entity1.getEntityAttributes());

        ERTable table1 = new ERTable(firstTableTitle, firstTableColumns, firstTablePrimaryKeys, firstTableUnique);

        String secondTableTitle = entity2.getTitle();

        ArrayList<ERAttribute> secondTablePrimaryKeys = new ArrayList<>();
        secondTablePrimaryKeys = entity2.getKey();

        ArrayList<ERAttribute> secondTableUnique = new ArrayList<>(entity2.getUniqueAttributes());
        secondTableUnique.remove(entity2.getKey());

        ArrayList<ERAttribute> secondTableColumns = new ArrayList<>();
        secondTableColumns.addAll(entity2.getUniqueAttributes());
        secondTableColumns.addAll(entity2.getEntityAttributes());

        ERTable table2 = new ERTable(secondTableTitle, secondTableColumns, secondTablePrimaryKeys, secondTableUnique);

        String thirdTableTitle = binary.getTitle();

        ArrayList<ERAttribute> thirdTablePrimaryKeys = new ArrayList<>();
        thirdTablePrimaryKeys.addAll(entity1.getKey());
        //thirdTablePrimaryKeys.addAll(entity2.getKey());

        ArrayList<ERForeignKey> thirdTableForeignKeys = new ArrayList<>();
        thirdTableForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));
        thirdTableForeignKeys.add(new ERForeignKey(entity2.getKey(),entity2));

        //The schema columns are only its primary keys

        ERTable table3 = new ERTable(thirdTableTitle, thirdTablePrimaryKeys, thirdTablePrimaryKeys);
        table3.setForeignKeys(thirdTableForeignKeys);

        tables.add(table1);
        tables.add(table2);
        tables.add(table3);

    }

    public ArrayList<ERTable> getTables() {
        return tables;
    }

    private void formPartialTotalRelationalSchema(ERBinaryRelationship binary) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = binary.getEntity1();
        EREntity entity2 = binary.getEntity2();

        String firstTableTitle = entity1.getTitle();

        ArrayList<ERAttribute> firstTablePrimaryKeys = new ArrayList<>();
        firstTablePrimaryKeys.addAll(entity1.getKey());

        ArrayList<ERAttribute> firstTableUnique = new ArrayList<>(entity1.getUniqueAttributes());
        firstTableUnique.remove(entity1.getKey());

        ArrayList<ERAttribute> firstTableColumns = new ArrayList<>();
        firstTableColumns.addAll(entity1.getUniqueAttributes());
        firstTableColumns.addAll(entity1.getEntityAttributes());

        ERTable table1 = new ERTable(firstTableTitle, firstTableColumns, firstTablePrimaryKeys, firstTableUnique);

        String secondTableTitle = entity2.getTitle();

        ArrayList<ERAttribute> secondTablePrimaryKeys = new ArrayList<>();
        secondTablePrimaryKeys.addAll(entity2.getKey());

        ArrayList<ERForeignKey> secondTableForeignKeys = new ArrayList<>();
        secondTableForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));

        ArrayList<ERAttribute> secondTableUnique = new ArrayList<>(entity2.getUniqueAttributes());
        secondTableUnique.remove(entity2.getKey());

        ArrayList<ERAttribute> secondTableColumns = new ArrayList<>();
        secondTableColumns.addAll(entity2.getUniqueAttributes());
        secondTableColumns.addAll(entity2.getEntityAttributes());
        secondTableColumns.addAll(entity1.getKey());

        ERTable table2 = new ERTable(secondTableTitle, secondTableColumns, secondTablePrimaryKeys, secondTableUnique, secondTableForeignKeys);

        tables.add(table1);
        tables.add(table2);
    }

    private void formTotalTotalRelationalSchema(ERBinaryRelationship binary) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = binary.getEntity1();
        EREntity entity2 = binary.getEntity2();

        String tableTitle = binary.getEntity1().getTitle() + "-" + binary.getEntity2().getTitle();

        ArrayList<ERAttribute> tablePrimaryKeys = new ArrayList<>();
        tablePrimaryKeys.addAll(entity1.getKey());

        ArrayList<ERAttribute> tableUnique= new ArrayList<>();
        tableUnique.addAll(entity1.getUniqueAttributes());
        tableUnique.addAll(entity2.getUniqueAttributes());
        tableUnique.remove(entity1.getKey());

        ArrayList<ERAttribute> tableColumns = new ArrayList<>();
        tableColumns.addAll(entity1.getUniqueAttributes());
        tableColumns.addAll(entity1.getEntityAttributes());
        tableColumns.addAll(entity2.getUniqueAttributes());
        tableColumns.addAll(entity2.getEntityAttributes());

        ERTable table = new ERTable(tableTitle, tableColumns, tablePrimaryKeys, tableUnique);

        tables.add(table);
    }
}
