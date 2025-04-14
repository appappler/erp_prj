package kr.co.sist.user.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.evt.DocumentShareViewEvt;
import kr.co.sist.user.service.ShareDeptFileService;
import kr.co.sist.user.vo.DeptFileVO;
import kr.co.sist.user.vo.UserAccountVO;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SuppressWarnings("serial")
public class DocumentShareView extends JPanel {

    private JTextField docuSearchField;
    private JTextField shareSearchField;
    private JButton docuConfirmButton;
    private JButton shareConfirmButton;
    private JButton uploadButton;
    private JButton docuDeleteButton;
    private JButton downloadButton;
    private JButton shareDeleteButton;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JButton toRightButton;
    private JButton toLeftButton;
    private JButton applyButton;
    private JButton cancelButton;
    
    private JTable leftTable;
    private JTable rightTable;
    
    private JFrame shareFrame;
    
    
    private DefaultListModel<DeptFileVO> leftListModel;
    private DefaultListModel<DeptFileVO> rightListModel;
    private JList<DeptFileVO> leftCheckList;
    private JList<DeptFileVO> rightCheckList;
    
    private  JComboBox<String> sortComboBox;
    private  JComboBox<String> rightSortComboBox;
    
    private DefaultTableModel leftModel, rightModel;
    
    private List<DeptFileVO> originalSharedList = new ArrayList<DeptFileVO>();
    
    private int userId;
    
    public int getUserId() {
		return userId;
	}

