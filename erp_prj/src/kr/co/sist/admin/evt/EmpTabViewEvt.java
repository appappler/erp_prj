package kr.co.sist.admin.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.Date;

import javax.swing.JOptionPane;

import kr.co.sist.admin.service.AppointmentService;
import kr.co.sist.admin.service.CareerService;
import kr.co.sist.admin.service.CertService;
import kr.co.sist.admin.service.EduService;
import kr.co.sist.admin.service.EmpService;
import kr.co.sist.admin.service.TrainingService;
import kr.co.sist.admin.view.AddCareerDialog;
import kr.co.sist.admin.view.AddCertDialog;
import kr.co.sist.admin.view.AddEduDialog;
import kr.co.sist.admin.view.AddPersonnelDialog;
import kr.co.sist.admin.view.AddTrainingDialog;
import kr.co.sist.admin.view.EmpView;
import kr.co.sist.admin.vo.AppointmentVO;
import kr.co.sist.admin.vo.CareerVO;
import kr.co.sist.admin.vo.CertVO;
import kr.co.sist.admin.vo.EduVO;
import kr.co.sist.admin.vo.EmpVO;
import kr.co.sist.admin.vo.TrainingVO;

/**
 * 
 */
public class EmpTabViewEvt extends MouseAdapter implements ActionListener {
    private EmpView ev;
    private AddEduDialog eduDialog;
    private AddCareerDialog careerDialog;
    private AddCertDialog certDialog;
    private AddPersonnelDialog personnelDialog;
    private AddTrainingDialog trainingDialog;

    public EmpTabViewEvt(EmpView ev) {
        this.ev = ev;
        showTabInfo();
    }

    public void setEduDialog(AddEduDialog dlg) { this.eduDialog = dlg; }
    public void setCareerDialog(AddCareerDialog dlg) { this.careerDialog = dlg; }
    public void setCertDialog(AddCertDialog dlg) { this.certDialog = dlg; }
    public void setPersonnelDialog(AddPersonnelDialog dlg) { this.personnelDialog = dlg; }
    public void setTrainingDialog(AddTrainingDialog dlg) { this.trainingDialog = dlg; }

    public void showEduDialog() {
        if (eduDialog == null) {
            eduDialog = new AddEduDialog(ev, this);
            eduDialog.getEduDialogCancel().addActionListener(e -> {
                eduDialog.dispose();
                eduDialog = null;
            });
            eduDialog.setVisible(true);
        }
    }

    public void showCareerDialog() {
        if (careerDialog == null) {
            careerDialog = new AddCareerDialog(ev, this);
            careerDialog.getBtnCancel().addActionListener(e -> {
                careerDialog.dispose();
                careerDialog = null;
            });
            careerDialog.setVisible(true);
        }
    }

    public void showCertDialog() {
        if (certDialog == null) {
            certDialog = new AddCertDialog(ev, this);
            certDialog.getBtnCancel().addActionListener(e -> {
                certDialog.dispose();
                certDialog = null;
            });
            certDialog.setVisible(true);
        }
    }

    public void showPersonnelDialog() {
        if (personnelDialog == null) {
            personnelDialog = new AddPersonnelDialog(ev, this);
            personnelDialog.getBtnCancel().addActionListener(e -> {
                personnelDialog.dispose();
                personnelDialog = null;
            });
            personnelDialog.setVisible(true);
        }
    }

    public void showTrainingDialog() {
        if (trainingDialog == null) {
            trainingDialog = new AddTrainingDialog(ev, this);
            trainingDialog.getBtnCancel().addActionListener(e -> {
                trainingDialog.dispose();
                trainingDialog = null;
            });
            trainingDialog.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ev.getEduTabPanel().getBtnAdd()) showEduDialog();
        else if (e.getSource() == ev.getEduTabPanel().getBtnDelete()) delEduRow();
        else if (eduDialog != null && e.getSource() == eduDialog.getEduDialogOK()) addEduRow();

        else if (e.getSource() == ev.getCareerTabPanel().getBtnAdd()) showCareerDialog();
        else if (e.getSource() == ev.getCareerTabPanel().getBtnDelete()) delCareerRow();
        else if (careerDialog != null && e.getSource() == careerDialog.getBtnOK()) addCareerRow();

        else if (e.getSource() == ev.getCertTabPanel().getBtnAdd()) showCertDialog();
        else if (e.getSource() == ev.getCertTabPanel().getBtnDelete()) delCertRow();
        else if (certDialog != null && e.getSource() == certDialog.getBtnOK()) addCertRow();

        else if (e.getSource() == ev.getPersonnelTabPanel().getBtnAdd()) showPersonnelDialog();
        else if (e.getSource() == ev.getPersonnelTabPanel().getBtnDelete()) delPersonnelRow();
        else if (personnelDialog != null && e.getSource() == personnelDialog.getBtnOK()) addPersonnelRow();

        else if (e.getSource() == ev.getTrainingTabPanel().getBtnAdd()) showTrainingDialog();
        else if (e.getSource() == ev.getTrainingTabPanel().getBtnDelete()) delTrainingRow();
        else if (trainingDialog != null && e.getSource() == trainingDialog.getBtnOK()) addTrainingRow();
    }

