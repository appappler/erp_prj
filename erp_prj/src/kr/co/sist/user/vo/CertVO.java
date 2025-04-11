package kr.co.sist.user.vo;
import java.sql.Date;

/**
 * 
 */
public class CertVO {

	private int cert_id;
	private int empno;
	private String certName;
	private String issuer;
	private Date acqDate;
	private Date expDate;
	
	public CertVO() {
		super();
	}
	
	public CertVO(int cert_id, int empno, String certName, String issuer, Date acqDate, Date expDate) {
		super();
		this.cert_id = cert_id;
		this.empno = empno;
		this.certName = certName;
		this.issuer = issuer;
		this.acqDate = acqDate;
		this.expDate = expDate;
	}
	
	public int getCert_id() {
		return cert_id;
	}
	public void setCert_id(int cert_id) {
		this.cert_id = cert_id;
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public Date getAcqDate() {
		return acqDate;
	}
	public void setAcqDate(Date acqDate) {
		this.acqDate = acqDate;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	@Override
	public String toString() {
		return "CertVO [cert_id=" + cert_id + ", empno=" + empno + ", certName=" + certName + ", issuer=" + issuer
				+ ", acqDate=" + acqDate + ", expDate=" + expDate + "]";
	}
	
}//class