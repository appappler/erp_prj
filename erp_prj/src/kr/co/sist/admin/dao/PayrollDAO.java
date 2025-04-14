package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.PayrollVO;

/**
 * 
 */
public class PayrollDAO {
    private static final PayrollDAO instance = new PayrollDAO();
    private List<PayrollVO> cachedList = new ArrayList<>();

    public static PayrollDAO getInstance() {
        return instance;
    }

    private PayrollDAO() {
    	
    }

    // 🔹 조건 검색 (JOIN 포함)
    public List<PayrollVO> selectSalarySearch(String dept, String pos, String year, String name) {
        List<PayrollVO> list = new ArrayList<>();
       

        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT * FROM v_salary_report WHERE 1=1 ");
        sql.append("SELECT s.payday, e.empno, e.emp_name, d.deptname, p.position_name, ")
        .append("p.salary, (p.salary * NVL(d.bonus_rate,0) / 100) AS bonus, ")
        .append("p.income_tax, p.local_tax, p.national_tax, p.health_tax, p.emp_tax, p.longterm_tax, ")
        .append("(p.income_tax + p.local_tax + p.national_tax + p.health_tax + ")
        .append("p.emp_tax + p.longterm_tax) AS total_deduction, ")
        .append("s.actual_salary, ")
        .append("e.hire_date ")
        .append("FROM salary s, employee e, department d, position p ")
        .append("WHERE s.empno = e.empno ")
        .append("AND e.deptno = d.deptno ")
        .append("AND e.position_id = p.position_id ");



        // 조건에 따라 SQL 구성
        if (dept != null && !dept.equals("부서") && !dept.trim().isEmpty()) {
            sql.append(" AND deptname = ? ");
        }
        if (pos != null && !pos.equals("직급") && !pos.trim().isEmpty()) {
            sql.append(" AND position_name = ? ");
        }
        if (year != null && !year.equals("년도") && !year.trim().isEmpty()) {
            sql.append(" AND TO_CHAR(payday, 'YYYY') = ? ");
        }
        if (name != null && !name.equals("이름") && !name.trim().isEmpty()) {
            sql.append(" AND REPLACE(LOWER(e.emp_name), ' ', '') LIKE ? ");
        }
       
      

        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (dept != null) pstmt.setString(index++, dept);
            if (pos != null) pstmt.setString(index++, pos);
            if (year != null) pstmt.setString(index++, year);
            if (name != null && !name.trim().isEmpty()) {
                pstmt.setString(index++, "%" + name.toLowerCase().replace(" ", "") + "%");
            }

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

    // 🔹 캐시에서 월별 급여 조회
    public List<PayrollVO> getMonthlyPayrollFromCache(String empno) {
        List<PayrollVO> result = new ArrayList<>();
        for (PayrollVO vo : cachedList) {
            if (vo.getEmpno().equals(empno)) {
                result.add(vo);
            }
        }
        return result;
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
    public List<String> getAllDepartments() {
        return getDistinct("department", "deptname");
    }

    public List<String> getAllPositions() {
        return getDistinct("position", "position_name");
    }

    public List<String> getAllYears() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT TO_CHAR(payday, 'YYYY') AS year FROM salary ORDER BY year";
        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) list.add(rs.getString("year"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getAllEmployeeNames() {
        return getDistinct("employee", "emp_name");
    }

    private List<String> getDistinct(String table, String column) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT " + column + " FROM " + table + " ORDER BY " + column;
  
        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) list.add(rs.getString(column));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}//class
