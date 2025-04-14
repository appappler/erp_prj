package kr.co.sist.user.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.co.sist.user.evt.UserMenuEvent;
import kr.co.sist.user.vo.EmpVO;
import kr.co.sist.user.vo.UserAccountVO;

/**
 * 
 */
public class UserParentFrame extends JFrame{
	
	private static final long serialVersionUID = -2066561797055913344L;
	private JPanel jpParent;
	private JPanel jpParentContent;
	private JPanel jpTitleSquare;
	private JPanel jpTitleWithLogout;
	private JPanel jpLeft;
	private JPanel jpView;
	
	private JPanel jpMenuForDashBoard;
	private JButton jbtnMenuHome;

	private JPanel jpMenuForTools;
	private JButton jbtnMenuMyAttend;
	private JButton jbtnMenuMyInfo;
	private JButton jbtnMenuMyPay;
	private JButton jbtnMenuMyShare;
	
	private JPanel jpTitleSquareBottom;
	
	private JLabel jlblTitle;
	private JLabel jlblTitleShadow;
	private JLabel jlblCurrentUserIDWellcom;
	private JLabel jlblCurrentUserIDWellcomShadow;
	private JLabel jlblCurrentUserID;
	private JLabel jlblCurrentUserIDShadow;
	private JButton jbtnLogout;
	
	private String currentUserID;
	
	public UserMenuEvent ume; 
	
	public AttView jpAttView;
	public EmpDetailView jpEmpDetailView;
	public MainFrame jpPayrollView;
	public DocumentShareView jpDocumnetShareView;
	
	private int currentUserIdToInt;
	
