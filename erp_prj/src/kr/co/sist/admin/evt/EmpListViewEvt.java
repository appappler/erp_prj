package kr.co.sist.admin.evt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.EmpService;
import kr.co.sist.admin.view.EmpDetailView;
import kr.co.sist.admin.view.EmpListView;
import kr.co.sist.admin.vo.EmpVO;

/**
 * 
 */
public class EmpListViewEvt extends MouseAdapter implements ActionListener, FocusListener{
	private EmpListView elv;
	
	public EmpListViewEvt(EmpListView elv) {
		this.elv=elv;
		
	}//EmpListViewEvt

	@Override
	public void mouseClicked(MouseEvent e) {
	    if (e.getSource() == elv.getJtbEmpList()) {
	    	int viewRow = elv.getJtbEmpList().getSelectedRow();
	    	if (viewRow == -1) return;

	    	int modelRow = elv.getJtbEmpList().convertRowIndexToModel(viewRow); // 🔥 핵심
	    	int empno = Integer.parseInt(elv.getDtm().getValueAt(modelRow, 0).toString());

	        // 사원 상세 정보 조회 후 EmpDetailView 띄우기
	        try {
	            EmpVO vo = new EmpService().getEmployeeByEmpno(empno);
	            if (vo == null) {
	                JOptionPane.showMessageDialog(elv, "사원 정보를 불러오지 못했습니다.");
	                return;
	            }
	            JDialog dialog = new JDialog((JFrame) null, "사원 상세정보", true); // true: modal
	            dialog.setLayout(null); // 패널 위치 조정 가능하도록 null layout 사용

	            EmpDetailView edv = new EmpDetailView(vo, elv);
	            edv.setBounds(80, 0, 900, 700); // ← 오른쪽으로 50px 밀고 크기 지정
	            dialog.add(edv);

	            dialog.setSize(1000, 720);
	            dialog.setLocationRelativeTo(null);
	            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	            dialog.setVisible(true);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(elv, "사원 정보를 불러오지 못했습니다.");
	        }
	    }
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

	    if (src == elv.getJtfSearchName() || src == elv.getJbtnSearch()) {
	        // 검색 버튼 클릭: 전체 검색 실행 + 결과 없으면 메시지 띄움
	        showAllEmpList(true);
	    } else if (src == elv.getJcbDept() || src == elv.getJcbPosition()) {
	        // 콤보박스 변경: 자동 검색 실행, 메시지는 띄우지 않음
	        showAllEmpList(false);
	    }
	}

	public void showAllEmpList(boolean showNoResultMsg) {
	    EmpService es = new EmpService();

	    String dept = String.valueOf(elv.getJcbDept().getSelectedItem());
	    String position = String.valueOf(elv.getJcbPosition().getSelectedItem());
	    String name = elv.getJtfSearchName().getText().trim();

	    if ("전체".equals(dept)) dept = null;
	    if ("전체".equals(position)) position = null;
	    if ("사원명".equals(name) || name.isEmpty()) name = null;

	    List<EmpVO> list = es.searchAllEmployees(dept, position, name);
	    System.out.println("🔍 검색 결과 개수: " + list.size());

	    DefaultTableModel dtm = elv.getDtm();
	    dtm.setRowCount(0);

	    if (list.isEmpty()) {
	        if (showNoResultMsg) {
	            JOptionPane.showMessageDialog(elv, "검색 결과가 없습니다.");
	        }
	        return;
	    }

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    for (EmpVO vo : list) {
	        dtm.addRow(new Object[]{
	            vo.getEmpno(),
	            vo.getEname(),
	            vo.getDept(),
	            vo.getPosition(),
	            sdf.format(vo.getBirthDate()),
	            vo.getTel(),
	            vo.getEmail(),
	            vo.getWorkingFlag()
	        });
	    }
	}


	@Override
	public void focusGained(FocusEvent e) {
	    if (elv.getJtfSearchName().getText().equals("사원명")) {
	        elv.getJtfSearchName().setText("");
	        elv.getJtfSearchName().setForeground(Color.BLACK);
	    }
	}

	@Override
	public void focusLost(FocusEvent e) {
	    String text = elv.getJtfSearchName().getText().trim();
	    if (text.isEmpty()) {
	        elv.getJtfSearchName().setText("사원명");
	        elv.getJtfSearchName().setForeground(Color.GRAY);
	    }
	}

}//class
