package kr.co.sist.user.vo;

import java.sql.Date;

/**
 * 
 */
public class CareerVO {

	private int career_id;
	private int empno;
	private String company;
	private Date hireDate;
	private Date leaveDate;
	private String exPosition;
	private String exDept;
	private String reason;
	
	public CareerVO() {
		super();
	}
	
	
	public CareerVO(int career_id, int empno, String company, Date hireDate, Date leaveDate, String exPosition,
			String exDept, String reason) {
		super();
		this.career_id = career_id;
		this.empno = empno;
		this.company = company;
		this.hireDate = hireDate;
		this.leaveDate = leaveDate;
		this.exPosition = exPosition;
		this.exDept = exDept;
		this.reason = reason;
	}


	public int getCareer_id() {
		return career_id;
	}

	public void setCareer_id(int career_id) {
		this.career_id = career_id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getExPosition() {
		return exPosition;
	}

	public void setExPosition(String exPosition) {
		this.exPosition = exPosition;
	}

	public String getExDept() {
		return exDept;
	}

	public void setExDept(String exDept) {
		this.exDept = exDept;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getEmpno() {
		return empno;
	}


	public void setEmpno(int empno) {
		this.empno = empno;
	}


	@Override
	public String toString() {
		return "CareerVO [career_id=" + career_id + ", empno=" + empno + ", company=" + company + ", hireDate="
				+ hireDate + ", leaveDate=" + leaveDate + ", exPosition=" + exPosition + ", exDept=" + exDept
				+ ", reason=" + reason + "]";
	}

}//class