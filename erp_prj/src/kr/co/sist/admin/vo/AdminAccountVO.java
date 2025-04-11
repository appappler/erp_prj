package kr.co.sist.admin.vo;

/**
 * 
 */
public class AdminAccountVO {
	
 	private String adminId;
 	private String adminPass;
 	
 	public AdminAccountVO() {
 		
 	}//AdminLoginVO

	public AdminAccountVO(String adminId, String adminPass) {
		this.adminId = adminId;
		this.adminPass = adminPass;
	}//AdminLoginVO

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	@Override
	public String toString() {
		return "AdminLoginVO [adminId=" + adminId + ", adminPass=" + adminPass + "]";
	}
	
}//class
