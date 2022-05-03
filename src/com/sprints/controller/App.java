package com.sprints.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import com.apps.util.Console;
import com.apps.util.Console;
import com.sprints.Player;
import com.sprints.TextParser;
import com.sprints.TimeElapsed;
import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
import com.sprints.OurJSONParser;


public class App {
    // ******** Fields **********
    private boolean gameOver = false;
    private TextParser parser = new TextParser();
    // instantiate scanner to read console input
    Scanner myObj = new Scanner(System.in);

    // ******** Business Methods **********
    // hold all methods used to run app
    public void execute() throws IOException, ParseException, InterruptedException {
        welcome();
        start();
    }

    // starts app and holds game run logic
    private void start() throws IOException, ParseException, InterruptedException {
        while (!gameOver) {
            showStatus();

            System.out.printf(">");
            String playerCommand = myObj.nextLine();

            //if player inputs "quit" it will break out of the while loop and exit the game----
            if ("quit".equals(playerCommand) || ("q".equals(playerCommand))) {
                quit();
            }

            if ("restart".equals(playerCommand)) {
                restart();
            }
            //Clear function coming from external jar
            Console.clear();
            parser.playerInput(playerCommand);
        }
    }

    // shows available commands
    private void getCommands() {
        // help should call this method
        System.out.println("go [direction]\nget [item]\nlook [item]\nhelp (allows you to view in game commands)");
    }

    // welcomes to game by displaying ascii and break description of game
    private void welcome() throws IOException {
        txtFileReader("/title.txt");
        //read from txt later
        System.out.println("You awake to find yourself in a twisted escape game.\n Can you gather all the clues and escape with your life in tact before time runs out?");
        System.out.println("---------------------");
        getCommands();
    }

    // restart our game
    void restart() throws IOException, ParseException, InterruptedException {
        System.out.println("Restarting game...");
        TimeUnit.SECONDS.sleep(2);
        Player.getInstance().setCurrentRoom("basement");
        Player.getInstance().getInventory().clear();
        start();
    }
    void quit() throws IOException, ParseException, InterruptedException {
        System.out.println("Are you sure you want to quit?");
        String q = myObj.nextLine();
        if ("yes".equals(q)) {
            System.out.println("quiting the game...");
            TimeUnit.SECONDS.sleep(2);
            gameOver = true;
            System.exit(0);
        }
        if ("no".equals(q)) {
            showStatus();
        }
    }

    // used to display status (current room, inventory, room description, etc)
    private static void showStatus () throws IOException, ParseException {
//        JSONParser parser = new JSONParser();
//        JSONObject roomsObj = (JSONObject) parser.parse(new InputStreamReader(App.class.getResourceAsStream("/rooms.json")));
        JSONObject roomObj = OurJSONParser.getRoomsJSON();
        JSONObject room = (JSONObject) roomObj.get(Player.getInstance().getCurrentRoom());
        JSONObject items = (JSONObject) room.get("item");

        System.out.println("---------------------------");
        System.out.println("You are in the " + Player.getInstance().getCurrentRoom());
        if (Player.getInstance().getCurrentRoom() == "basement") {
            txtFileReader("/basement.txt");
        }

        if (Player.getInstance().isItemEquipped()) {
            switch (Player.getInstance().getCurrentRoom()){
//                case "basement" :
//                    txtFileReader("/basement.txt");
//                    break;
                case "parlor" :
                    txtFileReader("/parlor.txt");
                    break;
                case "east hall":
                case "west hall":
                    txtFileReader("/hallway.txt");
                    break;
                case "kitchen":
                    txtFileReader("/kitchen.txt");
                    break;
            }
            System.out.println(room.get("description"));
        } else {
            System.out.println("Too dark to see everything here, you need some light equipped");
        }

        System.out.println("Inventory:" + Player.getInstance().getInventory());
        if (room.containsKey("item")) {
//            JSONObject items = (JSONObject) room.get("it);
            Set<String> roomItems = items.keySet();
            System.out.println("You see a " + roomItems);
        }
        System.out.println(TimeElapsed.getInstance().getTime());
        System.out.println("-----------------------------");

    }

    // read text files
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

    public boolean isGameOver() {
        return gameOver;
    }
}
