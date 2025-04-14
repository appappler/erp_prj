package kr.co.sist.admin.evt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

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

        // 콤보박스 초기화
        List<String> years = PayrollService.getInstance().getYearsByEmpno(empno);
        view.populateYearComboBox(years);

        // 리스너 등록
        view.getCbYear().addActionListener(this);
        registerTableDoubleClick();

        // 기본값 자동 선택
        if (!years.isEmpty()) {
            view.getCbYear().setSelectedIndex(0); // 자동 조회
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedYear = (String) view.getCbYear().getSelectedItem();
        if (selectedYear == null) return;

        List<PayrollVO> fullList = PayrollService.getInstance().getMonthlyPayrollFromDB(empno);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (PayrollVO vo : fullList) {
            if (vo.getPayDate().startsWith(selectedYear)) {
                String month = vo.getPayDate().substring(5, 7) + "월";
                model.addRow(new Object[]{
                    month,
                    vo.getBaseSalary(),
                    vo.getBonus(),
                    vo.getTotal_deduction(),
                    vo.getActualSalary()
                });
            }
        }
    }

    private void registerTableDoubleClick() {
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && view.getTable().getSelectedRow() != -1) {
                    int row = view.getTable().getSelectedRow();
                    String monthText = view.getTable().getValueAt(row, 0).toString();
                    String payMonth = monthText.replace("월", "");
                    if (payMonth.length() == 1) payMonth = "0" + payMonth;

                    String selectedYear = (String) view.getCbYear().getSelectedItem();
                    String payDatePrefix = selectedYear + "-" + payMonth;

                    List<PayrollVO> list = PayrollService.getInstance().getMonthlyPayrollFromDB(empno);
                    String matchedPayDate = null;

                    for (PayrollVO vo : list) {
                        if (vo.getPayDate().startsWith(payDatePrefix)) {
                            matchedPayDate = vo.getPayDate();
                            break;
                        }
                    }

                    if (matchedPayDate == null) {
                        JOptionPane.showMessageDialog(view, "해당 월의 상세 정보를 찾을 수 없습니다.");
                        return;
                    }

                    PayrollVO vo = PayrollService.getInstance().getPayrollDetailFreshFromDB(empno, matchedPayDate);

                    SalaryParticularView detailView = new SalaryParticularView(e1 -> {
                        List<PayrollVO> newList = PayrollService.getInstance().getMonthlyPayrollFromDB(empno);
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

                    new SalaryParticularEvt(detailView, empno);
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
