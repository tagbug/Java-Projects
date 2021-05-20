package SwingExample;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class ItemEventExam {
    public static void main(String[] args) {
        var win = new WindowItemEvent();
        win.setBounds(100, 100, 550, 340);
        win.setTitle("处理ItemEvent事件");
    }
}

class WindowItemEvent extends JFrame {
    JComboBox<String> choice;
    JTextArea textShow;
    PoliceListen listener;

    public WindowItemEvent() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        setLayout(new FlowLayout());
        choice = new JComboBox<String>();
        choice.addItem("请选择文件：");
        File dir = new File("D:/repos/Java Projects/vscode Examples/" + this.getClass().getPackageName());
        File[] files = dir.listFiles((file, name) -> name.endsWith(".java"));
        for (File file : files) {
            choice.addItem(file.getAbsolutePath());
        }
        textShow = new JTextArea(15, 45);
        listener = new PoliceListen();
        listener.setChoice(choice);
        listener.setTextShow(textShow);
        choice.addItemListener(listener);// choice是事件源，listener是监视器
        add(choice);
        add(new JScrollPane(textShow));
    }
    
    class PoliceListen implements ItemListener {
        JComboBox<String> choice;
        JTextArea textShow;

        public void itemStateChanged(ItemEvent e) {
            textShow.setText(null);
            try {
                String fileName = choice.getSelectedItem().toString();
                File file = new File(fileName);
                FileReader in = new FileReader(file);
                BufferedReader reader = new BufferedReader(in);
                String s = null;
                while ((s = reader.readLine()) != null) {
                    textShow.append(s + "\n");
                }
                reader.close();
            } catch (EOFException eof) {
                textShow.append("到达文件末尾！");
            } catch (Exception err) {
                textShow.append(err.toString());
            }
        }

        public void setChoice(JComboBox<String> choice) {
            this.choice = choice;
        }

        public void setTextShow(JTextArea textShow) {
            this.textShow = textShow;
        }

    }
}