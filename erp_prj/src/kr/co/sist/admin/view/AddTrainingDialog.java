package kr.co.sist.admin.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import kr.co.sist.admin.evt.EmpTabViewEvt;
import sh.util.PlaceholderUtil;

/**
 * 
 */
public class AddTrainingDialog extends JDialog {

	private static final long serialVersionUID = 6213092631711090741L;
	private JTextField tfInstitute, tfProgram, tfStartDate, tfEndDate, tfStatus;
    private JButton btnOK, btnCancel;

    public AddTrainingDialog(EmpView ev, EmpTabViewEvt evt) {
//        super(ev, "교육 이력 추가", ModalityType.APPLICATION_MODAL);
        setLayout(null);
        setBounds(100, 100, 480, 310);

        JLabel lbl1 = new JLabel("교육기관");
        lbl1.setBounds(50, 30, 100, 25);
        add(lbl1);

        tfInstitute = new JTextField();
        tfInstitute.setBounds(150, 30, 250, 25);
        add(tfInstitute);

        JLabel lbl2 = new JLabel("교육명");
        lbl2.setBounds(50, 65, 100, 25);
        add(lbl2);

        tfProgram = new JTextField();
        tfProgram.setBounds(150, 65, 250, 25);
        add(tfProgram);

        JLabel lbl3 = new JLabel("시작일자");
        lbl3.setBounds(50, 100, 100, 25);
        add(lbl3);

        tfStartDate =new JTextField("yyyy-mm-dd");
        PlaceholderUtil.applyDatePlaceholder(tfStartDate);
        tfStartDate.setBounds(150, 100, 250, 25);
        add(tfStartDate);

        JLabel lbl4 = new JLabel("종료일자");
        lbl4.setBounds(50, 135, 100, 25);
        add(lbl4);

        tfEndDate = new JTextField("yyyy-mm-dd");
        PlaceholderUtil.applyDatePlaceholder(tfEndDate);
        tfEndDate.setBounds(150, 135, 250, 25);
        add(tfEndDate);

        JLabel lbl5 = new JLabel("수료여부");
        lbl5.setBounds(50, 170, 100, 25);
        add(lbl5);

        tfStatus = new JTextField();
        tfStatus.setBounds(150, 170, 250, 25);
        add(tfStatus);

        btnOK = new JButton("OK");
        btnOK.setBounds(150, 220, 80, 30);
        btnOK.addActionListener(evt);
        add(btnOK);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(250, 220, 80, 30);
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    // Getter methods
    public String getInstitute() {
        return tfInstitute.getText().trim();
    }

    public String getProgram() {
        return tfProgram.getText().trim();
    }

    public String getStartDate() {
        return tfStartDate.getText().trim();
    }

    public String getEndDate() {
        return tfEndDate.getText().trim();
    }

    public String getStatus() {
        return tfStatus.getText().trim();
    }

    public JButton getBtnOK() {
        return btnOK;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
    
}//class
