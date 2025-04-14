package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 */
public class ChangePassDialog extends JDialog {

	private static final long serialVersionUID = -4834582046506201580L;
	public enum Mode { REGISTER, CHANGE }

    private JPasswordField oldPassField;
    private JPasswordField newPassField;
    private JPasswordField confirmPassField;
    private JButton btnOk, btnCancel;

    private Mode mode;

    public ChangePassDialog(Window owner, Mode mode) {
        super(owner, mode == Mode.REGISTER ? "비밀번호 입력" : "비밀번호 변경", ModalityType.APPLICATION_MODAL);
        this.mode = mode;

        JLabel lblOld = new JLabel("현재 비밀번호:");
        JLabel lblNew = new JLabel("새 비밀번호:");
        JLabel lblConfirm = new JLabel("새 비밀번호 확인:");

        oldPassField = new JPasswordField(15);
        newPassField = new JPasswordField(15);
        confirmPassField = new JPasswordField(15);

        // 🔧 숨김 또는 읽기 전용 처리
        if (mode == Mode.CHANGE) {
            oldPassField.setEditable(false); // 수정 불가능 (보안 UX)
        } else {
            lblOld.setVisible(false);
            oldPassField.setVisible(false);
        }

        // 📦 항상 추가 (GridLayout 4행 2열 유지)
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(lblOld);         panel.add(oldPassField);
        panel.add(lblNew);         panel.add(newPassField);
        panel.add(lblConfirm);     panel.add(confirmPassField);

        btnOk = new JButton(mode == Mode.REGISTER ? "등록" : "변경");
        btnCancel = new JButton("취소");
        panel.add(btnOk);          panel.add(btnCancel);

        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(owner);
    }


    public String getOldPassword() {
        return new String(oldPassField.getPassword()).trim();
    }

    public String getNewPassword() {
        return new String(newPassField.getPassword()).trim();
    }

    public String getConfirmPassword() {
        return new String(confirmPassField.getPassword()).trim();
    }

    public JButton getBtnOk() {
        return btnOk;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public Mode getMode() {
        return mode;
    }
    public void setOldPassword(String password) {
        oldPassField.setText(password);
    }
    
}//class
