package kr.co.sist.admin.run;




import kr.co.sist.admin.view.AdminParentFrame;
import kr.co.sist.admin.vo.AdminAccountVO;

/**
 * 
 * 
 * 관리자 메인 프레임의 실행 클래스<br>
 * 테스트용 계정으로 관리자 로그인을 생략할 수 있다.<br>
 */

public class RunAdminParentFrame { 
	
	public static void main(String[] args) {
		new AdminParentFrame( new AdminAccountVO("테스트용 아이디", "테스트용 비밀번호") );
	}//main
	
}//class
