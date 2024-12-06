import javax.swing.*;

public class LineGrinder {
    public static void main(String[] args) {
        int size = 19; // Default board size

        // Parse board size from arguments
        if (args.length > 0) {
            try {
                size = Integer.parseInt(args[0]);
                if (size < 5 || size > 50) {
                    throw new IllegalArgumentException("Board size must be between 5 and 50.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, 
                    "Invalid board size. Please provide an integer between 5 and 50.\nUsing default size of 19.", 
                    "Invalid Input", 
                    JOptionPane.WARNING_MESSAGE);
                size = 19;
            } catch (IllegalArgumentException e) {
                // You can either remove this or handle the specific case for illegal size
            }            
        }

        // Debug: Check if the size is correctly passed
        System.out.println("Game Size: " + size);

        // Create the game frame
        JFrame frame = new JFrame("LineGrinder");
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Pass the size to LineGrinderPanel
        LineGrinderPanel panel = new LineGrinderPanel(size);
        frame.add(panel);

        frame.setVisible(true); // Display the frame
    }
}

