package kr.co.sist.user.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.user.dao.CareerDAO;
import kr.co.sist.user.vo.CareerVO;

/**
 * 사원 경력(Career) 정보 관련 비즈니스 로직 처리 클래스
 */
public class CareerService {

    private CareerDAO careerDAO = CareerDAO.getInstance();

    /**
     * 경력 정보 추가
     */
    public boolean addCareer(CareerVO vo) {
        try {
            return careerDAO.insertCareer(vo) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 경력 정보 수정
     */
    public int modifyCareer(CareerVO vo) throws SQLException {
        return careerDAO.updateCareer(vo);
    }

    /**
     * 경력 정보 삭제 (career_id 기준)
     */
    public boolean deleteCareerById(int careerId) {
        try {
            return careerDAO.deleteCareer(careerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 특정 사원의 경력 목록 조회 (empno 기준)
     */
    public List<CareerVO> getCareerListByEmpno(int empno) throws SQLException {
        return careerDAO.selectByEmpno(empno);
    }
    
}//class
