package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.DeptService;
import kr.co.sist.admin.view.DeptAddView;
import kr.co.sist.admin.view.DeptDialogView;
import kr.co.sist.admin.view.DeptInfoView;
import kr.co.sist.admin.vo.DeptVO;

public class DeptViewEvt extends MouseAdapter implements MouseListener, ActionListener {

	private DeptService ds;
	private DeptInfoView div;
	private DeptAddView dav;
	private DeptDialogView ddv;
	// DeptInfoView
	private JTextField jtfSearch;
	private JButton jbtSearch;
	private JButton jbtnAdd;
	private JButton jbtnDelete;
	// DeptAddView
	private JButton jbtnDeptAdd;
	// DeptDialogView
	private JButton jbtnDiaModify;

	public DeptViewEvt(DeptInfoView div, DeptAddView dav, DeptDialogView ddv) {

		// DeptService 초기화
		this.ds = new DeptService();

		this.div = div;
		this.dav = dav;
		this.ddv = ddv;

		// DeptInfoView
		jtfSearch = div.getJtfSearch();
		jbtSearch = div.getJbtSearch();
		jbtnAdd = div.getJbtnAdd();
		jbtnDelete = div.getJbtnDelete();

		// DeptAddView
		jbtnDeptAdd = dav.getJbtnDeptAdd();

		// DeptDialogView
		jbtnDiaModify = ddv.getJbtnDiaModify();

		// View 컴포넌트에 이벤트 리스너 등록
		// DeptInfoView
		div.getJbtSearch().addActionListener(this);
		div.getJbtnAdd().addActionListener(this);
		div.getJbtnDelete().addActionListener(this);
		div.getJtDept().addMouseListener(this);
		// DeptAddView
		dav.getJbtnDeptAdd().addActionListener(this);
		// DeptDialogView
		ddv.getJbtnDiaModify().addActionListener(this);
//		ddv.getTable().addMouseListener(this); // 사원명 클릭시 사원명부로 이동

		// 처음 실행시 전체 부서 표시
		showAllDeptList();

		// 부서명 입력 텍스트 필드 클릭시 글자 사라짐
		jtfSearch.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent fe) {
				if (jtfSearch.getText().equals("부서명 입력")) {
					jtfSearch.setText("");
				} // end if
			}// focusGained

