package com.file.manage.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FileUtils;

import com.file.manage.Start;
import com.file.manage.dao.FilesDao;
import com.file.manage.views.UI;
import com.file.manage.vo.FileType;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
/**
 * 刷新表格表格的一行
 *
 */
public class CopyToAction extends AbstractAction{
	/**
	 */
	private static final long serialVersionUID = 1747039269399768751L;
	private static final String LASTDIR = "dir";
	private Start ui;
	public CopyToAction(Start ui) {
		this.ui = ui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String ext = ((FileType)ui.getCbFileType().getSelectedItem()).getId();
		if (StrKit.isBlank(ext)) {
			UI.showErrorMessageDialog(ui, "请选择文件类型");
			return;
		}
		File dir = selectDir(ui);
		if (dir == null) {
			UI.showErrorMessageDialog(ui, "请选择保存目录");
			return;
		}
		List<Record> datas = FilesDao.me.queryByExt(ext);
		for (Record data : datas) {
			String id = data.getStr("id");
			String name = data.getStr("name");
			try {
				FileUtils.copyFile(new File(id), new File(dir, name));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		UI.showMessageDialog(ui, "导出成功");
		try {
			java.awt.Desktop.getDesktop().open(dir);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 选择文件夹
	 * @param parent 父窗体,如果设置了父窗体，那这个对话框的图标就可以使用父窗体的
	 * @return
	 */
	public File selectDir(Component parent) {
		String saveFolder = new File(".").getAbsolutePath();
		Preferences pref = Preferences.userRoot().node(Start.class.getName());
		String lastPath = pref.get(LASTDIR, "");
		JFileChooser jfc= null;
		if (StrKit.isBlank(lastPath)) {
			jfc = new JFileChooser(saveFolder); 
		} else {
			jfc = new JFileChooser(lastPath); 
		}
	    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    jfc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "文件夹";
			}
			
			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				if (f.isDirectory()) {
					return true;
				}
				return false;
			}
		});
	    int r = jfc.showDialog(parent, "选择文件夹");
	    if(r==JFileChooser.APPROVE_OPTION){ 
	    	File file=jfc.getSelectedFile();  
	    	saveFolder = jfc.getSelectedFile().getPath();
	    	pref.put(LASTDIR,saveFolder);
	    	jfc = null;
	    	return file;
	    }
	    jfc = null;
		return null;
	}
}
