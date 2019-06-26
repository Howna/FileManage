package com.file.manage.dao;

import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class FilesDao implements IDao{
	
	public static final String TABLE_SQL = "CREATE TABLE \"files\" (\n" + 
			"\"id\"  TEXT NOT NULL,\n" + 
			"\"name\"  TEXT NOT NULL,\n" + 
			"\"ext\"  TEXT NOT NULL DEFAULT -1,\n" + 
			"\"addtime\"  INTEGER NOT NULL,\n" + 
			"PRIMARY KEY (\"id\")\n" + 
			");";
	public static final FilesDao me = new FilesDao();
	private FilesDao() {
		
	}
	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return "`files`";
	}

	/**
	 * 查询
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<Record> queryByExt(String ext) {
		String sql = "select * from " + tableName() + " where ext=? order by id asc";
		return Db.find(sql,ext);
	}
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> paginate(int pageNumber, int pageSize,String ext) {
		String select = "select *";
		if (StrKit.isBlank(ext)) {
			String sqlExceptSelect = "from " + tableName() + " order by id asc";
			return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect);
			
		} else {
			String sqlExceptSelect = "from " + tableName() + " where ext=? order by id asc";
			return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect,ext);
			
		}
	}
}
