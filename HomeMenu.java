import javax.swing.*;
import java.awt.*;

public class HomeMenu extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gomoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title (Logo)
        JLabel titleLabel = new JLabel();
        ImageIcon originalLogo = new ImageIcon(HomeMenu.class.getResource("GOMOKU_Logo.png"));
        Image scaledImage = originalLogo.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        titleLabel.setIcon(new ImageIcon(scaledImage));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Create buttons
        JButton playerVsPlayerButton = new JButton("Player vs Player");
        JButton howToPlayButton = new JButton("How to Play");

        // Button fonts
        Font buttonFont = new Font("Algerian", Font.BOLD, 30);
        playerVsPlayerButton.setFont(buttonFont);
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
                    + "<li>The first player to align the required number of pieces in a row (horizontally, vertically, or diagonally) wins.</li>"
                    + "<li>No moves can be placed on an occupied position.</li>"
                    + "<li>The game is a draw if the board is full and no player has the required alignment.</li>"
                    + "</ol></html>";
            JOptionPane.showMessageDialog(frame, rules, "How to Play", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add buttons to the panel
        buttonPanel.add(playerVsPlayerButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Add space between buttons
        buttonPanel.add(howToPlayButton);

        // Add components to the frame
        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Pack frame to fit contents
        frame.pack();

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Show the frame
        frame.setVisible(true);
    }

    private static void showPlayerSelectionDialog() {
        JFrame selectionFrame = new JFrame("Player Selection");
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setSize(600, 700);
        selectionFrame.setLocationRelativeTo(null); // Center the frame
        selectionFrame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Enter Players' Names, Select Avatars, and Timer");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectionFrame.add(titleLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Player 1 Panel
        JPanel player1Panel = new JPanel(new BorderLayout(10, 10));
        JLabel player1Label = new JLabel("Player 1 Name:");
        JTextField player1NameField = new JTextField();
        JButton player1AvatarButton = new JButton("Select Avatar");
        player1Panel.add(player1Label, BorderLayout.WEST);
        player1Panel.add(player1NameField, BorderLayout.CENTER);
        player1Panel.add(player1AvatarButton, BorderLayout.EAST);

        // Player 1 Timer
        JPanel player1TimerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel player1TimerLabel = new JLabel("Player 1 Timer:");
        String[] timerOptions = { "30s", "60s", "120s", "180s", "300s" };
        JComboBox<String> player1TimerComboBox = new JComboBox<>(timerOptions);
        player1TimerComboBox.setSelectedItem("300s"); // Default value
        player1TimerPanel.add(player1TimerLabel);
        player1TimerPanel.add(player1TimerComboBox);

        // Player 2 Panel
        JPanel player2Panel = new JPanel(new BorderLayout(10, 10));
        JLabel player2Label = new JLabel("Player 2 Name:");
        JTextField player2NameField = new JTextField();
        JButton player2AvatarButton = new JButton("Select Avatar");
        player2Panel.add(player2Label, BorderLayout.WEST);
        player2Panel.add(player2NameField, BorderLayout.CENTER);
        player2Panel.add(player2AvatarButton, BorderLayout.EAST);

        // Player 2 Timer
        JPanel player2TimerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel player2TimerLabel = new JLabel("Player 2 Timer:");
        JComboBox<String> player2TimerComboBox = new JComboBox<>(timerOptions);
        player2TimerComboBox.setSelectedItem("300s"); // Default value
        player2TimerPanel.add(player2TimerLabel);
        player2TimerPanel.add(player2TimerComboBox);

        // Board Size Panel
        JPanel boardSizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel boardSizeLabel = new JLabel("Board Size:");
        boardSizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField boardSizeField = new JTextField("19", 3);
        boardSizeField.setHorizontalAlignment(SwingConstants.CENTER);
        boardSizeField.setEditable(false);

        JButton incrementButton = new JButton("+");
        incrementButton.addActionListener(e -> {
            int size = Integer.parseInt(boardSizeField.getText());
            if (size < 50) {
                boardSizeField.setText(String.valueOf(size + 1));
            }
        });

        JButton decrementButton = new JButton("-");
        decrementButton.addActionListener(e -> {
            int size = Integer.parseInt(boardSizeField.getText());
            if (size > 5) {
                boardSizeField.setText(String.valueOf(size - 1));
            }
        });

        boardSizePanel.add(boardSizeLabel);
        boardSizePanel.add(decrementButton);
        boardSizePanel.add(boardSizeField);
        boardSizePanel.add(incrementButton);

        // Winning Condition Panel
        JPanel winningConditionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel winningConditionLabel = new JLabel("Winning Condition:");
        winningConditionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JComboBox<Integer> winningConditionComboBox = new JComboBox<>(new Integer[] { 3, 4, 5, 6, 7 });
        winningConditionComboBox.setSelectedItem(5); // Default value
        winningConditionPanel.add(winningConditionLabel);
        winningConditionPanel.add(winningConditionComboBox);

        // Add Panels to Input Panel
        inputPanel.add(player1Panel);
        inputPanel.add(player1TimerPanel);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(player2Panel);
        inputPanel.add(player2TimerPanel);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(boardSizePanel);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(winningConditionPanel);

        selectionFrame.add(inputPanel, BorderLayout.CENTER);

        // Start Game Button
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();
            int boardSize = Integer.parseInt(boardSizeField.getText());
            int winningCondition = (int) winningConditionComboBox.getSelectedItem();
            String player1Timer = (String) player1TimerComboBox.getSelectedItem();
            String player2Timer = (String) player2TimerComboBox.getSelectedItem();

            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(selectionFrame, "Both players must enter their names!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                ImageIcon player1Avatar = (ImageIcon) player1AvatarButton.getIcon();
                ImageIcon player2Avatar = (ImageIcon) player2AvatarButton.getIcon();
                selectionFrame.dispose();
                LineGrinder.startGame(boardSize, winningCondition, player1Avatar, player2Avatar, player1Timer,
                        player2Timer);
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
