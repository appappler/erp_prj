package kr.co.sist.admin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import kr.co.sist.admin.evt.EmpListViewEvt;
import kr.co.sist.admin.service.EmpService;

/**
 * 
 */
public class EmpListView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField jtfSearchName;
	private JTable jtbEmpList;
	private DefaultTableModel dtm;
	private JComboBox<String> jcbPosition;
	private JComboBox<String> jcbDept;
	private EmpListViewEvt elve; // ← 필드로 이벤트 객체 추가
	private JButton jbtnSearch;

	public EmpListView() {
		
	    setLayout(null);
	    
    	setPreferredSize(new Dimension(1000, 900));
	    
	    jcbDept = new JComboBox<>();
	    jcbDept.setBounds(259, 39, 100, 23);
	    add(jcbDept);

	    jcbPosition = new JComboBox<>();
	    jcbPosition.setBounds(398, 39, 100, 23);
	    add(jcbPosition);

	    JLabel jlblDept = new JLabel("부서");
	    jlblDept.setBounds(231, 43, 32, 15);
	    add(jlblDept);

	    JLabel jlblPosition = new JLabel("직급");
	    jlblPosition.setBounds(371, 43, 32, 15);
	    add(jlblPosition);

	    jtfSearchName = new JTextField("사원명");
	    jtfSearchName.setBounds(522, 40, 116, 21);
	    add(jtfSearchName);

	    jbtnSearch = new JButton("검색");
	    jbtnSearch.setBounds(639, 39, 65, 23);
	    add(jbtnSearch);

	    // 🔹 테이블 설정
	    String[] columnName = {"사원번호", "사원명", "부서", "직급", "생년월일", "연락처", "이메일", "재직여부"};
	    dtm = new DefaultTableModel(columnName, 0);
	    jtbEmpList = new JTable(dtm);
	    jtbEmpList.setRowHeight(30);
	    
	    
        JTableHeader jthTable = jtbEmpList.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 14));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));
        jthTable.setPreferredSize(new Dimension(jthTable.getWidth(), 30));
        

	    // 🔹 정렬 및 컬럼 순서 고정
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
	    jtbEmpList.setRowSorter(sorter);
	    jtbEmpList.getTableHeader().setReorderingAllowed(false);

	    JScrollPane scrollPane = new JScrollPane(jtbEmpList);
	    scrollPane.setBounds(50, 100, 867, 428);
	    add(scrollPane);

	    // 🔹 컬럼 너비 조정
	    jtbEmpList.getColumnModel().getColumn(5).setPreferredWidth(150); // 연락처
	    jtbEmpList.getColumnModel().getColumn(6).setPreferredWidth(200); // 이메일

	 // 🔹 가운데 정렬
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    for (int i = 0; i < jtbEmpList.getColumnCount(); i++) {
	        jtbEmpList.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }
	    
	    // 이벤트 등록 시 필요해서 분리
	    jbtnSearch.addActionListener(elve);
	    
        setVisible(true);
	    
	    
	    elve = new EmpListViewEvt(this);  // 🔥 여기서 1번만 생성
	    initEventBinding();
	    loadDeptAndPosition();
	    dtm.setRowCount(0);
	    
	}

	/*
	private void initComponents() {
	    // 🔹 필터 UI
	    jcbDept = new JComboBox<>();
	    jcbDept.setBounds(259, 39, 100, 23);
	    add(jcbDept);

	    jcbPosition = new JComboBox<>();
	    jcbPosition.setBounds(398, 39, 100, 23);
	    add(jcbPosition);

	    JLabel jlblDept = new JLabel("부서");
	    jlblDept.setBounds(231, 43, 32, 15);
	    add(jlblDept);

	    JLabel jlblPosition = new JLabel("직급");
	    jlblPosition.setBounds(371, 43, 32, 15);
	    add(jlblPosition);

	    jtfSearchName = new JTextField("사원명");
	    jtfSearchName.setBounds(522, 40, 116, 21);
	    add(jtfSearchName);

	    jbtnSearch = new JButton("검색");
	    jbtnSearch.setBounds(639, 39, 65, 23);
	    add(jbtnSearch);

	    // 🔹 테이블 설정
	    String[] columnName = {"사원번호", "사원명", "부서", "직급", "생년월일", "연락처", "이메일", "재직여부"};
	    dtm = new DefaultTableModel(columnName, 0);
	    jtbEmpList = new JTable(dtm);
	    jtbEmpList.setRowHeight(30);

	    // 🔹 정렬 및 컬럼 순서 고정
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
	    jtbEmpList.setRowSorter(sorter);
	    jtbEmpList.getTableHeader().setReorderingAllowed(false);

	    JScrollPane scrollPane = new JScrollPane(jtbEmpList);
	    scrollPane.setBounds(50, 100, 867, 428);
	    add(scrollPane);

	    // 🔹 컬럼 너비 조정
	    jtbEmpList.getColumnModel().getColumn(5).setPreferredWidth(150); // 연락처
	    jtbEmpList.getColumnModel().getColumn(6).setPreferredWidth(200); // 이메일

	    // 이벤트 등록 시 필요해서 분리
	    jbtnSearch.addActionListener(elve);
	}

*/ 
	
	private void initEventBinding() {

	    jcbDept.addActionListener(elve);
	    jcbPosition.addActionListener(elve);
	    jtfSearchName.addActionListener(elve);
	    jtfSearchName.addFocusListener(elve);
	    jtfSearchName.addMouseListener(elve);
	    jtbEmpList.addMouseListener(elve);
	    jbtnSearch.addActionListener(elve);
	}

	private void loadDeptAndPosition() {
	    try {
	        EmpService service = new EmpService();

	        jcbDept.addItem("전체");
	        for (String dept : service.getAllDeptNames()) {
	            jcbDept.addItem(dept);
	        }
	        jcbDept.setSelectedIndex(0); // 🔹 반드시 추가

	        jcbPosition.addItem("전체");
	        for (String pos : service.getAllPositionNames()) {
	            jcbPosition.addItem(pos);
	        }
	        jcbPosition.setSelectedIndex(0); // 🔹 반드시 추가

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "부서 및 직급 정보를 불러오는 데 실패했습니다.");
	    }
	}

	// 🟢 Getter
	public JTextField getJtfSearchName() { return jtfSearchName; }
	public JTable getJtbEmpList() { return jtbEmpList; }
	public DefaultTableModel getDtm() { return dtm; }
	public JComboBox<String> getJcbPosition() { return jcbPosition; }
	public JComboBox<String> getJcbDept() { return jcbDept; }
	public JButton getJbtnSearch() { return jbtnSearch; }

//	public static void main(String[] args) {
//	    SwingUtilities.invokeLater(() -> {
//	        JFrame frame = new JFrame("사원 목록 조회");
//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        frame.setSize(980, 600);
//	        frame.setLocationRelativeTo(null);
//	        frame.setContentPane(new EmpListView());
//	        frame.setVisible(true);
//	    });
//	}
	
	
}//class
