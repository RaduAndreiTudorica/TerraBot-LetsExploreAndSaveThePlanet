package main.entities;

import main.Section;

import java.util.*;

public class Animal extends Entity {
    private String status;
    private String type;

    public Animal(String name, double mass, List<Section> sections , String status, String type){
        super(name, mass, sections);

        this.status = status;
        this.type = type;
    }
}
