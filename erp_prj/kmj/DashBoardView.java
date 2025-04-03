package kr.co.sist.design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import kr.co.sist.dao.DbConnection;

@SuppressWarnings("serial")
public class DashBoardView extends JPanel {
    private JLabel jlblToday;
    private JLabel jlblCountin, jlblCountout, jlblAbsence;
    private JComboBox<String> jcbAttendance;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    // DB 연결 정보는 DbConnection에서 처리하므로 제거
    private DbConnection dbConnection;

    public DashBoardView() {
        setLayout(new BorderLayout());

        // DbConnection 인스턴스 가져오기
        dbConnection = DbConnection.getInstance();

        // 상단 패널 (오늘 날짜 & 콤보박스)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jlblToday = new JLabel("오늘 날짜: " + sdf.format(new Date()));
        topPanel.add(jlblToday);

        String[] attendanceOptions = {"전체", "출근", "퇴근", "결근"};
        jcbAttendance = new JComboBox<>(attendanceOptions);
        topPanel.add(jcbAttendance);

        add(topPanel, BorderLayout.NORTH);

        // 중앙 패널 (출근, 퇴근, 결근 인원 표시)
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        jlblCountin = new JLabel("출근: 0명", SwingConstants.CENTER);
        jlblCountout = new JLabel("퇴근: 0명", SwingConstants.CENTER);
        jlblAbsence = new JLabel("결근: 0명", SwingConstants.CENTER);

        centerPanel.add(jlblCountin);
        centerPanel.add(jlblCountout);
        centerPanel.add(jlblAbsence);
        add(centerPanel, BorderLayout.CENTER);

        // 테이블 생성 및 추가
        String[] columnNames = {"사원번호", "사원명", "부서명", "직급명", "출근시간", "퇴근시간", "상태"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        // 데이터 로딩
        updateTableData();
    }

    // DB에서 출퇴근 정보 불러오기
    private void updateTableData() {
        String sql = "SELECT e.empno, e.emp_name, d.deptname, p.position_name, a.in_time, a.out_time, a.status_id " +
                     "FROM employee e, department d, position p, attendance a " +
                     "WHERE e.deptno = d.deptno AND e.position_id = p.position_id AND e.empno = a.empno";

        try (Connection conn = dbConnection.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            tableModel.setRowCount(0); // 기존 데이터 초기화

            int countIn = 0, countOut = 0, countAbsence = 0;

            while (rs.next()) {
                // null 체크 및 타입 변환
                int empno = rs.getInt("empno");
                String empName = rs.getString("emp_name");
                String deptName = rs.getString("deptname");
                String positionName = rs.getString("position_name");

                // Timestamp를 Date로 변환
                java.sql.Timestamp inTime = rs.getTimestamp("in_time");
                java.sql.Timestamp outTime = rs.getTimestamp("out_time");

                // status_id를 Integer로 처리 (만약 null이라면 기본값 처리)
                String statusIdStr = rs.getString("status_id");
                Integer statusId = null;

                // status_id가 null이 아니고, Integer로 변환 가능한 경우에만 변환
                if (statusIdStr != null && !statusIdStr.isEmpty()) {
                    try {
                        statusId = Integer.parseInt(statusIdStr);
                    } catch (NumberFormatException e) {
                        // 변환 실패시 예외 처리 (숫자가 아닌 값이 있을 경우)
                        statusId = 0; // 기본값으로 처리
                    }
                }

                String status = getStatus(statusId != null ? statusId : 0); // null일 경우 기본값 처리

                Object[] row = {
                    empno,
                    empName,
                    deptName,
                    positionName,
                    inTime != null ? inTime : "미입력",  // null 체크 후 표시
                    outTime != null ? outTime : "미입력", // null 체크 후 표시
                    status
                };
                tableModel.addRow(row);

                // 상태별 인원 카운트
                if (status.equals("출근")) countIn++;
                else if (status.equals("퇴근")) countOut++;
                else if (status.equals("결근")) countAbsence++;
            }

            // UI에 카운트 반영
            jlblCountin.setText("출근: " + countIn + "명");
            jlblCountout.setText("퇴근: " + countOut + "명");
            jlblAbsence.setText("결근: " + countAbsence + "명");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터 불러오기 실패: " + e.getMessage(), "에러", JOptionPane.ERROR_MESSAGE);
        }
    }


    // 출퇴근 상태 변환
    private String getStatus(int statusId) {
        switch (statusId) {
            case 1: return "출근";
            case 2: return "퇴근";
            case 3: return "결근";
            default: return "미확인";
        }
    }

    // Getter
    public JLabel getJlblToday() {
        return jlblToday;
    }

    public JLabel getJlblCountin() {
        return jlblCountin;
    }

    public JLabel getJlblCountout() {
        return jlblCountout;
    }

    public JLabel getJlblAbsence() {
        return jlblAbsence;
    }

    public JComboBox<String> getJcbAttendance() {
        return jcbAttendance;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
