package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.admin.dao.EduDAO;
import kr.co.sist.admin.vo.EduVO;

/**
 * 
 */
public class EduService {

    // 교육 과정 추가
    public void addEducation(EduVO eVO) throws SQLException {  // 수정: EduVO 사용
        EduDAO.getInstance().insertEdu(eVO);
    }

 // EduService.java에 추가
    public boolean updateEducation(EduVO vo) {
        try {
            return EduDAO.getInstance().updateEducation(vo);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<EduVO> getEduListByEmpno(int empno) throws SQLException {
        return EduDAO.getInstance().selectByEmpno(empno);
    }

    
    public boolean deleteEducation(int eduId) throws SQLException {
        return EduDAO.getInstance().deleteEducation(eduId);
    }

}//class