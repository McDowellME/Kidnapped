package com.sprints.gui;

import com.sprints.OurJSONParser;
import com.sprints.Player;
import com.sprints.TextParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

class GameFrame extends JPanel implements ActionListener {
    // Final Constants
    private static final String BACKGROUND = "images/background.jpg";
    private static final String UNLIT = "images/dark.png";

    static ImageIcon unlitRoom;
    private static String firstLine = "Too dark to see everything here, you need some light";
    static DefaultListModel<String> model = new DefaultListModel<>();
    static DefaultListModel<String> itemModel = new DefaultListModel<>();
    TextParser textParser = new TextParser();
    static Timer timer;
    private static int seconds, minutes;
    private static String dblSeconds, dblMinutes;
    private static DecimalFormat decimalFormat = new DecimalFormat("00");
    private static JTextField textField;
    JScrollPane scrollPane;
    static JList inventoryList, itemList;
    private static JTextArea textArea, responseArea;
    private static JLabel inventoryLabel, locationLabel, countDownLabel, background, itemLabel;
    JButton enterBtn, northBtn, eastBtn, southBtn, westBtn, helpBtn, backBtn, audBtn;
    // Bookcase imgButtons
    private static JButton itBtn, wickedBtn, frankBtn, reprieveBtn, lightBtn;
    // Basement imgButtons
    private static JButton torchBtn, noteBtn, needleBtn;
    JSlider audSlider;
    Font txtFont = new Font("Times New Roman", Font.BOLD, 16);
    Font inventoryFont = new Font("Times New Roman", Font.BOLD, 20);
    private static boolean isHelpDisplayed = false;     // music is ON by default
    private static boolean gameEnd = false;

    private static final String resetVariable = firstLine;

    public GameFrame() throws IOException {
        //region params

        //region Dimensions of the frame
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);
        //endregion

        //region Location image
        locationLabel = new JLabel();
        locationLabel.setBounds (136, 20, 822, 400);
        // set with start location image, changes with location
        ImageIcon locIcon = IconBuilder.locationIcon(UNLIT);
        locationLabel.setIcon(locIcon);
        //endregion

        //region User input
        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.addActionListener( action ); // let me use enter key on keyboard
        textField.setFont(new Font("Calibri", Font.BOLD, 15));
        textField.setBorder(new LineBorder(Color.red));
        textField.setBounds(390,663,300,40);
        //endregion

        //region background
        background = new JLabel();
        background.setBounds(0,0,1094, 732);
        ImageIcon backgroundIcon = IconBuilder.mainIcon(BACKGROUND);
        background.setIcon(backgroundIcon);
        //endregion

