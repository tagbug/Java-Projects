package SwingExample;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class JFramExam {
    public static void main(String[] args) {
        JFrame window1 = new JFrame("第一个窗口");
        JFrame window2 = new JFrame("第一个窗口");
        Container con = window1.getContentPane();
        con.setBackground(Color.YELLOW);// 设置窗口的背景色
        window1.setBounds(60, 100, 288, 108);
        window2.setBounds(360, 100, 288, 108);
        window1.setVisible(true);
        window2.setVisible(true);
        window1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
