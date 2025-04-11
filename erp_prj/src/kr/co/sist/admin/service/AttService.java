package kr.co.sist.admin.service;

import java.util.List;

import kr.co.sist.admin.dao.AttDAO;
import kr.co.sist.admin.vo.AttVO;

/**
 * 
 */
public class AttService {
    private AttDAO dao = new AttDAO();

    /**
     * 조건에 맞는 근태 정보를 조회합니다.
     *
     * @param dept 부서명 (LIKE 검색용)
     * @param name 사원명 (LIKE 검색용)
     * @param startDate 조회 시작일 (YYYY-MM-DD 형식)
     * @param endDate 조회 종료일 (YYYY-MM-DD 형식)
     * @return 조건에 해당하는 근태 정보 리스트
     */
    public List<AttVO> searchAttendance(String dept, String name, String startDate, String endDate) {
        return dao.getAttendanceList(dept, name, startDate, endDate);
    }

    /**
     * 주어진 근태 번호(attId)의 상태를 newStatus로 업데이트합니다.
     *
     * @param attId     근태 ID
     * @param newStatus 새로운 상태값
     */
    public void updateStatus(int attId, String newStatus) {
        dao.updateAttendanceStatus(attId, newStatus);
    }
    
}//class
