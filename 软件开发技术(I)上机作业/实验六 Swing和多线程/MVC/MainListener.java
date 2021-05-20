package MVC;

import javax.swing.*;
import java.awt.event.*;

public class MainListener implements ActionListener {// 控制器
    JTabbedPane tabbedPane;
    JPanel colPanel;
    JTextArea showArea;

    MainListener(JTabbedPane tabbedPane, JPanel colPanel, JTextArea showArea) throws IllegalArgumentException {
        if (tabbedPane == null || colPanel == null || showArea == null)
            throw new IllegalArgumentException("传入了空的引用对象");
        this.tabbedPane = tabbedPane;
        this.colPanel = colPanel;
        this.showArea = showArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel selected = (JPanel) tabbedPane.getSelectedComponent();
        String choose = selected.getName();
        String strArgs[] = new String[selected.getComponentCount() / 2];
        for (int i = 0; i < selected.getComponentCount(); i++) {
            var com = selected.getComponent(i);
            if (com instanceof JTextField) {
                strArgs[i / 2] = ((JTextField) com).getText();
            }
        }
        double args[] = new double[strArgs.length];
        for (int i = 0; i < args.length; i++) {
            try {
                args[i] = Double.parseDouble(strArgs[i]);
            } catch (Exception exp) {
                showArea.setText(exp.toString() + "\n请检查输入数据格式是否正确！");
                return;
            }
        }
        double colHeight = 0;
        for (var com : colPanel.getComponents()) {
            if (com instanceof JTextField) {
                try {
                    colHeight = Double.parseDouble(((JTextField) com).getText());
                } catch (Exception exp) {
                    showArea.setText(exp.toString() + "\n请检查输入数据格式是否正确！");
                    return;
                }
            }
        }
        try {
            Column col = new Column();// 模型
            if (choose.equals("三角形")) {
                Triangle tri = new Triangle();// 模型
                tri.setBorder(args[0], args[1], args[2]);
                col.setArgs(tri, colHeight);
            } else if (choose.equals("梯形")) {
                Trapezium tra = new Trapezium(args[0], args[1], args[2]);// 模型
                col.setArgs(tra, colHeight);
            } else {
                throw new IllegalStateException("不支持的形状！");
            }
            showArea.setText("面积计算结果：" + col.computeVolume());
        } catch (Exception exp) {
            showArea.setText("计算时发生错误：\n" + exp.toString());
            return;
        }
    }
}
