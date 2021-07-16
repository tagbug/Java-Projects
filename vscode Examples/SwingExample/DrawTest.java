package SwingExample;

import java.awt.*;
import javax.swing.*;

public class DrawTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new DrawFrame();
            frame.setTitle("DrawTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class DrawFrame extends JFrame {
    public DrawFrame() {
        add(new DrawComponent());
        pack();
    }
}

class DrawComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    @Override
    protected void paintComponent(Graphics g) {
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
