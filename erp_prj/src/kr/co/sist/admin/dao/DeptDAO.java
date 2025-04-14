package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.DeptVO;

/**
 * DB 접근 담당, CRUD 실행 장소 => SQL을 사용해 DB에 실제로 CRUD 작업 수행
 */
public class DeptDAO {

	private static DeptDAO dDAO;

	private DeptDAO() {

	}// DeptDAO

	public static DeptDAO getInstance() {
		if (dDAO == null) {
			dDAO = new DeptDAO();
		}
		return dDAO;
	}// getInstance

	public void insertDept(DeptVO dVO) throws SQLException {// 부서 등록
		// 1.드라이버 로딩
		// 2.커넥션 얻기
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();

		try {
			con = dbConn.getConn();
			// 3.쿼리문을 넣어서 쿼리문 생성객체 얻기
			StringBuilder insertDept = new StringBuilder();
			insertDept.append("	insert into department(deptNo,deptName,contact,loc,note,bonus_rate)")
					.append("	values (?,?,?,?,?,?)");

			pstmt = con.prepareStatement(insertDept.toString());
			// 4.바인드 변수에 값 할당
			pstmt.setInt(1, dVO.getDeptNum());
			pstmt.setString(2, dVO.getDeptName());
			pstmt.setString(3, dVO.getTel());
			pstmt.setString(4, dVO.getLoc());
			pstmt.setString(5, dVO.getNote());
			pstmt.setInt(6, dVO.getBonus_rate());
			// 5.쿼리문 수행 후 결과 얻기
			pstmt.executeUpdate();
		} finally {
			// 6.연결 끊기
			dbConn.closeDB(null, pstmt, con);
		} // end finally

	}// insertDept

	public int updateDept(DeptVO dVO) throws SQLException {// 다이얼로그 수정

		int rowCnt = 0;

		// 1.드라이버 로딩
		// 2.커넥션 얻기
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.쿼리문 생성 객체 얻기
			StringBuilder updateDept = new StringBuilder();
			updateDept.append("	update	department ")
					.append("	set	deptNo=?,deptName=?,contact=?,loc=?,note=?,bonus_rate=?")
					.append("	where	deptNo=?	");

			pstmt = con.prepareStatement(updateDept.toString());
			// 4.바인드 변수에 값 설정
			pstmt.setInt(1, dVO.getDeptNum());
			pstmt.setString(2, dVO.getDeptName());
			pstmt.setString(3, dVO.getTel());
			pstmt.setString(4, dVO.getLoc());
			pstmt.setString(5, dVO.getNote());
			pstmt.setInt(6, dVO.getBonus_rate());
			pstmt.setInt(7, dVO.getDeptNum());
			// 5.쿼리문 수행 후 결과 얻기
			rowCnt = pstmt.executeUpdate();
		} finally {
			// 6.연결 끊기
			dbCon.closeDB(null, pstmt, con);
		} // end finally

