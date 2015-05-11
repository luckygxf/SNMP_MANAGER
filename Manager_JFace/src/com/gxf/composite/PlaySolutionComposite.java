package com.gxf.composite;

import java.io.File;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TableColumn;

import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.Util;
import org.eclipse.swt.custom.ScrolledComposite;

/**
 * 播放方案管理面板
 * @author Administrator
 *
 */
public class PlaySolutionComposite extends Composite {
	//面板上控件
	private static Table table_playSolution;	
	private ToolItem tltm_add;
	private ToolItem tltm_moveDown;
	private ToolItem tltm_moveUp;
	private ToolItem tltm_del;
	
	//工具类
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	
	//数据库访问类
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	private static DisplayDao displayDao = new DisplayDaoImpl();
	
	public PlaySolutionComposite(Composite parent, int style) {
		super(parent, style);
		
		ViewForm viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setBounds(10, 10, 592, 453);
		
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
		viewForm.setTopLeft(toolBar);
		//添加
		tltm_add = new ToolItem(toolBar, SWT.NONE);
		tltm_add.setText("添加");
		//设置图标
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "addDisplayIcon.png";		
		tltm_add.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//删除
		tltm_del = new ToolItem(toolBar, SWT.NONE);
		tltm_del.setText("删除");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "delDisplayIcon.png";		
		tltm_del.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//下移
		tltm_moveDown = new ToolItem(toolBar, SWT.NONE);
		tltm_moveDown.setText("下移");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveDown.png";		
		tltm_moveDown.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//上移
		tltm_moveUp = new ToolItem(toolBar, SWT.NONE);
		tltm_moveUp.setText("上移");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveUp.png";		
		tltm_moveUp.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//保存
		ToolItem tltm_save = new ToolItem(toolBar, SWT.NONE);
		tltm_save.setText("保存");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "saveDisplay.png";		
		tltm_save.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		Composite composite = new Composite(viewForm, SWT.NONE);
		viewForm.setContent(composite);
				
		table_playSolution = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		table_playSolution.setLocation(0, 0);
		table_playSolution.setSize(589, 431);
		table_playSolution.setHeaderVisible(true);
		table_playSolution.setLinesVisible(true);
		
		TableColumn tblclmn_nums = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_nums.setWidth(40);
		tblclmn_nums.setText("序号");
		
		TableColumn tblclmn_displaySolutionName = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_displaySolutionName.setWidth(81);
		tblclmn_displaySolutionName.setText("播放方案名");
		
		TableColumn tblclmn_displayName = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_displayName.setWidth(76);
		tblclmn_displayName.setText("显示屏名称");
		
		TableColumn tblclmn_createTime = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_createTime.setWidth(76);
		tblclmn_createTime.setText("创建时间");
		
		TableColumn tblclmn_updateCount = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_updateCount.setWidth(62);
		tblclmn_updateCount.setText("修改次数");
		
		TableColumn tblclmn_lastUpdateTime = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_lastUpdateTime.setWidth(86);
		tblclmn_lastUpdateTime.setText("最近修改");
		
		TableColumn tblclmn_comment = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_comment.setWidth(98);
		tblclmn_comment.setText("说明");
		
		//为表格添加上下文菜单
		Menu contextMenu = new Menu(Display.getDefault().getShells()[0], SWT.POP_UP);
		//设置菜单
		MenuItem queryDetailItem = new MenuItem(contextMenu, SWT.NONE);
		queryDetailItem.setText("设置");
		//刷新菜单
		MenuItem refreshItem = new MenuItem(contextMenu, SWT.NONE);
		refreshItem.setText("刷新");
		//添加监听器
		queryDetailItem.addSelectionListener(new MenuItemListenerImpl());
		refreshItem.addSelectionListener(new MenuItemListenerImpl());
		
		table_playSolution.setMenu(contextMenu);

		//初始化控件，加载数据到控件上
		init();
	}
	
	/**
	 * 初始化控件
	 */
	private void init(){
		//按钮添加监听事件
		tltm_add.addSelectionListener(new ButtonSelectionListener());
		tltm_moveDown.addSelectionListener(new ButtonSelectionListener());
		tltm_moveUp.addSelectionListener(new ButtonSelectionListener());
		tltm_del.addSelectionListener(new ButtonSelectionListener());
		
		//显示播放方案信息
		fillTable();
	}
	
