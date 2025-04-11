package kr.co.sist.user.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.user.dao.UserLoginDAO;
import kr.co.sist.user.vo.UserAccountVO;

/**
 * 
 */
public class UserLoginService {
	
	public UserLoginService() {
		
	}//UserLoginService
	
	public boolean checkOfAccount(UserAccountVO inputVO) throws SQLException {
        boolean checkFlag=false;
        
        UserLoginDAO alDAO = UserLoginDAO.getInstance();
        alDAO.selectAllAccount();
        
        if(alDAO.authenticateOfInput(inputVO)) {
        	checkFlag=true;
        }//end if

        return checkFlag;
        
	}//checkOfAccount
	
	public boolean login(UserAccountVO inputVO) throws SQLException{
		
		UserLoginDAO alDAO = UserLoginDAO.getInstance();
		boolean loginFlag = alDAO.authenticateOfInput(inputVO);
		
		return loginFlag;
	}//login
	
	/**
	 * 
	 * @return 
	 */
	public List<UserAccountVO> searchAllAccount(){
		List<UserAccountVO> list = null;
		UserLoginDAO alDAO=UserLoginDAO.getInstance();
		
		try {
			list=alDAO.selectAllAccount();
		}catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return list;
		
	}//searchAllAccount


}//class
