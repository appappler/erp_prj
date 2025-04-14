package kr.co.sist.admin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DeptInfoView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField jtfSearch;
	private JButton jbtSearch;
	private JButton jbtnAdd;
	private JButton jbtnDelete;
	private JTable jtDept;
	private JScrollPane deptScroll;

	@SuppressWarnings("serial")
	public DeptInfoView() {

    	setPreferredSize(new Dimension(900, 700));
		setLayout(null);

		jtfSearch = new JTextField();
		jtfSearch.setText("부서명 입력");
		jtfSearch.setColumns(10);
		jtfSearch.setBounds(50, 30, 200, 30);
		add(jtfSearch);

		jbtSearch = new JButton("검색");
		jbtSearch.setBounds(260, 30, 80, 30);
		add(jbtSearch);

		deptScroll = new JScrollPane();
		deptScroll.setBounds(50, 80, 820, 320);
		add(deptScroll);

		jtDept = new JTable(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null }, { null, null, null, null, null, null }, },
				new String[] { "부서번호", "부서명", "연락처", "위치", "부서설명", "상여금 지급률" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 모든 셀 수정 비활성화
			}
		});
		
		jtDept.setRowHeight(23);
		
		
        JTableHeader jthTable = jtDept.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 12));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));

        deptScroll.setViewportView(jtDept);

		
		jbtnAdd = new JButton("부서 등록");
		jbtnAdd.setBounds(638, 500, 100, 30);
		add(jbtnAdd);

		jbtnDelete = new JButton("삭제");
		jbtnDelete.setBounds(748, 500, 100, 30);
		add(jbtnDelete);

	}// DeptInfoView

	public JTextField getJtfSearch() {
		return jtfSearch;
	}

	public JButton getJbtSearch() {
		return jbtSearch; 
	}

	public JButton getJbtnAdd() {
		return jbtnAdd;
	}

	public JButton getJbtnDelete() {
		return jbtnDelete;
	}

	public JTable getJtDept() {
		return jtDept;
	}

}// class