        //region Description
        textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(txtFont);
        textArea.setText(firstLine);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(Color.RED));
        scrollPane.setBounds(310,460,500,195);
        //endregion

        //region response area
        responseArea = new JTextArea();
        responseArea.setBackground(Color.WHITE);
        responseArea.setForeground(Color.BLACK);
        responseArea.setFont(txtFont);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);
        responseArea.setBorder(new LineBorder(Color.red));
        responseArea.setBounds(310,427,500,28);
        //endregion

        //region Inventory
        inventoryList = new JList();
        DefaultListCellRenderer inventoryRender = (DefaultListCellRenderer) inventoryList.getCellRenderer();
        inventoryRender.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryList.setBackground(Color.WHITE);
        inventoryList.setForeground(Color.BLACK);
        inventoryList.setFont(txtFont);
        inventoryList.setBorder(new LineBorder(Color.red));
        inventoryList.setBounds(100,495,160,160);
        //endregion

        //region Inventory Label
        inventoryLabel = new JLabel("Inventory");
        inventoryLabel.setForeground(Color.RED);
        inventoryLabel.setFont(inventoryFont);
        inventoryLabel.setBounds(140,474,160,20);
        //endregion

        //region Items
        itemList = new JList();
        DefaultListCellRenderer itemRender = (DefaultListCellRenderer) itemList.getCellRenderer();
        itemRender.setHorizontalAlignment(SwingConstants.CENTER);
        itemList.setBackground(Color.WHITE);
        itemList.setForeground(Color.BLACK);
        itemList.setFont(txtFont);
        itemList.setBorder(new LineBorder(Color.red));
        itemList.setBounds(2,146,134,180);
        //endregion

        //region Items Label
        itemLabel = new JLabel("You See");
        itemLabel.setForeground(Color.RED);
        itemLabel.setFont(inventoryFont);
        itemLabel.setBounds(36,125,134,20);
        //endregion

        //region Enter button
        enterBtn = new JButton("Enter");
        enterBtn.setOpaque(true);
        enterBtn.setForeground(Color.RED);
        enterBtn.setBorderPainted(false);
        enterBtn.setFont(txtFont);
        enterBtn.setFocusPainted(false);
        enterBtn.setBorder(null);
        enterBtn.setContentAreaFilled(false);
        enterBtn.addActionListener(action);
        enterBtn.setBounds (690, 663, 80, 40);
        //endregion

        //region North button
        northBtn = new JButton();
        String north = "images/button_n.png";
        buildUserBtn(northBtn, north, "north",33);
        northBtn.setBounds (935, 455, 33, 33);
        //endregion

        //region East button
        String east  =  "images/button_e.png";
        eastBtn = new JButton();
        buildUserBtn(eastBtn, east, "east", 33);
        eastBtn.setBounds (995, 525, 33, 33);
        //endregion

        //region South button
        String south  =  "images/button_s.png";
        southBtn = new JButton();
        buildUserBtn(southBtn, south, "south", 33);
        southBtn.setBounds (935, 595, 33, 33);
        //endregion

        //region West button
        String west  =  "images/button_w.png";
        westBtn = new JButton();
        buildUserBtn(westBtn, west, "west", 33);
        westBtn.setBounds (865, 525, 33, 33);
        //endregion

        //region Help button
        String help = "images/button_help.png";
        helpBtn = new JButton();
        buildUserBtn(helpBtn, help, "help", 40);
        helpBtn.setBounds (1020, 670, 40, 40);
        //endregion

        //region Back button
        String back = "images/button_back.png";
        backBtn = new JButton();
        buildUserBtn(backBtn, back, "back", 40);
        backBtn.setBounds (820, 620, 40, 40);
        //endregion

        //region light button
        String light = "images/button_light.png";
        lightBtn = new JButton();
        buildUserBtn(lightBtn, light, "light", 33);
        lightBtn.setBounds (935, 525, 33, 33);
        setOff(lightBtn);
        //endregion

        //region Audio button
        String audio  =  "images/play.png";
        audBtn = new JButton();
        buildUserBtn(audBtn, audio, "audToggle", 33);
        audBtn.setBounds (1010, 287, 33, 33);
        //endregion

        //region Audio slider
        audSlider = new JSlider( JSlider.VERTICAL,-40,6,-17);
        audSlider.setOpaque(true);
        audSlider.setBackground(Color.WHITE);
        audSlider.addChangeListener(e -> {
            Audio.currentVolume = audSlider.getValue();
            // Barely audible past -40 so easier to mute at that level
            if(Audio.currentVolume == -40){
                Audio.currentVolume = -80;
            }
            System.out.println(Audio.currentVolume);
            Audio.fc.setValue(Audio.currentVolume);
        });
        audSlider.setBackground(Color.RED);
        audSlider.setBounds (1010, 150, 31, 120);
        //endregion

        //region Timer
        countDownLabel = new JLabel();

        //endregion

        //region basement
        torchBtn = new JButton();
        buildImgBtn(torchBtn,"images/torch.png","torch", 100,100);
        torchBtn.setBounds(300,315,100,100);
        setOn(torchBtn);

        noteBtn = new JButton();
        buildImgBtn(noteBtn,"images/note.png","note", 115,86);
        noteBtn.setBounds(650,150,115,86);

        needleBtn = new JButton();
        buildImgBtn(needleBtn, "images/needle.png","needle",100,100);
        needleBtn.setBounds(500,300,100,100);

        // region books
        //String it = "images/it.jpg";
        JSONObject itObj = (JSONObject) OurJSONParser.getBooks().get("it");
        String it = itObj.get("image").toString();
        itBtn = new JButton();
        buildImgBtn(itBtn,it,"it", 85, 145);
        itBtn.setBounds(300, 185, 150, 255);

        JSONObject wickedObj = (JSONObject) OurJSONParser.getBooks().get("something wicked");
        String wicked = wickedObj.get("image").toString();
        wickedBtn = new JButton();
        buildImgBtn(wickedBtn,wicked,"wicked", 85, 145);
        wickedBtn.setBounds(600, 185, 150, 255);

        JSONObject frankObj = (JSONObject) OurJSONParser.getBooks().get("frankenstein");
        String frank = frankObj.get("image").toString();
        frankBtn = new JButton();
        buildImgBtn(frankBtn,frank,"frank", 85, 145);
        frankBtn.setBounds(300, 10, 150, 255);

        JSONObject reprieveObj = (JSONObject) OurJSONParser.getBooks().get("reprieve");
        String reprieve = reprieveObj.get("image").toString();
        reprieveBtn = new JButton();
        buildImgBtn(reprieveBtn,reprieve,"reprieve", 85, 145);
        reprieveBtn.setBounds(600, 10, 150, 255);
        //end region


        // region Add components
        add(countDownLabel);

        //basement imgBtns
        add(torchBtn);
        add(noteBtn);
        add(needleBtn);

        add(itBtn);
        add(wickedBtn);
        add(frankBtn);
        add(reprieveBtn);

        add(locationLabel);
        add(backBtn);
        add(textField);
        add(scrollPane);
        add(inventoryList);
        add(inventoryLabel);
        add(itemList);
        add(itemLabel);
        add(enterBtn);
        add(northBtn);
        add(eastBtn);
        add(southBtn);
        add(westBtn);
        add(helpBtn);
        add(lightBtn);
        add(audBtn);
        add(audSlider);
        add(responseArea);
        add(background);

        setVisible(true);
        //endregion
        //endregion
        setItems();

    }

    private static void setOn(JButton imgBtn) {
        imgBtn.setVisible(true);
        imgBtn.setEnabled(true);
    }
    private static void setOff(JButton imgBtn) {
        imgBtn.setVisible(false);
        imgBtn.setEnabled(false);
    }

    static void setCountDown(){
        countDownLabel.setText("05:00");
        countDownLabel.setForeground(Color.RED);
        countDownLabel.setBounds(1000, 20, 50, 50);
        countDownLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        seconds = 0;
        minutes = 5;
        countDown();
        timer.start();
    }

    // build a button with Jbutton, img file, same height/width size, and command name
    private void buildUserBtn(JButton btn, String file, String command, int size) throws IOException {
        ImageIcon btnIcon = IconBuilder.buttonIcon(file, size);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(txtFont);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.addActionListener(this);
        btn.setIcon(btnIcon);
        btn.setActionCommand(command);
    }

    // build a book button with Jbutton, img file, same height/width size, and command name
    private void buildImgBtn(JButton btn, String file, String command, int width, int height) throws IOException {
        ImageIcon btnIcon = IconBuilder.imageIcon(file, width, height, Image.SCALE_DEFAULT);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(txtFont);
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.addActionListener(this);
        btn.setIcon(btnIcon);
        btn.setEnabled(false);
        btn.setVisible(false);
        btn.setActionCommand(command);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Player.setPlug("");
            switch (e.getActionCommand()){
                // direction buttons
                case "north":
                    Player.getInstance().playerActions(Arrays.asList("go", "north"));
                    break;
                case "east":
                    Player.getInstance().playerActions(Arrays.asList("go", "east"));
                    break;
                case "south":
                    Player.getInstance().playerActions(Arrays.asList("go", "south"));
                    break;
                case "west":
                    Player.getInstance().playerActions(Arrays.asList("go", "west"));
                    break;
                //end direction buttons
                case "audToggle":
                    Audio.toggleSound(audBtn, audSlider);
                    break;
                case "back":
                    updateAll(); // returns current room description
                    break;
                case "light":
                    if(Player.getInstance().getCurrentInventory().contains("torch") && Player.getInstance().isItemEquipped()){
                        Player.getInstance().setItemEquipped(false);
                    }
                    else Player.getInstance().playerActions(Arrays.asList("use", "torch"));
                    break;
                // book buttons
                case "it":
                    Player.getInstance().playerActions(Arrays.asList("look", "it"));
                    break;
                case "frank":
                    Player.getInstance().playerActions(Arrays.asList("look", "frankenstein"));
                    break;
                case "reprieve":
                    Player.getInstance().playerActions(Arrays.asList("look", "reprieve"));
                    break;
                case "wicked":
                    Player.getInstance().playerActions(Arrays.asList("look", "something wicked"));
                    break;
                // end book buttons
                case "help":
                    if (!isHelpDisplayed) {
                        textArea.read(ResourceReader.readText("/commandsmenu.txt"),null);
                        isHelpDisplayed = true;
                        // clear response area
                        setResponse(); // or
                        // responseArea.setText("");

                    }
                    else {
                        updateAll(); // returns current room description
                        isHelpDisplayed = false;
                    }
                    return;
                case "torch":
                    Player.getInstance().playerActions(Arrays.asList("get", "torch"));
                    break;
                case "note":
                    Player.getInstance().playerActions(Arrays.asList("look", "note"));
                    break;
                case "needle":
                    Player.getInstance().playerActions(Arrays.asList("look", "needle"));
                default:
            }
            updateAll();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // For the key 'enter'
    Action action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Player.setPlug("");
                List<String> input = textParser.commandTokenizer(textField.getText());
                textParser.parseInput(input);
                if(input.get(0).equals("godmode")){
                    timer.stop();
                    countDownLabel.setText("60:00");
                    countDownLabel.setForeground(Color.RED);
                    countDownLabel.setBounds(1000, 20, 50, 50);
                    countDownLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
                    seconds = 0;
                    minutes = 60;
                    countDown();
                    timer.start();
                }
                System.out.println(input);
                updateAll();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    };

    private void updateAll() throws IOException, InterruptedException {
        updateImage();
        setTextArea();
        setInventory();
        setItems();
        setResponse();
        textField.setText("");
    }
    private static void enableBooks() {
        Map<String, JButton> books = new HashMap<>();
        books.put("it", itBtn);
        books.put("reprieve", reprieveBtn);
        books.put("frankenstein", frankBtn);
        books.put("something wicked", wickedBtn);
        Set<String> bookKeys = OurJSONParser.getBooks().keySet();
        for (String book : bookKeys) {
            books.get(book).setEnabled(true);
            books.get(book).setVisible(true);
        }
    }

    private static void disableAllBooks() {
        itBtn.setEnabled(false);
        itBtn.setVisible(false);
        frankBtn.setVisible(false);
        frankBtn.setEnabled(false);
        reprieveBtn.setVisible(false);
        reprieveBtn.setEnabled(false);
        wickedBtn.setVisible(false);
        wickedBtn.setEnabled(false);
    }

    private static void updateImage() throws IOException {
        setOffAll();

        Map<String, JButton> roomItem = new HashMap<>();
        roomItem.put("torch", torchBtn);
        roomItem.put("note", noteBtn);
        roomItem.put("needle", needleBtn);
        Set<String> itemKeys = OurJSONParser.getRoomItems().keySet();

        if(Player.getInstance().getCurrentInventory().contains("torch")) setOn(lightBtn);
        else setOff(lightBtn);

        if(itemKeys.contains("torch") && !Player.getInstance().getCurrentInventory().contains("torch")) setOn(torchBtn);

        if(Player.getInstance().isItemEquipped()){
            for (String item : itemKeys) {
                if(roomItem.containsKey(item)){
                    setOn(roomItem.get(item));
                }
            }
        }
        // if command was to look at item and item has an image, make the location label item image
        if (Player.getIsLookBooks()) {
            locationLabel.setIcon(IconBuilder.locationIcon(Player.getImage()));
            // enable and show button
            enableBooks();
            Player.setIsLookBooks(false);
        }
        else {
            disableAllBooks();
            if(!Player.getInstance().isItemEquipped()){
                unlitRoom = IconBuilder.locationIcon(UNLIT);
                locationLabel.setIcon(unlitRoom);
            }
            else {
                // create the ImageIcon by finding the file name in json
                ImageIcon locIcon = IconBuilder.locationIcon(OurJSONParser.getRoom().get("image").toString());
                // set area with image
                locationLabel.setIcon(locIcon);
                // below one doesn't work
                // locationLabel.setIcon(IconBuilder.locationIcon(Player.getImage().toString()));
                System.out.println(Player.getImage().toString());
            }
        }

    }

    private static void setOffAll() {
        setOff(noteBtn);
        setOff(torchBtn);
        setOff(needleBtn);
    }

    private void setTextArea() {
        // create description by finding in json
        String locDescription = OurJSONParser.getRoom().get("description").toString();
        if(Player.getIsLook()){
            textArea.setText(Player.getPlug());
        }
        else if(OurJSONParser.getRoomItems()!=null){
            // set text area with room description
            if(Player.getInstance().isItemEquipped()){
                textArea.setText(Player.getInstance().getCurrentRoom().toUpperCase(Locale.ROOT) + "\n" + locDescription);
            }
            else if (!Player.getInstance().isItemEquipped()){
                textArea.setText(firstLine);
            }
            // if there are items in the room, append text area with items
        }
        else{
            textArea.setText(locDescription);
        }
    }

    private void setResponse() {
        if(Player.getIsLook()){
            responseArea.setText("");
        }
        else {
            responseArea.setText(Player.getPlug());
        }
        Player.setIsLook(false);
    }

    static void setInventory() throws IOException, InterruptedException {
        model = new DefaultListModel<>();
        model.addAll(Player.getInstance().getCurrentInventory());
        inventoryList.setModel(model);
        try {
            checkWin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void setItems(){
        Set item = OurJSONParser.getRoomItems().keySet();
        itemModel = new DefaultListModel<>();
        itemModel.addAll(item);
        itemList.setModel(itemModel);
    }

    static boolean checkWin() throws Exception {
        Set<String> remainingBooks = OurJSONParser.getBooks().keySet();
        int bookSelections = remainingBooks.size() - (remainingBooks.size() - 2);
        if (remainingBooks.size() <= bookSelections && remainingBooks.contains("it")) {
            try {
                resetGameField();
                LoseScreen.lossLabel.setText("<html> &ensp &nbsp You feel the floor shift beneath your feet. It opens up, dropping you into a massive pit of spikes.<br/>As you descend you see the bodies of countless others who have played and failed this twisted game.</html>");
                LoseScreen.lossLabel.setBounds(175,300,1000,50);
                Frame.getLoseScreen();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else if (model.contains("it")) {
            gameEnd = true;
            resetGameField();
            Frame.getWinScreen();
        }
        return gameEnd;
    }

    static void resetGameField() throws InterruptedException, IOException, ParseException, Exception {
        Player.setPlayer(new Player());
        OurJSONParser.resetAll();
        Player.setPlug("");

        timer.stop();
        Audio.stopSound();
        model.clear();
        textField.setText("");

        updateImage();
        responseArea.setText(Player.getPlug());
        textArea.setText(resetVariable);

        TimeUnit.SECONDS.sleep(1);
    }

    public static void countDown(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds--;
                dblSeconds = decimalFormat.format(seconds);
                dblMinutes = decimalFormat.format(minutes);
                countDownLabel.setText(dblMinutes + ":" + dblSeconds);

                if (seconds == -1){
                    seconds = 59;
                    minutes--;
                    dblSeconds = decimalFormat.format(seconds);
                    dblMinutes = decimalFormat.format(minutes);
                    countDownLabel.setText(dblMinutes + ":" + dblSeconds);
                }
                if (minutes == 0 && seconds == 0){
                    //timer.stop();
                    try {
                        resetGameField();
                        LoseScreen.lossLabel.setText("<html>Your body begins to stiffen and agony takes the name of each breath.<br/> &emsp &emsp &ensp Your world fades to black as you fall to the ground...</html>");
                        LoseScreen.lossLabel.setBounds(290,300,1000,50);
                        Frame.getLoseScreen();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

}