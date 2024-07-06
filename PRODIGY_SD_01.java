import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PRODIGY_SD_01 {

    private JFrame frame;
    private JTextField entryTemperature;
    private JLabel labelResult;
    private ButtonGroup unitGroup;
    private JRadioButton celsiusButton;
    private JRadioButton fahrenheitButton;
    private JRadioButton kelvinButton;

    public PRODIGY_SD_01() {
        frame = new JFrame("Temperature Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter temperature:");
        frame.add(label);

        entryTemperature = new JTextField(10);
        frame.add(entryTemperature);

        unitGroup = new ButtonGroup();

        celsiusButton = new JRadioButton("Celsius");
        fahrenheitButton = new JRadioButton("Fahrenheit");
        kelvinButton = new JRadioButton("Kelvin");

        unitGroup.add(celsiusButton);
        unitGroup.add(fahrenheitButton);
        unitGroup.add(kelvinButton);

        frame.add(celsiusButton);
        frame.add(fahrenheitButton);
        frame.add(kelvinButton);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertTemperature();
            }
        });
        frame.add(convertButton);

        labelResult = new JLabel("");
        frame.add(labelResult);

        frame.setVisible(true);
    }

    private void convertTemperature() {
        try {
            double num = Double.parseDouble(entryTemperature.getText());
            String result = "";

            if (celsiusButton.isSelected()) {
                double fr = num * (9.0 / 5.0) + 32;
                double kl = num + 273.15;
                result = String.format("%.2f°F and %.2fK", fr, kl);
            } else if (kelvinButton.isSelected()) {
                double cs = num - 273.15;
                double fr = cs * (9.0 / 5.0) + 32;
                result = String.format("%.2f°F and %.2f°C", fr, cs);
            } else if (fahrenheitButton.isSelected()) {
                double cs = (num - 32) * (5.0 / 9.0);
                double kl = cs + 273.15;
                result = String.format("%.2f°C and %.2fK", cs, kl);
            } else {
                result = "Not a valid unit!";
            }

            labelResult.setText(result);
        } catch (NumberFormatException e) {
            labelResult.setText("Invalid input!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PRODIGY_SD_01();
            }
        });
    }
}
