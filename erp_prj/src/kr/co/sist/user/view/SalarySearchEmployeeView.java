package kr.co.sist.user.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import kr.co.sist.user.service.PayrollService;

public class SalarySearchEmployeeView extends JPanel {

    private static final long serialVersionUID = 6533950438745559910L;
    private JComboBox<String> cbYear;
    private JButton btnConfirm;
    private JTable table;
    private DefaultTableModel model;
    private String loginEmpno;

    public SalarySearchEmployeeView(String empno) {
        this.loginEmpno = empno;
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(900, 480)); // 패널 크기 동일
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder("급여 검색"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // 라벨
        JLabel lblYear = new JLabel("년도");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblYear, gbc);

        // 콤보박스
        cbYear = new JComboBox<>();
        cbYear.addItem("년도");
        gbc.gridx = 1;
        panel.add(cbYear, gbc);

        // 버튼
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
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        table.setRowHeight(24);
        table.setDefaultEditor(Object.class, null);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 14));
        header.setForeground(Color.white);
        header.setBackground(new Color(8, 60, 80));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        return new JScrollPane(table);
    }

    // 콤보박스 세팅
    public void populateYearComboBox(PayrollService service) {
        cbYear.removeAllItems();
        cbYear.addItem("년도");
        for (String y : service.getYearsByEmpno(loginEmpno)) {
            cbYear.addItem(y);
        }
    }

    // Getter
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
