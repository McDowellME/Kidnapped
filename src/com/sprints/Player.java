package com.sprints;

import com.apps.util.Console;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // ******** Class Singleton **********
    private static Player player = null;

    // ******** Fields **********
    private String currentRoom = "basement";
    private int health = 100;
    private List<String> inventory = new ArrayList<>();
    private boolean itemEquipped = false;

    // ******** Business Methods **********
    /* we do not want to instantiate multiple.
    static allows us to use through entire app where needed.*/
    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    // ******** Business Methods **********
    /* takes in all commands along with current room info, rooms.json info,
    synonyms, and valid items */
    public void playerActions(String verb, String noun, JSONObject room, JSONObject roomsObj, JSONArray synonymObj, JSONArray validItems) {
        // separate synonyms.json at indexes
        JSONArray verbObj1 = (JSONArray) synonymObj.get(0); // go
        JSONArray verbObj2 = (JSONArray) synonymObj.get(1); // get
        JSONArray verbObj3 = (JSONArray) synonymObj.get(2); // look
        JSONArray verbObj4 = (JSONArray) synonymObj.get(3); // equip
        JSONArray verbObj5 = (JSONArray) synonymObj.get(4); // drop

        // pass to function depending on which synonym array verb belongs to
        if (verbObj1.contains(verb)) {
            locationChange(noun, room, roomsObj);
        }else if (verbObj2.contains(verb)) {
            getItems(noun, room, validItems);
        }else if (verbObj3.contains(verb)) {
            look(noun, room, validItems);
        }else if (verbObj4.contains(verb)) {
            equip(noun, room, validItems);
        }else if (verbObj5.contains(verb)) {
            dropItems(noun, room, validItems);
        }
    }

    // pick up items
    void getItems(String noun, JSONObject room, JSONArray validItems) {
        JSONObject items = (JSONObject) room.get("item");
        if (validItems.contains(noun) && items.containsKey(noun)) {
            System.out.println(noun + " picked up");
            inventory.add(noun);
            items.remove(noun);
        }

        if (!items.containsKey(noun)){

        }
//        else {
//            System.out.println("There is no item in this room");
//        }
    }

    private void dropItems(String noun, JSONObject room, JSONArray validItems) {
        JSONObject items = (JSONObject) room.get("item");
        JSONObject itemDescription = (JSONObject) items.get(noun);
        if (validItems.contains(noun) && !items.containsKey(noun)) {
            System.out.println(noun + " dropped");
            inventory.remove(noun);
            items.put(noun, itemDescription);
        }
        if (noun.equals("torch")) {
            itemEquipped = false;
        }

//        if (!items.containsKey(noun)){
//
//        }
//        else {
//            System.out.println("There is no item in this room");
//        }
    }

    void equip(String noun, JSONObject room, JSONArray validItems) {
        boolean hasItem = false;
        JSONObject items = (JSONObject) room.get("item");
        JSONObject equipment = (JSONObject) items.get("torch");
        if (!inventory.contains("torch")) {
            System.out.println("You must get the torch to use it!");
        }
        hasItem = true;
        if (hasItem) {
            System.out.println("Torch equipped");
            itemEquipped = true;
        }
    }


    // change player location
    void locationChange(String noun, JSONObject room, JSONObject roomsObj) {
        if (noun.split(" ").length == 2) {;
            if (roomsObj.containsKey(noun)) {
               setCurrentRoom(noun);
            }
            else {
                System.out.println(noun + " is not a known location");
            }
        }
        else if (room.containsKey(noun)) {
            setCurrentRoom((String) room.get(noun));
        }
        else {
            System.out.println("You cannot go that way");
        }
    }

    // look at room and items in room
    private void look(String noun, JSONObject room, JSONArray validItems) {
        if (noun.equals("here") && itemEquipped) {
            System.out.println(room.get("description"));
        }
        else {
            System.out.println("Too dark to see, you need some light");
        }
        if(validItems.contains(noun) && room.containsKey("item")){
            JSONObject items = (JSONObject) room.get("item");
            JSONObject item = (JSONObject) items.get(noun);
            System.out.println(item.get("description"));
        }
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public boolean isItemEquipped() {
        return itemEquipped;
    }
}