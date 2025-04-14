package kr.co.sist.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.user.dao.EmpDeptFileDAO;
import kr.co.sist.user.vo.DeptFileVO;



public class EmpDeptFileService {
	
	public EmpDeptFileService() {
		
	}
	
	public boolean addDocumentFile(DeptFileVO dfVO) {
		boolean flag = false;
		EmpDeptFileDAO edfDAO = EmpDeptFileDAO.getInstance();
		
		try {
			edfDAO.insertDocuTable(dfVO);
			flag= true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}//addDocumentFile
	
	  public List<DeptFileVO> showAllFile() {
	        List<DeptFileVO> list = new ArrayList<DeptFileVO>();
	        EmpDeptFileDAO edfDAO = EmpDeptFileDAO.getInstance();
	        try {
	            list = edfDAO.selectAllFile();
	        } catch (SQLException e) {
	            System.out.println("문서 조회 실패");
	            e.printStackTrace();
	        }
	        return list;
	    }//select
	  
	  public boolean removeFile(int num) {
		  boolean flag = false;
		  
		  EmpDeptFileDAO edfDAO = EmpDeptFileDAO.getInstance();
		  try {
			edfDAO.deleteFile(num);
			flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return flag;
	  }//removeFile
	  
	  public List<DeptFileVO> searchAllFile(String criteria) {
		  List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		
		  EmpDeptFileDAO epfDAO = EmpDeptFileDAO.getInstance();
		  try {
			list = epfDAO.searchFile(criteria);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		  return list;
	  }//searchAllfile
	  
	  public List<DeptFileVO> sortAllFile(String option){
		  List<DeptFileVO> sortList = new ArrayList<DeptFileVO>();
		  EmpDeptFileDAO epfDAO = EmpDeptFileDAO.getInstance();
		  
		  try {
			sortList = epfDAO.docuFileSort(option);
		} catch (SQLException e) {
			e.printStackTrace();
		}//sortallfile
		  
		  return sortList;
	  }//sortAllfile
	  
	  public byte[] getFileBlob(int docId) {
		    byte[] fileData = null;
		    EmpDeptFileDAO dao = EmpDeptFileDAO.getInstance();
		    try {
		        fileData = dao.getFileBlob(docId);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return fileData;
		    
		}//blob
	  
	  public boolean isEmpExists(int empId) {
		    try {
		        return EmpDeptFileDAO.getInstance().isEmpExists(empId);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}//isEmpExists
	  
	  public int getDeptIdByName(String deptName) {
		  EmpDeptFileDAO edfDAO = EmpDeptFileDAO.getInstance(); 
		 int deptId = 0;
		try {
			deptId = edfDAO.findDeptIdByName(deptName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  return deptId;
	  }
}
