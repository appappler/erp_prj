package kr.co.sist.admin.evt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import kr.co.sist.admin.view.AdminLoginFrame;
import kr.co.sist.admin.view.AdminParentFrame;

/**
 * 메인프레임(AdminParentFrame)의 메뉴에 대한 이벤트 처리 클래스<br>
 */
public class AdminMenuEvent extends WindowAdapter implements ActionListener{
	
	public String selection;
	private AdminParentFrame apf;
	
	public AdminMenuEvent(AdminParentFrame apf) {
		
		this.apf = apf;
		String selection = "대시보드"; 
		this.selection=selection;
		
		apf.setResizable(true);
		apf.setSize(1330, 810);
		apf.setResizable(false);
		
		apf.getJpTitleSquare().setSize(1100, 60);
		apf.getJpView().setSize(1100, 642);
		
		apf.getJbtnLogout().setBounds(1180, 35, 90, 30);
		
		apf.getJpTitleSquareBottom().setBounds(0, 741, 1330, 30);
		
		apf.getJlblCurrentAdminIDWellcom().setBounds(21, 725, 300, 60);
		apf.getJlblCurrentAdminIDWellcomShadow().setBounds(22, 726, 300, 60);
		
		apf.getJlblCurrentAdminID().setBounds(60, 725, 300, 60);
		apf.getJlblCurrentAdminIDShadow().setBounds(61, 726, 300, 60);
		
		apf.getJpDashBoardView().setVisible(true);//대시보드JPanel
		apf.getJpAttView().setVisible(false);//근태관리JPanel
		apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
		apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
		apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
		apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
		apf.getJpRankView().setVisible(false);//직급관리JPanel
		apf.getJpDeptView().setVisible(false);//부서관리JPanel
		apf.getJpAdminShareView().setVisible(false);
		
		System.out.println(getSelection().toString());
	}//AdminMenuEvent
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == apf.getJbtnMenuHome()) {
			
			apf.setResizable(true);
			apf.setSize(1330, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(1100, 60);
			apf.getJpView().setSize(1100, 642);
			
			apf.getJbtnLogout().setBounds(1180, 35, 90, 30);
			
			apf.getJpTitleSquareBottom().setBounds(0, 741, 1330, 30);
			
			setSelection("대시보드");
			switchOfState(apf);
			changeSubTitle();
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(true);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if  
		
		if(ae.getSource() == apf.getJbtnMenuAttend()) {
			
			apf.setResizable(true);
			apf.setSize(1030, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(800, 60);
			apf.getJpView().setSize(800, 642);
			
			apf.getJbtnLogout().setBounds(880, 35, 90, 30);
			
			apf.getJpTitleSquareBottom().setBounds(0, 741, 1330, 30);
			
			setSelection("근태관리");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuAttend());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(true);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == apf.getJbtnMenuAdd()) {
			
			apf.setResizable(true);
			apf.setSize(1170, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(940, 60);
			apf.getJpView().setSize(940, 642);
			
			apf.getJbtnLogout().setBounds(1020, 35, 90, 30);
			
			setSelection("사원추가");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuAdd());
			changeSubTitle();
			new EmpViewEvt(apf.getJpEmpViewPanel()).loadDeptAndPosition();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(true);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if  
		
		if(ae.getSource() == apf.getJbtnMenuList()) {
			
			apf.setResizable(true);
			apf.setSize(1170, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(940, 60);
			apf.getJpView().setSize(940, 642);
			
			apf.getJbtnLogout().setBounds(1020, 35, 90, 30);
			
			setSelection("사원목록");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuList());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(true);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
	
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == apf.getJbtnMenuPayroll()) {
			
			apf.setResizable(true);
			apf.setSize(1230, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(1000, 60);
			apf.getJpView().setSize(1000, 642);
			
			apf.getJbtnLogout().setBounds(1080, 35, 90, 30);
			
			
			setSelection("급여관리");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuPayroll());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(true);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == apf.getJbtnMenuSalary()) {
			
			apf.setResizable(true);
			apf.setSize(750, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(520, 60);
			apf.getJpView().setSize(520, 642);
//			apf.getJpView().setSize(520, 900);
			
			apf.getJbtnLogout().setBounds(600, 35, 90, 30);
			
			setSelection("연봉관리");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuSalary());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(true);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == apf.getJbtnMenuRank()) {
			
			apf.setResizable(true);
			apf.setSize(750, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(520, 60);
			apf.getJpView().setSize(520, 642);
			
			apf.getJbtnLogout().setBounds(600, 35, 90, 30);
			
			setSelection("직급관리");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuRank());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(true);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == apf.getJbtnMenuDept()) {
			
			apf.setResizable(true);
			apf.setSize(1170, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(940, 60);
			apf.getJpView().setSize(940, 642);
			
			apf.getJbtnLogout().setBounds(1020, 35, 90, 30);
			
			apf.getJpTitleSquareBottom().setBounds(0, 741, 1330, 30);
			
			setSelection("부서관리");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuDept());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(true);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == apf.getJbtnMenuDocument()) {
			
			apf.setResizable(true);
			apf.setSize(1230, 810);
			apf.setResizable(false);
			
			apf.getJpTitleSquare().setSize(1000, 60);
//			apf.getJpView().setSize(1100, 642);
//			apf.getJpView().setSize(1300, 742);
			apf.getJpView().setPreferredSize(new Dimension(1230, 600));
			
			apf.getJbtnLogout().setBounds(1080, 35, 90, 30);
			
			setSelection("문서관리");
			switchOfState(apf);
			showCurrentState(apf.getJbtnMenuDocument());
			changeSubTitle();
			
			apf.getJpDashBoardView().setVisible(false);//대시보드JPanel
			apf.getJpAttView().setVisible(false);//근태관리JPanel
			apf.getJpEmpViewPanel().setVisible(false);//사원추가JPanel
			apf.getJpEmpListViewPanel().setVisible(false);//사원목록JPanel
			apf.getJpPayrollViewFrame().setVisible(false);//급여관리JPanel
			apf.getJpSalaryView().setVisible(false);//연봉관리JPanel
			apf.getJpRankView().setVisible(false);//직급관리JPanel
			apf.getJpDeptView().setVisible(false);//부서관리JPanel
			apf.getJpAdminShareView().setVisible(true);
			
			System.out.println(getSelection().toString());
		}//end if
		
		
		if( ae.getSource() == apf.getJbtnLogout() ) {
			String msg=apf.getCurrentAdminID()+"님, 정말 로그아웃하시겠습니까?\n( 로그아웃 시, 관리자 로그인창으로 이동합니다. )";
			
			if(JOptionPane.showConfirmDialog(apf, msg, "관리자 로그아웃", JOptionPane.YES_NO_OPTION )==0){
				apf.dispose();
				new AdminLoginFrame();
			}//end if 
			
		}//end if
		
	}//actionPerformed
	
	public void changeSubTitle() {
		apf.getJlblTitle().setText(selection);
		apf.getJlblTitleShadow().setText(selection);
	}//changeSubTitle
	
	public void showCurrentState(JButton clickedButton) {
		clickedButton.setBackground(new Color(80,128,149));
		clickedButton.setForeground(Color.white);
		clickedButton.setFont(new Font("Dialog", Font.BOLD, 25));
	}//showCurrentState
	
	public void setDefaultState(JButton clickedButton) {
		clickedButton.setFont(new Font("Dialog", Font.BOLD, 18));
		clickedButton.setForeground(Color.black);
		clickedButton.setBackground(new Color(226,240,248));
	}//setDefaultState
	
	public void switchOfState(AdminParentFrame apf) {
		
		setDefaultState(apf.getJbtnMenuAdd());
		setDefaultState(apf.getJbtnMenuList());
		setDefaultState(apf.getJbtnMenuAttend());
		setDefaultState(apf.getJbtnMenuPayroll());
		setDefaultState(apf.getJbtnMenuSalary());
		setDefaultState(apf.getJbtnMenuRank());
		setDefaultState(apf.getJbtnMenuDept());
		setDefaultState(apf.getJbtnMenuDocument());
		
	}//switchOfState

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public AdminParentFrame getApf() {
		return apf;
	}

	public void setApf(AdminParentFrame apf) {
		this.apf = apf;
	}



}//class
