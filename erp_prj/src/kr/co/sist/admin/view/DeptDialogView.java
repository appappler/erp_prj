package kr.co.sist.admin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DeptDialogView extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField jtfDiaNum;
	private JTextField jtfDiaName;
	private JTextField jtfDiaTel;
	private JTextField jtfDiaLoc;
	private JTextField jtfDiaNote;
	private JTextField jtfDiaBonus_rate;
	private JButton jbtnDiaModify;
	private JTable table;

	public DeptDialogView() {

		setTitle("부서 정보");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(40, 10, 419, 249);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNum = new JLabel("부서번호");
		lblNum.setBounds(0, 3, 55, 15);
		panel.add(lblNum);

		jtfDiaNum = new JTextField();
		jtfDiaNum.setBounds(60, 0, 125, 21);
		jtfDiaNum.setEditable(false);
		panel.add(jtfDiaNum);

		JLabel lblName = new JLabel("부서명");
		lblName.setBounds(245, 3, 40, 15);
		panel.add(lblName);

		jtfDiaName = new JTextField();
		jtfDiaName.setBounds(291, 0, 125, 21);
		panel.add(jtfDiaName);

		JLabel lblTel = new JLabel("연락처");
		lblTel.setBounds(10, 29, 43, 15);
		panel.add(lblTel);

		jtfDiaTel = new JTextField();
		jtfDiaTel.setBounds(60, 26, 125, 21);
		panel.add(jtfDiaTel);

		JLabel lblLoc = new JLabel("위치");
		lblLoc.setBounds(255, 29, 34, 15);
		panel.add(lblLoc);

		jtfDiaLoc = new JTextField();
		jtfDiaLoc.setBounds(291, 26, 125, 21);
		panel.add(jtfDiaLoc);

		JLabel lblNote = new JLabel("부서설명");
		lblNote.setBounds(0, 52, 55, 15);
		panel.add(lblNote);

		jtfDiaNote = new JTextField();
		jtfDiaNote.setBounds(60, 52, 125, 21);
		panel.add(jtfDiaNote);

		JLabel lblBonus = new JLabel("상여금지급률");
		lblBonus.setBounds(210, 54, 75, 15);
		panel.add(lblBonus);

		jtfDiaBonus_rate = new JTextField();
		jtfDiaBonus_rate.setBounds(291, 52, 125, 21);
		panel.add(jtfDiaBonus_rate);

		jbtnDiaModify = new JButton("수정");
		jbtnDiaModify.setBounds(341, 84, 75, 23);
		panel.add(jbtnDiaModify);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 117, 416, 120);
		panel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "사번", "이름", "직급", "연락처" }) {
			private static final long serialVersionUID = 8647006177878765672L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 모든 셀 수정 비활성화
			}
		});
		
        JTableHeader jthTable = table.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 12));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));
        
		scrollPane.setViewportView(table);

		setSize(487, 302); // 딱 맞게 창 크기 설정
		setLocationRelativeTo(null); // 화면 가운데 정렬

	}// DeptDialogView

	public JTextField getJtfDiaNum() {
		return jtfDiaNum;
	}

	public JTextField getJtfDiaName() {
		return jtfDiaName;
	}

	public JTextField getJtfDiaTel() {
		return jtfDiaTel;
	}

	public JTextField getJtfDiaLoc() {
		return jtfDiaLoc;
	}

	public JTextField getJtfDiaNote() {
		return jtfDiaNote;
	}

	public JTextField getJtfDiaBonus_rate() {
		return jtfDiaBonus_rate;
	}

	public JButton getJbtnDiaModify() {
		return jbtnDiaModify;
	}

	public JTable getTable() {
		return table;
	}

	public void setJtfDiaNum(JTextField jtfDiaNum) {
		this.jtfDiaNum = jtfDiaNum;
	}

	public void setJtfDiaName(JTextField jtfDiaName) {
		this.jtfDiaName = jtfDiaName;
	}

	public void setJtfDiaTel(JTextField jtfDiaTel) {
		this.jtfDiaTel = jtfDiaTel;
	}

	public void setJtfDiaLoc(JTextField jtfDiaLoc) {
		this.jtfDiaLoc = jtfDiaLoc;
	}

	public void setJtfDiaNote(JTextField jtfDiaNote) {
		this.jtfDiaNote = jtfDiaNote;
	}

	public void setJtfDiaBonus_rate(JTextField jtfDiaBonus_rate) {
		this.jtfDiaBonus_rate = jtfDiaBonus_rate;
	}

	public void setJbtnDiaModify(JButton jbtnDiaModify) {
		this.jbtnDiaModify = jbtnDiaModify;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
}// class
