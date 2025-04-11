package kr.co.sist.user.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import kr.co.sist.user.evt.EmpDetailViewEvt;
import kr.co.sist.user.service.AppointmentService;
import kr.co.sist.user.service.CareerService;
import kr.co.sist.user.service.CertService;
import kr.co.sist.user.service.EduService;
import kr.co.sist.user.service.EmpService;
import kr.co.sist.user.service.TrainingService;
import kr.co.sist.user.vo.AppointmentVO;
import kr.co.sist.user.vo.CareerVO;
import kr.co.sist.user.vo.CertVO;
import kr.co.sist.user.vo.EduVO;
import kr.co.sist.user.vo.EmpVO;
import kr.co.sist.user.vo.TrainingVO;
import kr.co.sist.user.vo.UserAccountVO;


/**
 * 
 */
public class EmpDetailView extends EmpView {
	
	private static final long serialVersionUID = -2889911583056677807L;
	private JButton btnModify;
    private JButton btnSave;
    private File selectedFile;
    private int empno;
    private JButton jbtnEditPass;


//    // 📌 VO 기반 생성자
//    public EmpDetailView(EmpVO vo) {
//        this(vo.getEmpno());
//        System.out.println(vo.toString());
//    }
    	
    // 📌 VO 기반 생성자
    public EmpDetailView(UserAccountVO uaVO) {
    	this(Integer.parseInt(uaVO.getUserId()));
    	System.out.println(Integer.parseInt(uaVO.getUserId()));
    }

    // 📌 empno 기반 생성자
    public EmpDetailView(int empno) {
        super();
        this.empno = empno;
//        setTitle("사원 상세조회 - 사원번호: " + empno);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        getContentPane().setLayout(null);
        setPreferredSize(new Dimension(1100, 570));
        
        initDetailView();
        loadEmployeeInfo();
        loadEduInfo();
        loadCareerInfo();
        loadCertInfo();
        loadAppointmentInfo();
        loadTrainingInfo();
    }

    // 📌 버튼 및 이벤트 설정
    private void initDetailView() {
        loadDeptAndPosition(); // ✅ 반드시 먼저 호출

        setFieldsEditable(false);
        setButtonsVisible(false);
        getJbtnResetEmp().setVisible(false);




        btnModify = new JButton("수정하기");
        btnModify.setBounds(630, 420, 100, 30);
        btnModify.setVisible(true);
        getMainPanel().add(btnModify);

        btnSave = new JButton("저장");
        btnSave.setBounds(630, 480, 100, 30);
        btnSave.setVisible(false);
        getMainPanel().add(btnSave);
        
        jbtnEditPass = new JButton("비밀번호 수정");
        jbtnEditPass.setBounds(469, 256, 123, 23);
        jbtnEditPass.setVisible(false);              // 기본 노출
        getMainPanel().add(jbtnEditPass);

        ActionListener handler = new EmpDetailViewEvt(this);
        btnModify.addActionListener(handler);
        btnSave.addActionListener(handler);
        jbtnEditPass.addActionListener(handler);
        for (ActionListener al : getJbtnEditImg().getActionListeners()) {
            getJbtnEditImg().removeActionListener(al);
        }
        getJbtnEditImg().addActionListener(handler); // 📌 chooseImage() 호출

    }
    
    private void loadDeptAndPosition() {
        try {
            if (getJcbDept().getItemCount() == 0) {
                List<String> deptList = new EmpService().getAllDeptNames();
                for (String dept : deptList) getJcbDept().addItem(dept);
            }

            if (getJcbPosition().getItemCount() == 0) {
                List<String> posList = new EmpService().getAllPositionNames();
                for (String pos : posList) getJcbPosition().addItem(pos);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "부서/직급 정보를 불러오는 데 실패했습니다.");
        }
    }

