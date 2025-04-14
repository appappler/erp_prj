package kr.co.sist.user.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.service.PayrollService;
import kr.co.sist.user.view.SalaryParticularView;
import kr.co.sist.user.view.SalarySearchEmployeeView;
import kr.co.sist.user.vo.PayrollVO;

public class SalarySearchEmployeeEvt implements ActionListener {
    private SalarySearchEmployeeView view;
    private PayrollService service;

    public SalarySearchEmployeeEvt(SalarySearchEmployeeView view) {
        this.view = view;
        this.service = PayrollService.getInstance();

        // 🔸 확인 버튼 이벤트 등록
        view.getBtnConfirm().addActionListener(this);

        // 🔸 연도 콤보박스 세팅
        view.populateYearComboBox(service);

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
        String year = (String) view.getCbYear().getSelectedItem();
        if ("년도".equals(year)) {
            JOptionPane.showMessageDialog(view, "연도를 선택해주세요.");
            return;
        }

        String empno = view.getLoginEmpno();
        List<PayrollVO> result = service.searchPayroll(empno, year);

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

    // 🔸 상세 보기 창 열기
    private void openSalaryParticularWindow(String empno, String payday) {
        PayrollVO vo = service.getPayrollDetail(empno, payday);
        SalaryParticularView detailView = new SalaryParticularView();
        detailView.loadData(vo);

        JFrame detailFrame = new JFrame("급여 명세서 상세보기");
        detailFrame.setContentPane(detailView);
        detailFrame.setSize(700, 500);
        detailFrame.setLocationRelativeTo(null);
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setVisible(true);
    }
}
