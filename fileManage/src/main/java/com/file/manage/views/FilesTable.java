package com.file.manage.views;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.file.manage.Start;
import com.file.manage.dao.FilesDao;
import com.file.manage.vo.FileType;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class FilesTable extends DataTable{
	/**
	 * 定义列
	 */
	private static final String[] colNames = new String[] {"文件名","文件类型","路径","归档时间"};
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 定义列宽
	 */
	private static final int[] colWidth = {300,300,300,300};
	private Start ui;
	public FilesTable(Start ui) {
		super(colNames, null, colWidth, true);
		this.ui = ui;
	}
	@Override
	public Object[][] fillTable() {
		// TODO Auto-generated method stub
		//构造数据行
		Object[][] datas = null;
		try {
			String _ext = null;
			if (ui != null) {
				_ext = ((FileType)ui.getCbFileType().getSelectedItem()).getId();
			}
			Page<Record> all = FilesDao.me.paginate(pageNumber, pageSize,_ext);
			totalPage = all.getTotalPage();
			totalRow = all.getTotalRow();
			isFirstPage = all.isFirstPage();
			isLastPage = all.isLastPage();
			
			datas = new Object[all.getList().size()][];
			if (all.getList() == null || all.getList().size() == 0) {
				return datas;
			}
			for (int i = 0;i < all.getList().size();i++) {
				Record data = all.getList().get(i);
				String id = data.getStr("id");
				String name = data.getStr("name");
				String ext = data.getStr("ext");
				long addtime = data.getLong("addtime");
				datas[i] = new Object[]{name,ext,id,df.format(new Date(addtime))};
			}
		} catch (Exception e) {
			e.printStackTrace();
			UI.showErrorMessageDialog("装载数据异常");
		}
		return datas;
	}
	/**
	 * 重新装载数据
	 */
//	public void query(int startPage) {
//		if (startPage > 0) {
//			pageNumber = startPage;
//		}
//		Object[][] datas = null;
//		try {
//			Page<Record> all = FilesDao.me.paginate(pageNumber, pageSize);
//			totalPage = all.getTotalPage();
//			totalRow = all.getTotalRow();
//			isFirstPage = all.isFirstPage();
//			isLastPage = all.isLastPage();
//			
//			datas = new Object[all.getList().size()][];
//			if (all.getList() == null || all.getList().size() == 0) {
//				return;
//			}
//			for (int i = 0;i < all.getList().size();i++) {
//				Record data = all.getList().get(i);
//				String id = data.getStr("id");
//				String name = data.getStr("name");
//				String ext = data.getStr("ext");
//				long addtime = data.getLong("addtime");
//				datas[i] = new Object[]{name,ext,id,df.format(new Date(addtime))};
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			UI.showErrorMessageDialog("装载数据异常");
//		}
//		TableModel model = new DefaultTableModel(datas, getColNames()) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		};
//		UI.refreshTable(table, model,getColWidth(),hideColumns(),render);
//		if (rowHight() > 0) {
//			table.setRowHeight(rowHight());
//		}
//		pageInfo();
//	}
	@Override
	public void delAll() {
		FilesDao.me.deleteAll();
	}
	@Override
	public void onRowDel(JTable table, int rowDel) {
		Object id = table.getValueAt(rowDel, 2);
		FilesDao.me.deleteById(id);
	}
	@Override
	public String[] getColNames() {
		// TODO Auto-generated method stub
		return colNames;
	}
	@Override
	public int[] getColWidth() {
		// TODO Auto-generated method stub
		return colWidth;
	}
}
