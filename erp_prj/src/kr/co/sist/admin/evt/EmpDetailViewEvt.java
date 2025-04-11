package kr.co.sist.admin.evt;

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

import kr.co.sist.admin.evt.EmpListViewEvt;
import kr.co.sist.admin.service.AppointmentService;
import kr.co.sist.admin.service.CareerService;
import kr.co.sist.admin.service.CertService;
import kr.co.sist.admin.service.EduService;
import kr.co.sist.admin.service.EmpService;
import kr.co.sist.admin.service.TrainingService;
import kr.co.sist.admin.view.ChangePassDialog;
import kr.co.sist.admin.view.EmpDetailView;
import kr.co.sist.admin.vo.AppointmentVO;
import kr.co.sist.admin.vo.CareerVO;
import kr.co.sist.admin.vo.CertVO;
import kr.co.sist.admin.vo.EduVO;
import kr.co.sist.admin.vo.EmpVO;
import kr.co.sist.admin.vo.TrainingVO;

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
             // 🔥 사원 목록 새로고침
                if (detailView.getListView() != null) {
                    new EmpListViewEvt(detailView.getListView()).showAllEmpList(false);
                }
                
                disableEditing();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 저장 중 오류 발생: " + ex.getMessage());
            }
        }else if (src == detailView.getEduTabPanel().getBtnSave()) {
            try {
                saveEduChanges();
                JOptionPane.showMessageDialog(detailView, "✅ 학력 정보가 저장되었습니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 학력 정보 저장 중 오류 발생: " + ex.getMessage());
            }
        } else if (src == detailView.getCareerTabPanel().getBtnSave()) {
            try {
                saveCareerChanges();
                JOptionPane.showMessageDialog(detailView, "✅ 경력 정보가 저장되었습니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 경력 정보 저장 중 오류 발생: " + ex.getMessage());
            }
        } else if (src == detailView.getCertTabPanel().getBtnSave()) {
            try {
                saveCertChanges();
                JOptionPane.showMessageDialog(detailView, "✅ 자격증 정보가 저장되었습니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 자격증 정보 저장 중 오류 발생: " + ex.getMessage());
            }
        } else if (src == detailView.getPersonnelTabPanel().getBtnSave()) {
            try {
                savePersonnelChanges();
                JOptionPane.showMessageDialog(detailView, "✅ 인사 정보가 저장되었습니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 인사 정보 저장 중 오류 발생: " + ex.getMessage());
            }
        } else if (src == detailView.getTrainingTabPanel().getBtnSave()) {
            try {
                saveTrainingChanges();
                JOptionPane.showMessageDialog(detailView, "✅ 교육 정보가 저장되었습니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(detailView, "❌ 교육 정보 저장 중 오류 발생: " + ex.getMessage());
            }
        }else if (src == detailView.getJbtnEditImg()) {
	                detailView.chooseImage(); // 선택된 파일이 없을 때만 실행
	                // 👇 핵심 추가
	                File chosen = detailView.getSelectedFile(); // 이미 chooseImage 내부에서 설정됨
	                detailView.setSelectedFile(chosen); // 명시적 재지정 (안 하면 null로 인식됨)
        }else if (src == detailView.getEduTabPanel().getBtnDelete()) {
            deleteEduRow();  // ✅ 아래에서 새로 정의할 메서드
        }else if (src == detailView.getCareerTabPanel().getBtnDelete()) {
            deleteCareerRow();  // ✅ 아래에서 새로 정의할 메서드
        }else if (src == detailView.getCertTabPanel().getBtnDelete()) {
            deleteCertRow();
        }else if (src == detailView.getPersonnelTabPanel().getBtnDelete()) {
            deletePersonnelRow();
        }else if (src == detailView.getTrainingTabPanel().getBtnDelete()) {
            deleteTrainingRow();
        }else if (src == detailView.getJbtnEditPass()) {
            showPasswordDialog(); // 비밀번호 수정 다이얼로그 호출
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
        
        detailView.getJtfPass().setEditable(false);
        detailView.getJcbDept().setEnabled(false);
        detailView.getJcbPosition().setEnabled(false);

        // ✅ 하단 버튼
        detailView.getBtnModify().setVisible(false);
        detailView.getBtnSave().setVisible(true);
        detailView.getJbtnResetEmp().setVisible(false);

        // ✅ 탭별 패널: 버튼 표시, 테이블 수정 가능
        detailView.getEduTabPanel().setButtonsVisible(true);
        detailView.getEduTabPanel().setTableEditable(true);

        detailView.getCareerTabPanel().setButtonsVisible(true);
        detailView.getCareerTabPanel().setTableEditable(true);

        detailView.getCertTabPanel().setButtonsVisible(true);
        detailView.getCertTabPanel().setTableEditable(true);

        detailView.getPersonnelTabPanel().setButtonsVisible(true);
        detailView.getPersonnelTabPanel().setTableEditable(true);

        detailView.getTrainingTabPanel().setButtonsVisible(true);
        detailView.getTrainingTabPanel().setTableEditable(true);

    }

    private void saveChanges() {
        try {
            String empnoStr = detailView.getJtfEmpno().getText().trim();
            String pass = detailView.getJtfPass().getText().trim();
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
        String currentPw = detailView.getJtfPass().getText().trim();
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
                    detailView.getJtfPass().setText(newPw); // ✅ 화면 반영
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
    
    

    
 // EmpDetailViewEvt.java 에 추가할 메서드
    private void saveEduChanges() {
    	if (detailView.getEduTabPanel().getTable().isEditing()) {
    	    detailView.getEduTabPanel().getTable().getCellEditor().stopCellEditing();
    	}
        DefaultTableModel model = detailView.getEduTabPanel().getTableModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0) return;

        EduService service = new EduService();

        for (int i = 0; i < rowCount; i++) {
            try {
                String admissionStr = model.getValueAt(i, 1).toString().trim();
                String graduationStr = model.getValueAt(i, 2).toString().trim();
                String school = model.getValueAt(i, 3).toString().trim();
                String major = model.getValueAt(i, 4).toString().trim();
                String degree = model.getValueAt(i, 5).toString().trim();
                String gradStatus = model.getValueAt(i, 6).toString().trim();

                if (admissionStr.isEmpty() || graduationStr.isEmpty() || school.isEmpty()
                        || major.isEmpty() || degree.isEmpty() || gradStatus.isEmpty()) {
                    throw new RuntimeException((i + 1) + "번째 학력 정보에 누락된 항목이 있습니다.");
                }

                EduVO vo = new EduVO();
                vo.setEmpno(Integer.parseInt(detailView.getJtfEmpno().getText().trim()));
                vo.setEdu_id(Integer.parseInt(model.getValueAt(i, 0).toString()));
                vo.setAdmission(Date.valueOf(admissionStr));
                vo.setGraduation(Date.valueOf(graduationStr));
                vo.setSchoolName(school);
                vo.setMajor(major);
                vo.setDegree(degree);
                vo.setGradStatus(gradStatus);

                if (!service.updateEducation(vo)) {
                    throw new RuntimeException("학력 정보 수정 실패 (id: " + vo.getEdu_id() + ")");
                }

            } catch (Exception e) {
                throw new RuntimeException("학력 정보 수정 중 오류 발생: " + e.getMessage(), e);
            }
        }
    }

    
    private void saveCareerChanges() {
    	if (detailView.getCareerTabPanel().getTable().isEditing()) {
    	    detailView.getCareerTabPanel().getTable().getCellEditor().stopCellEditing();
    	}
        DefaultTableModel model = detailView.getCareerTabPanel().getTableModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0) return;

        CareerService service = new CareerService();

        for (int i = 0; i < rowCount; i++) {
            try {
                String company = model.getValueAt(i, 1).toString().trim();
                String hireStr = model.getValueAt(i, 2).toString().trim();
                String leaveStr = model.getValueAt(i, 3).toString().trim();
                String position = model.getValueAt(i, 4).toString().trim();
                String dept = model.getValueAt(i, 5).toString().trim();
                String reason = model.getValueAt(i, 6).toString().trim();

                if (company.isEmpty() || hireStr.isEmpty() || leaveStr.isEmpty()
                        || position.isEmpty() || dept.isEmpty() || reason.isEmpty()) {
                    throw new RuntimeException((i + 1) + "번째 경력 정보에 누락된 항목이 있습니다.");
                }

                CareerVO vo = new CareerVO();
                vo.setCareer_id(Integer.parseInt(model.getValueAt(i, 0).toString()));
                vo.setCompany(company);
                vo.setHireDate(Date.valueOf(hireStr));
                vo.setLeaveDate(Date.valueOf(leaveStr));
                vo.setExPosition(position);
                vo.setExDept(dept);
                vo.setReason(reason);

                if (service.modifyCareer(vo) == 0) {
                    throw new RuntimeException("경력 정보 수정 실패 (id: " + vo.getCareer_id() + ")");
                }

            } catch (Exception e) {
                throw new RuntimeException("경력 정보 수정 중 오류 발생: " + e.getMessage(), e);
            }
        }
    }

    
    private void saveCertChanges() {
    	if (detailView.getCertTabPanel().getTable().isEditing()) {
    	    detailView.getCertTabPanel().getTable().getCellEditor().stopCellEditing();
    	}
        DefaultTableModel model = detailView.getCertTabPanel().getTableModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0) return;

        CertService service = new CertService();

        for (int i = 0; i < rowCount; i++) {
            try {
                // 필수 항목 검사
                String name = model.getValueAt(i, 1).toString().trim();
                String issuer = model.getValueAt(i, 2).toString().trim();
                String acqStr = model.getValueAt(i, 3).toString().trim();
                Object expObj = model.getValueAt(i, 4);
                String expStr = (expObj == null) ? "" : expObj.toString().trim();


                if (name.isEmpty() || issuer.isEmpty() || acqStr.isEmpty()) {
                    throw new RuntimeException((i + 1) + "번째 자격증 정보에 누락된 항목이 있습니다.");
                }

                // CertVO 구성
                CertVO vo = new CertVO();

                // 🔹 cert_id 안전하게 파싱
                Object idObj = model.getValueAt(i, 0);
                if (idObj == null || idObj.toString().isBlank()) {
                    throw new RuntimeException((i + 1) + "번째 자격증 ID가 존재하지 않습니다.");
                }
                try {
                    vo.setCert_id(Integer.parseInt(idObj.toString()));
                } catch (NumberFormatException nfe) {
                    throw new RuntimeException((i + 1) + "번째 자격증 ID가 올바르지 않습니다.");
                }

                vo.setCertName(name);
                vo.setIssuer(issuer);

                // 날짜 파싱
                try {
                    vo.setAcqDate(Date.valueOf(acqStr));
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException((i + 1) + "번째 자격증의 취득일 형식이 잘못되었습니다. yyyy-mm-dd 형식이어야 합니다.");
                }

                Date expDate = null;
                if (!expStr.isEmpty()) {
                    try {
                        expDate = Date.valueOf(expStr);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException((i + 1) + "번째 자격증의 유효일 형식이 잘못되었습니다. yyyy-mm-dd 형식이어야 합니다.");
                    }
                }
                vo.setExpDate(expDate);

                // 수정 수행
                service.modifyCertificate(vo);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(detailView,
                    "자격증 정보 수정 중 오류 발생:\n" + e.getMessage(),
                    "오류", JOptionPane.ERROR_MESSAGE);
                return; // 중단
            }
        }

    }



    private void savePersonnelChanges() {
        if (detailView.getPersonnelTabPanel().getTable().isEditing()) {
            detailView.getPersonnelTabPanel().getTable().getCellEditor().stopCellEditing();
        }
        DefaultTableModel model = detailView.getPersonnelTabPanel().getTableModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0) return;

        EmpService service = new EmpService(); // 매번 생성하지 않도록 바깥으로 뺌

        for (int i = 0; i < rowCount; i++) {
            try {
                String appointment = getSafeString(model.getValueAt(i, 1));
                String dateStr = getSafeString(model.getValueAt(i, 2));
                String deptName = getSafeString(model.getValueAt(i, 3));
                String positionName = getSafeString(model.getValueAt(i, 4));

                if (appointment.isEmpty() || dateStr.isEmpty()) {
                    throw new RuntimeException((i + 1) + "번째 인사발령 정보에 발령구분과 발령일자는 필수입니다.");
                }

                Integer deptno = null;
                Integer positionId = null;

                if (!"퇴사".equals(appointment)) {
                    if (deptName.isEmpty() || positionName.isEmpty()) {
                        throw new RuntimeException((i + 1) + "번째 인사발령 정보에 부서 또는 직급이 누락되었습니다.");
                    }
                    deptno = service.getDeptnoByName(deptName);
                    positionId = service.getPositionIdByName(positionName);
                }

                AppointmentVO vo = new AppointmentVO();
                vo.setAppoint_id(Integer.parseInt(model.getValueAt(i, 0).toString()));
                vo.setEmpno(Integer.parseInt(detailView.getJtfEmpno().getText().trim()));
                vo.setAppointment(appointment);
                vo.setAppointmentDate(Date.valueOf(dateStr));
                vo.setDeptno(deptno);               // 🔥 퇴사인 경우 null
                vo.setPositionId(positionId);       // 🔥 퇴사인 경우 null

                new AppointmentService().modifyAppointment(vo);

            } catch (Exception e) {
                throw new RuntimeException("인사발령 정보 수정 중 오류 발생: " + e.getMessage(), e);
            }
        }
    }


    private String getSafeString(Object value) {
        return value == null ? "" : value.toString().trim();
    }
    
    private void saveTrainingChanges() {
    	if (detailView.getTrainingTabPanel().getTable().isEditing()) {
    	    detailView.getTrainingTabPanel().getTable().getCellEditor().stopCellEditing();
    	}
        DefaultTableModel model = detailView.getTrainingTabPanel().getTableModel();
        int rowCount = model.getRowCount();
        if (rowCount == 0) return;

        for (int i = 0; i < rowCount; i++) {
            try {
                String institution = model.getValueAt(i, 1).toString().trim();
                String title = model.getValueAt(i, 2).toString().trim();
                String startStr = model.getValueAt(i, 3).toString().trim();
                String endStr = model.getValueAt(i, 4).toString().trim();
                String completion = model.getValueAt(i, 5).toString().trim();

                if (institution.isEmpty() || title.isEmpty() || startStr.isEmpty()
                        || endStr.isEmpty() || completion.isEmpty()) {
                    throw new RuntimeException((i + 1) + "번째 교육 정보에 누락된 항목이 있습니다.");
                }

                TrainingVO vo = new TrainingVO();
                vo.setTraining_id(Integer.parseInt(model.getValueAt(i, 0).toString()));
                vo.setInstitution(institution);
                vo.setTrainingName(title);
                vo.setStartDate(Date.valueOf(startStr));
                vo.setEndDate(Date.valueOf(endStr));
                vo.setComplete(completion);

                new TrainingService().modifyTraining(vo);

            } catch (Exception e) {
                throw new RuntimeException("교육 정보 수정 중 오류 발생: " + e.getMessage(), e);
            }
        }
    }

    
    private void deleteEduRow() {
        int row = detailView.getEduTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(detailView, "삭제할 학력 정보를 선택하세요.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(detailView, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int eduId = Integer.parseInt(detailView.getEduTabPanel().getTableModel().getValueAt(row, 0).toString()); // ✅ 첫 컬럼: edu_id

            boolean success = new EduService().deleteEducation(eduId); // ✅ empno 없이 삭제

            if (success) {
                detailView.getEduTabPanel().getTableModel().removeRow(row);
                JOptionPane.showMessageDialog(detailView, "학력 정보 삭제 성공");
            } else {
                JOptionPane.showMessageDialog(detailView, "DB 삭제 실패");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(detailView, "삭제 중 오류 발생: " + ex.getMessage());
        }
    }

    private void deleteCareerRow() {
        int row = detailView.getCareerTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(detailView, "삭제할 경력 정보를 선택하세요.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(detailView, "선택한 경력 정보를 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Object idObj = detailView.getCareerTabPanel().getTableModel().getValueAt(row, 0);
            if (idObj == null || idObj.toString().isBlank()) {
                JOptionPane.showMessageDialog(detailView, "🟡 임시로 추가된 경력 정보입니다. 저장 후 다시 삭제하세요.");
                detailView.getCareerTabPanel().getTableModel().removeRow(row);
                return;
            }

            int careerId = Integer.parseInt(idObj.toString());
            boolean deleted = new CareerService().deleteCareerById(careerId);

            if (deleted) {
                detailView.getCareerTabPanel().getTableModel().removeRow(row);
                JOptionPane.showMessageDialog(detailView, "✅ 경력 정보가 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(detailView, "❌ 삭제 실패: DB 오류");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailView, "삭제 중 오류 발생: " + e.getMessage());
        }
    }

    
    private void deleteCertRow() {
        int row = detailView.getCertTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(detailView, "삭제할 자격증 정보를 선택하세요.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(detailView, "선택한 자격증 정보를 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Object idObj = detailView.getCertTabPanel().getTableModel().getValueAt(row, 0);
            if (idObj == null || idObj.toString().isBlank()) {
                JOptionPane.showMessageDialog(detailView, "🟡 임시로 추가된 자격증 정보입니다. 저장 후 다시 삭제하세요.");
                detailView.getCertTabPanel().getTableModel().removeRow(row);
                return;
            }

            int certId = Integer.parseInt(idObj.toString());
            boolean deleted = new CertService().deleteCertificateById(certId);

            if (deleted) {
                detailView.getCertTabPanel().getTableModel().removeRow(row);
                JOptionPane.showMessageDialog(detailView, "✅ 자격증 정보가 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(detailView, "❌ 삭제 실패: DB 오류");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailView, "삭제 중 오류 발생: " + e.getMessage());
        }
    }

    
    private void deletePersonnelRow() {
        int row = detailView.getPersonnelTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(detailView, "삭제할 인사발령 정보를 선택하세요.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(detailView, "선택한 인사 정보를 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Object idObj = detailView.getPersonnelTabPanel().getTableModel().getValueAt(row, 0);
            if (idObj == null || idObj.toString().isBlank()) {
                JOptionPane.showMessageDialog(detailView, "🟡 임시로 추가된 인사 정보입니다. 저장 후 다시 삭제하세요.");
                detailView.getPersonnelTabPanel().getTableModel().removeRow(row);
                return;
            }

            int appointId = Integer.parseInt(idObj.toString());

            boolean deleted = new AppointmentService().deleteAppointment(appointId);

            if (deleted) {
                detailView.getPersonnelTabPanel().getTableModel().removeRow(row);
                JOptionPane.showMessageDialog(detailView, "✅ 인사발령 정보가 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(detailView, "❌ 삭제 실패: DB 오류");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailView, "삭제 중 오류 발생: " + e.getMessage());
        }
    }


    private void deleteTrainingRow() {
        int row = detailView.getTrainingTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(detailView, "삭제할 교육 정보를 선택하세요.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(detailView, "선택한 교육 정보를 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Object idObj = detailView.getTrainingTabPanel().getTableModel().getValueAt(row, 0);
            if (idObj == null || idObj.toString().isBlank()) {
                JOptionPane.showMessageDialog(detailView, "🟡 임시로 추가된 교육 정보입니다. 저장 후 다시 삭제하세요.");
                detailView.getTrainingTabPanel().getTableModel().removeRow(row);
                return;
            }

            int trainingId = Integer.parseInt(idObj.toString());

            boolean deleted = new TrainingService().deleteTrainingById(trainingId);

            if (deleted) {
                detailView.getTrainingTabPanel().getTableModel().removeRow(row);
                JOptionPane.showMessageDialog(detailView, "✅ 교육 정보가 삭제되었습니다.");
            } else {
                JOptionPane.showMessageDialog(detailView, "❌ 삭제 실패: DB 오류");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailView, "삭제 중 오류 발생: " + e.getMessage());
        }
    }


}//class