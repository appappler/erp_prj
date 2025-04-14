package kr.co.sist.user.service;

import java.util.List;

import kr.co.sist.user.dao.PayrollDAO;
import kr.co.sist.user.vo.PayrollVO;

public class PayrollService {
    private static final PayrollService instance = new PayrollService();
    private final PayrollDAO dao = PayrollDAO.getInstance();

    private PayrollService() {}

    public static PayrollService getInstance() {
        return instance;
    }

    public List<String> getYearsByEmpno(String empno) {
        return dao.getYearsByEmpno(empno);
    }

    public List<PayrollVO> searchPayroll(String empno, String year) {
        return dao.selectSalarySearch(empno, year);
    }

    public PayrollVO getPayrollDetail(String empno, String payDate) {
        return dao.getPayrollDetailFromCache(empno, payDate);
    }
}
