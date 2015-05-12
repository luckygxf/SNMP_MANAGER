package com.gxf.composite;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.DateTime;










import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.AllIP;
import com.gxf.util.Config;
import com.gxf.util.PicFilenameFilter;
import com.gxf.util.SendPic;
import com.gxf.util.SolutionNameFilter;
import com.gxf.util.Util;

import org.eclipse.swt.custom.ScrolledComposite;

public class SendPlaySolution extends Composite {
	
	//各种控件
	private Label lb_showPlayStyle;
	private Text txt_timeInterval;
	private Button btn_sendSoluton;
	private Button btn_weekdays[];
	private List list_hostList;
	
	//播放日期控制
	private DateTime dateTime_start;
	private DateTime dateTime_end;
	//播放时间控制
	private DateTime time_start;
	private DateTime time_end;	
	//播放方式
	private Combo combo_playStyle;
	private Combo combo_solutions;
	
	//工具类
	private Util util = new Util();
	private SendPic sendPic = new SendPic();
	
	//当前shell
	public static Shell curShell;
	
	//存放所有播放方案的文件夹
//	private final String DIC_NAME_PLAY_SOLUTIONS = "playSolutions";
	//显示选中播放方案所有图片
	private ScrolledComposite scrolledComposite_pics;
	private Label lb_chooseDisplay;
	private Combo combo_display;
//	private Composite composite_pics;
//	private Label labels_pic[];
	
	//数据库访问类
	private DisplayDao displayDao =  new DisplayDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	public SendPlaySolution(Composite parent, int style) {
		super(parent, style);
		//获取当前shell
		curShell = parent.getShell();
		
		//显示播放方式标签
//		lb_showPlayStyle = new Label(this, SWT.NONE);
//		lb_showPlayStyle.setBounds(10, 227, 54, 12);
//		lb_showPlayStyle.setText("播放方式");
//		
//		combo_playStyle = new Combo(this, SWT.NONE);
//		combo_playStyle.setBounds(109, 219, 86, 20);
//		
//		//显示播放时间间隔标签
//		Label lb_timeInterval = new Label(this, SWT.NONE);
//		lb_timeInterval.setBounds(241, 227, 82, 12);
//		lb_timeInterval.setText("播放时间间隔");
//		
//		//播放时间间隔设置文本框,默认为1s
//		txt_timeInterval = new Text(this, SWT.BORDER);
//		txt_timeInterval.setBounds(329, 224, 35, 18);
//		txt_timeInterval.setText(String.valueOf(1));
//		
//		//显示秒标签
//		Label lb_second = new Label(this, SWT.NONE);
//		lb_second.setBounds(370, 227, 54, 12);
//		lb_second.setText("秒");
//		
//		//显示播放日期标签
//		Label lb_playDate = new Label(this, SWT.NONE);
//		lb_playDate.setBounds(10, 254, 54, 12);
//		lb_playDate.setText("播放日期");
//		
//		//显示到标签
//		Label lb_dateTo = new Label(this, SWT.NONE);
//		lb_dateTo.setBounds(241, 254, 54, 12);
//		lb_dateTo.setText("到");
//		
//		//显示播放时间标签
//		Label lb_playTime = new Label(this, SWT.NONE);
//		lb_playTime.setBounds(10, 287, 54, 12);
//		lb_playTime.setText("播放时间");
//		
//		//显示到标签
//		Label lb_timeTo = new Label(this, SWT.NONE);
//		lb_timeTo.setBounds(241, 287, 54, 12);
//		lb_timeTo.setText("到");
//		
//		Label lb_week = new Label(this, SWT.NONE);
//		lb_week.setBounds(10, 315, 54, 12);
//		lb_week.setText("星期");
//		
//		//初始化星期复选框
//		btn_weekdays = new Button[7];
//		for(int i = 0; i < btn_weekdays.length; i++){
//			btn_weekdays[i] = new Button(this, SWT.CHECK);
//		}		
//		
//		btn_weekdays[0].setBounds(109, 315, 57, 16);
//		btn_weekdays[0].setText("星期一");
//		
//		btn_weekdays[1].setText("星期二");
//		btn_weekdays[1].setBounds(175, 315, 57, 16);
//
//		btn_weekdays[2].setText("星期三");
//		btn_weekdays[2].setBounds(238, 315, 57, 16);
//		
//		btn_weekdays[3].setText("星期四");
//		btn_weekdays[3].setBounds(301, 315, 57, 16);
//		
//		btn_weekdays[4].setText("星期五");
//		btn_weekdays[4].setBounds(109, 339, 57, 16);
//		
//		btn_weekdays[5].setText("星期六");
//		btn_weekdays[5].setBounds(175, 339, 57, 16);
//		
//		btn_weekdays[6].setText("星期天");
//		btn_weekdays[6].setBounds(238, 339, 57, 16);
//		
//		Label lb_hostList = new Label(this, SWT.NONE);
//		lb_hostList.setBounds(10, 362, 54, 12);
//		lb_hostList.setText("主机列表");
		
		//主机列表控件
//		list_hostList = new List(this, SWT.BORDER);
//		list_hostList.setBounds(109, 357, 147, 68);
		
		btn_sendSoluton = new Button(this, SWT.NONE);
		btn_sendSoluton.setBounds(244, 451, 72, 22);
		btn_sendSoluton.setText("发送播放方案");
		
//		dateTime_start = new DateTime(this, SWT.BORDER);
//		dateTime_start.setBounds(109, 254, 86, 20);
//		
//		dateTime_end = new DateTime(this, SWT.BORDER);
//		dateTime_end.setBounds(329, 254, 84, 20);
//		
//		time_start = new DateTime(this, SWT.BORDER | SWT.TIME);
//		time_start.setBounds(111, 287, 84, 20);
//		
//		time_end = new DateTime(this, SWT.BORDER | SWT.TIME);
//		time_end.setBounds(329, 287, 84, 20);
		
		//显示播放方案
		Label lb_chooseSolution = new Label(this, SWT.NONE);
		lb_chooseSolution.setBounds(275, 9, 54, 12);
		lb_chooseSolution.setText("播放方案");
		
		//显示播放方案
		combo_solutions = new Combo(this, SWT.NONE);
		combo_solutions.setBounds(374, 6, 143, 20);
		
		scrolledComposite_pics = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_pics.setBounds(0, 27, 590, 414);
		
		lb_chooseDisplay = new Label(this, SWT.NONE);
		lb_chooseDisplay.setBounds(22, 9, 54, 12);
		lb_chooseDisplay.setText("显示屏");
		
		combo_display = new Combo(this, SWT.NONE);
		combo_display.setBounds(109, 6, 143, 20);
		
//		scrolledComposite_pics.setLayout(new FillLayout());
//		scrolledComposite_pics.setExpandHorizontal(true);
//		scrolledComposite_pics.setExpandVertical(true);
		
		
		//对控件进行初始化
		init();
	}
	
