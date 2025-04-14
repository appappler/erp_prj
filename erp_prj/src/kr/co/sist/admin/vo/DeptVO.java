package kr.co.sist.admin.vo;

import java.sql.Date;

public class DeptVO {
	
	private int deptNum;
	private String deptName;
	private	String note;
	private	String tel;
	private	String loc;
	private int bonus_rate;
	private Date input_date;
	private int empno;
	private String emp_name;
	private String position_name;
	private String contact;
	
	public DeptVO() {
		super();
	}

	public int getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(int deptNum) {
		this.deptNum = deptNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public int getBonus_rate() {
		return bonus_rate;
	}

	public void setBonus_rate(int bonus_rate) {
		this.bonus_rate = bonus_rate;
	}

	public Date getInput_date() {
		return input_date;
	}

	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public DeptVO(int deptNum, String deptName, String note, String tel, String loc, int bonus_rate, Date input_date,
			int empno, String emp_name, String position_name, String contact) {
		super();
		this.deptNum = deptNum;
		this.deptName = deptName;
		this.note = note;
		this.tel = tel;
		this.loc = loc;
		this.bonus_rate = bonus_rate;
		this.input_date = input_date;
		this.empno = empno;
		this.emp_name = emp_name;
		this.position_name = position_name;
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "DeptVO [deptNum=" + deptNum + ", deptName=" + deptName + ", note=" + note + ", tel=" + tel + ", loc="
				+ loc + ", bonus_rate=" + bonus_rate + ", input_date=" + input_date + ", empno=" + empno + ", emp_name="
				+ emp_name + ", position_name=" + position_name + ", contact=" + contact + "]";
	}
	
}//class