	public UserParentFrame(UserAccountVO inputVO) {
		
		super("사원관리 프로그램");
		
		currentUserID = inputVO.getUserId();
		currentUserIdToInt = Integer.parseInt(inputVO.getUserId());
		EmpVO eVO = new EmpVO();
		
		Color MainColor = new Color(8, 60, 80);
		Color SubColor = new Color(226,240,248);
		Font MenuFont = new Font("Dialog", Font.BOLD, 24);
		
		jpParent = new JPanel();
		jpParent.setLayout(null);
		jpParent.setBounds(0, 0, 1100, 800); 
		
		jpTitleWithLogout = new JPanel();
		jpTitleSquare = new JPanel();
		
		jpTitleSquareBottom = new JPanel();
		
		jpView = new JPanel();
		jpLeft = new JPanel();
		
		jlblTitle = new JLabel("대시보드"); 
		jbtnLogout = new JButton("로그아웃"); 
		
		jbtnMenuMyAttend = new JButton();
		jbtnMenuMyInfo = new JButton();
		jbtnMenuMyPay = new JButton();
		jbtnMenuMyShare = new JButton();
		
		jpParent.setLayout(null);
		jpParent.setBackground(new Color(182,199,209));
	
		jlblTitle.setBounds(210, 18, 240, 60);
		jlblTitle.setBackground(new Color(19, 85, 118));
		jlblTitle.setForeground(Color.white);
		jlblTitle.setFont(new Font("Dialog", Font.BOLD, 40));
		
		jlblTitleShadow = new JLabel("대시보드") ;
		jlblTitleShadow.setBounds(212, 20, 240, 60);
		jlblTitleShadow.setForeground(Color.black);
		jlblTitleShadow.setFont(new Font("Dialog", Font.BOLD, 40));
		
		jpTitleSquare.setBounds(190, 20, 1100, 60);
		jpTitleSquare.setBackground(new Color(8, 60, 80));
		jpTitleSquare.setForeground(MainColor);
		
		jpTitleSquareBottom.setBackground(MainColor);
		jpTitleSquareBottom.setBounds(0, 731, 1330, 30);
		
		jlblCurrentUserIDWellcom = new JLabel("접속:");
		jlblCurrentUserIDWellcom.setBounds(20, 715, 300, 60);
		jlblCurrentUserIDWellcom.setForeground(new Color(127,188,209));
		jlblCurrentUserIDWellcom.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jlblCurrentUserIDWellcomShadow = new JLabel("접속:");
		jlblCurrentUserIDWellcomShadow.setBounds(21, 716, 300, 60);
		jlblCurrentUserIDWellcomShadow.setForeground(Color.black);
		jlblCurrentUserIDWellcomShadow.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jlblCurrentUserID = new JLabel(currentUserID+"님");
		jlblCurrentUserID.setBounds(60, 715, 300, 60);
		jlblCurrentUserID.setForeground(Color.WHITE);
		jlblCurrentUserID.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jlblCurrentUserIDShadow = new JLabel(currentUserID+"님") ;
		jlblCurrentUserIDShadow.setBounds(61, 716, 300, 60);
		jlblCurrentUserIDShadow.setForeground(Color.black);
		jlblCurrentUserIDShadow.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jbtnLogout.setBounds(1180, 35, 90, 30);
		jbtnLogout.setBackground(new Color(22,48,59));
		jbtnLogout.setForeground(Color.white);
		jbtnLogout.setFont(new Font("Dialog", Font.PLAIN, 12));

		
		jpLeft.setBounds(15, 15, 160, 705);
		jpLeft.setOpaque(false);
		jpLeft.setLayout(null);
		
		jpView.setOpaque(false);
		jpView.setBounds(190, 75, 1300, 642);
		
		
		jpAttView = new AttView(inputVO);
		jpEmpDetailView = new EmpDetailView(currentUserIdToInt);
		jpPayrollView = new MainFrame(inputVO);
		jpDocumnetShareView = new DocumentShareView(currentUserIdToInt);
		
		jpView.add(jpAttView);
		jpView.add(jpEmpDetailView);
		jpView.add(jpPayrollView);
		jpView.add(jpDocumnetShareView);

		jpMenuForDashBoard = new JPanel();
		jpMenuForDashBoard.setLayout(new GridLayout(1, 1));
		jpMenuForDashBoard.setBounds(5, 5, 150, 60);
		
		jpMenuForTools = new JPanel();
		jpMenuForTools.setOpaque(false);
		jpMenuForTools.setLayout(new GridLayout(8, 1));
		jpMenuForTools.setBounds(5, 60, 150, 647);
		
		jbtnMenuHome = new JButton("사원 메뉴");
		jbtnMenuMyAttend = new JButton("근태");
		jbtnMenuMyInfo = new JButton("조회");
		jbtnMenuMyPay = new JButton("급여");
		jbtnMenuMyShare = new JButton("공유");
		
		//jbtnMenuHome.setPreferredSize(new Dimension(140, 60));
		//jbtnMenuAttend.setPreferredSize(new Dimension(140, 40));
		
		jbtnMenuHome.setFont(MenuFont);
		jbtnMenuHome.setForeground(Color.white);
		jbtnMenuHome.setBackground(MainColor);

		jbtnMenuMyAttend.setFont(MenuFont);
		jbtnMenuMyAttend.setForeground(Color.black);
		jbtnMenuMyAttend.setBackground(SubColor);

		jbtnMenuMyInfo.setFont(MenuFont);
		jbtnMenuMyInfo.setForeground(Color.black);
		jbtnMenuMyInfo.setBackground(SubColor);

		jbtnMenuMyPay.setFont(MenuFont);
		jbtnMenuMyPay.setForeground(Color.black);
		jbtnMenuMyPay.setBackground(SubColor);

		jbtnMenuMyShare.setFont(MenuFont);
		jbtnMenuMyShare.setForeground(Color.black);
		jbtnMenuMyShare.setBackground(SubColor);

		jpMenuForDashBoard.add(jbtnMenuHome);
		jpMenuForTools.add(jbtnMenuMyAttend);
		jpMenuForTools.add(jbtnMenuMyInfo);
		jpMenuForTools.add(jbtnMenuMyPay);
		jpMenuForTools.add(jbtnMenuMyShare);
		
		jpLeft.add(jpMenuForDashBoard);
		jpLeft.add(jpMenuForTools);
		
		jpParent.add(jlblTitle);
		jpParent.add(jlblTitleShadow);
		jpParent.add(jlblCurrentUserIDWellcom);
		jpParent.add(jlblCurrentUserIDWellcomShadow);
		jpParent.add(jlblCurrentUserID);
		jpParent.add(jlblCurrentUserIDShadow);
		jpParent.add(jbtnLogout);
		jpParent.add(jpTitleSquare);
		jpParent.add(jpTitleSquareBottom);
	    jpParent.add(jpLeft); 
	    
	    jpParent.add(jpView);
	    
	    add(jpParent);
	    
	    addWindowListener(ume);
	    ume = new UserMenuEvent(this);
	    jbtnMenuMyAttend.addActionListener(ume);
	    jbtnMenuMyInfo.addActionListener(ume);
	    jbtnMenuMyPay.addActionListener(ume);
	    jbtnMenuMyShare.addActionListener(ume);
	    jbtnLogout.addActionListener(ume);
	    
		setResizable(false);
		setBounds(50, 50, 1330, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		

	    
	}//UserParentFrame

	public DocumentShareView getJpDocumnetShareView() {
		return jpDocumnetShareView;
	}

	public void setJpDocumnetShareView(DocumentShareView jpDocumnetShareView) {
		this.jpDocumnetShareView = jpDocumnetShareView;
	}

	public AttView getJpAttView() {
		return jpAttView;
	}

	public void setJpAttView(AttView jpAttView) {
		this.jpAttView = jpAttView;
	}

	public int getCurrentUserIdToInt() {
		return currentUserIdToInt;
	}

	public void setCurrentUserIdToInt(int currentUserIdToInt) {
		this.currentUserIdToInt = currentUserIdToInt;
	}

	public JPanel getJpParent() {
		return jpParent;
	}

	public void setJpParent(JPanel jpParent) {
		this.jpParent = jpParent;
	}

	public JPanel getJpParentContent() {
		return jpParentContent;
	}

	public void setJpParentContent(JPanel jpParentContent) {
		this.jpParentContent = jpParentContent;
	}

	public JPanel getJpTitleSquare() {
		return jpTitleSquare;
	}

	public void setJpTitleSquare(JPanel jpTitleSquare) {
		this.jpTitleSquare = jpTitleSquare;
	}

	public JPanel getJpTitleWithLogout() {
		return jpTitleWithLogout;
	}

	public void setJpTitleWithLogout(JPanel jpTitleWithLogout) {
		this.jpTitleWithLogout = jpTitleWithLogout;
	}

	public JPanel getJpLeft() {
		return jpLeft;
	}

	public void setJpLeft(JPanel jpLeft) {
		this.jpLeft = jpLeft;
	}

	public JPanel getJpView() {
		return jpView;
	}

	public void setJpView(JPanel jpView) {
		this.jpView = jpView;
	}

	public JPanel getJpMenuForDashBoard() {
		return jpMenuForDashBoard;
	}

	public void setJpMenuForDashBoard(JPanel jpMenuForDashBoard) {
		this.jpMenuForDashBoard = jpMenuForDashBoard;
	}

	public JButton getJbtnMenuHome() {
		return jbtnMenuHome;
	}

	public void setJbtnMenuHome(JButton jbtnMenuHome) {
		this.jbtnMenuHome = jbtnMenuHome;
	}

	public JPanel getJpMenuForTools() {
		return jpMenuForTools;
	}

	public void setJpMenuForTools(JPanel jpMenuForTools) {
		this.jpMenuForTools = jpMenuForTools;
	}

	public JButton getJbtnMenuMyAttend() {
		return jbtnMenuMyAttend;
	}

	public void setJbtnMenuMyAttend(JButton jbtnMenuMyAttend) {
		this.jbtnMenuMyAttend = jbtnMenuMyAttend;
	}

	public JButton getJbtnMenuMyInfo() {
		return jbtnMenuMyInfo;
	}

	public void setJbtnMenuMyInfo(JButton jbtnMenuMyInfo) {
		this.jbtnMenuMyInfo = jbtnMenuMyInfo;
	}

	public JButton getJbtnMenuMyPay() {
		return jbtnMenuMyPay;
	}

	public void setJbtnMenuMyPay(JButton jbtnMenuMyPay) {
		this.jbtnMenuMyPay = jbtnMenuMyPay;
	}

	public JButton getJbtnMenuMyShare() {
		return jbtnMenuMyShare;
	}

	public void setJbtnMenuMyShare(JButton jbtnMenuMyShare) {
		this.jbtnMenuMyShare = jbtnMenuMyShare;
	}

	public JPanel getJpTitleSquareBottom() {
		return jpTitleSquareBottom;
	}

	public void setJpTitleSquareBottom(JPanel jpTitleSquareBottom) {
		this.jpTitleSquareBottom = jpTitleSquareBottom;
	}

	public JLabel getJlblTitle() {
		return jlblTitle;
	}

	public void setJlblTitle(JLabel jlblTitle) {
		this.jlblTitle = jlblTitle;
	}

	public JLabel getJlblTitleShadow() {
		return jlblTitleShadow;
	}

	public void setJlblTitleShadow(JLabel jlblTitleShadow) {
		this.jlblTitleShadow = jlblTitleShadow;
	}

	public JLabel getJlblCurrentUserIDWellcom() {
		return jlblCurrentUserIDWellcom;
	}

	public void setJlblCurrentUserIDWellcom(JLabel jlblCurrentUserIDWellcom) {
		this.jlblCurrentUserIDWellcom = jlblCurrentUserIDWellcom;
	}

	public JLabel getJlblCurrentUserIDWellcomShadow() {
		return jlblCurrentUserIDWellcomShadow;
	}

	public void setJlblCurrentUserIDWellcomShadow(JLabel jlblCurrentUserIDWellcomShadow) {
		this.jlblCurrentUserIDWellcomShadow = jlblCurrentUserIDWellcomShadow;
	}

	public JLabel getJlblCurrentUserID() {
		return jlblCurrentUserID;
	}

	public void setJlblCurrentUserID(JLabel jlblCurrentUserID) {
		this.jlblCurrentUserID = jlblCurrentUserID;
	}

	public JLabel getJlblCurrentUserIDShadow() {
		return jlblCurrentUserIDShadow;
	}

	public void setJlblCurrentUserIDShadow(JLabel jlblCurrentUserIDShadow) {
		this.jlblCurrentUserIDShadow = jlblCurrentUserIDShadow;
	}

	public JButton getJbtnLogout() {
		return jbtnLogout;
	}

	public void setJbtnLogout(JButton jbtnLogout) {
		this.jbtnLogout = jbtnLogout;
	}

	public String getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(String currentUserID) {
		this.currentUserID = currentUserID;
	}

	public UserMenuEvent getUme() {
		return ume;
	}

	public void setUme(UserMenuEvent ume) {
		this.ume = ume;
	}

	public EmpDetailView getJpEmpDetailView() {
		return jpEmpDetailView;
	}

	public void setJpEmpDetailView(EmpDetailView jpEmpDetailView) {
		this.jpEmpDetailView = jpEmpDetailView;
	}

	public MainFrame getJpPayrollView() {
		return jpPayrollView;
	}

	public void setJpPayrollView(MainFrame jpPayrollView) {
		this.jpPayrollView = jpPayrollView;
	}

	




}//class
