package com.sprints.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.sprints.TextParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class App {
    public static String currentRoom = "basement";
    public static List<String> inventory = new ArrayList<>();;
    private boolean tutorial = true;
    private boolean gameOver = false;
    private TextParser parser = new TextParser();

    Scanner myObj = new Scanner(System.in);

    public void execute() throws IOException, ParseException {
        //displays splash screen
        welcome();
        start();
    }

    private void start() throws IOException, ParseException {
        // possible start tutorial here where player prompted to look, get, and move
        // standard gave play starts after tutorial portion ends
        tutorial = false;

        while (!gameOver) {

            showStatus();
            System.out.printf(">");
            String playerCommand = myObj.nextLine(); //changed variable name from "command" to "playerCommand" for better readability

            //if player inputs "quit" it will break out of the while loop and exit the game----
            if (playerCommand.equals("quit")) {
                break;
            }

            if (playerCommand.equals("restart")) {
                currentRoom = "basement";
                inventory.clear();
                start();
            }
            parser.playerInput(playerCommand);
        }
    }

    private void getCommands() {
        // help should call this method
        System.out.println("go [direction]\nget [item]\nlook [item]\nhelp (allows you to view in game commands)");
    }

    private void welcome() throws IOException {
        txtFileReader("/title.txt");
        //read from txt later
        System.out.println("You awake to find yourself in a twisted escape game.\n Can you gather all the clues and escape with your life in tact before time runs out?");
        System.out.println("---------------------");
        getCommands();
    }

    public static void playerActions(String verb, String noun, JSONObject room, JSONObject roomsObj, JSONArray synonymObj, JSONArray validItems) {
        JSONArray verbObj1 = (JSONArray) synonymObj.get(0);
        JSONArray verbObj2 = (JSONArray) synonymObj.get(1);
        JSONArray verbObj3 = (JSONArray) synonymObj.get(2);

        if (verbObj1.contains(verb)) {
            locationChange(noun, room, roomsObj);
        }else if (verbObj2.contains(verb)) {
            getItems(noun, room, validItems);
        }else if (verbObj3.contains(verb)) {
            look(noun, room, validItems);
        }
    }


    private static void getItems(String noun, JSONObject room, JSONArray validItems) {
        JSONObject items = (JSONObject) room.get("item");
        if (validItems.contains(noun) && items.containsKey(noun)) {
            System.out.println(noun + " picked up");
            inventory.add(noun);
        }
        else {
            System.out.println("There is no item in this room");
        }
    }

    private static void locationChange(String noun, JSONObject room, JSONObject roomsObj) {
        if (noun.split(" ").length == 2) {;
            if (roomsObj.containsKey(noun)) {
                currentRoom = noun;
            }
            else {
                System.out.println(noun + " is not a known location");
            }
        }
        else if (room.containsKey(noun)) {
            currentRoom = (String) room.get(noun);
        }
        else {
            System.out.println("You cannot go that way");
        }
    }

    private static void look(String noun, JSONObject room, JSONArray validItems) {
        if (noun.equals("here")) {
            System.out.println(room.get("description"));
        }
        if(validItems.contains(noun) && room.containsKey("item")){
            JSONObject items = (JSONObject) room.get("item");
            JSONObject item = (JSONObject) items.get(noun);
            System.out.println(item.get("description"));
        }
    }

    private static void showStatus () throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject roomsObj = (JSONObject) parser.parse(new InputStreamReader(App.class.getResourceAsStream("/rooms.json")));
        JSONObject room = (JSONObject) roomsObj.get(currentRoom);


        System.out.println("---------------------------");
        System.out.println("You are in the " + currentRoom);
        System.out.println("Inventory:" + inventory);
        if (room.containsKey("item")) {
            JSONObject items = (JSONObject) room.get("item");
            Set<String> roomItems = items.keySet();
            System.out.println("You see a " + roomItems);
        }
        System.out.println("-----------------------------");
    }

    private static void txtFileReader(String filename) throws IOException {
        try (var in = App.class.getResourceAsStream(filename)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            while ( scanner.hasNextLine() ){
                System.out.println("\u001B[31m " + scanner.nextLine() + "\u001B[37m");
            }
        } catch (IOException e) {
            throw new RuntimeException("Uncaught", e);
        }
    }
}
