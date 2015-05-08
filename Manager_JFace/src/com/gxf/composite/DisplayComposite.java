package com.gxf.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.gxf.beans.Communication;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.util.Util;


public class DisplayComposite extends Composite {
	//面板上的控件
	private static Table table_display;
	private Util util = new Util();
	private String curProjectPath = util.getCurrentProjectPath();
	private Display defaultDisplay = Display.getDefault();
	
	private ToolItem tltm_add;
	private ToolItem tltm_del;
	private ToolItem tltm_moveDown;
	private ToolItem tltm_moveUp;
	private ToolItem tltm_save;
//	private ScrolledComposite scrolledComposite;
	
	//显示屏DAO
	private static DisplayDao displayDao = new DisplayDaoImpl();
	//显示屏列表，从数据库中读取出来的	
	private  List<com.gxf.beans.Display> listOfDisplay = new ArrayList<com.gxf.beans.Display>();
	private static  List<com.gxf.entities.TableItem> listOfItems = new ArrayList<com.gxf.entities.TableItem>();
	
	
	public DisplayComposite(Composite parent, int style) {
		super(parent, style);
		
		ViewForm viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setTopCenterSeparate(true);
		viewForm.setBounds(10, 10, 592, 453);
		
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT );
		viewForm.setTopLeft(toolBar);
		
		//添加
		tltm_add = new ToolItem(toolBar, SWT.NONE);
		tltm_add.setText("添加");
		//设置图标
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "addDisplayIcon.png";		
		tltm_add.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_del = new ToolItem(toolBar, SWT.NONE);
		tltm_del.setText("删除");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "delDisplayIcon.png";		
		tltm_del.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_moveDown = new ToolItem(toolBar, SWT.NONE);
		tltm_moveDown.setText("下移");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveDown.png";		
		tltm_moveDown.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_moveUp = new ToolItem(toolBar, SWT.NONE);
		tltm_moveUp.setText("上移");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveUp.png";		
		tltm_moveUp.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_save = new ToolItem(toolBar, SWT.NONE);
		tltm_save.setText("保存");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "saveDisplay.png";		
		tltm_save.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
//		scrolledComposite = new ScrolledComposite(viewForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//		scrolledComposite.setExpandHorizontal(true);
//		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(viewForm, SWT.NONE);
		viewForm.setContent(composite);
		composite.setBounds(0, 0, 589, 431);
		
		table_display = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.CENTER | SWT.CHECK);
		table_display.setBounds(0, 0, 579, 430);
		table_display.setHeaderVisible(true);
		table_display.setLinesVisible(true);
		
		TableColumn tblclmn_id = new TableColumn(table_display, SWT.NONE);
		tblclmn_id.setWidth(45);
		tblclmn_id.setText("序号");
		
		TableColumn tblclmn_name = new TableColumn(table_display, SWT.NONE);
		tblclmn_name.setWidth(87);
		tblclmn_name.setText("名称");
		
		TableColumn tblclmn_displayType = new TableColumn(table_display, SWT.NONE);
		tblclmn_displayType.setWidth(82);
		tblclmn_displayType.setText("类型");
		
		TableColumn tblclmn_curPlaySolution = new TableColumn(table_display, SWT.NONE);
		tblclmn_curPlaySolution.setWidth(100);
		tblclmn_curPlaySolution.setText("当前播放方案");
		
		TableColumn tblclmn_communicationType = new TableColumn(table_display, SWT.NONE);
		tblclmn_communicationType.setWidth(78);
		tblclmn_communicationType.setText("通信方式");
		
		TableColumn tblclmn_ip = new TableColumn(table_display, SWT.NONE);
		tblclmn_ip.setWidth(112);
		tblclmn_ip.setText("ip");
		
		TableColumn tblclmn_port = new TableColumn(table_display, SWT.NONE);
		tblclmn_port.setWidth(62);
		tblclmn_port.setText("端口号");
		
		TableColumn tblclmn_comment = new TableColumn(table_display, SWT.NONE);
		tblclmn_comment.setWidth(108);
		tblclmn_comment.setText("说明");
