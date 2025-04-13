package kr.co.sist.dao;


public class AdminLoginDAO {
    private static AdminLoginDAO alDAO = new AdminLoginDAO();
    //ddd
    private AdminLoginDAO() {}

    public static AdminLoginDAO getInstance() {
        return alDAO;
    }

    public boolean selectLogin(kr.co.sist.admin.vo.AdminAccountVO aav) {
        // 실제 DB 연동 부분 (하드코딩 
        return "admin".equals(aav.getAdminId()) && "123".equals(aav.getAdminPass());
    }
}//class
