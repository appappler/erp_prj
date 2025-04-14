package kr.co.sist.user.evt;

import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.user.service.AppointmentService;
import kr.co.sist.user.service.CareerService;
import kr.co.sist.user.service.CertService;
import kr.co.sist.user.service.EduService;
import kr.co.sist.user.service.EmpService;
import kr.co.sist.user.service.TrainingService;
import kr.co.sist.user.view.ChangePassDialog;
import kr.co.sist.user.view.EmpDetailView;
import kr.co.sist.user.vo.AppointmentVO;
import kr.co.sist.user.vo.CareerVO;
import kr.co.sist.user.vo.CertVO;
import kr.co.sist.user.vo.EduVO;
import kr.co.sist.user.vo.EmpVO;
import kr.co.sist.user.vo.TrainingVO;

/**
 * 
 */
public class EmpDetailViewEvt implements ActionListener {

    private EmpDetailView detailView;

    public EmpDetailViewEvt(EmpDetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == detailView.getBtnModify()) {
            enableEditing();
        } else if (src == detailView.getBtnSave()) {
        	try {
                saveChanges();              // 기본정보 수정
             //
                disableEditing();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 저장 중 오류 발생: " + ex.getMessage());
            }
        }else if (src == detailView.getJbtnEditImg()) {
	                detailView.chooseImage(); // 선택된 파일이 없을 때만 실행
	                // 👇 핵심 추가
	                File chosen = detailView.getSelectedFile(); // 이미 chooseImage 내부에서 설정됨
	                detailView.setSelectedFile(chosen); // 명시적 재지정 (안 하면 null로 인식됨)
        }




