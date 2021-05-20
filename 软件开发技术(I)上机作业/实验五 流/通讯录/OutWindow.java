package 通讯录;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class OutWindow extends MainWindow {
    OutWindow() throws IOException {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.getContentPane().setBackground(Color.cyan);
        init();
        setVisible(true);
    }

    void init() {
        JButton outButton = new JButton("录入");
        JLabel labels[] = new JLabel[3];
        String labelTexts[] = { "姓名", "email", "电话" };
        JTextField texts[] = new JTextField[3];
        for (int i = 0; i < texts.length; i++) {
            labels[i] = new JLabel("输入" + labelTexts[i]);
            texts[i] = new JTextField(20);
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.setBorder(new EmptyBorder(0, 200, 0, 200));
            panel.setBackground(this.getContentPane().getBackground());
            panel.add(labels[i]);
            panel.add(texts[i]);
            add(panel);
        }
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(new EmptyBorder(0, 200, 0, 200));
        panel.setBackground(this.getContentPane().getBackground());
        panel.add(new JLabel("单击录入"));
        panel.add(outButton);
        add(panel);
        outButton.addActionListener((e) -> {
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "还没有输入" + labelTexts[i], "错误", JOptionPane.ERROR_MESSAGE);
                    texts[i].grabFocus();
                    return;
                }
            }
            try {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < texts.length; i++) {
                    sb.append(labelTexts[i]);
                    sb.append('：');
                    sb.append(texts[i].getText());
                    sb.append('\t');
                }
                ioBridge.write(sb.toString());
                JOptionPane.showMessageDialog(this, "录入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < texts.length; i++) {
                    texts[i].setText(null);
                }
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, e1.toString(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