		return rowCnt;
	}// updateDept

	public int deleteDept(int num) throws SQLException {// 부서 삭제
		int rowCnt = 0;

		// 1.드라이버 로딩
		// 2.커넥션얻기
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.쿼리문 생성객체 얻기
			StringBuilder deleteDept = new StringBuilder();
			deleteDept.append("	delete from department	").append("	where deptNo=?			");

			pstmt = con.prepareStatement(deleteDept.toString());
			// 4.바인드 변수에 값 설정
			pstmt.setInt(1, num);
			// 5.쿼리문 수행 후 결과 얻기
			rowCnt = pstmt.executeUpdate();

		} finally {
			// 6.연결 끊기
			dbCon.closeDB(null, pstmt, con);
		} // end finally

		return rowCnt;
	}// deleteDept

	public List<DeptVO> selectAllDept() throws SQLException {
		List<DeptVO> list = new ArrayList<DeptVO>();

		// 1.드라이버 로딩
		// 2.커넥션얻기
		Connection con = null;// DB연결 담당 객체
		PreparedStatement pstmt = null;// SQL문 실행 객체
		ResultSet rs = null;// SQL 실행 결과 저장 객체

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.쿼리문 생성객체 얻기
			StringBuilder selectAllDept = new StringBuilder();
			selectAllDept.append("	select deptNo,deptName,contact,loc,note,bonus_rate	").append("	from department");

			pstmt = con.prepareStatement(selectAllDept.toString());
			// 4.바인드 변수에 값 설정
			// 5.쿼리문 수행 후 결과 얻기
			rs = pstmt.executeQuery();

			DeptVO dVO = null;
			while (rs.next()) {
				dVO = new DeptVO();
				// deptNum,deptName,tel,loc,note,bonus_rate
				dVO.setDeptNum(rs.getInt("deptno"));
				dVO.setDeptName(rs.getString("deptname"));
				dVO.setTel(rs.getString("contact"));
				dVO.setLoc(rs.getString("loc"));
				dVO.setNote(rs.getString("note"));
				dVO.setBonus_rate(rs.getInt("bonus_rate"));

				list.add(dVO);
			} // end while

		} finally {
			// 6.연결 끊기
			dbCon.closeDB(rs, pstmt, con);
		} // end finally

		return list;
	}// selectAllDept

	public List<DeptVO> selectOneDept(String deptName) throws SQLException {

		List<DeptVO> list = new ArrayList<DeptVO>();
		DeptVO dVO = null;

		// 1.
		// 2.
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder selectOneDept = new StringBuilder();
			selectOneDept.append("	select deptNo,deptName,contact,loc,note,bonus_rate").append("	from department	")
					.append("	where REPLACE(LOWER(deptName), ' ', '') LIKE ?		");

			pstmt = con.prepareStatement(selectOneDept.toString());
			// 4.
			pstmt.setString(1, "%" + deptName.toLowerCase().replace(" ", "") + "%");
			// 5.
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dVO = new DeptVO();

				dVO.setDeptNum(rs.getInt("deptNo"));
				dVO.setDeptName(rs.getString("deptName"));
				dVO.setTel(rs.getString("contact"));
				dVO.setLoc(rs.getString("loc"));
				dVO.setNote(rs.getString("note"));
				dVO.setBonus_rate(rs.getInt("bonus_rate"));
				list.add(dVO);
			} // end if
		} finally {
			// 6.연결 끊기
			dbCon.closeDB(rs, pstmt, con);

		} // end finally

		return list;
	}// selectOneDept

	public List<DeptVO> selectOneEmp(String deptName) throws SQLException {

		List<DeptVO> list = new ArrayList<DeptVO>();
		DeptVO dVO = null;

		// 1.
		// 2.
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder selectOneDept = new StringBuilder();
			selectOneDept.append("	select e.empno,e.emp_name,p.position_name,e.contact")
					.append("	from employee e, position p,department d")
					.append("	where e.position_id = p.position_id	").append("   and e.deptNo = d.deptNo ")
					.append("   and d.deptName = ? ");

			pstmt = con.prepareStatement(selectOneDept.toString());
			// 4.
			pstmt.setString(1, deptName);
			// 5.
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dVO = new DeptVO();
				dVO.setEmpno(rs.getInt("empno"));
				dVO.setEmp_name(rs.getString("emp_name"));
				dVO.setPosition_name(rs.getString("position_name"));
				dVO.setContact(rs.getString("contact"));
				list.add(dVO);

			} // end if
		} finally {
			// 6.연결 끊기
			dbCon.closeDB(rs, pstmt, con);

		} // end finally

		return list;
	}// selectOneDept

	public boolean deptNumExists(int deptNum) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder deptNumEx = new StringBuilder();
			deptNumEx.append("	select COUNT(*)	").append("	FROM department	").append("	WHERE deptNo = ?	");

			pstmt = con.prepareStatement(deptNumEx.toString());
			// 4.
			pstmt.setInt(1, deptNum);
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 1개 이상이면 존재함
			} // end if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.연결 끊기
			try {
				dbCon.closeDB(rs, pstmt, con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end finally
		return false;
	}// deptNumExists

	public boolean deptNameExists(String deptName) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder deptNameEx = new StringBuilder();
			deptNameEx.append("	select COUNT(*)	").append("	FROM department	")
					.append("	WHERE REPLACE(LOWER(deptName), ' ', '') LIKE ?		");

			pstmt = con.prepareStatement(deptNameEx.toString());
			// 4.
			pstmt.setString(1, deptName.toLowerCase().replace(" ", ""));
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 1개 이상이면 존재함
			} // end if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.연결 끊기
			try {
				dbCon.closeDB(rs, pstmt, con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end finally
		return false;
	}// deptNameExists

	public boolean deptTelExists(String contact) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder deptTelEx = new StringBuilder();
			deptTelEx.append("	select COUNT(*)	").append("	FROM department	").append("	WHERE contact = ?	");

			pstmt = con.prepareStatement(deptTelEx.toString());
			// 4.
			pstmt.setString(1, contact);
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 1개 이상이면 존재함
			} // end if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.연결 끊기
			try {
				dbCon.closeDB(rs, pstmt, con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end finally
		return false;
	}// deptTelExists

	public boolean deptTelExistsExceptSelf(String contact, int deptNum) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder deptTelEx = new StringBuilder();
			deptTelEx.append("	select COUNT(*)	").append("	FROM department	").append("	WHERE contact = ?	")
					.append("	and deptno != ?	");

			pstmt = con.prepareStatement(deptTelEx.toString());
			// 4.
			pstmt.setString(1, contact);
			pstmt.setInt(2, deptNum);
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 1개 이상이면 존재함
			} // end if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.연결 끊기
			try {
				dbCon.closeDB(rs, pstmt, con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end finally
		return false;
	}// deptTelExistsExceptSelf

	public boolean deptNameExistsExceptSelf(String deptName, int deptNum) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			// 3.
			StringBuilder deptTelEx = new StringBuilder();
			deptTelEx.append("	select COUNT(*)	").append("	FROM department	")
					.append("	WHERE REPLACE(LOWER(deptName), ' ', '') LIKE ?		").append("	and deptno != ?	");

			pstmt = con.prepareStatement(deptTelEx.toString());
			// 4.
			pstmt.setString(1, deptName.toLowerCase().replace(" ", ""));
			pstmt.setInt(2, deptNum);
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 1개 이상이면 존재함
			} // end if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6.연결 끊기
			try {
				dbCon.closeDB(rs, pstmt, con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end finally
		return false;
	}// deptNameExistsExceptSelf

}// class
