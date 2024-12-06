import javax.swing.*;
import java.awt.*;

public class HomeMenu extends JFrame {

    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Co Caro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1350, 800);
        frame.setLayout(new BorderLayout());

        //Title
        JLabel titleLabel = new JLabel(new ImageIcon(HomeMenu.class.getResource("GOMOKU_Logo.png")));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Create buttons
        JButton playerVsPlayerButton = new JButton("Player vs Player");
        JButton playerVsBotButton = new JButton("Player vs Bot");
        JButton howToPlayButton = new JButton("How to Play");

        //
        //Font for buttons
        playerVsPlayerButton.setFont(new Font("Algerian", Font.BOLD, 40));
        playerVsBotButton.setFont(new Font("Algerian", Font.BOLD, 40));
        howToPlayButton.setFont(new Font("Algerian", Font.BOLD, 40));
        howToPlayButton.addActionListener(e -> {
            String rules = "<html><h2>Gomoku Rules</h2>"
                         + "<ol>"
                         + "<li>Players alternate turns placing X and O on the board.</li>"
                         + "<li>The first player to align 5 in a row (horizontally, vertically, or diagonally) wins.</li>"
                         + "<li>No moves can be placed on an occupied position.</li>"
                         + "<li>The game is a draw if the board is full and no player has 5 in a row.</li>"
                         + "</ol></html>";
            JOptionPane.showMessageDialog(frame, rules, "How to Play", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add action listener to "Player vs Player" button
        playerVsPlayerButton.addActionListener(e -> {
            showPlayerSelectionDialog(frame);
        });

        // Add buttons to panel
        buttonPanel.add(playerVsPlayerButton);
        buttonPanel.add(playerVsBotButton);
        buttonPanel.add(howToPlayButton);

        // Add components to the frame
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Make frame visible
        frame.setVisible(true);
    }

    /**
     * Show the dialog for player selection.
     */
    private static void showPlayerSelectionDialog(JFrame parentFrame) {
        // Create a new JDialog
        JDialog dialog = new JDialog(parentFrame, "Player Selection", true);
        dialog.setSize(900, 300);  // Adjusted size to accommodate map selection
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parentFrame);
    
        // Title Location
        JLabel titleLabel = new JLabel("Enter Players' Names");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(titleLabel, BorderLayout.NORTH);
    
        // Create a panel for player inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Player 1 name input
        JLabel player1Label = new JLabel("Player 1 Name:");
        player1Label.setFont(new Font("Algerian", Font.BOLD, 18));
        JTextField player1NameField = new JTextField();
        player1NameField.setFont(new Font("Algerian", Font.PLAIN, 18));
    
        // Player 2 name input
        JLabel player2Label = new JLabel("Player 2 Name:");
        player2Label.setFont(new Font("Algerian", Font.BOLD, 18));
        JTextField player2NameField = new JTextField();
        player2NameField.setFont(new Font("Algerian", Font.PLAIN, 18));
    
        // Add components to the input panel
        inputPanel.add(player1Label);
        inputPanel.add(player1NameField);
        inputPanel.add(player2Label);
        inputPanel.add(player2NameField);
    
        // Add input panel to the dialog
        dialog.add(inputPanel, BorderLayout.CENTER);
    
        // "Next" button to proceed to map selection
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();
    
            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Both players must enter their names!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dialog.dispose(); // Close this dialog
                showMapSelectionDialog(parentFrame, player1Name, player2Name);
            }
        });
    
        dialog.add(nextButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private static void showMapSelectionDialog(JFrame parentFrame, String player1Name, String player2Name) {
        JDialog mapDialog = new JDialog(parentFrame, "Select Map", true);
        mapDialog.setSize(600, 300);
        mapDialog.setLayout(new BorderLayout());
        mapDialog.setLocationRelativeTo(parentFrame);
    
        JLabel titleLabel = new JLabel("Select the Map Type");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mapDialog.add(titleLabel, BorderLayout.NORTH);
    
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
    
        JButton squareButton = new JButton("Square");
        JButton circleButton = new JButton("Circle");
        JButton triangleButton = new JButton("Triangle");
    
        // Handle map selection
        squareButton.addActionListener(e -> {
            startGame(parentFrame, player1Name, player2Name, "Square");
            mapDialog.dispose();
        });
    
        circleButton.addActionListener(e -> {
            startGame(parentFrame, player1Name, player2Name, "Circle");
            mapDialog.dispose();
        });
    
        triangleButton.addActionListener(e -> {
            startGame(parentFrame, player1Name, player2Name, "Triangle");
            mapDialog.dispose();
        });
    
        mapPanel.add(squareButton);
        mapPanel.add(circleButton);
        mapPanel.add(triangleButton);
    
        mapDialog.add(mapPanel, BorderLayout.CENTER);
        mapDialog.setVisible(true);
    }
    
    private static void startGame(JFrame parentFrame, String player1Name, String player2Name, String mapType) {
        System.out.println("Starting game with:");
        System.out.println("Player 1: " + player1Name);
        System.out.println("Player 2: " + player2Name);
        System.out.println("Map Type: " + mapType);
    
        // Close parent frame
        parentFrame.dispose();
    
        // Initialize game window with selected map type
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("LineGrinder - " + mapType + " Map");
            gameFrame.setSize(800, 800);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
    
            // Pass map type to the game panel
            LineGrinderPanel panel = new LineGrinderPanel(19); // Example, could use mapType logic
            gameFrame.add(panel);
            gameFrame.setVisible(true);
        });
    }    
}