	public DocumentShareView(int userId) {
//        setTitle("문서 공유 시스템");
    	this.userId = userId;
//        setSize(1300, 500);
        
        setPreferredSize(new Dimension(1100, 600));
        
        
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2, 10, 10));

        // 왼쪽 (부서 문서함)
        leftPanel = createDocumentPanel("부서 문서함", new String[]{"번호", "등록인", "부서명", "등록일", "파일명", "공유여부", "문서번호"}, true);
        
        // 오른쪽 (공유 문서함)
        rightPanel = createDocumentPanel("공유 문서함", new String[]{"번호", "보낸부서", "파일명", "일자", "문서번호"}, false);
        
        JPanel leftBottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadButton = new JButton("파일 업로드");
        leftBottomPanel.add(uploadButton);
        
        JPanel rightBottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        downloadButton = new JButton("다운로드");
        rightBottomPanel.add(downloadButton);

        leftPanel.add(leftBottomPanel);
        rightPanel.add(rightBottomPanel);

        
        //이벤트처리
        DocumentShareViewEvt dsve = new DocumentShareViewEvt(this);
        docuConfirmButton.addActionListener(dsve);
        shareConfirmButton.addActionListener(dsve);        
        downloadButton.addActionListener(dsve);
        docuDeleteButton.addActionListener(dsve);
        shareDeleteButton.addActionListener(dsve);
        uploadButton.addActionListener(dsve);
        sortComboBox.addActionListener(dsve);
        rightSortComboBox.addActionListener(dsve);
        leftTable.addMouseListener(dsve);
        downloadButton.addActionListener(dsve);
       

        dsve.loadAllDocumentData();
        dsve.loadShareTable();
        
        add(leftPanel);
        add(rightPanel);
        setVisible(true);
    }

    private JPanel createDocumentPanel(String title, String[] columns, boolean isLeft) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(10, 50, 80));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        titleLabel.setPreferredSize(new Dimension(00, 40));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        if(isLeft) {
        	docuSearchField = new JTextField(30);
        	searchPanel.add(docuSearchField);        	
        }else {
        	shareSearchField = new JTextField(30);
        	searchPanel.add(shareSearchField);
        }
        
        if(isLeft) {
        	docuConfirmButton = new JButton("확인");
        	searchPanel.add(docuConfirmButton);        	
        }else {
        	shareConfirmButton = new JButton("확인");
        	searchPanel.add(shareConfirmButton);        	
        	
        }
        
        if(isLeft) {
        	docuDeleteButton = new JButton("삭제");
        	searchPanel.add(docuDeleteButton);
        }else {
        	shareDeleteButton = new JButton("삭제");
        	searchPanel.add(shareDeleteButton);
        }

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (isLeft && column == 5) {
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
        
        if (isLeft && columns.length > 6) {
            table.getColumnModel().getColumn(6).setMinWidth(0);
            table.getColumnModel().getColumn(6).setMaxWidth(0);
            table.getColumnModel().getColumn(6).setWidth(0);
        }
            else if(columns.length > 4) {
        	table.getColumnModel().getColumn(4).setMinWidth(0);
            table.getColumnModel().getColumn(4).setMaxWidth(0);
            table.getColumnModel().getColumn(4).setWidth(0);
        }
        
        
        if (isLeft) {
            leftTable = table;
            leftModel = model;
        } else {
            rightTable = table;
            rightModel = model;
        }
        if (isLeft) {
            //콤보박스
        	String [] sortOptions = {"최신순", "오래된순"};
            sortComboBox = new JComboBox<>(sortOptions);
            searchPanel.add(sortComboBox);
        } else {
        	String [] rightSortOptions = {"최신순", "오래된순"};
            rightSortComboBox = new JComboBox<>(rightSortOptions);
            searchPanel.add(rightSortComboBox);
        }

        panel.add(titleLabel);
        panel.add(searchPanel);
        panel.add(scrollPane);
        return panel;
    }
    
    //공유 체크박스 클릭 후 생성되는 jframe
    public void openShareWindow(DeptFileVO dfVO) {
        shareFrame = new JFrame("공유");
        shareFrame.setSize(500, 700);
        shareFrame.setLocationRelativeTo(this);
        shareFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //타이틀 라벨
        JLabel titleLabel = new JLabel("공유 현재파일명 : "+ dfVO.getFileName(), JLabel.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        //왼쪽 리스트 (체크박스 있는 부서)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("공유할 부서"));

        leftListModel = new DefaultListModel<>();
        int docId = dfVO.getDocID();
        ShareDeptFileService sdfs = new ShareDeptFileService();
        
         
//          List<DeptFileVO> allDepts = sdfs.getAllDepartment(); //전체 부서
//         leftListModel.addElement(dfVO);
     // 1. 전체 부서 가져오기 (이 메서드는 전체 DeptFileVO 리스트 반환해야 함)
//          List<DeptFileVO> sharedDepts = sdfs.loadAlreadyShareList(docId); // ← 이미 공유된 부서
//
//        // 2. sharedDepts에서 부서ID만 모으기
//        Set<Integer> sharedDeptIds = sharedDepts.stream()
////        		Set<Integer> sharedDeptIds = allDepts.stream()
//                .map(DeptFileVO::getDeptID)
//                .collect(Collectors.toSet());
//
//        // 3. 왼쪽 리스트엔 공유 안 된 부서만 추가
//            if (!sharedDeptIds.contains(dfVO.getDeptID())) {
//                leftListModel.addElement(dfVO);
//            }
          
          
         //2단계
        
//        List<DeptFileVO> sharedDepts = sdfs.loadAlreadyShareList(dfVO.getDocID());
//
//        for (DeptFileVO dept : allDepts) {
//            if (dept.getSenderDeptID() == dfVO.getSenderDeptID()) continue; // 본인 부서는 제외
//            
//            
//            boolean isAlreadyShared = false;
//            for (DeptFileVO shared : sharedDepts) {
//                if (shared.getDeptID() == dept.getDeptID()) {
//                    isAlreadyShared = true;
//                    break;
//                }
//            }
////         
//            if (isAlreadyShared) {
//                rightListModel.addElement(dept); // 공유된 건 오른쪽
//            } else {
//                leftListModel.addElement(dept); // 공유되지 않은 건 왼쪽
//            }
//        }
          
        
        leftCheckList = new JList<>(leftListModel);
        leftCheckList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane leftScroll = new JScrollPane(leftCheckList);
        leftPanel.add(leftScroll, BorderLayout.CENTER);

        //오른쪽 리스트 (공유받은 부서)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("공유함"));

        rightListModel = new DefaultListModel<>();
        rightCheckList = new JList<>(rightListModel);
        rightCheckList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane rightScroll = new JScrollPane(rightCheckList);
        rightPanel.add(rightScroll, BorderLayout.CENTER);

        //가운데 버튼
        JPanel centerButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        toRightButton = new JButton("→");
        toLeftButton = new JButton("←");
        toRightButton.setPreferredSize(new Dimension(50, 30));
        toLeftButton.setPreferredSize(new Dimension(50, 30));
        centerButtons.add(toLeftButton);
        centerButtons.add(toRightButton);

        //하단 버튼
        applyButton = new JButton("적용");
        cancelButton = new JButton("취소");
        applyButton.setBackground(new Color(0, 102, 204));
        applyButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);

        JPanel bottomButtons = new JPanel();
        bottomButtons.add(applyButton);
        bottomButtons.add(cancelButton);

        //전체 배치
        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.add(leftPanel);
        listsPanel.add(rightPanel);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(listsPanel, BorderLayout.CENTER);
        centerPanel.add(centerButtons, BorderLayout.SOUTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(bottomButtons, BorderLayout.SOUTH);
        
      
        
        originalSharedList = sdfs.loadAlreadyShareList(dfVO.getDocID());

        for (DeptFileVO shared : originalSharedList) {
            rightListModel.addElement(shared);
        }

        
        //이벤트처리
        DocumentShareViewEvt dsve = new DocumentShareViewEvt(this);
        toLeftButton.addActionListener(dsve);
        toRightButton.addActionListener(dsve);
        applyButton.addActionListener(dsve);
        cancelButton.addActionListener(dsve);
        
        leftCheckList.addMouseListener(dsve);
        
        System.out.println("공유창 VO: " + dfVO);
        System.out.println("doc_id: " + dfVO.getDocID());
        System.out.println("dept_id: " + dfVO.getSenderDeptID());
        
        
        
        shareFrame.setContentPane(contentPanel);
        shareFrame.setVisible(true);
    }
    
    //getter
    public JComboBox<String> getSortComboBox(){return sortComboBox;}
    public JComboBox<String> getRightSortComboBox(){return rightSortComboBox;}
    public JTextField getDocuSearchField() { return docuSearchField; }
    public JTextField getShareSearchField() { return shareSearchField; }
    public JButton getDocuConfirmButton() { return docuConfirmButton; }
    public JButton getShareConfirmButton() { return shareConfirmButton; }
    public JButton getDocuDeleteButton() { return docuDeleteButton; }
    public JButton getShareDeleteButton() { return shareDeleteButton;}
    public JButton getUploadButton() { return uploadButton; }
    public JButton getDownloadButton() { return downloadButton; }
    public JTable getLeftTable() { return leftTable; }
    public JTable getRightTable() { return rightTable; }
    public DefaultTableModel getLeftModel() { return leftModel; }
    public DefaultTableModel getRightModel() { return rightModel; }
    public JButton getToRightBtn() {return toRightButton;}
    public JButton getToLeftBtn() {return toLeftButton;}
    public JButton getApplyButton() {return applyButton;}
    public JButton getCancelButton() {return cancelButton;}
    public JFrame getShareFrame() {return shareFrame;}
	public JPanel getLeftPanel() {return leftPanel;}
	public JPanel getRightPanel() {return rightPanel;}
	public DefaultListModel<DeptFileVO> getLeftListModel() {return leftListModel;}
	public DefaultListModel<DeptFileVO> getRightListModel() {return rightListModel;}
	public JList<DeptFileVO> getLeftCheckList() {return leftCheckList;}
	public JList<DeptFileVO> getRightCheckList() {return rightCheckList;}
	public List<DeptFileVO> getOriginalSharedList() {return originalSharedList;}

	public void setOriginalSharedList(List<DeptFileVO> originalSharedList) {
		this.originalSharedList = originalSharedList;
	}
	
	
	
    
}