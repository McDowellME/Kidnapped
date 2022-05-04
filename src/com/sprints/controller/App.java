package com.sprints.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        pressEnterToContinue();
        Console.clear();
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
            if ("help".equals(playerCommand)) {
                getCommands();
            }
            //Clear function coming from external jar
            parser.playerInput(playerCommand);
            Thread.sleep(1000);
            Console.clear();
        }
    }

    // shows available commands
    private void getCommands() {
        // help should call this method
        System.out.println("go [direction]\nget [item]\nlook [item]\nequip [item]\nhelp (allows you to view in game commands)");
    }

    // welcomes to game by displaying ascii and break description of game
    private void welcome() throws IOException, InterruptedException {
        txtFileReader("/title.txt");
        pressEnterToContinue();
        Console.clear();
        //read from txt later
        String description = "You awake to find yourself in a twisted escape game. Can you gather all the clues and escape\nwith your life in tact before time runs out?";
        printWithDelays(description);
        Console.blankLines(2);
        System.out.println("-----------------------------");
        getCommands();
    }

    // restart our game
    void restart() throws IOException, ParseException, InterruptedException {
        System.out.println("Are you sure you want to restart?");
        String q = myObj.nextLine();
        if ("yes".equals(q) || "y".equals(q)) {
            System.out.println("Restarting game...");
            TimeUnit.SECONDS.sleep(2);
            Player.getInstance().setCurrentRoom("basement");
            Player.getInstance().getInventory().clear();
            Console.clear();
            execute();
        }
        else {
            showStatus();
        }
    }

    void quit() throws IOException, ParseException, InterruptedException {
        System.out.println("Are you sure you want to quit?");
        String q = myObj.nextLine();
        if ("yes".equals(q) || "y".equals(q)) {
            System.out.println("quiting the game...");
            TimeUnit.SECONDS.sleep(2);
            gameOver = true;
            System.exit(0);
        }
        else {
            showStatus();
        }
    }

    // used to display status (current room, inventory, room description, etc)
    private static void showStatus () throws IOException, ParseException, InterruptedException {
        JSONObject roomObj = OurJSONParser.instantiate().getRoomsJSON();
        JSONObject room = (JSONObject) roomObj.get(Player.getInstance().getCurrentRoom());
        JSONObject items = (JSONObject) room.get("item");

        if (Player.getInstance().isItemEquipped()) {
            switch (Player.getInstance().getCurrentRoom()){
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
                default:
                    txtFileReader("/basement.txt");
            }

            System.out.println("---------------------------");
            System.out.println("You are in the " + Player.getInstance().getCurrentRoom());
            Console.blankLines(1);
            System.out.println(room.get("description"));
            Console.blankLines(1);
        } else {
            System.out.println("Too dark to see everything here, you need some light");
            Console.blankLines(2);
        }
        if (room.containsKey("item")) {
            Set<String> roomItems = items.keySet();
            System.out.println("You notice: " + roomItems);
            Console.blankLines(1);
        }
        System.out.println("Inventory:" + Player.getInstance().getInventory());
        System.out.println(TimeElapsed.getTime());
        System.out.println("-----------------------------");
    }

    // read text files
    private static void txtFileReader(String filename) throws IOException {
        try (var in = App.class.getResourceAsStream(filename)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            while ( scanner.hasNextLine() ){
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException("Uncaught", e);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private static void printWithDelays(String data)
            throws InterruptedException {
        for (char ch:data.toCharArray()) {
            System.out.print(ch);
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }

    private void pressEnterToContinue() {
        Console.blankLines(1);
        System.out.println("Press ENTER to continue");
        try {
            System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
