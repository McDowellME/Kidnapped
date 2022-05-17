package com.sprints.gui;

import com.sprints.OurJSONParser;
import com.sprints.Player;
import com.sprints.TextParser;
import org.json.simple.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.sprints.OurJSONParser.roomsJSON;

public class GameFrame extends JPanel implements ActionListener {
    static ImageIcon unlitRoom;
    private static final String BACKGROUND = "images/background.jpg";
    private static final String UNLIT = "images/dark.png";
    private static String firstLine = "Too dark to see everything here, you need some light";
    private static DefaultListModel<String> model;
    TextParser textParser = new TextParser();
    private static Timer timer;
    private static int seconds, minutes;
    private static String dblSeconds, dblMinutes;
    private static DecimalFormat decimalFormat = new DecimalFormat("00");
    JTextField textField;
    JScrollPane scrollPane;
    JList inventoryList;
    private static JTextArea textArea, responseArea;
    private static JLabel inventoryLabel, locationLabel, countDownLabel, background;
    JButton enterBtn, northBtn, eastBtn, southBtn, westBtn, helpBtn, audBtn;
    JSlider audSlider;
    Font txtFont = new Font("Times New Roman", Font.BOLD, 15);
    Font inventoryFont = new Font("Times New Roman", Font.BOLD, 20);
    private static boolean isHelpDisplayed = false;     // music is ON by default

    private static final String resetVariable = firstLine + "\nYou see: " +
            OurJSONParser.getRoomItems().keySet();

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
        background.setBounds(0,0,1094, 730);
        ImageIcon backgroundIcon = IconBuilder.locationIcon(BACKGROUND);
        background.setIcon(backgroundIcon);

        //region Description
        textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(txtFont);
        Set item = OurJSONParser.getRoomItems().keySet();
        textArea.setText(firstLine);
        textArea.append("\nYou see: " + item);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(Color.RED));
        scrollPane.setBounds(310,475,500,180);
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
        responseArea.setBounds(310,427,500,42);
        //endregion

        //region Inventory
        inventoryList = new JList();
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
        buildImgButton(northBtn, north, "north",33);
        northBtn.setBounds (875, 475, 80, 50);
        //endregion

        //region East button
        String east  =  "images/button_e.png";
        eastBtn = new JButton();
        buildImgButton(eastBtn, east, "east", 33);
        eastBtn.setBounds (925, 525, 80, 50);
        //endregion

        //region South button
        String south  =  "images/button_s.png";
        southBtn = new JButton();
        buildImgButton(southBtn, south, "south", 33);
        southBtn.setBounds (875, 575, 80, 50);
        //endregion

        //region West button
        String west  =  "images/button_w.png";
        westBtn = new JButton();
        buildImgButton(westBtn, west, "west", 33);
        westBtn.setBounds (825, 525, 80, 50);
        //endregion

        // region Help button
        String help = "images/button_help.png";
        helpBtn = new JButton();
        buildImgButton(helpBtn, help, "help", 40);
        helpBtn.setBounds (1000, 650, 80, 50);
        //endregion

        //region Audio button
        String audio  =  "images/play.png";
        audBtn = new JButton();
        buildImgButton(audBtn, audio, "audToggle", 33);
        audBtn.setBounds (1000, 377, 80, 50);
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
        audSlider.setBounds (1025, 250, 31, 120);
        //endregion

        //region Timer
        countDownLabel = new JLabel();

        //endregion

        // region Add components
        add(countDownLabel);
        add(locationLabel);
        add(textField);
        add(scrollPane);
        add(inventoryList);
        add(inventoryLabel);
        add(enterBtn);
        add(northBtn);
        add(eastBtn);
        add(southBtn);
        add(westBtn);
        add(helpBtn);
        add(audBtn);
        add(audSlider);
        add(responseArea);
        add(background);

        setVisible(true);
        //endregion
    //endregion
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
    private void buildImgButton(JButton btn, String file, String command, int size) throws IOException {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Player.setPlug("");
            switch (e.getActionCommand()){
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
                case "audToggle":
                    Audio.toggleSound(audBtn, audSlider);
                    break;
                case "help":
                    if (!isHelpDisplayed) {
                        textArea.read(ResourceReader.readText("data/commandsmenu.txt"), null);
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
        setResponse();
        textField.setText("");
    }

    private static void updateImage() throws IOException {
        if(!Player.getInstance().isItemEquipped()){
            unlitRoom = IconBuilder.locationIcon(UNLIT);
            locationLabel.setIcon(unlitRoom);
        }
        else {
            // create the ImageIcon by finding the file name in json
            ImageIcon locIcon = IconBuilder.locationIcon(OurJSONParser.getRoom().get("image").toString());
            // set area with image
            locationLabel.setIcon(locIcon);
        }
    }

    private void setTextArea() {
        Set item = OurJSONParser.getRoomItems().keySet();
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
            if(item.size() != 0){
                textArea.append("\nYou see: " + item);
            }
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

    private void setInventory() throws IOException, InterruptedException {
        model = new DefaultListModel<>();
        model.addAll(Player.getInstance().getCurrentInventory());
        inventoryList.setModel(model);
        checkWin(model);
    }

    public void checkWin(DefaultListModel model) throws InterruptedException, IOException {
        Set<String> remainingBooks = OurJSONParser.getBooks().keySet();
        int bookSelections = remainingBooks.size() - (remainingBooks.size() - 2);
        if (remainingBooks.size() <= bookSelections && remainingBooks.contains("it")) {
            try {
                resetGameField();
                Frame.getLoseScreen();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (model.contains("it")) {
            resetGameField();
            Frame.getWinScreen();
        }
    }

    private static void resetGameField() throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        Player.setPlug("");
        Player.getInstance().setItemEquipped(false);
        responseArea.setText(Player.getPlug());
        timer.stop();
        Audio.stopSound();
        textArea.setText(resetVariable);
        model.clear();
        updateImage();
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
                    timer.stop();
                    try {
                        resetGameField();
                        Frame.getLoseScreen();
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

}