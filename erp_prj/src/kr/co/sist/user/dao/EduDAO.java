package kr.co.sist.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.vo.EduVO;

/**
 * 교육 이력(Education) 관련 DB 접근 객체
 */
public class EduDAO {

	private static EduDAO eduDAO;

	private EduDAO() {}

	/**
	 * EduDAO 싱글톤 인스턴스 반환
	 */
	public static EduDAO getInstance() {
		if (eduDAO == null) {
			eduDAO = new EduDAO();
		}
		return eduDAO;
	}

	/**
	 * 교육 이력 추가
	 */
	public void insertEdu(EduVO eduVO) throws SQLException {
	    Connection con = null;
	    CallableStatement cstmt = null;
	    DbConnection dbConn = DbConnection.getInstance();

	    try {
	        con = dbConn.getConn();

	        String sql = "BEGIN "
	                   + "INSERT INTO education(edu_id, empno, school_name, major, degree, admission, graduation, graduation_name, input_date) "
	                   + "VALUES(education_seq.nextval, ?, ?, ?, ?, ?, ?, ?, sysdate) "
	                   + "RETURNING edu_id INTO ?; "
	                   + "END;";

	        cstmt = con.prepareCall(sql);

	        cstmt.setInt(1, eduVO.getEmpno());
	        cstmt.setString(2, eduVO.getSchoolName());
	        cstmt.setString(3, eduVO.getMajor());
	        cstmt.setString(4, eduVO.getDegree());
	        cstmt.setDate(5, eduVO.getAdmission());
	        cstmt.setDate(6, eduVO.getGraduation());
	        cstmt.setString(7, eduVO.getGradStatus());
	        cstmt.registerOutParameter(8, java.sql.Types.INTEGER); // OUT 파라미터 등록

	        cstmt.execute();
	        int newId = cstmt.getInt(8);
	        eduVO.setEdu_id(newId); // VO에 저장

	    } finally {
	        dbConn.closeDB(null, cstmt, con);
	    }
	}


	/**
	 * 교육 이력 수정 (empno와 edu_id를 기준으로 수정)
	 */
	public boolean updateEducation(EduVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "UPDATE education SET admission = ?, graduation = ?, school_name = ?, major = ?, degree = ?, graduation_name = ? "
			           + "WHERE empno = ? AND edu_id = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, vo.getAdmission());
			pstmt.setDate(2, vo.getGraduation());
			pstmt.setString(3, vo.getSchoolName());
			pstmt.setString(4, vo.getMajor());
			pstmt.setString(5, vo.getDegree());
			pstmt.setString(6, vo.getGradStatus());
			pstmt.setInt(7, vo.getEmpno());
			pstmt.setInt(8, vo.getEdu_id());

			return pstmt.executeUpdate() > 0;
		} finally {
			db.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 교육 이력 삭제 (edu_id 기준)
	 */
	public boolean deleteEducation(int eduId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "DELETE FROM education WHERE edu_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, eduId);

			int result = pstmt.executeUpdate();
			return result > 0;
		} finally {
			db.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 특정 사원의 교육 이력 조회 (empno 기준)
	 */
	public List<EduVO> selectByEmpno(int empno) throws SQLException {
		List<EduVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbConn = DbConnection.getInstance();
		try {
			con = dbConn.getConn();
			String sql = "SELECT edu_id, admission, graduation, school_name, major, degree, graduation_name FROM education WHERE empno = ? ORDER BY graduation DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empno);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				EduVO vo = new EduVO();
				vo.setEdu_id(rs.getInt("edu_id"));
				vo.setAdmission(rs.getDate("admission"));
				vo.setGraduation(rs.getDate("graduation"));
				vo.setSchoolName(rs.getString("school_name"));
				vo.setMajor(rs.getString("major"));
				vo.setDegree(rs.getString("degree"));
				vo.setGradStatus(rs.getString("graduation_name"));
				list.add(vo);
			}
		} finally {
			dbConn.closeDB(rs, pstmt, con);
		}

		return list;
	}
	
}//class