    public void loadEmployeeInfo() {
        try {
            EmpVO vo = new EmpService().getEmployeeByEmpno(empno);
            loadEmployeeInfo(vo);  // ✅ 새로 만든 메서드 재사용
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "사원 정보를 불러오는 중 오류 발생: " + e.getMessage());
        }
    }



    // 📌 사원 기본 정보 로딩
    public void loadEmployeeInfo(EmpVO vo) {
        if (vo == null) {
            JOptionPane.showMessageDialog(this, "해당 사원 정보를 찾을 수 없습니다.");
            return;
        }

        getJtfEmpno().setText(String.valueOf(vo.getEmpno()));
        getJtfPass().setText(vo.getPassword());
        getJtfPass().setEditable(false);
        getJtfName().setText(vo.getEname());
        getJtfBirthDate().setText(vo.getBirthDate().toString());
        getJtfHireDate().setText(vo.getHireDate().toString());
        getJtfContact().setText(vo.getTel());
        getJtfEmail().setText(vo.getEmail());
        getJtfAddress().setText(vo.getAddress());

        if (vo.getDept() != null) {
            getJcbDept().setSelectedItem(vo.getDept());
        }
        if (vo.getPosition() != null) {
            getJcbPosition().setSelectedItem(vo.getPosition());
        }

        ImageIcon icon = null;
        byte[] imgBytes = vo.getImgBytes();

        if (imgBytes != null && imgBytes.length > 0) {
            icon = new ImageIcon(imgBytes);
        }

        if (icon == null) {
            icon = new ImageIcon(getClass().getClassLoader().getResource("images/default.png"));
        }

        Image scaled = icon.getImage().getScaledInstance(
            getJlblImg().getWidth(),
            getJlblImg().getHeight(),
            Image.SCALE_SMOOTH
        );
        getJlblImg().setIcon(new ImageIcon(scaled));

        System.out.println("🔄 reload ▶ 이미지 byte 크기: " + (imgBytes != null ? imgBytes.length : "null"));
    }




    // 📌 학력 정보
    private void loadEduInfo() {
        try {
            List<EduVO> list = new EduService().getEduListByEmpno(empno);
            for (EduVO vo : list) {
                getEduTabPanel().getTableModel().addRow(new Object[]{
                    vo.getEdu_id(), vo.getAdmission(), vo.getGraduation(),
                    vo.getSchoolName(), vo.getMajor(), vo.getDegree(), vo.getGradStatus()
                });
            }
            hideIdColumn(getEduTabPanel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 경력 정보
    private void loadCareerInfo() {
        try {
            List<CareerVO> list = new CareerService().getCareerListByEmpno(empno);
            for (CareerVO vo : list) {
                getCareerTabPanel().getTableModel().addRow(new Object[]{
                    vo.getCareer_id(), vo.getCompany(), vo.getHireDate(),
                    vo.getLeaveDate(), vo.getExPosition(), vo.getExDept(), vo.getReason()
                });
            }
            hideIdColumn(getCareerTabPanel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 자격증 정보
    private void loadCertInfo() {
        try {
            List<CertVO> list = new CertService().getCertListByEmpno(empno);
            for (CertVO vo : list) {
                getCertTabPanel().getTableModel().addRow(new Object[]{
                    vo.getCert_id(), vo.getCertName(), vo.getIssuer(),
                    vo.getAcqDate(), vo.getExpDate()
                });
            }
            hideIdColumn(getCertTabPanel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 인사발령 정보
    private void loadAppointmentInfo() {
        try {
            List<AppointmentVO> list = new AppointmentService().getAppointmentListByEmpno(empno);
            for (AppointmentVO vo : list) {
                getPersonnelTabPanel().getTableModel().addRow(new Object[]{
                    vo.getAppoint_id(), vo.getAppointment(), vo.getAppointmentDate(),
                    vo.getDeptName(), vo.getPositionName()
                });
            }
            hideIdColumn(getPersonnelTabPanel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 교육 정보
    private void loadTrainingInfo() {
        try {
            List<TrainingVO> list = new TrainingService().getTrainingListByEmpno(empno);
            for (TrainingVO vo : list) {
                getTrainingTabPanel().getTableModel().addRow(new Object[]{
                    vo.getTraining_id(), vo.getInstitution(), vo.getTrainingName(),
                    vo.getStartDate(), vo.getEndDate(), vo.getComplete()
                });
            }
            hideIdColumn(getTrainingTabPanel());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 📌 ID 컬럼 숨김 처리 (재사용)
    private void hideIdColumn(SubTabPanel panel) {
        panel.getTable().getColumnModel().getColumn(0).setMinWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setMaxWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    // 📌 Getter
    public JButton getBtnModify() { return btnModify; }
    public JButton getBtnSave() { return btnSave; }
    public File getSelectedFile() {  
    	System.out.println("[EmpDetailView] 📂 getSelectedFile 호출됨: " + selectedFile);
    	return selectedFile; }
    public JButton getJbtnEditPass() {return jbtnEditPass;}


    public void setSelectedFile(File file) {
        System.out.println("[EmpDetailView] 📌 setSelectedFile: " + file);
        this.selectedFile = file;
    }

    
    // 📌 이미지 선택 다이얼로그
    public void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            System.out.println(selected+"chooseImage에서 고른 사진");
            if (selected != null && selected.exists()) {
                setSelectedFile(selected);
                try {
                    ImageIcon icon = new ImageIcon(selected.getAbsolutePath());
                    Image scaled = icon.getImage().getScaledInstance(
                        getJlblImg().getWidth(), getJlblImg().getHeight(), Image.SCALE_SMOOTH);
                    getJlblImg().setIcon(new ImageIcon(scaled));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "이미지 로딩 중 오류 발생: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "선택한 파일이 유효하지 않습니다.");
            }
        }
        
    }
    
}//class
