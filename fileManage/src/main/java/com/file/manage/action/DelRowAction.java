package com.file.manage.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.file.manage.views.DataTable;
import com.file.manage.views.UI;
/**
 * 删除表格的一行
 * @author tiany
 *
 */
public class DelRowAction extends AbstractAction{
	/**
	 */
	private static final long serialVersionUID = 1747039269399768751L;
	private DataTable dataTable;
	public DelRowAction(DataTable dataTable,String name) {
		super(name);
		this.dataTable = dataTable;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (UI.showConfirmDialog("确认删除选中行?") == UI.CONFIRM_YES) {
			JTable table = dataTable.getTable();
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			int rowIdex = table.getSelectedRow();
//			Object c1 = table.getValueAt(rowIdex, 0);
//			System.err.println(c1);
			//回调--先从db删除，然后再删除界面的
			dataTable.onRowDel(table,rowIdex);
			model.removeRow(rowIdex);
			dataTable.reload();//刷新 因为页码信息也要更新
		}
	}

}
