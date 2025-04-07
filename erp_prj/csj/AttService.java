package service;

import dao.AttDAO;
import vo.AttVO;
import java.util.List;

public class AttService {
    private AttDAO dao = new AttDAO();

    /**
     * 조건에 맞는 근태 정보를 조회합니다.
     */
    public List<AttVO> searchAttendance(String dept, String name, String startDate, String endDate) {
        return dao.getAttendanceList(dept, name, startDate, endDate);
    }
    
    /**
     * 주어진 근태 번호(attId)의 상태를 newStatus로 업데이트합니다.
     */
    public void updateStatus(int attId, String newStatus) {
        dao.updateAttendanceStatus(attId, newStatus);
    }
}
