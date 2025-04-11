 package sh.util;

import javax.swing.table.DefaultTableModel;

/**
 * 
 */
public class TableUtil {

    public static int getIntValue(DefaultTableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return Integer.parseInt(val.toString().trim());
    }

    public static String getString(DefaultTableModel model, int row, int col) {
        Object val = model.getValueAt(row, col);
        return val == null ? "" : val.toString().trim();
    }

    public static void removeRow(DefaultTableModel model, int row) {
        if (row >= 0 && row < model.getRowCount()) {
            model.removeRow(row);
        }
    }
    
}//class