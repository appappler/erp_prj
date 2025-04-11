package kr.co.sist.user.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.vo.DeptFileVO;


@SuppressWarnings("serial")
public class DocumentManagerView extends JFrame{
    private JTextField managerSearchField;
    private JButton managerConfirmButton;
    private JButton docuDeleteButton;
    private JButton downloadButton;
    private JButton shareDeleteButton;
    private JPanel ManagerPanel;
    private JPanel rightPanel;

    private JButton toRightButton;
    private JButton toLeftButton;
    private JButton applyButton;
    private JButton cancelButton;
    
    private JTable managerTable;
    
    private JFrame shareFrame;
    
    
    private DefaultListModel<DeptFileVO> leftListModel;
    private DefaultListModel<DeptFileVO> rightListModel;
    private JList<DeptFileVO> leftCheckList;
    private JList<DeptFileVO> rightCheckList;
    
    private  JComboBox<String> sortComboBox;
    private  JComboBox<String> rightSortComboBox;
    
    private DefaultTableModel managerModel;
    
    private List<DeptFileVO> originalSharedList = new ArrayList<DeptFileVO>();
	
	
	 public DocumentManagerView() {
	        setTitle("문서 공유 시스템");
	        setSize(1000, 500);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new GridLayout(1, 2, 10, 10));

	        // 왼쪽 (부서 문서함)
	        ManagerPanel = createManagerPanel("부서별 공유 문서함", new String[]{"번호", "등록인", "등록일", "파일명", "공유여부", "문서번호"});
	        
	        
	    

	        
	        //이벤트처리
//	        DocumentShareViewEvt dsve = new DocumentShareViewEvt(this);
//	        docuConfirmButton.addActionListener(dsve);
//	        shareConfirmButton.addActionListener(dsve);        
//	        downloadButton.addActionListener(dsve);
//	        docuDeleteButton.addActionListener(dsve);
//	        shareDeleteButton.addActionListener(dsve);
//	        uploadButton.addActionListener(dsve);
//	        sortComboBox.addActionListener(dsve);
//	        rightSortComboBox.addActionListener(dsve);
//	        leftTable.addMouseListener(dsve);
//	        downloadButton.addActionListener(dsve);
	       

	        
	        add(ManagerPanel);
	        
	        setVisible(true);
	    }
	 
	 private JPanel createManagerPanel(String title, String [] columns) {
		  	JPanel panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
	        titleLabel.setOpaque(true);
	        titleLabel.setBackground(new Color(10, 50, 80));
	        titleLabel.setForeground(Color.WHITE);
	        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
	        titleLabel.setPreferredSize(new Dimension(00, 40));

	        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        
	        managerSearchField = new JTextField(30);
	        searchPanel.add(managerSearchField);        	
	       
	        
	        managerConfirmButton = new JButton("확인");
	        searchPanel.add(managerConfirmButton);        	
	       
	        

	        DefaultTableModel model = new DefaultTableModel(columns, 0) {
	            @Override
	            public Class<?> getColumnClass(int column) {
	                if (column == 4) {
	                    return Boolean.class; //공유 여부 체크박스로 설정
	                }
	                return String.class;
	            }
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return column == 5; // 공유여부 열만 true
	            }
	        };
	        
	        JTable table = new JTable(model);
	        JScrollPane scrollPane = new JScrollPane(table);
	        
	        if (columns.length > 5) {
	            table.getColumnModel().getColumn(5).setMinWidth(0);
	            table.getColumnModel().getColumn(5).setMaxWidth(0);
	            table.getColumnModel().getColumn(5).setWidth(0);
	        }
	        
	        
	            managerTable = table;
	            managerModel = model;
	            //콤보박스
	        	String [] sortOptions = {"최신순", "오래된순"};
	            sortComboBox = new JComboBox<>(sortOptions);
	            searchPanel.add(sortComboBox);

	        panel.add(titleLabel);
	        panel.add(searchPanel);
	        panel.add(scrollPane);
	        return panel;
	 }
	
}
