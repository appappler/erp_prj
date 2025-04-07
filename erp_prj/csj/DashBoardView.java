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

        // 🔹 왼쪽 관리자 메뉴 패널
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(9, 1, 5, 5));
        menuPanel.setBackground(new Color(30, 60, 90));

        // 🟦 "관리자 메뉴" 버튼을 일반 버튼으로 변경
        JButton btnAdminMenu = new JButton("관리자 메뉴");
        btnAdminMenu.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btnAdminMenu.setBackground(new Color(20, 50, 80));  // 진한 남색
        btnAdminMenu.setForeground(Color.WHITE);  // 글자색 흰색
        btnAdminMenu.setFocusPainted(false);
        btnAdminMenu.setBorderPainted(false);
        menuPanel.add(btnAdminMenu);

        // 🟦 나머지 메뉴 버튼 추가
        String[] menuItems = {"근태관리", "부서관리", "사원등록", "사원명부", "급여관리", "연봉관리", "직급관리", "문서관리"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            button.setBackground(new Color(20, 50, 80));  // 진한 남색
            button.setForeground(Color.WHITE);  // 글자색 흰색
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            menuPanel.add(button);
        }

        // 🔹 메인 패널 (CardLayout 사용)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 🔹 대시보드 화면 패널 생성
        JPanel dashboardPanel = createDashboardPanel();
        mainPanel.add(dashboardPanel, "대시보드");

        // 초기 화면 설정
        cardLayout.show(mainPanel, "대시보드");

        // 🔹 프레임에 추가
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 🔹 출근 테이블 생성
        String[] columnNames = {"사번", "이름", "부서", "직급", "출근시간", "퇴근시간", "상태"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // 🔹 출근 상태 표시
        JLabel lblAttendance = new JLabel("출근 0명", SwingConstants.CENTER);
        JLabel lblLeave = new JLabel("퇴근 0명", SwingConstants.CENTER);
        JLabel lblAbsent = new JLabel("결근 0명", SwingConstants.CENTER);
        JLabel lblDate = new JLabel(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()), SwingConstants.CENTER);

        // 🔹 로그아웃 버튼
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        logoutButton.setBackground(Color.LIGHT_GRAY);
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");
            System.exit(0);
        });

        // 🔹 상태 패널 추가
        JPanel statusPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        statusPanel.add(lblAttendance);
        statusPanel.add(lblLeave);
        statusPanel.add(lblAbsent);
        statusPanel.add(lblDate);
        statusPanel.add(logoutButton);

        panel.add(statusPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 🔹 데이터 로드
        new AttendanceEvt(tableModel, lblAttendance, lblLeave, lblAbsent).loadAttendanceData();
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashBoardView::new);
    }
}
