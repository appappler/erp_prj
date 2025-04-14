package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.vo.DeptFileVO;


public class ManagerDeptFileDAO {
	
	private static ManagerDeptFileDAO mdfDAO;
	
	private ManagerDeptFileDAO() {
		
	}
	
	public static ManagerDeptFileDAO getInStance() {
		if(mdfDAO == null) {
			mdfDAO = new ManagerDeptFileDAO();
		}
		
		return mdfDAO;
	}
	
	
	
	public List<DeptFileVO> selectManagerFile() throws SQLException {
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();
		DeptFileVO dfVO = null;

		try {
			con = dbCon.getConn();
			StringBuilder selectSB = new StringBuilder();
			selectSB
			.append("SELECT di.doc_id, dp.deptname, e.emp_name, di.upload_date, di.doc_name FROM ")
			.append(" docum_info di ")
			.append(" join employee e on di.empno = e.empno ")
			.append(" join department dp on e.deptno = dp.deptno");
	
			pstmt = con.prepareStatement(selectSB.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dfVO = new DeptFileVO();
				dfVO.setNum(rs.getInt("doc_id"));
	            dfVO.setEmpName(rs.getString("emp_name"));
	            dfVO.setDeptName(rs.getString("deptname"));
	            dfVO.setFileName(rs.getString("doc_name"));
	            dfVO.setInputDate(rs.getDate("upload_date"));
				list.add(dfVO);
			}
		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}

		return list;
	}// selectAllFile


	public List<DeptFileVO> searchManagerFile(String criteria) throws SQLException {
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbConn = DbConnection.getInstance();

		try {
			con = dbConn.getConn();
			StringBuilder searchSB = new StringBuilder();
			searchSB
			.append(" SELECT di.doc_id, e.emp_name, dp.deptname, di.doc_name, di.upload_date FROM ")
			.append(" docum_info di ")
			.append(" join employee e on di.empno = e.empno ")
			.append(" join department dp on e.deptno = dp.deptno")
			.append(" where di.doc_name like ? ");

			pstmt = con.prepareStatement(searchSB.toString());

			pstmt.setString(1, "%" + criteria + "%");

			rs = pstmt.executeQuery();
			DeptFileVO dfVO = null;

			while (rs.next()) {
				dfVO = new DeptFileVO();
				dfVO.setNum(rs.getInt("doc_id"));
				dfVO.setEmpName(rs.getString("emp_name"));
				dfVO.setDeptName(rs.getString("deptname"));
				dfVO.setFileName(rs.getString("doc_name"));
				dfVO.setInputDate(rs.getDate("upload_date"));
				list.add(dfVO);
			}
			

		} finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		return list;
	}
	
	public List<DeptFileVO> docuManagerFileSort(String options) throws SQLException{
		List<DeptFileVO> sortList = new ArrayList<DeptFileVO>();
		Connection con = null;
		ResultSet rs = null;
		DbConnection dbConn = DbConnection.getInstance();
		PreparedStatement pstmt = null;
		
		DeptFileVO dfVO = null;
		try {
			con = dbConn.getConn();
			
			String sortOption = options.equals("최신순") ? "DESC" : "ASC";
			
			
			StringBuilder sortSB = new StringBuilder();
			sortSB
			.append(" SELECT di.doc_id, e.emp_name, dp.deptname, di.doc_name, di.upload_date FROM ")
			.append(" docum_info di ")
			.append(" join employee e on di.empno = e.empno ")
			.append(" join department dp on e.deptno = dp.deptno")
			.append(" order by di.upload_date ")
			.append(sortOption);
			pstmt = con.prepareStatement(sortSB.toString());
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				dfVO = new DeptFileVO();
				dfVO.setNum(rs.getInt("doc_id"));
				dfVO.setEmpName(rs.getString("emp_name"));
				dfVO.setDeptName(rs.getString("deptname"));
				dfVO.setFileName(rs.getString("doc_name"));
				dfVO.setInputDate(rs.getDate("upload_date"));
				sortList.add(dfVO);
			}
			
		}finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		
		
		return sortList;
	}
}
