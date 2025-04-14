package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.TrainingVO;

/**
 * 사원 교육 이력 정보(Training) 관련 DB 접근 객체
 */
public class TrainingDAO {

	private static TrainingDAO tDAO;

	private TrainingDAO() {}

	/**
	 * TrainingDAO 싱글톤 인스턴스 반환
	 */
	public static TrainingDAO getInstance() {
		if (tDAO == null) {
			tDAO = new TrainingDAO();
		}
		return tDAO;
	}

	/**
	 * 교육 이력 추가
	 */
	public boolean insertTraining(TrainingVO vo) throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();

	    try {
	        con = dbConn.getConn();
	        String sql = "INSERT INTO training (training_id, empno, institution, training_name, start_date, end_date, col, input_date) "
	                   + "VALUES (training_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE)";
	        pstmt = con.prepareStatement(sql, new String[] { "training_id" }); // 🔥 PK 컬럼 명시

	        pstmt.setInt(1, vo.getEmpno());
	        pstmt.setString(2, vo.getInstitution());
	        pstmt.setString(3, vo.getTrainingName());
	        pstmt.setDate(4, vo.getStartDate());
	        pstmt.setDate(5, vo.getEndDate());
	        pstmt.setString(6, vo.getComplete());

	        int result = pstmt.executeUpdate();

	        if (result > 0) {
	            rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                vo.setTraining_id(rs.getInt(1)); // 🔥 VO에 id 세팅
	            }
	        }

	        return result > 0;

	    } finally {
	        dbConn.closeDB(rs, pstmt, con);
	    }
	}



	/**
	 * 교육 이력 수정 (training_id 기준)
	 */
	public int updateTraining(TrainingVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();

		try {
			con = dbConn.getConn();
			String sql = "UPDATE training SET institution = ?, training_name = ?, start_date = ?, end_date = ?, col = ? WHERE training_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getInstitution());
			pstmt.setString(2, vo.getTrainingName());
			pstmt.setDate(3, vo.getStartDate());
			pstmt.setDate(4, vo.getEndDate());
			pstmt.setString(5, vo.getComplete());
			pstmt.setInt(6, vo.getTraining_id());

			return pstmt.executeUpdate();
		} finally {
			dbConn.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 교육 이력 삭제 (training_id 기준)
	 */
	public boolean deleteTraining(int trainingId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "DELETE FROM training WHERE training_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, trainingId);

			return pstmt.executeUpdate() > 0;
		} finally {
			db.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 특정 사원의 교육 이력 조회 (empno 기준)
	 */
	public List<TrainingVO> selectByEmpno(int empno) throws SQLException {
		List<TrainingVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbConn = DbConnection.getInstance();

		try {
			con = dbConn.getConn();
			String sql = "SELECT training_id, institution, training_name, start_date, end_date, col FROM training WHERE empno = ? ORDER BY start_date DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				TrainingVO vo = new TrainingVO();
				vo.setTraining_id(rs.getInt("training_id"));
				vo.setInstitution(rs.getString("institution"));
				vo.setTrainingName(rs.getString("training_name"));
				vo.setStartDate(rs.getDate("start_date"));
				vo.setEndDate(rs.getDate("end_date"));
				vo.setComplete(rs.getString("col"));
				list.add(vo);
			}
		} finally {
			dbConn.closeDB(rs, pstmt, con);
		}
		return list;
	}
	
}//class
