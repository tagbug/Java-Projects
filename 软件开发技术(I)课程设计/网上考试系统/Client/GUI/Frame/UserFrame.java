package Client.GUI.Frame;

import Client.GUI.Action.*;
import Client.util.*;
import Data.*;
import Data.ClientRequest.*;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * 用户窗体，描述登录的用户的基本信息
 */
public abstract class UserFrame extends JFrame {
    final ClientSocket clientSocket;
    final JFrame loginFrame;

    public UserFrame(ClientSocket clientSocket, JFrame loginFrame, Map<String, String> userInfo, String userType) {
        this.clientSocket = clientSocket;
        this.loginFrame = loginFrame;
        setBounds(10, 10, 700, 400);
        setTitle(userInfo.get("userName") + "-" + userType + " ID:" + userInfo.get("id"));// 标题显示用户信息
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        validate();
        setVisible(true);
    }

    /**
     * 初始化布局&监听器
     */
    abstract void init();

    /**
     * 连接异常处理
     *
     * @param fun 要执行的方法
     */
    final void withConnectErrorCatch(ThrowableFunction fun) {
        try {
            fun.run();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            loginFrame.setVisible(true);
            this.dispose();
        }
    }

    /**
     * 从服务器获取题目库列表
     *
     * @return 题目库列表
     * @throws IllegalStateException 服务器错误
     * @throws IOException           连接异常
     */
    final public ArrayList<Map<String, String>> getQuestionList() throws IllegalStateException,IOException {
        var request = new ClientRequest();
        request.setRequestType(TYPE.GetQuestionList);
        var rs = clientSocket.query(request);
        if (rs.isSucceed()) {
            return rs.getResult();
        }
        throw new IllegalStateException("连接错误！" + rs.getFailReason());
    }

    final public JFrame getLoginFrame() {
        return loginFrame;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }
}