	/**
	 * 按钮监听器
	 * @author Administrator
	 *
	 */
	class ButtonSelectionListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == tltm_add){							//添加按钮
				addPlaySolution();
			}
			else if(e.getSource() == tltm_moveDown){				//下移按钮
				moveDown();
			}
			else if(e.getSource() == tltm_moveUp){					//上移按钮
				moveUp();
			}
			else if(e.getSource() == tltm_del){						//删除操作
				deletePlaySolution();
			}
			
		}
		
		
		/**
		 * 打开添加播放方案面板
		 */
		private void addPlaySolution(){
			AddPlaySolution addComposite = new AddPlaySolution();
			addComposite.showWindow();
		}
		
		/**
		 * 下移
		 */
		private void moveDown(){
			int tableSelectedIndex = table_playSolution.getSelectionIndex();
			if(tableSelectedIndex > -1 && tableSelectedIndex < table_playSolution.getItemCount() - 1){
				table_playSolution.setSelection(tableSelectedIndex + 1);
			} 
		}
		
		/**
		 * 上移
		 */
		private void moveUp(){
			int tableSelectedIndex = table_playSolution.getSelectionIndex();
			if(tableSelectedIndex > 0){
				table_playSolution.setSelection(tableSelectedIndex - 1);
			}
		}
		
		/**
		 * 删除播放方案
		 */
		private void deletePlaySolution(){
			if(!isSelected()){							//没有选中播放方案,提示用户选择播放方案
				MessageBox messageBox = new MessageBox(Display.getDefault().getShells()[0]);
				messageBox.setText("提示");
				messageBox.setMessage("请选择要删除的播放方案!");
				messageBox.open();
				return;
			}
			
			TableItem tableItems[] = table_playSolution.getItems();
			//遍历表格内容
			for(int i = 0; i < tableItems.length; i++){
				if(!tableItems[i].getChecked())				//没有选中的行直接跳过
					continue;
				int itemIndex = table_playSolution.indexOf(tableItems[i]);
				if(itemIndex < -1)							//不存在这样的行
					continue;
				//处理选中的行
				String playSolutionId_str = tableItems[i].getText(0);		//获取要删除的播放方案的id
				int playSolutionId = Integer.valueOf(playSolutionId_str);
				//从数据库中删除播放方案
				playSolutionDao.deletePlaySolutinById(playSolutionId);
				//删除播放方案文件夹
				String displayName = tableItems[i].getText(2);
				String playSolutionName = tableItems[i].getText(1);
				String solutionPath = curProjectPath + File.separator + displayName + File.separator + playSolutionName;
				File solutionFile = new File(solutionPath);
				solutionFile.delete();
				//从显示的表格中删除选中的行
				table_playSolution.remove(itemIndex);
			}
			
		}
		
		/**
		 * 判断是否有播放方案选中,这里只前面的复选框
		 * @return
		 */
		private boolean isSelected(){
			boolean isSelected = false;
			TableItem tableItems[] = table_playSolution.getItems();
			
			//遍历所有表格单元
			for(TableItem tableItem : tableItems){
				if(!tableItem.getChecked())
					continue;
				int itemIndex = table_playSolution.indexOf(tableItem);
				if(itemIndex < 0 )
					continue;
				isSelected = true;
				break;
			}
			
			return isSelected;
		}
	}
	
	/**
	 * 将数据库中所有播放方案显示出来
	 */
	private void fillTable(){
		List<PlaySolution> listOfPlaySolution = playSolutionDao.queryAllSolutions();
		
		for(PlaySolution playSolution : listOfPlaySolution){
			TableItem tableItem = new TableItem(table_playSolution, SWT.NONE);
			String itemText[] = new String[7];
			itemText[0] = String.valueOf(playSolution.getId());
			itemText[1] = playSolution.getName();
			itemText[2] = playSolution.getDisplay().getName();
			itemText[3] = String.valueOf(playSolution.getCreateTime());
			itemText[4] = String.valueOf(playSolution.getUpdateCount());
			itemText[5] = String.valueOf(playSolution.getUpdateTime());
			itemText[6] = playSolution.getComment() == null ? "" : playSolution.getComment();
			
			tableItem.setText(itemText);
		}
	}
	
	/**
	 * 添加播放方案到表格中
	 */
	public static void addTableItem(String displayName, String playSolutionName){
		com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
		PlaySolution playSolution = new PlaySolution();
		
		for(PlaySolution temp : display.getSolutions()){
			if(temp.getName().equals(playSolutionName)){
				playSolution = temp;
				break;
			}
		}
		
		//添加表格内容
		TableItem tableItem = new TableItem(table_playSolution, SWT.NONE);
		String itemText[] = new String[7];
		itemText[0] = String.valueOf(playSolution.getId());
		itemText[1] = playSolution.getName();
		itemText[2] = playSolution.getDisplay().getName();
		itemText[3] = String.valueOf(playSolution.getCreateTime());
		itemText[4] = String.valueOf(playSolution.getUpdateCount());
		itemText[5] = String.valueOf(playSolution.getUpdateTime());
		itemText[6] = playSolution.getComment() == null ? "" : playSolution.getComment();
		
		tableItem.setText(itemText);
	}
	
	/**
	 * 上下文菜单监听器
	 * @author Administrator
	 *
	 */
	class MenuItemListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.getText() == "设置"){						//查看播放方案详细信息
				SetPlaySolution updatePlaySolution = new SetPlaySolution();
				TableItem tableItemSelectedIndex = table_playSolution.getItem(table_playSolution.getSelectionIndex());
				String displayName = tableItemSelectedIndex.getText(2);
				String playSolutionName = tableItemSelectedIndex.getText(1);
				
				SetPlaySolution.displayName = displayName;
				SetPlaySolution.playSolutionName = playSolutionName;
				
				updatePlaySolution.showWindow();
			}else if(item.getText() == "刷新"){
				//刷新表格内容
				refreshTable();
			}
		}
		
	}	
	
	/**
	 * 刷新表格内容
	 */
	private void refreshTable(){
		table_playSolution.removeAll();
		fillTable();
	}
}
