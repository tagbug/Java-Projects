package 通讯录;

import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class ShowWindow extends MainWindow {
    JTextArea showArea;

    ShowWindow() throws IOException {
        super();
        init();
    }

    void init() {
        showArea = new JTextArea();
        add(new JScrollPane(showArea), BorderLayout.CENTER);
    }

    public JTextArea getShowArea() {
        return showArea;
    }
}