//		scrolledComposite.setContent(composite);
//		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));	
		
		//初始化操作
		init();
	}
	
	/**
	 * 对控件进行初始化操作
	 */
	public void init(){
		//加载数据到table中
		refreshTable();
		
		//按键注册监听器
		tltm_moveDown.addSelectionListener(new ButtonListener());
		tltm_moveUp.addSelectionListener(new ButtonListener());
		//增加表格宽度
		table_display.addListener(SWT.MeasureItem,  new Listener(){

			@Override
			public void handleEvent(Event event) {
				event.height = event.gc.getFontMetrics().getHeight() + 10;
				
			}
			
		});
		
		//添加监听器
		tltm_add.addSelectionListener(new ButtonSelectionListener());
		tltm_del.addSelectionListener(new ButtonSelectionListener());
		tltm_save.addSelectionListener(new ButtonSelectionListener());
	}
	
	/**
	 * 按键监听器
	 * @author Administrator
	 *
	 */
	class ButtonListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == tltm_moveUp){								//上移
				int selectionIndex = table_display.getSelectionIndex();
				if(selectionIndex > 0)
					table_display.setSelection(selectionIndex - 1);
			}
			else if(e.getSource() == tltm_moveDown){						//下移
				int selectionIndex = table_display.getSelectionIndex();
				if(selectionIndex > -1 && selectionIndex < table_display.getItemCount() - 1){
					table_display.setSelection(selectionIndex + 1);
				}
			}
			
		}
		
	}
	
	/**
	 * 设置表格内容可以编辑
	 */
	private static void makeTableEditable(){
		//获取表格所有行
		TableItem tableItems[] = table_display.getItems();
		
		//每次执行之前先清空
		for(int i = 0; i < listOfItems.size(); i++){
			listOfItems.get(i).dispose();
		}
		listOfItems.clear();
		
		for(int i = 0; i < tableItems.length; i++){
			int columNums = 1;
			com.gxf.entities.TableItem temp = new com.gxf.entities.TableItem();
			listOfItems.add(temp);
			//设置名称
			final Text txt_displayName = new Text(table_display, SWT.NONE);
			final TableEditor editor_displayName = new TableEditor(table_display);
			editor_displayName.grabHorizontal = true;
			txt_displayName.setText(tableItems[i].getText(columNums));
			editor_displayName.setEditor(txt_displayName, tableItems[i], columNums);
			txt_displayName.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_displayName.getItem().setText(1, txt_displayName.getText());
					
				}
			});
			temp.setTxt_displayName(txt_displayName);
			columNums++;
			//设置类型
			final Text txt_displayType = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayType = new TableEditor(table_display);
			editor_diplayType.grabHorizontal = true;
			txt_displayType.setText(tableItems[i].getText(columNums));
			editor_diplayType.setEditor(txt_displayType, tableItems[i], columNums);
			txt_displayType.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayType.getItem().setText(2, txt_displayType.getText());
					
				}
			});
			temp.setTxt_displayType(txt_displayType);
			columNums++;
			
			//这里跳过当前播放方案列
			columNums++;
			//设置通信方式
			final Combo combo_communityType = new Combo(table_display, SWT.NONE);
			combo_communityType.add("网络通信");
			combo_communityType.add("串口通信");
			final TableEditor editor_communityType = new TableEditor(table_display);
			combo_communityType.setText(tableItems[i].getText(columNums));
			editor_communityType.setEditor(combo_communityType, tableItems[i], columNums);
			editor_communityType.grabHorizontal = true;
			combo_communityType.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_communityType.getItem().setText(3, 
							combo_communityType.getItem(combo_communityType.getSelectionIndex()));
					
				}
			});
			temp.setCombo_communityType(combo_communityType);
			columNums++;
			
			//设置ip
			final Text txt_displayIp = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayIp = new TableEditor(table_display);
			editor_diplayIp.grabHorizontal = true;
			txt_displayIp.setText(tableItems[i].getText(columNums));
			editor_diplayIp.setEditor(txt_displayIp, tableItems[i], columNums);
			txt_displayIp.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayIp.getItem().setText(5, txt_displayIp.getText());
					
				}
			});
			temp.setTxt_displayIp(txt_displayIp);
			columNums++;
			
			//设置端口号
			final Text txt_displayPort = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayPort = new TableEditor(table_display);
			editor_diplayPort.grabHorizontal = true;
			txt_displayPort.setText(tableItems[i].getText(columNums));
			editor_diplayPort.setEditor(txt_displayPort, tableItems[i], columNums);
			txt_displayPort.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayPort.getItem().setText(6, txt_displayPort.getText());
					
				}
			});
			temp.setTxt_displayPort(txt_displayPort);
			columNums++;
			
			//设置说明
			final Text txt_displayComment = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayComment = new TableEditor(table_display);
			editor_diplayComment.grabHorizontal = true;
			txt_displayComment.setText(tableItems[i].getText(columNums));
			editor_diplayComment.setEditor(txt_displayComment, tableItems[i], columNums);
			txt_displayComment.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayComment.getItem().setText(7, txt_displayComment.getText());
					
				}
			});
			temp.setTxt_displayComment(txt_displayComment);
		}
	}
	/**
	 * 删除显示屏信息
	 */
	private void deleteDisplay(){
		//检查有没有选中显示屏
		if(!isSelected()){
			MessageBox messageBox = new MessageBox(Display.getDefault().getShells()[0]);
			messageBox.setText("提示");
			messageBox.setMessage("先选择要删除的显示屏!");
			messageBox.open();
			
			return;
		}
		TableItem tableItems[] = table_display.getItems();
		for(int i = 0; i < tableItems.length; i++){
			if(!tableItems[i].getChecked()){					//没有被选中
				continue;
			}
			int index = table_display.indexOf(tableItems[i]);
			if(index < 0)										//如果没有该行
				continue;
			String id_str = tableItems[i].getText(0);
			//从数据库中删除
			displayDao.deleteDisplayById(Integer.valueOf(id_str));
			System.out.println("index = " + index);
			System.out.println("size of list = " + listOfItems.size());
			//释放表格中添加的控件
			com.gxf.entities.TableItem tableItem = listOfItems.get(index);
			tableItem.dispose();
			listOfItems.remove(index);
			
			//获取显示屏名字
			String displayName = tableItems[i].getText(1);
			String displayPath = curProjectPath + File.separator + displayName;
			File displayFile = new File(displayPath);
			//删除屏幕信息所在文件夹
			util.deleteFile(displayFile);
			
			//从表中删除
			table_display.remove(index);
			
		}
		//提示删除成功
		MessageBox messageBox = new MessageBox(Display.getDefault().getShells()[0]);
		messageBox.setText("提示");
		messageBox.setMessage("显示屏信息删除成功!");
		messageBox.open();
	}
	
	/**
	 * 添加显示屏信息
	 */
	private void addDisplay(){
		AddDisplay addDisplay = new AddDisplay();
		addDisplay.show();
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
			if(e.getSource() == tltm_add){							//添加显示屏
				addDisplay();
			}
			else if(e.getSource() == tltm_del){						//删除显示屏
				deleteDisplay();
			}
			else if(e.getSource() == tltm_save){					//保存按钮
				saveDisplay();
			}
		}
		
	}
	
	/**
	 * 刷新表格内容
	 */
	public void refreshTable(){
		listOfDisplay = displayDao.queryAllDisplay();

		//向table中添加数据
		for(com.gxf.beans.Display display : listOfDisplay){
			Communication communication = display.getCommunication();
			
			TableItem item = new TableItem(table_display, SWT.NONE);
			String temp[] = new String[8];
			temp[0] = String.valueOf(display.getId());
			temp[1] = display.getName();
			temp[2] = display.getType();
			temp[3] = display.getCurPlaySolutionName();
			temp[4] = communication.getName();
			temp[5] = communication.getIp();
			temp[6] = String.valueOf(communication.getPort());
			temp[7] = display.getComment();
			
			item.setText(temp);
		}
		
		//表格可以编辑
		makeTableEditable();
	}
	
	/**
	 * 向表格中添加一条记录
	 * @param display
	 */
	public static void addTableItem(com.gxf.beans.Display display){
		Communication communication = display.getCommunication();
		
		TableItem item = new TableItem(table_display, SWT.NONE);
		String temp[] = new String[7];
		temp[0] = String.valueOf(display.getId());
		temp[1] = display.getName();
		temp[2] = display.getType();
		temp[4] = communication.getName();
		temp[5] = communication.getIp();
		temp[6] = String.valueOf(communication.getPort());
		temp[7] = display.getComment();
		
		item.setText(temp);
		
		//表格可以编辑
		makeTableEditable();
	}
	
	/**
	 * 删除之前判断是否有选中显示屏
	 * @return
	 */
	private boolean isSelected(){
		TableItem tableItems[] = table_display.getItems();
		boolean isSelected = false;
		for(int i = 0; i < tableItems.length; i++){
			if(!tableItems[i].getChecked()){					//没有被选中
				continue;
			}
			int index = table_display.indexOf(tableItems[i]);
			if(index < 0)										//如果没有该行
				continue;
			
			isSelected = true;
			break;
		}
		
		return isSelected;
	}
	
	/**
	 * 保存显示屏
	 */
	public void saveDisplay(){
		TableItem tableItems[] = table_display.getItems();
		for(int i = 0; i < tableItems.length; i++){
			//从数据库中查询出来，修改，更新到数据库 
			int displayId = Integer.valueOf(tableItems[i].getText(0));
			com.gxf.beans.Display display = displayDao.queryDisplayById(displayId);
			//设置
			display.setName(tableItems[i].getText(1));
			display.setType(tableItems[i].getText(2));
			
			Communication communication = display.getCommunication();
			communication.setName(tableItems[i].getText(4));
			communication.setIp(tableItems[i].getText(5));
			communication.setPort(Integer.valueOf(tableItems[i].getText(6)));
			
			display.setComment(tableItems[i].getText());
			
			//更新到数据库
			displayDao.updateDisplay(display);
			
		}
		//提示用户保存成功
		MessageBox messageBox = new MessageBox(Display.getDefault().getShells()[0]);
		messageBox.setText("提示");
		messageBox.setMessage("保存屏信息成功!");
		messageBox.open();
	}
	
}
