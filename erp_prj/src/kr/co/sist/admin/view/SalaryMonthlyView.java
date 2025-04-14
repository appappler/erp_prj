package kr.co.sist.admin.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;

import kr.co.sist.admin.vo.PayrollVO;

public class SalaryMonthlyView extends JPanel {
    private JLabel lblEmpInfo = new JLabel();
    private JComboBox<String> cbYear = new JComboBox<>();
    private JTable table;
    private DefaultTableModel model;

    public SalaryMonthlyView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 상단 패널
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
        }

        for (PayrollVO vo : list) {
            String month = vo.getPayDate().substring(5, 7) + "월";
            model.addRow(new Object[]{
                month,
                vo.getBaseSalary(),
                vo.getBonus(),
                vo.getTotal_deduction(),
                vo.getActualSalary()
            });
        }
    }

    public void populateYearComboBox(List<String> years) {
        cbYear.removeAllItems();
        for (String year : years) cbYear.addItem(year);
    }

    // Getter
    public JComboBox<String> getCbYear() { return cbYear; }
    public DefaultTableModel getTableModel() { return model; }
    public JTable getTable() { return table; }
}
