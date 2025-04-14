

package kr.co.sist.admin.evt;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.dao.AttendanceDAO;
import kr.co.sist.admin.vo.AttendanceVO;

/**
 * 
 */
public class AttendanceEvt implements Runnable{
    private DefaultTableModel tableModel;
    private JLabel lblAttendance, lblLeave, lblAbsent, lblEarly;
    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    public AttendanceEvt(DefaultTableModel tableModel, JLabel lblAttendance,
    		JLabel lblLeave, JLabel lblAbsent, JLabel lblEarly) {
        this.tableModel = tableModel;
        
        this.lblAttendance = lblAttendance;
        this.lblLeave = lblLeave;
        this.lblAbsent = lblAbsent;
        this.lblEarly = lblEarly;
    }
    
    
    @Override
    public void run() {
    	
        List<AttendanceVO> employees = null;
        while( true ) {
        	
        employees= attendanceDAO.getAllEmployees();
        tableModel.setRowCount(0);

        int attendanceCount = 0, leaveCount = 0, absentCount = 0, earlyCount=0;

        for (AttendanceVO emp : employees) {
        	String status = "결근";
            if ("출근".equals(emp.getStatus_Id())) {
//                status.equals("출근");
                status = "출근";
                attendanceCount++;
            } else if ("퇴근".equals(emp.getStatus_Id())) {
//            	status.equals("퇴근");
            	status = "퇴근";
                leaveCount++;
            } else if ("조퇴".equals(emp.getStatus_Id())) {
//            	status.equals("퇴근");
            	status = "조퇴";
            	earlyCount++;
            } else {
                absentCount++;
            }

            tableModel.addRow(new Object[]{
                    emp.getEmpno(),
                    emp.getEmp_name(),
                    emp.getDeptname(),
                    emp.getPosition_name(),
                    emp.getIn_Time(),
                    emp.getOut_Time(),
                    status
            });
        }

        
        lblAttendance.setText("출근 " + attendanceCount + "명");
        lblLeave.setText("퇴근 " + leaveCount + "명");
        lblAbsent.setText("결근 " + absentCount + "명");
        lblEarly.setText("조퇴 " + earlyCount + "명");
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        }
    }
    
}//class
