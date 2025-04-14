package kr.co.sist.admin.vo;

import java.sql.Date;

/**
 * 
 */
public class TrainingVO {

	private int training_id;
	private int empno;
	private String institution;
	private String trainingName;
	private Date startDate;
	private Date endDate;
	private String complete;
	
	public TrainingVO() {
		super();
	}
	
	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public TrainingVO(int training_id, int empno, String institution, String trainingName, Date startDate, Date endDate,
			String complete) {
		super();
		this.training_id = training_id;
		this.empno = empno;
		this.institution = institution;
		this.trainingName = trainingName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.complete = complete;
	}

	public int getTraining_id() {
		return training_id;
	}
	public void setTraining_id(int training_id) {
		this.training_id = training_id;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}

	@Override
	public String toString() {
		return "TrainingVO [training_id=" + training_id + ", empno=" + empno + ", institution=" + institution
				+ ", trainingName=" + trainingName + ", startDate=" + startDate + ", endDate=" + endDate + ", complete="
				+ complete + "]";
	}
	
}//class
