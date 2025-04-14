package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;

import kr.co.sist.admin.service.PayrollService;
import kr.co.sist.admin.view.SalaryMonthlyView;
import kr.co.sist.admin.view.SalaryParticularView;
import kr.co.sist.admin.vo.PayrollVO;

/**
 * 
 */
public class SalaryParticularEvt implements ActionListener {
    private SalaryParticularView view;
    private String empno;

    public SalaryParticularEvt(SalaryParticularView view, String empno) {
        this.view = view;
        this.empno = empno;

        view.getBtnMonthly().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<PayrollVO> list = PayrollService.getInstance().getMonthlyPayroll(empno);

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
    
}//class
