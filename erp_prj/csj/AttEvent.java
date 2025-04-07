package evt;

import service.AttService;
import view.AttView;
import vo.AttVO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AttEvent {
    private AttView view;
    private AttService service;

    public AttEvent(AttView view) {
        this.view = view;
        service = new AttService();

        // 검색 버튼 이벤트 등록
        this.view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAction();
            }
        });

        // 프로그램 시작 시 전체 데이터 로드 (조건 없이)
        loadAllEmployees();

        // 테이블의 "상태" 열(마지막 열)에 콤보박스 에디터 설정
        setStatusCellEditor();
    }

    // 전체 데이터를 로드하는 메서드
    private void loadAllEmployees() {
        List<AttVO> list = service.searchAttendance("", "", "1900-01-01", "2099-12-31");
        updateTable(list);
    }

    // 검색 버튼 클릭 시 호출되는 메서드
    private void searchAction() {
        String dept = view.getTfDept().getText().trim();
        String name = view.getTfName().getText().trim();
        String startDate = view.getCbYearFrom().getSelectedItem() + "-" +
                           view.getCbMonthFrom().getSelectedItem() + "-" +
                           view.getCbDayFrom().getSelectedItem();
        String endDate = view.getCbYearTo().getSelectedItem() + "-" +
                         view.getCbMonthTo().getSelectedItem() + "-" +
                         view.getCbDayTo().getSelectedItem();
        List<AttVO> list = service.searchAttendance(dept, name, startDate, endDate);
        updateTable(list);
    }

    // JTable 모델을 업데이트
    private void updateTable(List<AttVO> list) {
        // 컬럼: att_id, 사번, 이름, 부서, 직급, 출근시간, 퇴근시간, 상태
        String[] columnNames = {"att_id", "사번", "이름", "부서", "직급", "출근시간", "퇴근시간", "상태"};
        
        // 상태 컬럼만 편집 가능하도록 모델 오버라이드
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // "상태" 열(인덱스 7)만 편집 가능
                return column == 7;
            }
        };

        for (AttVO vo : list) {
            Object[] rowData = {
                vo.getAttId(),        // 근태번호
                vo.getEmpNo(),        // 사원번호
                vo.getEmpName(),      // 사원명
                vo.getDeptName(),     // 부서명
                vo.getPositionName(), // 직급명
                vo.getInTime(),       // 출근시간
                vo.getOutTime(),      // 퇴근시간
                vo.getStatusId()      // 근태상태
            };
            model.addRow(rowData);
        }
        view.getTable().setModel(model);
        
        // 상태 변경 시 DB 업데이트를 위한 TableModelListener 추가
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 7) {
                    int row = e.getFirstRow();
                    int attId = (int) model.getValueAt(row, 0); // 0번 열: att_id
                    String newStatus = (String) model.getValueAt(row, 7);
                    service.updateStatus(attId, newStatus);
                    JOptionPane.showMessageDialog(view, "상태가 [" + newStatus + "]로 변경되었습니다.");
                }
            }
        });
    }

    // "상태" 열에 콤보박스(Cell Editor)를 적용
    private void setStatusCellEditor() {
        String[] statusItems = {"출근", "지각", "조퇴", "퇴근", "휴가", "결근"};
        JComboBox<String> comboBox = new JComboBox<>(statusItems);
        view.getTable().getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(comboBox));
    }
}
