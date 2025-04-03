package csj_vo;

import java.sql.Timestamp;
import java.util.Date;

public class AttendanceVO {
    private int attId;
    private int empNo;
    private int statusId;
    private Timestamp inTime;
    private Timestamp outTime;
    private double workHours;
    private String remarks;
    private Date inputDate;

    public AttendanceVO() {}

    public AttendanceVO(int attId, int empNo, int statusId, Timestamp inTime, Timestamp outTime, 
                        double workHours, String remarks, Date inputDate) {
        this.attId = attId;
        this.empNo = empNo;
        this.statusId = statusId;
        this.inTime = inTime;
        this.outTime = outTime;
        this.workHours = workHours;
        this.remarks = remarks;
        this.inputDate = inputDate;
    }

    public int getAttId() { return attId; }
    public void setAttId(int attId) { this.attId = attId; }
    
    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }
    
    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }
    
    public Timestamp getInTime() { return inTime; }
    public void setInTime(Timestamp inTime) { this.inTime = inTime; }
    
    public Timestamp getOutTime() { return outTime; }
    public void setOutTime(Timestamp outTime) { this.outTime = outTime; }

    public double getWorkHours() { return workHours; }
    public void setWorkHours(double workHours) { this.workHours = workHours; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Date getInputDate() { return inputDate; }
    public void setInputDate(Date inputDate) { this.inputDate = inputDate; }
}