package kr.co.sist.user.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import kr.co.sist.user.evt.EmpViewEvt;
import sh.util.PlaceholderUtil;

public class EmpView extends JPanel {

    private static final long serialVersionUID = 1916152364214019354L;
    private JPanel contentPane;
    private JTextField jtfEmpno, jtfContact, jtfEmail, jtfAddress, jtfName, jtfBirthDate, jtfHireDate;
    private JPasswordField jpfPass;
    private JButton jbtnEditImg, jbtnAddEmp, jbtnInputPass, jbtnResetEmp;
    private JComboBox<String> jcbDept, jcbPosition;
    private JLabel jlblImg;
    private SubTabPanel eduTabPanel, careerTabPanel, certTabPanel, personnelTabPanel, trainingTabPanel;

    public EmpView() {
        setOpaque(false);
        setPreferredSize(new Dimension(1560, 1170));

        contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setPreferredSize(new Dimension(1300, 1170));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(200, 10, 960, 650); // ⬅ 위로 40px 올림
        panel.setLayout(null);

        Font font = new Font("Dialog", Font.BOLD, 16);



        jcbPosition = new JComboBox<>();
        jcbPosition.setBounds(640, 120, 130, 30); // ⬅ 기존보다 40px 위로
        jcbPosition.setFont(font);
        panel.add(jcbPosition);

        jcbDept = new JComboBox<>();
        jcbDept.setBounds(310, 120, 130, 30);
        jcbDept.setFont(font);
        panel.add(jcbDept);

        jlblImg = new JLabel("증명사진");
        jlblImg.setHorizontalAlignment(SwingConstants.CENTER);
        jlblImg.setBounds(20, 45, 165, 200);
        jlblImg.setFont(font);
        ImageIcon defaultIcon = new ImageIcon("src/default.png");
        Image scaled = defaultIcon.getImage().getScaledInstance(135, 160, Image.SCALE_SMOOTH);
        jlblImg.setIcon(new ImageIcon(scaled));
        panel.add(jlblImg);

        jtfEmpno = new JTextField("자동생성");
        jtfEmpno.setBounds(310, 75, 130, 30);
        jtfEmpno.setEditable(false);
        jtfEmpno.setBackground(Color.white);
        jtfEmpno.setFont(font);
        panel.add(jtfEmpno);

        jbtnEditImg = new JButton("수정하기");
        jbtnEditImg.setBounds(50, 255, 110, 30);
        jbtnEditImg.setFont(font);
        panel.add(jbtnEditImg);

        jpfPass = new JPasswordField();
        jpfPass.setBounds(640, 75, 130, 30);
        jpfPass.setEditable(false);
        jpfPass.setBackground(Color.white);
        jpfPass.setFont(font);
        panel.add(jpfPass);

        jtfName = new JTextField();
        jtfName.setBounds(310, 35, 130, 30);
        jtfName.setBackground(Color.white);
        jtfName.setFont(font);
        panel.add(jtfName);

        jtfBirthDate = new JTextField("yyyy-mm-dd");
        PlaceholderUtil.applyDatePlaceholder(jtfBirthDate);
        jtfBirthDate.setBounds(640, 35, 130, 30);
        jtfBirthDate.setBackground(Color.white);
        jtfBirthDate.setFont(font);
        panel.add(jtfBirthDate);

        jtfHireDate = new JTextField("yyyy-mm-dd");
        PlaceholderUtil.applyDatePlaceholder(jtfHireDate);
        jtfHireDate.setBounds(310, 160, 130, 30);
        jtfHireDate.setBackground(Color.white);
        jtfHireDate.setFont(font);
        panel.add(jtfHireDate);

        jtfContact = new JTextField();
        jtfContact.setBounds(640, 160, 130, 30);
        jtfContact.setBackground(Color.white);
        jtfContact.setFont(font);
        panel.add(jtfContact);

        jtfEmail = new JTextField();
        jtfEmail.setBounds(310, 215, 460, 30);
        jtfEmail.setFont(font);
        panel.add(jtfEmail);

        jtfAddress = new JTextField();
        jtfAddress.setBounds(310, 255, 460, 30);
        jtfAddress.setFont(font);
        panel.add(jtfAddress);

        jbtnAddEmp = new JButton("사원 등록");
        jbtnAddEmp.setBounds(660, 305, 110, 30);
        jbtnAddEmp.setFont(font);
        panel.add(jbtnAddEmp);

        jbtnInputPass = new JButton("비밀번호 입력");
        jbtnInputPass.setBounds(490, 305, 150, 30);
        jbtnInputPass.setFont(font);
        panel.add(jbtnInputPass);

        jbtnResetEmp = new JButton("초기화");
        jbtnResetEmp.setBounds(800, 570, 100, 30);
        jbtnResetEmp.setFont(font);
        panel.add(jbtnResetEmp);

        panel.add(makeLabel("이름", 210, 35, font));
        panel.add(makeLabel("사원번호", 210, 75, font));
        panel.add(makeLabel("비밀번호", 560, 75, font));
        panel.add(makeLabel("생년월일", 560, 35, font));
        panel.add(makeLabel("부서", 210, 120, font));
        panel.add(makeLabel("직급", 560, 120, font));
        panel.add(makeLabel("입사일", 210, 160, font));
        panel.add(makeLabel("연락처", 560, 160, font));
        panel.add(makeLabel("이메일", 210, 215, font));
        panel.add(makeLabel("주소", 210, 255, font));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(20, 360, 760, 280);
        tabbedPane.setFont(font);
        panel.add(tabbedPane);

        eduTabPanel = new SubTabPanel(new String[]{"edu_id", "입학년월", "졸업년월", "학교명", "전공", "학위", "졸업구분"}, null);
        careerTabPanel = new SubTabPanel(new String[]{"career_id", "회사명", "입사일자", "퇴사일자", "직급", "근무부서", "사유"}, null);
        certTabPanel = new SubTabPanel(new String[]{"cert_id", "자격증명", "발급기관", "취득일자", "유효일자"}, null);
        personnelTabPanel = new SubTabPanel(new String[]{"personnel_id", "발령구분", "발령일자", "발령부서", "발령직급"}, null);
        trainingTabPanel = new SubTabPanel(new String[]{"training_id", "교육기관", "교육명", "시작일자", "종료일자", "수료여부"}, null);

        hideIdColumn(eduTabPanel);
        hideIdColumn(careerTabPanel);
        hideIdColumn(certTabPanel);
        hideIdColumn(personnelTabPanel);
        hideIdColumn(trainingTabPanel);
        
        scaleSubTabPanel(eduTabPanel);
        scaleSubTabPanel(careerTabPanel);
        scaleSubTabPanel(certTabPanel);
        scaleSubTabPanel(personnelTabPanel);
        scaleSubTabPanel(trainingTabPanel);

        tabbedPane.addTab("학력", eduTabPanel);
        tabbedPane.addTab("경력", careerTabPanel);
        tabbedPane.addTab("자격증", certTabPanel);
        tabbedPane.addTab("인사", personnelTabPanel);
        tabbedPane.addTab("교육", trainingTabPanel);
        
        EmpViewEvt eve = new EmpViewEvt(this);
        jbtnEditImg.addActionListener(eve);
        jbtnAddEmp.addActionListener(eve);
        jbtnInputPass.addActionListener(eve);
        jbtnResetEmp.addActionListener(eve);



        contentPane.add(panel);
        add(contentPane, BorderLayout.CENTER);

        contentPane.setOpaque(false);
        panel.setOpaque(false);
        setVisible(true);
    }

