package kr.co.sist.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.vo.CertVO;

/**
 * 자격증 정보(Certificates) 관련 DB 접근 객체
 */
public class CertDAO {
	private static CertDAO cDAO;

	private CertDAO() {}

	/**
	 * CertDAO 싱글톤 인스턴스 반환
	 */
	public static CertDAO getInstance() {
		if (cDAO == null) {
			cDAO = new CertDAO();
		}
		return cDAO;
	}

	/**
	 * 자격증 정보 추가
	 */
	public boolean insertCertificate(CertVO vo) throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection db = DbConnection.getInstance();

	    try {
	        con = db.getConn();
	        String sql = "INSERT INTO certificates (cert_id, empno, cert_name, issuer, acq_date, exp_date, input_date) "
	                   + "VALUES (cert_seq.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
	        pstmt = con.prepareStatement(sql, new String[] { "cert_id" });

	        pstmt.setInt(1, vo.getEmpno());
	        pstmt.setString(2, vo.getCertName());
	        pstmt.setString(3, vo.getIssuer());
	        pstmt.setDate(4, vo.getAcqDate());
	        pstmt.setDate(5, vo.getExpDate());

	        int result = pstmt.executeUpdate();

	        if (result > 0) {
	            rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                vo.setCert_id(rs.getInt(1)); // 🔥 id도 세팅
	            }
	        }

	        return result > 0;
	    } finally {
	        db.closeDB(rs, pstmt, con);
	    }
	}


	/**
	 * 자격증 정보 수정 (cert_id 기준)
	 */
	public int updateCertificate(CertVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();

		try {
			con = dbConn.getConn();
			String sql = "UPDATE certificates SET cert_name = ?, issuer = ?, acq_date = ?, exp_date = ? WHERE cert_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getCertName());
			pstmt.setString(2, vo.getIssuer());
			pstmt.setDate(3, vo.getAcqDate());
			pstmt.setDate(4, vo.getExpDate());
			pstmt.setInt(5, vo.getCert_id());

			return pstmt.executeUpdate();
		} finally {
			dbConn.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 자격증 정보 삭제 (cert_id 기준)
	 */
	public boolean deleteCertificate(int certId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection db = DbConnection.getInstance();

		try {
			con = db.getConn();
			String sql = "DELETE FROM certificates WHERE cert_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, certId);

			int result = pstmt.executeUpdate();
			return result > 0;
		} finally {
			db.closeDB(null, pstmt, con);
		}
	}

	/**
	 * 특정 사원의 자격증 목록 조회 (empno 기준)
	 */
	public List<CertVO> selectByEmpno(int empno) throws SQLException {
		List<CertVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DbConnection.getInstance().getConn();
			String sql = "SELECT cert_id, cert_name, issuer, acq_date, exp_date FROM certificates WHERE empno = ? ORDER BY acq_date DESC";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, empno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CertVO vo = new CertVO();
				vo.setCert_id(rs.getInt("cert_id"));
				vo.setCertName(rs.getString("cert_name"));
				vo.setIssuer(rs.getString("issuer"));
				vo.setAcqDate(rs.getDate("acq_date"));
				vo.setExpDate(rs.getDate("exp_date"));
				list.add(vo);
			}
		} finally {
			DbConnection.getInstance().closeDB(rs, pstmt, con);
		}

		return list;
	}
	
}//class
