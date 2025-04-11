package kr.co.sist.user.service;

import kr.co.sist.user.dao.AttDAO;
import kr.co.sist.user.vo.AttVO;

public class AttService {
    private AttDAO dao = new AttDAO();

    // 출근 기록을 INSERT하는 메서드
    public void insertAttendance(int empNo, String empName, String inTime, String status) {
        dao.insertAttendance(empNo, empName, inTime, status);
    }
    
    // 출근 기록을 UPDATE하는 메서드 (퇴근/조퇴 처리)
    public void updateAttendanceStatus(int empNo, String outTime, String newStatus) {
        dao.updateAttendanceStatusForEmp(empNo, outTime, newStatus);
    }
    
    // 해당 사원이 오늘 출근 기록을 가지고 있는지 확인
    public boolean checkIfCheckedIn(int empNo, String date) {
        return dao.existsCheckInRecord(empNo, date);
    }
    
    // 기존 조회용 메서드 (조건 검색 등)
    public java.util.List<kr.co.sist.user.vo.AttVO> searchAttendance(String dept, String name, String startDate, String endDate) {
        return dao.getAttendanceList(dept, name, startDate, endDate);
    }
}
