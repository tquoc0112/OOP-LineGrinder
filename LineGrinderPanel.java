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

    private String mapType = "square"; // Default map type

    public LineGrinderPanel() {
        this(19, "square");
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
            double boardWidth = Math.min(panelWidth, panelHeight) - 2 * MARGIN;

            // Background color
            g2.setColor(new Color(0.925f, 0.670f, 0.34f));
            g2.fill(new Rectangle2D.Double(0, 0, panelWidth, panelHeight));

            if (mapType.equals("square")) {
                drawSquareBoard(g2, boardWidth);
            } else if (mapType.equals("circle")) {
                drawCircularBoard(g2, boardWidth);
            } else if (mapType.equals("triangle")) {
                drawTriangularBoard(g2, boardWidth);
            }

            drawPieces(g2, boardWidth);
        }

        private void drawSquareBoard(Graphics2D g2, double boardWidth) {
            double squareWidth = boardWidth / size;
            double xLeft = (getWidth() - boardWidth) / 2 + MARGIN;
            double yTop = (getHeight() - boardWidth) / 2 + MARGIN;

            g2.setColor(Color.BLACK);
            for (int i = 0; i < size; i++) {
                double offset = i * squareWidth;
                g2.draw(new Line2D.Double(xLeft, yTop + offset, xLeft + boardWidth, yTop + offset));
                g2.draw(new Line2D.Double(xLeft + offset, yTop, xLeft + offset, yTop + boardWidth));
            }
        }

        private void drawCircularBoard(Graphics2D g2, double boardWidth) {
            double radius = boardWidth / 2;
            double centerX = getWidth() / 2;
            double centerY = getHeight() / 2;

            g2.setColor(Color.BLACK);
            g2.draw(new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius));

            for (int i = 1; i < size; i++) {
                double ringRadius = i * radius / size;
                g2.draw(new Ellipse2D.Double(centerX - ringRadius, centerY - ringRadius, 2 * ringRadius, 2 * ringRadius));
            }
        }

        private void drawTriangularBoard(Graphics2D g2, double boardWidth) {
            double height = boardWidth * Math.sqrt(3) / 2;
            double xLeft = (getWidth() - boardWidth) / 2;
            double yTop = (getHeight() - height) / 2;

            Path2D.Double triangle = new Path2D.Double();
            triangle.moveTo(xLeft + boardWidth / 2, yTop);
            triangle.lineTo(xLeft, yTop + height);
            triangle.lineTo(xLeft + boardWidth, yTop + height);
            triangle.closePath();

            g2.setColor(Color.BLACK);
            g2.draw(triangle);

            for (int i = 1; i < size; i++) {
                double factor = (double) i / size;
                Path2D.Double innerTriangle = new Path2D.Double();
                innerTriangle.moveTo(xLeft + boardWidth / 2, yTop + factor * height);
                innerTriangle.lineTo(xLeft + factor * boardWidth / 2, yTop + height - factor * height);
                innerTriangle.lineTo(xLeft + boardWidth - factor * boardWidth / 2, yTop + height - factor * height);
                innerTriangle.closePath();
                g2.draw(innerTriangle);
            }
        }

        private void drawPieces(Graphics2D g2, double boardWidth) {
            double squareWidth = boardWidth / size;
            double pieceDiameter = PIECE_FRAC * squareWidth;
        
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    int piece = state.getPiece(row, col);
                    double xCenter = xLeft + col * squareWidth + (squareWidth / 2) ;
                    double yCenter = yTop + row * squareWidth + (squareWidth / 2)  ;

                    if (piece == LineGrinderState.X) {
                        g2.setColor(Color.BLACK);
                        g2.fill(new Ellipse2D.Double(xCenter - pieceDiameter / 2, yCenter - pieceDiameter / 2, pieceDiameter, pieceDiameter));
                    } else if (piece == LineGrinderState.O) {
                        g2.setColor(Color.RED);
                        g2.fill(new Ellipse2D.Double(xCenter - pieceDiameter / 2, yCenter - pieceDiameter / 2, pieceDiameter, pieceDiameter));
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
