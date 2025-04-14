package kr.co.sist.admin.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.co.sist.admin.evt.AdminMenuEvent;
import kr.co.sist.admin.vo.AdminAccountVO;

/**
 * 
 */
public class AdminParentFrame extends JFrame{
	
	private static final long serialVersionUID = 8038202301263791371L;
	private JPanel jpParent;
	private JPanel jpTitleSquare;
	private JPanel jpTitleSquareBottom;
	private JPanel jpTitleWithLogout;
	private JPanel jpLeft;
	private JPanel jpView;
	
	private JPanel jpMenuForDashBoard;
	private JButton jbtnMenuHome;
	
	private JPanel jpMenuForTools;
	private JButton jbtnMenuAdd;
	private JButton jbtnMenuList;
	private JButton jbtnMenuAttend;
	private JButton jbtnMenuPayroll;
	private JButton jbtnMenuSalary;
	private JButton jbtnMenuRank;
	private JButton jbtnMenuDept;
	private JButton jbtnMenuDocument;
	
	private JLabel jlblTitle;
	private JLabel jlblTitleShadow;
	private JLabel jlblCurrentAdminIDWellcom;
	private JLabel jlblCurrentAdminIDWellcomShadow;
	private JLabel jlblCurrentAdminID;
	private JLabel jlblCurrentAdminIDShadow;
	private JButton jbtnLogout;
	
	public AdminMenuEvent ame; 
	
	public DashBoardView jpDashBoardView;//대시보드
	public AttView jpAttView;
	public EmpView jpEmpViewPanel;//사원추가
	public EmpListView jpEmpListViewPanel;//사원목록
	public MainFrame jpPayrollViewFrame;//급여관리
	public RankView jpRankView;//직급관리
	public SalaryView jpSalaryView;//연봉관리
	public DeptRun jpDeptView;//부서관리
	public DocumentManagerView jpAdminShareView;//문서공유
	
	
	private String currentAdminID;

