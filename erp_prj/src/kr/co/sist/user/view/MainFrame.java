package kr.co.sist.user.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import kr.co.sist.user.evt.SalarySearchEmployeeEvt;
import kr.co.sist.user.vo.UserAccountVO;

public class MainFrame extends JPanel {

    private static final long serialVersionUID = 8402304884691432098L;
	public MainFrame(UserAccountVO uaVO) {
//        setTitle("급여관리 시스템 - 사원 전용");
		setPreferredSize(new Dimension(1200, 600));
        setSize(1200, 1000);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);

        // 🔹 사원용 급여 조회 화면 (사번 1001로 고정)
//        String empno = "1002";
        String empno = uaVO.getUserId();
        SalarySearchEmployeeView view = new SalarySearchEmployeeView(empno);
        new SalarySearchEmployeeEvt(view); // 이벤트 연결
        add(view);

        setVisible(true);
    }

//    public static void main(String[] args) {
//        new MainFrame();
//    }
}
