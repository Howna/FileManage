package com.file.manage.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class FileTypeDao implements IDao{
	
	public static final String TABLE_SQL = "CREATE TABLE \"filetype\" (\n" + 
			"\"id\"  TEXT NOT NULL,\n" + 
			"\"name\"  TEXT NOT NULL\n" + 
			");";
	public static final FileTypeDao me = new FileTypeDao();
	private FileTypeDao() {
		
	}
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return "`filetype`";
	}

	/**
	 * 查询所有数据
	 * @return
	 */
	public List<Record> queryAll() {
		return Db.find("select * from " + tableName() + " order by sort");
	}
}
