package SwingExample;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.*;

public class DocumentEventExam {
    public static void main(final String[] args) {
        final var win = new WindowTextSort();
        win.setBounds(10, 10, 200, 160);
        win.setTitle("处理DocumentEvent事件");
    }
}

class WindowTextSort extends JFrame {
    WindowTextSort() {
        init();
        setLayout(new FlowLayout());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JTextArea area1, area2;
        area1 = new JTextArea(6, 8);
        area2 = new JTextArea(6, 8);
        add(new JScrollPane(area1));
        add(new JScrollPane(area2));
        final PoliceListen listen = new PoliceListen();
        listen.setInputArea(area1);
        listen.setShowArea(area2);
        area1.getDocument().addDocumentListener(listen);
    }

    class PoliceListen implements DocumentListener {
        JTextArea inputArea, showArea;

        public void changedUpdate(final DocumentEvent e) {
            final String str = inputArea.getText();
            final String regex = "[^a-zA-Z]+";
            final String words[] = str.split(regex);
            Arrays.sort(words);
            showArea.setText(null);
            for (final String word : words) {
                showArea.append(word + '\n');
            }
        }

        public void removeUpdate(final DocumentEvent e) {
            changedUpdate(e);
        }

        public void insertUpdate(final DocumentEvent e) {
            changedUpdate(e);
        }

        public JTextArea getInputArea() {
            return inputArea;
        }

        public void setInputArea(final JTextArea inputArea) {
            this.inputArea = inputArea;
        }

        public JTextArea getShowArea() {
            return showArea;
        }

        public void setShowArea(final JTextArea showArea) {
            this.showArea = showArea;
        }

    }
}