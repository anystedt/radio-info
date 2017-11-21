package View;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class RadioView {

    public RadioView(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");

        jMenuBar.add(menu);
        JMenuItem item = new JMenuItem("Item", KeyEvent.VK_T);
        menu.add(item);
        frame.setJMenuBar(jMenuBar);

        frame.pack();
        frame.setVisible(true);
    }
}
