package kr.co.sist.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.co.sist.user.vo.DeptFileVO;


public class ShareDeptFileDAO {

	private static ShareDeptFileDAO sdfDAO;

	public ShareDeptFileDAO() {

	}

	public static ShareDeptFileDAO getInstance() {
		if (sdfDAO == null) {
			sdfDAO = new ShareDeptFileDAO();
		}
		return sdfDAO;
	}
	
	public void insertoneShareTable(DeptFileVO dfVO) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();
		
		try {
			con=dbConn.getConn();
			StringBuilder insertSB = new StringBuilder();
			insertSB
			.append("insert into docum_share(share_doc_id, doc_id, deptno, sender_deptno, share_date) ")
			.append(" values(share_seq.nextval, ?, ?, ?, sysdate)");
				
			pstmt = con.prepareStatement(insertSB.toString());
			
			pstmt.setInt(1, dfVO.getDocID());
			pstmt.setInt(2, dfVO.getDeptID());
			pstmt.setInt(3, dfVO.getSenderDeptID());
			
			
			
			pstmt.executeUpdate();
			
		}finally {
			dbConn.closeDB(null, pstmt, con);
			
		}
		
		
	}//insertShareTable
	
	public void deleteShareTable(int num) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();
		
		try {
			con = dbConn.getConn();
			StringBuilder deleteSB = new StringBuilder();
			deleteSB
			.append(" delete from docum_share where doc_id = ? ");
			
			pstmt = con.prepareStatement(deleteSB.toString());
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
		
		}finally {
			dbConn.closeDB(null, pstmt, con);
		}
	}
	
