package SwingExample;

import javax.swing.*;
import java.awt.*;

public class MVCExam {
    public static void main(String[] args) {
        var win = new MVCWin();
        win.setBounds(10, 10, 460, 260);
        win.setTitle("MVC结构");
    }
}

class MVCWin extends JFrame {
    MVCWin() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        Triangle triangle = new Triangle();
        JTextField text[] = new JTextField[3];
        JTextArea showArea = new JTextArea();
        JButton button = new JButton("计算面积");
        for (int i = 0; i < text.length; i++) {
            text[i] = new JTextField(5);
        }
        JPanel pNorth = new JPanel();
        pNorth.add(new JLabel("边A:"));
        pNorth.add(text[0]);
        pNorth.add(new JLabel("边B:"));
        pNorth.add(text[1]);
        pNorth.add(new JLabel("边B:"));
        pNorth.add(text[2]);
        pNorth.add(button);
        button.addActionListener((e) -> {
            showArea.setText(null);
            try {
                double a, b, c;
                a = Double.parseDouble(text[0].getText().trim());
                b = Double.parseDouble(text[1].getText().trim());
                c = Double.parseDouble(text[2].getText().trim());
                triangle.setBorder(a, b, c);
                showArea.append("三角形" + a + ", " + b + ", " + c + " 的面积： ");
                showArea.append(triangle.computeArea() + "\n");
            } catch (Exception ee) {
                showArea.setText(ee.toString());
            }
        });
        add(pNorth, BorderLayout.NORTH);
        add(new JScrollPane(showArea), BorderLayout.CENTER);
    }

    class Triangle {
        private double a, b, c;
        private boolean canCompute;

        Triangle() {
        }

        public double computeArea() throws IllegalArgumentException, IllegalStateException {
            if (!canCompute) {
                throw new IllegalStateException("边长未被正确赋值！");
            }
            if (a + b > c && a - b < c) {
                double halfC = (a + b + c) / 2;
                return Math.sqrt(halfC * (halfC - a) * (halfC - b) * (halfC - c));
            } else {
                throw new IllegalArgumentException(a + ", " + b + ", " + c + " 不符合构成三角形的条件！");
            }
        }

        public void setBorder(double a, double b, double c) throws IllegalArgumentException {
            if (a > 0 && b > 0 && c > 0) {
                this.a = a;
                this.b = b;
                this.c = c;
                canCompute = true;
            } else {
                canCompute = false;
                throw new IllegalArgumentException("边长不能为负数！");
            }
        }
    }
}