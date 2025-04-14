package kr.co.sist.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.vo.CareerVO;

/**
 * 사원 경력 정보(Career) 관련 DB 접근 객체
 */
public class CareerDAO {

	private static CareerDAO cDAO;

	private CareerDAO() {}

	/**
	 * CareerDAO 싱글톤 인스턴스 반환
	 */
	public static CareerDAO getInstance() {
		if (cDAO == null) {
			cDAO = new CareerDAO();
		}
		return cDAO;
	}

	/**
	 * 경력 정보 추가
	 */
	public int insertCareer(CareerVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "INSERT INTO career (career_id, empno, company_name, hire_date, leave_date, former_position, former_dept, reason, input_date) "
			           + "VALUES (career_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
			pstmt = con.prepareStatement(sql, new String[] {"career_id"});
			pstmt.setInt(1, vo.getEmpno());
			pstmt.setString(2, vo.getCompany());
			pstmt.setDate(3, vo.getHireDate());
			pstmt.setDate(4, vo.getLeaveDate());
			pstmt.setString(5, vo.getExPosition());
			pstmt.setString(6, vo.getExDept());
			pstmt.setString(7, vo.getReason());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					vo.setCareer_id(rs.getInt(1)); // 생성된 PK 저장
				}
			}

			return result;
		} finally {
			db.closeDB(rs, pstmt, con);
		}
	}

	/**
	 * 경력 정보 수정 (career_id 기준)
	 */
	public int updateCareer(CareerVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE career ")
			   .append("SET company_name = ?, hire_date = ?, leave_date = ?, former_position = ?, former_dept = ?, reason = ? ")
			   .append("WHERE career_id = ?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getCompany());
			pstmt.setDate(2, vo.getHireDate());
			pstmt.setDate(3, vo.getLeaveDate());
			pstmt.setString(4, vo.getExPosition());
			pstmt.setString(5, vo.getExDept());
			pstmt.setString(6, vo.getReason());
			pstmt.setInt(7, vo.getCareer_id());

			return pstmt.executeUpdate();
		} finally {
			dbCon.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 특정 사원의 경력 목록 조회 (empno 기준)
	 */
	public List<CareerVO> selectByEmpno(int empno) throws SQLException {
		List<CareerVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DbConnection.getInstance().getConn();
			String sql = "SELECT career_id, company_name, hire_date, leave_date, former_position, former_dept, reason FROM career WHERE empno = ? ORDER BY leave_date DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CareerVO vo = new CareerVO();
				vo.setCareer_id(rs.getInt("career_id"));
				vo.setCompany(rs.getString("company_name"));
				vo.setHireDate(rs.getDate("hire_date"));
				vo.setLeaveDate(rs.getDate("leave_date"));
				vo.setExPosition(rs.getString("former_position"));
				vo.setExDept(rs.getString("former_dept"));
				vo.setReason(rs.getString("reason"));
				list.add(vo);
			}
		} finally {
			DbConnection.getInstance().closeDB(rs, pstmt, con);
		}

		return list;
	}

	/**
	 * 경력 정보 삭제 (career_id 기준)
	 */
	public boolean deleteCareer(int careerId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "DELETE FROM career WHERE career_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, careerId);

			int result = pstmt.executeUpdate();
			return result > 0;
		} finally {
			db.closeDB(null, pstmt, con);
		}
	}
	
}//class