	/**
	 * 初始化控件
	 */
	public void init(){
		//初始化播放方式控件内容
//		String playStyles[] = new String[]{"普通播放", "定时播放"};
//		combo_playStyle.setItems(playStyles);
//		combo_playStyle.select(0);
		btn_sendSoluton.addSelectionListener(new ButtonListenerImpl());
		
		//初始化显示屏
		String displayNames[] = getDisplayNames();
		if(displayNames.length != 0){
			combo_display.setItems(displayNames);			
			combo_display.select(0);
		}
		
		//初始化播放方案
		String playSolutions[] = getSolutions();
		if(playSolutions != null && playSolutions.length != 0){
			combo_solutions.setItems(playSolutions);			
			combo_solutions.select(0);
		}
		
		//初始化日期和时间
//		dateTime_start.setYear(1990);
//		dateTime_start.setMonth(7);
//		dateTime_start.setDay(6);
//		
//		time_start.setHours(0);
//		time_start.setMinutes(0);
//		time_start.setSeconds(0);				

		//加载图片到滚动面板
		addPicsToScrollComposite();
		//显示屏combo添加监听器
		combo_display.addSelectionListener(new ComboSelectionListener());
		//播放方案list添加监听器
		combo_solutions.addSelectionListener(new ComboSelectionListener());
		
		//初始化主机列表
//		if(AllIP.IPS == null || AllIP.IPS.size() == 0)
//			new AllIP().getAllIP();
//		for(int i = 0; i < AllIP.IPS.size(); i++){
//			list_hostList.add(AllIP.IPS.get(i));
//		}
		
		//创建刷新上下文菜单
		Menu contextMenu = new Menu(curShell, SWT.TOP);
		MenuItem freshItem = new MenuItem(contextMenu, SWT.None);
		freshItem.setText("刷新");
		
		scrolledComposite_pics.setMenu(contextMenu);
		freshItem.addSelectionListener(new MenuItemSelectionImpl());
		
	}
	
