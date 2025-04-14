package kr.co.sist.admin.evt;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.PayrollService;
import kr.co.sist.admin.view.SalaryMonthlyView;
import kr.co.sist.admin.view.SalaryParticularView;
import kr.co.sist.admin.view.SalarySearchView;
import kr.co.sist.admin.vo.PayrollVO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SalaryParticularEvt implements ActionListener {
    private SalaryParticularView view;
    private String empno;
    private boolean isEditing = false;

    public SalaryParticularEvt(SalaryParticularView view, String empno) {
        this.view = view;
        this.empno = empno;

        view.getBtnMonthly().addActionListener(this);

        view.getBtnEdit().addActionListener(e -> {
            if (!isEditing) {
                view.getTfBaseSalary().setEditable(true);
                view.getBtnEdit().setText("수정 완료");
            } else {
                try {
                    int newSalary = Integer.parseInt(view.getTfBaseSalary().getText());
                    String rawPayDate = view.getPayDate();
                    String payday = rawPayDate.contains(" ") ? rawPayDate.split(" ")[0] : rawPayDate;

                    boolean result = PayrollService.getInstance().updateBaseSalary(empno, payday, newSalary);

                    if (result) {
                        JOptionPane.showMessageDialog(view, "기본급이 수정되었습니다.");
                        view.getTfBaseSalary().setEditable(false);
                        view.getBtnEdit().setText("수정하기");

                        // 👉 수정 후 최신 데이터 다시 로드
                        PayrollVO updatedVO = PayrollService.getInstance().getPayrollDetailFreshFromDB(empno, payday);
                        view.loadData(updatedVO);
                        
                     // SalarySearchView 갱신 시도 (있는 경우에만)
                        if (SalarySearchView.getInstanceIfExists() != null) {
                            SalarySearchView searchView = SalarySearchView.getInstanceIfExists();

                            List<PayrollVO> refreshed = PayrollService.getInstance()
                                .searchPayroll(
                                    searchView.getSelectedDept(),
                                    searchView.getSelectedPos(),
                                    searchView.getSelectedYear(),
                                    searchView.getEnteredName()
                                );

                            DefaultTableModel model = searchView.getTableModel();
                            model.setRowCount(0);

                            for (PayrollVO vo : refreshed) {
                                model.addRow(new Object[]{
                                    vo.getPayDate(),
                                    vo.getEmpno(),
                                    vo.getEmp_name(),
                                    vo.getDeptname(),
                                    vo.getPosition_name(),
                                    vo.getBaseSalary(),
                                    vo.getBonus(),
                                    vo.getTotal_deduction(),
                                    vo.getActualSalary()
                                });
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(view, "수정 실패!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "숫자로 입력해주세요.");
                }
            }
            isEditing = !isEditing;
            
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<PayrollVO> list = PayrollService.getInstance().getMonthlyPayrollFromDB(empno);

        SalaryMonthlyView monthlyView = new SalaryMonthlyView();
        monthlyView.loadData(list);
        new SalaryMonthlyEvt(monthlyView, empno);

        JFrame monthlyFrame = new JFrame("월별 급여 보기");
        monthlyFrame.setContentPane(monthlyView);
        monthlyFrame.setSize(700, 500);
        monthlyFrame.setLocationRelativeTo(null);
        monthlyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        monthlyFrame.setVisible(true);
    }
    
    
}
