package View;

import Model.Channel;
import Model.Program;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        frame.setMinimumSize(new Dimension(600,80));
        frame.setPreferredSize(new Dimension(700, 500));
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
        headerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 2;
        gbc.fill = 2;

        headerPanel.setBackground(Color.white);
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.add(new JLabel("Välj en kanal ur listan"), gbc);
        tablePanel.add(headerPanel, BorderLayout.PAGE_START);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = 0;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        updateButton = new JButton("Update");
        updateButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(updateButton);

        headerPanel.add(buttonPanel, gbc);

        return tablePanel;
    }

    public void setUpdateListener(ActionListener actionListener){
        updateButton.addActionListener(actionListener);
    }

    public JLabel createChannelLabel(String name, String imageUrl){
        JLabel label = new JLabel(name);
        label.setIcon(getImageFromUrl(imageUrl, "ListImage"));

        return label;
    }

    public void setChannels(List<JLabel> channelLabels){
        removeComponent(channelPanel);

        for(JLabel label: channelLabels){
            channelPanel.add(label);
        }

        frame.setVisible(true);
    }

    private ImageIcon getImageFromUrl(String imageUrl, String typeOfImage){
        ImageIcon icon = null;

        try {
            URL url = new URL(imageUrl);
            Image img = ImageIO.read(url);

            if(typeOfImage == "ListImage"){
                img = img.getScaledInstance(40,40,Image.SCALE_DEFAULT);
            } else {
                img = img.getScaledInstance(80,80,Image.SCALE_DEFAULT);
            }

            icon = new ImageIcon(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }

    public void addListenerToLabel(JLabel label, Channel channel){
        label.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                removeComponent(headerPanel);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.weightx = 2;
                gbc.fill = 2;

                JLabel header = new JLabel(channel.getName());
                header.setFont(header.getFont().deriveFont(24f));
                header.setIcon(getImageFromUrl(channel.getImageUrl(), "HeaderImage"));
                headerPanel.add(header, gbc);

                addTableau(channel.getTableau());

                frame.validate();
            }
        });
    }

    public void addTableau(List<Program> tableau){

        Component[] componentList = tablePanel.getComponents();

        for(Component c : componentList){
            if(c instanceof JScrollPane){
                tablePanel.remove(c);
            }
        }

        tablePanel.revalidate();
        tablePanel.repaint();

        DefaultTableModel tableModel = new DefaultTableModel(0,3);
        JTable tableTableau = new JTable(tableModel){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                c.setForeground(getForeground());

                int modelRow = convertRowIndexToModel(row);

                LocalDateTime start = (LocalDateTime) getModel().getValueAt(modelRow, 1);
                LocalDateTime end = (LocalDateTime)getModel().getValueAt(modelRow, 2);

                if (end.isBefore(LocalDateTime.now())){
                    c.setForeground(Color.GREEN);
                }

                //getModel().setValueAt(start.toLocalTime(), modelRow, 1);

                return c;
            }
        };

        JPanel panel = new JPanel();
        panel.add(tableTableau);
        JScrollPane scrollArea = new JScrollPane(panel);
        scrollArea.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        tablePanel.add(scrollArea, BorderLayout.CENTER);

        //STYLING
        tableTableau.getTableHeader().setUI(null);
        tableTableau.setRowHeight(25);
        tableTableau.setIntercellSpacing(new Dimension(40,0));

        //Defines the width of the columns
        tableTableau.getColumnModel().getColumn(0).setPreferredWidth(240);
        tableTableau.getColumnModel().getColumn(1).setPreferredWidth(80);
        tableTableau.getColumnModel().getColumn(2).setPreferredWidth(80);

        //Centering the time columns
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment( JLabel.CENTER );
        tableTableau.getColumnModel().getColumn(1).setCellRenderer(renderer);
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        tableTableau.getColumnModel().getColumn(2).setCellRenderer(renderer);

        //FILL TABLE
        if(tableau.size() != 0){
            for(Program program:tableau){
                Object[] programInfo = {
                        program.getTitle(),
                        program.getStart(),
                        program.getEnd()
                };

                tableModel.addRow(programInfo);

            }
        } else {
            Object[] programInfo = {"Sädningsuppehåll"};
            tableModel.addRow(programInfo);
        }

        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private void removeComponent(JComponent jComponent){

        Component[] componentList = jComponent.getComponents();

        for(Component c : componentList){
            if(c instanceof JLabel){
                jComponent.remove(c);
            }
        }

        jComponent.revalidate();
        jComponent.repaint();
    }
}
