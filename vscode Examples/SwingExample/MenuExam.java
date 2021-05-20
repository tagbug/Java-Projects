package SwingExample;

import javax.swing.*;
import java.awt.event.*;

public class MenuExam {
    public static void main(String[] args) {
        new WindowMenu("带菜单的窗口", 20, 30, 350, 190);
    }
}

class WindowMenu extends JFrame {
    JMenuBar menuBar;
    JMenu menu, subMenu;
    JMenuItem item1, item2;

    public WindowMenu(String s, int x, int y, int w, int h) {
        init(s);
        setLocation(x, y);
        setSize(w, h);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void init(String s) {
        setTitle(s);
        menuBar = new JMenuBar();
        menu = new JMenu("菜单");
        subMenu = new JMenu("软件项目");
        item1 = new JMenuItem("Java话题");
        item2 = new JMenuItem("动画话题");
        item1.setAccelerator(KeyStroke.getKeyStroke('A'));
        item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(item1);
        menu.addSeparator();
        menu.add(item2);
        menu.add(subMenu);// 把subMenu作为menu的一个菜单项
        subMenu.add(new JMenuItem("汽车销售系统"));
        subMenu.add(new JMenuItem("农场信息系统"));
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
}