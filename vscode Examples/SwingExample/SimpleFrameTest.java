package SwingExample;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

public class SimpleFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {// 以事件分配线程处理
            try {
                var frame = new SimpleFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
}

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame() throws MalformedURLException {
        // setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationByPlatform(true);
        setIconImage(new ImageIcon(new URL("https://cdn.jsdelivr.net/gh/tagbug/demo@1.0/img/avatar.png")).getImage());
    }
}
