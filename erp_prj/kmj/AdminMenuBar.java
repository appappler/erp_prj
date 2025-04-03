package kr.co.sist.design;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class AdminMenuBar extends JPanel {
    private JButton jbtnMenuHome, jbtnMenuAdd, jbtnMenuList, jbtnMenuAttend,
                    jbtnMenuPayroll, jbtnMenuSalary, jbtnMenuRank, jbtnMenuDept, jbtnMenuDocument;

    public AdminMenuBar() {
        setLayout(new GridLayout(9,1));
        	//테이블 연동해야됨. 임시로 넣음
        jbtnMenuHome = new JButton("홈");
        jbtnMenuAdd = new JButton("사원 추가");
        jbtnMenuList = new JButton("사원 목록");
        jbtnMenuAttend = new JButton("근태 관리");
        jbtnMenuPayroll = new JButton("급여 관리");
        jbtnMenuSalary = new JButton("연봉 관리");
        jbtnMenuRank = new JButton("직급 관리");
        jbtnMenuDept = new JButton("부서 관리");
        jbtnMenuDocument = new JButton("문서 관리");

        add(jbtnMenuHome);
        add(jbtnMenuAdd);
        add(jbtnMenuList);
        add(jbtnMenuAttend);
        add(jbtnMenuPayroll);
        add(jbtnMenuSalary);
        add(jbtnMenuRank);
        add(jbtnMenuDept);
        add(jbtnMenuDocument);
    }

    public JButton getJbtnMenuHome() {
        return jbtnMenuHome;
    }

    public JButton getJbtnMenuAdd() {
        return jbtnMenuAdd;
    }

    public JButton getJbtnMenuList() {
        return jbtnMenuList;
    }

    public JButton getJbtnMenuAttend() {
        return jbtnMenuAttend;
    }

    public JButton getJbtnMenuPayroll() {
        return jbtnMenuPayroll;
    }

    public JButton getJbtnMenuSalary() {
        return jbtnMenuSalary;
    }

    public JButton getJbtnMenuRank() {
        return jbtnMenuRank;
    }

    public JButton getJbtnMenuDept() {
        return jbtnMenuDept;
    }

    public JButton getJbtnMenuDocument() {
        return jbtnMenuDocument;
    }
}//class
