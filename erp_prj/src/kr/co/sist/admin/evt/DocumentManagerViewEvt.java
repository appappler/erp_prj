package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.ManagerDeptFileService;
import kr.co.sist.admin.view.DocumentManagerView;
import kr.co.sist.user.service.EmpDeptFileService;
import kr.co.sist.user.service.ShareDeptFileService;
import kr.co.sist.user.vo.DeptFileVO;


public class DocumentManagerViewEvt extends WindowAdapter implements ActionListener, MouseListener{
	
	private JButton managerConfirmButton;
	private JComboBox<String> sortComboBox;
	
	private ManagerDeptFileService mdfs;
	private DocumentManagerView dmv;
	private ShareDeptFileService sdfs;
	
	
	public DocumentManagerViewEvt(DocumentManagerView dmv) {
		this.dmv = dmv;
		
		mdfs = new ManagerDeptFileService();
		sdfs = new ShareDeptFileService();
		
		managerConfirmButton = dmv.getManagerConfirmButton();
		sortComboBox = dmv.getSortComboBox();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == managerConfirmButton) {
			searchManagerText();
		}else if(source == sortComboBox) {
			String selected = (String) sortComboBox.getSelectedItem();
			sortManagerTable(selected);
		}
	}
	
	public void loadAllDocumentData() {
	
		List<DeptFileVO> list = mdfs.showManagerFile();
		DefaultTableModel model = dmv.getManagerModel();

		Set<Integer> sharedDocIds = sdfs.allSharedDocids();
		System.out.println(sharedDocIds);
		model.setRowCount(0); // 기존 행 삭제

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int rowCount = 1;
		for (DeptFileVO dfVO : list) {
			String formattedDate = sdf.format(dfVO.getInputDate());

			boolean isShared = sharedDocIds.contains(dfVO.getNum());
			model.addRow(new Object[] { 
					rowCount++, 
					dfVO.getEmpName(), 
					dfVO.getDeptName(),
					formattedDate,
					dfVO.getFileName(), 
					isShared, 
					dfVO.getNum() });
		}
	}

	public void searchManagerText() {
		String keyword = dmv.getManagerSearchField().getText().trim();

		if (keyword.isEmpty()) {
			JOptionPane.showMessageDialog(dmv, "검색어를 입력하셈");
		}

		List<DeptFileVO> searchList = mdfs.searchManagerFile(keyword);

		DefaultTableModel model = dmv.getManagerModel();
		model.setRowCount(0); // 기존 행 삭제

		Set<Integer> sharedDocIds = sdfs.allSharedDocids();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int rowCount = 1;
		if (!searchList.isEmpty()) {
			for (DeptFileVO dfVO : searchList) {
				String formattedDate = sdf.format(dfVO.getInputDate());
				boolean isShared = sharedDocIds.contains(dfVO.getNum());
				model.addRow(new Object[] { 
						rowCount++, 
						dfVO.getEmpName(), 
						dfVO.getDeptName(),
						formattedDate,
						dfVO.getFileName(), 
						isShared, 
						dfVO.getNum() });
			}
		} else {
			JOptionPane.showMessageDialog(dmv, keyword + "가 포함된 파일이 없습니다.");
			return;
		}

	}// searchManagerText
	
	public void sortManagerTable(String option) {
		
		List<DeptFileVO> searchList = mdfs.sortManagerFile(option);
		
		DefaultTableModel model = dmv.getManagerModel();
		model.setRowCount(0);
		
		Set<Integer> sharedDocIds = sdfs.allSharedDocids();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		int rowCount = 1;
		
		for(DeptFileVO dfVO : searchList) {
			String formattedDate = sdf.format(dfVO.getInputDate());
			boolean isShared = sharedDocIds.contains(dfVO.getNum());
			model.addRow(new Object[] {
					rowCount++,
					dfVO.getEmpName(),
					dfVO.getDeptName(),
					formattedDate,
					dfVO.getFileName(),
					isShared,
					dfVO.getNum()
			});
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		EmpDeptFileService service = new EmpDeptFileService();
		if (source instanceof JTable) {
			JTable table = dmv.getManagerTable();
			int row = table.getSelectedRow();
			int col = table.getSelectedColumn();

			if (col == 5 && row != -1) {
				Boolean isChecked = (Boolean) table.getValueAt(row, col);
				if (isChecked != null && isChecked) {
					int docId = (int) table.getValueAt(row, 6); // 문서 번호 (PK)
					String deptName = (String) table.getValueAt(row, 2); // 부서명 //부서명 추가해야댐 
					String fileName = (String) table.getValueAt(row, 4); // 파일명
					String empName = (String) table.getValueAt(row, 1); // 사원명

					int senderDeptId = service.getDeptIdByName(deptName);
					if (senderDeptId == 0) {
						JOptionPane.showMessageDialog(dmv, "부서 ID 조회 실패: " + deptName);
						return;
					}
					
					//기본설정
					DeptFileVO dfVO = new DeptFileVO();
					dfVO.setDocID(docId);
					dfVO.setSenderDeptID(senderDeptId);
					dfVO.setDeptName(deptName);
					dfVO.setFileName(fileName);
//					dfVO.setDeptID(102);
					dfVO.setEmpName(empName);

					List<DeptFileVO> alreadyShared = sdfs.loadAlreadyShareList(docId);
					dmv.setOriginalSharedList(alreadyShared);

					// 공유 창 오픈
					dmv.confirmShareFileWindow(dfVO);
					System.out.println("doc_id: " + dfVO.getDocID() + ", senderdept_id: " + dfVO.getSenderDeptID());
					loadAlreadySharedDepts(docId);
//					initShareWindowLists(dfVO);
				}
			}
		}
	}
	public void loadAlreadySharedDepts(int docId) {
		List<DeptFileVO> alreadyShared = sdfs.loadAlreadyShareList(docId);

		DefaultListModel<DeptFileVO> managerModel = dmv.getManagerListModel();

		for (DeptFileVO sharedVO : alreadyShared) {
			if (!managerModel.contains(sharedVO)) {

				managerModel.addElement(sharedVO); // toString으로 deptName만 표시됨
			}
		}
	}// loadALready
	
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
