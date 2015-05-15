package com.gxf.composite;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.Util;

public class AddPlaySolution extends ApplicationWindow {
	//各种控件
	private Text txt_displaySolutionName;
	private Text txt_comment;
	private Button btn_add;
	private Button btn_close;
	
	//各种工具类
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private Combo combo_display;
	private Shell curShell;
	
	
	//数据库访问类
	private DisplayDao displayDao = new DisplayDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	//单例模式  实例
	private static AddPlaySolution addPlaySolution = new AddPlaySolution();
	
	/**
	 * 使用单例模式 
	 * Create the application window.
	 */
	private AddPlaySolution() {
		super(null);
		setShellStyle(SWT.MIN);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setBounds(10, 10, 432, 187);
		
		Label lb_displayName = new Label(composite, SWT.NONE);
		lb_displayName.setBounds(24, 32, 54, 12);
		lb_displayName.setText("显示屏");
		
		combo_display = new Combo(composite, SWT.NONE);
		combo_display.setBounds(84, 29, 109, 20);
		
		Label lb_playSolutionName = new Label(composite, SWT.NONE);
		lb_playSolutionName.setBounds(219, 32, 71, 12);
		lb_playSolutionName.setText("播放方案名");
		
		txt_displaySolutionName = new Text(composite, SWT.BORDER);
		txt_displaySolutionName.setBounds(296, 29, 115, 18);
		
		Label lb_comment = new Label(composite, SWT.NONE);
		lb_comment.setBounds(24, 75, 54, 12);
		lb_comment.setText("说明");
		
		txt_comment = new Text(composite, SWT.BORDER);
		txt_comment.setBounds(84, 69, 109, 18);
		
		btn_add = new Button(composite, SWT.NONE);
		btn_add.setBounds(121, 120, 72, 22);
		btn_add.setText("添加");
		
		btn_close = new Button(composite, SWT.NONE);
		btn_close.setBounds(219, 120, 72, 22);
		btn_close.setText("关闭");
		
		//加载数据到控件上
		init();
		return container;
	}
	
	/**
	 * 向加载数据到控件上
	 */
	private void init(){
		//加载数据到显示屏框中
		List<com.gxf.beans.Display> displays = displayDao.queryAllDisplay();
		String displayNames[] = new String[displays.size()];
		for(int i = 0; i < displays.size(); i++){
			displayNames[i] = displays.get(i).getName();
		}
		combo_display.setItems(displayNames);
		combo_display.select(0);
		
		//为按钮添加监听事件
		btn_add.addSelectionListener(new ButtonSelectionListener());
		btn_close.addSelectionListener(new ButtonSelectionListener());
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		return null;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		return null;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			AddPlaySolution window = new AddPlaySolution();
//			window.setBlockOnOpen(true);
//			window.open();
//			Display.getCurrent().dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		curShell = newShell;
		newShell.setText("添加播放方案");
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "addDisplayIcon.png";
		newShell.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(460, 252);
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
			if(e.getSource() == btn_add){						//添加播放方案
				//验证播放方案信息是否完整
				if(!validPlaySolution())
					return;
				addPlaySolution();
			}
			else if(e.getSource() == btn_close){				//关闭当前窗口
				curShell.dispose();
			}
			
		}
		
		/**
		 * 添加播放方案
		 */
		private void addPlaySolution(){
			//如果没有显示屏信息
			if(combo_display.getItemCount() == 0)
			{
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("提示");
				messageBox.setMessage("请先添加显示屏信息!");
				messageBox.open();
				
				return;
			}
			//播放方案不能为空
			if(txt_displaySolutionName.getText() == null){
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("提示");
				messageBox.setMessage("请填写播放方案名!");
				messageBox.open();
				
				return;
			}
			//构造要存放的播放方案
			String solutionName = txt_displaySolutionName.getText();
			PlaySolution playSolution = new PlaySolution();
			playSolution.setName(solutionName);
			playSolution.setCreateTime(new Date());
			playSolution.setUpdateTime(new Date());
			playSolution.setUpdateCount(0);
			playSolution.setComment(txt_comment.getText());
			//获取关联的显示屏
			String displayName = combo_display.getItem(combo_display.getSelectionIndex());
			com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
			playSolution.setDisplay(display);
			
			//存放到数据库中
			playSolutionDao.addPlaySolution(playSolution);
			
			//创建播放方案文件夹
			String playSolutionPath = curProjectPath + File.separator + displayName + File.separator 
					+  solutionName;
			File playSolutionFile = new File(playSolutionPath);
			playSolutionFile.mkdir();
			
			//向原来的窗口表格添加记录
			PlaySolutionComposite.addTableItem(displayName, solutionName);
			//提示创建成功
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("提示");
			messageBox.setMessage("播放方案创建成功!");
			messageBox.open();
		}
		
	}

	/**
	 * 显示窗口
	 */
	public void showWindow(){
		try {
			AddPlaySolution window = AddPlaySolution.getAddPlaySolution();
			window.setBlockOnOpen(true);
			window.open();
//			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取单例模式  实例
	 * @return
	 */
	public static AddPlaySolution getAddPlaySolution(){
		return addPlaySolution;
	}
	
	/**
	 * 验证播放方案信息是否完整
	 * 如方案名不能为空等
	 * @return
	 */
	private boolean validPlaySolution(){
		//没有显示屏信息
		if(combo_display.getItemCount() == 0){
			util.getMessageBox(curShell, "提示", "请先完成显示屏信息!").open();
			return false;
		}
		
		//没有填写播放方案名
		if(txt_displaySolutionName.getText() == null || txt_displaySolutionName.getText().equals("")){
			util.getMessageBox(curShell, "提示", "播放方案名不能为空!").open();
			return false;
		}
		
		return true;
	}
}
