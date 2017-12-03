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

    /**
     * Constructor that instantiates all the necessary parts for
     * the main view.
     */
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
        frame.setVisible(true);
    }

    /**
     * Creates the list where the channels are presented to the user.
     * @return the scrollable area that will be used for storing the
     * channels.
     */
    private JScrollPane createChannelList() {
        channelList = new JPanel();
        channelList.setLayout(new GridLayout(0, 1, 2, 5));
        JScrollPane scrollArea = new JScrollPane(channelList);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scrollArea.setPreferredSize(new Dimension(220, scrollArea.getHeight()));

        return scrollArea;
    }

    /**
     * Creates a label containing the channel image and the channel
     * name.
     * @param title the title
     * @param imageUrl the url string to the image
     * @return a label containing the title and the image
     */
    public JLabel createChannelLabel(String title, String imageUrl) {
        JLabel label = new JLabel(title);
        label.setIcon(getImageFromUrl(imageUrl, 40));

        return label;
    }

    /**
     * Adds all labels containing information about the channel to the
     * scrollable list.
     * @param channelLabels a list containing all the labels.
     */
    public void setChannels(List<JLabel> channelLabels) {
        channelList.removeAll();

        for (JLabel label : channelLabels) {
            channelList.add(label);
        }
        frame.revalidate();
    }

    /**
     * Retrieves a image using the given url, scaling it to the given
     * size. If the url is null a default image is used. If the image
     * cannot be loaded a error message will appear in the view. 
     * @param imageUrl the url string to the image
     * @param size the size the image will be scaled to.
     * @return an icon containing the given image.
     */
    private ImageIcon getImageFromUrl(String imageUrl, int size) {
        ImageIcon icon = null;
        URL url;

        try {
            if (imageUrl != null) {
                url = new URL(imageUrl);
                Image img = ImageIO.read(url);
                img = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } else {

               url = getClass().getResource("/images/default-placeholder.png");

               Image img = ImageIO.read(url);
               img = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
               icon = new ImageIcon(img);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                    "Could not load image " + imageUrl,
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return icon;
        }

        return icon;
    }

    /**
     * Adds a listener to the update button.
     * @param actionListener
     */
    public void setUpdateListener(ActionListener actionListener) {
        updateButton.addActionListener(actionListener);
    }

    /**
     * Adds a listener to the given label that will change the
     * channel information that is shown, and also add a listener
     * to the tableau.
     * @param label the label that the listener will be added to.
     * @param listOfPrograms a list of programs corresponding to
     *                       the given label.
     * @param programListener the listener that will be used for
     *                        notice actions on the tableau.
     */
    public void setViewListener(JLabel label, List<Object[]> listOfPrograms, MouseAdapter programListener) {
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                channelInfo.changeHeaderContent(label);
                channelInfo.addTable(listOfPrograms, programListener);
                frame.validate();
            }
        });
    }

    /**
     * Replaces the detailed information about a program in the area
     * displaying this information.
     * @param title the title of the program
     * @param subtitle the subtitle of the program
     * @param imageUrl the image url of the program
     * @param description the description of the program
     */
    public void showProgramInfo(String title, String subtitle, String imageUrl, String description) {
        channelInfo.clearProgram();
        ImageIcon image = getImageFromUrl(imageUrl, 100);
        channelInfo.fillProgramInfo(title, subtitle, image, description);
    }

    /**
     * Displays an errormessage explaining why the program could not
     * be started.
     * @param e the exception that is thrown.
     */
    public void showErrorMessage(Exception e) {
        JOptionPane.showMessageDialog(frame,
                "Could not start program because of a\n" + e.getClass().getName(),
                "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}