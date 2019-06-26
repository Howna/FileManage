package com.file.manage.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.file.manage.action.CopyRowAction;
import com.file.manage.action.DelRowAction;
import com.file.manage.action.ReloadAction;
import com.jfinal.kit.StrKit;

@SuppressWarnings("serial")
public abstract class DataTable extends JPanel {
	protected JTable table;
	protected JButton btnFirst;
	protected JButton btnPre;
	protected JButton btnNext;
	protected JButton btnLast;
	private JLabel lblPageMsg;
	protected Map<Integer, TableCellRenderer> render = new HashMap<Integer, TableCellRenderer>();
	private Map<Integer, TableCellEditor> editors = new HashMap<Integer, TableCellEditor>();
	/**
	 * 默认分页
	 */
	private boolean pagination = false;
//	/**
//	 * 默认不显示日志窗口
//	 */
//	private boolean showLog = false;
	/**
	 * 列名
	 */
	private String[] colNames;
	/**
	 * 列数据
	 */
	private Object[][] datas;
	/**
	 * 列宽
	 */
	private int[] colWidth;
	protected int pageNumber = 1;
	protected int pageSize = 50;
	protected int totalPage = 0;
	protected int totalRow = 0;
	protected boolean isFirstPage = false;
	protected boolean isLastPage = false;
	private JTextField txtPage;
	private JButton btnGo;
	private String pageMsg = "%s/%s,总数:%s";
	private JProgressBar progressBar;
	private JPopupMenu popupMenu = new JPopupMenu();
	private JButton btnDelAll;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea txtLog;
	private JPanel panelPage = new JPanel();
	/**
	 * 创建默认的示例表格,不分页
	 */
//	public DataTable() {
//		this(new String[] {""},new Object[][] {{null}},null);
//	}
	/**
	 * 创建指定列的表格
	 */
//	public DataTable(String[] colNames) {
//		this(colNames,null, null);
//	}
	/**
	 * 创建指定列的表格
	 */
//	public DataTable(String[] colNames,Object[][] datas) {
//		this(colNames,datas, null);
//	}
	/**
	 * 创建指定列的表格
	 */
//	public DataTable(String[] colNames,int[] width) {
//		this(colNames,new Object[][] {null}, width);
//	}
	/**
	 * 创建指定列的表格
	 */
//	public DataTable(String[] colNames,Object[][] datas,int[] width) {
//		this(colNames, datas, width, false);
//	}
	/**
	 * 创建指定列的表格
	 */
//	public DataTable(String[] colNames,Object[][] datas,int[] width,boolean pagination) {
//		this(colNames, datas, width, pagination, false);
//	}
	/**
	 * 创建指定列的表格
	 * @param colNames 列名
	 * @param datas 行数据
	 * @param width 列宽带,数量需要跟列名一致
	 * @param pagination 是否显示分页
	 */
	public DataTable(String[] colNames,Object[][] datas,int[] width,boolean pagination) {
		if (colNames == null || colNames.length == 0) {
			throw new IllegalArgumentException("初始化表格时,必须至少有一列");
		}
//		if (datas == null || datas.length == 0) {
//			throw new IllegalArgumentException("初始化表格时,必须至少有一行");
//		}
		this.colNames = colNames;
		this.datas = datas;
		this.colWidth = width;
		this.pagination = pagination;
//		this.showLog = showLog;
		if (width != null && colNames.length != width.length) {
			throw new IllegalArgumentException("列数和列的宽度数不一致");
		}
		init();
		Object[][] tableData = fillTable();
		if (tableData != null) {
			TableModel model = new DefaultTableModel(tableData, getColNames()) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			UI.refreshTable(table, model,getColWidth(),hideColumns(),render,editors);
			if (rowHight() > 0) {
				table.setRowHeight(rowHight());
			}
			pageInfo();
		}
	}
	/**
	 * 默认构造函数
	 */
	private void init() {
		setLayout(new BorderLayout(0, 0));
		/**
		 * 用户自定义的右键菜单
		 */
		List<JMenuItem> menuItems = addPopMenu();
		if (menuItems != null && menuItems.size() > 0) {
			for (JMenuItem menuItem : menuItems) {
				popupMenu.add(menuItem);
			}
		}
		//默认菜单
        popupMenu.add(new JMenuItem(new CopyRowAction(this,"复制")));
        popupMenu.add(new JMenuItem(new ReloadAction(this,"刷新")));
        popupMenu.add(new JMenuItem(new DelRowAction(this,"删除")));
		
		
		FlowLayout fl_panelPage = (FlowLayout) panelPage.getLayout();
		fl_panelPage.setAlignment(FlowLayout.LEFT);
		add(panelPage, BorderLayout.SOUTH);
		
		btnFirst = new JButton(new PageAction("首页"));
		btnFirst.setEnabled(false);//默认不可用
		panelPage.add(btnFirst);
		
		btnPre = new JButton(new PageAction("上一页"));
		btnPre.setEnabled(false);//默认不可用
		panelPage.add(btnPre);
		
		btnNext = new JButton(new PageAction("下一页"));
		btnNext.setEnabled(false);//默认不可用
		panelPage.add(btnNext);
		
		btnLast = new JButton(new PageAction("末页"));
		btnLast.setEnabled(false);//默认不可用
		panelPage.add(btnLast);
		
		txtPage = new JTextField();
		panelPage.add(txtPage);
		txtPage.setColumns(5);
		
		btnGo = new JButton(new PageAction("跳转"));
		panelPage.add(btnGo);
		
		if (showDelAll()) {
			btnDelAll = new JButton("清空数据");
			btnDelAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (UI.showConfirmDialog("确认清空表格里所有数据？此操作不可恢复") == UI.CONFIRM_YES) {
						delAll();
						reload();
					}
				}
			});
			panelPage.add(btnDelAll);
		}
		
		lblPageMsg = new JLabel(String.format(pageMsg, 0,0,0));
		panelPage.add(lblPageMsg);
		
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		panelPage.add(progressBar);
		
		panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		JScrollPane tablePane = new JScrollPane();
		panel.add(tablePane, BorderLayout.CENTER);
		table = new JTable();
		center(table);
		table.setModel(new DefaultTableModel(datas,colNames));
		//为JTable对象添加点击事件
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
				if (SwingUtilities.isRightMouseButton(e)) {
//					if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
					//通过点击位置找到点击为表格中的行
					int focusedRowIndex = table.rowAtPoint(e.getPoint());
					if (focusedRowIndex == -1) {
						return;
					}
					//将表格所选项设为当前右键点击的行
					table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
					//弹出菜单
					popupMenu.show(table, e.getX(), e.getY());
				}
			}
		});
		tablePane.setViewportView(table);
		
		if (showLog()) {
			scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.NORTH);
			
			txtLog = new JTextArea();
			txtLog.setRows(8);
