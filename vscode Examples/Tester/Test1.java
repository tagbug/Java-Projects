package Tester;

import javax.swing.*;
import java.awt.*;

public class Test1 {
    public static void main(String[] args){
        EventQueue.invokeLater(()->{
            var frame = new TextComponentFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        });
    }
}
