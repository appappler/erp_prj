package kr.co.sist.admin.dao;

import java.sql.*;
import java.util.*;

import kr.co.sist.admin.vo.PayrollVO;

public class PayrollDAO {
    private static final PayrollDAO instance = new PayrollDAO();
    private List<PayrollVO> cachedList = new ArrayList<>();

    public static PayrollDAO getInstance() {
        return instance;
    }

    private PayrollDAO() {}

    public List<PayrollVO> selectSalarySearch(String dept, String pos, String year, String name) {
        List<PayrollVO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TO_CHAR(s.payday, 'YYYY-MM-DD') AS payday, e.empno, e.emp_name, d.deptname, p.position_name, ")
        .append("NVL(s.base_salary, p.salary) AS salary, ")
        .append("(NVL(s.base_salary, p.salary) * NVL(d.bonus_rate, 0) / 100) AS bonus, ")
        .append("s.actual_salary, ")
        .append("e.hire_date, ")
        .append("t.income_tax, t.local_tax, t.national_tax, t.health_tax, t.emp_tax, t.longterm_tax, ")
        .append("(NVL(s.base_salary, p.salary) * (t.income_tax + t.local_tax + t.national_tax + t.health_tax + t.emp_tax + t.longterm_tax) / 100) AS total_deduction ")
        .append("FROM salary s ")
        .append("JOIN employee e ON s.empno = e.empno ")
        .append("JOIN department d ON e.deptno = d.deptno ")
        .append("JOIN position p ON e.position_id = p.position_id ")
        .append("JOIN tax_rate t ON 1 = 1 ");

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

    public PayrollVO getPayrollDetailFromCache(String empno, String payDate) {
        for (PayrollVO vo : cachedList) {
            if (vo.getEmpno().equals(empno) && vo.getPayDate().equals(payDate)) {
                return vo;
            }
        }
        return null;
    }

    public List<PayrollVO> getMonthlyPayrollFromDB(String empno) {
        List<PayrollVO> list = new ArrayList<>();
        String sql = "SELECT TO_CHAR(s.payday, 'YYYY-MM-DD') AS payday, e.empno, e.emp_name, d.deptname, p.position_name, " +
                     "NVL(s.base_salary, p.salary) AS salary, " +
                     "(NVL(s.base_salary, p.salary) * NVL(d.bonus_rate, 0) / 100) AS bonus, " +
                     "s.actual_salary, e.hire_date, " +
                     "t.income_tax, t.local_tax, t.national_tax, t.health_tax, t.emp_tax, t.longterm_tax, " +
                     "(NVL(s.base_salary, p.salary) * (t.income_tax + t.local_tax + t.national_tax + t.health_tax + t.emp_tax + t.longterm_tax) / 100) AS total_deduction " +
                     "FROM salary s " +
                     "JOIN employee e ON s.empno = e.empno " +
                     "JOIN department d ON e.deptno = d.deptno " +
                     "JOIN position p ON e.position_id = p.position_id " +
                     "JOIN tax_rate t ON 1 = 1 " +
                     "WHERE e.empno = ? ORDER BY s.payday";

        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(extractVO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private PayrollVO extractVO(ResultSet rs) throws SQLException {
        PayrollVO vo = new PayrollVO();
        vo.setEmpno(rs.getString("empno"));
        vo.setEmp_name(rs.getString("emp_name"));
        vo.setDeptname(rs.getString("deptname"));
        vo.setPosition_name(rs.getString("position_name"));
        vo.setHireDate(rs.getString("hire_date"));
        vo.setPayDate(rs.getString("payday"));
        vo.setBaseSalary(rs.getInt("salary"));
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

    public PayrollVO selectPayrollDirect(String empno, String payday) {
        PayrollVO vo = null;

        // ★ 시간 제거
        if (payday.contains(" ")) {
            payday = payday.split(" ")[0];
        }

        String sql = "SELECT TO_CHAR(s.payday, 'YYYY-MM-DD') AS payday, e.empno, e.emp_name, d.deptname, p.position_name, " +
                "NVL(s.base_salary, p.salary) AS salary, " +
                "(NVL(s.base_salary, p.salary) * NVL(d.bonus_rate, 0) / 100) AS bonus, " +
                "s.income_tax, s.local_tax, s.national_tax, " +
                "s.health_tax, s.emp_tax, s.longterm_tax, " +
                "s.total_deduction, s.actual_salary, e.hire_date " +
                "FROM salary s " +
                "JOIN employee e ON s.empno = e.empno " +
                "JOIN department d ON e.deptno = d.deptno " +
                "JOIN position p ON e.position_id = p.position_id " +
                "WHERE e.empno = ? AND TRUNC(s.payday) = TO_DATE(?, 'YYYY-MM-DD')"+
                "ORDER BY s.payday";


        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empno);
            pstmt.setString(2, payday); // ← 이미 yyyy-MM-dd 형식으로 보장됨

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                vo = extractVO(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vo;
    }


    public boolean updateBaseSalary(String empno, String payDate, int baseSalary) {
        String sql = "UPDATE salary SET base_salary = ? WHERE empno = ? AND payday = TO_DATE(?, 'YYYY-MM-DD')";

        try (Connection conn = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, baseSalary);
            pstmt.setString(2, empno);
            pstmt.setString(3, payDate);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
}