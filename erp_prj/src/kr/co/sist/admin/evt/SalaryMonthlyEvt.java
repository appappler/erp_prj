package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.PayrollService;
import kr.co.sist.admin.view.SalaryMonthlyView;
import kr.co.sist.admin.vo.PayrollVO;

/**
 * 
 */
public class SalaryMonthlyEvt implements ActionListener {
    private SalaryMonthlyView view;
    private String empno;

    public SalaryMonthlyEvt(SalaryMonthlyView view, String empno) {
        this.view = view;
        this.empno = empno;

        view.getCbYear().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedYear = (String) view.getCbYear().getSelectedItem();
        if (selectedYear == null) return;

        // 🔹 전체 리스트 가져오기 (DAO 캐시에서)
        List<PayrollVO> fullList = PayrollService.getInstance().getMonthlyPayroll(empno);

        // 🔹 해당 연도 데이터만 필터링
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (PayrollVO vo : fullList) {
            if (vo.getPayDate().startsWith(selectedYear)) {
                String month = vo.getPayDate().substring(5, 7) + "월";
                model.addRow(new Object[]{
                    month,
                    vo.getSalary(),
                    vo.getBonus(),
                    vo.getTotal_deduction(),
                    vo.getActualSalary()
                });
            }
        }
    }
    
}//class
