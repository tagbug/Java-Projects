package SwingExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileChooserExam {
    public static void main(String[] args) {
        var win = new WindowFile();
        win.setBounds(10, 10, 520, 500);
        win.setTitle("文件选择器");
    }
}

class WindowFile extends JFrame implements ActionListener {
    JFileChooser fileChooser;
    JMenuItem saveItem, loadItem;
    JTextArea t;

    WindowFile() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        t = new JTextArea(26, 44);
        t.setFont(new Font("楷体_gb2312", Font.PLAIN, 12));
        add(new JScrollPane(t), BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("文件");
        saveItem = new JMenuItem("保存文件");
        loadItem = new JMenuItem("打开文件");
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        menu.add(saveItem);
        menu.add(loadItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveItem) {
            int state = fileChooser.showSaveDialog(this);
            if (state == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(t.getText());
                    bw.flush();
                    bw.close();
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(this, ee.toString(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else if (state == JFileChooser.CANCEL_OPTION) {
                System.out.println("用户取消了操作！");
            }
        } else if (e.getSource() == loadItem) {
            int state = fileChooser.showOpenDialog(this);
            if (state == JFileChooser.APPROVE_OPTION) {
                try {
                    t.setText(null);
                    File file = fileChooser.getSelectedFile();
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        t.append(s + '\n');
                    }
                    br.close();
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(this, ee.toString(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("用户取消了操作！");
            }
        } else {
            System.out.println(e.getSource().toString());
        }
    }
}
