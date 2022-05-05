package com.sprints;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class TextParser {
    // ********** Fields **********
    private List<String> inputCommand;

    // ******** Business Methods **********
    /* checks input and passes to Tokenizer to break apart, then pass to parseInput
    method to separate verb and noun */
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

    // split input into tokens
    private List<String> commandTokenizer(String in) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(in);
        // as long as tokenizer has more tokens we will add them to tokens list
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    // separate verbs and nouns, passing them to JSON command parser
    private void parseInput(List<String> inputCommand) {
        String verb;
        String noun;
        List<String> command = new ArrayList<>();
        List<String> validSingleWords = new ArrayList<>(Arrays.asList("quit", "q", "restart", "no", "yes", "y", "n", "help", "mcdhapwt123"));

        if (inputCommand.size() == 1 && validSingleWords.contains(inputCommand.get(0))) {
            String oneWordCommand = inputCommand.get(0);
            command.add(oneWordCommand);
            return;
        }
        else if (inputCommand.size() == 1 && !validSingleWords.contains(inputCommand.get(0))) {
            System.out.println("Please clarify command");
            return;
        }
        else if (inputCommand.size() >3) {
            System.out.println("Please enter a valid 2 or 3 word command. Ex: [go north, go west hall, look room]");
            return;
        }
        else if (inputCommand.size() > 2) {
            verb = inputCommand.get(0);
            noun = inputCommand.get(1) + " " + inputCommand.get(2);
            command.add(verb);
            command.add(noun);
        }
        else {
            verb = inputCommand.get(0);
            noun = inputCommand.get(1);
            command.add(verb);
            command.add(noun);
        }
        OurJSONParser.instantiate().commandParser(command);
    }
}
