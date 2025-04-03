package kr.co.sist.evt;

import java.awt.event.*;

import javax.swing.JOptionPane;

import kr.co.sist.design.AdminLoginFrame;
import kr.co.sist.design.AdminParentFrame;
import kr.co.sist.service.AdminLoginService;
import kr.co.sist.vo.AdminAccountVO;

public class AdminLoginEvent extends WindowAdapter implements ActionListener {
    private AdminLoginFrame alf;
    private AdminParentFrame apf;
    
    public AdminLoginEvent(AdminLoginFrame alf) {
        this.alf = alf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = alf.getAdminId();
        String pass = alf.getAdminPass();

        AdminAccountVO aav = new AdminAccountVO(id, pass);
        AdminLoginService loginService = new AdminLoginService();
        
        if (loginService.Login(aav)) {
            alf.dispose(); // 로그인 창 닫기
            apf=new AdminParentFrame();
            // 이후 메인 화면으로 이동 가능
        } else {
            JOptionPane.showMessageDialog(alf, "아이디 는 비밀번호가 잘못되었습니다.", 
                                          "로그인 실패", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
}//class
