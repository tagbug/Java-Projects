package 序列号;

import java.awt.event.*;
import javax.swing.*;

public class MainListener implements KeyListener, FocusListener {
    private JTextField textField[] = new JTextField[3];

    MainListener(JTextField[] t) {
        textField = t;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JTextField t = (JTextField) e.getSource();
        if (t.getCaretPosition() >= 5) {
            if (t == textField[textField.length - 1]) {
                textField[textField.length - 1].setText(textField[textField.length - 1].getText() + e.getKeyChar());
                for (int i = 0; i < textField.length; i++) {
                    if (!textField[i].getText().equals(MainWindow.DEFAULT_SERIAL[i])) {
                        JOptionPane.showMessageDialog(t.getRootPane(), "序列号错误！", "错误", JOptionPane.ERROR_MESSAGE);
                        for (JTextField f : textField) {
                            f.setText(null);
                        }
                        return;
                    }
                }
                JOptionPane.showMessageDialog(t.getRootPane(), "序列号正确！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                t.transferFocus();
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField t = (JTextField) e.getSource();
        t.setText(null);
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

}
