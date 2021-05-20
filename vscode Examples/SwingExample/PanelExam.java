package SwingExample;

import java.awt.*;
import javax.swing.*;

public class PanelExam extends JFrame{
    JTabbedPane p;

    public PanelExam() {
        setBounds(100, 100, 500, 300);
        setVisible(true);
        p = new JTabbedPane(JTabbedPane.LEFT);
        p.add("观看FlowLayout", new FlowLayoutJPanel());
        p.add("观看GridLayout", new GridLayoutJPanel());
        p.add("观看BorderLayout", new BorderLayoutJPanel());
        // p.validate();
        add(p, BorderLayout.CENTER);
        // validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new PanelExam();
    }
    
    class FlowLayoutJPanel extends JPanel {
        FlowLayoutJPanel() {
            add(new JLabel("FlowLayout布局的面板"));
            add(new JButton("dog.jpg"));
            add(new JScrollPane(new JTextArea(12, 15)));
        }
    }

    class GridLayoutJPanel extends JPanel {
        GridLayoutJPanel() {
            GridLayout grid = new GridLayout(12, 12);// 网格布局
            setLayout(grid);
            Label label[][] = new Label[12][12];
            for (int i = 0; i < label.length; i++) {
                for (int j = 0; j < label[0].length; j++) {
                    label[i][j] = new Label();
                    if ((i + j) % 2 == 0)
                        label[i][j].setBackground(Color.black);
                    else
                        label[i][j].setBackground(Color.white);
                    add(label[i][j]);
                }
            }
        }
    }

    class BorderLayoutJPanel extends JPanel {
        BorderLayoutJPanel() {
            setLayout(new BorderLayout());
            add(new JButton("北"), BorderLayout.NORTH);
            add(new JButton("南"), BorderLayout.SOUTH);
            add(new JButton("东"), BorderLayout.EAST);
            add(new JButton("西"), BorderLayout.WEST);
            add(new JTextArea("中心"), BorderLayout.CENTER);
            validate();
        }
    }
}