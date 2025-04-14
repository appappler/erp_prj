package kr.co.sist.user.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.vo.PayrollVO;

public class SalaryParticularView extends JPanel {
    private JLabel lblEmpNo = new JLabel();
    private JLabel lblEmpName = new JLabel();
    private JLabel lblDept = new JLabel();
    private JLabel lblPosition = new JLabel();
    private JLabel lblHireDate = new JLabel();
    private JLabel lblPayDate = new JLabel();

    private JTable tableDeduction, tablePayment;
    private JLabel lblTotalDeduction = new JLabel();
    private JLabel lblActualSalary = new JLabel();

    public SalaryParticularView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 상단 - 사원 정보
        JPanel infoPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("사원 기본 정보"));
        infoPanel.setPreferredSize(new Dimension(100, 120));

        infoPanel.add(new JLabel("사원번호")); infoPanel.add(lblEmpNo);
        infoPanel.add(new JLabel("성명")); infoPanel.add(lblEmpName);
        infoPanel.add(new JLabel("부서")); infoPanel.add(lblDept);
        infoPanel.add(new JLabel("직급")); infoPanel.add(lblPosition);
        infoPanel.add(new JLabel("입사일")); infoPanel.add(lblHireDate);
        infoPanel.add(new JLabel("지급일자")); infoPanel.add(lblPayDate);

        add(infoPanel, BorderLayout.NORTH);

        // 🔹 중앙 - 공제 / 수령 테이블
        String[] deductionCol = {"공제 항목", "금액"};
        tableDeduction = new JTable(new DefaultTableModel(deductionCol, 0));
        styleTable(tableDeduction);

        String[] paymentCol = {"수령 항목", "금액"};
        tablePayment = new JTable(new DefaultTableModel(paymentCol, 0));
        styleTable(tablePayment);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        centerPanel.add(wrapTableWithTitle("공제 내역", tableDeduction));
        centerPanel.add(wrapTableWithTitle("수령 내역", tablePayment));

        add(centerPanel, BorderLayout.CENTER);

        // 🔹 하단 - 총액 (버튼 제거)
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel amountPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        amountPanel.setBorder(BorderFactory.createTitledBorder("합계"));
        amountPanel.setPreferredSize(new Dimension(100, 80));
        amountPanel.add(new JLabel("공제 총액")); amountPanel.add(lblTotalDeduction);
        amountPanel.add(new JLabel("실수령액")); amountPanel.add(lblActualSalary);

        bottomPanel.add(amountPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(24);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));
        table.setDefaultEditor(Object.class, null);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
    }

    private JPanel wrapTableWithTitle(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    public void loadData(PayrollVO vo) {
        lblEmpNo.setText(vo.getEmpno());
        lblEmpName.setText(vo.getEmp_name());
        lblDept.setText(vo.getDeptname());
        lblPosition.setText(vo.getPosition_name());
        lblHireDate.setText(vo.getHireDate());
        lblPayDate.setText(vo.getPayDate());

        DefaultTableModel deductionModel = (DefaultTableModel) tableDeduction.getModel();
        deductionModel.setRowCount(0);
        deductionModel.addRow(new Object[]{"소득세", vo.getIncomeTax()});
        deductionModel.addRow(new Object[]{"지방소득세", vo.getLocalIncomeTax()});
        deductionModel.addRow(new Object[]{"국민연금", vo.getNationalPension()});
        deductionModel.addRow(new Object[]{"건강보험", vo.getHealthInsurance()});
        deductionModel.addRow(new Object[]{"고용보험", vo.getEmploymentInsurance()});
        deductionModel.addRow(new Object[]{"장기요양보험", vo.getLongTermCareInsurance()});

        DefaultTableModel payModel = (DefaultTableModel) tablePayment.getModel();
        payModel.setRowCount(0);
        payModel.addRow(new Object[]{"급여", vo.getBaseSalary()});
        payModel.addRow(new Object[]{"상여금", vo.getBonus()});

        lblTotalDeduction.setText(String.format("%,d 원", vo.getTotal_deduction()));
        lblActualSalary.setText(String.format("%,d 원", vo.getActualSalary()));
    }
}
