package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.PayrollService;
import kr.co.sist.admin.view.SalaryParticularView;
import kr.co.sist.admin.view.SalarySearchView;
import kr.co.sist.admin.vo.PayrollVO;

/**
 * 
 */
public class SalarySearchEvt implements ActionListener {
    private SalarySearchView view;
    private PayrollService service;

    public SalarySearchEvt(SalarySearchView view) {
        this.view = view;
        this.service = PayrollService.getInstance();

        // 🔸 검색 버튼 이벤트
        view.getBtnSearch().addActionListener(this);

        // 🔸 콤보박스 초기화
        view.populateComboBoxes(service);

        // 🔸 테이블 더블 클릭 이벤트
        view.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && view.getTable().getSelectedRow() != -1) {
                    int row = view.getTable().getSelectedRow();
                    String empno = view.getTable().getValueAt(row, 1).toString();
                    String payday = view.getTable().getValueAt(row, 0).toString();

                    openSalaryParticularWindow(empno, payday);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String dept = (String) view.getCbDept().getSelectedItem();
        String pos = (String) view.getCbPosition().getSelectedItem();
        String year = (String) view.getCbYear().getSelectedItem();
        String name = view.getTfName().getText().trim();

        dept = "부서".equals(dept) ? null : dept;
        pos = "직급".equals(pos) ? null : pos;
        year = "년도".equals(year) ? null : year;
        name = name.isEmpty() ? null : name;

        List<PayrollVO> result = service.searchPayroll(dept, pos, year, name);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (PayrollVO vo : result) {
            model.addRow(new Object[]{
                vo.getPayDate(),
                vo.getEmpno(),
                vo.getEmp_name(),
                vo.getDeptname(),
                vo.getPosition_name(),
                vo.getSalary(),
                vo.getBonus(),
                vo.getTotal_deduction(),
                vo.getActualSalary()
            });
        }
    }

    private void openSalaryParticularWindow(String empno, String payday) {
        PayrollVO vo = service.getPayrollDetail(empno, payday);
        SalaryParticularView detailView = new SalaryParticularView(null);
        detailView.loadData(vo);
        new SalaryParticularEvt(detailView, empno);

        JFrame detailFrame = new JFrame("급여 명세서 상세보기");
        detailFrame.setContentPane(detailView);
        detailFrame.setSize(700, 500);
        detailFrame.setLocationRelativeTo(null);
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setVisible(true);
    }
    
}//class
