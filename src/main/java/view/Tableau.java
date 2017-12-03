/**
 * Created on 28/11/17
 * File: Tableau.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the tableau. Contains a table that stores all
 * program for a channel.
 */

package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Tableau extends JPanel {

    private JTable tableTableau;
    private DefaultTableModel tableModel;
    private final static int COL_TITLE = 0;
    private final static int COL_START = 1;
    private final static int COL_END = 2;

    /**
     * Constructor for the tableau. Creates the table and a table
     * model.
     */
    public Tableau(){
        tableModel= new DefaultTableModel(0, 3);
        initiateTable(tableModel);
        add(tableTableau);
    }

    /**
     * Initiates the table and the table model. Styles and color the
     * table appropriate.
     * @param tableModel the table model.
     */
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

            /**
             * Makes the table uneditable
             * @param row the row of the table
             * @param column the column of the table
             * @return false since all rows/columns should be
             * uneditable.
             */
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            /**
             * Styles the given component.
             * @param c
             */
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

            /**
             * Colors the programs depending on if the have been sent,
             * is sending or is about to send later.
             * @param c the component that should be styled.
             * @param start the start time of the program.
             * @param end the end time of the program.
             */
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

    /**
     * Inner class representing the renderer for table cells. Style
     * the cells for the given cells.
     */
    public class DateTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            if (value instanceof LocalDateTime) {
                value = ((LocalDateTime) value).toLocalTime().truncatedTo(ChronoUnit.MINUTES);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * Adds a row to the table containing the information about the
     * program.
     * @param program the program that should be added to the table.
     */
    public void addProgram(Object[] program){
           tableModel.addRow(program);
    }

    /**
     * Adds a listener to the row so the additional information
     * can be visible to the user.
     */
    public void addRowListener(MouseAdapter programListener){
        tableTableau.addMouseListener(programListener);
    }
}
