package com.example.david.ertosql.ERObjects;

import java.util.ArrayList;

public class ERMain {
    public static void main(String args[]) {
        //Simple Attributes
        ERAttribute K2 = new ERAttribute("DepID");
        ERAttribute A3 = new ERAttribute("Name");
        ERAttribute A4 = new ERAttribute("Building No.");

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

        ERTotalPartialRelationship R1 = new ERTotalPartialRelationship("Has");
        R1.addEntity(E1, ERTotalPartialRelationship.Participation.partialMany);
        R1.addEntity(E2, ERTotalPartialRelationship.Participation.totalOne);

        System.out.println(R1);
    }
}
