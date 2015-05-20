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
	//�ؼ���
	private Canvas canvas;
	
	//������
	private Shell curShell;
	private Util util = new Util();
	
	// �ļ��洢
	private File currentPic = null;
	private File[] pics;
	private int picPoint = 0;
	
	//��ǰ��Ҫ��ʾ��image
	private Image curImage;
	
	//���ŷ�������
	public static String playSolutionName ;
	public static String displayName;
	
	private com.gxf.beans.Display display;
	private PlaySolution playSolution;
	private Map<String, PlayControl> mapOfPlayControl;  			//ͼƬ���ƺͿ�����Ϣmap
		
	//���ݿ������
	private DisplayDao displayDao = new DisplayDaoImpl();
	
	//ֹͣ����
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
		//˫���������¼�
		canvas.addMouseListener(new MouseListenerImp());
		
		//��ʼ������
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
	 * ����main�����������ṩ���ýӿ�
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
	 * ��ʼ������
	 */
	public void init(){		
		//��ȡ��ʾ��ʵ�塢���ŷ���ʵ����Ϣ
		display = displayDao.queryDisplayByName(displayName);
		Set<PlaySolution> setOfPlaySolution = display.getSolutions();
				
		for(Iterator<PlaySolution> it = setOfPlaySolution.iterator(); it.hasNext();){
			PlaySolution temp = it.next();
			if(temp.getName().equals(playSolutionName)){
				playSolution = temp;
				break;
			}
		}//for
		
		//����xml
		util.createConfigXml(display, playSolution);
		
		//���벥�ŷ���
		importPlaySolution(displayName, playSolutionName);
			
		//��������ػ�������
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				if(curImage != null)
					gc.drawImage(curImage, 0, 0);
			}
		});
		
		//�����Զ������߳�
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
		newShell.setText("Ԥ��");
		
		//��ȡ��ǰshell,ע�������
		curShell = newShell;
		curShell.addKeyListener(new KeyListenerImpl());
		//����ͼƬԤ��logo
		//���ó���logo
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
	 * ������������������Ҫ��Ϊ�˼���esc�˳�ȫ����ʾ
	 * @author Administrator
	 *
	 */
	class KeyListenerImpl implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.keyCode == SWT.ESC)
			{
				setIsStop(true);
				//�رմ���
				curShell.dispose();				
				
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * ��������
	 * @author Administrator
	 *
	 */
	class MouseListenerImp implements MouseListener{
		
		//˫�����رմ���
		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
			setIsStop(true);
			//˫���رմ���
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
	 * ���벥�ŷ���
	 */
	private void importPlaySolution(String displayName, String solutionName){		
		String projectPath = util.getCurrentProjectPath();

		String filePath = projectPath + File.separator  + displayName
				+ File.separator + solutionName;

		//�ļ���������ͼƬ,����File����
		//����ͼƬ�ļ�����ֱ�Ӵ�Ӳ���ж�ȡ
		//��xml�н���˳�������ļ�����ʵ�ֶ�ͼƬ����˳��Ŀ���
		//��ȡ���ŷ�������
		mapOfPlayControl = util.parseXml(displayName, solutionName);
		
		List<PlayControl> listOfPlayControl = new ArrayList<PlayControl>();
		for(Iterator<String> it = mapOfPlayControl.keySet().iterator(); it.hasNext();){
			String temp_picName = it.next();
			listOfPlayControl.add(mapOfPlayControl.get(temp_picName));
		}//for
		
		//����
		Collections.sort(listOfPlayControl);
		pics = new File[listOfPlayControl.size()];
		for(int i = 0; i < pics.length; i++){
			pics[i] = new File(filePath + File.separator + listOfPlayControl.get(i).getPicName());
		}//for

		
		currentPic = pics[0];
		picPoint = 0;
		//��ʾͼƬ��������
//		drawImage(); 
		
	}
	//ͬ������isStop
	private synchronized void setIsStop(boolean isStop){
		this.isStop = isStop;
	}
	private synchronized boolean getIsStop(){
		return isStop;
	}
	
	/**
	 * ��ȡ��ͣ����ʱ��
	 * @param picIndex
	 * @return
	 * -1��ʾ���ܲ���
	 * >0��ʾ���ż���
	 */
	private int getTimeInterval(int picIndex){
		String picName = pics[picIndex].getName();
		//Ĭ�Ͽ��Բ���
		PlayControl playControl = mapOfPlayControl.get(picName);
		
		//1.�Ƚ����ڷ�Χ�Ƿ�valid
		//2.�Ƚ�ʱ�䷶Χ�Ƿ�valid
		//3.�����Ƿ�valid
		
		//�Ƚ�����+ʱ��
		java.util.Date curDate = new java.util.Date();
		String beginDateStr = playControl.getDateTimeStart().toString() + " " + playControl.getTimeStart();
		String afterDateStr = playControl.getDateTimeEnd().toString() + " " + playControl.getTimeEnd();
		
		java.sql.Timestamp beginDate = java.sql.Timestamp.valueOf(beginDateStr);
		java.sql.Timestamp afterDate = java.sql.Timestamp.valueOf(afterDateStr);
		
		if(!(curDate.after(beginDate) && curDate.before(afterDate))){
			return -1;
		}
		
		//�Ƚ�����
		String weekdaysStr = playControl.getWeekdays();
		//��ȡ��ǰ����
		Calendar c = Calendar.getInstance();
        int curWeekday = (c.get(Calendar.DAY_OF_WEEK) - 1);
        if(curWeekday < 0)
        	curWeekday = 6;
        if(weekdaysStr.charAt(curWeekday) == '0')
        	return -1;
        
//        System.out.println("curWeekdayStr = " + weekdaysStr + "curWeekday = " + curWeekday);
        
        //���ڡ�ʱ������ڶ�valid��ȡ���ż��ʱ�䷵��
        int timeInterval = playControl.getTimeInterval();
		
        return timeInterval;
	}
	
	/**
	 * �Զ�����ͼƬ�߳�
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
					//��ȡ���ż��
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
	 * ��ͼƬ��ʾ��������
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
	 * ������ʾ������
	 * @param displayName
	 */
	public static void setDisplayName(String displayName){
		PreViewWindow.displayName = displayName;
	}
	
	/**
	 * ���ò��ŷ�������
	 * @param playSolutionName
	 */
	public static void setPlaySolutinName(String playSolutionName){
		PreViewWindow.playSolutionName = playSolutionName;
	}
}
