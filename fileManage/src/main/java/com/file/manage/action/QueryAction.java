package com.file.manage.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.file.manage.Start;
/**
 * 刷新表格表格的一行
 * @author tiany
 *
 */
public class QueryAction extends AbstractAction{
	/**
	 */
	private static final long serialVersionUID = 1747039269399768751L;
	private Start ui;
	public QueryAction(Start ui) {
		this.ui = ui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		ui.getTable().reload();
	}

}
