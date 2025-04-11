package kr.co.sist.admin.evt;

import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import kr.co.sist.admin.service.RankService;
import kr.co.sist.admin.view.RankView;
import kr.co.sist.admin.vo.RankVO;

/**
 * 
 */
public class RankEvt {
	private RankView rv;

	public RankEvt(RankView rv) {
		this.rv = rv;
	}

	public void loadRankTable() {
		DefaultTableModel dtm = rv.getDtmRank();
		dtm.setRowCount(0); // 기존 데이터 초기화

		try {
			RankService rService = new RankService();
			List<RankVO> rankList = rService.searchRank();

			int totalEmpCount = 0;

			for (RankVO rVO : rankList) {
				Object[] rowData = { rVO.getRankName(), rVO.getEmpCount() };
				dtm.addRow(rowData);
				totalEmpCount += rVO.getEmpCount();
			}

			// 총계 행 추가
			Object[] totalRow = { "총계", totalEmpCount };
			dtm.addRow(totalRow);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}//class
