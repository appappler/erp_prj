package kr.co.sist.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import kr.co.sist.admin.evt.SalaryEvt;

/**
 * 
 */
public class SalaryView extends JPanel{

	private static final long serialVersionUID = 1476560443780096656L;
	private JPanel frame;
    private JTable jtbSalary;
    private DefaultTableModel dtmSalary;

    public SalaryView() {
    	
      	setPreferredSize(new Dimension(600, 880));
    	
        frame = new JPanel();
        frame.setBounds(100, 100, 450, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 테이블 컬럼명 설정
        String[] columnNames = {"직급", "기본급"};
        dtmSalary = new DefaultTableModel(columnNames, 0) {
        	
			private static final long serialVersionUID = -7984279552610965548L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
        };
        jtbSalary = new JTable(dtmSalary);
        
        JTableHeader jthTable = jtbSalary.getTableHeader();
        jthTable.setFont(new Font("Dialog", Font.BOLD, 16));
        jthTable.setForeground(Color.white);
        jthTable.setBackground(new Color(8, 60, 80));
        jthTable.setPreferredSize(new Dimension(jthTable.getWidth(), 40));
        
        jtbSalary.setRowHeight(40);
        jtbSalary.setFont(new Font("Dialog", Font.BOLD, 20));
        
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	    for (int i = 0; i < jtbSalary.getColumnCount(); i++) {
	    	jtbSalary.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

        // 테이블을 스크롤 가능하도록 JScrollPane 추가
        JScrollPane scrollPane = new JScrollPane(jtbSalary);

        // 우상단에 "(단위 : 원 / 년)"을 배치할 JPanel 생성
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel lblUnit = new JLabel("(단위 : 원 / 년)");
        lblUnit.setHorizontalAlignment(SwingConstants.RIGHT); // 우측 정렬

        topPanel.add(lblUnit, BorderLayout.EAST); // 우측 정렬된 라벨 추가
        frame.add(topPanel);
        frame.add(scrollPane);
        add(frame);
        
        setVisible(true);
    
        SalaryEvt evt = new SalaryEvt(this);
        evt.loadSalaryTable();
    }



    public JTable getJtbSalary() {
        return jtbSalary;
    }

    public JPanel getFrame() {
		return frame;
	}

	public void setFrame(JPanel frame) {
		this.frame = frame;
	}

	public void setJtbSalary(JTable jtbSalary) {
		this.jtbSalary = jtbSalary;
	}

	public void setDtmSalary(DefaultTableModel dtmSalary) {
		this.dtmSalary = dtmSalary;
	}

	public DefaultTableModel getDtmSalary() {
        return dtmSalary;
    }
	
}//class
