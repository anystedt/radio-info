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

    public ChannelInfo(JButton updateButton){
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.PAGE_START);
        add(createProgramInfo(), BorderLayout.PAGE_END);

        setGridBagConstraints(2,0,0);
        header.add(createUpdateButton(updateButton), gbc);
    }

    private JPanel createHeader(){
        header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.setBackground(Color.white);
        header.setPreferredSize(new Dimension(0, 80));

        setGridBagConstraints(0,2,2);
        header.add(new JLabel("Choose a channel from the list"), gbc);

        return header;
    }

    private JPanel createUpdateButton(JButton updateButton){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);

        updateButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(updateButton);
        return buttonPanel;
    }

    public void changeHeaderContent(JLabel channelLabel){
        removeHeaderContent();

        JLabel label = new JLabel(channelLabel.getText());
        label.setFont(label.getFont().deriveFont(24f));

        label.setIcon(resizeChannelImage((ImageIcon)channelLabel.getIcon()));
        setGridBagConstraints(0, 2, 2);
        header.add(label, gbc);
    }

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

    private void setGridBagConstraints(int gridx, int weightx, int fill){
        gbc.gridx = gridx;
        gbc.weightx = weightx;
        gbc.fill = fill;
    }

    private ImageIcon resizeChannelImage(ImageIcon icon){
        Image channelImage = icon.getImage();
        Image newImage = channelImage.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(newImage);
    }

    public void addTable(List<Object[]> listOfPrograms, MouseAdapter programListener){
        removeTable();

        Tableau tableau = new Tableau();
        tableau.addRowListener(programListener);

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

    private void removeTable(){
       for(Component c : getComponents()){
            if(c instanceof JScrollPane){
                remove(c);
            }
        }
    }

    private JPanel createProgramInfo(){
        programPanel = new JPanel();
        programPanel.setBackground(new Color(238,238,238));
        programPanel.setPreferredSize(new Dimension(0, 110));
        programPanel.setLayout(new GridBagLayout());

        return programPanel;
    }

    public void clearProgram(){
        programPanel.removeAll();
        programPanel.revalidate();
        programPanel.repaint();
    }

    public void fillProgramInfo(String title, String subtitle, ImageIcon image, String description) {
        String header;

        if(image != null){
            setGridBagConstraints(0,0,0);
            programPanel.add(new JLabel(image));
        }

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
    }
}
