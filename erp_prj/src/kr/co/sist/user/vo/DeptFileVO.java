package kr.co.sist.user.vo;

import java.sql.Date;
import java.util.Objects;

public class DeptFileVO {
	private int num; // 문서번호(doc_id)
	private int empId; // 사원번호(emp_id) - DB 저장용
	private String empName; // 사원명(ename) - 화면 출력용
	private String deptName;
	private Date inputDate; // 등록일(upload_date)
	private String fileName; // 파일명(doc_name)
	private String criteria; // 검색 키워드
	private byte[] fileData; // 파일 데이터(blob)

	private int docID;
	private int deptID;
	private Date shareData;

	
	public DeptFileVO(int num, int empId, String empName, String deptName, Date inputDate, String fileName,
			String criteria, byte[] fileData, int docID, int deptID, Date shareData) {
		super();
		this.num = num;
		this.empId = empId;
		this.empName = empName;
		this.deptName = deptName;
		this.inputDate = inputDate;
		this.fileName = fileName;
		this.criteria = criteria;
		this.fileData = fileData;
		this.docID = docID;
		this.deptID = deptID;
		this.shareData = shareData;
	}

	@Override
	public String toString() {
		return deptName;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof DeptFileVO) {
	        DeptFileVO other = (DeptFileVO) obj;
	        return this.docID == other.docID && this.deptID == other.deptID;
	    }
	    return false;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(docID, deptID);
	}
	

	public DeptFileVO() {
	}

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public Date getShareData() {
		return shareData;
	}

	public void setShareData(Date shareData) {
		this.shareData = shareData;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}