package kr.co.sist.user.evt;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import kr.co.sist.user.view.UserLoginFrame;
import kr.co.sist.user.view.UserParentFrame;

/**
 * 
 */
public class UserMenuEvent extends WindowAdapter implements ActionListener{
	
	public String selection;
	private UserParentFrame upf;
	
	public UserMenuEvent(UserParentFrame upf) {
		
		this.upf = upf;
		String selection = "사원 로그인 첫화면"; 
		this.selection=selection;
		
		setSelection("나의 근태");
		switchOfState(upf);
		showCurrentState(upf.getJbtnMenuMyAttend());
		changeSubTitle();
		
		upf.setResizable(true);
		upf.setSize(1530, 800);
		upf.setResizable(false);
		
		upf.getJpTitleSquare().setSize(1100, 60);
		upf.getJpView().setSize(1100, 532);
		
		upf.getJbtnLogout().setBounds(1180, 35, 90, 30);
		
		upf.getJpTitleSquareBottom().setBounds(0, 731, 1330, 30);
		
		upf.getJlblCurrentUserIDWellcom().setBounds(21, 715, 300, 60);
		upf.getJlblCurrentUserIDWellcomShadow().setBounds(22, 716, 300, 60);
		
		upf.getJlblCurrentUserID().setBounds(60, 715, 300, 60);
		upf.getJlblCurrentUserIDShadow().setBounds(61, 716, 300, 60);
		
		upf.getJpAttView().setVisible(true);//바로 근태 보이게~
		upf.getJpEmpDetailView().setVisible(false);
		upf.getJpPayrollView().setVisible(false);
		upf.getJpDocumnetShareView().setVisible(false);
		
//		System.out.println(getSelection().toString());
		System.out.println("사원프로그램의 로그인 성공 후 첫 화면");
	}//UserMenuEvent
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource() == upf.getJbtnMenuHome()) {
			
			/*
			upf.setResizable(true);
			upf.setSize(1330, 800);
			upf.setResizable(false);
			
			upf.getJpTitleSquare().setSize(1100, 60);
			upf.getJpView().setSize(1100, 642);
			
			upf.getJbtnLogout().setBounds(1180, 35, 90, 30);
			
			upf.getJpTitleSquareBottom().setBounds(0, 731, 1330, 30);
			
			setSelection("사원 메뉴");
			switchOfState(upf);
			changeSubTitle();
			changeSubTitle();
			
			upf.getJpAttView().setVisible(false);
			upf.getJpEmpDetailView().setVisible(false);
			upf.getJpPayrollView().setVisible(false);
			upf.getJpDocumnetShareView().setVisible(false);
			*/
			
//			System.out.println(getSelection().toString());
			System.out.println("사원 메뉴 클릭");
		}//end if  
		
		
		if(ae.getSource() == upf.getJbtnMenuMyAttend()) {
			
			upf.setResizable(true);
			upf.setSize(1330, 800);
			upf.setResizable(false);
			
			upf.getJpTitleSquare().setSize(1100, 60);
			upf.getJpView().setSize(1100, 642);
			
			upf.getJbtnLogout().setBounds(1180, 35, 90, 30);
			
			upf.getJpTitleSquareBottom().setBounds(0, 731, 1330, 30);
			
			setSelection("나의 근태");
			switchOfState(upf);
			showCurrentState(upf.getJbtnMenuMyAttend());
			changeSubTitle();
			
			upf.getJpAttView().setVisible(true);
			upf.getJpEmpDetailView().setVisible(false);
			upf.getJpPayrollView().setVisible(false);
			upf.getJpDocumnetShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == upf.getJbtnMenuMyInfo()) {
			
			upf.setResizable(true);
			upf.setSize(1330, 800);
			upf.setResizable(false);
			
			upf.getJpTitleSquare().setSize(1100, 60);
			upf.getJpView().setSize(1100, 642);
			
			upf.getJbtnLogout().setBounds(1180, 35, 90, 30);
			
			upf.getJpTitleSquareBottom().setBounds(0, 731, 1330, 30);
			
			
			setSelection("나의 정보");
			switchOfState(upf);
			showCurrentState(upf.getJbtnMenuMyInfo());
			changeSubTitle();
			
			upf.getJpAttView().setVisible(false);
			upf.getJpEmpDetailView().setVisible(true);
			upf.getJpPayrollView().setVisible(false);
			upf.getJpDocumnetShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == upf.getJbtnMenuMyPay()) {
			
			upf.setResizable(true);
			upf.setSize(1330, 800);
			upf.setResizable(false);
			
			upf.getJpTitleSquare().setSize(1100, 60);
			upf.getJpView().setSize(1100, 642);
			
			upf.getJbtnLogout().setBounds(1180, 35, 90, 30);
			
			upf.getJpTitleSquareBottom().setBounds(0, 731, 1330, 30);
			
			setSelection("나의 급여");
			switchOfState(upf);
			showCurrentState(upf.getJbtnMenuMyPay());
			changeSubTitle();
			
			upf.getJpAttView().setVisible(false);
			upf.getJpEmpDetailView().setVisible(false);
			upf.getJpPayrollView().setVisible(true);
			upf.getJpDocumnetShareView().setVisible(false);
			
			System.out.println(getSelection().toString());
		}//end if
		
		if(ae.getSource() == upf.getJbtnMenuMyShare()) {
			
			upf.setResizable(true);
			upf.setSize(1330, 800);
			upf.setResizable(false);
			
			upf.getJpTitleSquare().setSize(1100, 60);
			upf.getJpView().setSize(1100, 600);
			
			upf.getJbtnLogout().setBounds(1180, 35, 90, 30);
			
			upf.getJpTitleSquareBottom().setBounds(0, 731, 1400, 30);
			
			
			setSelection("나의 공유");
			switchOfState(upf);
			showCurrentState(upf.getJbtnMenuMyShare());
			changeSubTitle();
			
			upf.getJpAttView().setVisible(false);
			upf.getJpEmpDetailView().setVisible(false);
			upf.getJpPayrollView().setVisible(false);
			upf.getJpDocumnetShareView().setVisible(true);
			
			System.out.println(getSelection().toString());
		}//end if
		
		
		if( ae.getSource() == upf.getJbtnLogout() ) {
			String msg=upf.getCurrentUserID()+"님, 정말 로그아웃하시겠습니까?\n( 로그아웃 시, 사원 로그인창으로 이동합니다. )";
			
			if(JOptionPane.showConfirmDialog(upf, msg, "로그아웃", JOptionPane.YES_NO_OPTION )==0){
				upf.dispose();
				new UserLoginFrame();
			}//end if 
			
		}//end if
		
	}//actionPerformed
	
	public void changeSubTitle() {
		upf.getJlblTitle().setText(selection);
		upf.getJlblTitleShadow().setText(selection);
	}//changeSubTitle
	
	public void showCurrentState(JButton clickedButton) {
		clickedButton.setBackground(new Color(110,142,154));
		clickedButton.setForeground(Color.white);
		clickedButton.setFont(new Font("Dialog", Font.BOLD, 24));
	}//showCurrentState
	
	public void setDefaultState(JButton clickedButton) {
		clickedButton.setFont(new Font("Dialog", Font.BOLD, 24));
		clickedButton.setForeground(Color.black);
		clickedButton.setBackground(new Color(205,234,248));
	}//setDefaultState
	
	public void switchOfState(UserParentFrame upf) {
		
		setDefaultState(upf.getJbtnMenuMyAttend());
		setDefaultState(upf.getJbtnMenuMyInfo());
		setDefaultState(upf.getJbtnMenuMyPay());
		setDefaultState(upf.getJbtnMenuMyShare());
		
	}//switchOfState
	
	public String getSelection() {
		return selection;
	}
	
	public void setSelection(String selection) {
		this.selection = selection;
	}
	
}//class
