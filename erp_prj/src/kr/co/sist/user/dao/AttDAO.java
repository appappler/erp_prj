package kr.co.sist.user.dao;

import java.sql.Connection; import java.sql.PreparedStatement; import java.sql.ResultSet; import java.sql.SQLException;

import kr.co.sist.admin.vo.AdminAccountVO;
import kr.co.sist.user.vo.AttVO;
import kr.co.sist.user.vo.UserAccountVO;

import java.util.ArrayList; import java.util.List;

public class AttDAO {
	// 기존 조회 메서드 (생략된 나머지 코드는 그대로 유지)
	public List<AttVO> getAttendanceList(String dept, String name, String startDate, String endDate) {
	    List<AttVO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    DbConnection dbConn = DbConnection.getInstance();

	    try {
	        conn = dbConn.getConn();
	        String sql = "SELECT a.att_id, e.empno, e.emp_name, d.deptname, p.position_name, " +
	                     "a.in_time, " +
	                     "a.out_time, " +
	                     "a.status_id " +
	                     "FROM employee e " +
	                     "JOIN attendance a ON e.empno = a.empno " +
	                     "JOIN department d ON e.deptno = d.deptno " +
	                     "JOIN position p ON e.position_id = p.position_id " +
	                     "WHERE d.deptname LIKE ? " +
	                     "AND e.emp_name LIKE ? " +
	                     "AND TRUNC(a.in_time) BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + dept + "%");
	        pstmt.setString(2, "%" + name + "%");
	        pstmt.setString(3, startDate);
	        pstmt.setString(4, endDate);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            AttVO vo = new AttVO();
	            vo.setAttId(rs.getInt("att_id"));
	            vo.setEmpNo(rs.getInt("empno"));
	            vo.setEmpName(rs.getString("emp_name"));
	            vo.setDeptName(rs.getString("deptname"));
	            vo.setPositionName(rs.getString("position_name"));
	            vo.setInTime(rs.getDate("in_time"));
	            vo.setOutTime(rs.getDate("out_time"));
	            vo.setStatusId(rs.getString("status_id"));
	            list.add(vo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if(rs != null) rs.close(); } catch(Exception e) {}
	        try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
	        try { if(conn != null) conn.close(); } catch(Exception e) {}
	    }
	    return list;
	}

	// 수정된 출근 기록 INSERT 메서드
	public void insertAttendance(int empNo, String empName, String inTime, String status) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DbConnection.getInstance().getConn();
	        // 여기서 att_id를 시퀀스를 이용하여 직접 지정합니다.
	        String sql = "INSERT INTO attendance (att_id, empno, status_id, in_time, input_date) " +
	                     "VALUES (seq_attendance.NEXTVAL, ?, ?, TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), SYSDATE)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, empNo);
	        pstmt.setString(2, status);
	        pstmt.setString(3, inTime);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (pstmt != null) pstmt.close(); } catch(Exception e) {}
	        try { if (conn != null) conn.close(); } catch(Exception e) {}
	    }
	}

	// 퇴근/조퇴 기록 UPDATE 메서드
	public void updateAttendanceStatusForEmp(int empNo, String outTime, String status) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DbConnection.getInstance().getConn();
	        String sql = "UPDATE attendance SET out_time = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), status_id = ? " +
	                     "WHERE empno = ? AND TRUNC(in_time) = TO_DATE(?, 'YYYY-MM-DD')";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, outTime);
	        pstmt.setString(2, status);
	        pstmt.setInt(3, empNo);
	        pstmt.setString(4, outTime.substring(0, 10));
	        int result = pstmt.executeUpdate();
	        if(result == 0) {
	            System.out.println("업데이트할 출근 기록이 없습니다.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
	        try { if(conn != null) conn.close(); } catch(Exception e) {}
	    }
	}

	// 해당 사원의 오늘 출근 기록 여부 확인
	public boolean existsCheckInRecord(int empNo, String date) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    boolean exists = false;
	    try {
	        conn = DbConnection.getInstance().getConn();
	        String sql = "SELECT COUNT(*) cnt FROM attendance " +
	                     "WHERE empno = ? AND TRUNC(in_time) = TO_DATE(?, 'YYYY-MM-DD')";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, empNo);
	        pstmt.setString(2, date);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            exists = rs.getInt("cnt") > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if(rs != null) rs.close(); } catch(Exception e) {}
	        try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
	        try { if(conn != null) conn.close(); } catch(Exception e) {}
	    }
	    return exists;
	}
	
	
	public String convertToName(int inputId) {
		
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    String outputEmpName=null;
	    
	    try {
	        conn = DbConnection.getInstance().getConn();
	        StringBuilder sb = new StringBuilder();
	        
	        sb.append("	select	EMP_NAME	")
	        .append("	from	employee	")
	        .append("	where	EMPNO	=	?")
	        ;
	        pstmt = conn.prepareStatement(sb.toString());
	        pstmt.setInt(1, inputId);
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()){outputEmpName = rs.getString("emp_name");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if(rs != null) rs.close(); } catch(Exception e) {}
	        try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
	        try { if(conn != null) conn.close(); } catch(Exception e) {}
	    }
	    
	    return outputEmpName;

	}//convertToName
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}