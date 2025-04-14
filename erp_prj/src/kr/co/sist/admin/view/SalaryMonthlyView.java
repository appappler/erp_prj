

package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.vo.PayrollVO;

/**
 * 
 */
public class SalaryMonthlyView extends JPanel {
	
    private static final long serialVersionUID = 605586280366061426L;
	private JLabel lblEmpInfo = new JLabel();  
    private JComboBox<String> cbYear = new JComboBox<>();
    private JTable table;
    private DefaultTableModel model;

    public SalaryMonthlyView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 상단 - 사원정보 + 연도 선택
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("사원 정보"));

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 10, 5, 10);
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 1; gbc2.gridy = 0;
        topPanel.add(lblEmpInfo, gbc2);

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.insets = new Insets(5, 10, 5, 10);
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.gridx = 3; gbc4.gridy = 0;
        topPanel.add(cbYear, gbc4);

        add(topPanel, BorderLayout.NORTH);

        // 🔹 테이블
        String[] columns = {"월", "급여", "상여금", "공제총액", "실수령액"};
        model = new DefaultTableModel(columns, 0) {
        	
            private static final long serialVersionUID = -4974981613254152461L;
			public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(24);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    public void loadData(List<PayrollVO> list) {
        model.setRowCount(0);

        if (!list.isEmpty()) {
            PayrollVO first = list.get(0);
            lblEmpInfo.setText(first.getEmp_name() + " (" + first.getEmpno() + ")");

            cbYear.removeAllItems();
            String year = first.getPayDate().substring(0, 4);
            cbYear.addItem(year);
        }

        for (PayrollVO vo : list) {
            String month = vo.getPayDate().substring(5, 7) + "월";
            model.addRow(new Object[]{
                month,
                vo.getSalary(),
                vo.getBonus(),
                vo.getTotal_deduction(),
                vo.getActualSalary()
            });
        }
    }

    public JComboBox getCbYear() {
    	return cbYear;
    }

    public DefaultTableModel getTableModel() {
    	return model;
    }

    public JTable getTable() {
    	return table;
    }
    
}//class