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
    private ChannelList channelList;
    private ChannelInfo channelInfo;

    public RadioView(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600,80));
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setLayout(new BorderLayout());

        frame.setJMenuBar(new RadioMenu());
        frame.add(createChannelList(), BorderLayout.LINE_START);

        updateButton = new JButton("Update");
        channelInfo = new ChannelInfo(updateButton);
        frame.add(channelInfo);

        frame.pack();
    }

    public JScrollPane createChannelList(){
        channelList = new ChannelList();

        JScrollPane scrollArea = new JScrollPane(channelList);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollArea;
    }

    public void setUpdateListener(ActionListener actionListener){
        updateButton.addActionListener(actionListener);
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

    public void addListenerToLabel(JLabel label, List<Object[]> listOfPrograms){
        label.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                channelInfo.changeHeaderContent(label);
                addTableau(listOfPrograms);
                frame.validate();

            }
        });
    }

    public void addTableau(List<Object[]> listOfPrograms){
        channelInfo.addTable();
        if(listOfPrograms.size() != 0){
            for(Object[] program: listOfPrograms){
                channelInfo.addProgramToTable(program);
            }
        } else{
            Object[] program = {"Sändningsuppehåll"};
            channelInfo.addProgramToTable(program);
        }
    }
}
