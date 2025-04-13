package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import kr.co.sist.admin.evt.EmpTabViewEvt;
import sh.util.PlaceholderUtil;

/**
 * 
 */
public class AddEduDialog extends JDialog {

	private static final long serialVersionUID = -6726426059318094936L;
	private EmpView ev;
	private final JPanel contentPanel = new JPanel();
	private JTextField jtfEduDialog1;
	private JTextField jtfEduDialog2;
	private JTextField jtfEduDialog3;
	private JTextField jtfEduDialog4;
	private JTextField jtfEduDialog5;
	private JTextField jtfEduDialog6;

	private JButton eduDialogOK;
	private JButton eduDialogCancel;

	public AddEduDialog(EmpView ev, EmpTabViewEvt etve) {
		super(SwingUtilities.getWindowAncestor(ev), "학력 정보 추가", ModalityType.APPLICATION_MODAL);
		this.ev = ev;
	    setLayout(null);
	    setSize(498, 291);
	    setLocationRelativeTo(SwingUtilities.getWindowAncestor(ev));


		etve.setEduDialog(this);


		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel jlblEduDialog1 = new JLabel("입학년월");
		jlblEduDialog1.setBounds(121, 13, 57, 15);
		contentPanel.add(jlblEduDialog1);

		JLabel jlblEduDialog2 = new JLabel("졸업년월");
		jlblEduDialog2.setBounds(121, 38, 57, 15);
		contentPanel.add(jlblEduDialog2);

		JLabel jlblEduDialog3 = new JLabel("학교명");
		jlblEduDialog3.setBounds(121, 63, 57, 15);
		contentPanel.add(jlblEduDialog3);

		jtfEduDialog1 = new JTextField("yyyy-mm-dd");
		PlaceholderUtil.applyDatePlaceholder(jtfEduDialog1);
		jtfEduDialog1.setBounds(210, 10, 116, 21);
		contentPanel.add(jtfEduDialog1);
		jtfEduDialog1.setColumns(10);

		jtfEduDialog2 = new JTextField("yyyy-mm-dd");
		PlaceholderUtil.applyDatePlaceholder(jtfEduDialog2);
		jtfEduDialog2.setBounds(210, 35, 116, 21);
		contentPanel.add(jtfEduDialog2);
		jtfEduDialog2.setColumns(10);

		jtfEduDialog3 = new JTextField();
		jtfEduDialog3.setBounds(210, 60, 116, 21);
		contentPanel.add(jtfEduDialog3);
		jtfEduDialog3.setColumns(10);

		JLabel jlblEduDialog4 = new JLabel("전공");
		jlblEduDialog4.setBounds(121, 94, 57, 15);
		contentPanel.add(jlblEduDialog4);

		jtfEduDialog4 = new JTextField();
		jtfEduDialog4.setBounds(210, 91, 116, 21);
		contentPanel.add(jtfEduDialog4);
		jtfEduDialog4.setColumns(10);

		JLabel jlblEduDialog5 = new JLabel("학위");
		jlblEduDialog5.setBounds(121, 125, 57, 15);
		contentPanel.add(jlblEduDialog5);

		jtfEduDialog5 = new JTextField();
		jtfEduDialog5.setBounds(210, 122, 116, 21);
		contentPanel.add(jtfEduDialog5);
		jtfEduDialog5.setColumns(10);

		JLabel jlblEduDialog6 = new JLabel("졸업구분");
		jlblEduDialog6.setBounds(121, 155, 57, 15);
		contentPanel.add(jlblEduDialog6);

		jtfEduDialog6 = new JTextField();
		jtfEduDialog6.setBounds(210, 152, 116, 21);
		contentPanel.add(jtfEduDialog6);
		jtfEduDialog6.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		eduDialogOK = new JButton("OK");
		eduDialogOK.setActionCommand("OK");
		eduDialogOK.addActionListener(etve);
		buttonPane.add(eduDialogOK);
		getRootPane().setDefaultButton(eduDialogOK);

		eduDialogCancel = new JButton("Cancel");
		eduDialogCancel.setActionCommand("Cancel");
		eduDialogCancel.addActionListener(e -> dispose());
		buttonPane.add(eduDialogCancel);
	}

	public EmpView getEv() {
		return ev;
	}

	public JTextField getJtfEduDialog1() {
		return jtfEduDialog1;
	}

	public JTextField getJtfEduDialog2() {
		return jtfEduDialog2;
	}

	public JTextField getJtfEduDialog3() {
		return jtfEduDialog3;
	}

	public JTextField getJtfEduDialog4() {
		return jtfEduDialog4;
	}

	public JTextField getJtfEduDialog5() {
		return jtfEduDialog5;
	}

	public JTextField getJtfEduDialog6() {
		return jtfEduDialog6;
	}

	public JButton getEduDialogOK() {
		return eduDialogOK;
	}

	public JButton getEduDialogCancel() {
		return eduDialogCancel;
	}
	
}//class
