package kr.co.sist.design;

import javax.swing.*;

import kr.co.sist.evt.AdminMenuEvent;

import java.awt.*;

@SuppressWarnings("serial")
public class AdminParentFrame extends JFrame {
    private JLabel jlblTitle;
    private JButton jbtnLogout;
    private AdminMenuBar menuBar;
    private DashBoardView dashBoardView;
    private AdminMenuEvent menuEvent;

    public AdminParentFrame() {
        setTitle("관리자 메인 화면");
        setSize(700,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 상단 제목 레이블 + 로그아웃 버튼
        JPanel topPanel = new JPanel(new BorderLayout());
        jlblTitle = new JLabel("관리자 화면", SwingConstants.CENTER);
        jlblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        jbtnLogout = new JButton("로그아웃");
        topPanel.add(jlblTitle, BorderLayout.CENTER);
        topPanel.add(jbtnLogout, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 메뉴바와 대시보드 추가
        JPanel centerPanel = new JPanel(new BorderLayout());
        menuBar = new AdminMenuBar();
        dashBoardView = new DashBoardView();

        centerPanel.add(menuBar, BorderLayout.WEST);
        centerPanel.add(dashBoardView, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 이벤트 등록
        menuEvent = new AdminMenuEvent(menuBar, this);
        jbtnLogout.addActionListener(e -> {
            dispose();
            new AdminLoginFrame();
        });

        setVisible(true);
    }

}//class
