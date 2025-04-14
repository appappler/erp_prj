package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import kr.co.sist.admin.service.PayrollService;

/**
 * 
 */
public class SalarySearchView extends JPanel {
	
    private static final long serialVersionUID = 2120720178854949797L;
	private JComboBox<String> cbDept, cbPosition, cbYear;
    private JTextField tfName;
    private JButton btnSearch;
    private JTable table;
    private DefaultTableModel model;

    public SalarySearchView() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(1000, 630));//***패널전체 사이즈 조절
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 검색 필터 영역
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createTitledBorder("검색 조건"));

        Insets insets = new Insets(5, 10, 5, 10);

        // 부서
        GridBagConstraints gbcDeptLabel = new GridBagConstraints();
        gbcDeptLabel.gridx = 0; gbcDeptLabel.gridy = 0;
        gbcDeptLabel.insets = insets; gbcDeptLabel.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(new JLabel("부서"), gbcDeptLabel);

        GridBagConstraints gbcDeptCombo = new GridBagConstraints();
        gbcDeptCombo.gridx = 1; gbcDeptCombo.gridy = 0;
        gbcDeptCombo.insets = insets; gbcDeptCombo.fill = GridBagConstraints.HORIZONTAL;
        cbDept = new JComboBox<>(new String[]{"부서"});
        searchPanel.add(cbDept, gbcDeptCombo);

        // 직급
        GridBagConstraints gbcPosLabel = new GridBagConstraints();
        gbcPosLabel.gridx = 2; gbcPosLabel.gridy = 0;
        gbcPosLabel.insets = insets; gbcPosLabel.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(new JLabel("직급"), gbcPosLabel);

        GridBagConstraints gbcPosCombo = new GridBagConstraints();
        gbcPosCombo.gridx = 3; gbcPosCombo.gridy = 0;
        gbcPosCombo.insets = insets; gbcPosCombo.fill = GridBagConstraints.HORIZONTAL;
        cbPosition = new JComboBox<>(new String[]{"직급"});
        searchPanel.add(cbPosition, gbcPosCombo);

        // 년도
        GridBagConstraints gbcYearLabel = new GridBagConstraints();
        gbcYearLabel.gridx = 0; gbcYearLabel.gridy = 1;
        gbcYearLabel.insets = insets; gbcYearLabel.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(new JLabel("년도"), gbcYearLabel);

        GridBagConstraints gbcYearCombo = new GridBagConstraints();
        gbcYearCombo.gridx = 1; gbcYearCombo.gridy = 1;
        gbcYearCombo.insets = insets; gbcYearCombo.fill = GridBagConstraints.HORIZONTAL;
        cbYear = new JComboBox<>(new String[]{"년도"});
        searchPanel.add(cbYear, gbcYearCombo);

        // 이름
        GridBagConstraints gbcNameLabel = new GridBagConstraints();
        gbcNameLabel.gridx = 2; gbcNameLabel.gridy = 1;
        gbcNameLabel.insets = insets; gbcNameLabel.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(new JLabel("이름"), gbcNameLabel);

        GridBagConstraints gbcNameField = new GridBagConstraints();
        gbcNameField.gridx = 3; gbcNameField.gridy = 1;
        gbcNameField.insets = insets; gbcNameField.fill = GridBagConstraints.HORIZONTAL;
        tfName = new JTextField(10);
        searchPanel.add(tfName, gbcNameField);

        // 검색 버튼
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 4; gbcBtn.gridy = 0;
        gbcBtn.gridheight = 2;
        gbcBtn.insets = insets;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        btnSearch = new JButton("검색");
        searchPanel.add(btnSearch, gbcBtn);

        add(searchPanel, BorderLayout.NORTH);

        // 🔹 테이블
        String[] columns = {"지급일자", "사원번호", "성명", "부서", "직급", "급여", "상여금", "공제총액", "실수령액"};
        model = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 8059914616836205435L;

			public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        
        //***테이블사이즈 조절
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 22));
		header.setForeground(Color.white);
		header.setBackground(new Color(8, 60, 80));
		header.setPreferredSize(new Dimension(header.getWidth(), 30));
		
        table.setRowHeight(24);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // ❌ 더블클릭 리스너는 제거됨 (이벤트 클래스에서 처리)

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // 🔹 콤보박스 항목을 외부에서 PayrollService를 통해 세팅하는 메서드
    public void populateComboBoxes(PayrollService service) {
        cbDept.removeAllItems(); cbDept.addItem("부서");
        for (String d : service.getAllDepartments()) cbDept.addItem(d);

        cbPosition.removeAllItems(); cbPosition.addItem("직급");
        for (String p : service.getAllPositions()) cbPosition.addItem(p);

        cbYear.removeAllItems(); cbYear.addItem("년도");
        for (String y : service.getAllYears()) cbYear.addItem(y);
    }

    // 🔹 getter 메서드들 (이벤트 클래스에서 사용 가능)
    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JComboBox<String> getCbDept() {
        return cbDept;
    }

    public JComboBox<String> getCbPosition() {
        return cbPosition;
    }

    public JComboBox<String> getCbYear() {
        return cbYear;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }
    
}//class
