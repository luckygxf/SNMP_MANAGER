package com.gxf.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.gxf.beans.PlayControl;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.util.Util;

import org.eclipse.swt.widgets.Canvas;

public class PreViewWindow extends ApplicationWindow {
	//控件类
	private Canvas canvas;
	
	//工具类
	private Shell curShell;
	private Util util = new Util();
	
	// 文件存储
	private File currentPic = null;
	private File[] pics;
	private int picPoint = 0;
	
	//当前需要显示的image
	private Image curImage;
	
	//播放方案名称
	public static String playSolutionName ;
	public static String displayName;
	
	private com.gxf.beans.Display display;
	private PlaySolution playSolution;
	private Map<String, PlayControl> mapOfPlayControl;  			//图片名称和控制信息map
		
	//数据库访问类
	private DisplayDao displayDao = new DisplayDaoImpl();
	
	//停止播放
	private boolean isStop = false;
	
	
	/**
	 * Create the application window.
	 */
	public PreViewWindow() {
		super(null);
		setShellStyle(SWT.NO_TRIM);
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
		
		canvas = new Canvas(container, SWT.NONE);
		canvas.setBounds(0, 0, 256, 124);
		//双击鼠标监听事件
		canvas.addMouseListener(new MouseListenerImp());
		
		//初始化操作
		init();
		
		return container;
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
		return null;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			PreViewWindow window = new PreViewWindow();
//			window.setBlockOnOpen(true);
//			window.open();
//			Display.getCurrent().dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 代替main函数，对外提供调用接口
	 */
	public void showWindow(){
		try {
			PreViewWindow window = new PreViewWindow();
			window.setBlockOnOpen(true);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化操作
	 */
	public void init(){		
		//获取显示屏实体、播放方案实体信息
		display = displayDao.queryDisplayByName(displayName);
		Set<PlaySolution> setOfPlaySolution = display.getSolutions();
				
		for(Iterator<PlaySolution> it = setOfPlaySolution.iterator(); it.hasNext();){
			PlaySolution temp = it.next();
			if(temp.getName().equals(playSolutionName)){
				playSolution = temp;
				break;
			}
		}//for
		
		//生成xml
		util.createConfigXml(display, playSolution);
		
		//导入播放方案
		importPlaySolution(displayName, playSolutionName);
			
		//画布添加重画监听器
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				if(curImage != null)
					gc.drawImage(curImage, 0, 0);
			}
		});
		
		//启动自动播放线程
		PlayPicThread picPlayThread = new PlayPicThread();
		picPlayThread.start();
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("预览");
		
		//获取当前shell,注册监听器
		curShell = newShell;
		curShell.addKeyListener(new KeyListenerImpl());
		//设置图片预览logo
		//设置程序logo
		String projectPath = util.getCurrentProjectPath();
		String logoPath = projectPath + "\\icons\\" + "logo.png";
		ImageData logoImageData = new ImageData(logoPath);
		Image logoImage = new Image(newShell.getDisplay(), logoImageData);
		newShell.setImage(logoImage);
		
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(256, 128);
	}
	
