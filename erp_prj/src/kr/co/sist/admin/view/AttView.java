package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.evt.AttEvent;

/**
 * 
 */
public class AttView extends JPanel {
	
	private static final long serialVersionUID = 7830995456499555927L;

	// 상단 패널
    private JButton btnLogout;

    // 검색 관련
    private JTextField tfDept;
    private JTextField tfName;
    private JComboBox<String> cbYearFrom, cbMonthFrom, cbDayFrom;
    private JComboBox<String> cbYearTo, cbMonthTo, cbDayTo;
    private JButton btnSearch;

    // 테이블 관련
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    // 하단 상태 라벨
    private JLabel lblAttendance, lblLeave, lblAbsent;

    // 저장 버튼
    private JButton btnSave;

    public AttView() {
        setLayout(new BorderLayout());
        
    	setPreferredSize(new Dimension(800, 700));
    	

        // 상단 패널 (로그아웃)
    	/*
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("로그아웃");
        topPanel.add(btnLogout);
        add(topPanel, BorderLayout.NORTH);
        */

        // 중앙 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);

        JLabel lblTitle = new JLabel("근태 관리");
        lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        lblTitle.setBounds(20, 10, 100, 30);
        centerPanel.add(lblTitle);

        // 부서, 이름
        JLabel lblDept = new JLabel("부서:");
        lblDept.setBounds(20, 60, 30, 25);
        centerPanel.add(lblDept);

        tfDept = new JTextField();
        tfDept.setBounds(55, 60, 80, 25);
        centerPanel.add(tfDept);

        JLabel lblName = new JLabel("이름:");
        lblName.setBounds(150, 60, 30, 25);
        centerPanel.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(185, 60, 80, 25);
        centerPanel.add(tfName);

        // 날짜 선택
        JLabel lblFrom = new JLabel("년도-월-일");
        lblFrom.setBounds(280, 60, 60, 25);
        centerPanel.add(lblFrom);

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

        JLabel lblTo = new JLabel("~");
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

    	

        // 테이블
        String[] columnNames = {"사번", "이름", "부서", "직급", "출근시간", "퇴근시간", "상태"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 730, 200);
        centerPanel.add(scrollPane);

//        // 하단 상태 라벨
//        lblAttendance = new JLabel("출근 0명");
//        lblAttendance.setBounds(20, 310, 100, 30);
//        centerPanel.add(lblAttendance);
//
//        lblLeave = new JLabel("퇴근 0명");
//        lblLeave.setBounds(120, 310, 100, 30);
//        centerPanel.add(lblLeave);
//
//        lblAbsent = new JLabel("결근 0명");
//        lblAbsent.setBounds(220, 310, 100, 30);
//        centerPanel.add(lblAbsent);

//        // 저장 버튼
//        btnSave = new JButton("저장");
//        btnSave.setBounds(670, 310, 80, 30);
//        centerPanel.add(btnSave);
        
        AttEvent ae=new AttEvent(this);

        // 검색 버튼
        btnSearch = new JButton("검색");
        btnSearch.setBounds(690, 60, 60, 25);
        btnSearch.addActionListener(ae);
        centerPanel.add(btnSearch);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Getter 메서드들
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
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnSave() { return btnSave; }
    public JLabel getLblAttendance() { return lblAttendance; }
    public JLabel getLblLeave() { return lblLeave; }
    public JLabel getLblAbsent() { return lblAbsent; }
    
}//class
