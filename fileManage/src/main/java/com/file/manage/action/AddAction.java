package com.file.manage.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.file.manage.Start;
import com.file.manage.worker.ScanerWorker;
import com.jfinal.kit.StrKit;
/**
 * 拷贝当前单元格
 * @author tiany
 *
 */
public class AddAction extends AbstractAction{
	/**
	 */
	private static final long serialVersionUID = 1747039269399768751L;
	private static final String LASTDIR = Start.class.getName() + "_dir";
	private Start ui;
	public AddAction(Start ui) {
		this.ui = ui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		File file = selectDir(ui);
		if (file == null) {
			return;
		}
		new ScanerWorker(ui, file).execute();
	}

	/**
	 * 选择文件夹
	 * @param parent 父窗体,如果设置了父窗体，那这个对话框的图标就可以使用父窗体的
	 * @return
	 */
	public static File selectDir(Component parent) {
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