			@Override
			public void focusLost(FocusEvent fe) {
				if (jtfSearch.getText().isEmpty()) {
					jtfSearch.setText("부서명 입력");
				} // end if
			}// focusLost
		});// addFocusListener

	}// DeptViewEvt

	// DeptInfoView의 검색 버튼 클릭시 실행
	// 입력된 부서명으로 검색 → 결과를 JTable에 표시
	private void searchDept() {
		// 1. 검색어 가져오기
		String keyword = div.getJtfSearch().getText().trim();
		if (keyword == null || keyword.isEmpty() || keyword.equals("부서명 입력")) {
			showAllDeptList();
			return;
		}
		// 2. 서비스로부터 검색 결과 받기
		List<DeptVO> list = ds.searchAllDept(keyword);
		// 3. JTable의 모델 가져오기 (DefaultTableModel로 다운캐스팅)
		DefaultTableModel model = (DefaultTableModel) div.getJtDept().getModel();
		// 4. 기존 테이블 내용 초기화
		model.setRowCount(0);
		// 5. 검색 결과를 테이블에 추가
		for (DeptVO vo : list) {
			Object[] rowData = { vo.getDeptNum(), vo.getDeptName(), vo.getTel(), vo.getLoc(), vo.getNote(),
					vo.getBonus_rate() };
			model.addRow(rowData);
		} // end for

	}// searchDept

	// DeptInfoView의 "부서 등록" 버튼 클릭 시 실행
	// DeptAddView 화면 생성
	private void addDeptPage() {

		JFrame frame = new JFrame("부서 등록");
		frame.setContentPane(dav); // 또는 this.dav 사용
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null); // 중앙 정렬
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 이 창만 닫힘
		frame.setVisible(true);

		dav.getJtfDeptNum().setText("");
		dav.getJtfDeptName().setText("");
		dav.getJtfDeptTel().setText("");
		dav.getJtfDeptLoc().setText("");
		dav.getJtfDeptNote().setText("");
		dav.getJtfDeptBonus_rate().setText("");
	}

	// DeptAddView의 "등록" 버튼 클릭 시 실행
	// 텍스트필드에서 정보 가져와 DeptVO 객체 생성 → 저장 요청
	// 저장 성공 시 DeptInfoView의 테이블 갱신
	private void addDept() {

		String deptName = dav.getJtfDeptName().getText().trim();
		String tel = dav.getJtfDeptTel().getText().trim();
		String loc = dav.getJtfDeptLoc().getText().trim();
		String note = dav.getJtfDeptNote().getText().trim();
		int deptNum = 0;
		int bonus_rate = 0;

		if (deptName.isEmpty() || tel.isEmpty() || loc.isEmpty() || note.isEmpty()) {
			JOptionPane.showMessageDialog(dav, "정보를 입력해주세요.");
			return;
		}

		try {
			deptNum = Integer.parseInt(dav.getJtfDeptNum().getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(dav, "부서 번호는 숫자만 입력 가능합니다.");
			return;
		}

		try {
			bonus_rate = Integer.parseInt(dav.getJtfDeptBonus_rate().getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(dav, "상여금 지급률은 숫자만 입력 가능합니다.");
			return;
		}

		// 중복 체크
		if (ds.NumExists(deptNum)) {
			JOptionPane.showMessageDialog(dav, "중복된 부서 번호입니다.");
			return;
		}
		if (ds.NameExists(deptName)) {
			JOptionPane.showMessageDialog(dav, "중복된 부서명 입니다.");
			return;
		}
		if (ds.TelExists(tel)) {
			JOptionPane.showMessageDialog(dav, "중복된 연락처 입니다.");
			return;
		}

		// 2. DeptVO 객체 생성
		DeptVO dVO = new DeptVO();
		dVO.setDeptNum(deptNum);
		dVO.setDeptName(deptName);
		dVO.setTel(tel);
		dVO.setLoc(loc);
		dVO.setNote(note);
		dVO.setBonus_rate(bonus_rate);

		// 3. 저장 요청
		boolean result = ds.addDept(dVO);

		// 4. 결과 처리
		if (result) {
			JOptionPane.showMessageDialog(dav, "부서가 등록되었습니다.");
			showAllDeptList();
		}

		dav.getJtfDeptNum().setText("");
		dav.getJtfDeptName().setText("");
		dav.getJtfDeptTel().setText("");
		dav.getJtfDeptLoc().setText("");
		dav.getJtfDeptNote().setText("");
		dav.getJtfDeptBonus_rate().setText("");

	}// addDeptPage

	// DeptInfoView의 "삭제" 버튼 클릭 시 실행
	// JTable에서 선택된 부서를 확인 → 삭제 요청 → 삭제 성공 시 테이블 갱신
	private void removeDept() {// 테이블에서 부서 삭제하기
		JTable jtb = div.getJtDept();
		int selectedRow = jtb.getSelectedRow();

		if (selectedRow == -1) {// 아무 부서도 선택되지 않았을때 띄울 메세지
			JOptionPane.showMessageDialog(div, "삭제할 부서를 선택하세요.");
			return;
		} // end if

		int deptNum = (int) jtb.getValueAt(selectedRow, 0);

		int confirm = JOptionPane.showConfirmDialog(div, "정말 삭제하시겠습니까?");
		if (confirm == JOptionPane.YES_OPTION) {
			boolean result = ds.removeDept(deptNum);
			if (result) {
				JOptionPane.showMessageDialog(div, "삭제 성공");
				showAllDeptList(); // 삭제 후 목록 갱신
			} else {
				JOptionPane.showMessageDialog(div, "삭제 실패");
			} // end else
		} // end if

	}// removeDept

	// DeptInfoView의 테이블에서 부서번호 더블 클릭 시 실행
	// 선택된 부서의 상세 정보를 DeptDialogView에 띄우기
	private void showDeptDialog() {
		int selectedRow = div.getJtDept().getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(div, "수정할 부서를 선택하세요.");
			return;
		}

		// JTable에서 선택한 값 가져오기
		int deptNum = (int) div.getJtDept().getValueAt(selectedRow, 0);
		String deptName = div.getJtDept().getValueAt(selectedRow, 1).toString();
		String tel = div.getJtDept().getValueAt(selectedRow, 2).toString();
		String loc = div.getJtDept().getValueAt(selectedRow, 3).toString();
		String note = div.getJtDept().getValueAt(selectedRow, 4).toString();
		int bonus_rate = (int) div.getJtDept().getValueAt(selectedRow, 5);

		System.out.println(deptNum);
		System.out.println(tel);
		System.out.println(loc);
		System.out.println(deptNum);
		System.out.println(note);
		System.out.println(bonus_rate);
		
		// DeptDialogView에 정보 세팅
		ddv.getJtfDiaNum().setText(String.valueOf(deptNum));
		ddv.getJtfDiaName().setText(deptName);
		ddv.getJtfDiaTel().setText(tel);
		ddv.getJtfDiaLoc().setText(loc);
		ddv.getJtfDiaNote().setText(note);
		ddv.getJtfDiaBonus_rate().setText(String.valueOf(bonus_rate));

		// 사원 목록 불러오기
		List<DeptVO> empList = ds.searchEmp(deptName);
		DefaultTableModel empModel = (DefaultTableModel) ddv.getTable().getModel();
		empModel.setRowCount(0); // 기존 행 제거

		for (DeptVO vo : empList) {
			empModel.addRow(new Object[] { vo.getEmpno(), vo.getEmp_name(), vo.getPosition_name(), vo.getContact() });
		}

		// 다이얼로그 띄우기
		ddv.setSize(500, 300);
		ddv.setLocationRelativeTo(null);
		ddv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ddv.setVisible(true);
	}

	// DeptDialogView의 "수정" 버튼 클릭 시 실행
	// 텍스트필드에서 정보 가져와 DeptVO 수정 요청
	private void modifyDept() {
		try {
			int deptNum = Integer.parseInt(ddv.getJtfDiaNum().getText().trim());
			String deptName = ddv.getJtfDiaName().getText().trim();
			String tel = ddv.getJtfDiaTel().getText().trim();
			String loc = ddv.getJtfDiaLoc().getText().trim();
			String note = ddv.getJtfDiaNote().getText().trim();
			String bonusRateStr = ddv.getJtfDiaBonus_rate().getText().trim();

			if (deptName.isEmpty() || tel.isEmpty() || loc.isEmpty() || note.isEmpty()) {
				JOptionPane.showMessageDialog(ddv, "정보를 입력해주세요.");
				return;
			}

			int bonus_rate;
			try {
				bonus_rate = Integer.parseInt(bonusRateStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(ddv, "상여금 지급률은 숫자만 입력 가능합니다.");
				return;
			}

			// 중복 검사
			if (ds.NameExistsExceptSelf(deptName, deptNum)) {
				JOptionPane.showMessageDialog(ddv, "중복된 부서명입니다.");
				return;
			}

			if (ds.TelExistsExceptSelf(tel, deptNum)) {
				JOptionPane.showMessageDialog(ddv, "중복된 연락처입니다.");
				return;
			}

			// DeptVO 생성
			DeptVO dVO = new DeptVO();
			dVO.setDeptNum(deptNum);
			dVO.setDeptName(deptName);
			dVO.setTel(tel);
			dVO.setLoc(loc);
			dVO.setNote(note);
			dVO.setBonus_rate(bonus_rate); 

			// 수정 요청
			boolean result = ds.modifyDept(dVO);

			if (result) {
				JOptionPane.showMessageDialog(ddv, "수정되었습니다.");
				ddv.dispose();
				showAllDeptList();
			} else {
				JOptionPane.showMessageDialog(ddv, "수정 실패");
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ddv, "숫자 입력 오류: 부서번호와 상여금은 숫자여야 합니다.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ddv, "수정 중 오류 발생: " + e.getMessage());
		}
	}

	public void showAllDeptList() {

		List<DeptVO> list = ds.searchAllDept(null); // 또는 조건 검색
		DefaultTableModel model = (DefaultTableModel) div.getJtDept().getModel(); // DeptInfoView의 모델 가져오기
		model.setRowCount(0); // 기존 내용 지우기

		for (DeptVO vo : list) {
			model.addRow(new Object[] { vo.getDeptNum(), vo.getDeptName(), vo.getTel(), vo.getLoc(), vo.getNote(),
					vo.getBonus_rate() });
		}

	}// showAllDeptList

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == div.getJbtSearch()) {
			searchDept();
		} else if (source == div.getJbtnAdd()) {
			addDeptPage();
		} else if (source == div.getJbtnDelete()) {
			removeDept();
		} else if (source == dav.getJbtnDeptAdd()) {
			addDept();
		} else if (source == ddv.getJbtnDiaModify()) {
			modifyDept();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 부서 상세정보 다이얼로그 창 띄우기
		if (e.getClickCount() == 2) {
			showDeptDialog();
		}

//		if (e.getClickCount() == 2) {
//			Object src = e.getSource();
//			if (src == div.getJtDept()) {
//				showDeptDialog();
//			} else if (src == ddv.getTable()) {
//				showEmployeeListView();
//			}
//		}

	}

	public void windowClosing(WindowEvent we) {

	}

	// 사용 안함
	@Override
	public void mousePressed(MouseEvent e) {

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

}// class
