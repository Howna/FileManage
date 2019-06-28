package com.file.manage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.file.manage.action.AddAction;
import com.file.manage.action.CopyToAction;
import com.file.manage.action.QueryAction;
import com.file.manage.dao.DbUtils;
import com.file.manage.dao.FileTypeDao;
import com.file.manage.views.FilesTable;
import com.file.manage.vo.FileType;
import com.jfinal.plugin.activerecord.Record;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Start extends JFrame {

	private static final long serialVersionUID = 1379744371243009345L;
	private JPanel contentPane;
	private FilesTable table;
	private JComboBox<FileType> cbFileType;
	private JButton btnFileType;
	private JButton btnAdd;
	private JButton btnQuery;
	private JButton btnExp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
					UIManager.put("RootPane.setupButtonVisible", false);
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					Start frame = new Start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Start() {
		DbUtils.initSqlite(new File("db.dat"));
		setTitle("文件归档管理器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1147, 805);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		table = new FilesTable(this);
		contentPane.add(table, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.WEST);
		
		btnAdd = new JButton("导入");
		btnAdd.addActionListener(new AddAction(this));
		panel_1.add(btnAdd);
		
		JLabel label = new JLabel("文件类型：");
		panel_1.add(label);
		
		cbFileType = new JComboBox<>();
		panel_1.add(cbFileType);
		List<Record> types = FileTypeDao.me.queryAll();
		cbFileType.addItem(new FileType("","全部"));
		for (Record type : types) {
			cbFileType.addItem(new FileType(type.getStr("id"), type.getStr("name")));
		}
		
		btnQuery = new JButton("查询");
		btnQuery.addActionListener(new QueryAction(this));
		panel_1.add(btnQuery);
		
		btnExp = new JButton("导出");
		btnExp.addActionListener(new CopyToAction(this));
		panel_1.add(btnExp);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.EAST);
		
		btnFileType = new JButton("文件类型管理");
		btnFileType.setVisible(false);
		panel_2.add(btnFileType);
		setLocationRelativeTo(null);
	}

	public FilesTable getTable() {
		return table;
	}

	public JComboBox<FileType> getCbFileType() {
		return cbFileType;
	}
	public JButton getBtnFileType() {
		return btnFileType;
	}
	public JButton getBtnAdd() {
		return btnAdd;
	}
	public JButton getBtnQuery() {
		return btnQuery;
	}
	public JButton getBtnExp() {
		return btnExp;
	}
}
