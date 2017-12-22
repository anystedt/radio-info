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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        frame.setJMenuBar(new RadioMenu(frame));
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
     * @param image the image
     * @return a label containing the title and the image
     */
    public JLabel createChannelLabel(String title, ImageIcon image) {
        JLabel label = new JLabel(title);
        label.setIcon(image);

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
     * @param image the image of the program
     * @param description the description of the program
     */
    public void showProgramInfo(String title, String subtitle, ImageIcon image, String description) {
        channelInfo.clearProgram();
        channelInfo.fillProgramInfo(title, subtitle, image, description);
    }

    /**
     * Shows an error message when a image could not be loaded from
     * url.
     * @param imageUrl
     */
    public void showImageError(String imageUrl){
        JOptionPane.showMessageDialog(null,
                "Could not load image " + imageUrl,
                "Warning", JOptionPane.WARNING_MESSAGE);
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