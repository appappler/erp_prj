package kr.co.sist.user.service;

import java.sql.SQLException;
import java.util.List;
import kr.co.sist.user.dao.UserLoginDAO;
import kr.co.sist.user.vo.UserAccountVO;

public class UserLoginService {
    
    public UserLoginService() {
    }
    
    public boolean checkOfAccount(UserAccountVO inputVO) throws SQLException {
        boolean checkFlag = false;
        UserLoginDAO alDAO = UserLoginDAO.getInstance();
        alDAO.selectAllAccount();
        if (alDAO.authenticateOfInput(inputVO)) {
            checkFlag = true;
        }
        return checkFlag;
    }
    
    public boolean login(UserAccountVO inputVO) throws SQLException {
        UserLoginDAO alDAO = UserLoginDAO.getInstance();
        boolean loginFlag = alDAO.authenticateOfInput(inputVO);
        return loginFlag;
    }
    
    public List<UserAccountVO> searchAllAccount() {
        List<UserAccountVO> list = null;
        UserLoginDAO alDAO = UserLoginDAO.getInstance();
        try {
            list = alDAO.selectAllAccount();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return list;
    }
}
