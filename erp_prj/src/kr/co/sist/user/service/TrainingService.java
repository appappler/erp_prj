package kr.co.sist.user.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.user.dao.TrainingDAO;
import kr.co.sist.user.vo.TrainingVO;

/**
 * 사원 교육 이력(Training) 관련 비즈니스 로직 처리 클래스
 */
public class TrainingService {

    private TrainingDAO trainingDAO = TrainingDAO.getInstance();

    /**
     * 교육 이력 추가
     */
    public boolean addTraining(TrainingVO vo) {
        try {
            return trainingDAO.insertTraining(vo);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 교육 이력 수정 (training_id 기준)
     */
    public int modifyTraining(TrainingVO vo) throws SQLException {
        return trainingDAO.updateTraining(vo);
    }

    /**
     * 교육 이력 삭제 (training_id 기준)
     */
    public boolean deleteTrainingById(int trainingId) {
        try {
            return trainingDAO.deleteTraining(trainingId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 특정 사원의 교육 이력 목록 조회 (empno 기준)
     */
    public List<TrainingVO> getTrainingListByEmpno(int empno) throws SQLException {
        return trainingDAO.selectByEmpno(empno);
    }

}//class
