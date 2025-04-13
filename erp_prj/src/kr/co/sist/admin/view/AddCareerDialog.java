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
public class AddCareerDialog extends JDialog {
	
	private static final long serialVersionUID = 2578469115111826932L;
	private EmpView ev;
	private final JPanel contentPanel = new JPanel();
	private JTextField jtfCareerDialog1; // 회사명
	private JTextField jtfCareerDialog2; // 입사일자
	private JTextField jtfCareerDialog3; // 퇴사일자
	private JTextField jtfCareerDialog4; // 직급
	private JTextField jtfCareerDialog5; // 근무부서
	private JTextField jtfCareerDialog6; // 퇴직사유

	private JButton careerDialogOK;
	private JButton careerDialogCancel;

	public AddCareerDialog(EmpView ev, EmpTabViewEvt etve) {
	    super(SwingUtilities.getWindowAncestor(ev), "경력 추가", ModalityType.APPLICATION_MODAL);
	    this.ev = ev;
	    setLayout(null);
	    setSize(498, 291);
	    setLocationRelativeTo(SwingUtilities.getWindowAncestor(ev));
		etve.setCareerDialog(this);
		


		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel jlbl1 = new JLabel("회사명");
		jlbl1.setBounds(121, 13, 57, 15);
		contentPanel.add(jlbl1);

		jtfCareerDialog1 = new JTextField();
		jtfCareerDialog1.setBounds(210, 10, 116, 21);
		contentPanel.add(jtfCareerDialog1);
		jtfCareerDialog1.setColumns(10);

		JLabel jlbl2 = new JLabel("입사일자");
		jlbl2.setBounds(121, 38, 57, 15);
		contentPanel.add(jlbl2);

		jtfCareerDialog2 = new JTextField("yyyy-mm-dd");
		PlaceholderUtil.applyDatePlaceholder(jtfCareerDialog2);

		jtfCareerDialog2.setBounds(210, 35, 116, 21);
		contentPanel.add(jtfCareerDialog2);
		jtfCareerDialog2.setColumns(10);

		JLabel jlbl3 = new JLabel("퇴사일자");
		jlbl3.setBounds(121, 63, 57, 15);
		contentPanel.add(jlbl3);

		jtfCareerDialog3 = new JTextField("yyyy-mm-dd");
		PlaceholderUtil.applyDatePlaceholder(jtfCareerDialog3);
		jtfCareerDialog3.setBounds(210, 60, 116, 21);
		contentPanel.add(jtfCareerDialog3);
		jtfCareerDialog3.setColumns(10);

		JLabel jlbl4 = new JLabel("직급");
		jlbl4.setBounds(121, 94, 57, 15);
		contentPanel.add(jlbl4);

		jtfCareerDialog4 = new JTextField();
		jtfCareerDialog4.setBounds(210, 91, 116, 21);
		contentPanel.add(jtfCareerDialog4);
		jtfCareerDialog4.setColumns(10);

		JLabel jlbl5 = new JLabel("근무부서");
		jlbl5.setBounds(121, 125, 57, 15);
		contentPanel.add(jlbl5);

		jtfCareerDialog5 = new JTextField();
		jtfCareerDialog5.setBounds(210, 122, 116, 21);
		contentPanel.add(jtfCareerDialog5);
		jtfCareerDialog5.setColumns(10);

		JLabel jlbl6 = new JLabel("퇴직사유");
		jlbl6.setBounds(121, 155, 57, 15);
		contentPanel.add(jlbl6);

		jtfCareerDialog6 = new JTextField();
		jtfCareerDialog6.setBounds(210, 152, 116, 21);
		contentPanel.add(jtfCareerDialog6);
		jtfCareerDialog6.setColumns(10);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		careerDialogOK = new JButton("OK");
		careerDialogOK.setActionCommand("OK");
		careerDialogOK.addActionListener(etve);
		buttonPane.add(careerDialogOK);
		getRootPane().setDefaultButton(careerDialogOK);

		careerDialogCancel = new JButton("Cancel");
		careerDialogCancel.setActionCommand("Cancel");
		careerDialogCancel.addActionListener(e -> dispose());
		buttonPane.add(careerDialogCancel);
	}

	// Getter methods
	public String getCompany() { return jtfCareerDialog1.getText().trim(); }
	public String getHireDate() { return jtfCareerDialog2.getText().trim(); }
	public String getLeaveDate() { return jtfCareerDialog3.getText().trim(); }
	public String getPosition() { return jtfCareerDialog4.getText().trim(); }
	public String getDept() { return jtfCareerDialog5.getText().trim(); }
	public String getReason() { return jtfCareerDialog6.getText().trim(); }

	public JButton getBtnOK() { return careerDialogOK; }
	public JButton getBtnCancel() { return careerDialogCancel; }
	
}//class
