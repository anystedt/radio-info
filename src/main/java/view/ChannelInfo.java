/**
 * Created on 28/11/17
 * File: ChannelInfo.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the part of the gui presenting the channel
 * information for the user. Contains the tableau for the channel.
 */

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.List;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class ChannelInfo extends JPanel {

    private JPanel header;
    private JPanel programPanel;
    private GridBagConstraints gbc = new GridBagConstraints();

    /**
     * Constructor instantiating all the necessary parts for the
     * information displayed about the channel.
     * @param updateButton the button that will be displayed in the
     *                     view.
     */
    public ChannelInfo(JButton updateButton){
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.PAGE_START);
        add(createProgramInfo(), BorderLayout.PAGE_END);

        setGridBagConstraints(2,0,0);
        header.add(createUpdateButton(updateButton), gbc);
    }

    /**
     * Creates the header showing which channel is selected.
     * @return the panel constitutes the header.
     */
    private JPanel createHeader(){
        header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.setBackground(Color.white);
        header.setPreferredSize(new Dimension(0, 80));

        setGridBagConstraints(0,2,2);
        header.add(new JLabel("Choose a channel from the list"), gbc);

        return header;
    }

    /**
     * Creates the panel that will store the button used for update.
     * @param updateButton the update button
     * @return the panel containing the button.
     */
    private JPanel createUpdateButton(JButton updateButton){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);

        updateButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(updateButton);
        return buttonPanel;
    }

    /**
     * Changes the header content displaying the image and name
     * of the given channel label.
     * @param channelLabel the channel label that should be displayed
     *                     in the header.
     */
    public void changeHeaderContent(JLabel channelLabel){
        removeHeaderContent();

        JLabel label = new JLabel(channelLabel.getText());
        label.setFont(label.getFont().deriveFont(24f));

        label.setIcon(resizeChannelImage((ImageIcon)channelLabel.getIcon()));
        setGridBagConstraints(0, 2, 2);
        header.add(label, gbc);
    }

    /**
     * Removes the label in the header.
     */
    private void removeHeaderContent(){
        Component[] componentList = header.getComponents();

        for(Component c : componentList){
            if(c instanceof JLabel){
                header.remove(c);
            }
        }

        header.revalidate();
        header.repaint();
    }

    /**
     * Sets the constraints for the GridBagConstraints variable.
     * @param gridx the value of gridx
     * @param weightx the value of weightx
     * @param fill the value of fill.
     */
    private void setGridBagConstraints(int gridx, int weightx, int fill){
        gbc.gridx = gridx;
        gbc.weightx = weightx;
        gbc.fill = fill;
    }

    /**
     * Resizes the image to a another size.
     * @param icon the icon containing the image
     * @return the resized icon
     */
    private ImageIcon resizeChannelImage(ImageIcon icon){
        Image channelImage = icon.getImage();
        Image newImage = channelImage.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(newImage);
    }

    /**
     * Adds the table containing the tableau to the view. Adds all the
     * programs to the tableau if there is any, otherwise informs the
     * user about the lack of programs.
     * @param listOfPrograms a list containing all the programs.
     * @param programListener a listener to add to the tableau for
     *                        displaying detailed information about
     *                        programs.
     */
    public void addTable(List<Object[]> listOfPrograms, MouseAdapter programListener){
        //Removes the old tableau if there is one and add a new one
        // connected to the listener.
        removeTable();
        Tableau tableau = new Tableau();
        tableau.addRowListener(programListener);

        //If the given list contains any programs these are added to
        // the tableau, otherwise a message will be displayed in the
        // view telling the user the channel does not send any
        // program this day.
        if(listOfPrograms.size() != 0){
            JScrollPane scrollArea = new JScrollPane(tableau);
            scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
            for(Object[] program: listOfPrograms){
                tableau.addProgram(program);
                add(scrollArea);
            }
        } else {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Pause of transmission"));
            JScrollPane scroll = new JScrollPane(panel);
            add(scroll);
        }
    }

    /**
     * Removes the table or other scrollable content in the
     * information about the channel.
     */
    private void removeTable(){
       for(Component c : getComponents()){
            if(c instanceof JScrollPane){
                remove(c);
            }
        }
    }

    /**
     * Creates the panel intended to show the detailed information
     * about a program when a program is clicked in the tableau.
     * @return the panel intended to store the information.
     */
    private JPanel createProgramInfo(){
        programPanel = new JPanel();
        programPanel.setBackground(new Color(238,238,238));
        programPanel.setPreferredSize(new Dimension(0, 110));
        programPanel.setLayout(new GridBagLayout());

        return programPanel;
    }

    /**
     * Clears the program information displayed in the panel for
     * detailed information at the moment.
     */
    public void clearProgram(){
        programPanel.removeAll();
        programPanel.revalidate();
        programPanel.repaint();
    }

    /**
     * Fills the area reserved for the program information with the
     * given information.
     * @param title the title of the program.
     * @param subtitle the subtitle of the program
     * @param image the image of the program
     * @param description the description of the program.
     */
    public void fillProgramInfo(String title, String subtitle, ImageIcon image, String description) {
        String header;

        //If the given program has a image it will be displayed in
        // the view
        if(image != null){
            setGridBagConstraints(0,0,0);
            programPanel.add(new JLabel(image));
        }

        //Creates a panel that will store the title, subtitle and the
        // description about the program
        JPanel infoPanel = new JPanel();
        setGridBagConstraints(1,2,2);
        programPanel.add(infoPanel, gbc);
        infoPanel.setLayout(new BorderLayout(2,10));

        if(subtitle != null){
            header = title + subtitle;
        } else{
            header = title;
        }

        JTextArea titleArea = new JTextArea(header);
        titleArea.setBackground(new Color(238,238,238));
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setFont(titleArea.getFont().deriveFont(14f));

        infoPanel.add(titleArea, BorderLayout.PAGE_START);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setBackground(new Color(238,238,238));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(titleArea.getFont().deriveFont(12f));

        infoPanel.add(descriptionArea, BorderLayout.CENTER);

        programPanel.revalidate();
    }
}
