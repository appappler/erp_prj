package kr.co.sist.admin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.dao.ManagerDeptFileDAO;
import kr.co.sist.user.vo.DeptFileVO;



public class ManagerDeptFileService {
	
	public ManagerDeptFileService() {
		
	}
	
	 public List<DeptFileVO> showManagerFile() {
	        List<DeptFileVO> list = new ArrayList<DeptFileVO>();
	       
	        ManagerDeptFileDAO mdfDAO = ManagerDeptFileDAO.getInStance();
	        
	        
	        try {
	            list = mdfDAO.selectManagerFile();
	        } catch (SQLException e) {
	            System.out.println("문서 조회 실패");
	            e.printStackTrace();
	        }
	        return list;
	    }//select
	
	  public List<DeptFileVO> searchManagerFile(String criteria) {
		  List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		  ManagerDeptFileDAO mdfDAO = ManagerDeptFileDAO.getInStance();
		  
		  try {
			list = mdfDAO.searchManagerFile(criteria);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		  return list;
	  }//searchAllfile
	  
	  
	 public List<DeptFileVO> sortManagerFile(String option){
		 List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		 ManagerDeptFileDAO mdfDAO = ManagerDeptFileDAO.getInStance();
		 
		 try {
			list = mdfDAO.docuManagerFileSort(option);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 return list;
	 }
	
}
