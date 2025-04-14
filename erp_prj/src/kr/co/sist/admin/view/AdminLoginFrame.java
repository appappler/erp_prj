package kr.co.sist.admin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.admin.evt.AdminLoginEvent;

/**
 * 
 */
public class AdminLoginFrame extends JFrame{

	private static final long serialVersionUID = -5105682923635494615L;
	private JPanel jpParent;
	
	private JPanel jpTitleBackLine1;
	private JPanel jpTitleBackLine2;
	private JPanel jpTitleBackLine3;
	private JPanel jpTitleBackLine4;
	private JPanel jpTitleBackLine5;
	
	private JLabel jlblTitleText;
	private JLabel jlblTitleTextShadow;
	private JLabel jlblAdminModeText;
	
	private JLabel jlblIdText;
	private JLabel jlblPassText;
	
	private JTextField jtfInputId;
	private JPasswordField jpfInputPass;
	
	private JButton jbtnLogin;
	private JButton jbtnExit;
	
	public AdminLoginFrame() {
		super("사원관리 프로그램(관리자)");
		
		jpParent = new JPanel();
		
		jpParent.setLayout(null);
		jpParent.setBackground(new Color(182,199,209));
		
		jlblTitleText = new JLabel("사원 관리 시스템");
		jlblTitleText.setOpaque(false);
		jlblTitleText.setFont(new Font("Dialog", Font.BOLD, 58));
		jlblTitleText.setForeground(new Color(8, 60, 80));
		jlblTitleText.setBounds(257, 108, 500, 70);
		
		jlblTitleTextShadow = new JLabel("사원 관리 시스템");
		jlblTitleTextShadow.setOpaque(false);
		jlblTitleTextShadow.setFont(new Font("Dialog", Font.BOLD, 58));
		jlblTitleTextShadow.setForeground(Color.white);
		jlblTitleTextShadow.setBounds(259, 110, 500, 70);
		
		jlblAdminModeText = new JLabel("관리자 모드");
		jlblAdminModeText.setOpaque(false);
		jlblAdminModeText.setFont(new Font("Dialog", Font.BOLD, 16));
		jlblAdminModeText.setForeground(new Color(8, 60, 80));
		jlblAdminModeText.setBounds(615, 170, 440, 60);
		
		jlblIdText = new JLabel("아이디");
		jlblIdText.setOpaque(false);
		jlblIdText.setFont(new Font("Dialog", Font.BOLD, 20));
		jlblIdText.setForeground(new Color(75,98,113));
		jlblIdText.setBounds(185, 220, 440, 60);
		
		jlblPassText = new JLabel("비밀번호");
		jlblPassText.setOpaque(false);
		jlblPassText.setFont(new Font("Dialog", Font.BOLD, 20));
		jlblPassText.setForeground(new Color(75,98,113));
		jlblPassText.setBounds(165, 288, 440, 60);
		
		jtfInputId = new JTextField();
		jtfInputId.setBackground(Color.white);
		jtfInputId.setFont(new Font("Dialog", Font.BOLD, 32));
		jtfInputId.setBounds(260, 220, 440, 54);
		
		jpfInputPass = new JPasswordField();
		jpfInputPass.setBackground(Color.white);
		jpfInputPass.setFont(new Font("Dialog", Font.BOLD, 32));
		jpfInputPass.setBounds(260, 290, 440, 54);
		
		jbtnLogin = new JButton("로그인");
		jbtnLogin.setBackground(new Color(8, 60, 80));
		jbtnLogin.setFont(new Font("Dialog", Font.BOLD, 24));
		jbtnLogin.setForeground(Color.white);
		jbtnLogin.setBounds(260, 360, 220, 60);
		
		jbtnExit = new JButton("종료");
		jbtnExit.setBackground(new Color(127,142,158));
		jbtnExit.setFont(new Font("Dialog", Font.BOLD, 24));
		jbtnExit.setForeground(Color.white);
		jbtnExit.setBounds(600, 360, 100, 60);
		
		
		jpParent.add(jlblTitleText);
		jpParent.add(jlblTitleTextShadow);
		jpParent.add(jlblAdminModeText);
		jpParent.add(jlblIdText);
		jpParent.add(jlblPassText);
		 
		jpParent.add(jtfInputId);
		jpParent.add(jpfInputPass);
		jpParent.add(jbtnLogin);
		jpParent.add(jbtnExit);
		
		add(jpParent);
		
		AdminLoginEvent ale = new AdminLoginEvent(this);
		jbtnLogin.addActionListener(ale);
		jbtnExit.addActionListener(ale);
		jpfInputPass.addKeyListener(ale);
		
		setResizable(false);
		setBounds(300, 200, 960, 560);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}//AdminLoginFrame

