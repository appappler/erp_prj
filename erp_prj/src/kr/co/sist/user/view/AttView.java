package kr.co.sist.user.view;

import javax.swing.*;
import java.awt.*;

import kr.co.sist.user.dao.AttDAO;
import kr.co.sist.user.dao.UserLoginDAO;
import kr.co.sist.user.evt.AttEvent;
import kr.co.sist.user.vo.UserAccountVO;

public class AttView extends JPanel {

	private JButton btnCheckIn;
	private JButton btnCheckOut;
	private JButton btnEarlyLeave;
	private JLabel lblMessage;
	private String empName;
	private UserAccountVO uaVO;
	
	public AttView(UserAccountVO uaVO) {
		
	    this.empName = uaVO.getUserId();
	    this.uaVO = uaVO;
	    
	    AttDAO aDAO = new AttDAO();
	   

	    
	    
		Color MainColor = new Color(8, 60, 80);
		Color SubColor = new Color(226,240,248);
		Font MenuFont = new Font("Dialog", Font.BOLD, 24);
		
	    setLayout(null);
	    setPreferredSize(new Dimension(1100, 600));

	    /*
	    
	    // 중앙 패널: 버튼 배치
	    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 80));
	    btnCheckIn = new JButton("출근");
	    btnCheckOut = new JButton("퇴근");
	    btnEarlyLeave = new JButton("조퇴");
	    Dimension btnSize = new Dimension(150, 80);
	    btnCheckIn.setPreferredSize(btnSize);
	    btnCheckOut.setPreferredSize(btnSize);
	    btnEarlyLeave.setPreferredSize(btnSize);
	    centerPanel.add(btnCheckIn);
	    centerPanel.add(btnCheckOut);
	    centerPanel.add(btnEarlyLeave);
	    add(centerPanel, BorderLayout.CENTER);
	    
	    btnCheckIn.setBackground(MainColor);
	    btnCheckIn.setForeground(Color.white);
	    btnCheckIn.setFont(new Font("Dialog", Font.BOLD, 36));
	    
	    btnCheckOut.setBackground(MainColor);
	    btnCheckOut.setForeground(Color.white);
	    btnCheckOut.setFont(new Font("Dialog", Font.BOLD, 36));
	    
	    btnEarlyLeave.setBackground(MainColor);
	    btnEarlyLeave.setForeground(Color.white);
	    btnEarlyLeave.setFont(new Font("Dialog", Font.BOLD, 36));
	    
	    // 하단 패널: 메시지 라벨
	    lblMessage = new JLabel(" ", SwingConstants.CENTER);
	    lblMessage.setOpaque(true);
	    lblMessage.setBackground(Color.YELLOW);
	    lblMessage.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));  // 윈도우라면
	    lblMessage.setPreferredSize(new Dimension(600, 50));
	    lblMessage.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	    add(lblMessage, BorderLayout.SOUTH);
	    
	    */
	    
	    JPanel centerPanel = new JPanel();
	    btnCheckIn = new JButton("출근");
	    btnCheckOut = new JButton("퇴근");
	    btnEarlyLeave = new JButton("조퇴");
	    
	    centerPanel.setBounds(0, 80, 1100, 200);
	    centerPanel.add(btnCheckIn);
	    centerPanel.add(btnCheckOut);
	    centerPanel.add(btnEarlyLeave);
	    
//	    centerPanel.setBackground(Color.red);
	    
	    Dimension btnSize = new Dimension(220, 160);
	    btnCheckIn.setPreferredSize(btnSize);
	    btnCheckOut.setPreferredSize(btnSize);
	    btnEarlyLeave.setPreferredSize(btnSize);
	    
//	    btnCheckIn.setBounds(20,100,300,100);
//	    btnCheckOut.setBounds(120,100,300,100);
//	    btnEarlyLeave.setBounds(320,100,300,100);
	    
	    add(centerPanel);
	    
	    JPanel bottomPanel = new JPanel();
	    lblMessage = new JLabel(" ", SwingConstants.CENTER);
	    lblMessage.setOpaque(true);
//	    lblMessage.setBackground(Color.YELLOW);
	    lblMessage.setBackground(new Color(158,249,202));
//	    lblMessage.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));  // 윈도우라면
	    lblMessage.setFont(new Font("Malgun Gothic", Font.BOLD, 32));  // 윈도우라면
	    lblMessage.setForeground(MainColor);
	    lblMessage.setPreferredSize(new Dimension(800, 140));
	    lblMessage.setBorder(BorderFactory.createLineBorder(MainColor, 2));
	    
	    bottomPanel.add(lblMessage);
	    bottomPanel.setBounds(0, 300, 1100, 500);
	    add(bottomPanel);
	    
	    btnCheckIn.setBackground(MainColor);
	    btnCheckIn.setForeground(Color.white);
	    btnCheckIn.setFont(new Font("Dialog", Font.BOLD, 36));
	    
	    btnCheckOut.setBackground(MainColor);
	    btnCheckOut.setForeground(Color.white);
	    btnCheckOut.setFont(new Font("Dialog", Font.BOLD, 36));
	    
	    btnEarlyLeave.setBackground(MainColor);
	    btnEarlyLeave.setForeground(Color.white);
	    btnEarlyLeave.setFont(new Font("Dialog", Font.BOLD, 36));
	    
	    
	    setVisible(true);
	    
//	    add(lblMessage, BorderLayout.SOUTH);
	    
	    
	    
	    

	    // AttEvent 등록: 버튼 클릭 시 이벤트 핸들링
	    new AttEvent(this);
	}

	public UserAccountVO getUaVO() {
		return uaVO;
	}

	public JButton getBtnCheckIn() { return btnCheckIn; }
	public JButton getBtnCheckOut() { return btnCheckOut; }
	public JButton getBtnEarlyLeave() { return btnEarlyLeave; }
	public JLabel getLblMessage() { return lblMessage; }
	public String getEmpName() { return empName; }
}
// 필드 선언 (클래스 멤버)
