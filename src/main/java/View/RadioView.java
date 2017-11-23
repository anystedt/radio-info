package View;

import Model.Channel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
    private JPanel channelPanel;
    private JPanel tablePanel;
    private JPanel headerPanel;

    public RadioView(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(700,400));
        frame.setLayout(new BorderLayout());

        frame.setJMenuBar(createMenu());
        frame.add(createChannelPanel(), BorderLayout.LINE_START);
        frame.add(createTablePanel());


        frame.pack();
    }

    public JMenuBar createMenu(){
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Radio Info");
        jMenuBar.add(menu);
        JMenuItem item = new JMenuItem("Item", KeyEvent.VK_T);
        menu.add(item);

        return jMenuBar;
    }

    public JScrollPane createChannelPanel(){
        channelPanel = new JPanel();
        channelPanel.setLayout(new GridLayout(0,1, 2, 5));
        JScrollPane scrollArea = new JScrollPane(channelPanel);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollArea;
    }

    public JPanel createTablePanel(){

        tablePanel = new JPanel();
        headerPanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        headerPanel.setLayout(new BorderLayout());
        tablePanel.add(headerPanel, BorderLayout.PAGE_START);

        updateButton = new JButton("Update");
        updateButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);

        headerPanel.add(updateButton, BorderLayout.LINE_END);

        return tablePanel;
    }

    public void setUpdateListener(ActionListener actionListener){
        updateButton.addActionListener(actionListener);
    }

    public void setChannels(List<Channel> listOfChannels){
        for(Channel channel: listOfChannels){
            JLabel label = new JLabel(channel.getName());

            try {
                URL url = new URL(channel.getImageUrl());
                Image img = ImageIO.read(url);
                img = img.getScaledInstance(40,40,Image.SCALE_DEFAULT);
                ImageIcon icon = new ImageIcon(img);
                label.setIcon(icon);
                addListenerToLabel(label, channel);
                channelPanel.add(label);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        frame.setVisible(true);
    }

    private void addListenerToLabel(JLabel label, Channel channel){
        label.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                //This will happen when a channel is chosen...
                headerPanel.add(label, BorderLayout.LINE_START);
                frame.validate();
            }
        });
    }
}
