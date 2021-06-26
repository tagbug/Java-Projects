package Tester;

import javax.swing.*;
import java.awt.*;

/**
 * @TextComponentFrame giaogiao
 */
public class TextComponentFrame extends JFrame {
    public static final int TEXTAREA_ROWS = 8;
    public static final int TEXTAREA_COLUMNS = 20;

    public TextComponentFrame(){
        var textField = new JTextField();
        var passWorldField = new JPasswordField();

        var northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2,2));
        northPanel.add(new Label("User name:",SwingConstants.RIGHT));
        northPanel.add(textField);
        northPanel.add(new Label("Password:",SwingConstants.RIGHT));
        northPanel.add(passWorldField);

        add(northPanel,BorderLayout.NORTH);
        var textArea = new JTextArea(TEXTAREA_ROWS,TEXTAREA_COLUMNS);
        var scrollPane = new JScrollPane(textArea);

        add(scrollPane,BorderLayout.CENTER);

        var southPanel = new JPanel();

        var insertButton = new JButton("Insert");
        southPanel.add(insertButton);
        insertButton.addActionListener(e ->
            textArea.append("User name:" + textField.getText() + "PassWorld:" + new String(passWorldField.getPassword()) + "\n"));

        add(southPanel,BorderLayout.SOUTH);
        pack();
    }
}
