/**
 * Created on 28/11/17
 * File: RadioMenu.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the menu of the program. Creates the menu and
 * initializes menu items with appropriate actions.
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;

public class RadioMenu extends JMenuBar{

    public  RadioMenu(){
        JMenu menu = new JMenu("Radio Info");
        add(menu);
        JMenuItem srLink = new JMenuItem("Go to SR");
        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(srLink);
        menu.add(exitItem);
        initiateListeners(srLink, exitItem);
    }

    private void initiateListeners(JMenuItem srLink, JMenuItem exitItem){
        srLink.addActionListener((ActionEvent event) -> {
            String url = "http://sverigesradio.se/";
            Desktop desktop = java.awt.Desktop.getDesktop();
            try {
                desktop.browse(URI.create(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        exitItem.addActionListener((ActionEvent event) -> System.exit(0));
    }
}
