package csj_dao;

import csj_vo.AttendanceVO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "c##comodo";
    private static final String PASSWORD = "dragon";

    public List<AttendanceVO> getAllEmployees() {
        List<AttendanceVO> employees = new ArrayList<>();
        String query = "SELECT att_id, empno, status_id, in_time, out_time, work_hours, remarks, input_date FROM attendance ORDER BY in_time DESC";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new AttendanceVO(
                    rs.getInt("att_id"),
                    rs.getInt("empno"),
                    rs.getInt("status_id"),
                    rs.getTimestamp("in_time"),
                    rs.getTimestamp("out_time"),
                    rs.getDouble("work_hours"),
                    rs.getString("remarks"),
                    rs.getDate("input_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
