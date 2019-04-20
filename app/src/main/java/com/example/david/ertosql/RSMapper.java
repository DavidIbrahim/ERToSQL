package com.example.david.ertosql;

import com.example.david.ertosql.ERObjects.ERAttribute;
import com.example.david.ertosql.ERObjects.ERDiagram;
import com.example.david.ertosql.ERObjects.EREntity;
import com.example.david.ertosql.ERObjects.EROneToOneRelationship;
import com.example.david.ertosql.ERObjects.ERRelationalSchema;
import com.example.david.ertosql.ERObjects.ERRelationship;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RSMapper {
    ArrayList<ERRelationalSchema> relationalSchemas;

    public RSMapper(ERDiagram diagram) {
        this.relationalSchemas = new ArrayList<>();
        for (ERRelationship erRelationship : diagram.getRelations()) {
            Class erClass = erRelationship.getClass();
            if (erClass.equals(EROneToOneRelationship.class)) {
                EROneToOneRelationship oneToOne = (EROneToOneRelationship) erRelationship;

                if (oneToOne.getParticipation().toString().equals("TOTAL_TOTAL")) {
                    formTotalTotalRelationalSchema(oneToOne);
                }
                else if (oneToOne.getParticipation().toString().equals("PARTIAL_TOTAL")) {
                    formPartialTotalRelationalSchema(oneToOne);
                }
                else if (oneToOne.getParticipation().toString().equals("PARTIAL_PARTIAL")) {
                    formPartialPartialRelationalSchema(oneToOne);
                }
            }
        }
    }

    private void formPartialPartialRelationalSchema(EROneToOneRelationship oneToOne) {
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String schemaTitle1 = entity1.getTitle();
        ArrayList<ERAttribute> schemaCandidateKeys1=entity1.getKey();
        ArrayList<ERAttribute> schemaAttributes1 = entity1.getEntityAttributes();

        String schemaTitle2 = entity2.getTitle();
        ArrayList<ERAttribute> schemaCandidateKeys2=entity2.getKey();
        ArrayList<ERAttribute> schemaAttributes2 = entity2.getEntityAttributes();

        String schemaTitle3 = oneToOne.getTitle();
        ArrayList<ERAttribute> schemaCandidateKeys3=new ArrayList<>();
        schemaCandidateKeys3.addAll(entity1.getKey());
        schemaCandidateKeys3.addAll(entity2.getKey());

        //todo: support composite/multivalued attributes?
        ERRelationalSchema relationalSchema1 = new ERRelationalSchema(schemaTitle1,schemaCandidateKeys1,schemaAttributes1);
        ERRelationalSchema relationalSchema2 = new ERRelationalSchema(schemaTitle2,schemaCandidateKeys2,schemaAttributes2);
        ERRelationalSchema relationalSchema3 = new ERRelationalSchema(schemaTitle3,schemaCandidateKeys3);

        relationalSchemas.add(relationalSchema1);
        relationalSchemas.add(relationalSchema2);
        relationalSchemas.add(relationalSchema3);

    }

    public ArrayList<ERRelationalSchema> getRelationalSchemas() {
        return relationalSchemas;
    }

    private void formPartialTotalRelationalSchema(EROneToOneRelationship oneToOne) {
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String schemaTitle1 = entity1.getTitle();
        ArrayList<ERAttribute> schemaCandidateKeys1=entity1.getKey();
        ArrayList<ERAttribute> schemaAttributes1 = entity1.getEntityAttributes();

        String schemaTitle2 = entity2.getTitle();
        ArrayList<ERAttribute> schemaCandidateKeys2=entity2.getKey();
        ArrayList<ERAttribute> schemaAttributes2 = entity2.getEntityAttributes();

        //todo: support composite/multivalued attributes?
        ERRelationalSchema relationalSchema1 = new ERRelationalSchema(schemaTitle1,schemaCandidateKeys1,schemaAttributes1);
        ERRelationalSchema relationalSchema2 = new ERRelationalSchema(schemaTitle2,schemaCandidateKeys2,schemaAttributes2);
        relationalSchema2.setForeignKeys(schemaCandidateKeys1);

        relationalSchemas.add(relationalSchema1);
        relationalSchemas.add(relationalSchema2);
    }

    private void formTotalTotalRelationalSchema(EROneToOneRelationship oneToOne) {
        EREntity entity1 = oneToOne.getEntity1();
        EREntity entity2 = oneToOne.getEntity2();

        String schemaTitle;
        ArrayList<ERAttribute> schemaCandidateKeys;


        if(entity1.getKey().size()<entity2.getKey().size()){
            schemaCandidateKeys=entity1.getKey();
            schemaTitle = oneToOne.getEntity1().getTitle() + "-" + oneToOne.getEntity2().getTitle();
        }
        else{
            schemaCandidateKeys=entity2.getKey();
            schemaTitle = oneToOne.getEntity2().getTitle() + "-" + oneToOne.getEntity1().getTitle();
        }
        //todo: support composite/multivalued attributes?
        ArrayList<ERAttribute> schemaAttributes = new ArrayList<>();
        schemaAttributes.addAll(entity1.getEntityAttributes());
        schemaAttributes.addAll(entity2.getEntityAttributes());


        ERRelationalSchema relationalSchema = new ERRelationalSchema(schemaTitle,schemaCandidateKeys,schemaAttributes);

        relationalSchemas.add(relationalSchema);
    }
}
