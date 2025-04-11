package kr.co.sist.admin.evt;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.SalaryService;
import kr.co.sist.admin.view.SalaryView;
import kr.co.sist.admin.vo.RankVO;

/**
 * 
 */
public class SalaryEvt {
	private SalaryView sv;

	public SalaryEvt(SalaryView sv) {
		this.sv = sv;
	}

	public void loadSalaryTable() {
		DefaultTableModel dtm = sv.getDtmSalary();
		dtm.setRowCount(0); // 기존 테이블 초기화

		try {
			SalaryService sService = new SalaryService();
			List<RankVO> salaryList = sService.searchSalary();

			// 숫자 포맷 지정
			DecimalFormat df = new DecimalFormat("#,###");

			for (RankVO rVO : salaryList) {
				Object[] rowData = {
					rVO.getRankName(),
					df.format(rVO.getSalary())  // DecimalFormat 적용
				};
				dtm.addRow(rowData);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}//class
