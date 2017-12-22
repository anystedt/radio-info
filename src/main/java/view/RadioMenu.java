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

    public  RadioMenu(Frame frame){
        JMenu menu = new JMenu("Radio Info");
        add(menu);
        JMenuItem srLink = new JMenuItem("Go to SR");
        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(srLink);
        menu.add(exitItem);
        initiateListeners(srLink, exitItem, frame);
    }

    private void initiateListeners(JMenuItem srLink, JMenuItem exitItem, Frame frame){
        srLink.addActionListener((ActionEvent event) -> {
            String url = "http://sverigesradio.se/";
            Desktop desktop = java.awt.Desktop.getDesktop();
            try {
                desktop.browse(URI.create(url));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame,
                        "Something went wrong when creating link to " + url,
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        exitItem.addActionListener((ActionEvent event) -> System.exit(0));
    }
}
