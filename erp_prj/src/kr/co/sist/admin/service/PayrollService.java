package kr.co.sist.admin.service;

import java.util.List;

import kr.co.sist.admin.dao.PayrollDAO;
import kr.co.sist.admin.vo.PayrollVO;

/**
 * 
 */
public class PayrollService {
    private static final PayrollService instance = new PayrollService();
    private final PayrollDAO dao = PayrollDAO.getInstance();

    private PayrollService() {}

    public static PayrollService getInstance() {
        return instance;
    }

    // 🔹 콤보박스 항목 조회
    public List<String> getAllDepartments() {
        return dao.getAllDepartments();
    }

    public List<String> getAllPositions() {
        return dao.getAllPositions();
    }

    public List<String> getAllYears() {
        return dao.getAllYears();
    }

    public List<String> getAllEmployeeNames() {
        return dao.getAllEmployeeNames();
    }

    // 🔹 검색 실행 → 리스트 반환 + DAO에서 캐시 저장됨
    public List<PayrollVO> searchPayroll(String dept, String pos, String year, String name) {
        return dao.selectSalarySearch(dept, pos, year, name);
    }

    // 🔹 캐시된 리스트에서 특정 사원의 월급 명세서 1건 가져오기
    public PayrollVO getPayrollDetail(String empno, String payDate) {
        return dao.getPayrollDetailFromCache(empno, payDate);
    }

    // 🔹 캐시된 리스트에서 특정 사원의 월별 급여 전체 리스트 가져오기
    public List<PayrollVO> getMonthlyPayroll(String empno) {
        return dao.getMonthlyPayrollFromCache(empno);
    }
    
}//class
