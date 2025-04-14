package kr.co.sist.user.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.user.service.UserLoginService;
import kr.co.sist.user.view.UserLoginFrame;
import kr.co.sist.user.view.UserParentFrame;
import kr.co.sist.user.vo.UserAccountVO;

/**
 * 
 */
public class UserLoginEvent extends WindowAdapter implements ActionListener, KeyListener{
	
	private UserLoginFrame alf;
	private JButton jbtnLogin;
	private JButton jbtnExit;

	public UserLoginEvent(UserLoginFrame alf) {
		this.alf=alf;
	}//UserLoginEvent
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if( ae.getSource() == alf.getJbtnLogin() ) {
			System.out.println("로그인버튼을 클릭하셨습니다.");
			printCurrnetInput(alf.getJtfInputId(), alf.getJpfInputPass());
			
			String inputId = alf.getJtfInputId().getText().toString();
			String inputPass = new String(alf.getJpfInputPass().getPassword());
			UserAccountVO inputVO = new UserAccountVO(inputId, inputPass);
			
			try {
				loginEventToService(inputVO);
			} catch (SQLException se) {
				String ErrorOfDbConnection = "[ DB 접속 실패 ]\n( DB 사용자명/암호를 확인해주세요 )";
				JOptionPane.showMessageDialog(alf, ErrorOfDbConnection);
				se.printStackTrace();
			}//end catch
			
		}//end if
		
		if( ae.getSource() == alf.getJbtnExit() ) {
			String msg="프로그램을 종료하시겠습니까?";
			if(JOptionPane.showConfirmDialog(alf, msg, "프로그램 종료", JOptionPane.YES_NO_OPTION )==0){
				alf.dispose();
			}//end if 
		}//end if
		
	}//actionPerformed
	
	public void loginEventToService(UserAccountVO inputVO) throws SQLException{
		
		UserLoginService als = new UserLoginService();
		String msg="로그인 [ 실패 ]\n( 아이디 또는 비밀번호를 확인해주세요 )";

		if(als.checkOfAccount(inputVO)) {
			System.out.println("als.checkOfAccount(inputVO) : "+als.checkOfAccount(inputVO));
			msg="로그인 [ 성공 ]\n"+"접속 : "+inputVO.getUserId()+"님\n반갑습니다!";
			JOptionPane.showMessageDialog(alf, msg);
			alf.dispose();
			new UserParentFrame(inputVO);
		}else {
			JOptionPane.showMessageDialog(alf, msg);
		}//end if-else
		
	}//checkFromEvent

	public void printCurrnetInput(JTextField inputJTfId, JPasswordField inputJpfPass) {
		String currentId = inputJTfId.getText().toString();
		String currentPass = new String(inputJpfPass.getPassword());
		System.out.println("입력 Id  : "+currentId);
		System.out.println("입력 Pass: "+currentPass);
	}//printCurrnetInput
	
	@Override
	public void windowClosing(WindowEvent e) {
		alf.dispose();
	}//windowClosing
	
	public UserLoginFrame getAlf() {
		return alf;
	}

	public void setAlf(UserLoginFrame alf) {
		this.alf = alf;
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		if( ke.getKeyCode()==KeyEvent.VK_ENTER) {
			System.out.println("엔터키를 누르셨습니다.");
			printCurrnetInput(alf.getJtfInputId(), alf.getJpfInputPass());
			
			String inputId = alf.getJtfInputId().getText().toString();
			String inputPass = new String(alf.getJpfInputPass().getPassword());
			UserAccountVO inputVO = new UserAccountVO(inputId, inputPass);
			try {
				loginEventToService(inputVO);
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch 
			
		}//end if
	}//keyPressed

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}//class
