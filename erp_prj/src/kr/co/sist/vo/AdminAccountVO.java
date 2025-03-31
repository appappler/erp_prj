package kr.co.sist.vo;

public class AdminAccountVO {
    public String adminId;
    public String adminPass;

    public AdminAccountVO(String adminId, String adminPass) {
        this.adminId = adminId;
        this.adminPass = adminPass;
    }

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
}//class