//		panel.add(textArea, BorderLayout.SOUTH);
			scrollPane.setViewportView(txtLog);
		}
		
		if (!pagination) {
			panelPage.setVisible(false);
		}

	}
	public void initLog() {  
		if (scrollPane == null || txtLog == null) {
			return;
		}
		if (!showLog()) {
			return;
		}
        try {  
//            Thread t2 = new TextAreaLogAppender(txtLog, scrollPane);  
//            t2.start();  
        } catch (Exception e) {
        	e.printStackTrace();
//            JOptionPane.showMessageDialog(this, e, "绑定日志输出组件错误", JOptionPane.ERROR_MESSAGE);  
        }  
    }
	public int rowHight() {
		return 0;
	}
	/**
	 * 重写此方法，增加右键菜单
	 * @return
	 */
	public List<JMenuItem> addPopMenu() {
		return null;
	}
	/**
	 * 动态添加菜单
	 * @return
	 */
	public void addPopMenu(JMenuItem item) {
		addPopMenu(item, 0);
	}
	/**
	 * 动态添加菜单
	 * @return
	 */
	public void addPopMenu(JMenuItem item,int index) {
		popupMenu.add(item,index);
	}
	/**
	 * 要隐藏的列，要隐藏列，重写此方法即可
	 * @return
	 */
	public int[] hideColumns() {
		return null;
	}
	/**
	 * 行被删除的事件，重写后，可以做一些动作：删除db，调用api
	 * @param table 表格
	 * @param rowDel 被删除的行号
	 */
	public abstract void onRowDel(JTable table,int rowDel);
	/**
	 * 重新装载数据
	 */
	public void reload() {
		reload(0);
	}
	public void status(boolean show) {
		if (show) {
			getProgressBar().setVisible(true);
			getProgressBar().setIndeterminate(true);
//			getProgressBar().setString("采集中");
		}else {
			getProgressBar().setVisible(false);
		}
	}
	/**
	 * 重新装载数据
	 */
	public void reload(int startPage) {
		if (startPage > 0) {
			pageNumber = startPage;
		}
		Object[][] tableData = fillTable();
		TableModel model = new DefaultTableModel(tableData, getColNames()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		UI.refreshTable(table, model,getColWidth(),hideColumns(),render);
		if (rowHight() > 0) {
			table.setRowHeight(rowHight());
		}
		pageInfo();
	}
	/**
	 * 重新装载数据
	 * @param tableData
	 */
//	public void reload(Object[][] tableData) {
//		UI.refreshTable(table, new DefaultTableModel(tableData, getColNames()),getColWidth());
//		pageInfo();
//	}
	/**
	 * 写入表格,返回写入的 数据
	 */
//	public void refreshTable(Object[][] _datas) {
//		table.setModel(new DefaultTableModel(_datas,colNames));
//		if (colWidth != null) {
//			TableColumnModel columnModel = table.getColumnModel();
//			for (int i = 0; i < colWidth.length; i++) {
//				columnModel.getColumn(i).setPreferredWidth(getColWidth()[i]);
//			}
//		}
//	}
	/**
	 * 写入表格,返回写入的 数据
	 */
	public Object[][] fillTable() {
		return null;
	}
	/**
	 * 删除全部数据
	 */
	public void delAll() {
	}
	/**
	 * 分页操作
	 * @author tiany
	 *
	 */
	class PageAction extends AbstractAction{
		public PageAction(String name) {
			// TODO Auto-generated constructor stub
			super(name);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equals("首页")) {
				pageNumber = 1;
			}
			if (e.getActionCommand().equals("上一页")) {
				pageNumber--;
			}
			if (e.getActionCommand().equals("下一页")) {
				pageNumber++;
			}
			if (e.getActionCommand().equals("末页")) {
				pageNumber = totalPage;//最后一页
			}
			if (e.getActionCommand().equals("跳转")) {
				String page = txtPage.getText();
				if (StrKit.isBlank(page)) {
					UI.showErrorMessageDialog("请输入页码");
					return;
				}
				if (totalPage == 0) {
					return;
				}
				try {
					pageNumber = Integer.parseInt(page);//最后一页
					if (pageNumber > totalPage) {
						pageNumber = totalPage;
					}
				} catch (NumberFormatException e1) {
					UI.showErrorMessageDialog("输入的页码不正确");
					txtPage.selectAll();
					return;
				}
			}
			Object[][] tableData = fillTable();
			if (tableData != null) {
				TableModel model = new DefaultTableModel(tableData, getColNames()) {
					@Override
					public boolean isCellEditable(int row, int column) {
						// TODO Auto-generated method stub
						return false;
					}
				};
				UI.refreshTable(table, model,getColWidth(),hideColumns(),render);
				if (rowHight() > 0) {
					table.setRowHeight(120);
				}
				pageInfo();
			}
		}
		
	}
	
	protected void pageInfo() {
		//显示信息
//		String p = "第" + pageNumber + "页/共" + totalPage + "页,数据条数:" + totalRow;
		String p = String.format(pageMsg, pageNumber,totalPage,totalRow);
		UI.refreshLabel(getLblPageMsg(), p);
		//控制按钮
		btnFirst.setEnabled(true);
		btnPre.setEnabled(true);
		btnNext.setEnabled(true);
		btnLast.setEnabled(true);
		//按钮问题
		if (isFirstPage) {//当前页是第一页时，禁用上一页，第一页
			btnFirst.setEnabled(false);
			btnPre.setEnabled(false);
		}else if (isLastPage) {//当前页是第一页时，禁用上一页，第一页
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
		}else {
			btnFirst.setEnabled(true);
			btnPre.setEnabled(true);
			btnNext.setEnabled(true);
			btnLast.setEnabled(true);
		}
		if (totalPage == 1) {
			btnFirst.setEnabled(false);
			btnPre.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
		}
	}
	/**
	 * 使表格内容居中
	 * @param table
	 */
	public void center(JTable table) {
		//列头居中
		DefaultTableCellRenderer hr = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);//列名居中
		//内容居中
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
	}
	public boolean showDelAll() {
		return true;
	}
	public boolean showLog() {
		return false;
	}
	public JTable getTable() {
		return table;
	}
	public JButton getBtnFirst() {
		return btnFirst;
	}
	public JButton getBtnPre() {
		return btnPre;
	}
	public JButton getBtnNext() {
		return btnNext;
	}
	public JButton getBtnLast() {
		return btnLast;
	}
	public JLabel getLblPageMsg() {
		return lblPageMsg;
	}
	public String[] getColNames() {
		return colNames;
	}
	public Object[][] getDatas() {
		return datas;
	}
//	public int[] getWidth() {
//		return width;
//	}
	public int getPageNumber() {
		return pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}
	public void setDatas(Object[][] datas) {
		this.datas = datas;
	}
//	public void setWidth(int[] width) {
//		this.width = width;
//	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int[] getColWidth() {
		return colWidth;
	}
	public void setColWidth(int[] colWidth) {
		this.colWidth = colWidth;
	}
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public void addRender(Integer colomn,TableCellRenderer cellRenderer) {
		render.put(colomn, cellRenderer);
	}
	public void addEditor(Integer colomn,TableCellEditor editor) {
		editors.put(colomn, editor);
	}
}
