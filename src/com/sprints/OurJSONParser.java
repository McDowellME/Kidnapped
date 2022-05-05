package com.sprints;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    private static JSONObject roomsJSON;

    private JSONObject commandJSON;
    private JSONArray synJSON;
    private JSONObject inventoryJSON;
    private List<String> commands;

    // ******** Singleton Instantiation **********
    /* we do not want to instantiate multiple.
    static allows us to use through entire app where needed.*/
    static OurJSONParser instantiate() {
        if (ourParser == null) {
            try {
                ourParser = new OurJSONParser();
            }
            catch (IOException | ParseException e) {
                System.out.println(e);
                System.exit(0);
            }
        }
        return ourParser;
    }

    static JSONObject getRoomsJSON() {
        return roomsJSON;
    }

    //******** CTOR **********
    // read JSON info as streams and parse
    private OurJSONParser() throws IOException, ParseException {
        commandJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(COMMANDS))));
        roomsJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(ROOMS))));
        inventoryJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(INVENTORY))));
        synJSON = (JSONArray) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(SYN))));
        commands = new ArrayList<>();
    }

    // ******** Business Methods **********
    void commandParser(List<String> command) {
        String verb;
        String noun;

        // clear commands list to ensure there's only ever a singular set of commands present
        commands.clear();
        // access out valid verbs, nouns, and items arrays inside commands.json
        JSONArray verbs = (JSONArray) commandJSON.get("verbs");
        JSONArray nouns = (JSONArray) commandJSON.get("nouns");
        JSONArray items = (JSONArray) commandJSON.get("items");
        JSONObject room = (JSONObject) roomsJSON.get(Player.getInstance().getCurrentRoom());
        JSONObject westHall = (JSONObject) roomsJSON.get("west hall");
        JSONObject hallItems = (JSONObject) westHall.get("item");
        JSONObject bookcase = (JSONObject) hallItems.get("bookcase");
        JSONObject books = (JSONObject) bookcase.get("books");

        if (command.size() == 1) {
            commands.add(command.get(0));
        }

        else if (!verbs.contains(command.get(0)) && !nouns.contains(command.get(1))) {
            System.out.println(command.get(0) + " " + command.get(1) + " is not a valid command");
        }
        else {
            commands.add(command.get(0));
            commands.add(command.get(1));
            Player.getInstance().playerActions(commands, room, roomsJSON, synJSON, items, inventoryJSON, books);
        }

    }
}