package MVC;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {// 视图
    MainWindow() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JPanel pNorth = new JPanel();
        JTabbedPane p = new JTabbedPane(JTabbedPane.LEFT);
        JPanel tri = new JPanel();
        tri.setName("三角形");
        tri.setLayout(new FlowLayout());
        tri.add(new JLabel("边A"));
        tri.add(new JTextField(5));
        tri.add(new JLabel("边B"));
        tri.add(new JTextField(5));
        tri.add(new JLabel("边C"));
        tri.add(new JTextField(5));
        p.add("三角底", tri);
        JPanel tra = new JPanel();
        tra.setName("梯形");
        tra.setLayout(new FlowLayout());
        tra.add(new JLabel("上底"));
        tra.add(new JTextField(5));
        tra.add(new JLabel("下底"));
        tra.add(new JTextField(5));
        tra.add(new JLabel("高"));
        tra.add(new JTextField(5));
        p.add("梯形底", tra);
        pNorth.add(p);
        JPanel col = new JPanel();
        col.add(new JLabel("柱体高"));
        col.add(new JTextField(5));
        JButton computeButton = new JButton("计算体积");
        col.add(computeButton);
        pNorth.add(col);
        add(pNorth, BorderLayout.NORTH);
        JTextArea showArea = new JTextArea();
        add(new JScrollPane(showArea));
        MainListener listener = new MainListener(p, col, showArea);
        computeButton.addActionListener(listener);
    }
}
