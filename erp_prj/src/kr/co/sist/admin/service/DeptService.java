package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.admin.dao.DeptDAO;
import kr.co.sist.admin.vo.DeptVO;

/**
 * 비즈니스 로직 처리 및 흐름 제어, DAO를 호출해 DB 작업 처리
 */
public class DeptService {

	public boolean addDept(DeptVO dVO) {
		boolean flag = false;

		DeptDAO dDAO = DeptDAO.getInstance();

		try {
			dDAO.insertDept(dVO);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch

		return flag;
	}// addDept

	public boolean modifyDept(DeptVO dVO) {
		boolean flag = false;
		DeptDAO dDAO = DeptDAO.getInstance();

		try {
			flag = dDAO.updateDept(dVO) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}// modifyDept

	public boolean removeDept(int num) {
		boolean flag = false;
		DeptDAO dDAO = DeptDAO.getInstance();

		try {
			flag = dDAO.deleteDept(num) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch

		return flag;
	}// removeDept

	public List<DeptVO> searchAllDept(String deptName) {
		List<DeptVO> list = null;
		DeptDAO dDAO = DeptDAO.getInstance();
		try {
			if (deptName == null || deptName.trim().isEmpty()) {
				list = dDAO.selectAllDept();
			}else {
				list = dDAO.selectOneDept(deptName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch

		return list;
	}// searchAllDept
	
	public List<DeptVO> searchEmp(String deptName) {
		List<DeptVO> list = null;
		DeptDAO dDAO = DeptDAO.getInstance();
		try {
				list = dDAO.selectOneEmp(deptName);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
		
		return list;
	}// searchAllDept
	
//	public DeptVO searchOneDept(int num) {
//		DeptVO dVO = null;
//		DeptDAO dDAO = DeptDAO.getInstance();
//
//		try {
//			dVO = dDAO.selectOneDept(num);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} // end catch
//
//		return dVO;
//	}// searchOneDept
	
	public boolean NumExists(int deptNum) {
		DeptDAO dDAO = DeptDAO.getInstance();
		return dDAO.deptNumExists(deptNum);
	}//NumExists
	
	public boolean NameExists(String deptName) {
		DeptDAO dDAO = DeptDAO.getInstance();
		return dDAO.deptNameExists(deptName);
	}//NameExists
	
	public boolean TelExists(String contact) {
		DeptDAO dDAO = DeptDAO.getInstance();
		return dDAO.deptTelExists(contact);
	}//NameExists
	
	public boolean TelExistsExceptSelf(String contact, int deptNum) {
		DeptDAO dDAO = DeptDAO.getInstance();
		return dDAO.deptTelExistsExceptSelf(contact,deptNum);
	}//TelExistsExceptSelf
	
	public boolean NameExistsExceptSelf(String deptName, int deptNum) {
		DeptDAO dDAO = DeptDAO.getInstance();
		return dDAO.deptNameExistsExceptSelf(deptName,deptNum);
	}// NameExistsExceptSelf

}// class
