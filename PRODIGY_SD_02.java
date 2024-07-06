import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PRODIGY_SD_02 {

    private JFrame frame;
    private JTextField startRangeField;
    private JTextField endRangeField;
    private JTextField guessField;
    private JButton startButton;
    private JButton guessButton;
    private JLabel resultLabel;
    private int targetNumber;
    private int guessCount;

    public PRODIGY_SD_02() {
        frame = new JFrame("Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel startRangeLabel = new JLabel("Starting Range:");
        startRangeField = new JTextField(5);

        JLabel endRangeLabel = new JLabel("Ending Range:");
        endRangeField = new JTextField(5);

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        guessField = new JTextField(5);
        guessButton = new JButton("Submit Guess");
        guessButton.setEnabled(false);
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });

        resultLabel = new JLabel("Enter range and start the game.");

        frame.add(startRangeLabel);
        frame.add(startRangeField);
        frame.add(endRangeLabel);
        frame.add(endRangeField);
        frame.add(startButton);
        frame.add(new JLabel("Your Guess:"));
        frame.add(guessField);
        frame.add(guessButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private void startGame() {
        try {
            int startRange = Integer.parseInt(startRangeField.getText());
            int endRange = Integer.parseInt(endRangeField.getText());

            if (startRange >= endRange) {
                resultLabel.setText("The ending number must be greater than the starting number.");
                return;
            }

            Random random = new Random();
            targetNumber = random.nextInt(endRange - startRange + 1) + startRange;
            guessCount = 0;
            guessButton.setEnabled(true);
            resultLabel.setText("Game started. Enter your guess.");

        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers.");
        }
    }

    private void makeGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            guessCount++;

            if (guess < targetNumber) {
                resultLabel.setText("Your guess is too low. Try again.");
            } else if (guess > targetNumber) {
                resultLabel.setText("Your guess is too high. Try again.");
            } else {
                resultLabel.setText("Congratulations! You guessed the number in " + guessCount + " attempts.");
                guessButton.setEnabled(false);
            }

        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PRODIGY_SD_02();
            }
        });
    }
}
