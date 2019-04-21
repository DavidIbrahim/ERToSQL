package com.example.david.ertosql.ERObjects;

import com.example.david.ertosql.RSMapper;
import com.example.david.ertosql.SQLMapper;

import java.util.ArrayList;

public class ERMain {
    public static void main(String args[]) {
        //Creating Simple Attributes
        ERAttribute K2 = new ERAttribute("DepID");
        ERAttribute A3 = new ERAttribute("Name");
        ERAttribute A4 = new ERAttribute("Building No.");

        //Setting columns to unique in an entity
        ArrayList<ERAttribute> keys2 = new ArrayList<>();
        keys2.add(K2);
        ArrayList<ERAttribute> attributes2 = new ArrayList<>();
        attributes2.add(A3);
        attributes2.add(A4);

        ERAttribute K1 = new ERAttribute("ID");
        ERAttribute A1 = new ERAttribute("Name");
        //Composite Attribute
        ERCompositeAttribute A2 = new ERCompositeAttribute("DOB");
        A2.addAttribute(new ERAttribute("dd"));
        A2.addAttribute(new ERAttribute("mm"));
        A2.addAttribute(new ERAttribute("yyyy"));

        ArrayList<ERAttribute> keys1 = new ArrayList<>();
        keys1.add(K1);
        ArrayList<ERAttribute> attributes1 = new ArrayList<>();
        attributes1.add(A1);
        attributes1.add(A2);

        EREntity E1 = new EREntity("Employee", keys1, attributes1);
        EREntity E2 = new EREntity("Department", keys2, attributes2);

        EROneToOneRelationship R1 = new EROneToOneRelationship("Has",E2,E1,EROneToOneRelationship.Participation.PARTIAL_TOTAL);

        //System.out.println(R1);

        //ER Diagram
        ERDiagram D1 = new ERDiagram("Diagram 1", R1);

        //Relational Schema
        RSMapper rsMapper = new RSMapper(D1);
        ERRelationalSchema relationalSchema = new ERRelationalSchema(rsMapper.getTables());

        System.out.println(relationalSchema);

        //SQL code
        SQLMapper sqlMapper = new SQLMapper(relationalSchema);
        String SQLCode = sqlMapper.getSQLCode();

        //System.out.println(SQLCode);
    }
}
