package kr.co.sist.user.vo;

import java.sql.Date;

/**
 * 
 */
public class EduVO {

	private int edu_id;
	private int empno;
	private Date admission;
	private Date graduation;
	private String schoolName;
	private String major;
	private String gradStatus;
	private String degree;
	
	public EduVO() {
		super();
	}
	
	public EduVO(int edu_id, int empno, Date admission, Date graduation, String schoolName, String major,
			String gradStatus, String degree) {
		super();
		this.edu_id = edu_id;
		this.empno = empno;
		this.admission = admission;
		this.graduation = graduation;
		this.schoolName = schoolName;
		this.major = major;
		this.gradStatus = gradStatus;
		this.degree = degree;
	}

	public int getEdu_id() {
		return edu_id;
	}
	public void setEdu_id(int edu_id) {
		this.edu_id = edu_id;
	}
	public Date getAdmission() {
		return admission;
	}
	public void setAdmission(Date admission) {
		this.admission = admission;
	}
	public Date getGraduation() {
		return graduation;
	}
	public void setGraduation(Date graduation) {
		this.graduation = graduation;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getGradStatus() {
		return gradStatus;
	}
	public void setGradStatus(String gradStatus) {
		this.gradStatus = gradStatus;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}

	@Override
	public String toString() {
		return "EduVO [edu_id=" + edu_id + ", empno=" + empno + ", admission=" + admission + ", graduation="
				+ graduation + ", schoolName=" + schoolName + ", major=" + major + ", gradStatus=" + gradStatus
				+ ", degree=" + degree + "]";
	}
	
}//class