    private JLabel makeLabel(String text, int x, int y, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 90, 30);
        label.setFont(font);
        return label;
    }

    private void scaleSubTabPanel(SubTabPanel panel) {
        Font font = new Font("Dialog", Font.BOLD, 15);
        JTable table = panel.getTable();
        table.setFont(font);
        table.setRowHeight(24);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 15));

        JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
        scrollPane.setBounds(10, 40, 730, 120);
    }

    public void hideIdColumn(SubTabPanel panel) {
        panel.getTable().getColumnModel().getColumn(0).setMinWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setMaxWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setWidth(0);
        panel.getTable().getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public SubTabPanel getEduTabPanel() { return eduTabPanel; }
    public SubTabPanel getCareerTabPanel() { return careerTabPanel; }
    public SubTabPanel getCertTabPanel() { return certTabPanel; }
    public SubTabPanel getPersonnelTabPanel() { return personnelTabPanel; }
    public SubTabPanel getTrainingTabPanel() { return trainingTabPanel; }
    public JPanel getMainPanel() {
        return contentPane.getComponentCount() > 0 ? (JPanel) contentPane.getComponent(0) : null;
    }// class


    
    public JTextField getJtfEmpno() { return jtfEmpno; }
    public JPasswordField getJpfPass() { return jpfPass; }
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

    public void setFieldsEditable(boolean editable) {
        jtfEmpno.setEditable(editable);
        jpfPass.setEditable(editable);
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