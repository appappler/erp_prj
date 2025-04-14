package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import kr.co.sist.admin.evt.EmpTabViewEvt;
import kr.co.sist.admin.service.EmpService;
import sh.util.PlaceholderUtil;

/**
 * 
 */
public class AddPersonnelDialog extends JDialog {
	
	private static final long serialVersionUID = 758575259077451837L;
	private final JPanel contentPanel = new JPanel();

    private JTextField tfAppointmentDate;     // 발령일자
    private JComboBox<String> cbAppointment;

    private JComboBox<String> cbDept;         // 발령부서
    private JComboBox<String> cbPosition;     // 발령직급

    private JButton btnOK;
    private JButton btnCancel;

    public AddPersonnelDialog(EmpView ev, EmpTabViewEvt etve) {
    	 super(SwingUtilities.getWindowAncestor(ev), "인사 이력 추가", ModalityType.APPLICATION_MODAL);
    	    setLayout(null);
    	    setSize(450, 300);
    	    setLocationRelativeTo(SwingUtilities.getWindowAncestor(ev));        
        
        etve.setPersonnelDialog(this);


        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblAppointment = new JLabel("발령구분:");
        lblAppointment.setBounds(50, 30, 100, 20);
        contentPanel.add(lblAppointment);

        // 발령구분 콤보박스
        cbAppointment = new JComboBox<>(new String[]{"입사", "부서이동", "승진", "퇴사", "기타"});
        cbAppointment.setBounds(150, 30, 200, 24);
        contentPanel.add(cbAppointment);
        
        cbAppointment.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                boolean isRetired = "퇴사".equals(e.getItem().toString());
                cbDept.setEnabled(!isRetired);
                cbPosition.setEnabled(!isRetired);
            }
        });

        JLabel lblAppointmentDate = new JLabel("발령일자:");
        lblAppointmentDate.setBounds(50, 65, 100, 20);
        contentPanel.add(lblAppointmentDate);

        tfAppointmentDate = new JTextField("yyyy-mm-dd");
		PlaceholderUtil.applyDatePlaceholder(tfAppointmentDate);
        tfAppointmentDate.setBounds(150, 65, 200, 24);
        contentPanel.add(tfAppointmentDate);
        tfAppointmentDate.setColumns(10);

        JLabel lblDept = new JLabel("발령부서:");
        lblDept.setBounds(50, 100, 100, 20);
        contentPanel.add(lblDept);

        cbDept = new JComboBox<>();
        cbDept.setBounds(150, 100, 200, 24);
        contentPanel.add(cbDept);

        JLabel lblPosition = new JLabel("발령직급:");
        lblPosition.setBounds(50, 135, 100, 20);
        contentPanel.add(lblPosition);

        cbPosition = new JComboBox<>();
        cbPosition.setBounds(150, 135, 200, 24);
        contentPanel.add(cbPosition);

        // 하단 버튼
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnOK = new JButton("OK");
        btnOK.addActionListener(etve);
        buttonPane.add(btnOK);
        getRootPane().setDefaultButton(btnOK);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        buttonPane.add(btnCancel);

        loadDeptAndPosition();
    }

    private void loadDeptAndPosition() {
        try {
            List<String> deptList = new EmpService().getAllDeptNames();
            for (String dept : deptList) {
                cbDept.addItem(dept);
            }

            List<String> posList = new EmpService().getAllPositionNames();
            for (String pos : posList) {
                cbPosition.addItem(pos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    // Getter methods
    public String getSelectedAppointment() {
        return cbAppointment.getSelectedItem().toString();
    }

    public String getAppointmentDate() {
        return tfAppointmentDate.getText().trim();
    }

    public String getAppDept() {
        return cbDept.isEnabled() ? cbDept.getSelectedItem().toString() : null;
    }

    public String getAppPosition() {
        return cbPosition.isEnabled() ? cbPosition.getSelectedItem().toString() : null;
    }

    public JButton getBtnOK() {
        return btnOK;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
    
}//class
