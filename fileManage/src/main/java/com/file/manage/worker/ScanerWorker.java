package com.file.manage.worker;

import java.io.File;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;

import com.file.manage.Start;
import com.file.manage.dao.FilesDao;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

public class ScanerWorker extends SwingWorker<Void, Void>{
	private Start ui;
	private File file;
	public ScanerWorker(Start ui,File file) {
		this.ui = ui;
		this.file = file;
	}
	@Override
	protected Void doInBackground() throws Exception {
		ui.getBtnAdd().setEnabled(false);
		ui.getBtnQuery().setEnabled(false);
		ui.getTable().status(true);
		File[] files = file.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				String id = file.getAbsolutePath();
				String name = StringUtils.substringAfterLast(id, File.separator);
				String ext = StringUtils.substringAfterLast(name, ".");
				if (StrKit.isBlank(ext)) {
					ext = "none";
				}
				Record f = FilesDao.me.findById(id);
				if (f == null) {
					f = new Record();
					f.set("id", id);
					f.set("name", name);
					f.set("ext", ext);
					f.set("addtime", System.currentTimeMillis());
					FilesDao.me.save(f);
				}
			} else {
				subFiles(file);
			}
		}
		publish();
		return null;
	}
	protected void subFiles(File dir){
		// TODO Auto-generated method stub
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return;
		}
		for (File file : files) {
			if (file.isFile()) {
				String id = file.getAbsolutePath();
				String name = StringUtils.substringAfterLast(id, File.separator);
				String ext = StringUtils.substringAfterLast(name, ".");
				if (StrKit.isBlank(ext)) {
					ext = "none";
				}
				Record f = FilesDao.me.findById(id);
				if (f == null) {
					f = new Record();
					f.set("id", id);
					f.set("name", name);
					f.set("ext", ext);
					f.set("addtime", System.currentTimeMillis());
					FilesDao.me.save(f);
				}
			} else {
				subFiles(file);
			}
		}
		publish();
	}
	@Override
	protected void process(List<Void> chunks) {
		ui.getTable().reload();
	}
	@Override
	protected void done() {
		ui.getBtnAdd().setEnabled(true);
		ui.getBtnQuery().setEnabled(true);
		ui.getTable().status(false);
	}
}
