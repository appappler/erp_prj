package csj_view;

import csj_evt.AttendanceEvt;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardView {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public DashBoardView() {
        JFrame frame = new JFrame("관리자 모드");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());

        // 왼쪽 관리자 메뉴 패널
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(9, 1, 5, 5));
        menuPanel.setBackground(new Color(30, 60, 90));

        JLabel menuTitle = new JLabel("관리자 메뉴", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuPanel.add(menuTitle);

        String[] menuItems = {"근태관리", "부서관리", "사원등록", "사원명부", "급여관리", "연봉관리", "직급관리", "문서관리"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);
            menuPanel.add(button);
        }

        // 메인 패널 (CardLayout 사용)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 대시보드 화면 패널 생성
        JPanel dashboardPanel = createDashboardPanel();
        mainPanel.add(dashboardPanel, "대시보드");

        // 초기 화면 설정
        cardLayout.show(mainPanel, "대시보드");

        // 프레임에 추가
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"사번", "출근 시간", "퇴근 시간", "근무 시간", "비고", "상태"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JLabel lblAttendance = new JLabel("출근 0명", SwingConstants.CENTER);
        JLabel lblLeave = new JLabel("퇴근 0명", SwingConstants.CENTER);
        JLabel lblAbsent = new JLabel("결근 0명", SwingConstants.CENTER);
        JLabel lblDate = new JLabel(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()), SwingConstants.CENTER);
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");
            System.exit(0);
        });

        JPanel statusPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statusPanel.add(lblAttendance);
        statusPanel.add(lblLeave);
        statusPanel.add(lblAbsent);
        statusPanel.add(lblDate);
        statusPanel.add(logoutButton);

        panel.add(statusPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        new AttendanceEvt(tableModel, lblAttendance, lblLeave, lblAbsent).loadAttendanceData();
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashBoardView::new);
    }
}
