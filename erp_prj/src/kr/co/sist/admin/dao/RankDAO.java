package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.RankVO;

/**
 * 
 */
public class RankDAO {
	private static RankDAO rankDAO;

	private RankDAO() {
	}

	public static RankDAO getInstance() {
		if (rankDAO == null) {
			rankDAO = new RankDAO();
		}
		return rankDAO;
	}

	public List<RankVO> selectRank() throws SQLException {
		List<RankVO> list = new ArrayList<RankVO>();

		DbConnection dbConn = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		try {
			con = dbConn.getConn();
			StringBuilder selectRank = new StringBuilder();
			selectRank.append("select position_name, salary from position group by position_name, salary order by salary");

			pstmt = con.prepareStatement(selectRank.toString());
 
			rs = pstmt.executeQuery();

			RankVO rVO = null;
			while (rs.next()) {
				rVO = new RankVO(rs.getString("position_name"), rs.getInt("salary"), 0);
				list.add(rVO);
			}
		} finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		
		return list;
	}

	public List<RankVO> selectSalary() throws SQLException {
		List<RankVO> list = new ArrayList<RankVO>();
		
		DbConnection dbConn = DbConnection.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConn.getConn();
			StringBuilder selectSalary = new StringBuilder();
			selectSalary.append("select position_name, count(position_name) from position group by position_name");
			pstmt = con.prepareStatement(selectSalary.toString());
			
			rs = pstmt.executeQuery();
			RankVO rVO = null;
			while (rs.next()) {
				rVO = new RankVO(rs.getString("position_name"),0,rs.getInt("count(position_name)"));
				list.add(rVO);
			}
		} finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		
		return list;
	}
	
	public static void main(String[] args) {
//		try {
//			List<RankVO> list = new RankDAO().selectRank();
//			for (RankVO rVO : list) {
//				System.out.println(rVO );
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		List<RankVO> list;
		try {
			list = new RankDAO().selectSalary();
			for (RankVO rVO : list) {
				System.out.println(rVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}//class
