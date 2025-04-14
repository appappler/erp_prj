package kr.co.sist.admin.vo;

import java.sql.Date;

/**
 * 
 */
public class RankVO {
	private int rankNum;
	private String rankName;
	private int salary;
	private	int incomeTax;
	private	int nationalPension;
	private	int healthInsurance;
	private	int employmentInsurance;
	private	int longTermCareInsurance;
	private	int localIncomeTax;
	private	Date localDate;
	private	int empCount;
	
	public RankVO() {
		super();
	}

	public RankVO(String rankName, int salary, int empCount) {
		super();
		this.rankName = rankName;
		this.salary = salary;
		this.empCount = empCount;
	}

	public RankVO(int rankNum, String rankName, int salary, int incomeTax, int nationalPension, int healthInsurance,
			int employmentInsurance, int longTermCareInsurance, int localIncomeTax, Date localDate, int empCount) {
		super();
		this.rankNum = rankNum;
		this.rankName = rankName;
		this.salary = salary;
		this.incomeTax = incomeTax;
		this.nationalPension = nationalPension;
		this.healthInsurance = healthInsurance;
		this.employmentInsurance = employmentInsurance;
		this.longTermCareInsurance = longTermCareInsurance;
		this.localIncomeTax = localIncomeTax;
		this.localDate = localDate;
		this.empCount = empCount;
	}

	public int getRankNum() {
		return rankNum;
	}

	public void setRankNum(int rankNum) {
		this.rankNum = rankNum;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(int incomeTax) {
		this.incomeTax = incomeTax;
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

	public int getLocalIncomeTax() {
		return localIncomeTax;
	}

	public void setLocalIncomeTax(int localIncomeTax) {
		this.localIncomeTax = localIncomeTax;
	}

	public Date getLocalDate() {
		return localDate;
	}

	public void setLocalDate(Date localDate) {
		this.localDate = localDate;
	}

	public int getEmpCount() {
		return empCount;
	}

	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}

	@Override
	public String toString() {
		return "RankVO [rankNum=" + rankNum + ", rankName=" + rankName + ", salary=" + salary + ", incomeTax="
				+ incomeTax + ", nationalPension=" + nationalPension + ", healthInsurance=" + healthInsurance
				+ ", employmentInsurance=" + employmentInsurance + ", longTermCareInsurance=" + longTermCareInsurance
				+ ", localIncomeTax=" + localIncomeTax + ", localDate=" + localDate + ", empCount=" + empCount + "]";
	}
	
}//class
