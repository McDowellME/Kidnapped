package com.sprints;

import com.apps.util.Console;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    // ******** Class Singleton **********
    private static Player player = null;

    // ******** Fields **********
    private String currentRoom = "basement";
    private List<String> inventory = new ArrayList<>();
    private List<String> cluesFound = new ArrayList<>();
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
    public void playerActions(List<String> commands, JSONObject room, JSONObject roomsObj, JSONArray synonymObj, JSONArray validItems, JSONObject inventoryObj) {
        // separate synonyms.json at indexes
        JSONArray verbObj1 = (JSONArray) synonymObj.get(0); // go
        JSONArray verbObj2 = (JSONArray) synonymObj.get(1); // get
        JSONArray verbObj3 = (JSONArray) synonymObj.get(2); // look
        JSONArray verbObj4 = (JSONArray) synonymObj.get(3); // equip
        JSONArray verbObj5 = (JSONArray) synonymObj.get(4); // drop

        // pass to function depending on which synonym array verb belongs to
        if (verbObj1.contains(commands.get(0))) {
            locationChange(commands.get(1), room, roomsObj);
        }else if (verbObj2.contains(commands.get(0))) {
            getItems(commands.get(1), room, validItems, inventoryObj);
        }else if (verbObj3.contains(commands.get(0))) {
            look(commands.get(1), room, roomsObj, validItems, inventoryObj);
        }else if (verbObj4.contains(commands.get(0))) {
            equip(room);
        }else if (verbObj5.contains(commands.get(0))) {
            dropItems(commands.get(1), room, validItems);
        }
    }

    // pick up items
    void getItems(String noun, JSONObject room, JSONArray validItems, JSONObject inventoryObj) {
        JSONObject items = (JSONObject) room.get("item");
        ArrayList<String> keys = new ArrayList<>(items.keySet());
        if (validItems.contains(noun) && inventoryObj.containsKey(noun)) {
            System.out.println(noun + " picked up");
            inventory.add(noun);
            items.remove(noun);
            return;
        }
        else {
            System.out.println("You cannot pick up " + noun);
        }

        if (keys.contains("portrait") || keys.contains("vase") || keys.contains("cabinets") || keys.contains("bookcase")) {
            for (int i = 0; i < keys.size(); i++) {
                JSONObject item = (JSONObject) items.get(keys.get(i));
                if (inventoryObj.containsKey(noun) && item.containsKey("clue")) {
                    JSONObject clueObj = (JSONObject) item.get("clue");
                    String clue = (String) clueObj.get("name");
                    cluesFound.add(clue);
                    inventory.add(clue);
                    item.remove("clue");
                }
            }
        }
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

    }

    void equip(JSONObject room) {
        boolean hasItem = false;
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
        if(roomsObj.containsKey(noun)) {
            setCurrentRoom(noun);
        }
        else if (room.containsKey(noun) || roomsObj.containsKey(noun)) {
            setCurrentRoom((String) room.get(noun));
        }
        else {
            System.out.println("You cannot go that way");
        }
    }

    // look at room and items in room
    private void look(String noun, JSONObject room, JSONObject roomsObj, JSONArray validItems, JSONObject inventoryObj) {
        JSONObject items = (JSONObject) room.get("item");
        ArrayList<String> keys = new ArrayList<>(items.keySet());
        ArrayList<String> roomKeys = new ArrayList<>(roomsObj.keySet());

        if (roomKeys.contains(noun) && itemEquipped) {
            System.out.println(room.get("description"));
            return;
        }
        else if (inventory.contains(noun) && inventoryObj.containsKey(noun)) {
            JSONObject itemDescription = (JSONObject) inventoryObj.get(noun);
            System.out.println(itemDescription.get("description"));
            return;
        }
        else if (validItems.contains(noun) && room.containsKey("item") && itemEquipped){
            JSONObject item = (JSONObject) items.get(noun);
            System.out.println(item.get("description"));
            return;
        }
        else {
            System.out.println("Too dark to see, you need some light");
        }

//        if (keys.contains("portrait") || keys.contains("vase") || keys.contains("cabinets") || keys.contains("bookcase") ) {
//            for (int i = 0; i < keys.size(); i++) {
//                JSONObject item = (JSONObject) items.get(keys.get(i));
//                if (!item.containsKey("clue")) {
//                    System.out.println(item.get("description2"));
//                }
//            }
//        }
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


    public boolean isItemEquipped() {
        return itemEquipped;
    }
}