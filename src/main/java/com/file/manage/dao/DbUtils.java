package com.file.manage.dao;

import java.io.File;
import java.util.List;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;
/**
 * 数据源初始化
 */
public class DbUtils {
	/**
	 * 初始化,默认创建数据库文件
	 * @param db db文件
	 */
	public static void initSqlite(File db) {
		initSqlite(db, true);
	}
	/**
	 * 初始化,是否强迫创建
	 * @param db db文件
	 * @param force 是否创建数据库文件,true创建数据库文件
	 */
	public static void initSqlite(File db,boolean force) {
		if (db == null || !db.exists()) {
			if (!force) {
				throw new IllegalArgumentException("数据文件不存在");
			}
		}
		DruidPlugin dp = new DruidPlugin("jdbc:sqlite:" + db.getAbsolutePath(), null, null,"org.sqlite.JDBC");
//		dp.addFilter(new StatFilter());
		dp.start();
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		arp.setDialect(new Sqlite3Dialect());  //指定 Dialect
		arp.start();
	}
	/**
	 * 判断表是否存在,true:存在
	 * @param table
	 * @return
	 */
	public static boolean tableExist(String table) {	
		String sql = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name=?";
		int count = Db.queryInt(sql, table);
		return count == 0?false:true;
	}
	/**
	 * 判断索引是否存在,true:存在
	 * @param table
	 * @return
	 */
	public static boolean indexExist(String table) {	
		String sql = "SELECT count(*) FROM sqlite_master WHERE type='index' AND name=?";
		int count = Db.queryInt(sql, table);
		return count == 0?false:true;
	}
	/**
	 * 查询表的列信息
	 * @param table
	 * @return
	 */
	public static List<Record> tableColumn(String table) {
		List<Record> columns = Db.find("PRAGMA table_info('" + table + "')");
		return columns;
	}
	/**
	 * 查询表的列信息,封装成字符串："id", "asin", "title", "url"
	 * @param table
	 * @return
	 */
	public static String tableColumnStr(String table) {
		List<Record> columns = Db.find("PRAGMA table_info('" + table + "')");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columns.size(); i++) {
			Record c = columns.get(i);
			sb.append("\"" + c.getStr("name") + "\"");
			if (i < columns.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
