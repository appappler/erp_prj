package csj_evt;

import csj_dao.AttendanceDAO;
import csj_vo.AttendanceVO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AttendanceEvt {
    private DefaultTableModel tableModel;
    private JLabel lblAttendance, lblLeave, lblAbsent;
    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    public AttendanceEvt(DefaultTableModel tableModel, JLabel lblAttendance, JLabel lblLeave, JLabel lblAbsent) {
        this.tableModel = tableModel;
        this.lblAttendance = lblAttendance;
        this.lblLeave = lblLeave;
        this.lblAbsent = lblAbsent;
    }

    public void loadAttendanceData() {
        List<AttendanceVO> employees = attendanceDAO.getAllEmployees();
        tableModel.setRowCount(0);

        int attendanceCount = 0, leaveCount = 0, absentCount = 0;
        for (AttendanceVO emp : employees) {
            String status = (emp.getStatusId() == 1) ? "출근" : (emp.getStatusId() == 2) ? "퇴근" : "결근";
            if (status.equals("출근")) attendanceCount++;
            else if (status.equals("퇴근")) leaveCount++;
            else absentCount++;
            tableModel.addRow(new Object[]{emp.getEmpNo(), emp.getInTime(), emp.getOutTime(), emp.getWorkHours(), emp.getRemarks(), status});
        }
        lblAttendance.setText("출근 " + attendanceCount + "명");
        lblLeave.setText("퇴근 " + leaveCount + "명");
        lblAbsent.setText("결근 " + absentCount + "명");
    }
}