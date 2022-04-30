package com.sprints;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TextParser {
    private JSONArray validVerbs;
    private JSONArray validNouns;
    private JSONArray validItems;
    private List<String> inputCommand;
    private List<String> validCommand;
    private List<JSONArray> commands;

    public TextParser() {
        validCommand = new ArrayList<>();
    }

    public List<String> getValidCommand() {
        return validCommand;
    }

    public void playerInput(String input) {
        String in = input.trim().toLowerCase();

        if ("".equals(input)) {
            System.out.println("What would you like to do?");
        }
        else {
            inputCommand = commandTokenizer(in);
            parseInput(inputCommand);
        }
    }

    private List<String> commandTokenizer(String in) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(in);

        // as long as tokenizer has more tokens we will add them to tokens list
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        return tokens;
    }

    private void parseInput(List<String> inputCommand) {
        String verb;
        String noun;

        if (inputCommand.size() >3) {
            System.out.println("Please enter a valid 2 or 3 word command. Ex: [go north, go west hall, look room]");
        }
        if (inputCommand.size() > 2) {
            verb = inputCommand.get(0);
            noun = inputCommand.get(1) + " " + inputCommand.get(2);
        }
        else {
            verb = inputCommand.get(0);
            noun = inputCommand.get(1);
        }
        OurJSONParser.instantiate().commandParser(noun, verb);
    }
}
