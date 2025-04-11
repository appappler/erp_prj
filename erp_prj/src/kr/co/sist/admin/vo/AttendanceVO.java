package kr.co.sist.admin.vo;

import java.util.Date;

/**
 * 
 */
public class AttendanceVO {
    private String empno; 
    private String emp_name;
    private String deptname;
    private String position_name;
    private Date in_Time;
    private Date out_Time;
    private String status_Id;
	
    public AttendanceVO () {}
    
    public AttendanceVO(String empno, String emp_name, String deptname, String position_name, Date in_Time,
			Date out_Time, String status_Id) {
		super();
		this.empno = empno;
		this.emp_name = emp_name;
		this.deptname = deptname;
		this.position_name = position_name;
		this.in_Time = in_Time;
		this.out_Time = out_Time;
		this.status_Id = status_Id;
	}
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
	public Date getIn_Time() {
		return in_Time;
	}
	public void setIn_Time(Date in_Time) {
		this.in_Time = in_Time;
	}
	public Date getOut_Time() {
		return out_Time;
	}
	public void setOut_Time(Date out_Time) {
		this.out_Time = out_Time;
	}
	public String getStatus_Id() {
		return status_Id;
	}
	public void setStatus_Id(String status_Id) {
		this.status_Id = status_Id;
	}
	@Override
	public String toString() {
		return "AttendanceVO [empno=" + empno + ", emp_name=" + emp_name + ", deptname=" + deptname + ", position_name="
				+ position_name + ", in_Time=" + in_Time + ", out_Time=" + out_Time + ", status_Id=" + status_Id + "]";
	}
	
}//class