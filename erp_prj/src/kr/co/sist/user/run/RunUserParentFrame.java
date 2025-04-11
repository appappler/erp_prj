package kr.co.sist.user.run;

import kr.co.sist.user.view.UserParentFrame;
import kr.co.sist.user.vo.UserAccountVO;

/**
 * 
 */
public class RunUserParentFrame { 
	
	public static void main(String[] args) {
		new UserParentFrame( new UserAccountVO("테스트용 사번", "테스트용 비밀번호") );
	}//main
	
}//class
