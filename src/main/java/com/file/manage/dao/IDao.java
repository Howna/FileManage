package com.file.manage.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public interface IDao {
	public String tableName();
	/**
	 * 根据主键查找
	 * @return
	 */
	public default Record findById( Object idValue) {
		return Db.findById(tableName(), idValue);
	}
	/**
	 * 根据主键查找
	 * @return
	 */
	public default Record findById(String primaryKey, Object... idValue) {
		return Db.findById(tableName(), primaryKey, idValue);
	}
	/**
	 * 保存
	 * @return
	 */
	public default boolean save(final Record record) {
		return Db.save(tableName(), record);
	}
	/**
	 * 保存
	 * @return
	 */
	public default boolean save(final Record record,String primaryKey) {
		return Db.save(tableName(), primaryKey, record);
	}
	/**
	 * 保存
	 * @return
	 */
	public default boolean save(String primaryKey,Record record) {
		return Db.save(tableName(),primaryKey, record);
	}
	/**
	 * 更新
	 * @return
	 */
	public default boolean update(Record record) {
		return Db.update(tableName(), record);
	}
	/**
	 * 更新
	 * @return
	 */
	public default boolean update(String primaryKey,Record record) {
		return Db.update(tableName(),primaryKey,record);
	}
	/**
	 * 查询总数
	 * @return
	 */
	public default int count() {
		return Db.queryInt("select count(*) from " + tableName());
	}
	/**
	 * 根据ID删除
	 * @return
	 */
	public default boolean deleteById(Object idValue) {
		return Db.deleteById(tableName(), idValue);
	}
	/**
	 * 删除全部数据
	 * @return
	 */
	public default int deleteAll() {
		return Db.update("delete from " +tableName());
	}
	/**
	 * 查询所有数据
	 * @return
	 */
	public default List<Record> queryAll() {
		return Db.find("select * from " + tableName());
	}
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public default Page<Record> paginate(int pageNumber, int pageSize) {
		String select = "select *";
		String sqlExceptSelect = "from " + tableName() + " order by id asc";
		return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