	public AdminParentFrame(AdminAccountVO inputVO) {
		
		super("사원관리 프로그램(관리자)");
		
		currentAdminID = inputVO.getAdminId();
		
		Color MainColor = new Color(8, 60, 80);
		Color SubColor = new Color(226,240,248);
		Font MenuFont = new Font("Dialog", Font.BOLD, 18);
		
		jpParent = new JPanel();
		jpParent.setLayout(null);
		jpParent.setBounds(0, 0, 1100, 700); 
		
		jpTitleWithLogout = new JPanel();
		jpTitleSquare = new JPanel();
		
		jpTitleSquareBottom = new JPanel();
		
		jpView = new JPanel();
		jpLeft = new JPanel();
		
		jlblTitle = new JLabel("대시보드"); 
		jbtnLogout = new JButton("로그아웃"); 
		
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
		jpTitleSquareBottom.setBounds(0, 741, 1330, 30);
		
		jlblCurrentAdminIDWellcom = new JLabel("접속:");
		jlblCurrentAdminIDWellcom.setBounds(20, 725, 300, 60);
		jlblCurrentAdminIDWellcom.setForeground(new Color(127,188,209));
		jlblCurrentAdminIDWellcom.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jlblCurrentAdminIDWellcomShadow = new JLabel("접속:");
		jlblCurrentAdminIDWellcomShadow.setBounds(21, 726, 300, 60);
		jlblCurrentAdminIDWellcomShadow.setForeground(Color.black);
		jlblCurrentAdminIDWellcomShadow.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jlblCurrentAdminID = new JLabel(currentAdminID+"(관리자)");
		jlblCurrentAdminID.setBounds(60, 725, 300, 60);
		jlblCurrentAdminID.setForeground(Color.WHITE);
		jlblCurrentAdminID.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jlblCurrentAdminIDShadow = new JLabel(currentAdminID+"(관리자)") ;
		jlblCurrentAdminIDShadow.setBounds(61, 726, 300, 60);
		jlblCurrentAdminIDShadow.setForeground(Color.black);
		jlblCurrentAdminIDShadow.setFont(new Font("Dialog", Font.BOLD, 14));
		
		jbtnLogout.setBounds(880, 35, 90, 30);
		jbtnLogout.setBackground(new Color(22,48,59));
		jbtnLogout.setForeground(Color.white);
		jbtnLogout.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		jpLeft.setBounds(15, 15, 160, 705);
		jpLeft.setOpaque(false);
		jpLeft.setLayout(null);
		
		jpView.setOpaque(false);
		jpView.setBounds(190, 75, 1100, 710);
		
		jpDashBoardView = new DashBoardView();
		jpEmpViewPanel = new EmpView();
		jpEmpListViewPanel = new EmpListView();
		jpPayrollViewFrame = new MainFrame();
		jpSalaryView = new SalaryView();
		jpRankView = new RankView();
		jpAttView = new AttView();
		jpDeptView = new DeptRun();
		jpAdminShareView = new DocumentManagerView();

		jpView.add(jpDashBoardView);
		jpView.add(jpAttView);//근태
		jpView.add(jpEmpViewPanel);//사원등록
		jpView.add(jpEmpListViewPanel);//사원목록
		jpView.add(jpPayrollViewFrame);//급여관리
		jpView.add(jpSalaryView);//연봉
		jpView.add(jpRankView);//직급
		jpView.add(jpDeptView);//부서
		jpView.add(jpAdminShareView);//문서
		
		jpMenuForDashBoard = new JPanel();
		jpMenuForDashBoard.setLayout(new GridLayout(1, 1));
		jpMenuForDashBoard.setBounds(5, 5, 150, 60);
		
		jpMenuForTools = new JPanel();
		jpMenuForTools.setOpaque(false);
		jpMenuForTools.setLayout(new GridLayout(8, 1));
		jpMenuForTools.setBounds(5, 60, 150, 647);
		
		jbtnMenuHome = new JButton("관리자 메뉴");
		
		jbtnMenuAttend = new JButton("근태관리");
		jbtnMenuAdd = new JButton("사원추가");
		jbtnMenuList = new JButton("사원목록");
		jbtnMenuPayroll = new JButton("급여관리");
		jbtnMenuSalary = new JButton("연봉관리");
		jbtnMenuRank = new JButton("직급관리");
		jbtnMenuDept = new JButton("부서관리");
		jbtnMenuDocument = new JButton("문서관리");
		
		//jbtnMenuHome.setPreferredSize(new Dimension(140, 60));
		//jbtnMenuAttend.setPreferredSize(new Dimension(140, 40));
		
		jbtnMenuHome.setFont(MenuFont);
		jbtnMenuHome.setForeground(Color.white);
		jbtnMenuHome.setBackground(MainColor);

		jbtnMenuAdd.setFont(MenuFont);
		jbtnMenuAdd.setForeground(Color.black);
		jbtnMenuAdd.setBackground(SubColor);

		jbtnMenuList.setFont(MenuFont);
		jbtnMenuList.setForeground(Color.black);
		jbtnMenuList.setBackground(SubColor);

		jbtnMenuAttend.setFont(MenuFont);
		jbtnMenuAttend.setForeground(Color.black);
		jbtnMenuAttend.setBackground(SubColor);

		jbtnMenuPayroll.setFont(MenuFont);
		jbtnMenuPayroll.setForeground(Color.black);
		jbtnMenuPayroll.setBackground(SubColor);

		jbtnMenuSalary.setFont(MenuFont);
		jbtnMenuSalary.setForeground(Color.black);
		jbtnMenuSalary.setBackground(SubColor);

		jbtnMenuRank.setFont(MenuFont);
		jbtnMenuRank.setForeground(Color.black);
		jbtnMenuRank.setBackground(SubColor);

		jbtnMenuDept.setFont(MenuFont);
		jbtnMenuDept.setForeground(Color.black);
		jbtnMenuDept.setBackground(SubColor);

		jbtnMenuDocument.setFont(MenuFont);
		jbtnMenuDocument.setForeground(Color.black);
		jbtnMenuDocument.setBackground(SubColor);

		jpMenuForDashBoard.add(jbtnMenuHome);
		jpMenuForTools.add(jbtnMenuAttend);
		jpMenuForTools.add(jbtnMenuAdd);
		jpMenuForTools.add(jbtnMenuList);
		jpMenuForTools.add(jbtnMenuPayroll);
		jpMenuForTools.add(jbtnMenuSalary);
		jpMenuForTools.add(jbtnMenuRank);
		jpMenuForTools.add(jbtnMenuDept);
		jpMenuForTools.add(jbtnMenuDocument);
		
		jpLeft.add(jpMenuForDashBoard);
		jpLeft.add(jpMenuForTools);
		
		jpParent.add(jlblTitle);
		jpParent.add(jlblTitleShadow);
		jpParent.add(jlblCurrentAdminIDWellcom);
		jpParent.add(jlblCurrentAdminIDWellcomShadow);
		jpParent.add(jlblCurrentAdminID);
		jpParent.add(jlblCurrentAdminIDShadow);
		jpParent.add(jbtnLogout);
		jpParent.add(jpTitleSquare);
		jpParent.add(jpTitleSquareBottom);
	    jpParent.add(jpLeft); 
	    
	    jpParent.add(jpView);
	    
	    add(jpParent);
	    
	    addWindowListener(ame);
	    
	    ame = new AdminMenuEvent(this);
		jbtnMenuHome.addActionListener(ame);
		jbtnMenuAdd.addActionListener(ame);
		jbtnMenuList.addActionListener(ame);
		jbtnMenuAttend.addActionListener(ame);
		jbtnMenuPayroll.addActionListener(ame);
		jbtnMenuSalary.addActionListener(ame);
		jbtnMenuRank.addActionListener(ame);
		jbtnMenuDept.addActionListener(ame);
		jbtnMenuDocument.addActionListener(ame);
		jbtnLogout.addActionListener(ame);
		
		
		setResizable(false);
		setBounds(50, 50, 1330, 810);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	}//AdminParentFrame

