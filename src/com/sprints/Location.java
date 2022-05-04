package com.sprints;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

class Location {
    private String name;
    private String description;
    private ArrayList<String> items;
    private Map<String, String> directions;
    private boolean itemsPresent = false;


    public Location(String name, String description, ArrayList<String> items) {
        this.name = name;
        this.description = description;
        this.items = items;
//        this.directions = directions;
    }
}
