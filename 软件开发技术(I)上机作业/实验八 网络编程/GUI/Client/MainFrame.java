package GUI.Client;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.*;

import GUI.QueryRequest;

import java.awt.event.*;

/**
 * 主窗体
 * 
 * @since 10
 * @author TagBug {@link https://github.com/tagbug}
 */
public class MainFrame extends JFrame {
    private ClientSocket clientSocket = new ClientSocket();
    private static final String HOST = "localhost";// 服务器地址配置
    private static final int PORT = 6666;// 服务器端口配置

    private JTextArea showArea;

    MainFrame() {
        setTitle("查询数据库服务器");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(10, 10, 560, 260);
        init();
        setVisible(true);
    }

    /**
     * 主要负责窗体内容的布局和事件监听器的设置
     */
    void init() {
        var northPanel = new JPanel();
        var connButton = new JButton("连接服务器");
        connButton.addActionListener(e -> {
            try {
                clientSocket.connect(HOST, PORT);
            } catch (IllegalStateException | IOException e1) {
                JOptionPane.showMessageDialog(this, e1, "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "成功建立连接！", "提示", JOptionPane.INFORMATION_MESSAGE);
        });
        var idText = new JTextField(6);
        var nameText = new JTextField(6);
        var idButton = new JButton("按学号查询");
        idButton.addActionListener(new QueryActionListener(idText, QueryRequest.TYPE.ID));
        var nameButton = new JButton("按姓名查询");
        nameButton.addActionListener(new QueryActionListener(nameText, QueryRequest.TYPE.NAME));
        northPanel.add(connButton);
        northPanel.add(new JLabel("学号："));
        northPanel.add(idText);
        northPanel.add(idButton);
        northPanel.add(new JLabel("姓名："));
        northPanel.add(nameText);
        northPanel.add(nameButton);
        add(northPanel, BorderLayout.NORTH);
        showArea = new JTextArea();
        add(new JScrollPane(showArea), BorderLayout.CENTER);
    }

    /**
     * 查询请求监听器，用以不同的查询请求
     * <p>
     * 接收到数据后，进行处理并在showArea显示；当发生错误时，捕获异常并用弹窗显示
     */
    class QueryActionListener implements ActionListener {
        private JTextField textField;
        private QueryRequest.TYPE type;

        QueryActionListener(JTextField textField, QueryRequest.TYPE type) {
            this.textField = textField;
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                var result = clientSocket.query(new QueryRequest(type, textField.getText()));
                if (result.isEmpty()) {
                    showArea.append("未查询到相关结果！\n");
                } else {
                    for (var s : result) {
                        showArea.append(s);
                        showArea.append("\n");
                    }
                }
            } catch (IllegalStateException | IOException e1) {
                JOptionPane.showMessageDialog(getRootPane(), e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
