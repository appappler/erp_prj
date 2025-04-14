package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.evt.DocumentManagerViewEvt;
import kr.co.sist.user.service.ShareDeptFileService;
import kr.co.sist.user.vo.DeptFileVO;




@SuppressWarnings("serial")
public class DocumentManagerView extends JPanel{
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
    
    
    private DefaultListModel<DeptFileVO> managerListModel;
    private JList<DeptFileVO> managerCheckList;
    
    private  JComboBox<String> sortComboBox;
    
    private DefaultTableModel managerModel;
    
    private List<DeptFileVO> originalSharedList = new ArrayList<DeptFileVO>();
	
	
	 public DocumentManagerView() {
//	        setTitle("문서 공유 시스템");
//		 	setPreferredSize(new Dimension(900, 600));
	        setSize(1300, 700);
//	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new GridLayout(1, 2, 10, 10));

	        // 왼쪽 (부서 문서함)
	        ManagerPanel = createManagerPanel("부서별 공유 문서함", new String[]{"번호", "등록인", "부서명" , "등록일", "파일명", "공유여부", "문서번호"});
	        
	        ManagerPanel.setBounds(0,0, 1200, 800);
	    

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
//	        downloadButton.addActionListener(dsve);
	       
	        DocumentManagerViewEvt dmve = new DocumentManagerViewEvt(this);
	        managerConfirmButton.addActionListener(dmve);
	        sortComboBox.addActionListener(dmve);
	        managerTable.addMouseListener(dmve);
	        
	        
	        
	        dmve.loadAllDocumentData();
	        
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
	                if (column == 5) {
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
	        
	        if (columns.length > 6) {
	            table.getColumnModel().getColumn(6).setMinWidth(0);
	            table.getColumnModel().getColumn(6).setMaxWidth(0);
	            table.getColumnModel().getColumn(6).setWidth(0);
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
	 public void confirmShareFileWindow(DeptFileVO dfVO) {
		 	ShareDeptFileService sdfs = new ShareDeptFileService();
		 
		 	shareFrame = new JFrame("공유");
	        shareFrame.setSize(500, 700);
	        shareFrame.setLocationRelativeTo(this);
	        shareFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        
	      //타이틀 라벨
	        JLabel titleLabel = new JLabel("공유 현재파일명 : "+ "dfVO.getFileName()", JLabel.CENTER);
	        titleLabel.setForeground(Color.BLUE);
	        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
	        
	        JPanel managerPanel = new JPanel(new BorderLayout());
	        managerPanel.setBorder(BorderFactory.createTitledBorder("공유한 부서"));
	        
	        managerListModel = new DefaultListModel<>();
	        managerCheckList = new JList<>(managerListModel);
	        
	        managerCheckList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        
	        JScrollPane managerScroll = new JScrollPane(managerCheckList);
	        managerPanel.add(managerScroll, BorderLayout.CENTER);
	        
	        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 10));
	        listPanel.add(managerPanel);
	        
	        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
	        centerPanel.add(listPanel, BorderLayout.CENTER);
	        
	        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
	        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        contentPanel.add(titleLabel, BorderLayout.NORTH);
	        contentPanel.add(centerPanel, BorderLayout.CENTER);
	        
	        originalSharedList = sdfs.loadAlreadyShareList(dfVO.getDocID());

	        for (DeptFileVO shared : originalSharedList) {
	            managerListModel.addElement(shared);
	        }
	        
	        DocumentManagerViewEvt dmve = new DocumentManagerViewEvt(this);
	        managerCheckList.addMouseListener(dmve);
	        
	        
	        shareFrame.setContentPane(contentPanel);
	        shareFrame.setVisible(true);
	        
	 }

	public JTextField getManagerSearchField() {
		return managerSearchField;
	}

	public JButton getManagerConfirmButton() {
		return managerConfirmButton;
	}

	public JButton getDocuDeleteButton() {
		return docuDeleteButton;
	}

	public JButton getDownloadButton() {
		return downloadButton;
	}

	public JButton getShareDeleteButton() {
		return shareDeleteButton;
	}

	public JPanel getManagerPanel() {
		return ManagerPanel;
	}

	public JPanel getRightPanel() {
		return rightPanel;
	}

	public JButton getToRightButton() {
		return toRightButton;
	}

	public JButton getToLeftButton() {
		return toLeftButton;
	}

	public JButton getApplyButton() {
		return applyButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JTable getManagerTable() {
		return managerTable;
	}

	public JFrame getShareFrame() {
		return shareFrame;
	}


	public DefaultListModel<DeptFileVO> getManagerListModel() {
		return managerListModel;
	}

	public JList<DeptFileVO> getManagerCheckList() {
		return managerCheckList;
	}

	public JComboBox<String> getSortComboBox() {
		return sortComboBox;
	}


	public DefaultTableModel getManagerModel() {
		return managerModel;
	}

	public List<DeptFileVO> getOriginalSharedList() {
		return originalSharedList;
	}

	public void setOriginalSharedList(List<DeptFileVO> originalSharedList) {
		this.originalSharedList = originalSharedList;
	}
	
}
