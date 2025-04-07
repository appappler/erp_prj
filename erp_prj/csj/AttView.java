package view;

import javax.swing.*;
import java.awt.*;

public class AttView extends JFrame {

    // 상단 패널 (로그아웃)
    private JButton btnLogout;

    // 중앙(메인) 패널
    private JPanel centerPanel;

    // 상단 라벨
    private JLabel lblTitle; // "근태 관리"

    // 부서, 이름 입력 필드
    private JLabel lblDept;
    private JTextField tfDept;
    private JLabel lblName;
    private JTextField tfName;

    // 날짜 범위 선택 (년/월/일 콤보박스)
    private JLabel lblFrom;
    private JComboBox<String> cbYearFrom;
    private JComboBox<String> cbMonthFrom;
    private JComboBox<String> cbDayFrom;

    private JLabel lblTo;
    private JComboBox<String> cbYearTo;
    private JComboBox<String> cbMonthTo;
    private JComboBox<String> cbDayTo;

    // 검색 버튼
    private JButton btnSearch;

    // 결과를 보여줄 테이블
    private JTable table;
    private JScrollPane scrollPane;

    // 저장 버튼 (필요시 사용)
    private JButton btnSave;

    public AttView() {
        setTitle("근태관리 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. 상단 패널 (로그아웃)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("로그아웃");
        topPanel.add(btnLogout);
        add(topPanel, BorderLayout.NORTH);

        // 2. 중앙(메인) 패널 (왼쪽 메뉴 패널 제거)
        centerPanel = new JPanel();
        centerPanel.setLayout(null); // 절대 위치 배치

        // (1) 제목 라벨
        lblTitle = new JLabel("근태 관리");
        lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        lblTitle.setBounds(20, 10, 100, 30);
        centerPanel.add(lblTitle);

        // (2) 부서, 이름 입력 필드
        lblDept = new JLabel("부서:");
        lblDept.setBounds(20, 60, 30, 25);
        centerPanel.add(lblDept);

        tfDept = new JTextField();
        tfDept.setBounds(55, 60, 80, 25);
        centerPanel.add(tfDept);

        lblName = new JLabel("이름:");
        lblName.setBounds(150, 60, 30, 25);
        centerPanel.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(185, 60, 80, 25);
        centerPanel.add(tfName);

        // (3) 날짜 범위 선택 (년/월/일)
        lblFrom = new JLabel("년도-월-일");
        lblFrom.setBounds(280, 60, 60, 25);
        centerPanel.add(lblFrom);

        // 콤보박스 예시 데이터
        String[] years = {"2023", "2024", "2025"};
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
                         "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                         "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

        cbYearFrom = new JComboBox<>(years);
        cbYearFrom.setBounds(340, 60, 60, 25);
        centerPanel.add(cbYearFrom);

        cbMonthFrom = new JComboBox<>(months);
        cbMonthFrom.setBounds(405, 60, 45, 25);
        centerPanel.add(cbMonthFrom);

        cbDayFrom = new JComboBox<>(days);
        cbDayFrom.setBounds(455, 60, 45, 25);
        centerPanel.add(cbDayFrom);

        lblTo = new JLabel("~");
        lblTo.setBounds(505, 60, 20, 25);
        centerPanel.add(lblTo);

        cbYearTo = new JComboBox<>(years);
        cbYearTo.setBounds(520, 60, 60, 25);
        centerPanel.add(cbYearTo);

        cbMonthTo = new JComboBox<>(months);
        cbMonthTo.setBounds(585, 60, 45, 25);
        centerPanel.add(cbMonthTo);

        cbDayTo = new JComboBox<>(days);
        cbDayTo.setBounds(635, 60, 45, 25);
        centerPanel.add(cbDayTo);

        // (4) 검색 버튼
        btnSearch = new JButton("검색");
        btnSearch.setBounds(690, 60, 60, 25);
        centerPanel.add(btnSearch);

        // (5) 결과 테이블
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 730, 200);
        centerPanel.add(scrollPane);

        // (6) 저장 버튼 (필요시 사용)
        btnSave = new JButton("저장");
        btnSave.setBounds(670, 310, 80, 30);
        centerPanel.add(btnSave);

        add(centerPanel, BorderLayout.CENTER);

        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Getter 메서드들 (왼쪽 메뉴 관련은 제거)
    public JButton getBtnLogout() { return btnLogout; }
    public JTextField getTfDept() { return tfDept; }
    public JTextField getTfName() { return tfName; }
    public JComboBox<String> getCbYearFrom() { return cbYearFrom; }
    public JComboBox<String> getCbMonthFrom() { return cbMonthFrom; }
    public JComboBox<String> getCbDayFrom() { return cbDayFrom; }
    public JComboBox<String> getCbYearTo() { return cbYearTo; }
    public JComboBox<String> getCbMonthTo() { return cbMonthTo; }
    public JComboBox<String> getCbDayTo() { return cbDayTo; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public JButton getBtnSave() { return btnSave; }
}
