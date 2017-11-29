package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tableau extends JPanel {

    private JTable tableTableau;
    private DefaultTableModel tableModel;
    private final static int COL_TITLE = 0;
    private final static int COL_START = 1;
    private final static int COL_END = 2;
    private final static int COL_SUBTITLE = 3;
    private final static int COL_IMAGE = 4;
    private final static int COL_DESCRIPTION = 5;

    public Tableau(){

        tableModel= new DefaultTableModel(0, 6);
        initiateTable(tableModel);
        add(tableTableau);
        hideColumns();
        addRowListener();
    }

    private void initiateTable(DefaultTableModel tableModel){
        tableTableau = new JTable(tableModel){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                styleTable(c);

                //Define TableCellRenderer for time columns.
                getColumnModel().getColumn(COL_START).setCellRenderer(new DateTableCellRenderer());
                getColumnModel().getColumn(COL_END).setCellRenderer(new DateTableCellRenderer());

                //Gets the time for the start and the end of the program
                int modelRow = convertRowIndexToModel(row);
                LocalDateTime start = (LocalDateTime)getModel().getValueAt(modelRow, COL_START);
                LocalDateTime end = (LocalDateTime)getModel().getValueAt(modelRow, COL_END);

                //Colors the programs depending on if the program
                //has been sent or is sending.
                colorProgram(c, start, end);

                return c;
            }

            private void styleTable(Component c){
                //Color
                c.setForeground(getForeground());
                c.setBackground(getBackground());

                //Layout
                getTableHeader().setUI(null);
                setRowHeight(25);
                setIntercellSpacing(new Dimension(0,0));
                ((JComponent) c).setBorder(BorderFactory
                        .createEmptyBorder(0, 20, 0, 0));

                getColumnModel().getColumn(COL_TITLE).setPreferredWidth(240);
                getColumnModel().getColumn(COL_START).setPreferredWidth(80);
                getColumnModel().getColumn(COL_END).setPreferredWidth(80);
            }

            private void colorProgram(Component c, LocalDateTime start, LocalDateTime end){
                //Change the color if the program has been sent
                if (end.isBefore(LocalDateTime.now())){
                    c.setForeground(new Color(0,0,0, (float) 0.2));
                }

                //Change the color if the program is sending at the moment
                if(end.isAfter(LocalDateTime.now()) && start.isBefore(LocalDateTime.now())){
                    c.setBackground(Color.orange);
                }
            }
        };
    }

    public class DateTableCellRenderer extends DefaultTableCellRenderer {

        public DateTableCellRenderer() {}

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            if (value instanceof LocalDateTime) {
                value = ((LocalDateTime) value).toLocalTime().truncatedTo(ChronoUnit.MINUTES);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    private void hideColumns(){
        //Hide the part of table that should not be visible to the user.
        tableTableau.removeColumn(tableTableau.getColumnModel().getColumn(COL_DESCRIPTION));
        tableTableau.removeColumn(tableTableau.getColumnModel().getColumn(COL_IMAGE));
        tableTableau.removeColumn(tableTableau.getColumnModel().getColumn(COL_SUBTITLE));
    }

    public void addProgram(Object[] program){
           tableModel.addRow(program);
    }

    private void addRowListener(){
        tableTableau.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (tableTableau.getSelectedRow() > -1) {
                    System.out.println(tableTableau.getValueAt(tableTableau.getSelectedRow(), 0).toString());
                }
            }
        });
    }

    /*FRÅGOR
    - Visa upp information om ett program
     */
}
