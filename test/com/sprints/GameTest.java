package com.sprints;

import org.junit.Test;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void testPlayerWinGame_ifInventoryHasBookIt() throws LineUnavailableException, UnsupportedAudioFileException, IOException, ParseException, InterruptedException {
        Player.getInstance().getInventory().add("it");
        Game.getInstance().start();
        assertTrue(Game.getInstance().isGameOver());
    }
}