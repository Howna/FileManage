package com.file.manage.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.file.manage.views.DataTable;
/**
 * 刷新表格表格的一行
 *
 */
public class ReloadAction extends AbstractAction{
	/**
	 */
	private static final long serialVersionUID = 1747039269399768751L;
	private DataTable dataTable;
	public ReloadAction(DataTable dataTable,String name) {
		super(name);
		this.dataTable = dataTable;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		dataTable.reload();
	}

}
