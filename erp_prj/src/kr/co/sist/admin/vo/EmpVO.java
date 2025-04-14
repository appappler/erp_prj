package kr.co.sist.admin.vo;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 
 */
public class EmpVO {
	private int empno;
	private String password;
	private String dept;
	private String ename;
	private String position;
	private Date birthDate;
	private String tel;
	private String email;
	private String address;
	private String imgName;
	private Date hireDate;
	private Timestamp inputDate;
	private String workingFlag;
	private InputStream imgInputStream; // 👈 BLOB 저장용
	private byte[] imgBytes; // 🔹 DB에서 안전하게 읽어오기 위한 필드


	public EmpVO() {
		super();
	}

	public EmpVO(int empno, String password, String dept, String ename, String position, Date birthDate, String tel,
			String email, String address, String imgName, Date hireDate, Timestamp inputDate, String workingFlag,
			InputStream imgInputStream, byte[] imgBytes) {
		super();
		this.empno = empno;
		this.password = password;
		this.dept = dept;
		this.ename = ename;
		this.position = position;
		this.birthDate = birthDate;
		this.tel = tel;
		this.email = email;
		this.address = address;
		this.imgName = imgName;
		this.hireDate = hireDate;
		this.inputDate = inputDate;
		this.workingFlag = workingFlag;
		this.imgInputStream = imgInputStream;
		this.imgBytes = imgBytes;
	}


	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Timestamp getInputDate() {
		return inputDate;
	}

	public void setInputDate(Timestamp inputDate) {
		this.inputDate = inputDate;
	}

	public String getWorkingFlag() {
		return workingFlag;
	}

	public void setWorkingFlag(String workingFlag) {
		this.workingFlag = workingFlag;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public InputStream getImgInputStream() {
		return imgInputStream;
	}


	public void setImgInputStream(InputStream imgInputStream) {
		this.imgInputStream = imgInputStream;
	}

	public byte[] getImgBytes() {
	    return imgBytes;
	}

	public void setImgBytes(byte[] imgBytes) {
	    this.imgBytes = imgBytes;
	}

	@Override
	public String toString() {
		return "EmpVO [empno=" + empno + ", password=" + password + ", dept=" + dept + ", ename=" + ename
				+ ", position=" + position + ", birthDate=" + birthDate + ", tel=" + tel + ", email=" + email
				+ ", address=" + address + ", imgName=" + imgName + ", hireDate=" + hireDate + ", inputDate="
				+ inputDate + ", workingFlag=" + workingFlag + ", imgInputStream=" + imgInputStream + ", imgBytes="
				+ Arrays.toString(imgBytes) + "]";
	}

}//class