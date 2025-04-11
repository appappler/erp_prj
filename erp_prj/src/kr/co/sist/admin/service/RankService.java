package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.admin.dao.RankDAO;
import kr.co.sist.admin.vo.RankVO;

/**
 * 
 */
public class RankService {
	public List<RankVO> searchRank() throws SQLException {
		RankDAO rDAO = RankDAO.getInstance();
		
		return rDAO.selectSalary();
	}
	
}//class
