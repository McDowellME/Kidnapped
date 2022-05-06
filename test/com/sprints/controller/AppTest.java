package com.sprints.controller;

import com.sprints.Game;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.apps.util.Console;
import com.sprints.TextParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.sprints.OurJSONParser;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static org.junit.Assert.*;

public class AppTest {

    // restart is private method, switch to package private to and remove prompt to test
//    @Test
//    public void testRestartShouldSetDefaultValues() throws IOException, ParseException, InterruptedException, java.text.ParseException, UnsupportedAudioFileException, LineUnavailableException {
//        Player p1 = Player.getInstance();
//        p1.setCurrentRoom("kitchen");
//        Game.getInstance().restart();
//        assertEquals("basement", Player.getInstance().getCurrentRoom());
//        assertEquals(0, Player.getInstance().getInventory().size());
//    }

    // quit is private method, switch to package private to and remove prompt to test
//    @Test
//    public void testQuitShouldSetGameOverTrue() throws IOException, ParseException, InterruptedException, java.text.ParseException, LineUnavailableException {
//        Game.getInstance().quit();
//        assertTrue(Game.getInstance().isGameOver());
//    }

}