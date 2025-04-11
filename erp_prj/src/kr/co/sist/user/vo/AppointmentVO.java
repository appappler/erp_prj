package kr.co.sist.user.vo;

import java.sql.Date;

/**
 * 
 */
public class AppointmentVO {

    private int appoint_id;
    private int empno;
    private String appointment;
    private Date appointmentDate;
    private Integer deptno;        // 변경 전: int → 변경 후: Integer
    private Integer positionId;    // 변경 전: int → 변경 후: Integer

    // 조회용 이름 필드 추가
    private String deptName;
    private String positionName;

    public AppointmentVO() {
        super();
    }


    public AppointmentVO(int appoint_id, int empno, String appointment, Date appointmentDate, Integer deptno,
			Integer positionId, String deptName, String positionName) {
		super();
		this.appoint_id = appoint_id;
		this.empno = empno;
		this.appointment = appointment;
		this.appointmentDate = appointmentDate;
		this.deptno = deptno;
		this.positionId = positionId;
		this.deptName = deptName;
		this.positionName = positionName;
	}


	public int getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(int appoint_id) {
        this.appoint_id = appoint_id;
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

 // getter/setter도 int → Integer로 변경
    public Integer getDeptno() { return deptno; }
    public void setDeptno(Integer deptno) { this.deptno = deptno; }

    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "AppointmentVO [appoint_id=" + appoint_id + ", empno=" + empno + ", appointment=" + appointment
                + ", appointmentDate=" + appointmentDate + ", deptno=" + deptno + ", positionId=" + positionId
                + ", deptName=" + deptName + ", positionName=" + positionName + "]";
    }

}//class