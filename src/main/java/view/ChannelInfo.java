package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class ChannelInfo extends JPanel {

    private JPanel header;
    private Tableau tableau;
    private GridBagConstraints gbc = new GridBagConstraints();

    public ChannelInfo(JButton updateButton){
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.PAGE_START);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = 0;

        header.add(createUpdateButton(updateButton), gbc);
    }

    private JPanel createHeader(){
        header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.setBackground(Color.white);
        header.setPreferredSize(new Dimension(0, 80));

        gbc.gridx = 0;
        gbc.weightx = 2;
        gbc.fill = 2;

        header.add(new JLabel("Välj en kanal ur listan"), gbc);

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

        gbc.gridx = 0;
        gbc.weightx = 2;
        gbc.fill = 2;

        JLabel label = new JLabel(channelLabel.getText());
        label.setFont(label.getFont().deriveFont(24f));
        label.setIcon(resizeChannelImage((ImageIcon)channelLabel.getIcon()));

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

    private ImageIcon resizeChannelImage(ImageIcon icon){
        Image channelImage = icon.getImage();
        Image newImage = channelImage.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(newImage);
    }

    public void addTable(List<Object[]> listOfPrograms){
        removeTable();

        tableau = new Tableau();
        JScrollPane scrollArea = new JScrollPane(tableau);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollArea);

        if(listOfPrograms.size() != 0){
            for(Object[] program: listOfPrograms){
                tableau.addProgram(program);
            }
        } else {
            Object[] program = {"Pause of transmission"};
            tableau.addProgram(program);
        }
    }

    private void removeTable(){
        for(Component c : getComponents()){
            if(c instanceof JScrollPane){
                remove(c);
            }
        }
    }
}
