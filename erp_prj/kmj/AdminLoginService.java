package kr.co.sist.service;

import kr.co.sist.dao.AdminLoginDAO;
import kr.co.sist.vo.AdminAccountVO;

public class AdminLoginService {
    public boolean Login(AdminAccountVO aav) {
        AdminLoginDAO dao = AdminLoginDAO.getInstance();
        return dao.selectLogin(aav);
    }
}//class
