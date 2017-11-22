package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RadioView {

    JButton update;

    public RadioView(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300,300));

        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");

        jMenuBar.add(menu);
        JMenuItem item = new JMenuItem("Item", KeyEvent.VK_T);
        menu.add(item);
        frame.setJMenuBar(jMenuBar);

        update = new JButton("Update");
        update.setMaximumSize(new Dimension(40, 40));

        frame.add(update);

        frame.pack();
        frame.setVisible(true);
    }

    public void setUpdateListener(ActionListener actionListener){
        update.addActionListener(actionListener);
    }
}
