package 序列号;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final String[] DEFAULT_SERIAL = { "111111", "222222", "333333" };

    MainWindow() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JTextField textField[] = new JTextField[3];
        MainListener listener = new MainListener(textField);
        for (int i = 0; i < textField.length; i++) {
            textField[i] = new JTextField(7);
            textField[i].addKeyListener(listener);
            textField[i].addFocusListener(listener);
            add(textField[i]);
        }
    }
}
