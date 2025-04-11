package kr.co.sist.user.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.dao.ShareDeptFileDAO;
import kr.co.sist.user.service.EmpDeptFileService;
import kr.co.sist.user.service.ShareDeptFileService;
import kr.co.sist.user.view.DocumentShareView;
import kr.co.sist.user.vo.DeptFileVO;

public class DocumentShareViewEvt extends WindowAdapter implements ActionListener, MouseListener {

	private DocumentShareView dsv;
	private JButton docuConfirmButton;
	private JButton shareConfirmButton;
	private JButton docuDeleteButton;
	private JButton shareDeleteButton;
	private JButton uploadButton;
	private JButton downloadButton;
	private JButton toRightButton;
	private JButton toLeftButton;
	private JButton cancelButton;
	private JButton applyButton;

	private JTextField searchField;

	private JFrame shareFrame;

	private final JComboBox<String> sortComboBox;
	private final JComboBox<String> rightSortComboBox;

	private EmpDeptFileService service;
	private ShareDeptFileService sdfs;

	public DocumentShareViewEvt(DocumentShareView dsv) {
		this.dsv = dsv;
		sortComboBox = dsv.getSortComboBox();
		rightSortComboBox = dsv.getRightSortComboBox();
		searchField = dsv.getDocuSearchField();
		docuConfirmButton = dsv.getDocuConfirmButton();
		shareConfirmButton = dsv.getShareConfirmButton();
		docuDeleteButton = dsv.getDocuDeleteButton();
		shareDeleteButton = dsv.getShareDeleteButton();
		uploadButton = dsv.getUploadButton();
		downloadButton = dsv.getDownloadButton();
		toRightButton = dsv.getToRightBtn();
		toLeftButton = dsv.getToLeftBtn();
		cancelButton = dsv.getCancelButton();
		applyButton = dsv.getApplyButton();
		shareFrame = dsv.getShareFrame();

		service = new EmpDeptFileService(); // 서비스 초기화
		sdfs = new ShareDeptFileService();
	}

//	public void windowClosing() {
//		dsv.dispose();
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == uploadButton) {
			uploadFile();
		} else if (source == docuDeleteButton) {
			removeRow();
		} else if (source == docuConfirmButton) {
			searchDocuText();
		} else if (source == sortComboBox) {
			String selected = (String) sortComboBox.getSelectedItem();
			sortFile(selected);
		} else if (source == rightSortComboBox) {
			String selected = (String) rightSortComboBox.getSelectedItem();
			sortShareFile(selected);
		} else if (source == downloadButton) {
			downloadSelectedFile();
		} else if (source == toRightButton) {
			moveAllLeftToRight();
		} else if (source == toLeftButton) {
			moveSelectedRightToLeft();
		} else if (source == applyButton) {
			moveToShareTable();
		} else if (source == cancelButton) {
			shareFrame.dispose();
		} else if (source == shareConfirmButton) {
			searchShareText();
		} else if (source == shareDeleteButton) {
			removeShareRow();
		}
	}

	public void uploadFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(dsv);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String fileName = fileChooser.getSelectedFile().getName();
			String uploader = JOptionPane.showInputDialog("사원번호를 입력하세요:");

			if (uploader == null || uploader.trim().isEmpty()) {
				JOptionPane.showMessageDialog(dsv, "등록인이 입력되지 않았습니다.", "알림", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int uploader2;
			try {
				uploader2 = Integer.parseInt(uploader);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(dsv, "숫자 형식의 사원번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!service.isEmpExists(uploader2)) {
				JOptionPane.showMessageDialog(dsv, "없는 사원번호입니다.");
				return;
			}

			byte[] fileBytes;

			DeptFileVO dfVO = new DeptFileVO();
			try {
				fileBytes = Files.readAllBytes(selectedFile.toPath());
				dfVO.setEmpId(uploader2);
				dfVO.setFileName(fileName);
				dfVO.setFileData(fileBytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// VO 생성

			// insert
			boolean success = service.addDocumentFile(dfVO);

			if (success) {
				// 전체데이터
				List<DeptFileVO> list = service.showAllFile();

				// 가장 마지막에 insert된 데이터만 꺼냄
				dfVO = list.get(list.size() - 1); // 마지막꺼

				if (dfVO != null) {
					DefaultTableModel model = dsv.getLeftModel();
					int rowCount = model.getRowCount() + 1;
					Set<Integer> sharedDocIds = sdfs.allSharedDocids();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String formattedDate = sdf.format(dfVO.getInputDate());
//					boolean isShared = sharedDocIds.contains(dfVO.getDocID());
					// 테이블 행에 데이터 추가 new object[] 어떤 데이터형태든 들어갈수있는 배열
					model.addRow(new Object[] { rowCount++, dfVO.getEmpName(), dfVO.getDeptName(), formattedDate,
							dfVO.getFileName(), false, dfVO.getNum()

					});
				}
			} else {
				JOptionPane.showMessageDialog(dsv, "DB 저장 실패!", "오류", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// 전체데이터출력
	public void loadAllDocumentData() {
		List<DeptFileVO> list = service.showAllFile();
		DefaultTableModel model = dsv.getLeftModel();

		Set<Integer> sharedDocIds = sdfs.allSharedDocids();
		System.out.println(sharedDocIds);
		model.setRowCount(0); // 기존 행 삭제

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int rowCount = 1;
		for (DeptFileVO dfVO : list) {
			String formattedDate = sdf.format(dfVO.getInputDate());

			boolean isShared = sharedDocIds.contains(dfVO.getNum());
			model.addRow(new Object[] { rowCount++, dfVO.getEmpName(), dfVO.getDeptName(), formattedDate,
					dfVO.getFileName(), isShared, dfVO.getNum() });
		}
	}

	// 추가되자마자 출력
	public void loadShareTable() {
		List<DeptFileVO> list = sdfs.showShareTable();
		DefaultTableModel rightModel = dsv.getRightModel();
		rightModel.setRowCount(0);// 초기화

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int rowCount = 1;
		for (DeptFileVO dfVO : list) {
			String formattedDate = sdf.format(dfVO.getShareData());

			rightModel.addRow(new Object[] { rowCount++, dfVO.getDeptName(), dfVO.getFileName(), formattedDate,
					dfVO.getDocID() });
		}
	}// selectshareTable

	// 파일 삭제
	public void removeRow() {

		int leftSelectedRow = dsv.getLeftTable().getSelectedRow();
		DefaultTableModel model = dsv.getLeftModel();

		if (leftSelectedRow == -1) {
			JOptionPane.showMessageDialog(dsv, "파일 선택해주세요.");
			return;
		}

		// 0번 열 = 번호(doc_id) 컬럼
		Object docIdObj = model.getValueAt(leftSelectedRow, 6);
		int docId = Integer.parseInt(docIdObj.toString());

		service = new EmpDeptFileService();

		boolean leftSelectedFlag = service.removeFile(docId);

		if (leftSelectedFlag) {
			dsv.getLeftModel().removeRow(leftSelectedRow); // 화면에서도 제거
			JOptionPane.showMessageDialog(dsv, "파일이 삭제가 되었습니다");
			loadShareTable();
		} else {
			JOptionPane.showMessageDialog(dsv, "무슨 문제가 있다...");
		}

	}// removeRow

	public void removeShareRow() {
		int rightSelectedRow = dsv.getRightTable().getSelectedRow();
		DefaultTableModel rightModel = dsv.getRightModel();

		if (rightSelectedRow == -1) {
			JOptionPane.showMessageDialog(dsv, "파일 선택해주세요.");
			return;
		}

		// 0번 열 = 번호(doc_id) 컬럼
		Object docIdObj = rightModel.getValueAt(rightSelectedRow, 4);
		int docId = Integer.parseInt(docIdObj.toString());

		boolean rightSelectedFlag = sdfs.deleteShare(docId);

		if (rightSelectedFlag) {
			dsv.getLeftModel().removeRow(rightSelectedRow); // 화면에서도 제거
			JOptionPane.showMessageDialog(dsv, "파일이 삭제가 되었습니다");
			loadShareTable();
		} else {
			JOptionPane.showMessageDialog(dsv, "무슨 문제가 있다...");
		}
	}// removeShareRow()

	public void searchDocuText() {
		String keyword = dsv.getDocuSearchField().getText().trim();

		if (keyword.isEmpty()) {
			JOptionPane.showMessageDialog(dsv, "검색어를 입력하셈");
		}

		List<DeptFileVO> searchList = service.searchAllFile(keyword);

		DefaultTableModel model = dsv.getLeftModel();
		model.setRowCount(0); // 기존 행 삭제

		Set<Integer> sharedDocIds = sdfs.allSharedDocids();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int rowCount = 1;
		if (!searchList.isEmpty()) {
			for (DeptFileVO dfVO : searchList) {
				String formattedDate = sdf.format(dfVO.getInputDate());
				boolean isShared = sharedDocIds.contains(dfVO.getNum());
				model.addRow(new Object[] { rowCount++, dfVO.getEmpName(), dfVO.getDeptName(), formattedDate,
						dfVO.getFileName(), isShared, dfVO.getNum() });
			}
		} else {
			JOptionPane.showMessageDialog(dsv, keyword + "가 포함된 파일이 없습니다.");
			return;
		}

	}// searchDocuText

	public void searchShareText() {
		String keyword = dsv.getShareSearchField().getText().trim();
		DefaultTableModel model = dsv.getRightModel();
		model.setRowCount(0); // 기존 데이터 삭제

		List<DeptFileVO> list = sdfs.serachShareTable(keyword);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!list.isEmpty()) {
			int rowNum = 1;
			for (DeptFileVO dfVO : list) {

				model.addRow(new Object[] { rowNum++, dfVO.getDeptName(), dfVO.getFileName(),
						sdf.format(dfVO.getShareData()), dfVO.getDocID() });
			}
		} else {
			JOptionPane.showMessageDialog(dsv, keyword + "가 포함된 파일이 없습니다.");

			List<DeptFileVO> fullList = sdfs.showShareTable();
			int rowNum = 1;
			for (DeptFileVO dfVO : fullList) {
				model.addRow(new Object[] { rowNum++, dfVO.getDeptName(), dfVO.getFileName(),
						sdf.format(dfVO.getShareData()), dfVO.getDocID()

				});
			}
		}
	}

	public void sortFile(String option) {

		List<DeptFileVO> searchList = service.sortAllFile(option);

		DefaultTableModel model = dsv.getLeftModel();
		model.setRowCount(0); // 기존 행 삭제

		Set<Integer> sharedDocIds = sdfs.allSharedDocids();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int rowCount = 1;

		for (DeptFileVO dfVO : searchList) {
			String formattedDate = sdf.format(dfVO.getInputDate());
			boolean isShared = sharedDocIds.contains(dfVO.getNum());
			model.addRow(new Object[] { rowCount++, dfVO.getEmpName(), dfVO.getDeptName(), formattedDate,
					dfVO.getFileName(), isShared, dfVO.getNum() });
		}
	}// sortFile

	public void sortShareFile(String option) {

		List<DeptFileVO> searchList = sdfs.sortShareTable(option);

		DefaultTableModel model = dsv.getRightModel();
		model.setRowCount(0); // 기존 행 삭제

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		int rowCount = 1;

		for (DeptFileVO dfVO : searchList) {
			String formattedDate = sdf.format(dfVO.getShareData());

			model.addRow(new Object[] { rowCount++, dfVO.getDeptName(), dfVO.getFileName(),
					sdf.format(dfVO.getShareData()), dfVO.getDocID() });
		}

	}// sortFile

	public void downloadSelectedFile() {
		JTable leftTable = dsv.getLeftTable();
		JTable rightTable = dsv.getRightTable();

		int selectedLeftRow = leftTable.getSelectedRow();
		int selectedRightRow = rightTable.getSelectedRow();

		int docId = -1;
		String fileName = null;

		// 왼쪽 테이블이 선택된 경우
		if (selectedLeftRow != -1) {
			docId = (int) dsv.getLeftModel().getValueAt(selectedLeftRow, 6); // 문서번호
			fileName = (String) dsv.getLeftModel().getValueAt(selectedLeftRow, 4); // 파일명
		}
		// 오른쪽 테이블이 선택된 경우
		else if (selectedRightRow != -1) {
			docId = (int) dsv.getRightModel().getValueAt(selectedRightRow, 4); // 숨겨진 문서번호 컬럼
			fileName = (String) dsv.getRightModel().getValueAt(selectedRightRow, 2); // 파일명
		}

		if (docId == -1) {
			JOptionPane.showMessageDialog(dsv, "다운로드할 파일을 선택하세요.");
			return;
		}

		try {
			byte[] fileData = service.getFileBlob(docId);

			if (fileData == null) {
				JOptionPane.showMessageDialog(dsv, "파일 데이터가 없습니다.");
				return;
			}

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(new File(fileName));
			int result = fileChooser.showSaveDialog(dsv);

			if (result == JFileChooser.APPROVE_OPTION) {
				File saveFile = fileChooser.getSelectedFile();
				try (FileOutputStream fos = new FileOutputStream(saveFile)) {
					fos.write(fileData);
					JOptionPane.showMessageDialog(dsv, "파일이 성공적으로 저장되었습니다.");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(dsv, "파일 저장 중 오류: " + e.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(dsv, "다운로드 중 오류 발생: " + e.getMessage());
		}
	}// 다운로드

	// 공유테이블로 이동
	public void moveToShareTable() {

		DefaultListModel<DeptFileVO> currentRightModel = dsv.getRightListModel();
		List<DeptFileVO> originalSharedList = dsv.getOriginalSharedList(); 

		// 1. 🔻 현재 오른쪽 리스트 → 공유 INSERT
		for (int i = 0; i < currentRightModel.size(); i++) {
			DeptFileVO dfVO = currentRightModel.get(i);

			// 원래 없었던 것만 insert
			if (!originalSharedList.contains(dfVO)) {
				if (dfVO.getDocID() == 0 || dfVO.getDeptID() == 0) {
					System.out.println("insert 실패: doc_id/dept_id 없음");
					continue;
				}

				boolean success = sdfs.addShareTable(dfVO);
				if (success) {
					loadShareTable();
					System.out.println("공유 성공: " + dfVO.getDeptName());
				} else {
					System.out.println("공유 실패: " + dfVO.getDeptName());
				}
			}
		}

		// 2. 🔻 기존에 있었는데 지금은 오른쪽 리스트에 없는 경우 → 삭제
		for (DeptFileVO original : originalSharedList) {
			if (!currentRightModel.contains(original)) {
				boolean deleted = sdfs.deleteAlreadyShare(original);
				if (deleted) {
					loadShareTable();
					System.out.println("공유 삭제 성공: " + original.getDeptName());
				} else {
					System.out.println("공유 삭제 실패: " + original.getDeptName());
				}
			}
		}

		JOptionPane.showMessageDialog(dsv, "공유 적용이 완료되었습니다.");
		dsv.getShareFrame().dispose();
	}
	// 2번째
	// List<DeptFileVO> originalSharedList = dsv.getOriginalSharedList(); // 이전에
	// 공유됐던 부서들
//		    DefaultListModel<DeptFileVO> rightListModel = dsv.getRightListModel(); // 현재 오른쪽에 보이는 부서들
//
//		    // 현재 공유로 선택된 부서 목록
//		    List<DeptFileVO> currentSharedList = new ArrayList<>();
//		    for (int i = 0; i < rightListModel.size(); i++) {
//		        currentSharedList.add(rightListModel.get(i));
//		    }
//
//		    // ▶ Insert: 현재 있고, 이전에는 없던 부서만 INSERT
//		    for (DeptFileVO curr : currentSharedList) {
//		        if (!originalSharedList.contains(curr)) {
//		            if (curr.getDocID() != 0 && curr.getDeptID() != 0) {
//		                boolean success = sdfs.addShareTable(curr);
//		                if (success) {
//		                    System.out.println("INSERT 성공 → doc_id: " + curr.getDocID() + ", dept_id: " + curr.getDeptID());
//		                } else {
//		                    System.out.println("INSERT 실패 → doc_id: " + curr.getDocID() + ", dept_id: " + curr.getDeptID());
//		                }
//		            }
//		        }
//		    }
//
//		    // ▶ Delete: 이전엔 있었는데 지금 오른쪽에 없는 부서만 DELETE
//		    for (DeptFileVO orig : originalSharedList) {
//		        if (!currentSharedList.contains(orig)) {
//		            boolean deleted = sdfs.deleteAlreadyShare(orig.getDocID(), orig.getDeptID());
//		            if (deleted) {
//		                System.out.println("DELETE 성공 → doc_id: " + orig.getDocID() + ", dept_id: " + orig.getDeptID());
//		            } else {
//		                System.out.println("DELETE 실패 → doc_id: " + orig.getDocID() + ", dept_id: " + orig.getDeptID());
//		            }
//		        }
//		    }

//		    JOptionPane.showMessageDialog(dsv, "공유 상태가 적용되었습니다.");
//		    dsv.getShareFrame().dispose();
//
//		    loadShareTable(); // 오른쪽 테이블 갱신
//	    DefaultListModel<DeptFileVO> rightListModel = dsv.getRightListModel();
//
//	    for (int i = 0; i < rightListModel.size(); i++) {
//	        DeptFileVO dfVO = rightListModel.get(i);
//
//	        JOptionPane.showMessageDialog(dsv, "파일 공유 성공!");
//	        shareFrame.dispose();
//	        
//	        if (dfVO.getDocID() == 0 || dfVO.getDeptID() == 0) {
//	            System.out.println("insert 실패");
//	            continue;
//	        }
//
//	        boolean success = sdfs.addShareTable(dfVO);
//	        if(success){
//	        	List<DeptFileVO> list = sdfs.showShareTable();
//	        	
//	        	dfVO = list.get(list.size() - 1);
//	        	if(dfVO != null) {
//	        		
//	        		DefaultTableModel rightTable = dsv.getRightModel();
//	        		int rowCount = rightTable.getRowCount() +1;
//	        		
//	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        		String formattedDate = sdf.format(dfVO.getShareData());
//	        		
//	        		rightTable.addRow(new Object[] {
//	        				rowCount++,
//	        				dfVO.getDeptName(), 
//	        				dfVO.getFileName(), 
//	        				formattedDate, 
//	        				dfVO.getDocID() });
//	        	}
//	        }
//	        else {
//	            System.out.println("insert 실패: doc_id=" + dfVO.getDocID());
//	        }
//	    }
	// movetosharetable

	// 문서공유 메서드들
	// 행 체크 하면 오른쪽으로 보내는 기능
	public void moveCheckedItemsToRight() {

		DefaultListModel<DeptFileVO> leftModel = dsv.getLeftListModel();
		DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel();

		List<DeptFileVO> selectedItems = dsv.getLeftCheckList().getSelectedValuesList();
		for (DeptFileVO item : selectedItems) {
			if (!rightModel.contains(item)) {
				rightModel.addElement(item);
			}
			leftModel.removeElement(item); // 왼쪽에서 제거
		}
	}

	// 오른쪽버튼 클릭하면 전부 문서공유함으로
	public void moveAllLeftToRight() {

		DefaultListModel<DeptFileVO> leftModel = dsv.getLeftListModel();
		DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel();

		for (int i = 0; i < leftModel.size(); i++) {
			DeptFileVO item = leftModel.getElementAt(i);

			System.out.println("doc_id " + item.getDocID() + ", dept_id: " + item.getDeptID());

			if (!rightModel.contains(item)) {
				rightModel.addElement(item);
			}
		}
		leftModel.clear();
	}

	// 공유함에서 선택하면 부서문서함으로
	public void moveSelectedRightToLeft() {

		DefaultListModel<DeptFileVO> leftModel = dsv.getLeftListModel();
		DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel();

		List<DeptFileVO> selectedItems = dsv.getRightCheckList().getSelectedValuesList();

		for (DeptFileVO item : selectedItems) {
			rightModel.removeElement(item);
			if (!leftModel.contains(item)) {
				leftModel.addElement(item);
			}

		} // moveselected
//	    DefaultListModel<DeptFileVO> leftModel = dsv.getLeftListModel();
//	    DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel();
//
//	    List<DeptFileVO> selectedItems = dsv.getRightCheckList().getSelectedValuesList();
//
//	    for (DeptFileVO item : selectedItems) {
//	        // 먼저 DB에서 삭제
//	        boolean deleted = sdfs.deleteAlreadyShare(item.getDocID(), item.getDeptID());
//	        if (deleted) {
//	            System.out.println("공유 삭제 완료 → doc_id: " + item.getDocID() + ", dept_id: " + item.getDeptID());
//	            loadShareTable();
//
//	            // 리스트에서도 삭제
//	            rightModel.removeElement(item);
//	            if (!leftModel.contains(item)) {
//	                leftModel.addElement(item);
//	            }
//
//	        } else {
//	            JOptionPane.showMessageDialog(dsv, "공유 삭제 실패 (doc_id: " + item.getDocID() + ", dept_id: " + item.getDeptID() + ")");
//	        }
//	    }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();

		// 테이블에서 공유 여부 체크되면 공유 창 열기
		if (source instanceof JTable) {
			JTable table = dsv.getLeftTable();
			int row = table.getSelectedRow();
			int col = table.getSelectedColumn();

			if (col == 5 && row != -1) {
				Boolean isChecked = (Boolean) table.getValueAt(row, col);
				if (isChecked != null && isChecked) {
					int docId = (int) table.getValueAt(row, 6); // 문서 번호 (PK)
					String deptName = (String) table.getValueAt(row, 2); // 부서명
					String fileName = (String) table.getValueAt(row, 4); // 파일명
					String empName = (String) table.getValueAt(row, 1); // 사원명

					int deptId = service.getDeptIdByName(deptName);
					if (deptId == 0) {
						JOptionPane.showMessageDialog(dsv, "부서 ID 조회 실패: " + deptName);
						return;
					}

					DeptFileVO dfVO = new DeptFileVO();
					dfVO.setDocID(docId);
					dfVO.setDeptID(deptId);
					dfVO.setDeptName(deptName);
					dfVO.setFileName(fileName);
					dfVO.setEmpName(empName);
					System.out.println("doc_id: " + dfVO.getDocID() + ", dept_id: " + dfVO.getDeptID());

					List<DeptFileVO> alreadyShared = sdfs.loadAlreadyShareList(docId);
					dsv.setOriginalSharedList(alreadyShared);

					// 공유 창 오픈
					dsv.openShareWindow(dfVO);
					System.out.println("doc_id: " + dfVO.getDocID() + ", dept_id: " + dfVO.getDeptID());
					loadAlreadySharedDepts(docId);
//					initShareWindowLists(dfVO.getDocID());
				}
			}
		}
		// 오른쪽으로 이동
		if (source instanceof JList) {
			JList<DeptFileVO> leftList = dsv.getLeftCheckList();
			DefaultListModel<DeptFileVO> leftModel = dsv.getLeftListModel();
			DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel();

			int index = leftList.locationToIndex(e.getPoint());
			if (index != -1) {
				DeptFileVO item = leftModel.getElementAt(index);
				if (!rightModel.contains(item)) {
					rightModel.addElement(item);
					leftModel.removeElement(item);
				}
			}
		}

	}

	public void loadAlreadySharedDepts(int docId) {
		ShareDeptFileDAO sdfDAO = ShareDeptFileDAO.getInstance();
		List<DeptFileVO> alreadyShared = sdfs.loadAlreadyShareList(docId);

		DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel();

		for (DeptFileVO sharedVO : alreadyShared) {
			if (!rightModel.contains(sharedVO)) {

				rightModel.addElement(sharedVO); // toString으로 deptName만 표시됨
			}
		}
	}// loadALready
	
	public void initShareWindowLists(int docId) {
	    List<DeptFileVO> allDepts = sdfs.getAllDepartment();         // 모든 부서
	    List<DeptFileVO> alreadyShared = sdfs.loadAlreadyShareList(docId); // 이미 공유된 부서

	    DefaultListModel<DeptFileVO> leftModel = dsv.getLeftListModel();   // 공유할 부서 리스트
	    DefaultListModel<DeptFileVO> rightModel = dsv.getRightListModel(); // 이미 공유된 부서 리스트

	    leftModel.clear();
	    rightModel.clear();

	    for (DeptFileVO dept : allDepts) {
	        boolean isShared = false;
	        for (DeptFileVO shared : alreadyShared) {
	            if (dept.getDeptID() == shared.getDeptID()) {
	                isShared = true;
	                break;
	            }
	        }
	        if (isShared) {
	            rightModel.addElement(dept);  // 이미 공유된 건 오른쪽 리스트로
	        } else {
	            leftModel.addElement(dept);   // 나머진 왼쪽 리스트로
	        }
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {

//	    JList<CheckListItem> list = dsv.getLeftCheckList();
//	    if (list == null) return;
//
//	    int index = list.locationToIndex(e.getPoint());
//	    if (index == -1) return;
//
//	    list.ensureIndexIsVisible(index); // 리스트 스크롤 고려
//
//	    Rectangle bounds = list.getCellBounds(index, index);
//	    if (bounds == null) return;
//
//	    // 마우스 클릭이 체크박스 영역(왼쪽 20픽셀) 안에 있는지 확인
//	    if (e.getX() <= bounds.x + 20) {
//	        CheckListItem item = list.getModel().getElementAt(index);
//	        item.setSelected(!item.isSelected());
//	        list.repaint();
//
//	        if (item.isSelected()) {
//	            moveCheckedItemsToRight();
//	        }
//	    }

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

//	@Override
//	public void windowClosing(WindowEvent e) {
//		windowClosing();
//	}
}
