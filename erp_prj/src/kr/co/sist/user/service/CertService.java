package kr.co.sist.user.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.user.dao.CertDAO;
import kr.co.sist.user.vo.CertVO;

/**
 * 자격증(Certificates) 관련 비즈니스 로직 처리 클래스
 */
public class CertService {
    private CertDAO certDAO = CertDAO.getInstance();

    /**
     * 자격증 정보 추가
     */
    public boolean addCertificate(CertVO cVO) {
        try {
            return certDAO.insertCertificate(cVO) ;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 자격증 정보 수정
     */
    public int modifyCertificate(CertVO cVO) throws SQLException {
        return certDAO.updateCertificate(cVO);
    }

    /**
     * 자격증 정보 삭제 (PK 기준)
     */
    public boolean deleteCertificateById(int certId) {
        try {
            return certDAO.deleteCertificate(certId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 특정 사원의 자격증 목록 조회 (empno 기준)
     */
    public List<CertVO> getCertListByEmpno(int empno) throws SQLException {
        return certDAO.selectByEmpno(empno);
    }

}//class
