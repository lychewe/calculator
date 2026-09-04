import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360; 
    int boardHeight = 540;

    Color customLightBlue = new Color(200, 214, 225);
    Color customDarkBlue = new Color(33, 58, 79);
    Color customBlue = new Color(150, 177, 197);
    Color customDarkerBlue = new Color(74, 109, 135);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×",
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", "√", "="

    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};
 
    JFrame frame = new JFrame("𓂃˖ calculator ☘︎ ݁˖"); 
    JLabel displayLabel = new JLabel(); 
    JPanel displayPanel = new JPanel();  
    JPanel buttonsPanel = new JPanel(); 
    JButton acButton; //ac to c button
    
    //calculator inputs 
    String A = "0";
    String operator = null;
    String B = null;


    Calculator() { 
        //window setup
        frame.setVisible(true); 
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); 
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //user click on X button = terminates
        frame.setLayout(new BorderLayout());  

        //styling for the label
        displayLabel.setBackground(customDarkBlue);
        displayLabel.setForeground(Color.white); //text color 
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 76));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);
        displayLabel.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 8));


        //styling for the panel
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel); //put label inside panel
        frame.add(displayPanel, BorderLayout.NORTH); //put panel inside frame (the window)
         
        //setting up the buttons panel
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customDarkBlue);
        frame.add(buttonsPanel);

        for(int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];

            
            if(buttonValue.equals("AC")) {
                acButton = button;
            }
            
            
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false); 
            button.setBorder(new LineBorder(customDarkBlue)); 
            if(Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightBlue);
                button.setForeground(Color.black);
            }
            else if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customDarkerBlue);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(customBlue);
                button.setForeground(Color.white);
            }
            //tweaking to remove mac override
            button.setOpaque(true); //this is auto off on mac, turn on to actually have correct button color
            button.setBorderPainted(false); //no auto rounded button border so then nothing covers the color fill of the buttons
            
            buttonsPanel.add(button);

            //RECEIVING INPUT
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {  
                    JButton button = (JButton) event.getSource(); //if u click, now the click is a jbutton 
                    String buttonValue = button.getText();
                    if(button == acButton) {
                            clearAll();
                            displayLabel.setText("0");

                            updateACButton(false);
                        }
                    if(Arrays.asList(topSymbols).contains(buttonValue)) {
                        /* 
                        if(buttonValue.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0"); 
                        } */
                        if(buttonValue.equals("+/-")) {
                            double input = Double.parseDouble(displayLabel.getText());
                            input *= -1;
                            displayLabel.setText(removeZeroDecimal(input));
                            updateACButton(true);
                        }
                        else if(buttonValue.equals("%")) {
                            double input = Double.parseDouble(displayLabel.getText());
                            input /= 100;
                            displayLabel.setText(removeZeroDecimal(input));
                            updateACButton(true);
                        }
                    }
                    else if(Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if(buttonValue.equals("=")) {
                            if(A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B); 

                                if(operator.equals("+")) {
                                    displayLabel.setText(removeZeroDecimal(numA  + numB)); 
                                }
                                
                                else if(operator.equals("-")) {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                }

                                else if(operator.equals("×")) {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                }
                                else if(operator.equals("÷")) {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }
                        }
                        else if("+-×÷".contains(buttonValue)) {
                            if(operator == null) { //so that operator buttons aren't clicked on twice b4 equal button
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue; //operator will be most recently clicked on one
                        }

                    }
                    else { //0-9, .
                        if(buttonValue.equals(".")) {
                            if(!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                            updateACButton(true);
                        }
                        else if("0123456789".contains(buttonValue)) {
                            if(displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonValue); 
                            }
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue); //concatenates
                            }
                            updateACButton(true);
                        }
                        else if(buttonValue.equals("√")) { //sqrt operator
                            String s = displayLabel.getText();
                            double input = Double.parseDouble(s);
                            if(input >= 0) {
                                double sqrtinput = Math.sqrt(input);
                                displayLabel.setText(removeZeroDecimal(sqrtinput));
                                /* if((sqrtinput % 1) == 0) {
                                    int integerinput = (int) sqrtinput;
                                    displayLabel.setText(Integer.toString(integerinput));
                                }
                                else {
                                    displayLabel.setText(Double.toString(sqrtinput));
                                } */
                               updateACButton(true);
                            }
                            else {
                                displayLabel.setText("Error");
                            }

                        }
                    }
                }
            }); 
        }
    }
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double value) {
        if((value % 1) == 0) {
            return Integer.toString((int) value);
        }
        return Double.toString(value);
    }

    void updateACButton(boolean theInput) {
        if(theInput == true) {
            acButton.setText("C");
        }
        else {
            acButton.setText("AC");
        }
    }
}
