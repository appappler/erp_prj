package kr.co.sist.admin.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import kr.co.sist.admin.evt.EmpTabViewEvt;

/**
 * 
 */
public class AddCertDialog extends JDialog {
	
	private static final long serialVersionUID = 2700158877704135813L;
	private JTextField tfCertName, tfIssuer, tfIssueDate, tfExpiryDate;
    private JButton btnOK, btnCancel;

    public AddCertDialog(EmpView ev, EmpTabViewEvt evt) {
//        super(ev, "자격증 추가", ModalityType.APPLICATION_MODAL);
        setLayout(null);
        setBounds(100, 100, 450, 300);

        JLabel lbl1 = new JLabel("자격증명");
        lbl1.setBounds(50, 30, 100, 25);
        add(lbl1);

        tfCertName = new JTextField();
        tfCertName.setBounds(150, 30, 200, 25);
        add(tfCertName);

        JLabel lbl2 = new JLabel("발급기관");
        lbl2.setBounds(50, 65, 100, 25);
        add(lbl2);

        tfIssuer = new JTextField();
        tfIssuer.setBounds(150, 65, 200, 25);
        add(tfIssuer);

        JLabel lbl3 = new JLabel("취득일자");
        lbl3.setBounds(50, 100, 100, 25);
        add(lbl3);

        tfIssueDate = new JTextField("yyyy-mm-dd");
        tfIssueDate.setBounds(150, 100, 200, 25);
        add(tfIssueDate);

        JLabel lbl4 = new JLabel("유효일자");
        lbl4.setBounds(50, 135, 100, 25);
        add(lbl4);

        tfExpiryDate = new JTextField("");
        tfExpiryDate.setBounds(150, 135, 200, 25);
        add(tfExpiryDate);

        btnOK = new JButton("OK");
        btnOK.setBounds(150, 190, 80, 30);
        btnOK.addActionListener(evt);
        add(btnOK);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(250, 190, 80, 30);
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    public String getCertName() {
        return tfCertName.getText().trim();
    }

    public String getIssuer() {
        return tfIssuer.getText().trim();
    }

    public String getIssueDate() {
        return tfIssueDate.getText().trim();
    }

    public String getExpiryDate() {
        return tfExpiryDate.getText().trim();
    }

    public JButton getBtnOK() {
        return btnOK;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
    
}//class
