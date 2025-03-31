package kr.co.sist.design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class DashBoardView extends JPanel {
    private JLabel jlblToday;
    private JLabel jlblCountin, jlblCountout, jlblAbsence;
    private JComboBox<String> jcbAttendance;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public DashBoardView() {
        setLayout(new BorderLayout());

        // 상단 패널 (오늘 날짜 & 콤보박스)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 오늘 날짜 표시
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jlblToday = new JLabel("오늘 날짜: " + sdf.format(new Date()));
        topPanel.add(jlblToday);

        // 출근 상태 콤보박스
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

        // 테이블 (출퇴근 현황)
        String[] columnNames = {"사원명", "출근 시간", "퇴근 시간", "상태"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.SOUTH);
    }

    // ✅ Getter
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
}//class