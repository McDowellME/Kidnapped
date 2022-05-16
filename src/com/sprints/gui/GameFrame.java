package com.sprints.gui;

import com.sprints.OurJSONParser;
import com.sprints.Player;
import com.sprints.TextParser;

import javax.imageio.ImageIO;
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

class GameFrame extends JPanel implements ActionListener {
    TextParser textParser = new TextParser();
    private static Timer timer;
    private static int seconds, minutes;
    private static String dblSeconds, dblMinutes;
    private static DecimalFormat decimalFormat = new DecimalFormat("00");
    JTextField textField;
    JScrollPane scrollPane;
    JList inventoryList;
    JTextArea textArea, responseArea;
    private static JLabel inventoryLabel, locationLabel, countDownLabel;
    JButton enterBtn, northBtn, eastBtn, southBtn, westBtn, helpBtn, audBtn;
    JSlider audSlider;
    Font txtFont = new Font("Times New Roman", Font.BOLD, 15);
    Font inventoryFont = new Font("Times New Roman", Font.BOLD, 20);

    public GameFrame() throws IOException {
        //region Dimensions of the frame
        setPreferredSize (new Dimension(1094, 730));
        setLayout(null);
        setBackground(Color.BLACK);
        //endregion

        //region Location image
        InputStream location = classLoaderResourceStream("images/placeholder.png");
        Image locImg = ImageIO.read(location);
        ImageIcon imageIcon = new ImageIcon(locImg);
        Image locImage = imageIcon.getImage();
        Image locImg2 = locImage.getScaledInstance(1094, 730,  Image.SCALE_SMOOTH);
        locationLabel = new JLabel(new ImageIcon(locImg2));
        locationLabel.setBounds (136, 20, 822, 400);
        //endregion

        //region User input
        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.addActionListener( action ); // let me use enter key on keyboard
        textField.setFont(new Font("Calibri", Font.BOLD, 15));
        textField.setBorder(new LineBorder(Color.red));
        textField.setBounds(390,663,300,40);
        //endregion


        //region Description
        textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(txtFont);
        Set item = OurJSONParser.getRoomItems().keySet();
        textArea.setText(Player.getInstance().getCurrentRoom().toUpperCase(Locale.ROOT) + "\n" + OurJSONParser.getRoom().get("description").toString()+ "\nYou see: "+ item);
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
        InputStream north  =  classLoaderResourceStream("images/button_n.png");
        Image nImg = ImageIO.read(north);
        ImageIcon nIcon = new ImageIcon(nImg);
        Image nImage = nIcon.getImage();
        Image nImg2 = nImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        northBtn = new JButton();
        northBtn.setOpaque(true);
        northBtn.setBorderPainted(false);
        northBtn.setFont(txtFont);
        northBtn.setFocusPainted(false);
        northBtn.setBorder(null);
        northBtn.setContentAreaFilled(false);
        northBtn.addActionListener(this);
        northBtn.setActionCommand("north");
        northBtn.setIcon(new ImageIcon(nImg2));
        northBtn.setBounds (875, 475, 80, 50);
        //endregion

        //region East button
        InputStream east  =  classLoaderResourceStream("images/button_e.png");

        Image eImg = ImageIO.read(east);
        ImageIcon eIcon = new ImageIcon(eImg);
        Image eImage = eIcon.getImage();
        Image eImg2 = eImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        eastBtn = new JButton();
        eastBtn.setOpaque(true);
        eastBtn.setBorderPainted(false);
        eastBtn.setFont(txtFont);
        eastBtn.setFocusPainted(false);
        eastBtn.setBorder(null);
        eastBtn.setContentAreaFilled(false);
        eastBtn.addActionListener(this);
        eastBtn.setActionCommand("east");
        eastBtn.setIcon(new ImageIcon(eImg2));
        eastBtn.setBounds (925, 525, 80, 50);
        //endregion

        //region South button
        InputStream south  =  classLoaderResourceStream("images/button_s.png");
        Image sImg = ImageIO.read(south);
        ImageIcon sIcon = new ImageIcon(sImg);
        Image sImage = sIcon.getImage();
        Image sImg2 = sImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        southBtn = new JButton();
        southBtn.setOpaque(true);
        southBtn.setBorderPainted(false);
        southBtn.setFont(txtFont);
        southBtn.setFocusPainted(false);
        southBtn.setBorder(null);
        southBtn.setContentAreaFilled(false);
        southBtn.addActionListener(this);
        southBtn.setActionCommand("south");
        southBtn.setIcon(new ImageIcon(sImg2));
        southBtn.setBounds (875, 575, 80, 50);
        //endregion

        //region West button
        InputStream west  =  classLoaderResourceStream("images/button_w.png");
        Image wImg = ImageIO.read(west);
        ImageIcon wIcon = new ImageIcon(wImg);
        Image wImage = wIcon.getImage();
        Image wImg2 = wImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        westBtn = new JButton();
        westBtn.setOpaque(true);
        westBtn.setBorderPainted(false);
        westBtn.setFont(txtFont);
        westBtn.setFocusPainted(false);
        westBtn.setBorder(null);
        westBtn.setContentAreaFilled(false);
        westBtn.addActionListener(this);
        westBtn.setActionCommand("west");
        westBtn.setIcon(new ImageIcon(wImg2));
        westBtn.setBounds (825, 525, 80, 50);
        //endregion

        // region Help button
        InputStream help = classLoaderResourceStream("images/button_help.png");
        Image helpImg = ImageIO.read(help);
        Image helpImgScaled = helpImg.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        ImageIcon helpIcon = new ImageIcon(helpImgScaled);

        helpBtn = new JButton();
        helpBtn.setOpaque(true);
        helpBtn.setBorderPainted(false);
        helpBtn.setFont(txtFont);
        helpBtn.setFocusPainted(false);
        helpBtn.setBorder(null);
        helpBtn.setContentAreaFilled(false);
        helpBtn.addActionListener(this);
        helpBtn.setActionCommand("help");
        helpBtn.setIcon(helpIcon);
        helpBtn.setBounds (1000, 650, 80, 50);
        //endregion

        //region Audio button
        InputStream audio  =  classLoaderResourceStream("images/play.png");
        Image audImg = ImageIO.read(audio);
        ImageIcon audIcon = new ImageIcon(audImg);
        Image audImage = audIcon.getImage();
        Image audImg2 = audImage.getScaledInstance(33, 33,  Image.SCALE_DEFAULT);

        audBtn = new JButton();
        audBtn.setOpaque(true);
        audBtn.setBorderPainted(false);
        audBtn.setFocusPainted(false);
        audBtn.setBorder(null);
        audBtn.setContentAreaFilled(false);
        audBtn.addActionListener(this);
        audBtn.setActionCommand("audToggle");
        audBtn.setIcon(new ImageIcon(audImg2));
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

//        //region Timer
        countDownLabel = new JLabel();

//        //endregion

        //region Add components
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

        setVisible(true);
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

    // for Jar-ing purposes
    private static InputStream classLoaderResourceStream(String file){
        InputStream is = GameFrame.class.getClassLoader().getResourceAsStream(file);
        return is;
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
                    BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("data/commandsmenu.txt")));
                    textArea.read(input, null);
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
        Set item;
        if(OurJSONParser.getRoomItems()!=null){
            item = OurJSONParser.getRoomItems().keySet();
            if(item.size() == 0){
                textArea.setText(Player.getInstance().getCurrentRoom().toUpperCase(Locale.ROOT) + "\n" + OurJSONParser.getRoom().get("description").toString());
            } else{
                textArea.setText(Player.getInstance().getCurrentRoom().toUpperCase(Locale.ROOT) + "\n" + OurJSONParser.getRoom().get("description").toString() + "\nYou see: " + item);
            }
        }
        else{
            textArea.setText(OurJSONParser.getRoom().get("description").toString());
        }
        setInventory();
        setResponse();
        textField.setText("");
    }

    private void setResponse() {
        responseArea.setText(Player.getPlug());
    }

    private void setInventory() throws IOException, InterruptedException {
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addAll(Player.getInstance().getCurrentInventory());
        inventoryList.setModel(model);
        checkWin(model);
    }

    public void checkWin(DefaultListModel model) throws InterruptedException, IOException {
        Set<String> remainingBooks = OurJSONParser.getBooks().keySet();
        int bookSelections = remainingBooks.size() - (remainingBooks.size() - 2);
        if (remainingBooks.size() <= bookSelections && remainingBooks.contains("it")) {
            try {
                timer.stop();
                TimeUnit.SECONDS.sleep(1);
                Audio.stopSound();
                Frame.getLoseScreen();
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } else if (model.contains("it")) {
            TimeUnit.SECONDS.sleep(1);
            timer.stop();
            Audio.stopSound();
            Frame.getWinScreen();
        }
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
                        TimeUnit.SECONDS.sleep(1);
                        Audio.stopSound();
                        Frame.getLoseScreen();
                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}