	/**
	 * 按键监听器，这里主要是为了监听esc退出全屏显示
	 * @author Administrator
	 *
	 */
	class KeyListenerImpl implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.keyCode == SWT.ESC)
			{
				setIsStop(true);
				//关闭窗口
				curShell.dispose();				
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * 鼠标监听器
	 * @author Administrator
	 *
	 */
	class MouseListenerImp implements MouseListener{
		
		//双击鼠标关闭窗口
		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
			setIsStop(true);
			//双击关闭窗口
			curShell.dispose();			
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseUp(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * 导入播放方案
	 */
	private void importPlaySolution(String displayName, String solutionName){		
		String projectPath = util.getCurrentProjectPath();

		String filePath = projectPath + File.separator  + displayName
				+ File.separator + solutionName;

		//文件夹中所有图片,生成File对象
		//这里图片文件不能直接从硬盘中读取
		//从xml中解析顺序生成文件对象，实现对图片播放顺序的控制
		//获取播放方案配置
		mapOfPlayControl = util.parseXml(displayName, solutionName);
		
		List<PlayControl> listOfPlayControl = new ArrayList<PlayControl>();
		for(Iterator<String> it = mapOfPlayControl.keySet().iterator(); it.hasNext();){
			String temp_picName = it.next();
			listOfPlayControl.add(mapOfPlayControl.get(temp_picName));
		}//for
		
		//排序
		Collections.sort(listOfPlayControl);
		pics = new File[listOfPlayControl.size()];
		for(int i = 0; i < pics.length; i++){
			pics[i] = new File(filePath + File.separator + listOfPlayControl.get(i).getPicName());
		}//for

		
		currentPic = pics[0];
		picPoint = 0;
		//显示图片到画布上
//		drawImage(); 
		
	}
	//同步访问isStop
	private synchronized void setIsStop(boolean isStop){
		this.isStop = isStop;
	}
	private synchronized boolean getIsStop(){
		return isStop;
	}
	
	/**
	 * 获取暂停播放时间
	 * @param picIndex
	 * @return
	 * -1表示不能播放
	 * >0表示播放几秒
	 */
	private int getTimeInterval(int picIndex){
		String picName = pics[picIndex].getName();
		//默认可以播放
		PlayControl playControl = mapOfPlayControl.get(picName);
		
		//1.比较日期范围是否valid
		//2.比较时间范围是否valid
		//3.星期是否valid
		
		//比较日期+时间
		java.util.Date curDate = new java.util.Date();
		String beginDateStr = playControl.getDateTimeStart().toString() + " " + playControl.getTimeStart();
		String afterDateStr = playControl.getDateTimeEnd().toString() + " " + playControl.getTimeEnd();
		
		java.sql.Timestamp beginDate = java.sql.Timestamp.valueOf(beginDateStr);
		java.sql.Timestamp afterDate = java.sql.Timestamp.valueOf(afterDateStr);
		
		if(!(curDate.after(beginDate) && curDate.before(afterDate))){
			return -1;
		}
		
		//比较星期
		String weekdaysStr = playControl.getWeekdays();
		//获取当前星期
		Calendar c = Calendar.getInstance();
        int curWeekday = (c.get(Calendar.DAY_OF_WEEK) - 1);
        if(curWeekday < 0)
        	curWeekday = 6;
        if(weekdaysStr.charAt(curWeekday) == '0')
        	return -1;
        
//        System.out.println("curWeekdayStr = " + weekdaysStr + "curWeekday = " + curWeekday);
        
        //日期、时间和星期都valid获取播放间隔时间返回
        int timeInterval = playControl.getTimeInterval();
		
        return timeInterval;
	}
	
	/**
	 * 自动播放图片线程
	 * @author Administrator
	 *
	 */
	class PlayPicThread extends Thread{
		public void run(){
			while(!getIsStop()){
				
				picPoint = picPoint % pics.length;
				currentPic = pics[picPoint];			
				try {
//					sleep((long)(config.getPlayTimeInterval() * 1000));
					//获取播放间隔
					int timeInterval = getTimeInterval(picPoint);
					if(timeInterval == -1)
					{
						picPoint++;
						continue;
					}
					Display.getDefault().syncExec(new Runnable() {
						
						@Override
						public void run() {
							drawImage();
							
						}
					});
					sleep(timeInterval * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				picPoint++;
			}//while				
		}
	}
	
	/**
	 * 将图片显示到画布上
	 */
	private void drawImage(){
		ImageData imageData = new ImageData(currentPic.getPath());
		int width = curShell.getBounds().width;
		int height = curShell.getBounds().height;
		imageData = imageData.scaledTo(width, height);
		curImage = new Image(curShell.getDisplay(), imageData);
		
		canvas.redraw();
		
	}
	
	/**
	 * 设置显示屏名称
	 * @param displayName
	 */
	public static void setDisplayName(String displayName){
		PreViewWindow.displayName = displayName;
	}
	
	/**
	 * 设置播放方案名称
	 * @param playSolutionName
	 */
	public static void setPlaySolutinName(String playSolutionName){
		PreViewWindow.playSolutionName = playSolutionName;
	}
}
