package com.sprints.gui;

import com.sprints.Game;
import com.sprints.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.SortedMap;

import static org.junit.Assert.*;

public class GameFrameTest {

    @Before
    public void setUp() throws Exception {
        new Frame();
        GameFrame.setCountDown();
        GameFrame.timer.start();
    }

    @Test
    public void setCountDown() {
    }

    @Test
    public void actionPerformed() {

    }

    @Test
    public void checkWin_getIT() throws Exception {
        Player.getInstance().setCurrentRoom("west hall");
        Player.getInstance().getItems("it");
        GameFrame.setInventory();
        assertTrue(GameFrame.checkWin());
    }

    @Test
    public void checkWin_wrongBook() throws Exception {
        Player.getInstance().setCurrentRoom("west hall");
        Player.getInstance().getItems("something wicked");
        GameFrame.setInventory();
        assertFalse(GameFrame.checkWin());
    }

    @Test
    public void checkWin_noBook() throws Exception {
        GameFrame.setInventory();
        assertFalse(GameFrame.checkWin());
    }



    @Test
    public void countDown() {

    }
}