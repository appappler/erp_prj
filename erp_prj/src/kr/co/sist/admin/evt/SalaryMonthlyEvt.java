package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.PayrollService;
import kr.co.sist.admin.view.SalaryMonthlyView;
import kr.co.sist.admin.view.SalaryParticularView;
import kr.co.sist.admin.vo.PayrollVO;

public class SalaryMonthlyEvt implements ActionListener {
    private SalaryMonthlyView view;
    private String empno;

    public SalaryMonthlyEvt(SalaryMonthlyView view, String empno) {
        this.view = view;
        this.empno = empno;

        view.getCbYear().addActionListener(this);
        registerTableDoubleClick(); // 추가
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedYear = (String) view.getCbYear().getSelectedItem();
        if (selectedYear == null) return;

        List<PayrollVO> fullList = PayrollService.getInstance().getMonthlyPayroll(empno);

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

    // 🔹 테이블 더블 클릭 리스너
    private void registerTableDoubleClick() {
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && view.getTable().getSelectedRow() != -1) {
                    int row = view.getTable().getSelectedRow();

                    String monthText = view.getTable().getValueAt(row, 0).toString(); // "04월"
                    String payMonth = monthText.replace("월", "");
                    if (payMonth.length() == 1) payMonth = "0" + payMonth;

                    String selectedYear = (String) view.getCbYear().getSelectedItem();
                    String payDatePrefix = selectedYear + "-" + payMonth;

                    // 🔍 캐시에서 정확한 payDate 찾기
                    List<PayrollVO> list = PayrollService.getInstance().getMonthlyPayroll(empno);
                    String matchedPayDate = null;

                    for (PayrollVO vo : list) {
                        if (vo.getPayDate().startsWith(payDatePrefix)) {
                            matchedPayDate = vo.getPayDate(); // 예: "2025-04-01"
                            break;
                        }
                    }

                    if (matchedPayDate == null) {
                        JOptionPane.showMessageDialog(view, "해당 월의 상세 정보를 찾을 수 없습니다.");
                        return;
                    }

                    PayrollVO vo = PayrollService.getInstance().getPayrollDetail(empno, matchedPayDate);

                    SalaryParticularView detailView = new SalaryParticularView(e1 -> {
                        List<PayrollVO> newList = PayrollService.getInstance().getMonthlyPayroll(empno);
                        SalaryMonthlyView monthlyView = new SalaryMonthlyView();
                        monthlyView.loadData(newList);
                        new SalaryMonthlyEvt(monthlyView, empno);

                        JFrame monthlyFrame = new JFrame("월별 급여 보기");
                        monthlyFrame.setContentPane(monthlyView);
                        monthlyFrame.setSize(700, 500);
                        monthlyFrame.setLocationRelativeTo(null);
                        monthlyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        monthlyFrame.setVisible(true);
                    });

                    detailView.loadData(vo);

                    JFrame frame = new JFrame("급여 상세 보기");
                    frame.setContentPane(detailView);
                    frame.setSize(700, 500);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                }
            }
        });
    }

}





/*

package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.PayrollService;
import kr.co.sist.admin.view.SalaryMonthlyView;
import kr.co.sist.admin.vo.PayrollVO;


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

*/
