import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LineGrinderStartScreen extends JFrame {
    public LineGrinderStartScreen() {
        setTitle("LineGrinder - Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("LineGrinder", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton startButton = new JButton("Start");
        JButton optionButton = new JButton("Options");
        JButton creditsButton = new JButton("Credits");

        buttonPanel.add(startButton);
        buttonPanel.add(optionButton);
        buttonPanel.add(creditsButton);

        add(buttonPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> startGame());
        optionButton.addActionListener(e -> showOptions());
        creditsButton.addActionListener(e -> showCredits());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame() {
        // Pass size to LineGrinder directly, e.g., 19
        SwingUtilities.invokeLater(() -> {
            dispose(); // Close the starting screen
            LineGrinder.main(new String[]{"19"}); // Start the game with size 19
        });
    }

    private void showOptions() {
        JOptionPane.showMessageDialog(this, 
            "Options screen coming soon!", 
            "Options", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCredits() {
        JOptionPane.showMessageDialog(this, 
            "<html><center><b>Created by: Your Name</b><br>Thanks for playing!</center></html>", 
            "Credits", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new LineGrinderStartScreen();
    }
}
