package com.example.david.ertosql;

import com.example.david.ertosql.ERObjects.ERAttribute;
import com.example.david.ertosql.ERObjects.ERDiagram;
import com.example.david.ertosql.ERObjects.EREntity;
import com.example.david.ertosql.ERObjects.ERForeignKey;
import com.example.david.ertosql.ERObjects.EROneToOneRelationship;
import com.example.david.ertosql.ERObjects.ERRelationalSchema;
import com.example.david.ertosql.ERObjects.ERRelationship;

import java.util.ArrayList;

public class RSMapper {
    private ArrayList<ERRelationalSchema> relationalSchemas;

    public RSMapper(ERDiagram diagram) {
        //todo: separate entities
        this.relationalSchemas = new ArrayList<>();
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

        String schemaTitle1 = entity1.getTitle();

        ArrayList<ERAttribute> firstSchemaPrimaryKeys = new ArrayList<>();
        firstSchemaPrimaryKeys.add(entity1.getKey());

        ArrayList<ERAttribute> firstSchemaColumns = new ArrayList<>();
        firstSchemaColumns.addAll(entity1.getUniqueAttributes());
        firstSchemaColumns.addAll(entity1.getEntityAttributes());

        ERRelationalSchema relationalSchema1 = new ERRelationalSchema(schemaTitle1, firstSchemaColumns, firstSchemaPrimaryKeys);

        String schemaTitle2 = entity2.getTitle();
        ArrayList<ERAttribute> secondSchemaPrimaryKeys = new ArrayList<>();
        secondSchemaPrimaryKeys.add(entity2.getKey());

        ArrayList<ERAttribute> secondSchemaColumns = new ArrayList<>();
        secondSchemaColumns.addAll(entity2.getUniqueAttributes());
        secondSchemaColumns.addAll(entity2.getEntityAttributes());

        ERRelationalSchema relationalSchema2 = new ERRelationalSchema(schemaTitle2, secondSchemaColumns, secondSchemaPrimaryKeys);

        String schemaTitle3 = oneToOne.getTitle();

        ArrayList<ERAttribute> thirdSchemaPrimaryKeys = new ArrayList<>();
        thirdSchemaPrimaryKeys.add(entity1.getKey());
        thirdSchemaPrimaryKeys.add(entity2.getKey());

        ArrayList<ERForeignKey> thirdSchemaForeignKeys = new ArrayList<>();
        thirdSchemaForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));
        thirdSchemaForeignKeys.add(new ERForeignKey(entity2.getKey(),entity2));

        //The schema columns are only its primary keys

        ERRelationalSchema relationalSchema3 = new ERRelationalSchema(schemaTitle3, thirdSchemaPrimaryKeys, thirdSchemaPrimaryKeys,thirdSchemaForeignKeys);

        relationalSchemas.add(relationalSchema1);
        relationalSchemas.add(relationalSchema2);
        relationalSchemas.add(relationalSchema3);

    }

    public ArrayList<ERRelationalSchema> getRelationalSchemas() {
        return relationalSchemas;
    }

    private void formPartialTotalRelationalSchema(EROneToOneRelationship oneToOne) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String firstSchemaTitle = entity1.getTitle();

        ArrayList<ERAttribute> firstSchemaPrimaryKeys = new ArrayList<>();
        firstSchemaPrimaryKeys.add(entity1.getKey());

        ArrayList<ERAttribute> firstSchemaColumns = new ArrayList<>();
        firstSchemaColumns.addAll(entity1.getUniqueAttributes());
        firstSchemaColumns.addAll(entity1.getEntityAttributes());

        ERRelationalSchema relationalSchema1 = new ERRelationalSchema(firstSchemaTitle, firstSchemaColumns, firstSchemaPrimaryKeys);

        String secondSchemaTitle = entity2.getTitle();

        ArrayList<ERAttribute> secondSchemaPrimaryKeys = new ArrayList<>();
        secondSchemaPrimaryKeys.add(entity2.getKey());

        ArrayList<ERForeignKey> secondSchemaForeignKeys = new ArrayList<>();
        secondSchemaForeignKeys.add(new ERForeignKey(entity1.getKey(),entity1));

        ArrayList<ERAttribute> secondSchemaColumns = new ArrayList<>();
        firstSchemaColumns.addAll(entity1.getUniqueAttributes());
        firstSchemaColumns.addAll(entity1.getEntityAttributes());


        ERRelationalSchema relationalSchema2 = new ERRelationalSchema(secondSchemaTitle, secondSchemaColumns, secondSchemaPrimaryKeys, secondSchemaForeignKeys);

        relationalSchemas.add(relationalSchema1);
        relationalSchemas.add(relationalSchema2);
    }

    private void formTotalTotalRelationalSchema(EROneToOneRelationship oneToOne) {
        //todo: support composite/multivalued attributes?
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String schemaTitle = oneToOne.getEntity1().getTitle() + "-" + oneToOne.getEntity2().getTitle();

        ArrayList<ERAttribute> schemaPrimaryKeys = new ArrayList<>();
        schemaPrimaryKeys.add(entity1.getKey());

        ArrayList<ERAttribute> schemaColumns = new ArrayList<>();
        schemaColumns.addAll(entity1.getUniqueAttributes());
        schemaColumns.addAll(entity1.getEntityAttributes());
        schemaColumns.addAll(entity2.getUniqueAttributes());
        schemaColumns.addAll(entity2.getEntityAttributes());

        ERRelationalSchema relationalSchema = new ERRelationalSchema(schemaTitle, schemaColumns, schemaPrimaryKeys);

        relationalSchemas.add(relationalSchema);
    }
}
