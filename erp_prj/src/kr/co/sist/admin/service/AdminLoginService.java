package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.admin.dao.AdminLoginDAO;
import kr.co.sist.admin.vo.AdminAccountVO;

/**
 * 
 */
public class AdminLoginService {
	
	public AdminLoginService() {
		
	}//AdminLoginService
	
	public boolean checkOfAccount(AdminAccountVO inputVO) throws SQLException {
        boolean checkFlag=false;
        
        AdminLoginDAO alDAO = AdminLoginDAO.getInstance();
        alDAO.selectAllAccount();
        
        if(alDAO.authenticateOfInput(inputVO)) {
        	checkFlag=true;
        }//end if

        return checkFlag;
        
	}//checkOfAccount
	
	public boolean login(AdminAccountVO inputVO) throws SQLException{
		
		AdminLoginDAO alDAO = AdminLoginDAO.getInstance();
		boolean loginFlag = alDAO.authenticateOfInput(inputVO);
		
		return loginFlag;
	}//login
	
	public List<AdminAccountVO> searchAllAccount(){
		List<AdminAccountVO> list = null;
		AdminLoginDAO alDAO=AdminLoginDAO.getInstance();
		
		try {
			list=alDAO.selectAllAccount();
		}catch(SQLException se) {
			se.printStackTrace();
		}//end catch
		
		return list;
		
	}//searchAllAccount

}//class
