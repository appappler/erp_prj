package kr.co.sist.admin.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import kr.co.sist.admin.service.PayrollService;

public class SalarySearchView extends JPanel {

    private static final long serialVersionUID = 2120720178854949797L;
    private static SalarySearchView instance;

    private JComboBox<String> cbDept, cbPosition, cbYear;
    private JTextField tfName;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel model;

    public SalarySearchView() {
        instance = this;
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(900, 480));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createTitledBorder("검색 조건"));
        Insets insets = new Insets(5, 10, 5, 10);

        cbDept = new JComboBox<>(new String[]{"부서"});
        addSearchField(searchPanel, "", 0, 0, cbDept, insets);

        cbPosition = new JComboBox<>(new String[]{"직급"});
        addSearchField(searchPanel, "", 2, 0, cbPosition, insets);

        cbYear = new JComboBox<>(new String[]{"년도"});
        addSearchField(searchPanel, "", 0, 1, cbYear, insets);

        tfName = new JTextField("사원명", 10);
        tfName.setForeground(Color.GRAY);
        tfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if ("사원명".equals(tfName.getText())) {
                    tfName.setText("");
                    tfName.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tfName.getText().trim().isEmpty()) {
                    tfName.setText("사원명");
                    tfName.setForeground(Color.GRAY);
                }
            }
        });
        addSearchField(searchPanel, "", 2, 1, tfName, insets);

        btnSearch = new JButton("검색");
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 4; gbcBtn.gridy = 0; gbcBtn.gridheight = 2;
        gbcBtn.insets = insets; gbcBtn.anchor = GridBagConstraints.CENTER;
        searchPanel.add(btnSearch, gbcBtn);

        add(searchPanel, BorderLayout.NORTH);

        String[] columns = {
            "지급일자", "사원번호", "성명", "부서", "직급",
            "기본급", "상여금", "공제총액", "실수령액"
        };

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
        header.setPreferredSize(new Dimension(100, 35));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1250, 450));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSearchField(JPanel panel, String label, int x, int y, JComponent input, Insets insets) {
        GridBagConstraints lbl = new GridBagConstraints();
        lbl.gridx = x; lbl.gridy = y; lbl.insets = insets; lbl.anchor = GridBagConstraints.WEST;
        if (!label.isEmpty()) {
            panel.add(new JLabel(label), lbl);
        }

        GridBagConstraints field = new GridBagConstraints();
        field.gridx = x + 1; field.gridy = y;
        field.insets = insets; field.fill = GridBagConstraints.HORIZONTAL;
        panel.add(input, field);
    }

    public void populateComboBoxes(PayrollService service) {
        cbDept.removeAllItems(); cbDept.addItem("부서");
        for (String d : service.getAllDepartments()) cbDept.addItem(d);

        cbPosition.removeAllItems(); cbPosition.addItem("직급");
        for (String p : service.getAllPositions()) cbPosition.addItem(p);

        cbYear.removeAllItems(); cbYear.addItem("년도");
        for (String y : service.getAllYears()) cbYear.addItem(y);
    }

    public static SalarySearchView getInstanceIfExists() { return instance; }
    public JButton getBtnSearch() { return btnSearch; }
    public JComboBox<String> getCbDept() { return cbDept; }
    public JComboBox<String> getCbPosition() { return cbPosition; }
    public JComboBox<String> getCbYear() { return cbYear; }
    public JTextField getTfName() { return tfName; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return model; }

    public String getSelectedDept() {
        String dept = (String) cbDept.getSelectedItem();
        return "부서".equals(dept) ? null : dept;
    }

    public String getSelectedPos() {
        String pos = (String) cbPosition.getSelectedItem();
        return "직급".equals(pos) ? null : pos;
    }

    public String getSelectedYear() {
        String year = (String) cbYear.getSelectedItem();
        return "년도".equals(year) ? null : year;
    }

    public String getEnteredName() {
        String name = tfName.getText().trim();
        return ("사원명".equals(name) || name.isEmpty()) ? null : name;
    }
}
