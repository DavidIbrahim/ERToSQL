package com.example.david.ertosql;

import com.example.david.ertosql.ERObjects.ERAttribute;
import com.example.david.ertosql.ERObjects.ERDiagram;
import com.example.david.ertosql.ERObjects.EREntity;
import com.example.david.ertosql.ERObjects.ERForeignKey;
import com.example.david.ertosql.ERObjects.EROneToOneRelationship;
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
            if (erClass.equals(EROneToOneRelationship.class)) {
                EROneToOneRelationship oneToOne = (EROneToOneRelationship) erRelationship;

                switch (oneToOne.getParticipation().toString()) {
                    case "TOTAL_TOTAL":
                        formTotalTotalRelationalSchema(oneToOne);
                        break;

                    case "PARTIAL_TOTAL":
                        formPartialTotalRelationalSchema(oneToOne);
                        break;

                    case "PARTIAL_PARTIAL":
                        formPartialPartialRelationalSchema(oneToOne);
                        break;
                }
            }
        }
    }

    private void formPartialPartialRelationalSchema(EROneToOneRelationship oneToOne) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String firstTableTitle = entity1.getTitle();

        ArrayList<ERAttribute> firstTablePrimaryKeys = new ArrayList<>();
        firstTablePrimaryKeys.add(entity1.getKey());

        ArrayList<ERAttribute> firstTableColumns = new ArrayList<>();
        firstTableColumns.addAll(entity1.getUniqueAttributes());
        firstTableColumns.addAll(entity1.getEntityAttributes());

        ERTable table1 = new ERTable(firstTableTitle, firstTableColumns, firstTablePrimaryKeys);

        String secondTableTitle = entity2.getTitle();
        ArrayList<ERAttribute> secondTablePrimaryKeys = new ArrayList<>();
        secondTablePrimaryKeys.add(entity2.getKey());

        ArrayList<ERAttribute> secondTableColumns = new ArrayList<>();
        secondTableColumns.addAll(entity2.getUniqueAttributes());
        secondTableColumns.addAll(entity2.getEntityAttributes());

        ERTable table2 = new ERTable(secondTableTitle, secondTableColumns, secondTablePrimaryKeys);

        String thirdTableTitle = oneToOne.getTitle();

        ArrayList<ERAttribute> thirdTablePrimaryKeys = new ArrayList<>();
        thirdTablePrimaryKeys.add(entity1.getKey());
        thirdTablePrimaryKeys.add(entity2.getKey());

        ArrayList<ERForeignKey> thirdTableForeignKeys = new ArrayList<>();
        thirdTableForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));
        thirdTableForeignKeys.add(new ERForeignKey(entity2.getKey(),entity2));

        //The schema columns are only its primary keys

        ERTable table3 = new ERTable(thirdTableTitle, thirdTablePrimaryKeys, thirdTablePrimaryKeys,thirdTableForeignKeys);

        tables.add(table1);
        tables.add(table2);
        tables.add(table3);

    }

    public ArrayList<ERTable> getTables() {
        return tables;
    }

    private void formPartialTotalRelationalSchema(EROneToOneRelationship oneToOne) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String firstTableTitle = entity1.getTitle();

        ArrayList<ERAttribute> firstTablePrimaryKeys = new ArrayList<>();
        firstTablePrimaryKeys.add(entity1.getKey());

        ArrayList<ERAttribute> firstTableColumns = new ArrayList<>();
        firstTableColumns.addAll(entity1.getUniqueAttributes());
        firstTableColumns.addAll(entity1.getEntityAttributes());

        ERTable table1 = new ERTable(firstTableTitle, firstTableColumns, firstTablePrimaryKeys);

        String secondTableTitle = entity2.getTitle();

        ArrayList<ERAttribute> secondTablePrimaryKeys = new ArrayList<>();
        secondTablePrimaryKeys.add(entity2.getKey());

        ArrayList<ERForeignKey> secondTableForeignKeys = new ArrayList<>();
        secondTableForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));

        ArrayList<ERAttribute> secondTableColumns = new ArrayList<>();
        secondTableColumns.addAll(entity2.getUniqueAttributes());
        secondTableColumns.addAll(entity2.getEntityAttributes());
        secondTableColumns.add(entity1.getKey());

        ERTable table2 = new ERTable(secondTableTitle, secondTableColumns, secondTablePrimaryKeys, secondTableForeignKeys);

        tables.add(table1);
        tables.add(table2);
    }

    private void formTotalTotalRelationalSchema(EROneToOneRelationship oneToOne) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String tableTitle = oneToOne.getEntity1().getTitle() + "-" + oneToOne.getEntity2().getTitle();

        ArrayList<ERAttribute> tablePrimaryKeys = new ArrayList<>();
        tablePrimaryKeys.add(entity1.getKey());

        ArrayList<ERAttribute> tableColumns = new ArrayList<>();
        tableColumns.addAll(entity1.getUniqueAttributes());
        tableColumns.addAll(entity1.getEntityAttributes());
        tableColumns.addAll(entity2.getUniqueAttributes());
        tableColumns.addAll(entity2.getEntityAttributes());

        ERTable table = new ERTable(tableTitle, tableColumns, tablePrimaryKeys);

        tables.add(table);
    }
}
