package 通讯录;

import java.io.IOException;

import javax.swing.*;

public class MainWindow extends JFrame {
    JMenuBar menuBar;
    IOBridge ioBridge;
    
    JMenuItem outItem, showItem;

    MainWindow() throws IOException {
        _init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 500, 300);
        setTitle("通讯录");
        ioBridge = new IOBridge("通讯录/通讯录.txt");
    }
    
    void _init() {
        menuBar = new JMenuBar();
        outItem = new JMenuItem("录入");
        showItem = new JMenuItem("显示");
        JMenu menu = new JMenu("菜单选项");
        menu.add(outItem);
        menu.add(showItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    void setListenedFrame(JFrame out, JFrame show,JTextArea showArea) {
        outItem.addActionListener((e) -> {
            out.setVisible(true);
            show.setVisible(false);
        });
        showItem.addActionListener((e) -> {
            out.setVisible(false);
            try {
                showArea.setText(ioBridge.read());
            } catch (IOException e1) {
                showArea.setText(e1.toString());
            }
            show.setVisible(true);
        });
    }
}