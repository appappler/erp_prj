package kr.co.sist.user.vo;

public class UserAccountVO {
    
    private String userId;    // 사원번호
    private String userPass;  // 비밀번호
    private String empName;   // 사원이름

    public UserAccountVO() {
    }
    
    public UserAccountVO(String userId, String userPass) {
        this.userId = userId;
        this.userPass = userPass;
    }
    
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
    
    public String getEmpName() {
        return empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
