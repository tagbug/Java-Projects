package SwingExample;

import javax.swing.*;
import java.awt.*;

public class NotHalloWorld {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new NotHelloWorldFrame();
            frame.setTitle("NotHalloWorld");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}


class NotHelloWorldFrame extends JFrame {
    public NotHelloWorldFrame() {
        add(new NotHelloWorldComponent());
        pack();
    }
}


class NotHelloWorldComponent extends JComponent {
    public static final int MESSAGE_X = 75;
    public static final int MESSAGE_Y = 100;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    @Override
    public void paintComponent(Graphics g) {
        g.drawString("Not a Hello,World program", MESSAGE_X, MESSAGE_Y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
