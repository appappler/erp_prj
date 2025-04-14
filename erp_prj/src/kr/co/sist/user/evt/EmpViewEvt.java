package kr.co.sist.user.evt;

import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import kr.co.sist.user.service.EmpService;
import kr.co.sist.user.view.ChangePassDialog;
import kr.co.sist.user.view.EmpView;
import kr.co.sist.user.vo.EmpVO;

/**
 * 
 */
public class EmpViewEvt implements ActionListener {

    private EmpView empView;
    private File selectedFile;
    private String tempPassword = null;  // 클래스 필드로 추가

    public EmpViewEvt(EmpView empView) {
        this.empView = empView;
        loadDeptAndPosition();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == empView.getJbtnEditImg()) {
            editImg();
        } else if (e.getSource() == empView.getJbtnInputPass()) {
            inputPass();  // ✅ 신규 메서드 추가
        } else if (e.getSource() == empView.getJbtnResetEmp()) {
            resetForm();
        }
    }

    public void loadDeptAndPosition() {
        try {
            List<String> deptList = new EmpService().getAllDeptNames();      // 부서명 목록
            List<String> positionList = new EmpService().getAllPositionNames(); // 직급명 목록

            if( empView.getJcbDept()!=null) {
            	empView.getJcbDept().removeAllItems();
            }
            if( empView.getJcbPosition()!=null) {
            	empView.getJcbPosition().removeAllItems();
            }

            for (String dept : deptList) {
                empView.getJcbDept().addItem(dept);
            }

            for (String pos : positionList) {
                empView.getJcbPosition().addItem(pos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 사원의 증명사진을 수정하는 메소드
    private void editImg() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(empView);
        selectedFile = fileChooser.getSelectedFile();

        if (selectedFile != null && selectedFile.exists()) {
        	 ImageIcon originalIcon = new ImageIcon(selectedFile.getAbsolutePath());
             Image scaledImage = originalIcon.getImage().getScaledInstance(
                 empView.getJlblImg().getWidth(),
                 empView.getJlblImg().getHeight(),
                 Image.SCALE_SMOOTH
             );
             empView.getJlblImg().setIcon(new ImageIcon(scaledImage));
            // 파일을 DB에 저장하는 로직을 추가하세요.
        } else {
            JOptionPane.showMessageDialog(empView, "파일이 존재하지 않습니다.");
        }
    }

    
    // 사원의 비밀번호를 압력하는 메소드 
    private void inputPass() {
    	Window parent = SwingUtilities.getWindowAncestor(empView);

        ChangePassDialog dialog = new ChangePassDialog(parent, ChangePassDialog.Mode.CHANGE);

        dialog.getBtnOk().addActionListener(ev -> {
            String newPw = dialog.getNewPassword();
            String confirmPw = dialog.getConfirmPassword();

            if (newPw.isEmpty() || confirmPw.isEmpty()) {
                JOptionPane.showMessageDialog(empView, "비밀번호를 입력해주세요.");
            } else if (!newPw.equals(confirmPw)) {
                JOptionPane.showMessageDialog(empView, "비밀번호가 일치하지 않습니다.");
            } else {
                tempPassword = newPw;
                empView.getJtfPass().setText(tempPassword); // ✅ 뷰에 비밀번호 표시
                JOptionPane.showMessageDialog(empView, "비밀번호가 설정되었습니다.");
                dialog.dispose();
            }
        });

        dialog.getBtnCancel().addActionListener(ev -> dialog.dispose());
        dialog.setVisible(true);

    }


    
    
    
    private void resetForm() {
        empView.getJtfEmpno().setText("자동생성");
        empView.getJtfName().setText("");
        empView.getJtfBirthDate().setText("");
        empView.getJtfContact().setText("");
        empView.getJtfEmail().setText("");
        empView.getJtfAddress().setText("");
        empView.getJtfHireDate().setText("");
        empView.getJlblImg().setIcon(null);
        empView.getJtfPass().setText("");

        empView.getJbtnAddEmp().setEnabled(true);

        // 하위 탭 테이블들도 초기화
        empView.getEduTabPanel().resetTable();
        empView.getCareerTabPanel().resetTable();
        empView.getCertTabPanel().resetTable();
        empView.getPersonnelTabPanel().resetTable();
        empView.getTrainingTabPanel().resetTable();
        
        empView.getJcbDept().setSelectedIndex(0);     // 부서 콤보박스 초기화
        empView.getJcbPosition().setSelectedIndex(0); // 직급 콤보박스 초기화
    }
    
    
}//class