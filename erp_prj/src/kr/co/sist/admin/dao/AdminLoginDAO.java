package kr.co.sist.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.AdminAccountVO;

/**
 * 
 */
public class AdminLoginDAO {
	
	private static AdminLoginDAO alDAO;
	
	private AdminLoginDAO() {
		
	}//AdminLoginDAO
	
	public static AdminLoginDAO getInstance() {
		
		if(alDAO == null) {
			alDAO = new AdminLoginDAO();
		}//end if
		return alDAO;
	}//getInstance
	
	public boolean authenticateOfInput(AdminAccountVO inputVO) throws SQLException {
		List<AdminAccountVO> targetAccounts = new ArrayList<AdminAccountVO>();

		AdminLoginDAO alDAO = AdminLoginDAO.getInstance();
		targetAccounts= alDAO.selectAllAccount();
		
		boolean authenticationOfId = false;
		boolean authenticationOfPass = false;
		boolean authenticationFlag = false;
		
		System.out.println();
		System.out.println("현재 admin_account 테이블에 존재하는 row수 : "+alDAO.selectAccountCnt());
		System.out.println();
		
		for(int i=0; i<alDAO.selectAccountCnt(); i++) {
			authenticationOfId=inputVO.getAdminId().equals(targetAccounts.get(i).getAdminId());
			authenticationOfPass=inputVO.getAdminPass().equals(targetAccounts.get(i).getAdminPass());
			authenticationFlag = authenticationOfId&&authenticationOfPass;
			
			if( authenticationFlag ) {
				break;
			}//end if
			
		}//end for
		
		return authenticationFlag;
		
	}//authenticateOfInput
	
	public List<AdminAccountVO> selectAllAccount() throws SQLException{
		
		List<AdminAccountVO> list = new ArrayList<AdminAccountVO>();
		
		//1. 드라이버 로딩
		//2. 커넥션 얻기
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con=dbCon.getConn();
			StringBuilder selectAccount = new StringBuilder();
			selectAccount
			.append("	select 	admin_id, password	")
			.append("	from 	admin_account		")
			;
			pstmt=con.prepareStatement(selectAccount.toString());
			
			//4. 바인딩변수 없음
			//5. 쿼리문수행후 결과얻기
			rs=pstmt.executeQuery();//커서의 제어권 얻은 리절트셋
			AdminAccountVO dbVO = null;
			while(rs.next()) {
				dbVO = new AdminAccountVO();
				dbVO.setAdminId(rs.getString("admin_id"));
				dbVO.setAdminPass(rs.getString("password"));
				list.add(dbVO);
			}//end while
			
		}finally {
			dbCon.closeDB(rs, pstmt, con);
		}//end finally 
		
		return list;//그리고 서비스로 가서~
	}//selectAllAccount
	
	public void selectFromVO() {
		
	}//selectFromVO
	
	public int selectAccountCnt() throws SQLException{
		int cnt=0;
		
		//1.드라이버 로딩
		//2.커넥션 얻기
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			//3.쿼리문생성객체 얻기
			con=dbCon.getConn();
			StringBuilder selectCntMember=new StringBuilder();
			selectCntMember
			.append("	select	count(admin_id) cnt	")
			.append("	from	admin_account 		");
			
			pstmt=con.prepareStatement(selectCntMember.toString());
			//4. 바인드변수 없으니까~~~
			//5.쿼리문 수행 후 결과얻기
			rs=pstmt.executeQuery();
			if( rs.next() ) {
				cnt=rs.getInt("cnt");
			}//end if
		
		}finally {
			//6.연결끊기!!!!!
			dbCon.closeDB(rs, pstmt, con);
		}//end finally
		
		return cnt;
	}//selectAccountCnt
	
	public static void main(String[] args) {
		
		AdminLoginDAO alDAO = AdminLoginDAO.getInstance();
		try {
			alDAO.selectAllAccount();
		}catch (SQLException se) {
			se.printStackTrace();
		}//end catch
		
	}//main

}//class
