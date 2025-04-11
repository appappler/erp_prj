package kr.co.sist.admin.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.admin.vo.EmpVO;

/**
 * 
 */
public class EmpDAO {

    private static EmpDAO eDAO;

    private EmpDAO() {}

    public static EmpDAO getInstance() {
        if (eDAO == null) {
            eDAO = new EmpDAO();
        }
        return eDAO;
    }

    public int insertEmployee(EmpVO eVO) throws SQLException {
        int generatedEmpno = -1;

        String sql = "INSERT INTO employee " +
                "(deptno, emp_name, position_id, birthdate, contact, email, address, img, img_name, hire_date, input_date, emp_status, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, ?)";

        try (
            Connection con = DbConnection.getInstance().getConn();
            // 🔸 사번을 자동 생성하는 트리거가 있으므로 empno는 입력하지 않음
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"empno"})
        ) {
            pstmt.setInt(1, Integer.parseInt(eVO.getDept()));
            pstmt.setString(2, eVO.getEname());
            pstmt.setInt(3, Integer.parseInt(eVO.getPosition()));
            pstmt.setDate(4, eVO.getBirthDate());
            pstmt.setString(5, eVO.getTel());
            pstmt.setString(6, eVO.getEmail());
            pstmt.setString(7, eVO.getAddress());
            pstmt.setBlob(8, eVO.getImgInputStream());
            pstmt.setString(9, eVO.getImgName());
            pstmt.setDate(10, eVO.getHireDate());
            pstmt.setString(11, eVO.getWorkingFlag());
            pstmt.setString(12, eVO.getPassword());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedEmpno = rs.getInt(1);
                        eVO.setEmpno(generatedEmpno); // ✅ VO에도 세팅
                    }
                }
            }
        }

        return generatedEmpno;
    }



    
    /** 사원 정보 전체 수정 */
    public int updateEmployee(EmpVO eVO) throws SQLException {
        String sql = "UPDATE employee SET deptno=?, emp_name=?, position_id=?, birthdate=?, contact=?, email=?, address=?, img_name=?, hire_date=?, emp_status=? WHERE empno=?";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, eVO.getDept());
            pstmt.setString(2, eVO.getEname());
            pstmt.setString(3, eVO.getPosition());
            pstmt.setDate(4, eVO.getBirthDate());
            pstmt.setString(5, eVO.getTel());
            pstmt.setString(6, eVO.getEmail());
            pstmt.setString(7, eVO.getAddress());
            pstmt.setString(8, eVO.getImgName());
            pstmt.setDate(9, eVO.getHireDate());
            pstmt.setString(10, eVO.getWorkingFlag());
            pstmt.setInt(11, eVO.getEmpno());

            return pstmt.executeUpdate();
        }
    }

    /** 사원 삭제 */
    public int deleteEmployee(int empno) throws SQLException {
        String sql = "DELETE FROM employee WHERE empno=?";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, empno);
            return pstmt.executeUpdate();
        }
    }
    
    /** 사원 전체 조회 - 조건 필터링 포함 */
    public List<EmpVO> selectAllEmployees(String dept, String position, String name) throws SQLException {
        List<EmpVO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT e.empno, e.emp_name, d.deptname, p.position_name, e.birthdate, e.contact, e.email, e.emp_status " +
            "FROM employee e " +
            "JOIN department d ON e.deptno = d.deptno " +
            "JOIN position p ON e.position_id = p.position_id " +
            "WHERE 1=1 "
        );

        if (dept != null && !"전체".equals(dept)) sql.append("AND d.deptname = ? ");
        if (position != null && !"전체".equals(position)) sql.append("AND p.position_name = ? ");
        if (name != null && !name.isBlank() && !"사원명".equals(name)) sql.append("AND e.emp_name LIKE ? ");
        sql.append("ORDER BY e.empno");

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            int index = 1;
            if (dept != null && !"전체".equals(dept)) pstmt.setString(index++, dept);
            if (position != null && !"전체".equals(position)) pstmt.setString(index++, position);
            if (name != null && !name.isBlank() && !"사원명".equals(name)) pstmt.setString(index++, "%" + name + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EmpVO eVO = new EmpVO();
                    eVO.setEmpno(rs.getInt("empno"));
                    eVO.setEname(rs.getString("emp_name"));
                    eVO.setDept(rs.getString("deptname"));
                    eVO.setPosition(rs.getString("position_name"));
                    eVO.setBirthDate(rs.getDate("birthdate"));
                    eVO.setTel(rs.getString("contact"));
                    eVO.setEmail(rs.getString("email"));
                    eVO.setWorkingFlag(rs.getString("emp_status"));
                    list.add(eVO);
                }
            }
        }

        return list;
    }
    
    /** 부서명 전체 조회 */
    public List<String> selectAllDeptNames() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT deptname FROM department ORDER BY deptno";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) list.add(rs.getString("deptname"));
        }
        return list;
    }

    /** 직급명 전체 조회 */
    public List<String> selectAllPositionNames() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT position_name FROM position ORDER BY position_id";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) list.add(rs.getString("position_name"));
        }
        return list;
    }

    /** 부서명으로 deptno 조회 */
    public int getDeptnoByName(String deptName) throws SQLException {
        String sql = "SELECT deptno FROM department WHERE deptname = ?";
        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, deptName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt("deptno");
            }
        }
        return -1;
    }

    /** 직급명으로 position_id 조회 */
    public int getPositionIdByName(String positionName) throws SQLException {
        String sql = "SELECT position_id FROM position WHERE position_name = ?";
        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, positionName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt("position_id");
            }
        }
        return -1;
    }
    
    public EmpVO selectByEmpno(int empno) throws SQLException, IOException {
        EmpVO vo = null;
        String sql = "SELECT e.empno, e.emp_name, e.birthdate, e.contact, e.email, e.address, e.hire_date, " +
                     "e.deptno, d.deptname, e.position_id, p.position_name, " +
                     "e.img_name, e.img, e.password " +
                     "FROM employee e " +
                     "LEFT JOIN department d ON e.deptno = d.deptno " +
                     "LEFT JOIN position p ON e.position_id = p.position_id " +
                     "WHERE e.empno = ?";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setInt(1, empno);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    vo = new EmpVO();
                    vo.setEmpno(rs.getInt("empno"));
                    vo.setEname(rs.getString("emp_name"));
                    vo.setBirthDate(rs.getDate("birthdate"));
                    vo.setTel(rs.getString("contact"));
                    vo.setEmail(rs.getString("email"));
                    vo.setAddress(rs.getString("address"));
                    vo.setHireDate(rs.getDate("hire_date"));

                    // ✅ 부서번호는 필요 없다면 생략 가능
                    // vo.setDept(String.valueOf(rs.getInt("deptno")));
                    vo.setDept(rs.getString("deptname"));  // 부서명 저장
                    vo.setPosition(rs.getString("position_name")); // 직급명 저장

                    vo.setImgName(rs.getString("img_name"));
                    vo.setPassword(rs.getString("password"));

                    Blob imgBlob = rs.getBlob("img");
                    if (imgBlob != null) {
                        try (InputStream is = imgBlob.getBinaryStream();
                             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            while ((bytesRead = is.read(buffer)) != -1) {
                                baos.write(buffer, 0, bytesRead);
                            }
                            vo.setImgBytes(baos.toByteArray()); // ✅ 이미지 byte 저장
                        }
                    }
                    System.out.println("imgBytes length: " + (vo.getImgBytes() != null ? vo.getImgBytes().length : "null"));

                } 
            }
        }
        return vo;
    }

    
    /** 연락처, 이메일, 주소 부분 수정 */
    public int updateContactInfo(EmpVO vo) throws SQLException {
        String sql = "UPDATE employee SET contact = ?, email = ?, address = ? WHERE empno = ?";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, vo.getTel());
            pstmt.setString(2, vo.getEmail());
            pstmt.setString(3, vo.getAddress());
            pstmt.setInt(4, vo.getEmpno());

            return pstmt.executeUpdate();
        }
    }
    
    public int updateEmpPartial(EmpVO vo, int deptno, int positionId) throws SQLException {
        String sql = "UPDATE employee SET contact = ?, email = ?, address = ?, img = ?, img_name = ?, password = ?, deptno = ?, position_id = ? WHERE empno = ?";

        try (
            Connection con = DbConnection.getInstance().getConn();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            pstmt.setString(1, vo.getTel());
            pstmt.setString(2, vo.getEmail());
            pstmt.setString(3, vo.getAddress());

            byte[] imgBytes = vo.getImgBytes(); // ← 이것만 사용해도 충분
            if (imgBytes != null && imgBytes.length > 0) {
                pstmt.setBytes(4, imgBytes);
                pstmt.setString(5, vo.getImgName());
            } else {
                pstmt.setNull(4, java.sql.Types.BLOB);
                pstmt.setNull(5, java.sql.Types.VARCHAR);
            }

            pstmt.setString(6, vo.getPassword());
            pstmt.setInt(7, deptno);
            pstmt.setInt(8, positionId);
            pstmt.setInt(9, vo.getEmpno());

            return pstmt.executeUpdate();
        }
    }


    
 // 현재 비밀번호 조회
    public String getPassword(int empno) throws SQLException {
        String sql = "SELECT password FROM employee WHERE empno = ?";
        try (Connection con = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, empno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        }
        return null;
    }

    // 비밀번호 업데이트
    public int updatePassword(int empno, String newPw) throws SQLException {
        String sql = "UPDATE employee SET password = ? WHERE empno = ?";
        try (Connection con = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, newPw);
            pstmt.setInt(2, empno);
            return pstmt.executeUpdate();
        }
    }

}//class






