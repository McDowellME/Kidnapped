package com.sprints;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OurJSONParser {

    // ******** Class Singleton **********
    private static OurJSONParser ourParser = null;

    // ******** Constants **********
    private static final String COMMANDS = "/commands.json";
    private static final String ROOMS = "/rooms.json";
    private static final String INVENTORY = "/inventory.json";
    private static final String SYN = "/synonyms.json";


    // ******** Fields **********
    private static JSONParser jsonParser = new JSONParser();

    private static JSONObject commandJSON;
    private static JSONObject roomsJSON;
    private static JSONObject inventoryJSON;
    private static JSONArray synJSON;

    static {
        try {
            commandJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(COMMANDS))));
            roomsJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(ROOMS))));
            inventoryJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(INVENTORY))));
            synJSON = (JSONArray) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(SYN))));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private static JSONObject westHall = (JSONObject) roomsJSON.get("west hall");
    private static JSONObject hallItems = (JSONObject) westHall.get("item");
    private static JSONObject bookcase = (JSONObject) hallItems.get("bookcase");
    private static JSONObject books = (JSONObject) bookcase.get("books");
    private static List<String> commands = new ArrayList<>();


    static JSONArray verbs = (JSONArray) commandJSON.get("verbs");
    static JSONArray nouns = (JSONArray) commandJSON.get("nouns");
    static JSONArray items = (JSONArray) commandJSON.get("items");
    static JSONObject room = (JSONObject) roomsJSON.get(Player.getInstance().getCurrentRoom());
    static JSONObject roomItems = (JSONObject) room.get("item");
    static JSONObject clueHolder = null;


    private JSONObject getCommandJSON(){
        return commandJSON;
    }
    //******** CTOR **********
    // read JSON info as streams and parse
    private OurJSONParser() throws IOException, ParseException {

    }

    // ******** Singleton Instantiation **********
    /* we do not want to instantiate multiple.
    static allows us to use through entire app where needed.*/
    public static OurJSONParser instantiate() {
        if (ourParser == null) {
            try {
                ourParser = new OurJSONParser();
            }
            catch (IOException | ParseException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        return ourParser;
    }


    // ******** Business Methods **********
    public static void commandParser(List<String> command) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        // clear commands list to ensure there's only ever a singular set of commands present
        commands.clear();
        // access out valid verbs, nouns, and items arrays inside commands.json


        if (command.size() == 1) {
            commands.add(command.get(0));
        }

        else if (!verbs.contains(command.get(0)) && !nouns.contains(command.get(1))) {
            System.out.println(command.get(0) + " " + command.get(1) + " is not a valid command");
        }
        else {
            commands.add(command.get(0));
            commands.add(command.get(1));
            Player.getInstance().playerActions(commands);
        }
    }

    public static JSONObject getRoomsJSON() {
        return roomsJSON;
    }

    public static JSONObject getInventoryJSON() {
        return inventoryJSON;
    }

    public static JSONArray getSynJSON() {
        return synJSON;
    }

    public static JSONObject getWestHall() {
        return westHall;
    }

    public static JSONObject getHallItems() {
        return hallItems;
    }

    public static JSONObject getBookcase() {
        return bookcase;
    }

    public static JSONObject getBooks() {
        return books;
    }

    public static List<String> getCommands() {
        return commands;
    }

    public static JSONArray getVerbs() {
        return verbs;
    }

    public static JSONArray getNouns() {
        return nouns;
    }

    public static JSONArray getItems() {
        return items;
    }

    public static JSONObject getRoom() {
        return room;
    }

    public static JSONObject getRoomItems() {
        return roomItems;
    }

    public static JSONObject getClueHolder() {
        return clueHolder;
    }

    public static void setClueHolder(JSONObject clueHolder) {
        OurJSONParser.clueHolder = clueHolder;
    }

    public static void setRoom(JSONObject room) {
        OurJSONParser.room = room;
    }

    public static void setCommands(List<String> commands) {
        OurJSONParser.commands = commands;
    }
}


