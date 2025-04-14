package kr.co.sist.admin.view;

import javax.swing.*;

import kr.co.sist.admin.evt.SalarySearchEvt;

/**
*
*/
public class MainFrame extends JPanel {

    private static final long serialVersionUID = 2466076878779159402L;

    public MainFrame() {
        setSize(1000, 900);
        salarySearchView = new SalarySearchView();
        new SalarySearchEvt(salarySearchView);
        add(salarySearchView);
        setVisible(true);
    }

   private SalarySearchView salarySearchView;

   public SalarySearchView getSalarySearchView() {
       return salarySearchView;
   }
   
}//class

/*
package kr.co.sist.admin.view;

import javax.swing.*;

import kr.co.sist.admin.evt.SalarySearchEvt;


public class MainFrame extends JPanel {

    private static final long serialVersionUID = 2466076878779159402L;

	public MainFrame() {
//        setTitle("급여관리 시스템");
        setSize(1000, 900);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);

        // 👉 검색 화면만 표시하고 끝
        SalarySearchView view = new SalarySearchView(); // 리스너 없이 생성
        new SalarySearchEvt(view); // 이벤트가 모든 동작 처리
        add(view);

        setVisible(true); // 여기서 바로 보여줘도 됨
    }

    public static void main(String[] args) {
       new MainFrame();
    }
    
}//class

*/