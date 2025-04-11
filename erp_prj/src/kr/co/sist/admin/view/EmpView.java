package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import kr.co.sist.admin.evt.EmpTabViewEvt;
import kr.co.sist.admin.evt.EmpViewEvt;

/**
 * 
 */
public class EmpView extends JPanel {

	private static final long serialVersionUID = 1916152364214019354L;
	private JPanel contentPane;
    private JTextField jtfEmpno, jtfPass, jtfContact, jtfEmail, jtfAddress, jtfName, jtfBirthDate, jtfHireDate;
    private JButton jbtnEditImg, jbtnAddEmp, jbtnInputPass, jbtnResetEmp;
    private JComboBox<String> jcbDept, jcbPosition;
    private JLabel jlblImg;

    // 공통 탭 패널
    private SubTabPanel eduTabPanel, careerTabPanel, certTabPanel, personnelTabPanel, trainingTabPanel;

    public JTextField getJtfEmpno() { return jtfEmpno; }
    public JTextField getJtfPass() { return jtfPass; }
    public JTextField getJtfContact() { return jtfContact; }
    public JTextField getJtfEmail() { return jtfEmail; }
    public JTextField getJtfAddress() { return jtfAddress; }
    public JTextField getJtfName() { return jtfName; }
    public JTextField getJtfBirthDate() { return jtfBirthDate; }
    public JTextField getJtfHireDate() { return jtfHireDate; }
    public JButton getJbtnEditImg() { return jbtnEditImg; }
    public JButton getJbtnAddEmp() { return jbtnAddEmp; }
    public JButton getJbtnInputPass() { return jbtnInputPass; }
    public JButton getJbtnResetEmp() { return jbtnResetEmp; }
    public JComboBox<String> getJcbDept() { return jcbDept; }
    public JComboBox<String> getJcbPosition() { return jcbPosition; }
    public JLabel getJlblImg() { return jlblImg; }
    
    // 공통 탭 접근자
    public SubTabPanel getEduTabPanel() { return eduTabPanel; }
    public SubTabPanel getCareerTabPanel() { return careerTabPanel; }
    public SubTabPanel getCertTabPanel() { return certTabPanel; }
    public SubTabPanel getPersonnelTabPanel() { return personnelTabPanel; }
    public SubTabPanel getTrainingTabPanel() { return trainingTabPanel; }

    public JPanel getMainPanel() {
        return contentPane.getComponentCount() > 0 ? (JPanel) contentPane.getComponent(0) : null;
    }