	/**
	 * 按键监控事件
	 * @author Administrator
	 *
	 */
	class ButtonListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == btn_sendSoluton){					//发送配送方案
				sendPlaySolution();
			}
			
		}
		
	}
	
	/**
	 * 获取用户的配置
	 * @return
	 */
	public Config getConfig(){
		Config config = new Config();	
		String playStyle = combo_playStyle.getItem(combo_playStyle.getSelectionIndex());
		if(playStyle.equals("普通播放"))
			config.setPlayStyle(0);														//0--普通播放
		else
			config.setPlayStyle(1);
		config.setPlayTimeInterval(Integer.parseInt(txt_timeInterval.getText()));		//设置播放时间间隔
		//设置开始播放日期
		config.setYear_start(this.dateTime_start.getYear());
		config.setMonth_start(this.dateTime_start.getMonth());
		config.setDay_start(this.dateTime_start.getDay());
		//设置结束播放日期
		config.setYear_end(this.dateTime_end.getYear());
		config.setMonth_end(this.dateTime_end.getMonth());
		config.setDay_end(this.dateTime_end.getDay());
		//设置开始播放时间
		config.setHour_start(this.time_start.getHours());
		config.setMin_start(this.time_start.getMinutes());
		config.setSec_start(this.time_start.getSeconds());
		//设置结束播放时间
		config.setHour_end(this.time_end.getHours());
		config.setMin_end(this.time_end.getMinutes());
		config.setSec_end(this.time_end.getSeconds());
		//获取星期一到星期天
		boolean weekdays[] = config.getWeekdays();
		for(int i = 0; i < weekdays.length; i++){
			weekdays[i] = btn_weekdays[i].getSelection();
		}
		
		return config;
	}
	
	/**
	 * 根据config生成config.xml
	 * @param config
	 */