    public void showTabInfo() {
        // 초기 로딩 시 구현
    }

    public void addEduRow() {
        try {
        	String empnoText = ev.getJtfEmpno().getText().trim();
            
        	if (empnoText.equals("자동생성") || empnoText.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "사원 등록 후 입력할 수 있습니다.");
                return;
            }
            
         // 🔽 DB에 사번이 존재하는지 검증
            int empno = Integer.parseInt(empnoText);
            EmpVO empVO = new EmpService().getEmployeeByEmpno(empno);
            if (empVO == null) {
                JOptionPane.showMessageDialog(ev, "사원 정보가 DB에 아직 등록되지 않았습니다.\n사원 등록 후 다시 시도하세요.");
                return;
            }
            
            String admission = eduDialog.getJtfEduDialog1().getText().trim();
            String graduation = eduDialog.getJtfEduDialog2().getText().trim();
            String school = eduDialog.getJtfEduDialog3().getText().trim();
            String major = eduDialog.getJtfEduDialog4().getText().trim();
            String degree = eduDialog.getJtfEduDialog5().getText().trim();
            String gradStatus = eduDialog.getJtfEduDialog6().getText().trim();

            if (admission.isEmpty() || graduation.isEmpty() || school.isEmpty() ||
                major.isEmpty() || degree.isEmpty() || gradStatus.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "모든 항목을 빠짐없이 입력하세요.");
                return;
            }

            EduVO vo = new EduVO();
            vo.setEmpno(Integer.parseInt(ev.getJtfEmpno().getText().trim()));
            vo.setAdmission(Date.valueOf(admission));
            vo.setGraduation(Date.valueOf(graduation));
            vo.setSchoolName(school);
            vo.setMajor(major);
            vo.setDegree(degree);
            vo.setGradStatus(gradStatus);

