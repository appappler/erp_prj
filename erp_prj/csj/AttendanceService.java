package csj_service;

import csj_dao.AttendanceDAO;
import csj_vo.AttendanceVO;
import java.util.List;

public class AttendanceService {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    public List<AttendanceVO> getEmployeeList() {
        return attendanceDAO.getAllEmployees();
    }
}