//	public void createConfigXml(String solutionName, Config config){
////		util.createConfigXml(solutionName, config);
//	}
	
	/**
	 * 发送播放方案
	 */
	public void sendPlaySolution(){
		//发送播放方案步骤
		//0.判断远端主机是否可达
		//1.生成xml控制文件
		//2.压缩图片和xml文件
		//3.发送压缩文件
		//4.设置当前播放方案，写入数据库 		
		
		//获取对应的显示屏和播放方案实体
		com.gxf.beans.Display display = displayDao.queryDisplayByName(combo_display.getItem(combo_display.getSelectionIndex()));
		
		//目的主机IP
		String ip = display.getCommunication().getIp();
		
		//如果远端主机不可达
		if(!util.validConnection(ip, 16202)){
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("提示");
			messageBox.setMessage("远端主机没有开启，或者没有启动播放应用程序!");
			messageBox.open();
			return ;
		}

		//如果没有可以发送的播放方案
		if(combo_display.getItemCount() == 0|| combo_solutions.getItemCount() == 0)
		{
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("提示");
			messageBox.setMessage("没有可以发送的播放方案!");
			messageBox.open();
			return ;
		}
		
		Set<PlaySolution> setOfPlaySolution = display.getSolutions();
		String playSolutionName = combo_solutions.getItem(combo_solutions.getSelectionIndex());
		
		PlaySolution playSolution = null;
		for(Iterator<PlaySolution> it = setOfPlaySolution.iterator(); it.hasNext();){
			playSolution = it.next();
			if(playSolution.getName().equals(playSolutionName))
				break;
		}//for
		
		
		//生成xml控制文件
		util.createConfigXml(display, playSolution);
		//压缩图片
		util.compressPlaySoution(display.getName(), playSolution.getName());
		//发送解决方案
		String solutionPath = util.getCurrentProjectPath() + File.separator + display.getName() 
				+ File.separator + display.getName() +  "+" + playSolution.getName() + ".zip";
		System.out.println("ip = " + ip);
		
		sendPic.send(ip, solutionPath);
		//设置当前播放方案，写入到数据库中
		displayDao.updateCurPlaySolution(display.getName(), playSolution.getName());
		//提示发送成功
		MessageBox messageBox = new MessageBox(curShell);
		messageBox.setText("发送方案");
		messageBox.setMessage("发送播放方案成功!");
		messageBox.open();
	}
	
	/**
	 * 获取所有的播放方案
	 * @return
	 */
	private String[] getSolutions(){
		int displayCount = combo_display.getItemCount();
		if(displayCount == 0)
			return null;
//		String solutionNames[] = null;
		String displayName = combo_display.getItem(combo_display.getSelectionIndex());
		
		com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
		Set<PlaySolution> setOfSolution = display.getSolutions();
		String solutionNames[] = new String[setOfSolution.size()];
		int index = 0;
		for(Iterator<PlaySolution> it = setOfSolution.iterator(); it.hasNext(); ){
			solutionNames[index++] = it.next().getName();
		}//for
		
		return solutionNames;
	}
	
	
	
	/**
	 * 加载播放方案的图片到滚动面板上
	 * @param solutionName
	 */
	public void addPicsToScrollComposite(){
		//如果没有播放方案直接返回
		Control controls[] = scrolledComposite_pics.getChildren();
		
		if(controls != null && controls.length != 0)
			controls[0].dispose();
		
		if(combo_display.getItemCount() == 0 || combo_solutions.getItemCount() == 0)
			return;
		
		
		//获取选中的显示屏和播放方案名
		String displayName = combo_display.getItem(combo_display.getSelectionIndex());
		String playSolutionName = combo_solutions.getItem(combo_solutions.getSelectionIndex());
		
		//显示播放方案的图片
		Composite composite_pics = new Composite(scrolledComposite_pics, SWT.NONE);
		scrolledComposite_pics.setContent(composite_pics);
		//初始化滚动面板布局等每行显示4张图片
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite_pics.setLayout(gridLayout);
		
		String curProjectPath = util.getCurrentProjectPath();
		String solutionPath = curProjectPath + File.separator + displayName 
							+ File.separator  + playSolutionName;
		File solutionPathFile = new File(solutionPath);
		//获取所有图片文件
		File pics[] = solutionPathFile.listFiles(new PicFilenameFilter());
		int compositeWidth = scrolledComposite_pics.getBounds().width;
		//图片宽度和高度
		int picWidth = compositeWidth / 4 - 8;
		int picHeight = 100;
		String picPath[] = new String[pics.length];
		//显示图片的标签
		Label labels_pic[]= new Label[pics.length];
		for(int i = 0; i < pics.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.NONE);
			picPath[i] = pics[i].getPath();
			ImageData imageData = new ImageData(picPath[i]);
			imageData = imageData.scaledTo(picWidth, picHeight);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					scrolledComposite_pics.setFocus();
					
				}
			});
		}
		composite_pics.setSize(composite_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite_pics.layout();
		
	}

	
	/**
	 * 从数据库中读取显示屏名字
	 * @return
	 */
	private String[] getDisplayNames(){
		java.util.List<com.gxf.beans.Display> listOfDisplay = displayDao.queryAllDisplay();
		String names[] = new String[listOfDisplay.size()];
		
		for(int i = 0; i <listOfDisplay.size(); i++){
			names[i] = listOfDisplay.get(i).getName();
		}//for
		
		return names;
	}
	
	/**
	 * 列表监听器
	 * @author Administrator
	 *
	 */
	class ComboSelectionListener extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == combo_display){														//显示屏改变
				//如果没有显示屏信息，播放方案应该置为空
				if(combo_display.getItemCount() == 0)
				{
					combo_solutions.removeAll();
					addPicsToScrollComposite();
					return;
				}
				//获取显示屏名字
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
				Set<PlaySolution> listOfSolution = display.getSolutions();
				
				if(listOfSolution.size() == 0)
				{
					combo_solutions.removeAll();
					addPicsToScrollComposite();
					return;
				}
				int index = 0;
				String solutionNames[] = new String[listOfSolution.size()];
				for(Iterator<PlaySolution> it = listOfSolution.iterator(); it.hasNext();){
					solutionNames[index++] = it.next().getName();
				}
				combo_solutions.setItems(solutionNames);
				combo_solutions.select(0);
				
				//刷新图片显示
				addPicsToScrollComposite();
			}
			else if(e.getSource() == combo_solutions){												//播放方案改变
				//刷新图片显示
				addPicsToScrollComposite();
			}
		}
		
	}
	
	/**
	 * 菜单选项监听器
	 * @author Administrator
	 *
	 */
	class MenuItemSelectionImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.getText().equals("刷新")){								//刷新
				init();
			}
		}
		
	}
}
