package com.file.manage.action;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import com.file.manage.views.DataTable;
import com.jfinal.kit.StrKit;
/**
 * 拷贝当前单元格
 * @author tiany
 *
 */
public class CopyRowAction extends AbstractAction{
	/**
	 */
	private static final long serialVersionUID = 1747039269399768751L;
	private DataTable dataTable;
	public CopyRowAction(DataTable dataTable,String name) {
		super(name);
		this.dataTable = dataTable;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			JTable table = dataTable.getTable();
			int rowIdx = table.getSelectedRow();
			int columIdx = table.getSelectedColumn();
			Object value = table.getValueAt(rowIdx, columIdx);
			if (value instanceof String) {
				setClipboardText((String)value);
			}
	}

	/**
	 * 设置剪切板文本内容
	 * @param content 内容
	 */
	public void setClipboardText(String content){
		if (StrKit.isBlank(content)) {
			return;
		}
		String vc = content.trim();
		StringSelection ss = new StringSelection(vc);
		java.awt.datatransfer.Clipboard sysClb=null;
		sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
		sysClb.setContents(ss,null);
	}
}
