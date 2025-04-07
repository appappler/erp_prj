package vo;

public class AttVO {
    private int attId;         // 근태 고유번호
    private int empNo;         // 사원번호
    private String empName;    // 사원명
    private String deptName;   // 부서명
    private String positionName; // 직급명
    private String inTime;     // 출근시간
    private String outTime;    // 퇴근시간
    private String statusId;   // 근태 상태

    public AttVO() {}

    public AttVO(int attId, int empNo, String empName, String deptName, String positionName,
                        String inTime, String outTime, String statusId) {
        this.attId = attId;
        this.empNo = empNo;
        this.empName = empName;
        this.deptName = deptName;
        this.positionName = positionName;
        this.inTime = inTime;
        this.outTime = outTime;
        this.statusId = statusId;
    }

    // Getter/Setter
    public int getAttId() {
        return attId;
    }
    public void setAttId(int attId) {
        this.attId = attId;
    }
    public int getEmpNo() {
        return empNo;
    }
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
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
    public String getInTime() {
        return inTime;
    }
    public void setInTime(String inTime) {
        this.inTime = inTime;
    }
    public String getOutTime() {
        return outTime;
    }
    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }
    public String getStatusId() {
        return statusId;
    }
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
