package kr.co.sist.user.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.service.PayrollService;

public class SalarySearchEmployeeView extends JPanel {

	private static final long serialVersionUID = 6533950438745559910L;
	private JComboBox<String> cbYear;
    private JButton btnConfirm;
    private JTable table;
    private DefaultTableModel model;
    private String loginEmpno;

    public SalarySearchEmployeeView(String empno) {
    	setPreferredSize(new Dimension(1000, 500));
        this.loginEmpno = empno;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("급여 검색"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        cbYear = new JComboBox<>();
        cbYear.addItem("년도");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("년도 선택"), gbc);

        gbc.gridx = 1;
        panel.add(cbYear, gbc);

        btnConfirm = new JButton("조회");
        gbc.gridx = 2;
        panel.add(btnConfirm, gbc);

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = {"지급일자", "사원번호", "성명", "부서", "직급", "급여", "상여금", "공제총액", "실수령액"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));
        table.setDefaultEditor(Object.class, null);

        // 셀 정렬
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // 컬럼 너비 조금 조정
        table.getColumnModel().getColumn(0).setPreferredWidth(90);  // 지급일자
        table.getColumnModel().getColumn(1).setPreferredWidth(80);  // 사원번호
        table.getColumnModel().getColumn(2).setPreferredWidth(90);  // 성명
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // 부서
        table.getColumnModel().getColumn(4).setPreferredWidth(90);  // 직급

        return new JScrollPane(table);
    }

    // 🔹 연도 콤보박스 세팅
    public void populateYearComboBox(PayrollService service) {
        cbYear.removeAllItems();
        cbYear.addItem("년도");
        for (String y : service.getYearsByEmpno(loginEmpno)) {
            cbYear.addItem(y);
        }
    }

    // 🔹 Getter
    public JComboBox<String> getCbYear() {
        return cbYear;
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    public String getLoginEmpno() {
        return loginEmpno;
    }
}
