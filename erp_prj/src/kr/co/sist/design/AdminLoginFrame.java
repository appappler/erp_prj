package kr.co.sist.design;

import javax.swing.*;

import kr.co.sist.evt.AdminLoginEvent;

import java.awt.*;

@SuppressWarnings("serial")
public class AdminLoginFrame extends JFrame {
    private JTextField jtfId;          // 아이디 입력 필드
    private JPasswordField jpfPass;    // 비밀번호 입력 필드
    private JButton jbtnLogin, jbtnExit;
    private AdminLoginEvent loginEvent;

    public AdminLoginFrame() {
        setTitle("관리자 로그인");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // 입력 필드 생성
        jtfId = new JTextField();
        jpfPass = new JPasswordField();
        
        jbtnLogin = new JButton("로그인");
        jbtnExit = new JButton("종료");

        add(new JLabel("아이디:"));
        add(jtfId);
        add(new JLabel("비밀번호:"));
        add(jpfPass);
        add(jbtnLogin);
        add(jbtnExit);

        loginEvent = new AdminLoginEvent(this);
        jbtnLogin.addActionListener(loginEvent);
        jbtnExit.addActionListener(e -> System.exit(0));

        addWindowListener(loginEvent);

        setVisible(true);
    }

    // ✅ ID와 Password를 가져오는 메서드 추가
    public String getAdminId() {
        return jtfId.getText();
    }

    public String getAdminPass() {
        return new String(jpfPass.getPassword());
    }
}
