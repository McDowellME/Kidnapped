package com.sprints;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
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
    public void playerInput(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
    public List<String> commandTokenizer(String in) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(in);
        // as long as tokenizer has more tokens we will add them to tokens list
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }

    // separate verbs and nouns, passing them to JSON command parser
    public void parseInput(List<String> inputCommand) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String verb;
        String noun;
        List<String> command = new ArrayList<>();
        List<String> validSingleWords = new ArrayList<>(Arrays.asList("quit", "q", "restart", "no", "yes", "y", "n", "help", "mcdhapwt123", "mute", "play", "volume-up", "volume-down"));

        if(inputCommand.size()==0){
            return;
        }
        if(inputCommand.size() ==1 && inputCommand.get(0).equals("godmode")){
            command.add(inputCommand.get(0));
            return;
        }
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
        OurJSONParser.commandParser(command);
    }
}
