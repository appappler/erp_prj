package kr.co.sist.user.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.co.sist.user.dao.ShareDeptFileDAO;
import kr.co.sist.user.vo.DeptFileVO;


public class ShareDeptFileService {
	
	public ShareDeptFileService() {
		
	}
	
	//공유 테이블에 추가
	public boolean addShareTable(DeptFileVO dfVO) {
		boolean flag = false;
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();

		try {
			sdfDAO.insertoneShareTable(dfVO);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}//addshare
	
	
	
	//공유테이블 출력
	public List<DeptFileVO> showShareTable(int userId){
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		try {
			list = sdfDAO.selectAllShareFile(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return list;
	}//showsharetable
	
	//문서찾기
	public List<DeptFileVO> serachShareTable(String search, int userId){
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		try {
			list = sdfDAO.searchShareFile(search, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}//searchShareTable
	
	public List<DeptFileVO> sortShareTable(String options, int userId){
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		try {
			list = sdfDAO.shareFileSort(options, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}//sortShareTable
	
	public boolean deleteShare(int num) {
		boolean flag = false;
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		try {
			sdfDAO.deleteShareTable(num);
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return flag;
	}
	
	public List<DeptFileVO> loadAlreadyShareList(int docId){
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		try {
			list = sdfDAO.getSharedDeptsByDocId(docId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean deleteAlreadyShare(DeptFileVO dfVO) {
		boolean flag = false;
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		try {
			sdfDAO.deleteShareByDocAndDept(dfVO);
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	public Set<Integer> allSharedDocids(){
		Set<Integer> set = new HashSet<Integer>();
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		set = sdfDAO.getAllSharedDocIds();
		
		return set;
	}
	
	public List<DeptFileVO> getAllDepartment(int senderDept){
		List<DeptFileVO> list = new ArrayList<DeptFileVO>();
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		
		try {
			list = sdfDAO.getAllDepartments(senderDept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
}