//	
//	public void insertAllShareTable(DeptFileVO dfVO) throws SQLException{
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		DbConnection dbConn = DbConnection.getInstance();
//		
//		try {
//			con = dbConn.getConn();
//			StringBuilder insertAllSB = new StringBuilder();
//			insertAllSB
//			.append()
//			.append()
//			.append()
//			.append()
//		}finally {
//			dbConn.closeDB(null, pstmt, con);
//		}
//		
//	}//insertALL
	
	public List<DeptFileVO> selectAllShareFile() throws SQLException{
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbConn = DbConnection.getInstance();
		
		try {
			con = dbConn.getConn();
			StringBuilder selectSB = new StringBuilder();
			selectSB
			.append("SELECT doc.doc_id, dp.deptname, doc.doc_name, ds.share_date ")
			.append(" FROM docum_share ds ")
			.append(" join docum_info doc on ds.doc_id = doc.doc_id")
			.append(" join employee e on doc.empno = e.empno ")
			.append(" join department dp on e.deptno = dp.deptno")
			.append(" where ds.deptno = 103 ");
			
			pstmt = con.prepareStatement(selectSB.toString());
			rs = pstmt.executeQuery();
			DeptFileVO dfVO = null;
			while(rs.next()) {
				dfVO = new DeptFileVO();
				dfVO.setDocID(rs.getInt("doc_id"));
				dfVO.setDeptName(rs.getString("deptname"));
				dfVO.setFileName(rs.getString("doc_name"));
				dfVO.setShareData(rs.getDate("share_date"));
				list.add(dfVO);
			}	
		}finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		return list;
	}//selectAllShareFile
	
	//찾기
	public List<DeptFileVO> searchShareFile(String search) throws SQLException{
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbConn = DbConnection.getInstance();
		
		try {
			con = dbConn.getConn();
			StringBuilder searchSB = new StringBuilder();
			searchSB
			.append("SELECT doc.doc_id, dp.deptname, doc.doc_name, ds.share_date ")
			.append(" FROM docum_share ds ")
			.append(" join docum_info doc on ds.doc_id = doc.doc_id")
			.append(" join employee e on doc.empno = e.empno ")
			.append(" join department dp on e.deptno = dp.deptno")
			.append(" where doc.doc_name like ? ");
			
			pstmt = con.prepareStatement(searchSB.toString());
			pstmt.setString(1, "%" + search + "%");
			
			rs = pstmt.executeQuery();
			DeptFileVO dfVO = null;
			while(rs.next()) {
				dfVO = new DeptFileVO();
				dfVO.setDocID(rs.getInt("doc_id"));
				dfVO.setDeptName(rs.getString("deptname"));
				dfVO.setFileName(rs.getString("doc_name"));
				dfVO.setShareData(rs.getDate("share_date"));
				list.add(dfVO);
			}
			
		}finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		
		return list;
	}//search
	
	//정렬
	public List<DeptFileVO> shareFileSort(String options) throws SQLException{
		List<DeptFileVO> shareSortList = new ArrayList<DeptFileVO>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();
		
		String sortOptions = options.equals("최신순") ? "DESC" : "ASC";
		
		try {
			con = dbConn.getConn();
			StringBuilder sortSB = new StringBuilder();
			sortSB
			.append(" SELECT doc.doc_id, dp.deptname, doc.doc_name, ds.share_date ")
			.append(" from docum_share ds ")
			.append(" join docum_info doc on ds.doc_id = doc.doc_id ")
			.append(" join employee e on doc.empno = e.empno ")
			.append(" join department dp on e.deptno = dp.deptno")
			.append(" order by ds.share_date ").append(sortOptions);
			
			pstmt = con.prepareStatement(sortSB.toString());
			
			rs=pstmt.executeQuery();
			
			DeptFileVO dfVO = null;
			
			while(rs.next()) {
				dfVO = new DeptFileVO();
				dfVO.setDocID(rs.getInt("doc_id"));
				dfVO.setDeptName(rs.getString("deptname"));
				dfVO.setFileName(rs.getString("doc_name"));
				dfVO.setShareData(rs.getDate("share_date"));
				shareSortList.add(dfVO);
			}
			
		} finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		
		return shareSortList;
	}//sort
	
	//문서번호로 문서 select
	public List<DeptFileVO> getSharedDeptsByDocId(int docId) throws SQLException {
	    List<DeptFileVO> list = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();

	    try {
	        con = dbConn.getConn();
	        StringBuilder sb = new StringBuilder();
	        sb.append("SELECT d.deptname, ds.deptno, ds.doc_id ")
	          .append("FROM docum_share ds ")
	          .append("JOIN department d ON ds.deptno = d.deptno ")
	          .append("WHERE ds.doc_id = ?");

	        pstmt = con.prepareStatement(sb.toString());
	        pstmt.setInt(1, docId);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            DeptFileVO vo = new DeptFileVO();
	            vo.setDeptName(rs.getString("deptname"));
	            vo.setDeptID(rs.getInt("deptno"));
	            vo.setDocID(rs.getInt("doc_id"));
	            list.add(vo);
	        }
	    } finally {
	        dbConn.closeDB(rs, pstmt, con);
	    }
	    return list;
	}//getSharedDeptsBtDOcid
	
	//삭제 공유리스트
	public boolean deleteShareByDocAndDept(DeptFileVO vo) throws SQLException{
	
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();
	    try {
	    	con = dbConn.getConn();
	    	String sql = "DELETE FROM docum_share WHERE doc_id = ? AND deptno = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, vo.getDocID());
	        pstmt.setInt(2, vo.getDeptID());
	        int result = pstmt.executeUpdate();
	        return result > 0;
	    }finally {
	    	dbConn.closeDB(null, pstmt, con);
	    }
	}//deleteshareby
	
	
	public Set<Integer> getAllSharedDocIds() {
	    Set<Integer> sharedDocIds = new HashSet<Integer>();
	    String sql = "SELECT DISTINCT doc_id FROM docum_share";
	    
	    try (Connection con = DbConnection.getInstance().getConn();
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            sharedDocIds.add(rs.getInt("doc_id"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return sharedDocIds;
	}
	
	
	//db에 저장된 모든 부서들
	public List<DeptFileVO> getAllDepartments(int senderDept) throws SQLException {
	    List<DeptFileVO> list = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();
	    
	    try {
	        con = dbConn.getConn();
	        String sql = "SELECT deptno, deptname FROM department where deptno != ? ORDER BY deptno";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, senderDept);
	        rs = pstmt.executeQuery();
	       
	        while (rs.next()) {
	            DeptFileVO vo = new DeptFileVO();
	            vo.setDeptID(rs.getInt("deptno"));
	            vo.setDeptName(rs.getString("deptname"));
	            list.add(vo);
	        }
	    } finally {
	        dbConn.closeDB(rs, pstmt, con);
	    }

	    return list;
	}
	
	
}
