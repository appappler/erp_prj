package kr.co.sist.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.vo.PayrollVO;

public class PayrollDAO {
    private static final PayrollDAO instance = new PayrollDAO();
    private List<PayrollVO> cachedList = new ArrayList<>();

    public static PayrollDAO getInstance() {
        return instance;
    }

    private PayrollDAO() {}

    // 🔹 조건 검색 (JOIN 포함)
    public List<PayrollVO> selectSalarySearch(String empno, String year) {
        List<PayrollVO> list = new ArrayList<>();
        

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.payday, e.empno, e.emp_name, d.deptname, p.position_name, ")
        .append("p.salary, ")
        .append("CASE WHEN TO_CHAR(s.payday, 'MM') = '04' ")
        .append("     THEN (p.salary * NVL(d.bonus_rate,0) / 100) ")
        .append("     ELSE 0 END AS bonus, ")
        .append("s.income_tax, s.local_tax, s.national_tax, s.health_tax, s.emp_tax, s.longterm_tax, ")
        .append("(s.income_tax + s.local_tax + s.national_tax + s.health_tax + ")
        .append("s.emp_tax + s.longterm_tax) AS total_deduction, ")
        .append("s.actual_salary, ")
        .append("e.hire_date ")
        .append("FROM salary s, employee e, department d, position p ")
        .append("WHERE s.empno = e.empno ")
        .append("AND e.deptno = d.deptno ")
        .append("AND e.position_id = p.position_id ")
        .append("AND s.empno = ? ")
        .append("AND TO_CHAR(s.payday, 'YYYY') = ? ")
        .append("ORDER BY s.payday DESC");


        
       

        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

        	pstmt.setString(1, empno);
            pstmt.setString(2, year);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PayrollVO vo = extractVO(rs);
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cachedList = list;
        return list;
    }

    // 🔹 캐시에서 단건 조회
    public PayrollVO getPayrollDetailFromCache(String empno, String payDate) {
        for (PayrollVO vo : cachedList) {
            if (vo.getEmpno().equals(empno) && vo.getPayDate().equals(payDate)) {
                return vo;
            }
        }
        return null;
    }

  

    // 🔹 VO 생성
    private PayrollVO extractVO(ResultSet rs) throws SQLException {
        PayrollVO vo = new PayrollVO();
        vo.setEmpno(rs.getString("empno"));
        vo.setEmp_name(rs.getString("emp_name"));
        vo.setDeptname(rs.getString("deptname"));
        vo.setPosition_name(rs.getString("position_name"));
        vo.setHireDate(rs.getString("hire_date"));
        vo.setPayDate(rs.getString("payday"));
        vo.setSalary(rs.getInt("salary"));
        vo.setBonus(rs.getInt("bonus"));
        vo.setIncomeTax(rs.getInt("income_tax"));
        vo.setLocalIncomeTax(rs.getInt("local_tax"));
        vo.setNationalPension(rs.getInt("national_tax"));
        vo.setHealthInsurance(rs.getInt("health_tax"));
        vo.setEmploymentInsurance(rs.getInt("emp_tax"));
        vo.setLongTermCareInsurance(rs.getInt("longterm_tax"));
        vo.setTotal_deduction(rs.getInt("total_deduction"));
        vo.setActualSalary(rs.getInt("actual_salary"));
        return vo;
    }
    
    
    

    // 🔹 콤보박스용 목록

 // 🔹 사번 기준으로만 연도 목록 조회 (사원 전용)
    public List<String> getYearsByEmpno(String empno) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT TO_CHAR(payday, 'YYYY') AS year "
                   + "FROM salary WHERE empno = ? ORDER BY year DESC";
        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("year"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


   
}
