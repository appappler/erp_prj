package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.admin.dao.AppointmentDAO;
import kr.co.sist.admin.vo.AppointmentVO;

/**
 * 인사 발령(Appointment / Personnel) 관련 비즈니스 로직 처리 클래스
 */
public class AppointmentService {

    private AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    /**
     * 인사 발령 정보 추가
     */
    public boolean addAppointment(AppointmentVO vo) {
        try {
            appointmentDAO.insertAppointment(vo);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 인사 발령 정보 수정
     */
    public int modifyAppointment(AppointmentVO vo) throws SQLException {
        return appointmentDAO.updateAppointment(vo);
    }

    /**
     * 인사 발령 정보 삭제 (personnel_id 기준)
     */
    public boolean deleteAppointment(int appointmentId) {
        try {
            return appointmentDAO.deleteAppointment(appointmentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 특정 사원의 인사 발령 목록 조회 (empno 기준)
     */
    public List<AppointmentVO> getAppointmentListByEmpno(int empno) throws SQLException {
        return appointmentDAO.selectByEmpno(empno);
    }
    
}//class
