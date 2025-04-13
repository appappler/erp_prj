package kr.co.sist.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kr.co.sist.user.vo.UserAccountVO;

public class UserLoginDAO {
    
    private static UserLoginDAO alDAO;
    
    private UserLoginDAO() {
    }
    
    public static UserLoginDAO getInstance() {
        if(alDAO == null) {
            alDAO = new UserLoginDAO();
        }
        return alDAO;
    }
    
    public boolean authenticateOfInput(UserAccountVO inputVO) throws SQLException {
        List<UserAccountVO> targetAccounts = new ArrayList<UserAccountVO>();
        UserLoginDAO dao = UserLoginDAO.getInstance();
        targetAccounts = dao.selectAllAccount();
        
        boolean authenticationOfId = false;
        boolean authenticationOfPass = false;
        boolean authenticationFlag = false;
        
        System.out.println("현재 employee 테이블에 존재하는 row수 : " + dao.selectAccountCnt());
        
        for (int i = 0; i < dao.selectAccountCnt(); i++) {
            authenticationOfId = inputVO.getUserId().equals(targetAccounts.get(i).getUserId());
            authenticationOfPass = inputVO.getUserPass().equals(targetAccounts.get(i).getUserPass());
            authenticationFlag = authenticationOfId && authenticationOfPass;
            if(authenticationFlag) {
                break;
            }
        }
        return authenticationFlag;
    }
    
    public List<UserAccountVO> selectAllAccount() throws SQLException {
        List<UserAccountVO> list = new ArrayList<UserAccountVO>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DbConnection dbCon = DbConnection.getInstance();
        
        try {
            con = dbCon.getConn();
            StringBuilder selectAccount = new StringBuilder();
            // emp_name 컬럼을 추가하여 사원이름도 함께 조회
            selectAccount.append(" SELECT empno, emp_name, password ");
            selectAccount.append(" FROM employee ");
            
            pstmt = con.prepareStatement(selectAccount.toString());
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                UserAccountVO dbVO = new UserAccountVO();
                dbVO.setUserId(rs.getString("empno"));
                dbVO.setUserPass(rs.getString("password"));
                dbVO.setEmpName(rs.getString("emp_name"));
                list.add(dbVO);
            }
        } finally {
            dbCon.closeDB(rs, pstmt, con);
        }
        return list;
    }
    
    public int selectAccountCnt() throws SQLException {
        int cnt = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DbConnection dbCon = DbConnection.getInstance();
        
        try {
            con = dbCon.getConn();
            StringBuilder selectCntMember = new StringBuilder();
            selectCntMember.append(" SELECT count(empno) cnt ");
            selectCntMember.append(" FROM employee ");
            
            pstmt = con.prepareStatement(selectCntMember.toString());
            rs = pstmt.executeQuery();
            if(rs.next()) {
                cnt = rs.getInt("cnt");
            }
        } finally {
            dbCon.closeDB(rs, pstmt, con);
        }
        return cnt;
    }
    
    // 추가된 메서드: 사원번호로 계정 정보를 조회 (emp_name 포함)
    public UserAccountVO getAccountById(String empno) throws SQLException {
        UserAccountVO account = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DbConnection dbCon = DbConnection.getInstance();
        try {
            con = dbCon.getConn();
            String sql = "SELECT empno, emp_name, password FROM employee WHERE empno = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, empno);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                account = new UserAccountVO();
                account.setUserId(rs.getString("empno"));
                account.setUserPass(rs.getString("password"));
                account.setEmpName(rs.getString("emp_name"));
            }
        } finally {
            dbCon.closeDB(rs, pstmt, con);
        }
        return account;
    }
    
    public static void main(String[] args) {
        UserLoginDAO dao = UserLoginDAO.getInstance();
        try {
            dao.selectAllAccount();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
