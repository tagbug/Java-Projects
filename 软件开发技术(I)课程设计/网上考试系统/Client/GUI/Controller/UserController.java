package Client.GUI.Controller;

import Client.GUI.Action.*;
import Client.GUI.Frame.*;
import Client.GUI.Panel.*;
import Client.util.*;
import Data.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * 用户窗体控制器，描述用户窗体的基本交互行为
 * @param <T> 用户题库布局的子类
 */
public abstract class UserController<T extends UserQuestionPanel> implements ActionListener {
    final ClientSocket clientSocket;// 客户端Socket处理对象
    final ArrayList<T> questionPanel;// 试题库页面容器
    T nowPanel;// 目前的题目库容器
    ArrayList<QuestionPanel> qPanels;// 题目容器
    final JTabbedPane tabbedPanel;// 选项卡Panel
    final UserFrame window;// 窗口
    ArrayList<Map<String, String>> nowQuestionList;// 目前的题目信息数组
    int nowPos;// 目前题目的位置
    int maxPos;// 最大题目位置

    public UserController(UserFrame window, JTabbedPane tabbedPane, ArrayList<T> questionPanel) {
        this.window = window;
        this.clientSocket = window.getClientSocket();
        this.tabbedPanel = tabbedPane;
        this.questionPanel = questionPanel;
    }

    // 由目前选项卡所选题目库更新Controller状态信息
    void init() {
        nowPanel = questionPanel.get(tabbedPanel.getSelectedIndex());
        nowQuestionList = nowPanel.getQuestionList();
        qPanels = nowPanel.getQPanels();
        var pos = nowPanel.getPosLabel().getText().split("[/]");
        nowPos = Integer.parseInt(pos[0]);
        maxPos = Integer.parseInt(pos[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        init();// 由目前选项卡所选题目库更新Controller状态信息
        var source = (JComponent) e.getSource();
        handleAction(source);// 针对不同Action处理
        // 更新视图
        nowPanel.getPosLabel().setText(nowPos + "/" + maxPos);
        window.validate();
        window.repaint();
    }

    abstract void handleAction(JComponent source);

    /**
     * 带有{@code 连接异常处理}和{@code 弹窗消息显示}的服务器请求处理
     *
     * @param request     客户端请求对象
     * @param showMessage 是否显示操作结果弹窗
     * @return 操作是否成功
     */
    boolean withErrorCatch(ClientRequest request, boolean showMessage) {
        try {
            var rs = clientSocket.query(request);
            if (rs.isSucceed()) {
                if (showMessage)
                    JOptionPane.showMessageDialog(window, "操作成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                if (showMessage)
                    JOptionPane.showMessageDialog(window, "操作失败！\n原因：" + rs.getFailReason(), "错误",
                            JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
        return false;
    }

    /**
     * 带有{@code 连接异常处理}的服务器请求处理，返回服务器响应包
     *
     * @param request 客户端请求对象
     * @return 服务器响应包
     */
    ServerResponse withReturnErrorCatch(ClientRequest request) {
        try {
            return clientSocket.query(request);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
        return null;
    }

    /**
     * 执行一个Function 并带有{@code 连接异常处理}
     *
     * @param c UserFrame.ThrowableFunction对象
     */
    void updateWithErrorCatch(ThrowableFunction c) {
        try {
            c.run();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(window, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            window.getLoginFrame().setVisible(true);
            window.dispose();
            Thread.currentThread().interrupt();
        }
    }

}
