import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LineGrinderPanel extends JPanel {
    private int size;
    private LineGrinderState state;
    private ImageIcon player1Avatar;
    private ImageIcon player2Avatar;

    public LineGrinderPanel(int size, String mapType, ImageIcon player1Avatar, ImageIcon player2Avatar) {
        this.size = size;
        this.player1Avatar = player1Avatar;
        this.player2Avatar = player2Avatar;
        state = new LineGrinderState(size);
        this.setLayout(new BorderLayout());
        this.add(new BoardPanel(), BorderLayout.CENTER);
    }

    private class BoardPanel extends JPanel {
        public BoardPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    double squareSize = getWidth() / (double) size;
                    int col = (int) (e.getX() / squareSize);
                    int row = (int) (e.getY() / squareSize);

                    if (row < size && col < size && state.getPiece(row, col) == LineGrinderState.NONE) {
                        state.playPiece(row, col);
                        repaint();

                        int winner = state.getWinner();
                        if (winner != LineGrinderState.NONE) {
                            JOptionPane.showMessageDialog(null,
                                    winner == LineGrinderState.X ? "Player 1 wins!" : "Player 2 wins!");
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // Grid settings
            double panelWidth = getWidth();
            double panelHeight = getHeight();
            double squareSize = Math.min(panelWidth, panelHeight) / size;

            // Draw gridlines
            g2.setColor(Color.BLACK);
            for (int i = 0; i <= size; i++) {
                double linePos = i * squareSize;
                // Horizontal lines
                g2.drawLine(0, (int) linePos, (int) panelWidth, (int) linePos);
                // Vertical lines
                g2.drawLine((int) linePos, 0, (int) linePos, (int) panelHeight);
            }

            // Draw pieces (avatars)
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    int piece = state.getPiece(row, col);
                    double x = col * squareSize;
                    double y = row * squareSize;

                    if (piece == LineGrinderState.X) {
                        g2.drawImage(player1Avatar.getImage(), (int) x, (int) y, (int) squareSize, (int) squareSize,
                                null);
                    } else if (piece == LineGrinderState.O) {
                        g2.drawImage(player2Avatar.getImage(), (int) x, (int) y, (int) squareSize, (int) squareSize,
                                null);
                    }
                }
            }
        }
    }
}
