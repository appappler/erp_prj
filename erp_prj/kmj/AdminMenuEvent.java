package kr.co.sist.evt;

import java.awt.event.*;

import kr.co.sist.design.AdminMenuBar;
import kr.co.sist.design.AdminParentFrame;

public class AdminMenuEvent extends WindowAdapter implements ActionListener {
    private AdminParentFrame apf;
    private AdminMenuBar amb;

    public AdminMenuEvent(AdminMenuBar amb, AdminParentFrame apf) {
        this.amb = amb;
        this.apf = apf;

        // 각 버튼에 액션 리스너 등록 -> this 임시 표시 연동되게 변경
        amb.getJbtnMenuHome().addActionListener(this);
        amb.getJbtnMenuAdd().addActionListener(this);
        amb.getJbtnMenuList().addActionListener(this);
        amb.getJbtnMenuAttend().addActionListener(this);
        amb.getJbtnMenuPayroll().addActionListener(this);
        amb.getJbtnMenuSalary().addActionListener(this);
        amb.getJbtnMenuRank().addActionListener(this);
        amb.getJbtnMenuDept().addActionListener(this);
        amb.getJbtnMenuDocument().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
    	// 제목을 변경
    }

    @Override
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
    
}//class
