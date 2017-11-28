package View;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class RadioMenu extends JMenuBar{

    public  RadioMenu(){
        JMenu menu = new JMenu("Radio Info");
        add(menu);
        JMenuItem item = new JMenuItem("Item", KeyEvent.VK_T);
        menu.add(item);
    }
}
