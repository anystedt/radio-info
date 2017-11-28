package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;

public class Tableau extends JPanel {

    private JTable tableTableau;
    private DefaultTableModel tableModel;

    public Tableau(){

        tableModel= new DefaultTableModel(0, 3);
        initiateTable(tableModel);
        add(tableTableau);
        styleTable();
    }

    private void initiateTable(DefaultTableModel tableModel){

        tableTableau = new JTable(tableModel){
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

                //getModel().setValueAt(, modelRow, 1);



                return c;
            }
        };
    }

    private void styleTable(){
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
    }

    public void addProgram(Object[] program){
           tableModel.addRow(program);
    }
}
