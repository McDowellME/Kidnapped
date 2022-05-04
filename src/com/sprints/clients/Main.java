package com.sprints.clients;

import com.sprints.controller.App;
import org.json.simple.parser.ParseException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException, ParseException, InterruptedException, java.text.ParseException, UnsupportedAudioFileException, LineUnavailableException {
        App app = new App();
        app.execute();
    }
}