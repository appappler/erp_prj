package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.AttendanceVO;

/**
 * 
 */
public class AttendanceDAO {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "comodo";
    private static final String PASSWORD = "dragon";

    public List<AttendanceVO> getAllEmployees() {
        List<AttendanceVO> employees = new ArrayList<>();

        
        String query = "SELECT e.empno AS empno, 		" 
        		+"	e.emp_name AS emp_name, 			"
        		+"	d.deptname AS deptname, 			"
        		+"	p.position_name AS position_name,	 " 
        		+"	a.in_time AS in_time, 				"
        		+"	a.out_time AS out_time,				" 
        		+"	a.status_id AS status_id			" 
        		+"	FROM employee e, department d, position p, attendance a "
        		+"	WHERE e.deptno = d.deptno			"
        		+"	AND e.position_id = p.position_id	"
        		+"	AND e.empno = a.empno		";
  

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employees.add(new AttendanceVO(
                        rs.getString("empno"),
                        rs.getString("emp_name"),
                        rs.getString("deptname"),
                        rs.getString("position_name"),
                        rs.getTimestamp("in_time"),
                        rs.getTimestamp("out_time"),
                        rs.getString("status_id") //
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
    
}//class
