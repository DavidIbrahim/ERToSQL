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

    public ArrayList<ERRelationalSchema> getRelationalSchemas() {
        return relationalSchemas;
    }

    public RSMapper(ERDiagram diagram) {
        this.relationalSchemas = new ArrayList<>();
        for (ERRelationship erRelationship : diagram.getRelations()) {
            Class erClass = erRelationship.getClass();
            if (erClass.equals(EROneToOneRelationship.class)) {
                EROneToOneRelationship oneToOne = (EROneToOneRelationship) erRelationship;

                if (oneToOne.getParticipation().toString().equals("TOTAL_TOTAL")) {
                    formTotalTotalRelationalSchema(oneToOne);
                }
            }
        }
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
        //todo: support composite attributes?
        ArrayList<ERAttribute> schemaAttributes = new ArrayList<>();
        schemaAttributes.addAll(entity1.getEntityAttributes());
        schemaAttributes.addAll(entity2.getEntityAttributes());


        ERRelationalSchema relationalSchema = new ERRelationalSchema(schemaTitle,schemaCandidateKeys,schemaAttributes);

        relationalSchemas.add(relationalSchema);
    }
}
