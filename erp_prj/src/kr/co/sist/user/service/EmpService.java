package kr.co.sist.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import kr.co.sist.user.dao.AppointmentDAO;
import kr.co.sist.user.dao.EmpDAO;
import kr.co.sist.user.vo.AppointmentVO;
import kr.co.sist.user.vo.EmpVO;

/**
 * 
 */
public class EmpService {

    // 사원 추가
	public boolean addEmployee(EmpVO eVO) {
	    try {
	        int empno = EmpDAO.getInstance().insertEmployee(eVO);
	        if (empno > 0) {
	            eVO.setEmpno(empno); // VO에 사번 설정

	            // ✅ 입사 인사내역 자동 추가
	            AppointmentVO appointVO = new AppointmentVO();
	            appointVO.setEmpno(empno);
	            appointVO.setAppointment("입사");
	            appointVO.setAppointmentDate(eVO.getHireDate());
	            appointVO.setDeptno(Integer.parseInt(eVO.getDept()));
	            appointVO.setPositionId(Integer.parseInt(eVO.getPosition()));

	            boolean inserted = AppointmentDAO.getInstance().insertAppointment(appointVO);
	            if (!inserted) {
	                System.err.println("[경고] 인사내역 자동 등록 실패 (입사)");
	            }

	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

    // 사원 정보 수정
    public int modifyEmployee(EmpVO eVO) throws SQLException {
    	EmpDAO empDAO = EmpDAO.getInstance();

        return empDAO.updateEmployee(eVO);
    }
    
 // EmpService.java
    public boolean modifyEmployeeContact(EmpVO vo) throws SQLException {
        return EmpDAO.getInstance().updateContactInfo(vo) > 0;
    }

    // 사원 삭제
    public int removeEmployee(int empno) throws SQLException {
    	EmpDAO empDAO = EmpDAO.getInstance();

        return empDAO.deleteEmployee(empno);
    }

    // 전체 사원 조회
    public List<EmpVO> searchAllEmployees(String dept, String position, String name)  {
    	EmpDAO empDAO = EmpDAO.getInstance();
    	List<EmpVO> list= null;

        try {
			list= empDAO.selectAllEmployees(dept, position, name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return list;
    }
    
    public List<String> getAllDeptNames() throws SQLException {
        return EmpDAO.getInstance().selectAllDeptNames();
    }

    public List<String> getAllPositionNames() throws SQLException {
        return EmpDAO.getInstance().selectAllPositionNames();
    }
    
    public int getDeptnoByName(String deptName) {
        try { 
            return EmpDAO.getInstance().getDeptnoByName(deptName);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getPositionIdByName(String positionName) {
        try {
            return EmpDAO.getInstance().getPositionIdByName(positionName);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public EmpVO getEmployeeByEmpno(int empno) throws SQLException, IOException {
        return EmpDAO.getInstance().selectByEmpno(empno);
    }
    
    public boolean updateEmpPartial(EmpVO vo) {
        try {
            int deptno = getDeptnoByName(vo.getDept());
            int positionId = getPositionIdByName(vo.getPosition());
            return EmpDAO.getInstance().updateEmpPartial(vo, deptno, positionId) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
 // 현재 비밀번호 확인
    public boolean checkPassword(int empno, String inputPw) {
        try {
            String currentPw = EmpDAO.getInstance().getPassword(empno);
            return inputPw.equals(currentPw); // 추후 보안을 위해 hash 비교 권장
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 비밀번호 변경
    public boolean changePassword(int empno, String newPw) {
        try {
            return EmpDAO.getInstance().updatePassword(empno, newPw) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}//class