	public DocumentManagerView getJpAdminShareView() {
		return jpAdminShareView;
	}

	public void setJpAdminShareView(DocumentManagerView jpAdminShareView) {
		this.jpAdminShareView = jpAdminShareView;
	}

	public JPanel getJpParent() {
		return jpParent;
	}

	public void setJpParent(JPanel jpParent) {
		this.jpParent = jpParent;
	}

	public JPanel getJpTitleSquare() {
		return jpTitleSquare;
	}

	public void setJpTitleSquare(JPanel jpTitleSquare) {
		this.jpTitleSquare = jpTitleSquare;
	}

	public JPanel getJpTitleSquareBottom() {
		return jpTitleSquareBottom;
	}

	public void setJpTitleSquareBottom(JPanel jpTitleSquareBottom) {
		this.jpTitleSquareBottom = jpTitleSquareBottom;
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

	public JButton getJbtnMenuAdd() {
		return jbtnMenuAdd;
	}

	public void setJbtnMenuAdd(JButton jbtnMenuAdd) {
		this.jbtnMenuAdd = jbtnMenuAdd;
	}

	public JButton getJbtnMenuList() {
		return jbtnMenuList;
	}

	public void setJbtnMenuList(JButton jbtnMenuList) {
		this.jbtnMenuList = jbtnMenuList;
	}

	public JButton getJbtnMenuAttend() {
		return jbtnMenuAttend;
	}

	public void setJbtnMenuAttend(JButton jbtnMenuAttend) {
		this.jbtnMenuAttend = jbtnMenuAttend;
	}

	public JButton getJbtnMenuPayroll() {
		return jbtnMenuPayroll;
	}

	public void setJbtnMenuPayroll(JButton jbtnMenuPayroll) {
		this.jbtnMenuPayroll = jbtnMenuPayroll;
	}

	public JButton getJbtnMenuSalary() {
		return jbtnMenuSalary;
	}

	public void setJbtnMenuSalary(JButton jbtnMenuSalary) {
		this.jbtnMenuSalary = jbtnMenuSalary;
	}

	public JButton getJbtnMenuRank() {
		return jbtnMenuRank;
	}

	public void setJbtnMenuRank(JButton jbtnMenuRank) {
		this.jbtnMenuRank = jbtnMenuRank;
	}

	public JButton getJbtnMenuDept() {
		return jbtnMenuDept;
	}

	public void setJbtnMenuDept(JButton jbtnMenuDept) {
		this.jbtnMenuDept = jbtnMenuDept;
	}

	public JButton getJbtnMenuDocument() {
		return jbtnMenuDocument;
	}

	public void setJbtnMenuDocument(JButton jbtnMenuDocument) {
		this.jbtnMenuDocument = jbtnMenuDocument;
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

	public JLabel getJlblCurrentAdminIDWellcom() {
		return jlblCurrentAdminIDWellcom;
	}

	public void setJlblCurrentAdminIDWellcom(JLabel jlblCurrentAdminIDWellcom) {
		this.jlblCurrentAdminIDWellcom = jlblCurrentAdminIDWellcom;
	}

	public JLabel getJlblCurrentAdminIDWellcomShadow() {
		return jlblCurrentAdminIDWellcomShadow;
	}

	public void setJlblCurrentAdminIDWellcomShadow(JLabel jlblCurrentAdminIDWellcomShadow) {
		this.jlblCurrentAdminIDWellcomShadow = jlblCurrentAdminIDWellcomShadow;
	}

	public JLabel getJlblCurrentAdminID() {
		return jlblCurrentAdminID;
	}

	public void setJlblCurrentAdminID(JLabel jlblCurrentAdminID) {
		this.jlblCurrentAdminID = jlblCurrentAdminID;
	}

	public JLabel getJlblCurrentAdminIDShadow() {
		return jlblCurrentAdminIDShadow;
	}

	public void setJlblCurrentAdminIDShadow(JLabel jlblCurrentAdminIDShadow) {
		this.jlblCurrentAdminIDShadow = jlblCurrentAdminIDShadow;
	}

	public JButton getJbtnLogout() {
		return jbtnLogout;
	}

	public void setJbtnLogout(JButton jbtnLogout) {
		this.jbtnLogout = jbtnLogout;
	}

	public AdminMenuEvent getAme() {
		return ame;
	}

	public void setAme(AdminMenuEvent ame) {
		this.ame = ame;
	}

	public DashBoardView getJpDashBoardView() {
		return jpDashBoardView;
	}

	public void setJpDashBoardView(DashBoardView jpDashBoardView) {
		this.jpDashBoardView = jpDashBoardView;
	}

	public AttView getJpAttView() {
		return jpAttView;
	}

	public void setJpAttView(AttView jpAttView) {
		this.jpAttView = jpAttView;
	}

	public EmpView getJpEmpViewPanel() {
		return jpEmpViewPanel;
	}

	public void setJpEmpViewPanel(EmpView jpEmpViewPanel) {
		this.jpEmpViewPanel = jpEmpViewPanel;
	}

	public EmpListView getJpEmpListViewPanel() {
		return jpEmpListViewPanel;
	}

	public void setJpEmpListViewPanel(EmpListView jpEmpListViewPanel) {
		this.jpEmpListViewPanel = jpEmpListViewPanel;
	}

	public MainFrame getJpPayrollViewFrame() {
		return jpPayrollViewFrame;
	}

	public void setJpPayrollViewFrame(MainFrame jpPayrollViewFrame) {
		this.jpPayrollViewFrame = jpPayrollViewFrame;
	}

	public RankView getJpRankView() {
		return jpRankView;
	}

	public void setJpRankView(RankView jpRankView) {
		this.jpRankView = jpRankView;
	}

	public SalaryView getJpSalaryView() {
		return jpSalaryView;
	}

	public void setJpSalaryView(SalaryView jpSalaryView) {
		this.jpSalaryView = jpSalaryView;
	}





	public DeptRun getJpDeptView() {
		return jpDeptView;
	}

	public void setJpDeptView(DeptRun jpDeptView) {
		this.jpDeptView = jpDeptView;
	}

	public String getCurrentAdminID() {
		return currentAdminID;
	}

	public void setCurrentAdminID(String currentAdminID) {
		this.currentAdminID = currentAdminID;
	}



}//class