	public JPanel getJpParent() {
		return jpParent;
	}

	public void setJpParent(JPanel jpParent) {
		this.jpParent = jpParent;
	}

	public JPanel getJpTitleBackLine1() {
		return jpTitleBackLine1;
	}

	public void setJpTitleBackLine1(JPanel jpTitleBackLine1) {
		this.jpTitleBackLine1 = jpTitleBackLine1;
	}

	public JPanel getJpTitleBackLine2() {
		return jpTitleBackLine2;
	}

	public void setJpTitleBackLine2(JPanel jpTitleBackLine2) {
		this.jpTitleBackLine2 = jpTitleBackLine2;
	}

	public JPanel getJpTitleBackLine3() {
		return jpTitleBackLine3;
	}

	public void setJpTitleBackLine3(JPanel jpTitleBackLine3) {
		this.jpTitleBackLine3 = jpTitleBackLine3;
	}

	public JPanel getJpTitleBackLine4() {
		return jpTitleBackLine4;
	}

	public void setJpTitleBackLine4(JPanel jpTitleBackLine4) {
		this.jpTitleBackLine4 = jpTitleBackLine4;
	}

	public JPanel getJpTitleBackLine5() {
		return jpTitleBackLine5;
	}

	public void setJpTitleBackLine5(JPanel jpTitleBackLine5) {
		this.jpTitleBackLine5 = jpTitleBackLine5;
	}

	public JLabel getJlblTitleText() {
		return jlblTitleText;
	}

	public void setJlblTitleText(JLabel jlblTitleText) {
		this.jlblTitleText = jlblTitleText;
	}

	public JLabel getJlblTitleTextShadow() {
		return jlblTitleTextShadow;
	}

	public void setJlblTitleTextShadow(JLabel jlblTitleTextShadow) {
		this.jlblTitleTextShadow = jlblTitleTextShadow;
	}

	public JLabel getJlblAdminModeText() {
		return jlblAdminModeText;
	}

	public void setJlblAdminModeText(JLabel jlblAdminModeText) {
		this.jlblAdminModeText = jlblAdminModeText;
	}

	public JLabel getJlblIdText() {
		return jlblIdText;
	}

	public void setJlblIdText(JLabel jlblIdText) {
		this.jlblIdText = jlblIdText;
	}

	public JLabel getJlblPassText() {
		return jlblPassText;
	}

	public void setJlblPassText(JLabel jlblPassText) {
		this.jlblPassText = jlblPassText;
	}

	public JTextField getJtfInputId() {
		return jtfInputId;
	}

	public void setJtfInputId(JTextField jtfInputId) {
		this.jtfInputId = jtfInputId;
	}

	public JPasswordField getJpfInputPass() {
		return jpfInputPass;
	}

	public void setJpfInputPass(JPasswordField jpfInputPass) {
		this.jpfInputPass = jpfInputPass;
	}

	public JButton getJbtnLogin() {
		return jbtnLogin;
	}

	public void setJbtnLogin(JButton jbtnLogin) {
		this.jbtnLogin = jbtnLogin;
	}

	public JButton getJbtnExit() {
		return jbtnExit;
	}

	public void setJbtnExit(JButton jbtnExit) {
		this.jbtnExit = jbtnExit;
	}

}//class
