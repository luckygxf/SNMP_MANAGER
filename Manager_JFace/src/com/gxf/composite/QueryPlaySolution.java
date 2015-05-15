package com.gxf.composite;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;

import com.gxf.beans.Picture;
import com.gxf.beans.PlayControl;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PictureDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PictureDaoImpl;
import com.gxf.util.PicFilenameFilter;
import com.gxf.util.Util;

/**
 * 查询播放方案面板
 * @author Administrator
 *
 */
public class QueryPlaySolution extends Composite {
	//面板上的控件
	private Label lb_playSolutinIcon;
	private Text txt_playTimeInterval;
	private Button cbtn_weeks[];
	private Combo combo_display;
	private Combo combo_playSolution;
	private ScrolledComposite sc_pics;
	private Combo combo_playType;
	private DateTime dateTime_start;
	private DateTime dateTime_end;
	private DateTime time_start;
	private DateTime time_end;
	
	//数据库访问类
	private DisplayDao displayDao = new DisplayDaoImpl();
	private PictureDao pictureDao = new PictureDaoImpl();
	
	//工具类
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private String displayName;
	private String playSolutionName;
	
	//label选中背景色
	private final Color LABEL_SELECTED_COLOR =  Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN);
	
	private Shell curShell;

	public QueryPlaySolution(Composite parent, int style) {
		super(parent, style);
		
		Label lb_displayIcon = new Label(this, SWT.NONE);
		lb_displayIcon.setBounds(22, 9, 54, 12);
		lb_displayIcon.setText("显示屏");
		
		combo_display = new Combo(this, SWT.NONE);
		combo_display.setBounds(109, 6, 143, 20);
		
		lb_playSolutinIcon = new Label(this, SWT.NONE);
		lb_playSolutinIcon.setBounds(275, 9, 54, 12);
		lb_playSolutinIcon.setText("播放方案");
		
		combo_playSolution = new Combo(this, SWT.NONE);
		combo_playSolution.setBounds(374, 6, 143, 20);
		
		sc_pics = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_pics.setBounds(0, 27, 590, 306);
		sc_pics.setExpandHorizontal(true);
		sc_pics.setExpandVertical(true);
		
		Group group_playSettings = new Group(this, SWT.NONE);
		group_playSettings.setText("播放参数");
		group_playSettings.setBounds(0, 339, 590, 142);
		
		Label lb_playTypeIcon = new Label(group_playSettings, SWT.NONE);
		lb_playTypeIcon.setBounds(36, 20, 54, 12);
		lb_playTypeIcon.setText("播放类型");
		
		combo_playType = new Combo(group_playSettings, SWT.NONE);
		combo_playType.setBounds(96, 12, 86, 20);
		
		//加载数据到播放方式组合框中
		String playStyles[] = new String[]{"普通播放", "定时播放"};
		combo_playType.setItems(playStyles);
		combo_playType.select(0);
		
		Label lb_playTimeInterval = new Label(group_playSettings, SWT.NONE);
		lb_playTimeInterval.setBounds(288, 20, 86, 12);
		lb_playTimeInterval.setText("播放时间间隔");
		
		txt_playTimeInterval = new Text(group_playSettings, SWT.BORDER);
		txt_playTimeInterval.setBounds(380, 14, 41, 18);
		
		Label lb_secondIcon = new Label(group_playSettings, SWT.NONE);
		lb_secondIcon.setBounds(436, 20, 32, 12);
		lb_secondIcon.setText("秒");
		
		Label lb_playDateStart = new Label(group_playSettings, SWT.NONE);
		lb_playDateStart.setBounds(36, 47, 54, 12);
		lb_playDateStart.setText("播放日期");
		
		dateTime_start = new DateTime(group_playSettings, SWT.BORDER);
		dateTime_start.setBounds(96, 39, 86, 20);
		
		Label lb_toIcon = new Label(group_playSettings, SWT.NONE);
		lb_toIcon.setBounds(288, 47, 21, 12);
		lb_toIcon.setText("到");
		
		dateTime_end = new DateTime(group_playSettings, SWT.BORDER);
		dateTime_end.setBounds(380, 39, 86, 20);
		
		Label lb_playTimeStart = new Label(group_playSettings, SWT.NONE);
		lb_playTimeStart.setBounds(36, 74, 54, 12);
		lb_playTimeStart.setText("播放时间");
		
		time_start = new DateTime(group_playSettings, SWT.BORDER);
		time_start.setBounds(96, 66, 86, 20);
		
		Label lb_toIcon1 = new Label(group_playSettings, SWT.NONE);
		lb_toIcon1.setBounds(288, 74, 54, 12);
		lb_toIcon1.setText("到");
		
		time_end = new DateTime(group_playSettings, SWT.BORDER);
		time_end.setBounds(380, 66, 86, 20);
		
		Label lb_weekdays = new Label(group_playSettings, SWT.NONE);
		lb_weekdays.setBounds(36, 99, 46, 12);
		lb_weekdays.setText("星期");
		
		//星期复选框
		cbtn_weeks = new Button[7];
		
		cbtn_weeks[0] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[0].setBounds(96, 93, 54, 22);
		cbtn_weeks[0].setText("星期一");
		
		cbtn_weeks[1] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[1].setBounds(154, 94, 54, 22);
		cbtn_weeks[1].setText("星期二");
		
		cbtn_weeks[2] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[2].setText("星期三");
		cbtn_weeks[2].setBounds(216, 94, 54, 22);
		
		cbtn_weeks[3] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[3].setText("星期四");
		cbtn_weeks[3].setBounds(276, 93, 54, 22);
		
		cbtn_weeks[4] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[4].setText("星期五");
		cbtn_weeks[4].setBounds(96, 121, 54, 22);
		
		cbtn_weeks[5] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[5].setText("星期六");
		cbtn_weeks[5].setBounds(154, 121, 54, 22);
		
		cbtn_weeks[6] = new Button(group_playSettings, SWT.CHECK);
		cbtn_weeks[6].setText("星期天");
		cbtn_weeks[6].setBounds(216, 121, 54, 22);
		
		//获取当前shell
		curShell = this.getShell();
		
		//初始化操作
		init();
	}
	
	/**
	 * 对控件进行初始化，从数据库中查询数据绑定到控件上、为控件添加监听器等
	 */
	private void init(){
		//查询所有的显示屏
		String displayNames[] = getDisplayNames();
		if(displayNames.length != 0){
			combo_display.setItems(displayNames);
			combo_display.select(0);
			displayName = combo_display.getItem(0);
		}
		//查询所有的播放方案
		String solutionNames[] = getSolutions();
		if(solutionNames.length != 0){
			combo_playSolution.setItems(solutionNames);
			combo_playSolution.select(0);
			playSolutionName = combo_playSolution.getItem(0);
		}
		//为两个组合框添加监听事件
		combo_display.addSelectionListener(new ComboSelectionListener());
		combo_playSolution.addSelectionListener(new ComboSelectionListener());
		
		//加载所有播放方案图片
		addPicturesToComposite();	
		
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
	 * 获取所有的播放方案
	 * @return
	 */
	private String[] getSolutions(){
		int displayCount = combo_display.getItemCount();
		if(displayCount == 0)
			return new String[0];
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
	 * 显示播放方案下所有图片
	 */
	private void addPicturesToComposite(){
		//1.判断是否有播放方案选中
		//2.加载所有播放方案下的图片，这里直接从硬盘读取，不从数据库读取
		//3.加载鼠标点击事件
		
		Control controls[] = sc_pics.getChildren();
		
		if(controls != null && controls.length != 0)
			controls[0].dispose();
		
		//判断是否有播放方案选中
		if(combo_display.getItemCount() == 0 || combo_playSolution.getItemCount() == 0)
			return;
		//加载播放方案所有图片
		Composite composite_pics = new Composite(sc_pics, SWT.NONE);
		//创建上下文菜单
		Menu contextMenu = new Menu(curShell, SWT.TOP);
		MenuItem refreshItem = new MenuItem(contextMenu, SWT.NONE);
		refreshItem.setText("刷新");
		composite_pics.setMenu(contextMenu);
		//添加监听器
		refreshItem.addSelectionListener(new MenuItemSelectionImpl());
		
		sc_pics.setContent(composite_pics);
		sc_pics.setExpandHorizontal(true);
		sc_pics.setExpandVertical(true);
		
		sc_pics.setMinSize(sc_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		String displayName = combo_display.getItem(combo_display.getSelectionIndex());
		String playSolutionName = combo_playSolution.getItem(combo_playSolution.getSelectionIndex());
		
		//加载所有图片文件 
		File pics[] = util.getPictureOrderByPlayOrder(displayName, playSolutionName);
		
		//设置图片的宽度和高度
		int compositeWidth = sc_pics.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		
		final Label labels_pic[]= new Label[pics.length];
		
		//显示每一张图片
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.CENTER);
			
			//在标签上显示图片
			ImageData imageData = new ImageData(pics[i].getPath());
			labels_pic[i].setSize(picWidth, picHeight);
			imageData = imageData.scaledTo(picWidth - 15, picHeight - 15);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			labels_pic[i].setBounds((i % 4) * picWidth, (i / 4) * picHeight, picWidth, picHeight);
			
			//图片路径放到label.data中
			String picRelativePath = displayName + File.separator + playSolutionName + File.separator + pics[i].getName();
			labels_pic[i].setData(picRelativePath);
			
			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					Label labelClicked = (Label) e.widget;
					//取消所有图片的背景色
					for(Label labelInComposite : labels_pic){
						labelInComposite.setBackground(null);
					}
					//设置选中的图片的背景色
					labelClicked.setBackground(LABEL_SELECTED_COLOR);
					
					//获取图片对象和播放控制信息
					String picPath = (String) labelClicked.getData();
					Picture picture = pictureDao.queryByPicPath(picPath);
					PlayControl playControl = picture.getPlayControl();
					showDisplayControl(playControl);
				}
			});
		}//for
	}
	
	/**
	 * 显示每张图片的控制播放信息
	 * @param playControl
	 */
	private void showDisplayControl(PlayControl playControl){
		//刷新播放方式和时间间隔
		combo_playType.select(playControl.getPlayType() - 1);
		txt_playTimeInterval.setText(String.valueOf(playControl.getTimeInterval()));
		//刷新播放开始日期
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(playControl.getDateTimeStart());
		dateTime_start.setYear(calendar.get(Calendar.YEAR));
		dateTime_start.setMonth(calendar.get(Calendar.MONTH + 1));
		dateTime_start.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		
		//播放结束日期
		calendar.setTime(playControl.getDateTimeEnd());
		dateTime_end.setYear(calendar.get(Calendar.YEAR));
		dateTime_end.setMonth(calendar.get(Calendar.MONTH + 1));
		dateTime_end.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		
		//播放开始时间
		calendar.setTime(playControl.getTimeStart());
		time_start.setHours(calendar.get(Calendar.HOUR_OF_DAY));
		time_start.setMinutes(calendar.get(Calendar.MINUTE));
		time_start.setSeconds(calendar.get(Calendar.SECOND));
		
		//播放结束时间
		calendar.setTime(playControl.getTimeEnd());
		time_end.setHours(calendar.get(Calendar.HOUR_OF_DAY));
		time_end.setMinutes(calendar.get(Calendar.MINUTE));
		time_end.setSeconds(calendar.get(Calendar.SECOND));
		
		//播放星期
		String weekdays = playControl.getWeekdays();
		for(int i = 0; i < weekdays.length(); i++){
			if(weekdays.charAt(i) == '1')
				cbtn_weeks[i].setSelection(true);
			else
				cbtn_weeks[i].setSelection(false);
		}
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
				if(combo_display.getItemCount() == 0)
				{
					combo_playSolution.removeAll();
					addPicturesToComposite();
					return;
				}
				//获取显示屏名字
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
				Set<PlaySolution> listOfSolution = display.getSolutions();
				
				if(listOfSolution.size() == 0)
				{
					combo_playSolution.removeAll();
					addPicturesToComposite();
					return;
				}
				int index = 0;
				String solutionNames[] = new String[listOfSolution.size()];
				for(Iterator<PlaySolution> it = listOfSolution.iterator(); it.hasNext();){
					solutionNames[index++] = it.next().getName();
				}
				combo_playSolution.setItems(solutionNames);
				combo_playSolution.select(0);
				
				//刷新图片显示
				addPicturesToComposite();
			}
			else if(e.getSource() == combo_playSolution){											//播放方案改变
				//刷新图片显示
				addPicturesToComposite();
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
