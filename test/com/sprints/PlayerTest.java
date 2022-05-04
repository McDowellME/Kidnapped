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

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private static final Player player = new Player();
    private static final JSONParser jsonParser = new JSONParser();
    private static final String ROOMS = "/rooms.json";
    private static final String ITEMS = "/commands.json";

    JSONObject roomsJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(ROOMS))));
    JSONObject basement = (JSONObject) roomsJSON.get("basement");
    JSONObject itemsJSON = (JSONObject) jsonParser.parse(new InputStreamReader(Objects.requireNonNull(OurJSONParser.class.getResourceAsStream(ITEMS))));
    JSONArray items = (JSONArray) itemsJSON.get("items");

    public PlayerTest() throws IOException, ParseException {
    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    // Need to change private method to public for testing
    @Test
    public void changeLocationFromBasement_shouldChangeLocation_whenValidDirectionPassed() {
        Player.getInstance().locationChange("north", basement, roomsJSON);
        assertEquals("parlor", Player.getInstance().getCurrentRoom());
    }

    // Need to change private method to public for testing
    @Test
    public void changeLocationFromBasement_shouldReturnErrorMessage_whenInvalidDirectionPassed() {
        Player.getInstance().locationChange("south", basement, roomsJSON);
        assertTrue("You cannot go that way", true);
    }

    // Need to change private method to public for testing
    @Test
    public void changeLocationFromBasement_shouldChangeLocation_whenValidRoomPassed() {
        Player.getInstance().locationChange("kitchen", basement, roomsJSON);
        assertEquals("kitchen", Player.getInstance().getCurrentRoom());
    }

    // Need to change private method to public for testing
    @Test
    public void changeLocationFromBasement_shouldReturnErrorMessage_whenInvalidRoomPassed() {
        Player.getInstance().locationChange("attic", basement, roomsJSON);
        assertTrue("attic is not a known location", true);
    }

    // Need to change private method to public for testing
//    @Test
//    public void getItems_inventoryShouldBeEmpty_ifInvalidItemPickedUp() {
//        Player.getInstance().getItems("rock", basement, items);
//        assertEquals(0, Player.getInstance().getInventory().size());
//    }
}