import javax.swing.*;
import java.awt.*;

public class HomeMenu extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gomoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1350, 800);
        frame.setLayout(new BorderLayout());

        // Title
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

        // Button fonts
        Font buttonFont = new Font("Algerian", Font.BOLD, 40);
        playerVsPlayerButton.setFont(buttonFont);
        playerVsBotButton.setFont(buttonFont);
        howToPlayButton.setFont(buttonFont);

        // Add action listeners
        playerVsPlayerButton.addActionListener(e -> {
            frame.dispose();
            showPlayerSelectionDialog();
        });

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

        // Add buttons to the panel
        buttonPanel.add(playerVsPlayerButton);
        buttonPanel.add(playerVsBotButton);
        buttonPanel.add(howToPlayButton);

        // Add components to the frame
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }

    private static void showPlayerSelectionDialog() {
        JFrame selectionFrame = new JFrame("Player Selection");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(500, 400);
        selectionFrame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Enter Players' Names and Select Avatars");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectionFrame.add(titleLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Player 1
        JPanel player1Panel = new JPanel(new BorderLayout(10, 10));
        JLabel player1Label = new JLabel("Player 1 Name:");
        JTextField player1NameField = new JTextField();
        JButton player1AvatarButton = new JButton("Select Avatar");
        player1Panel.add(player1Label, BorderLayout.WEST);
        player1Panel.add(player1NameField, BorderLayout.CENTER);
        player1Panel.add(player1AvatarButton, BorderLayout.EAST);

        // Player 2
        JPanel player2Panel = new JPanel(new BorderLayout(10, 10));
        JLabel player2Label = new JLabel("Player 2 Name:");
        JTextField player2NameField = new JTextField();
        JButton player2AvatarButton = new JButton("Select Avatar");
        player2Panel.add(player2Label, BorderLayout.WEST);
        player2Panel.add(player2NameField, BorderLayout.CENTER);
        player2Panel.add(player2AvatarButton, BorderLayout.EAST);

        // Add Panels to Input Panel
        inputPanel.add(player1Panel);
        inputPanel.add(Box.createVerticalStrut(15)); // Spacing
        inputPanel.add(player2Panel);

        selectionFrame.add(inputPanel, BorderLayout.CENTER);

        // Start Game Button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();

            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(selectionFrame, "Both players must enter their names!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ImageIcon player1Avatar = (ImageIcon) player1AvatarButton.getIcon();
                ImageIcon player2Avatar = (ImageIcon) player2AvatarButton.getIcon();
                selectionFrame.dispose();
                LineGrinder.startGame(19, player1Avatar, player2Avatar);
            }
        });

        selectionFrame.add(startGameButton, BorderLayout.SOUTH);
        selectionFrame.setVisible(true);

        // Avatar Selection Dialog
        player1AvatarButton.addActionListener(e -> showAvatarSelectionDialog(selectionFrame, player1AvatarButton));
        player2AvatarButton.addActionListener(e -> showAvatarSelectionDialog(selectionFrame, player2AvatarButton));
    }

    private static void showAvatarSelectionDialog(JFrame parentFrame, JButton avatarButton) {
        JDialog avatarDialog = new JDialog(parentFrame, "Select Avatar", true);
        avatarDialog.setSize(500, 400);
        avatarDialog.setLayout(new GridLayout(2, 2, 10, 10));

        String[] avatarPaths = { "avatar1.png", "avatar2.png", "avatar3.png", "avatar4.png" };
        ImageIcon[] avatarIcons = new ImageIcon[4];

        for (int i = 0; i < avatarPaths.length; i++) {
            final int index = i;
            ImageIcon originalIcon = new ImageIcon(HomeMenu.class.getResource(avatarPaths[index]));
            Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            avatarIcons[i] = new ImageIcon(resizedImage);
        }

        for (int i = 0; i < avatarIcons.length; i++) {
            final int index = i;
            JButton avatarSelectionButton = new JButton(avatarIcons[index]);
            avatarSelectionButton.addActionListener(e -> {
                avatarButton.setIcon(avatarIcons[index]);
                avatarDialog.dispose();
            });
            avatarDialog.add(avatarSelectionButton);
        }

        avatarDialog.setVisible(true);
    }
}
