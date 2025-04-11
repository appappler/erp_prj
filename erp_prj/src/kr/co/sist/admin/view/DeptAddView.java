package kr.co.sist.admin.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeptAddView extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField jtfDeptNum;
	private JTextField jtfDeptName;
	private JTextField jtfDeptTel;
	private JTextField jtfDeptLoc;
	private JTextField jtfDeptNote;
	private JTextField jtfDeptBonus_rate;
	private JButton jbtnDeptAdd;

	public DeptAddView() {

		setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("부서번호");
		lblNewLabel.setBounds(105, 45, 57, 15);
		panel.add(lblNewLabel);

		jtfDeptNum = new JTextField();
		jtfDeptNum.setBounds(167, 42, 233, 21);
		panel.add(jtfDeptNum);
		jtfDeptNum.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("부서명");
		lblNewLabel_1.setBounds(115, 71, 47, 15);
		panel.add(lblNewLabel_1);

		jtfDeptName = new JTextField();
		jtfDeptName.setBounds(167, 68, 233, 21);
		panel.add(jtfDeptName);
		jtfDeptName.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("연락처");
		lblNewLabel_2.setBounds(115, 97, 47, 15);
		panel.add(lblNewLabel_2);

		jtfDeptTel = new JTextField();
		jtfDeptTel.setBounds(167, 94, 233, 21);
		panel.add(jtfDeptTel);
		jtfDeptTel.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("위치");
		lblNewLabel_3.setBounds(125, 123, 37, 15);
		panel.add(lblNewLabel_3);

		jtfDeptLoc = new JTextField();
		jtfDeptLoc.setBounds(167, 120, 233, 21);
		panel.add(jtfDeptLoc);
		jtfDeptLoc.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("부서설명");
		lblNewLabel_4.setBounds(105, 149, 57, 15);
		panel.add(lblNewLabel_4);

		jtfDeptNote = new JTextField();
		jtfDeptNote.setBounds(167, 146, 233, 21);
		panel.add(jtfDeptNote);
		jtfDeptNote.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("상여금지급률");
		lblNewLabel_5.setBounds(84, 175, 78, 15);
		panel.add(lblNewLabel_5);

		jtfDeptBonus_rate = new JTextField();
		jtfDeptBonus_rate.setBounds(167, 172, 233, 21);
		panel.add(jtfDeptBonus_rate);
		jtfDeptBonus_rate.setColumns(10);

		jbtnDeptAdd = new JButton("등록");
		jbtnDeptAdd.setBounds(337, 198, 63, 23);
		
		panel.add(jbtnDeptAdd);

	}// DeptAddView

	public JTextField getJtfDeptNum() {
		return jtfDeptNum;
	}

	public JTextField getJtfDeptName() {
		return jtfDeptName;
	}

	public JTextField getJtfDeptTel() {
		return jtfDeptTel;
	}

	public JTextField getJtfDeptLoc() {
		return jtfDeptLoc;
	}

	public JTextField getJtfDeptNote() {
		return jtfDeptNote;
	}

	public JTextField getJtfDeptBonus_rate() {
		return jtfDeptBonus_rate;
	}

	public JButton getJbtnDeptAdd() {
		return jbtnDeptAdd;
	}

}// class
