package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import kr.co.sist.admin.evt.RankEvt;

/**
 * 
 */
public class RankView extends JPanel{

	private static final long serialVersionUID = 3568676053726060081L;
	private JPanel frame;
	private JTable jtbRank;
	private DefaultTableModel dtmRank;

	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RankView window = new RankView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	public RankView() {
		
    	setPreferredSize(new Dimension(600, 1000));
    	
    	
		frame = new JPanel();
		frame.setBounds(100, 100, 450, 800);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// 테이블의 컬럼명 추가
		String[] columnNames = { "직급", "사원수(명)" };
		dtmRank = new DefaultTableModel(columnNames, 0) {
        	
			private static final long serialVersionUID = -7984279552610965548L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
        };

		jtbRank = new JTable(dtmRank);
		
        JTableHeader jthTable = jtbRank.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 16));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));
        jthTable.setPreferredSize(new Dimension(jthTable.getWidth(), 40));
        
        jtbRank.setRowHeight(36);
        jtbRank.setFont(new Font("Dialog", Font.BOLD, 20));
        
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	    for (int i = 0; i < jtbRank.getColumnCount(); i++) {
	        jtbRank.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

		// 스크롤 가능하도록 JScrollPane 추가
		JScrollPane scrollPane = new JScrollPane(jtbRank);
//		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.add(scrollPane);
		add(frame);

		// ✅ 이벤트 연결
		RankEvt evt = new RankEvt(this);
		evt.loadRankTable();
		
		setVisible(true);
	}
	
	/*
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// 테이블의 컬럼명 추가
		String[] columnNames = { "직급", "사원수(명)" };
		dtmRank = new DefaultTableModel(columnNames, 0);
		jtbRank = new JTable(dtmRank);

		// 스크롤 가능하도록 JScrollPane 추가
		JScrollPane scrollPane = new JScrollPane(jtbRank);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// ✅ 이벤트 연결
		RankEvt evt = new RankEvt(this);
		evt.loadRankTable();
	}
	*/



	public JTable getJtbRank() {
		return jtbRank;
	}

	public JPanel getFrame() {
		return frame;
	}

	public void setFrame(JPanel frame) {
		this.frame = frame;
	}

	public void setJtbRank(JTable jtbRank) {
		this.jtbRank = jtbRank;
	}

	public void setDtmRank(DefaultTableModel dtmRank) {
		this.dtmRank = dtmRank;
	}

	public DefaultTableModel getDtmRank() {
		return dtmRank;
	}
	
}//class
