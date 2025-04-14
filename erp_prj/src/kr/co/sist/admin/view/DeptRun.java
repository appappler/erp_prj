package kr.co.sist.admin.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import kr.co.sist.admin.evt.DeptViewEvt;

public class DeptRun extends JPanel{
	
	/*
	public static void main(String[] args) {

//		new DeptInfoView();
		
		// 메인 윈도우 프레임 생성
		JFrame jf = new JFrame("부서 관리");
		jf.setBackground(Color.red);
		
		// View
		DeptInfoView div = new DeptInfoView();
		DeptAddView dav = new DeptAddView();
		DeptDialogView ddv = new DeptDialogView();
		
//		div.setBackground(Color.red);

		// 이벤트 연결
		new DeptViewEvt(div, dav, ddv);

		// View 프레임에 붙이기
		jf.add(div);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(100, 100, 700, 500);
		jf.setResizable(false);

	}// main
	*/
	
	private static final long serialVersionUID = -3319399050208150706L;

	public DeptRun() {
		
	   	setPreferredSize(new Dimension(1100, 700));
		
		// 메인 JPanel 생성
		JPanel jf = new JPanel();
		jf.setOpaque(false);
		// View
		DeptInfoView div = new DeptInfoView();
		DeptAddView dav = new DeptAddView();
		DeptDialogView ddv = new DeptDialogView();
		
//		div.setBackground(Color.red);

		// 이벤트 연결
		new DeptViewEvt(div, dav, ddv);

		// View 프레임에 붙이기
		jf.add(div);
		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(20, 20, 900, 500);
//		jf.setResizable(false);
		add(jf);

	}

}// class

