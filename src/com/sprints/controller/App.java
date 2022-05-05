package com.sprints.controller;

import java.io.IOException;
import java.text.ParseException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import com.sprints.Game;

public class App {
    // ******** Business Methods **********
    // call Game start method to kick off game
    public void execute() throws IOException, ParseException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        Game.getInstance().start();
    }

}
