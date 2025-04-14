package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import kr.co.sist.admin.evt.AttendanceEvt;

/**
 * 
 */
public class DashBoardView extends JPanel{
	
	private static final long serialVersionUID = 2235800353033636975L;
	private CardLayout cardLayout;
    private JPanel mainPanel;

    public DashBoardView() {
    	
    	setPreferredSize(new Dimension(1100, 700));
    	
    	
//        JFrame frame = new JFrame("관리자 모드");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1200, 700);
//        frame.setLayout(new BorderLayout());

        // 🔹 왼쪽 관리자 메뉴 패널
//        JPanel menuPanel = new JPanel();
//        menuPanel.setLayout(new GridLayout(9, 1, 5, 5));
//        menuPanel.setBackground(new Color(30, 60, 90));

        // 🟦 "관리자 메뉴" 버튼을 일반 버튼으로 변경
//        JButton btnAdminMenu = new JButton("관리자 메뉴");
//        btnAdminMenu.setFont(new Font("맑은 고딕", Font.BOLD, 14));
//        btnAdminMenu.setBackground(new Color(20, 50, 80));  // 진한 남색
//        btnAdminMenu.setForeground(Color.WHITE);  // 글자색 흰색
//        btnAdminMenu.setFocusPainted(false);
//        btnAdminMenu.setBorderPainted(false);
//        menuPanel.add(btnAdminMenu);

        // 🟦 나머지 메뉴 버튼 추가
//        String[] menuItems = {"근태관리", "부서관리", "사원등록", "사원명부", "급여관리", "연봉관리", "직급관리", "문서관리"};
//        for (String item : menuItems) {
//            JButton button = new JButton(item);
//            button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
//            button.setBackground(new Color(20, 50, 80));  // 진한 남색
//            button.setForeground(Color.WHITE);  // 글자색 흰색
//            button.setFocusPainted(false);
//            button.setBorderPainted(false);
//            menuPanel.add(button);
//        }

        // 🔹 메인 패널 (CardLayout 사용)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(1000, 600));

        // 🔹 대시보드 화면 패널 생성
        JPanel dashboardPanel = createDashboardPanel();
        mainPanel.add(dashboardPanel, "대시보드");
        // 초기 화면 설정
        cardLayout.show(mainPanel, "대시보드");

        // 🔹 프레임에 추가
//        add(menuPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        // 🔹 출근 테이블 생성
        String[] columnNames = {"사번", "이름", "부서", "직급", "출근시간", "퇴근시간", "상태"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        	
			private static final long serialVersionUID = -7984279552610965548L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
        };
        
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JTableHeader jthTable = table.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 12));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));
        jthTable.setPreferredSize(new Dimension(jthTable.getWidth(), 24));
        
        TableColumn tc0= table.getColumnModel().getColumn(0);
        tc0.setMinWidth(90);//사번
        tc0.setMaxWidth(90);//사번
        TableColumn tc1= table.getColumnModel().getColumn(1);
        tc1.setMinWidth(130);//이름
        tc1.setMaxWidth(130);//이름
        TableColumn tc2= table.getColumnModel().getColumn(2);
        tc2.setMinWidth(120);//부서
        tc2.setMaxWidth(120);//부서
        TableColumn tc3= table.getColumnModel().getColumn(3);
        tc3.setMinWidth(120);//직급
        tc3.setMaxWidth(120);//직급
        TableColumn tc4= table.getColumnModel().getColumn(4);
        tc4.setMinWidth(210);//출근시간
        tc4.setMaxWidth(210);//
        TableColumn tc5= table.getColumnModel().getColumn(5);
        tc5.setMinWidth(210);//퇴근시간
        tc5.setMaxWidth(210);//퇴근시간
        TableColumn tc6= table.getColumnModel().getColumn(6);
        tc6.setPreferredWidth(100);
        

        table.setRowHeight(48);
        table.setFont(new Font("Dialog", Font.BOLD, 16));

        // 🔹 출근 상태 표시
        JLabel lblAttendance = new JLabel("출근 0명", SwingConstants.CENTER);
        JLabel lblLeave = new JLabel("퇴근 0명", SwingConstants.CENTER);
        JLabel lblAbsent = new JLabel("결근 0명", SwingConstants.CENTER);
        JLabel lblEarly = new JLabel("조퇴 0명", SwingConstants.CENTER);
        JLabel lblDate = new JLabel(new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date()), SwingConstants.CENTER);

        lblAttendance.setFont(new Font("Dialog", Font.BOLD, 20));
        lblLeave.setFont(new Font("Dialog", Font.BOLD, 20));
        lblAbsent.setFont(new Font("Dialog", Font.BOLD, 20));
        lblEarly.setFont(new Font("Dialog", Font.BOLD, 20));
        lblDate.setFont(new Font("Dialog", Font.BOLD, 20));
        
        lblAttendance.setForeground(new Color(8, 60, 80));
        lblLeave.setForeground(new Color(8, 60, 80));
        lblAbsent.setForeground(new Color(8, 60, 80));
        lblEarly.setForeground(new Color(8, 60, 80));
        lblDate.setForeground(new Color(8, 60, 80));
        
//        // 🔹 로그아웃 버튼
//        JButton logoutButton = new JButton("로그아웃");
//        logoutButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
//        logoutButton.setBackground(Color.LIGHT_GRAY);
//        logoutButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(null, "로그아웃되었습니다.");
//            System.exit(0);
//        });

        // 🔹 상태 패널 추가
        JPanel statusPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        statusPanel.setOpaque(false);
        statusPanel.setPreferredSize(new Dimension(1000, 40));
        statusPanel.add(lblAttendance);
        statusPanel.add(lblLeave);
//        statusPanel.add(lblAbsent);
        statusPanel.add(lblEarly);
        statusPanel.add(lblDate);
//        statusPanel.add(logoutButton);

        panel.setOpaque(false);
        panel.add(statusPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 🔹 데이터 로드
//        new AttendanceEvt(tableModel, lblAttendance, lblLeave, lblAbsent).loadAttendanceData();
        AttendanceEvt ae=new AttendanceEvt(tableModel, lblAttendance, lblLeave, lblAbsent, lblEarly);
        
        Thread thread=new Thread( ae );
        thread.start();
        return panel;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(DashBoardView::new);
//    }
    
    
}//class