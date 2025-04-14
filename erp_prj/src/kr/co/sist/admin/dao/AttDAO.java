package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.AttVO;

public class AttDAO {

    /**
     * 부서, 이름, 시작일과 종료일을 조건으로 근태 정보를 조회합니다.
     */
    public List<AttVO> getAttendanceList(String dept, String name, String startDate, String endDate) {
        List<AttVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        DbConnection db = DbConnection.getInstance();

        try {
            conn = db.getConn();

            // SQL 문에서 TO_DATE()를 제거하고, 날짜는 바인드 변수로 직접 전달합니다.
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
                         "AND TRUNC(a.in_time) BETWEEN ? AND ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + dept + "%");
            pstmt.setString(2, "%" + name + "%");
            // 날짜 문자열은 반드시 "YYYY-MM-DD" 형식이어야 합니다.
            pstmt.setDate(3, java.sql.Date.valueOf(startDate));
            pstmt.setDate(4, java.sql.Date.valueOf(endDate));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                AttVO vo = new AttVO();
                vo.setAttId(rs.getInt("att_id"));
                vo.setEmpNo(rs.getInt("empno"));
                vo.setEmpName(rs.getString("emp_name"));
                vo.setDeptName(rs.getString("deptname"));
                vo.setPositionName(rs.getString("position_name"));
                vo.setInTime(rs.getTimestamp("in_time"));
                vo.setOutTime(rs.getTimestamp("out_time"));
                vo.setStatusId(rs.getString("status_id"));
                list.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch(Exception e) {}
            try { if (conn != null) conn.close(); } catch(Exception e) {}
        }
        return list;
    }
   
    /**
     * 주어진 근태 번호(attId)에 대해 상태를 newStatus로 업데이트합니다.
     */
    public void updateAttendanceStatus(int attId, String newStatus) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        DbConnection db = DbConnection.getInstance();

        try {
            conn = db.getConn();
            String sql = "UPDATE attendance SET status_id = ? WHERE att_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, attId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch(Exception e) {}
            try { if (conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}
