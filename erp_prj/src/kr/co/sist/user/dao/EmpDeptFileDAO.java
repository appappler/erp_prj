package kr.co.sist.user.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.vo.DeptFileVO;

public class EmpDeptFileDAO {

	private static EmpDeptFileDAO efDAO;

	private EmpDeptFileDAO() {

	}// empdeptfileDAO

	public static EmpDeptFileDAO getInstance() {
		if (efDAO == null) {
			efDAO = new EmpDeptFileDAO();
		}
		return efDAO;
	}

	public void insertDocuTable(DeptFileVO dfVO) throws SQLException {
		// 1. 드라이버 로딩
		// 2. 커넥션 얻기
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3. 쿼리문 넣어서 쿼리문 생성객체 얻기
			StringBuilder insertDocument = new StringBuilder();
			insertDocument.append(
					"insert into docum_info(DOC_ID, empno, DOC_NAME, UPLOAD_DATE, file_data) values( document_seq.nextval,?,?, sysdate, ?)");

			pstmt = con.prepareStatement(insertDocument.toString());
			// 4. 바인드 변수 할당
			pstmt.setInt(1, dfVO.getEmpId());
			pstmt.setString(2, dfVO.getFileName());
			pstmt.setBytes(3, dfVO.getFileData());

			pstmt.executeUpdate();

		} finally {
			dbCon.closeDB(null, pstmt, con);

		}
	}// insertDocuTable

	public List<DeptFileVO> selectAllFile() throws SQLException {
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
			.append("SELECT di.doc_id, e.emp_name, dp.deptname, di.doc_name, di.upload_date FROM ")
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

	public void deleteFile(int num) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder deleteSB = new StringBuilder();
			deleteSB.append("delete from docum_info ").append("where doc_id = ? ");
			pstmt = con.prepareStatement(deleteSB.toString());

			pstmt.setInt(1, num);

			pstmt.executeUpdate();
		} finally {
			dbCon.closeDB(null, pstmt, con);

		}

	}// deleteFile

	public List<DeptFileVO> searchFile(String criteria) throws SQLException {
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
	
	public List<DeptFileVO> docuFileSort(String options) throws SQLException{
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
	//다운로드
	public byte[] getFileBlob(int docId) throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();
	    byte[] fileData = null;

	    try {
	        con = dbConn.getConn();
	        String sql = "SELECT file_data FROM docum_info WHERE doc_id = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, docId);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            Blob blob = rs.getBlob("file_data");
	            fileData = blob.getBytes(1, (int) blob.length());
	        }
	    } finally {
	        dbConn.closeDB(rs, pstmt, con);
	    }

	    return fileData;
	}//getFileBlob
	
	public boolean isEmpExists(int empId) throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();
	    boolean exists = false;

	    
	    
	    
	    try {
	        con = dbConn.getConn();
	        String sql = "SELECT COUNT(*) FROM employee WHERE empno = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, empId);
	        rs = pstmt.executeQuery();

	        if (rs.next() && rs.getInt(1) > 0) {
	            exists = true;
	        }
	    } finally {
	        dbConn.closeDB(rs, pstmt, con);
	    }

	    return exists;
	}//사원번호 유효성 검증
	
	public int findDeptIdByName(String deptName) throws SQLException{
	    int deptId = 0;
	    
	    Connection con = null; 
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();
	   		try{
	   			
	   		con = dbConn.getConn();
	   		String sql = "SELECT deptno FROM department WHERE deptname = ?";
	   		pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, deptName);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            deptId = rs.getInt("deptno");
	        }
	        
	    } finally {
	    	dbConn.closeDB(rs, pstmt, con);
	    }
	    
	    return deptId;
	}
	
	
}
