package kr.co.sist.user.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * 
 */
public class SubTabPanel extends JPanel {
	
	private static final long serialVersionUID = 13660220831488592L;
	private DefaultTableModel tableModel;
    private JTable table;


    /**
     * 공통 탭 패널 구성자
     * @param columnNames 테이블 컬럼명 배열
     * @param listener 이벤트 리스너 (EmpTabViewEvt 등)
     */
    public SubTabPanel(String[] columnNames, ActionListener listener) {
        setLayout(null);
        initTable(columnNames);
//        initButtons(listener);
    }

    // 📌 테이블 초기화
    private void initTable(String[] columnNames) {
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // editable 여부를 내부 플래그로 제어
                return false;  // 모든 셀에 대해 직접 수정 불가
            }

            @Override
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;  // 더블클릭 시에도 편집 시작되지 않도록 차단
            }
        };


        JTableHeader jthTable = table.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 14));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));
        jthTable.setPreferredSize(new Dimension(jthTable.getWidth(), 30));
        jthTable.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 30, 560, 80);
        add(scrollPane);
        
        centerTableCells(table);

    }



    // 📌 버튼 생성 공통 메서드
    private JButton createButton(String title, int x, int y, ActionListener listener) {
        JButton btn = new JButton(title);
        btn.setBounds(x, y, 81, 23);
        btn.addActionListener(listener);
        add(btn);
        return btn;
    }
    
    private void centerTableCells(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // 컬럼 헤더도 가운데 정렬하고 싶다면:
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // 📌 테이블 수정 가능 여부 설정
    public void setTableEditable(boolean editable) {
        if (!editable) {
            for (int i = 0; i < table.getColumnCount(); i++) {
                Class<?> columnClass = table.getColumnClass(i);
                table.setDefaultEditor(columnClass, null);
            }
        } else {
            DefaultCellEditor editor = new DefaultCellEditor(new JTextField());
            for (int i = 0; i < table.getColumnCount(); i++) {
                Class<?> columnClass = table.getColumnClass(i);
                table.setDefaultEditor(columnClass, editor);
            }
        }
    }

    
    public void resetTable() {
        tableModel.setRowCount(0);
    }

    // ✅ Getter 메서드들
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }

    
}//class