import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Calculator implements ActionListener {

    //create window
    JFrame window = new JFrame("Calculator");

    //create output screens
    JTextField textField = new JTextField();

    //create buttons
    JButton[] numButtons = new JButton[10];
    JButton[] funcationbuttons = new JButton[9];
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JButton multiply = new JButton("*");
    JButton divide = new JButton("/");
    JButton negative = new JButton("(-)");
    JButton equals = new JButton("=");
    JButton clear = new JButton("Clear");
    JButton delete = new JButton("Delete");
    JButton decimal = new JButton(".");

    JPanel panel = new JPanel();

    //set font
    Font font = new Font("ariel", Font.BOLD, 30);

    char operator;
    double num1=0,num2=0,result=0;


    Calculator(){
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(420, 550);
        window.setLayout(null);
        window.setVisible(true);

        textField.setBounds(50, 25, 300, 50);
        textField.setFont(font);
        textField.setEditable(false);
        window.add(textField);

        funcationbuttons[0] = plus;
        funcationbuttons[1] = minus;
        funcationbuttons[2] = multiply;
        funcationbuttons[3] = divide;
        funcationbuttons[4] = negative;
        funcationbuttons[5] = equals;
        funcationbuttons[6] = clear;
        funcationbuttons[7] = delete;
        funcationbuttons[8] = decimal;

        for(int i = 0; i < 9; i++)
        {
            funcationbuttons[i].addActionListener(this);
            funcationbuttons[i].setFont(font);
            funcationbuttons[i].setFocusable(false);
        }

        for(int i =0;i<10;i++) {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].addActionListener(this);
            numButtons[i].setFont(font);
            numButtons[i].setFocusable(false);
        }

        delete.setBounds(270,430,120,50);
        clear.setBounds(150,430,120,50);
        negative.setBounds(20,430,120,50);
        window.add(delete);
        window.add(clear);
        window.add(negative);
        window.add(panel);

        panel.setBounds(50, 100 , 300, 300);
        panel.setLayout(new GridLayout(4,4,5,5));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(numButtons[1]);
        panel.add(numButtons[2]);
        panel.add(numButtons[3]);
        panel.add(plus);
        panel.add(numButtons[4]);
        panel.add(numButtons[5]);
        panel.add(numButtons[6]);
        panel.add(minus);
        panel.add(numButtons[7]);
        panel.add(numButtons[8]);
        panel.add(numButtons[9]);
        panel.add(multiply);
        panel.add(numButtons[0]);
        panel.add(divide);
        panel.add(equals);
        panel.add(decimal);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0; i < 10; i++)
        {
            if(e.getSource() == numButtons[i]){
                textField.setText(textField.getText().concat(String.valueOf(i)));
            }
        }

        if(e.getSource()==decimal)
        {
            textField.setText(textField.getText().concat("."));
        }
        if(e.getSource()==plus) {
            num1 = Double.parseDouble(textField.getText());
            operator ='+';
            textField.setText("");
        }
        if(e.getSource()==minus) {
            num1 = Double.parseDouble(textField.getText());
            operator ='-';
            textField.setText("");
        }
        if(e.getSource()==multiply) {
            num1 = Double.parseDouble(textField.getText());
            operator ='*';
            textField.setText("");
        }
        if(e.getSource()==divide) {
            num1 = Double.parseDouble(textField.getText());
            operator ='/';
            textField.setText("");
        }
        if(e.getSource()==equals) {
            num2 = Double.parseDouble(textField.getText());

            switch(operator) {
                case'+':
                    result=num1+num2;
                    break;
                case'-':
                    result=num1-num2;
                    break;
                case'*':
                    result=num1*num2;
                    break;
                case'/':
                    result=num1/num2;
                    break;
            }
            textField.setText(String.valueOf(result));
            num1=result;

        }

        if(e.getSource()==clear){
            textField.setText("");
        }

        if(e.getSource()==delete){
            String str = textField.getText();
            textField.setText("");
            for(int i=0; i < str.length()-1; i++){
                textField.setText(textField.getText()+str.charAt(i));
            }
        }

        if(e.getSource()==negative){
            double temp = Double.parseDouble(textField.getText());
            temp *= -1;
            textField.setText(String.valueOf(temp));
        }

    }
}
