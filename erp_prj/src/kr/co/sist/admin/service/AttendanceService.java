package kr.co.sist.admin.service;

import java.util.List;

import kr.co.sist.admin.dao.AttendanceDAO;
import kr.co.sist.admin.vo.AttendanceVO;

/**
 * 
 */
public class AttendanceService {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    public List<AttendanceVO> getEmployeeList() {
        return attendanceDAO.getAllEmployees();
    }
    
}//class
