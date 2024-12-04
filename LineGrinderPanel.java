import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

class LineGrinderPanel extends JPanel {
    private final int MARGIN = 5;
    private final double PIECE_FRAC = 0.9;

    private int size = 19;
    private LineGrinderState state;
    private JButton restartButton;
    private JButton mainMenuButton;

    public LineGrinderPanel() {
        this(19);
    }

    public LineGrinderPanel(int size) {
        super(new BorderLayout());
        this.size = size;
        state = new LineGrinderState(size);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        restartButton = new JButton("Restart");
        mainMenuButton = new JButton("Main Menu");

        restartButton.addActionListener(e -> restartGame());
        mainMenuButton.addActionListener(e -> returnToMainMenu());

        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        BoardPanel boardPanel = new BoardPanel();
        this.add(boardPanel, BorderLayout.CENTER);
    }

    private void restartGame() {
        state = new LineGrinderState(size);
        repaint();
    }

    private void returnToMainMenu() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
        LineGrinderStartScreen.main(new String[0]); // Return to the main menu
    }

    private class BoardPanel extends JPanel {
        public BoardPanel() {
            addMouseListener(new LineGrinderListener());
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            double panelWidth = getWidth();
            double panelHeight = getHeight();

            g2.setColor(new Color(0.925f, 0.670f, 0.34f));
            g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));

            double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;
            double squareWidth = boardWidth / size;
            double gridWidth = (size - 1) * squareWidth;
            double pieceDiameter = PIECE_FRAC * squareWidth;
            boardWidth -= pieceDiameter;
            double xLeft = (panelWidth - boardWidth) / 2 + MARGIN;
            double yTop = (panelHeight - boardWidth) / 2 + MARGIN;

            g2.setColor(Color.BLACK);
            for (int i = 0; i < size; i++) {
                double offset = i * squareWidth;
                g2.draw(new Line2D.Double(xLeft, yTop + offset, 
                                          xLeft + gridWidth, yTop + offset));
                g2.draw(new Line2D.Double(xLeft + offset, yTop,
                                          xLeft + offset, yTop + gridWidth));
            }

            g2.setFont(new Font("Arial", Font.BOLD, (int) pieceDiameter));
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    int piece = state.getPiece(row, col);
                    double xCenter = xLeft + col * squareWidth + (squareWidth / 2) ;
                    double yCenter = yTop + row * squareWidth + (squareWidth / 2)  ;

                    if (piece == LineGrinderState.X) {
                        g2.setColor(Color.BLACK);
                        g2.drawString("X", (float) (xCenter - pieceDiameter / 3), 
                                      (float) (yCenter + pieceDiameter / 3));
                    } else if (piece == LineGrinderState.O) {
                        g2.setColor(Color.BLACK);
                        g2.drawString("O", (float) (xCenter - pieceDiameter / 3), 
                                      (float) (yCenter + pieceDiameter / 3));
                    }
                }
            }
        }
    }

    class LineGrinderListener extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            double panelWidth = getWidth();
            double panelHeight = getHeight();
            double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;
            double squareWidth = boardWidth / size;
            double pieceDiameter = PIECE_FRAC * squareWidth;
            double xLeft = (panelWidth - boardWidth) / 2 + MARGIN;
            double yTop = (panelHeight - boardWidth) / 2 + MARGIN;
            int col = (int) Math.round((e.getX() - xLeft) / squareWidth - 0.5);
            int row = (int) Math.round((e.getY() - yTop) / squareWidth - 0.5);
            if (row >= 0 && row < size && col >= 0 && col < size
                && state.getPiece(row, col) == LineGrinderState.NONE
                && state.getWinner() == LineGrinderState.NONE) {
                state.playPiece(row, col);
                repaint();
                int winner = state.getWinner();
                if (winner != LineGrinderState.NONE)
                    JOptionPane.showMessageDialog(null,
                        (winner == LineGrinderState.X) ? "X wins!" : "O wins!");
            }
        }
    }
}