        // 나머지 탭의 버튼 이벤트도 비슷하게 추가
    }

    private void enableEditing() {
        // ✅ 기본 인적사항 필드 설정
        detailView.getJtfContact().setEditable(true);
        detailView.getJtfEmail().setEditable(true);
        detailView.getJtfAddress().setEditable(true);
        detailView.getJbtnEditImg().setVisible(true);
        detailView.getJbtnEditPass().setVisible(true);


        // ❌ 나머지 필드는 수정 불가
        detailView.getJtfEmpno().setEditable(false);
       
        detailView.getJtfName().setEditable(false);
        detailView.getJtfBirthDate().setEditable(false);
        detailView.getJtfHireDate().setEditable(false);
        
        detailView.getJpfPass().setEditable(false);
        detailView.getJcbDept().setEnabled(false);
        detailView.getJcbPosition().setEnabled(false);

        // ✅ 하단 버튼
        detailView.getBtnModify().setVisible(false);
        detailView.getBtnSave().setVisible(true);
        detailView.getJbtnResetEmp().setVisible(false);

        // ✅ 탭별 패널: 버튼 표시, 테이블 수정 가능
        detailView.getEduTabPanel().setTableEditable(false);

        detailView.getCareerTabPanel().setTableEditable(false);

        detailView.getCertTabPanel().setTableEditable(false);

        detailView.getPersonnelTabPanel().setTableEditable(false);

        detailView.getTrainingTabPanel().setTableEditable(false);

    }

    private void saveChanges() {
        try {
            String empnoStr = detailView.getJtfEmpno().getText().trim();
            String pass = new String(detailView.getJpfPass().getPassword()).trim();
            String contact = detailView.getJtfContact().getText().trim();
            String email = detailView.getJtfEmail().getText().trim();
            String address = detailView.getJtfAddress().getText().trim();

            if (empnoStr.isEmpty() || pass.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty()) {
                throw new RuntimeException("모든 필수 항목(사번, 비밀번호, 연락처, 이메일, 주소)을 입력하세요.");
            }

            int empno = Integer.parseInt(empnoStr);
            EmpVO vo = new EmpVO();
            vo.setEmpno(empno);
            vo.setPassword(pass);
            vo.setDept(detailView.getJcbDept().getSelectedItem().toString());
            vo.setPosition(detailView.getJcbPosition().getSelectedItem().toString());
            vo.setTel(contact);
            vo.setEmail(email);
            vo.setAddress(address);

            File selectedFile = detailView.getSelectedFile();
            System.out.println("[EmpDetailViewEvt] 🔘 저장 버튼 클릭됨");
            System.out.println("[EmpDetailViewEvt] 선택된 이미지 파일: " + detailView.getSelectedFile());
            if (selectedFile != null && selectedFile.exists()) {
                byte[] bytes = java.nio.file.Files.readAllBytes(selectedFile.toPath());
                vo.setImgBytes(bytes);
                vo.setImgInputStream(new java.io.ByteArrayInputStream(bytes));
                vo.setImgName(selectedFile.getName());
            } else {
                // 새로 선택한 이미지가 없으면 기존 이미지 유지
                EmpVO original = new EmpService().getEmployeeByEmpno(empno);
                if (original != null && original.getImgBytes() != null) {
                    byte[] existingBytes = original.getImgBytes();
                    vo.setImgBytes(existingBytes);
                    vo.setImgInputStream(new java.io.ByteArrayInputStream(existingBytes));
                    vo.setImgName(original.getImgName());
                }
            }

            boolean success = new EmpService().updateEmpPartial(vo);
            if (!success) {
                throw new RuntimeException("사원 정보 수정 실패");
            }

            // 창 닫지 않고 기존 화면에 새 정보 반영
            EmpVO updatedVO = new EmpService().getEmployeeByEmpno(empno);
            detailView.loadEmployeeInfo(updatedVO);  // 기존 메서드에 EmpVO 전달 가능
            detailView.setSelectedFile(null);  // ★ 중요: 다시 선택하지 않는 이상 같은 파일 반복 저장 방지

            JOptionPane.showMessageDialog(detailView, "사원 정보가 성공적으로 수정되었습니다.");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(detailView, "사원 정보 수정 중 오류: " + ex.getMessage());
        }
    }





    private void disableEditing() {
        detailView.setFieldsEditable(false);
        detailView.setTabEditable(false);                  // ✅ 테이블 편집 비활성화
        detailView.setButtonsVisible(false);               // ✅ 버튼 숨김
        detailView.getJbtnEditImg().setVisible(false);

        detailView.getBtnModify().setVisible(true);
        detailView.getBtnSave().setVisible(false);
        detailView.getJbtnResetEmp().setVisible(false);
    }
    
    
    private void showPasswordDialog() {
    	Window parent = SwingUtilities.getWindowAncestor(detailView);

        ChangePassDialog dialog = new ChangePassDialog(parent, ChangePassDialog.Mode.CHANGE);
        String currentPw = new String(detailView.getJpfPass().getPassword()).trim();
        dialog.setOldPassword(currentPw);

        dialog.getBtnOk().addActionListener(e -> {
            String oldPw = dialog.getOldPassword();
            String newPw = dialog.getNewPassword();
            String confirmPw = dialog.getConfirmPassword();
         // ✅ 현재 비밀번호 세팅
            

            if (oldPw.isEmpty() || newPw.isEmpty() || confirmPw.isEmpty()) {
                JOptionPane.showMessageDialog(detailView, "모든 항목을 입력해주세요.");
                return;
            }

            if (!newPw.equals(confirmPw)) {
                JOptionPane.showMessageDialog(detailView, "새 비밀번호가 일치하지 않습니다.");
                return;
            }

            int empno = Integer.parseInt(detailView.getJtfEmpno().getText().trim());
            EmpService service = new EmpService();

            try {
                if (!service.checkPassword(empno, oldPw)) {
                    JOptionPane.showMessageDialog(detailView, "현재 비밀번호가 일치하지 않습니다.");
                    return;
                }

                boolean changed = service.changePassword(empno, newPw);
                if (changed) {
                    JOptionPane.showMessageDialog(detailView, "비밀번호가 성공적으로 변경되었습니다.");
                    detailView.getJpfPass().setText(newPw); // ✅ 화면 반영
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(detailView, "비밀번호 변경 실패: DB 오류");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "오류 발생: " + ex.getMessage());
            }
        });

        dialog.getBtnCancel().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
    

    
 


}//class