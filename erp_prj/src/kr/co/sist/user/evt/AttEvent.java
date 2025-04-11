package kr.co.sist.user.evt;

import java.awt.event.ActionEvent; import java.awt.event.ActionListener; import java.time.LocalDateTime; import java.time.format.DateTimeFormatter; import javax.swing.JOptionPane; import kr.co.sist.user.service.AttService; import kr.co.sist.user.view.AttView;

public class AttEvent { private AttView view;


public AttEvent(AttView view) {
    this.view = view;
    System.out.println("[DEBUG] AttEvent 등록된 AttView, 해시코드: " + view.hashCode());
    
    // 출근 버튼 이벤트 등록
    view.getBtnCheckIn().addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            handleAttendance("출근", "출근하셨습니다.");
        }
    });
    // 퇴근 버튼 이벤트 등록
    view.getBtnCheckOut().addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            handleAttendance("퇴근", "퇴근하셨습니다.");
        }
    });
    // 조퇴 버튼 이벤트 등록
    view.getBtnEarlyLeave().addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            handleAttendance("조퇴", "조퇴하셨습니다.");
        }
    });
}

private void handleAttendance(String status, String message) {
    System.out.println("[DEBUG] handleAttendance 호출됨, status = " + status);
    // 현재 날짜 및 시간 구하기
    LocalDateTime now = LocalDateTime.now();
    String currentTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    String currentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String empName = view.getEmpName();
    
    // 메시지 구성: "사원이름님 현재시간 상태하셨습니다."
    String displayMsg = empName + "님 " + currentTime + " " + message;
    view.getLblMessage().setText(displayMsg);
    view.getLblMessage().revalidate();
    view.getLblMessage().repaint();
    
    System.out.println("[DEBUG] lblMessage 텍스트: " + view.getLblMessage().getText());
    
    try {
        // 실제 empNo는 로그인 VO 등에서 전달받아야 합니다.
        // 테스트용으로 empNo를 1001로 하드코딩합니다.
        int empNo = Integer.parseInt(view.getUaVO().getUserId());
        
        AttService service = new AttService();
        
        if("출근".equals(status)) {
            // 하루에 이미 출근 기록이 있는지 체크
            boolean alreadyChecked = service.checkIfCheckedIn(empNo, currentDate);
            if(alreadyChecked) {
                JOptionPane.showMessageDialog(view, "이미 출근 기록이 있습니다.");
                return;
            }
            // 없으면 INSERT 수행
            service.insertAttendance(empNo, empName, currentTime, status);
            System.out.println("[DEBUG] DB INSERT 완료: empNo=" + empNo + ", empName=" + empName + ", inTime=" + currentTime + ", status=" + status);
        } else if("퇴근".equals(status) || "조퇴".equals(status)) {
            // 출근 기록이 없다면 UPDATE 불가
            boolean exists = service.checkIfCheckedIn(empNo, currentDate);
            if(!exists) {
                JOptionPane.showMessageDialog(view, "출근 기록이 없습니다. 먼저 출근을 해주세요.");
                return;
            }
            // 출근 기록이 있으면 UPDATE 수행
            service.updateAttendanceStatus(empNo, currentTime, status);
            System.out.println("[DEBUG] DB UPDATE 완료: empNo=" + empNo + ", outTime=" + currentTime + ", status=" + status);
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
}

}