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

package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class RadioView {

    private JButton updateButton;
    private JFrame frame;
    private JPanel channelList;
    private ChannelInfo channelInfo;

    public RadioView() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(650, 350));
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setLayout(new BorderLayout());

        frame.setJMenuBar(new RadioMenu());
        frame.add(createChannelList(), BorderLayout.LINE_START);

        updateButton = new JButton("Update");
        channelInfo = new ChannelInfo(updateButton);
        frame.add(channelInfo);

        frame.pack();
    }

    public JScrollPane createChannelList() {
        channelList = new JPanel();
        channelList.setLayout(new GridLayout(0, 1, 2, 5));

        JScrollPane scrollArea = new JScrollPane(channelList);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollArea;
    }

    public JLabel createChannelLabel(String name, String imageUrl) {
        JLabel label = new JLabel(name);
        label.setIcon(getImageFromUrl(imageUrl, 40));

        return label;
    }

    public void setChannels(List<JLabel> channelLabels) {
        channelList.removeAll();

        for (JLabel label : channelLabels) {
            channelList.add(label);
        }

        frame.setVisible(true);
    }

    private ImageIcon getImageFromUrl(String imageUrl, int size) {
        ImageIcon icon = null;

        try {
            if (imageUrl != null) {
                URL url = new URL(imageUrl);
                Image img = ImageIO.read(url);
                img = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
                icon = new ImageIcon(img);
            } else {

               /* BufferedImage image=ImageIO.read(getClass().getClassLoader().getResource("default_image.jpg"));
                System.out.println(image);  */

                ;


                System.out.println(getClass().getResource("/resources/images/default_image.jpg"));
                /*img = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
                icon = new ImageIcon(img);    */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }

    public void setUpdateListener(ActionListener actionListener) {
        updateButton.addActionListener(actionListener);
    }

    public void setViewListener(JLabel label, List<Object[]> listOfPrograms, MouseAdapter programListener) {
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                channelInfo.changeHeaderContent(label);
                channelInfo.addTable(listOfPrograms, programListener);
                frame.validate();
            }
        });
    }

    public void showProgramInfo(String title, String subtitle, String imageUrl, String description) {
        channelInfo.clearProgram();
        ImageIcon image = getImageFromUrl(imageUrl, 100);
        channelInfo.fillProgramInfo(title, subtitle, image, description);
    }

    public void showErrorMessage(Exception e) {
        JOptionPane.showMessageDialog(frame,
                "Could not start program because of a\n" + e.getClass().getName(),
                "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}


//TODO Fixa så att om ett program inte har en bild visas en default?
//TODO UNDANTAG - Om en bild inte går att ladda?
//TODO TESTER
