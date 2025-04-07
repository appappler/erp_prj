package dao;

import vo.AttVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttDAO {

    /**
     * 부서, 이름, 시작일과 종료일을 조건으로 근태 정보를 조회합니다.
     */
    public List<AttVO> getAttendanceList(String dept, String name, String startDate, String endDate) {
        List<AttVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT a.att_id, e.empno, e.emp_name, d.deptname, p.position_name, " +
                         "TO_CHAR(a.in_time, 'YYYY-MM-DD HH24:MI:SS') AS in_time, " +
                         "TO_CHAR(a.out_time, 'YYYY-MM-DD HH24:MI:SS') AS out_time, " +
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
                vo.setInTime(rs.getString("in_time"));
                vo.setOutTime(rs.getString("out_time"));
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
    
    /**
     * 주어진 근태 번호(attId)에 대해 상태를 newStatus로 업데이트합니다.
     */
    public void updateAttendanceStatus(int attId, String newStatus) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE attendance SET status_id = ? WHERE att_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, attId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if(pstmt != null) pstmt.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}
