package kr.co.sist.user.vo;

/**
 * 
 */
public class UserAccountVO {
	
 	private String userId;
 	private String userPass;
 	
 	
 	public UserAccountVO() {
 		
 	}//UserAccountVO

	public UserAccountVO(String userId, String userPass) {
		this.userId = userId;
		this.userPass = userPass;
	}//UserAccountVO

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}



}//class
