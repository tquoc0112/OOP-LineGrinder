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
        dialog.setSize(900, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(parentFrame);

        //Title Location
        JLabel titleLabel = new JLabel("Enters Players Name and Select Avatars");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for player inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
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

        // Avatar selection buttons
        JButton player1AvatarButton = new JButton("Select Avatar");
        player1AvatarButton.setFont(new Font("Algerian", Font.BOLD, 16));
        JButton player2AvatarButton = new JButton("Select Avatar");
        player2AvatarButton.setFont(new Font("Algerian", Font.BOLD, 16));

        inputPanel.add(player1AvatarButton);
        inputPanel.add(player2AvatarButton);

        // Add input panel to the dialog
        dialog.add(inputPanel, BorderLayout.CENTER);

        // "Start Game" button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            // Get player names
            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();

            // Validate input
            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Both players must enter their names!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Handle game start logic here
                System.out.println("Player 1: " + player1Name);
                System.out.println("Player 2: " + player2Name);

                dialog.dispose(); // Close the dialog
                parentFrame.dispose();// close the menu
                
                    LineGrinder.main(new String[]{});// Start the game
                };
            ;
        });

        // Add "Start Game" button to the dialog
        dialog.add(startGameButton, BorderLayout.SOUTH);

        // Make the dialog visible
        dialog.setVisible(true);
    }
}
