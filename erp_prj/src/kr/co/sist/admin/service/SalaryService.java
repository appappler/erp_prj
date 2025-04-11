package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.dao.RankDAO;
import kr.co.sist.admin.vo.RankVO;

/**
 * 
 */
public class SalaryService {
	public List<RankVO> searchSalary() throws SQLException {
		List<RankVO> list = new ArrayList<RankVO>();
		RankDAO rDAO = RankDAO.getInstance();
		
		list = rDAO.selectRank();
		
		return list;
	}
	
}//class
