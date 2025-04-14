package kr.co.sist.admin.vo;

/**
 * 
 */
public class PayrollVO {
    private String empno;
    private String emp_name;
    private String deptname;
    private String position_name;
    private String hireDate;
    private String payDate;

    private int salary;
    private int bonus;

    private int incomeTax;
    private int localIncomeTax;
    private int nationalPension;
    private int healthInsurance;
    private int employmentInsurance;
    private int longTermCareInsurance;

    private int total_deduction;
    private int actualSalary;

    // 🔹 Getter & Setter
    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(int incomeTax) {
        this.incomeTax = incomeTax;
    }

    public int getLocalIncomeTax() {
        return localIncomeTax;
    }

    public void setLocalIncomeTax(int localIncomeTax) {
        this.localIncomeTax = localIncomeTax;
    }

    public int getNationalPension() {
        return nationalPension;
    }

    public void setNationalPension(int nationalPension) {
        this.nationalPension = nationalPension;
    }

    public int getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(int healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public int getEmploymentInsurance() {
        return employmentInsurance;
    }

    public void setEmploymentInsurance(int employmentInsurance) {
        this.employmentInsurance = employmentInsurance;
    }

    public int getLongTermCareInsurance() {
        return longTermCareInsurance;
    }

    public void setLongTermCareInsurance(int longTermCareInsurance) {
        this.longTermCareInsurance = longTermCareInsurance;
    }

    public int getTotal_deduction() {
        return total_deduction;
    }

    public void setTotal_deduction(int total_deduction) {
        this.total_deduction = total_deduction;
    }

    public int getActualSalary() {
        return actualSalary;
    }

    public void setActualSalary(int actualSalary) {
        this.actualSalary = actualSalary;
    }
    
}//class
