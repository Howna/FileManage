package com.file.manage.views;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.jfinal.kit.StrKit;

/**
 * UI界面方面
 *
 */
public class UI {
	/**
	 * confirm弹出提示框-选择了是
	 */
	public final static int CONFIRM_YES=0;
	
	/**
	 * confirm弹出提示框-选择了否
	 */
	public final static int CONFIRM_NO=1;
	
	/**
	 * confirm弹出提示框-选择了取消
	 */
	public final static int CONFIRM_CENCEL=2;
	/**
	 * 弹出提示框，
	 * @param message 要显示的信息，支持HTML
	 */
	public static void showMessageDialog(String message){
		JOptionPane.showMessageDialog(null, "<html>"+message);
	}
	/**
	 * 弹出提示框，
	 * @param message 要显示的信息，支持HTML
	 */
	public static void showMessageDialog(Component parentComponent,String message){
		JOptionPane.showMessageDialog(parentComponent, "<html>"+message);
	}
	/**
	 * 弹出提示框，
	 * @param message 要显示的信息，支持HTML
	 */
	public static void showMessageDialog(String message,String title){
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * 弹出提示框，
	 * @param message 要显示的信息，支持HTML
	 */
	public static void showMessageDialog(Component parentComponent,String message,String title){
		JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * 弹出错误信息提示框，
	 * @param message 要显示的信息，支持HTML
	 */
	public static void showErrorMessageDialog(String message){
		JOptionPane.showMessageDialog(null, "<html>"+message, "错误", JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * 弹出错误信息提示框，
	 * @param message 要显示的信息，支持HTML
	 */
	public static void showErrorMessageDialog(Component parentComponent,String message){
		JOptionPane.showMessageDialog(parentComponent, "<html>"+message, "错误", JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * 弹出选择、确认框 
	 * @param message 要显示的信息 ，支持HTML
	 * @return int {@link #CONFIRM_YES} {@link #CONFIRM_NO} {@link #CONFIRM_CENCEL}
	 */
	public static int showConfirmDialog(String message){
		return JOptionPane.showConfirmDialog(null,"<html>"+message);
	}
	/**
	 * 弹出选择、确认框 
	 * @param message 要显示的信息 ，支持HTML
	 * @return int {@link #CONFIRM_YES} {@link #CONFIRM_NO} {@link #CONFIRM_CENCEL}
	 */
	public static int showConfirmDialog(String message,String title){
		return JOptionPane.showConfirmDialog(null,"<html>"+message,title,JOptionPane.YES_NO_OPTION);
	}
	/**
	 * 弹出选择、确认框 
	 * @param message 要显示的信息 ，支持HTML
	 * @return int {@link #CONFIRM_YES} {@link #CONFIRM_NO} {@link #CONFIRM_CENCEL}
	 */
	public static int showConfirmDialog(Component parentComponent,String message){
		return JOptionPane.showConfirmDialog(parentComponent,"<html>"+message);
	}
	/**
	 * 弹出选择、确认框 
	 * @param message 要显示的信息 ，支持HTML
	 * @return int {@link #CONFIRM_YES} {@link #CONFIRM_NO} {@link #CONFIRM_CENCEL}
	 */
	public static int showConfirmDialog(Component parentComponent,String message,String title){
		return JOptionPane.showConfirmDialog(parentComponent,"<html>"+message,title,JOptionPane.YES_NO_OPTION);
	}
	/**
	 * 弹出选择、确认框 
	 * @param message 要显示的信息 ，支持HTML
	 * @return int {@link #CONFIRM_YES} {@link #CONFIRM_NO} {@link #CONFIRM_CENCEL}
	 */
	public static String showInputDialog(Component parentComponent, String message, String initialSelectionValue){
		return JOptionPane.showInputDialog(parentComponent, message, initialSelectionValue);
	}
    /**
     * 最大化jframe
     * @param frame
     */
    public static void max(JFrame frame) {
    	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    /**
     * jframe居中
     * @param frame
     */
    public static void center(JFrame frame) {
    	frame.setLocationRelativeTo(null);
    }
    /**
     *   	获取auth的key,appId+_auth
     * @param appId
     */
    public static String authKey(String appId) {
    	return appId + "_auth";
    }
    /**
     * 在UI线程上更新table
     * @param table
     * @param model
     */
    public static void refreshTable(JTable table,TableModel model) {
    	refreshTable(table, model, null);
	}
    /**
     * 在UI线程上更新table,同时设置表格宽度
     * @param table
     * @param model
     * @param width 每列的宽度
     * @param hideIndex 要隐藏的列
     */
    public static void refreshTable(JTable table,TableModel model,int[] width) {
    	refreshTable(table, model, width, null);
    }
    /**
     * 在UI线程上更新table,同时设置表格宽度
     * @param table
     * @param model
     * @param width 每列的宽度
     * @param hideIndex 要隐藏的列
     */
    public static void refreshTable(JTable table,TableModel model,int[] width,int[] hideIndex) {
    	refreshTable(table, model, width, hideIndex, null);
    }
    /**
     * 在UI线程上更新table,同时设置表格宽度
     * @param table
     * @param model
     * @param width 每列的宽度
     * @param height 行高度
     * @param hideIndex 要隐藏的列
     * @param render 某列的render
     */
//    public static void refreshTable(JTable table,TableModel model,int[] width,int height,int[] hideIndex,Map<Integer, TableCellRenderer> render) {
//    	
//    }
    /**
	 * 在UI线程上更新table,同时设置表格宽度
	 * @param table
	 * @param model
	 * @param width 每列的宽度
	 * @param hideIndex 要隐藏的列
	 * @param render 某列的render
	 */
	public static void refreshTable(JTable table,TableModel model,int[] width,int[] hideIndex,Map<Integer,TableCellRenderer> render) {
		refreshTable(table, model, width, hideIndex, render, null);
	}
    	/**
    	 * 在UI线程上更新table,同时设置表格宽度
    	 * @param table
    	 * @param model
    	 * @param width 每列的宽度
    	 * @param hideIndex 要隐藏的列
    	 * @param render 某列的render
    	 * @param render 某列的editors
    	 */
    	public static void refreshTable(JTable table,TableModel model,int[] width,int[] hideIndex,Map<Integer, 
    			TableCellRenderer> render,Map<Integer, TableCellEditor> editors) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			//设置数据
    			table.setModel(model);
    			if (editors != null && editors.size() > 0) {
    				for (Map.Entry<Integer,TableCellEditor>  entry : editors.entrySet()) {
    					table.getColumnModel().getColumn(entry.getKey()).setCellEditor(entry.getValue());
    				}
    			}
    			/**
    			 * 设置render
    			 */
    			if (render != null && render.size() > 0) {
					for (Map.Entry<Integer,TableCellRenderer>  entry : render.entrySet()) {
						table.getColumnModel().getColumn(entry.getKey()).setCellRenderer(entry.getValue());
					}
				}
    			//设置宽度
    			if (width != null && width.length > 0) {
//    				for (int i = 0; i < width.length; i++) {
//    					table.getColumnModel().getColumn(i).setPreferredWidth(width[i]);
//    				}
    				TableColumnModel columns = table.getColumnModel();
    				for (int i = 0; i < width.length; i++) {
    					TableColumn column = columns.getColumn(i);
    					column.setPreferredWidth(width[i]);
    				}
    				table.setColumnModel(columns);
				}
    			//隐藏列
    			if (hideIndex != null && hideIndex.length > 0) {
    				for (int hi : hideIndex) {
//    					hideTableColumn(table, hi);
    					//直接在一个事件里隐藏。不会跳动
    					TableColumn tc = table.getTableHeader().getColumnModel().getColumn(hi);
    	    			tc.setMaxWidth(0);
    	    			tc.setPreferredWidth(0);
    	    			tc.setWidth(0);
    	    			tc.setMinWidth(0);
    	    			table.getTableHeader().getColumnModel().getColumn(hi).setMaxWidth(0);
    	    			table.getTableHeader().getColumnModel().getColumn(hi).setMinWidth(0);
    				}
    			}
    		}
    	});
    }
    /**
     * 修改table
     * @param table
     * @param model
     */
    public static void modTable(JTable table,Object aValue, int row, int column) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			table.setValueAt(aValue, row, column);
    		}
    	});
    }
    /**
     * 删除table的列
     * @param table
     * @param model
     */
    public static void removeTableColumn(JTable table,int column) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
			public void run() {
    			TableColumnModel tcm = table.getColumnModel();  
				TableColumn tc = tcm.getColumn(column) ; 
				tcm.removeColumn(tc);
			}
    	});
    }
    /**
     * 隐藏table的列
     * @param table
     * @param model
     */
    public static void hideTableColumn(JTable table,int column) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			TableColumn tc = table.getTableHeader().getColumnModel().getColumn(column);
    			tc.setMaxWidth(0);
    			tc.setPreferredWidth(0);
    			tc.setWidth(0);
    			tc.setMinWidth(0);
    			table.getTableHeader().getColumnModel().getColumn(column).setMaxWidth(0);
    			table.getTableHeader().getColumnModel().getColumn(column).setMinWidth(0);
    		}
    	});
    }
    /**
     * 在UI线程上更新label
     * @param label
     * @param msg
     */
    public static void refreshLabel(JLabel label,String msg) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			label.setText(msg);
    		}
    	});
    }
    /**
     * 在UI线程上更新按钮的文本和可用性
     * @param label
     * @param msg
     */
    public static void enabled(JButton button,String name,boolean enabled) {
    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			if (!StrKit.isBlank(name)) {
    				button.setText(name);
				}
    			button.setEnabled(enabled);
    		}
    	});
    }
}
