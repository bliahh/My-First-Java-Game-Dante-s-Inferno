
package main;

import DatabaseManager.DataBaseSaveSlotManager;
import DatabaseManager.SaveData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainMenu {
    private JFrame menuFrame; //FEREASTRA PRINCIPALA A MENIULUII
    private GamePanel gamePanel;

    //SCHIMB MAI MULTE PAGINI
    private CardLayout cardLayout; //creare interfete care schimba ce panouri sunt vizibile
    private JPanel cardPanel; //PANOU CE CONTINE TOATE CARDURILE

    private final Color goldColor = new Color(179, 143, 46);
    private final Color darkBlue = new Color(0, 0, 20, 230);
    private static final String DB_URL = "jdbc:sqlite:resources/database/savedStates.db";

    public MainMenu() {
        createMenuFrame();
    }

    private void createMenuFrame() {

        //creez JFrame si setez ca inchiderea fer sa opreasca program
        menuFrame = new JFrame("Dante's Inferno - Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(800, 600);
        //centrez pe ecran
        menuFrame.setResizable(false);
        menuFrame.setLocationRelativeTo(null);

        //creez ca sa pot afisa cate un panou pe rand
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        createMainMenu();
        createGameMenu();
        createLoadGameMenu();

        menuFrame.add(cardPanel);
        menuFrame.setVisible(true);
    }

    private void createMainMenu() {
        //GridBagLayout--> poz flexibila
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());
        mainMenuPanel.setBackground(darkBlue);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;//FIECARE ELEM OCUPA TOT RANDU
        gbc.fill = GridBagConstraints.HORIZONTAL; //UMPLU IN ORIZONTALA
        gbc.insets = new Insets(10, 50, 10, 50);

        JLabel title = new JLabel("DANTE'S INFERNO", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(goldColor);
        gbc.insets = new Insets(0, 50, 50, 50);
        mainMenuPanel.add(title, gbc); //adaug eticheta in panou,folosind reguli gbc

        gbc.insets = new Insets(10, 50, 10, 50);

        JButton startButton = createMenuButton("START");
        startButton.addActionListener(e -> cardLayout.show(cardPanel, "GAME_MENU"));
        mainMenuPanel.add(startButton, gbc);

        JButton leaderboardButton = createMenuButton("LEADERBOARD");
        leaderboardButton.addActionListener(e -> showLeaderboard());
        mainMenuPanel.add(leaderboardButton, gbc);

        JButton exitButton = createMenuButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0));
        mainMenuPanel.add(exitButton, gbc);

        cardPanel.add(mainMenuPanel, "MAIN_MENU");
    }


    //MENIUL DE START JOC
    private void createGameMenu() {
        JPanel gameMenuPanel = new JPanel(new GridBagLayout());
        gameMenuPanel.setBackground(darkBlue);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        JLabel title = new JLabel("SELECT GAME", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(goldColor);
        gbc.insets = new Insets(0, 50, 50, 50);
        gameMenuPanel.add(title, gbc);

        gbc.insets = new Insets(10, 50, 10, 50);

        JButton newGameButton = createMenuButton("NEW GAME");
        newGameButton.addActionListener(e -> {
            menuFrame.dispose(); //INCHIDE FEREASTRA PRINCIPALA MENIU
            startGame(); //APELEAZA START GAME
        });
        gameMenuPanel.add(newGameButton, gbc);

        JButton loadGameButton = createMenuButton("LOAD GAME");
        loadGameButton.addActionListener(e -> cardLayout.show(cardPanel, "LOAD_GAME_MENU"));
        gameMenuPanel.add(loadGameButton, gbc);

        JButton backButton = createMenuButton("BACK");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN_MENU"));
        gameMenuPanel.add(backButton, gbc);

        cardPanel.add(gameMenuPanel, "GAME_MENU");
    }

    private void createLoadGameMenu() {
        JPanel loadGamePanel = new JPanel(new GridBagLayout());
        loadGamePanel.setBackground(darkBlue);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        JLabel title = new JLabel("LOAD GAME", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(goldColor);
        gbc.insets = new Insets(0, 50, 50, 50);
        loadGamePanel.add(title, gbc);

        gbc.insets = new Insets(10, 50, 10, 50);
        for (int i = 1; i <= 7; i++) {
            int slotId = i;
            JButton slotButton = createMenuButton("Slot " + i);
            slotButton.addActionListener(e -> {
                menuFrame.dispose();
                startGameFromSlot(slotId);
            });
            loadGamePanel.add(slotButton, gbc);
        }

        JButton backButton = createMenuButton("BACK");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "GAME_MENU"));
        loadGamePanel.add(backButton, gbc);

        cardPanel.add(loadGamePanel, "LOAD_GAME_MENU");
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(goldColor);
        button.setBackground(darkBlue);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setBorder(BorderFactory.createLineBorder(goldColor));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(darkBlue);
            }
        });

        return button;
    }

    private void startGame() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Dante's Inferno");

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startgameThread();
    }

    private void startGameFromSlot(int slotId) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Dante's Inferno");

        gamePanel = new GamePanel();
        DataBaseSaveSlotManager dbManager = new DataBaseSaveSlotManager();
        SaveData saveData = dbManager.loadFromSlot(slotId, gamePanel);

        if (saveData != null) {
            //LOAD SAVE DATA SEEAZA ISNEWGAME=FALSE;
            gamePanel.loadSaveData(saveData);
        }
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startgameThread();
    }

    private void showLeaderboard() {
        String sql = "SELECT name, karma FROM WINNERS ORDER BY karma DESC";
       //FOLOSIT POPULARE JTABLE
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Name", "Karma"}, 0);
         //0->initial nu am randuri
         //2 coloane
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DataBaseSaveSlotManager.DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int karma = rs.getInt("karma");
                tableModel.addRow(new Object[]{name, karma});
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(menuFrame, "Error loading leaderboard.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JTable leaderboardTable = new JTable(tableModel);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 14));
        leaderboardTable.setRowHeight(30);
        //pot selecta un singur rand
        leaderboardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //nu pot editaaa
        leaderboardTable.setDefaultEditor(Object.class, null); // Disable editing cells

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardPanel.setBackground(darkBlue);
        leaderboardPanel.add(new JLabel("Leaderboard", SwingConstants.CENTER), BorderLayout.NORTH);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);//in centrul panoului

        JOptionPane.showMessageDialog(menuFrame, leaderboardPanel, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

}