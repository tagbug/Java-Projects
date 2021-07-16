package Client.GUI.Action;

import Client.util.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public abstract class ClientAction<T extends Component> extends AbstractAction {
    final ClientSocket clientSocket;// 客户端Socket处理对象
    final T component;// 显示弹窗的组件

    public ClientAction(String name, ClientSocket clientSocket, T component) {
        super(name);
        this.component = component;
        this.clientSocket = clientSocket;
    }

    /**
     * 连接异常捕获并提示重连
     *
     * @param fun 要执行的方法
     */
    void withConnectionReset(ThrowableFunction fun) {
        try {
            fun.run();
        } catch (IllegalStateException | IOException e) {
            // 连接失效
            e.printStackTrace();
            int choose = JOptionPane.showConfirmDialog(component, "与服务器未建立连接或连接失效，是否重新连接？", "提示",
                    JOptionPane.YES_NO_OPTION);
            if (choose == JOptionPane.YES_OPTION) {
                try {
                    clientSocket.enforceConnect();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(component, "建立连接失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * 将getValue()获取的Object以String返回
     *
     * @param key 键
     * @return String值
     */
    String getStrValue(String key) {
        return (String) getValue(key);
    }
}
