import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private JTextField[][] cells = new JTextField[9][9];
    private SudokuSolver solver = new SudokuSolver();

    public SudokuGUI() {
        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));

                for (int i = 0; i <= 9; i++) {
                    if (i % 3 == 0) {
                        g2.setStroke(new BasicStroke(3));
                    } else {
                        g2.setStroke(new BasicStroke(1));
                    }
                    g.drawLine(0, i * (getHeight() / 9), getWidth(), i * (getHeight() / 9));
                    g.drawLine(i * (getWidth() / 9), 0, i * (getWidth() / 9), getHeight());
                }
            }
        };
        gridPanel.setLayout(new GridLayout(9, 9));
        Font font = new Font("Arial", Font.BOLD, 20);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(font);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                gridPanel.add(cells[row][col]);
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.setFont(new Font("Arial", Font.BOLD, 20));
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = getBoard();
                if (solver.solveSudoku(board)) {
                    setBoard(board);
                } else {
                    JOptionPane.showMessageDialog(SudokuGUI.this, "No solution exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(gridPanel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);
    }

    private int[][] getBoard() {
        int[][] board = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = cells[row][col].getText();
                if (!text.isEmpty()) {
                    board[row][col] = Integer.parseInt(text);
                } else {
                    board[row][col] = 0;
                }
            }
        }
        return board;
    }

    private void setBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setText(Integer.toString(board[row][col]));
            }
        }
    }
}