    public EmpView() {
    	
    	setPreferredSize(new Dimension(1200, 900));
    	
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(904,599);
//        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setPreferredSize(new Dimension(1000, 900));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(136, 33, 740, 505);
        panel.setBounds(70, 33, 740, 505);
        panel.setBorder(new TitledBorder("사원등록"));
//        contentPane.add(panel);
        panel.setLayout(null);

        jcbPosition = new JComboBox<>();
        jcbPosition.setBounds(492, 123, 100, 24);
        panel.add(jcbPosition);

        jcbDept = new JComboBox<>();
        jcbDept.setBounds(237, 123, 100, 24);
        panel.add(jcbDept);

        EmpViewEvt eve = new EmpViewEvt(this);
        EmpTabViewEvt etve = new EmpTabViewEvt(this);

        jlblImg = new JLabel("증명사진");
        jlblImg.setBounds(31, 58, 105, 125);
     // 👉 기본 이미지 세팅
        ImageIcon defaultIcon = new ImageIcon("src/default.png"); 
        Image scaled = defaultIcon.getImage().getScaledInstance(105, 125, Image.SCALE_SMOOTH);
        jlblImg.setIcon(new ImageIcon(scaled));
        panel.add(jlblImg);

        jtfEmpno = new JTextField("자동생성");
        jtfEmpno.setBounds(237, 89, 100, 24);
        jtfEmpno.setEditable(false);
        panel.add(jtfEmpno);

        jbtnEditImg = new JButton("수정하기");
        jbtnEditImg.setBounds(42, 193, 97, 23);
        jbtnEditImg.addActionListener(eve);
        panel.add(jbtnEditImg);

        jtfPass = new JTextField();
        jtfPass.setBounds(492, 89, 100, 24);
        jtfPass.setEditable(false);
        panel.add(jtfPass);

        jtfName = new JTextField();
        jtfName.setBounds(237, 58, 100, 24);
        panel.add(jtfName);

        jtfBirthDate = new JTextField();
        jtfBirthDate.setBounds(492, 58, 100, 24);
        panel.add(jtfBirthDate);

        jtfHireDate = new JTextField();
        jtfHireDate.setBounds(237, 152, 100, 24);
        panel.add(jtfHireDate);

        jtfContact = new JTextField();
        jtfContact.setBounds(492, 152, 100, 24);
        panel.add(jtfContact);

        jtfEmail = new JTextField();
        jtfEmail.setBounds(237, 193, 355, 24);
        panel.add(jtfEmail);

        jtfAddress = new JTextField();
        jtfAddress.setBounds(237, 220, 355, 24);
        panel.add(jtfAddress);

        jbtnAddEmp = new JButton("사원 등록");
        jbtnAddEmp.setBounds(500, 256, 87, 23);
        jbtnAddEmp.addActionListener(eve);
        panel.add(jbtnAddEmp);

        jbtnInputPass = new JButton("비밀번호 입력");
        jbtnInputPass.setBounds(364, 256, 123, 23);
        jbtnInputPass.addActionListener(eve);
        panel.add(jbtnInputPass);
        
        jbtnResetEmp = new JButton("초기화"); 
        jbtnResetEmp.setBounds(640,460, 87,23);
        jbtnResetEmp.addActionListener(eve);
        panel.add(jbtnResetEmp);

        // 라벨들
        panel.add(makeLabel("이름", 168, 58));
        panel.add(makeLabel("사원번호", 168, 88));
        panel.add(makeLabel("비밀번호", 423, 88));
        panel.add(makeLabel("생년월일", 423, 58));
        panel.add(makeLabel("부서", 168, 122));
        panel.add(makeLabel("직급", 423, 122));
        panel.add(makeLabel("입사일", 168, 151));
        panel.add(makeLabel("연락처", 423, 151));
        panel.add(makeLabel("이메일", 168, 192));
        panel.add(makeLabel("주소", 168, 219));

        // 탭 구성
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(46, 277, 585, 179);
        panel.add(tabbedPane);

        eduTabPanel = new SubTabPanel(new String[]{"edu_id", "입학년월", "졸업년월", "학교명", "전공", "학위", "졸업구분"}, etve);
        careerTabPanel = new SubTabPanel(new String[]{"career_id", "회사명", "입사일자", "퇴사일자", "직급", "근무부서", "사유"}, etve);
        certTabPanel = new SubTabPanel(new String[]{"cert_id", "자격증명", "발급기관", "취득일자", "유효일자"}, etve);
        personnelTabPanel = new SubTabPanel(new String[]{"personnel_id", "발령구분", "발령일자", "발령부서", "발령직급"}, etve);
        trainingTabPanel = new SubTabPanel(new String[]{"training_id", "교육기관", "교육명", "시작일자", "종료일자", "수료여부"}, etve);

        hideIdColumn(eduTabPanel);
        hideIdColumn(careerTabPanel);
        hideIdColumn(certTabPanel);
        hideIdColumn(personnelTabPanel);
        hideIdColumn(trainingTabPanel);

        tabbedPane.addTab("학력", eduTabPanel);
        tabbedPane.addTab("경력", careerTabPanel);
        tabbedPane.addTab("자격증", certTabPanel);
        tabbedPane.addTab("인사", personnelTabPanel);
        tabbedPane.addTab("교육", trainingTabPanel);
        
        contentPane.add(panel);
        add(contentPane, BorderLayout.CENTER);
        
        contentPane.setOpaque(false);
        panel.setOpaque(false);
        setVisible(true);
//        contentPane.setBackground(Color.red);
//        contentPane.add(panel);
//        add(contentPane);
    }

    private JLabel makeLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 57, 24);
        return label;
    }

    private void hideIdColumn(SubTabPanel panel) {
        panel.getTable().getColumnModel().getColumn(0).setMinWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setMaxWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void setFieldsEditable(boolean editable) {
        jtfEmpno.setEditable(editable);
        jtfPass.setEditable(editable);
        jtfName.setEditable(editable);
        jtfBirthDate.setEditable(editable);
        jtfHireDate.setEditable(editable);
        jtfContact.setEditable(editable);
        jtfEmail.setEditable(editable);
        jtfAddress.setEditable(editable);
        jcbDept.setEnabled(editable);
        jcbPosition.setEnabled(editable);
    }

    public void setButtonsVisible(boolean visible) {
        jbtnAddEmp.setVisible(visible);
        jbtnEditImg.setVisible(visible);
        jbtnInputPass.setVisible(visible);

        eduTabPanel.setButtonsVisible(visible);
        careerTabPanel.setButtonsVisible(visible);
        certTabPanel.setButtonsVisible(visible);
        personnelTabPanel.setButtonsVisible(visible);
        trainingTabPanel.setButtonsVisible(visible);
    }

    public void setTabEditable(boolean editable) {
        eduTabPanel.setTableEditable(editable);
        careerTabPanel.setTableEditable(editable);
        certTabPanel.setTableEditable(editable);
        personnelTabPanel.setTableEditable(editable);
        trainingTabPanel.setTableEditable(editable);
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                EmpView frame = new EmpView();
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

}//class