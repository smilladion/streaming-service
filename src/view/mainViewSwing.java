package view;

import javax.swing.*;
import java.awt.*;

public class mainViewSwing extends JFrame {

    public mainViewSwing() {
        super("mainView");
        makeFrame();
    }

    private void makeFrame() {
        Container pane = getContentPane();
        JLabel label = new JLabel("hejsa");
        pane.add(label);

        JButton button = new JButton("en knap");
        JButton button2 = new JButton("en anden knap");
        button.addActionListener(e -> System.out.println("KLIK"));
        button2.addActionListener(e -> System.out.println("Klik2"));
        pane.add(button);

        pack();
        setVisible(true);
    }

}
