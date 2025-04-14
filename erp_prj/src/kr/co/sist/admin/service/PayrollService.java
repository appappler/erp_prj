package kr.co.sist.admin.service;

import java.util.List;

import kr.co.sist.admin.dao.PayrollDAO;
import kr.co.sist.admin.vo.PayrollVO;

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

    // 🔹 조건 검색 → 캐시에 저장
    public List<PayrollVO> searchPayroll(String dept, String pos, String year, String name) {
        return dao.selectSalarySearch(dept, pos, year, name);
    }

    // 🔹 캐시된 리스트에서 단건 가져오기
    public PayrollVO getPayrollDetail(String empno, String payDate) {
        return dao.getPayrollDetailFromCache(empno, payDate);
    }

    // ✅ 🔹 DB에서 월별 급여 직접 조회 (수정 반영용)
    public List<PayrollVO> getMonthlyPayrollFromDB(String empno) {
        return dao.getMonthlyPayrollFromDB(empno);
    }

    // ✅ 🔹 DB에서 단건 급여 직접 조회 (캐시 무시)
    public PayrollVO getPayrollDetailFreshFromDB(String empno, String payDate) {
        return dao.selectPayrollDirect(empno, payDate);
    }

    // ✅ 🔹 base_salary 업데이트 기능
    public boolean updateBaseSalary(String empno, String payDate, int baseSalary) {
        return dao.updateBaseSalary(empno, payDate, baseSalary);
    }
    
    public List<String> getYearsByEmpno(String empno) {
        return PayrollDAO.getInstance().getYearsByEmpno(empno);
    }

}
