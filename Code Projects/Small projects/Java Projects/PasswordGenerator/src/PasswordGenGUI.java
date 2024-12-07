import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class PasswordGenGUI extends JFrame {
    private PasswordGen passwordGen;

    public PasswordGenGUI() {
        super("password Generator");

        //window setup
        setSize(500, 550);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //initialise password generator
        passwordGen = new PasswordGen();

        //GUI Componants
        addGuiCom();
    }

    private void addGuiCom()
    {
        //label
        JLabel title = new JLabel("Password Generator");
        title.setFont((new Font("Dialog", Font.BOLD, 30)));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0,20,500,50);
        add(title);

        //password output
        JTextArea genPassword = new JTextArea();
        genPassword.setEditable(false);
        genPassword.setFont(new Font("dialog",Font.BOLD, 32));
        JScrollPane passwordOutputPane = new JScrollPane(genPassword);
        passwordOutputPane.setBounds(25,100,450,70);
        add(passwordOutputPane);

        //password length label
        JLabel passLength = new JLabel("Password length");
        passLength.setFont(new Font("Dialog",Font.PLAIN,32));
        passLength.setBounds(25,215,250,40);
        add(passLength);

        //toggle buttons
        JToggleButton upperCaseButton = new JToggleButton("Uppercase");
        JToggleButton lowerCaseButton = new JToggleButton("Lowercase");
        JToggleButton symbolButton = new JToggleButton("Symbol");
        JToggleButton numberButton = new JToggleButton("Number");
        upperCaseButton.setFont(new Font("dialog",Font.BOLD, 32));
        lowerCaseButton.setFont(new Font("dialog",Font.BOLD, 32));
        symbolButton.setFont(new Font("dialog",Font.BOLD, 32));
        numberButton.setFont(new Font("dialog",Font.BOLD, 32));
        upperCaseButton.setBounds(25,300, 220,60);
        lowerCaseButton.setBounds(25,380, 220,60);
        symbolButton.setBounds(255,300, 220,60);
        numberButton.setBounds(255,380, 220,60);
        add(upperCaseButton);
        add(lowerCaseButton);
        add(symbolButton);
        add(numberButton);

        //user length input area
        JTextArea passwordLengthInput = new JTextArea();
        passwordLengthInput.setFont(new Font("dialog",Font.BOLD, 32));
        passwordLengthInput.setBounds(275,215, 200,40);
        add(passwordLengthInput);

        //geneerate passsword button
        JButton generateButton = new JButton("Generate Password");
        generateButton.setFont(new Font("dialog",Font.BOLD, 32));
        generateButton.setBounds(25,450,450,40);
        add(generateButton);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //button to create password when length number added and one toogle on
                if(passwordLengthInput.getText().length() <= 0 ) return;
                boolean toggleselected = lowerCaseButton.isSelected() ||
                upperCaseButton.isSelected() || symbolButton.isSelected() || numberButton.isSelected();

                //convert user input to number
                int passwordLength = Integer.parseInt(passwordLengthInput.getText());
                if (toggleselected){
                    String createdPassword = passwordGen.generatedPassword(passwordLength, lowerCaseButton.isSelected(),
                            upperCaseButton.isSelected(), numberButton.isSelected(), symbolButton.isSelected()     );

                    //display password
                    genPassword.setText(createdPassword);
                }

            }
        });






    }

}