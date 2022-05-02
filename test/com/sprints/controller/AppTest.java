package com.sprints.controller;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.apps.util.Console;
import com.sprints.Player;
import com.sprints.TextParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.sprints.OurJSONParser;

import static org.junit.Assert.*;

public class AppTest {
    App testApp = new App();

    @Test
    public void testRestartShouldSetDefaultValues() throws IOException, ParseException, InterruptedException {
        Player p1 = Player.getInstance();
        p1.setCurrentRoom("kitchen");
        testApp.restart();
        assertEquals("basement", Player.getInstance().getCurrentRoom());
        assertEquals(0, Player.getInstance().getInventory().size());
    }

    @Test
    public void testQuitShouldSetGameOverTrue() throws IOException, ParseException, InterruptedException {
        testApp.quit();
        assertTrue(testApp.isGameOver());
    }

}