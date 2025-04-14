package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.AppointmentVO;

/**
 * 인사 발령 정보(Appointment/Personnel) 관련 DB 접근 객체
 */
public class AppointmentDAO {

	private static AppointmentDAO aDAO;

	private AppointmentDAO() {}

	/**
	 * AppointmentDAO 싱글톤 인스턴스 반환
	 */
	public static AppointmentDAO getInstance() {
		if (aDAO == null) {
			aDAO = new AppointmentDAO();
		}
		return aDAO;
	}

	/**
	 * 인사 발령 정보 추가
	 */
	public boolean insertAppointment(AppointmentVO aVO) throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    PreparedStatement pstmt2 = null;
	    ResultSet rs = null;
	    DbConnection dbConn = DbConnection.getInstance();

	    try {
	        con = dbConn.getConn();
	        con.setAutoCommit(false); // 🔥 트랜잭션 처리 시작

	        // 🔹 1. personnel 테이블 INSERT
	        String sql = "INSERT INTO personnel (personnel_id, empno, deptno, position_id, appointment, appointment_date, input_date) "
	                   + "VALUES (appointment_seq.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
	        pstmt = con.prepareStatement(sql, new String[] { "personnel_id" });
	        pstmt.setInt(1, aVO.getEmpno());
	        pstmt.setObject(2, aVO.getDeptno());
	        pstmt.setObject(3, aVO.getPositionId());
	        pstmt.setString(4, aVO.getAppointment());
	        pstmt.setDate(5, aVO.getAppointmentDate());

	        int result = pstmt.executeUpdate();
	        if (result > 0) {
	            rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                aVO.setAppoint_id(rs.getInt(1));
	            }
	        }

	        // 🔹 2. employee 테이블 자동 반영
	        if ("입사".equals(aVO.getAppointment()) || "부서이동".equals(aVO.getAppointment()) || "승진".equals(aVO.getAppointment())) {
	            String sql2 = "UPDATE employee SET deptno = ?, position_id = ?, emp_status = '재직' WHERE empno = ?";
	            pstmt2 = con.prepareStatement(sql2);
	            pstmt2.setInt(1, aVO.getDeptno());
	            pstmt2.setInt(2, aVO.getPositionId());
	            pstmt2.setInt(3, aVO.getEmpno());
	            pstmt2.executeUpdate();
	        } else if ("퇴사".equals(aVO.getAppointment())) {
	            String sql2 = "UPDATE employee SET emp_status = '퇴사' WHERE empno = ?";
	            pstmt2 = con.prepareStatement(sql2);
	            pstmt2.setInt(1, aVO.getEmpno());
	            pstmt2.executeUpdate();
	        }

	        con.commit(); // 🔥 성공 시 커밋
	        return result > 0;

	    } catch (Exception e) {
	        if (con != null) con.rollback(); // 🔥 롤백
	        e.printStackTrace();
	        throw e;
	    } finally {
	        dbConn.closeDB(rs, pstmt, null);
	        if (pstmt2 != null) pstmt2.close();
	        if (con != null) con.setAutoCommit(true); // 원상복구
	        if (con != null) con.close();
	    }
	}



	/**
	 * 인사 발령 정보 수정 (personnel_id 기준)
	 */
	public int updateAppointment(AppointmentVO aVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();

		try {
			con = dbConn.getConn();
			String sql = "UPDATE personnel SET deptno = ?, position_id = ?, appointment = ?, appointment_date = ? WHERE personnel_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setObject(1, aVO.getDeptno());
			pstmt.setObject(2, aVO.getPositionId());
			pstmt.setString(3, aVO.getAppointment());
			pstmt.setDate(4, aVO.getAppointmentDate());
			pstmt.setInt(5, aVO.getAppoint_id());

			return pstmt.executeUpdate();
		} finally {
			dbConn.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 인사 발령 정보 삭제 (personnel_id 기준)
	 */
	public boolean deleteAppointment(int appointId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "DELETE FROM personnel WHERE personnel_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, appointId);

			int result = pstmt.executeUpdate();
			return result > 0;
		} finally {
			db.closeDB(null, pstmt, con);
		}
	}


	/**
	 * 특정 사원의 인사 발령 목록 조회 (조회 전용 정보 포함)
	 */
	public List<AppointmentVO> selectByEmpno(int empno) throws SQLException {
		List<AppointmentVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "SELECT personnel_id, appointment, appointment_date, d.deptname, p.position_name "
			           + "FROM personnel pe "
			           + "LEFT JOIN department d ON pe.deptno = d.deptno "
			           + "LEFT JOIN position p ON pe.position_id = p.position_id "
			           + "WHERE pe.empno = ? "
			           +"ORDER BY appointment_date DESC";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AppointmentVO vo = new AppointmentVO();
				vo.setAppoint_id(rs.getInt("personnel_id"));
				vo.setAppointment(rs.getString("appointment"));
				vo.setAppointmentDate(rs.getDate("appointment_date"));
				vo.setDeptName(rs.getString("deptname"));
				vo.setPositionName(rs.getString("position_name"));
				list.add(vo);
			}
		} finally {
			db.closeDB(rs, pstmt, con);
		}
		return list;
	}
	
}//class
