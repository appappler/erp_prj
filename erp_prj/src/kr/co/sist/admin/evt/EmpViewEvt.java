package kr.co.sist.admin.evt;

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

import kr.co.sist.admin.service.EmpService;
import kr.co.sist.admin.view.ChangePassDialog;
import kr.co.sist.admin.view.EmpView;
import kr.co.sist.admin.vo.EmpVO;

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
        } else if (e.getSource() == empView.getJbtnAddEmp()) {
        	addEmp();
        } else if (e.getSource() == empView.getJbtnResetEmp()) {
            resetForm();
        }
    }

    public void loadDeptAndPosition() {
        try {
            List<String> deptList = new EmpService().getAllDeptNames();      // 부서명 목록
            List<String> positionList = new EmpService().getAllPositionNames(); // 직급명 목록

            empView.getJcbDept().removeAllItems();
            empView.getJcbPosition().removeAllItems();

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

        ChangePassDialog dialog = new ChangePassDialog(parent, ChangePassDialog.Mode.REGISTER);

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


    
    //사원을 등록하는 메소드
    private void addEmp() {
        EmpVO eVO = new EmpVO();
        EmpService es = new EmpService();
   
        String name = empView.getJtfName().getText().trim();
        String birth = empView.getJtfBirthDate().getText().trim();
        String hire = empView.getJtfHireDate().getText().trim();
        String contact = empView.getJtfContact().getText().trim();
        String email = empView.getJtfEmail().getText().trim();
        String address = empView.getJtfAddress().getText().trim();
        String deptName = (String) empView.getJcbDept().getSelectedItem();
        String positionName = (String) empView.getJcbPosition().getSelectedItem();

        if (name.isEmpty() || birth.isEmpty() || hire.isEmpty() ||
            contact.isEmpty() || email.isEmpty() || address.isEmpty() ||
            deptName == null || positionName == null || tempPassword == null) {

            JOptionPane.showMessageDialog(empView, "모든 항목을 빠짐없이 입력해주세요.");
            return;
        }

        if (!contact.matches("^010-\\d{4}-\\d{4}$")) {
            JOptionPane.showMessageDialog(empView, "연락처는 010-XXXX-XXXX 형식으로 입력해주세요.");
            return;
        }
        
     // 📌 이메일 형식 검사
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(empView, "유효한 이메일 형식을 입력해주세요.");
            return;
        }
        
        int deptno = es.getDeptnoByName(deptName);
        int positionId = es.getPositionIdByName(positionName);

        eVO.setDept(String.valueOf(deptno)); // VO는 아직 dept(String) 타입일 경우
        eVO.setPosition(String.valueOf(positionId));
        if (tempPassword == null) {
            JOptionPane.showMessageDialog(empView, "비밀번호를 먼저 입력해주세요.");
            return;
        }
        eVO.setPassword(tempPassword);        
        eVO.setEname(empView.getJtfName().getText().trim());
        eVO.setTel(empView.getJtfContact().getText().trim());
        eVO.setEmail(empView.getJtfEmail().getText().trim());
        eVO.setAddress(empView.getJtfAddress().getText().trim());
        eVO.setWorkingFlag("Y");

        try {
            eVO.setBirthDate(Date.valueOf(empView.getJtfBirthDate().getText().trim()));
            eVO.setHireDate(Date.valueOf(empView.getJtfHireDate().getText().trim()));
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(empView, "날짜 형식은 yyyy-mm-dd로 입력하세요.");
            return;
        }

        if (selectedFile != null && selectedFile.exists()) {
            try {
                eVO.setImgInputStream(new FileInputStream(selectedFile));
                eVO.setImgName(selectedFile.getName());  
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }



        boolean flag = es.addEmployee(eVO);

        String msg = "사원 등록 실패";
        if (flag) {
            msg = "사원 등록 성공 - 사번: " + eVO.getEmpno();
            System.out.println("사번 세팅 전: " + empView.getJtfEmpno().getText());
            empView.getJtfEmpno().setText(String.valueOf(eVO.getEmpno()));
            System.out.println("사번 세팅 후: " + empView.getJtfEmpno().getText());
            empView.getJtfEmpno().revalidate();  // layout 갱신
            empView.getJtfEmpno().repaint();     // UI 강제 갱신
         // 인사발령 탭에도 반영 (입사 기록)
            empView.getPersonnelTabPanel().getTableModel().addRow(new Object[]{
                "", // appoint_id는 생략 (숨겨진 컬럼)
                "입사",
                eVO.getHireDate(),
                empView.getJcbDept().getSelectedItem(),
                empView.getJcbPosition().getSelectedItem()
            });

        }

        JOptionPane.showMessageDialog(empView, msg);
       
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