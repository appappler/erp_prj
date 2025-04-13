package kr.co.sist.user.view;

import javax.swing.*;
import java.awt.*;
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
        // 수정: VO에서 채워진 사원이름(empName)을 사용
        this.empName = uaVO.getEmpName();
        this.uaVO = uaVO;
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        
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
        
        lblMessage = new JLabel(" ", SwingConstants.CENTER);
        lblMessage.setOpaque(true);
        lblMessage.setBackground(Color.YELLOW);
        lblMessage.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
        lblMessage.setPreferredSize(new Dimension(600, 50));
        lblMessage.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        add(lblMessage, BorderLayout.SOUTH);
        
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
