/**
 * Created on 22/11/17
 * File: RadioView.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the view. Creates the GUI for the application
 * that handles the communication between the user and the program.
 * Makes the information about channels and programs visible to the
 * user and receives actions from the user and present appropriate
 * feedback.
 */

package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class RadioView {

    private JButton updateButton;
    private JFrame frame;
    private JPanel channelList;
    private ChannelInfo channelInfo;

    public RadioView(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(650,80));
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setLayout(new BorderLayout());

        frame.setJMenuBar(new RadioMenu());
        frame.add(createChannelList(), BorderLayout.LINE_START);

        updateButton = new JButton("Uppdatera");
        channelInfo = new ChannelInfo(updateButton);
        frame.add(channelInfo);

        frame.pack();
    }

    public JScrollPane createChannelList(){
        channelList = new JPanel();
        channelList.setLayout(new GridLayout(0,1, 2, 5));

        JScrollPane scrollArea = new JScrollPane(channelList);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollArea;
    }

    public JLabel createChannelLabel(String name, String imageUrl){
        JLabel label = new JLabel(name);
        label.setIcon(getImageFromUrl(imageUrl));

        return label;
    }

    public void setChannels(List<JLabel> channelLabels){
        channelList.removeAll();

        for(JLabel label: channelLabels){
            channelList.add(label);
        }

        frame.setVisible(true);
    }

    private ImageIcon getImageFromUrl(String imageUrl){
        ImageIcon icon = null;

        try {
            URL url = new URL(imageUrl);
            Image img = ImageIO.read(url);
            img = img.getScaledInstance(40,40,Image.SCALE_DEFAULT);
            icon = new ImageIcon(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }

    public void setUpdateListener(ActionListener actionListener){
        updateButton.addActionListener(actionListener);
    }

    public void setChannelLabelListener(JLabel label, List<Object[]> listOfPrograms){
        label.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                channelInfo.changeHeaderContent(label);
                channelInfo.addTable(listOfPrograms);
                frame.validate();
            }
        });
    }
}