            new EduService().addEducation(vo);
            ev.getEduTabPanel().getTableModel().addRow(new Object[]{
                vo.getEdu_id(), vo.getAdmission(), vo.getGraduation(), vo.getSchoolName(), vo.getMajor(), vo.getDegree(), vo.getGradStatus()
            });
            JOptionPane.showMessageDialog(ev, "학력 정보가 저장되었습니다.");
            eduDialog.dispose();
            eduDialog = null;

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ev, "날짜는 yyyy-mm-dd 형식으로 입력하세요.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ev, "DB 저장 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void delEduRow() {
    	int row = ev.getEduTabPanel().getTable().getSelectedRow();
    	if (row == -1) {
    	    JOptionPane.showMessageDialog(ev, "삭제할 학력 정보를 선택하세요.");
    	    return;
    	}

    	int confirm = JOptionPane.showConfirmDialog(ev, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
    	if (confirm != JOptionPane.YES_OPTION) return;

    	try {
    	    int eduId = Integer.parseInt(ev.getEduTabPanel().getTableModel().getValueAt(row, 0).toString());
    	    boolean success = new EduService().deleteEducation(eduId);

    	    if (success) {
    	    	ev.getEduTabPanel().getTableModel().removeRow(row);
    	        JOptionPane.showMessageDialog(ev, "학력 정보 삭제 성공");
    	    } else {
    	        JOptionPane.showMessageDialog(ev, "삭제 실패: DB 오류");
    	    }

    	} catch (Exception e) {
    	    e.printStackTrace();
    	    JOptionPane.showMessageDialog(ev, "삭제 중 오류 발생: " + e.getMessage());
    	}

    }

    public void addCareerRow() {
        try {
        	String empnoText = ev.getJtfEmpno().getText().trim();
            if (empnoText.equals("자동생성") || empnoText.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "사원 등록 후 입력할 수 있습니다.");
                return;
            }
            String company = careerDialog.getCompany().trim();
            String hire = careerDialog.getHireDate().trim();
            String leave = careerDialog.getLeaveDate().trim();
            String pos = careerDialog.getPosition().trim();
            String dept = careerDialog.getDept().trim();
            String reason = careerDialog.getReason().trim();

            if (company.isEmpty() || hire.isEmpty() || leave.isEmpty() || pos.isEmpty() || dept.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "모든 항목을 빠짐없이 입력하세요.");
                return;
            }

            CareerVO vo = new CareerVO();
            vo.setEmpno(Integer.parseInt(ev.getJtfEmpno().getText().trim()));
            vo.setCompany(company);
            vo.setHireDate(Date.valueOf(hire));
            vo.setLeaveDate(Date.valueOf(leave));
            vo.setExPosition(pos);
            vo.setExDept(dept);
            vo.setReason(reason);

            boolean success = new CareerService().addCareer(vo);
            if (success) {
                ev.getCareerTabPanel().getTableModel().addRow(new Object[]{
                    vo.getCareer_id(), vo.getCompany(), vo.getHireDate(), vo.getLeaveDate(), vo.getExPosition(), vo.getExDept(), vo.getReason()
                });
                JOptionPane.showMessageDialog(ev, "경력 정보 저장 완료");
                careerDialog.dispose();
                careerDialog = null;
            } else {
                JOptionPane.showMessageDialog(ev, "DB 저장 실패");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ev, "날짜는 yyyy-mm-dd 형식으로 입력하세요.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ev, "DB 저장 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void delCareerRow() {
        int row = ev.getCareerTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(ev, "삭제할 경력 정보를 선택하세요.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(ev, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            // ✅ 첫 번째 컬럼은 career_id로 가정 (숨겨진 컬럼이어야 함)
            int careerId = Integer.parseInt(ev.getCareerTabPanel().getTableModel().getValueAt(row, 0).toString());
            boolean success = new CareerService().deleteCareerById(careerId);

            if (success) {
                ev.getCareerTabPanel().getTableModel().removeRow(row);
                JOptionPane.showMessageDialog(ev, "경력 정보 삭제 성공");
            } else {
                JOptionPane.showMessageDialog(ev, "삭제 실패: DB 오류");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(ev, "삭제 중 오류 발생: " + e.getMessage());
        }
    }

    public void addCertRow() {
        try {
        	String empnoText = ev.getJtfEmpno().getText().trim();
            if (empnoText.equals("자동생성") || empnoText.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "사원 등록 후 입력할 수 있습니다.");
                return;
            }
            String name = certDialog.getCertName().trim();
            String issuer = certDialog.getIssuer().trim();
            String acq = certDialog.getIssueDate().trim();
            String exp = certDialog.getExpiryDate().trim();

            if (name.isEmpty() || issuer.isEmpty() || acq.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "모든 항목을 빠짐없이 입력하세요.");
                return;
            }

            CertVO vo = new CertVO();
            vo.setEmpno(Integer.parseInt(ev.getJtfEmpno().getText().trim()));
            vo.setCertName(name);
            vo.setIssuer(issuer);
            vo.setAcqDate(Date.valueOf(acq));
            if (!exp.equals("yyyy-mm-dd") && !exp.isEmpty()) {
                vo.setExpDate(Date.valueOf(exp));
            } else {
                vo.setExpDate(null);
            }

            boolean success = new CertService().addCertificate(vo);
            if (success) {
                ev.getCertTabPanel().getTableModel().addRow(new Object[]{
                   vo.getCert_id(), vo.getCertName(), vo.getIssuer(), vo.getAcqDate(), vo.getExpDate() != null ? vo.getExpDate() : ""
                });
                JOptionPane.showMessageDialog(ev, "자격증 정보 저장 완료");
                certDialog.dispose();
                certDialog = null;
            } else {
                JOptionPane.showMessageDialog(ev, "DB 저장 실패");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ev, "날짜는 yyyy-mm-dd 형식으로 입력하세요.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ev, "DB 저장 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void delCertRow() {
        int row = ev.getCertTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(ev, "삭제할 자격증 정보를 선택하세요.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(ev, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        int certId = Integer.parseInt(ev.getCertTabPanel().getTableModel().getValueAt(row, 0).toString());
        boolean success = new CertService().deleteCertificateById(certId);
        if (success) {
            ev.getCertTabPanel().getTableModel().removeRow(row);
            JOptionPane.showMessageDialog(ev, "자격증 정보 삭제 완료");
        } else {
            JOptionPane.showMessageDialog(ev, "DB 삭제 실패");
        }
    }

    public void addPersonnelRow() {
        try {
            String empnoText = ev.getJtfEmpno().getText().trim();
            if (empnoText.equals("자동생성") || empnoText.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "사원 등록 후 입력할 수 있습니다.");
                return;
            }

            String appointment = personnelDialog.getSelectedAppointment().trim();
            String dateStr = personnelDialog.getAppointmentDate().trim();
            String deptName = personnelDialog.getAppDept();
            String positionName = personnelDialog.getAppPosition();

            if (appointment.isEmpty() || dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "발령구분과 날짜는 반드시 입력해야 합니다.");
                return;
            }

            EmpService service = new EmpService();
            AppointmentVO vo = new AppointmentVO();
            vo.setEmpno(Integer.parseInt(empnoText));
            vo.setAppointment(appointment);
            vo.setAppointmentDate(Date.valueOf(dateStr));

            if ("퇴사".equals(appointment)) {
                vo.setDeptno(null);
                vo.setPositionId(null);
            } else {
                if (deptName == null || positionName == null) {
                    JOptionPane.showMessageDialog(ev, "부서와 직급을 선택해주세요.");
                    return;
                }
                vo.setDeptno(service.getDeptnoByName(deptName));
                vo.setPositionId(service.getPositionIdByName(positionName));
            }

            boolean success = new AppointmentService().addAppointment(vo);
            if (success) {
                // ✅ 1. 테이블에 바로 반영
                ev.getPersonnelTabPanel().getTableModel().addRow(new Object[]{
                    vo.getAppoint_id(), vo.getAppointment(), vo.getAppointmentDate(),
                    deptName, positionName
                });

                // ✅ 2. 사원정보 UI도 갱신 (JComboBox 선택 변경)
                if (!"퇴사".equals(appointment)) {
                    ev.getJcbDept().setSelectedItem(deptName);
                    ev.getJcbPosition().setSelectedItem(positionName);
                }
                String flag = "퇴사".equals(appointment) ? "퇴사" : "재직";

                JOptionPane.showMessageDialog(ev, "인사발령 정보 저장 완료");
                personnelDialog.dispose();
                personnelDialog = null;
            } else {
                JOptionPane.showMessageDialog(ev, "DB 저장 실패");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ev, "날짜는 yyyy-mm-dd 형식으로 입력하세요.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ev, "DB 저장 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
    }





    public void delPersonnelRow() {
        int row = ev.getPersonnelTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(ev, "삭제할 인사발령 정보를 선택하세요.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(ev, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        int appointId = Integer.parseInt(ev.getPersonnelTabPanel().getTableModel().getValueAt(row, 0).toString());
        boolean success = new AppointmentService().deleteAppointment(appointId);

        if (success) {
            ev.getPersonnelTabPanel().getTableModel().removeRow(row);
            JOptionPane.showMessageDialog(ev, "인사발령 삭제 완료");
        } else {
            JOptionPane.showMessageDialog(ev, "DB 삭제 실패");
        }
    }





    public void addTrainingRow() {
        try {
        	String empnoText = ev.getJtfEmpno().getText().trim();
            if (empnoText.equals("자동생성") || empnoText.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "사원 등록 후 입력할 수 있습니다.");
                return;
            }
            String institution = trainingDialog.getInstitute().trim();
            String title = trainingDialog.getProgram().trim();
            String start = trainingDialog.getStartDate().trim();
            String end = trainingDialog.getEndDate().trim();
            String complete = trainingDialog.getStatus().trim();

            if (institution.isEmpty() || title.isEmpty() || start.isEmpty() || end.isEmpty() || complete.isEmpty()) {
                JOptionPane.showMessageDialog(ev, "모든 항목을 빠짐없이 입력하세요.");
                return;
            }

            TrainingVO vo = new TrainingVO();
            vo.setEmpno(Integer.parseInt(ev.getJtfEmpno().getText().trim()));
            vo.setInstitution(institution);
            vo.setTrainingName(title);
            vo.setStartDate(Date.valueOf(start));
            vo.setEndDate(Date.valueOf(end));
            vo.setComplete(complete);

            boolean success = new TrainingService().addTraining(vo);
            if (success) {
                ev.getTrainingTabPanel().getTableModel().addRow(new Object[]{
                    vo.getTraining_id(), vo.getInstitution(), vo.getTrainingName(), vo.getStartDate(), vo.getEndDate(), vo.getComplete()
                });
                JOptionPane.showMessageDialog(ev, "교육 정보 저장 완료");
                trainingDialog.dispose();
                trainingDialog = null;
            } else {
                JOptionPane.showMessageDialog(ev, "DB 저장 실패");
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ev, "날짜는 yyyy-mm-dd 형식으로 입력하세요.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ev, "DB 저장 중 오류 발생: " + ex.getMessage());
            ex.printStackTrace();
        }
    }




    public void delTrainingRow() {
        int row = ev.getTrainingTabPanel().getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(ev, "삭제할 교육 정보를 선택하세요.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(ev, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int trainingId = Integer.parseInt(ev.getTrainingTabPanel().getTableModel().getValueAt(row, 0).toString());
        boolean success = new TrainingService().deleteTrainingById(trainingId);

        if (success) {
            ev.getTrainingTabPanel().getTableModel().removeRow(row);
            JOptionPane.showMessageDialog(ev, "교육 정보 삭제 완료");
        } else {
            JOptionPane.showMessageDialog(ev, "DB 삭제 실패");
        }
    }

}//class
