package com.gxf.composite;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.gxf.beans.Picture;
import com.gxf.beans.PlayControl;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PictureDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PictureDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.Util;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;

/**
 * 添加播放方案窗口
 * @author Administrator
 *
 */
public class SetPlaySolution extends ApplicationWindow {
	//工具类
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private Shell curShell;
	
	//控件类
	private Combo combo_display;
	private Combo combo_playSolutionName;
	private Button btn_weekdays[];
	private Composite composite_settings;
	private Combo combo_playType;
	private DateTime dateTime_start;
	private DateTime time_start;
	private DateTime time_end;
	private DateTime dateTime_end;
	private ScrolledComposite sc_picsToChoose;
	private ScrolledComposite sc_picsChosed;
	private Text txt_playInteraval;
	private Button btn_editPic;
	private Button btn_close;
	private Button btn_setPlayControl;
	
	
	//数据库访问类
	private DisplayDao displayDao = new DisplayDaoImpl();
	private PictureDao pictureDao = new PictureDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	//显示屏和播放方案
	public static String displayName;
	public static String playSolutionName;
	
	//当前窗口对象
	private SetPlaySolution curUpdatePlaySolution;
	//label选中背景色
	private final Color LABEL_SELECTED_COLOR =  Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN);
	//上下文菜单
//	private Menu contextMenu;
	
	//单例模式 实例
	private static SetPlaySolution setPlaySolution = new SetPlaySolution();
	
	/**
	 * Create the application window.
	 */
	private SetPlaySolution() {
		super(null);
		//获取当前对象
		curUpdatePlaySolution = this;
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
		
		Composite composite_head = new Composite(container, SWT.NONE);
		composite_head.setBounds(10, 0, 574, 28);
		
		Label lb_display = new Label(composite_head, SWT.NONE);
		lb_display.setBounds(60, 6, 54, 12);
		lb_display.setText("显示屏");
		
		//显示屏
		combo_display = new Combo(composite_head, SWT.NONE);
		combo_display.setBounds(130, 3, 122, 20);
		
		Label lb_playSolution = new Label(composite_head, SWT.NONE);
		lb_playSolution.setBounds(318, 6, 70, 12);
		lb_playSolution.setText("播放方案");
		
		combo_playSolutionName = new Combo(composite_head, SWT.NONE);
		combo_playSolutionName.setBounds(394, 3, 122, 20);
		
		Group group_picsToChoose = new Group(container, SWT.NONE);
		group_picsToChoose.setText("可选图片");
		group_picsToChoose.setBounds(10, 30, 574, 159);
		
		sc_picsToChoose = new ScrolledComposite(group_picsToChoose, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsToChoose.setBounds(10, 20, 554, 129);
//		sc_picsToChoose.setExpandHorizontal(true);
//		sc_picsToChoose.setExpandVertical(true);
		
		Group group_picsChosed = new Group(container, SWT.NONE);
		group_picsChosed.setText("已添加图片");
		group_picsChosed.setBounds(10, 195, 574, 172);
		
		sc_picsChosed = new ScrolledComposite(group_picsChosed, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsChosed.setBounds(10, 20, 554, 142);
//		sc_picsChosed.setExpandHorizontal(true);
//		sc_picsChosed.setExpandVertical(true);
		
		Group group_playSettings = new Group(container, SWT.NONE);
		group_playSettings.setText("播放设置");
		group_playSettings.setBounds(10, 373, 574, 171);
		
		composite_settings = new Composite(group_playSettings, SWT.NONE);
		composite_settings.setBounds(10, 20, 554, 140);
		
		Label lb_playType = new Label(composite_settings, SWT.NONE);
		lb_playType.setBounds(25, 10, 54, 12);
		lb_playType.setText("播放方式");
		
		combo_playType = new Combo(composite_settings, SWT.NONE);
		combo_playType.setBounds(85, 7, 86, 20);
		
		Label lb_playTimeInterval = new Label(composite_settings, SWT.NONE);
		lb_playTimeInterval.setBounds(248, 10, 86, 12);
		lb_playTimeInterval.setText("播放时间间隔");
		
		txt_playInteraval = new Text(composite_settings, SWT.BORDER);
		txt_playInteraval.setBounds(372, 7, 44, 18);
		
		Label lb_secondIcon = new Label(composite_settings, SWT.NONE);
		lb_secondIcon.setBounds(435, 10, 44, 12);
		lb_secondIcon.setText("秒");
		
		Label lb_playDate = new Label(composite_settings, SWT.NONE);
		lb_playDate.setBounds(25, 43, 54, 12);
		lb_playDate.setText("播放日期");
		
		dateTime_start = new DateTime(composite_settings, SWT.BORDER);
		dateTime_start.setBounds(85, 35, 84, 20);
		
		Label lb_toIcon = new Label(composite_settings, SWT.NONE);
		lb_toIcon.setBounds(248, 43, 54, 12);
		lb_toIcon.setText("到");
		
		dateTime_end = new DateTime(composite_settings, SWT.BORDER);
		dateTime_end.setBounds(372, 35, 84, 20);
		
		Label lb_playTime = new Label(composite_settings, SWT.NONE);
		lb_playTime.setBounds(25, 69, 54, 12);
		lb_playTime.setText("播放时间");
		
		time_start = new DateTime(composite_settings, SWT.BORDER | SWT.TIME);
		time_start.setBounds(85, 61, 84, 20);
		
		Label lb_toIcon1 = new Label(composite_settings, SWT.NONE);
		lb_toIcon1.setBounds(248, 69, 54, 12);
		lb_toIcon1.setText("到");
		
		time_end = new DateTime(composite_settings, SWT.BORDER | SWT.TIME);
		time_end.setBounds(372, 61, 84, 20);
		
		Label lb_week = new Label(composite_settings, SWT.NONE);
		lb_week.setBounds(25, 95, 54, 12);
		lb_week.setText("星期");
		
		//编辑图片按钮
		btn_editPic = new Button(container, SWT.NONE);
		btn_editPic.setBounds(132, 550, 72, 22);
		btn_editPic.setText("编辑图片");
		//设置按钮
		btn_setPlayControl = new Button(container, SWT.NONE);
		btn_setPlayControl.setBounds(244, 550, 72, 22);
		btn_setPlayControl.setText("设置");
		
		btn_close = new Button(container, SWT.NONE);
		btn_close.setBounds(360, 550, 72, 22);
		btn_close.setText("关闭");
		
		//加载数据到控件上
		init();

		return container;
	}
	
	/**
	 * 加载数据到控件上
	 */
	private void init(){
		//加载显示屏名字到列表中
		List<com.gxf.beans.Display> listOfDisplay = displayDao.queryAllDisplay();
		String displayItems[] = new String[listOfDisplay.size()];
		for(int i = 0; i < displayItems.length; i++)
			displayItems[i] = listOfDisplay.get(i).getName();
		combo_display.setItems(displayItems);
		
		for(int i = 0; i < combo_display.getItems().length; i++){
			String itemString = combo_display.getItem(i);
			if(itemString.equals(SetPlaySolution.displayName))
			{
				combo_display.select(i);
				break;
			}
		}
		if(combo_display.getSelectionIndex() == -1 && combo_display.getItemCount() != 0){
			combo_display.select(0);
		}

		
		//为按钮注册监听器
		btn_editPic.addSelectionListener(new ButtonSelectionListener());
		btn_close.addSelectionListener(new ButtonSelectionListener());
		btn_setPlayControl.addSelectionListener(new ButtonSelectionListener());
		
		queryDisplayNamesToCombo();
		
		//设置从前一个页面传过来的播放方案
		for(int i = 0; i < combo_playSolutionName.getItemCount(); i++){
			String itemString = combo_playSolutionName.getItem(i);
			if(itemString.equals(SetPlaySolution.playSolutionName)){
				combo_playSolutionName.select(i);
				break;
			}
		}
		
		//初始化星期复选框
		btn_weekdays = new Button[7];
		for(int i = 0; i < btn_weekdays.length; i++){
			btn_weekdays[i] = new Button(composite_settings, SWT.CHECK);
			btn_weekdays[i].setSelection(true);
		}		
		
		btn_weekdays[0].setBounds(85, 95, 57, 16);
		btn_weekdays[0].setText("星期一");
		
		btn_weekdays[1].setText("星期二");
		btn_weekdays[1].setBounds(141, 95, 57, 16);
		
		btn_weekdays[2].setText("星期三");
		btn_weekdays[2].setBounds(207, 95, 57, 16);
		
		btn_weekdays[3].setText("星期四");
		btn_weekdays[3].setBounds(273, 95, 57, 16);
		
		btn_weekdays[4].setText("星期五");
		btn_weekdays[4].setBounds(85, 119, 57, 16);
		
		btn_weekdays[5].setText("星期六");
		btn_weekdays[5].setBounds(141, 119, 57, 16);
		
		btn_weekdays[6].setText("星期天");
		btn_weekdays[6].setBounds(207, 119, 57, 16);
		
		//加载数据到播放方式组合框中
		String playStyles[] = new String[]{"普通播放", "定时播放"};
		combo_playType.setItems(playStyles);
		combo_playType.select(0);
		
		//设置默认播放时间间隔
		txt_playInteraval.setText(String.valueOf(1));
		
		//初始化日期和时间
		dateTime_end.setYear(3000);
		dateTime_end.setMonth(7);
		dateTime_end.setDay(6);
		
		time_start.setHours(0);
		time_start.setMinutes(0);
		time_start.setSeconds(0);
		
		time_end.setHours(23);
		time_end.setMinutes(59);
		time_end.setSeconds(59);
		
		//显示所有已有图片，供用户选择
		addImageToChoose();
		//显示播放方案下面所有图片
		addSc_picsChosed();
		
		//添加监听事件
		combo_display.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				queryDisplayNamesToCombo();
				//重新加载播放方案下面的图片
				addSc_picsChosed();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		combo_playSolutionName.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//重新加载播放方案下面的图片
				addSc_picsChosed();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//初始化上下文菜单
//		contextMenu = new Menu(curShell);
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
//			UpdatePlaySolution window = new UpdatePlaySolution();
//			window.setBlockOnOpen(true);
//			window.open();
//			Display.getCurrent().dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	//对外提供显示窗口的接口
	public void showWindow(){
		try {
			SetPlaySolution window = SetPlaySolution.getSetPlaySolution();
			window.setBlockOnOpen(true);
			window.open();
//			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("设置播放方案");
		String logoPath = curProjectPath + File.separator + "icons" + File.separator 
							+ "setting.png";
		newShell.setImage(new Image(Display.getDefault(), new ImageData(logoPath)));
		
		//获取当前shell
		curShell = newShell;
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(608, 637);
	}
	
	/**
	 * 按钮监听器
	 * @author Administrator
	 *
	 */
	class ButtonSelectionListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == btn_editPic){							//编辑图片按钮,打开文字图片编辑器
				WordPicEditTool wordPicEditTool = WordPicEditTool.getWordPicEditTool();
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				String playSolutionName = combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex());
				String playSolutionPath = curProjectPath + File.separator + 
											displayName + File.separator + playSolutionName;
				//向图片编辑器传递显示屏名称和播放方案名称
				WordPicEditTool.solutionPath = playSolutionPath;
				WordPicEditTool.playSolutionName = playSolutionName;
				WordPicEditTool.displayName = displayName;
				//或播放控制信息，传递给文字图片编辑器
				WordPicEditTool.playControl = getPlayControl();
				//向编辑器注册
				wordPicEditTool.addUpdatePlaySolution(curUpdatePlaySolution);
				wordPicEditTool.open();
			}
			else if(e.getSource() == btn_close){						//关闭窗口
				curShell.dispose();			
			}
			else if(e.getSource() == btn_setPlayControl){				//设置按钮
				String picPath = selectedPicture();
				
				if(picPath.length() == 0){								//没有选中要设置的图片
					MessageBox messageBox = new MessageBox(curShell);
					messageBox.setText("提示");
					messageBox.setMessage("请选择要设置的图片!");
					messageBox.open();
					return;
				}
				//更新图片控制信息
				updatePlayControl(picPath);
				
				//提示更新成功
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("提示");
				messageBox.setMessage("设置成功!");
				messageBox.open();
				
			}
		}
		
	}
	
	/**
	 * 查询播放方案名到组合框中
	 */
	private void queryDisplayNamesToCombo(){
		//查询显示屏对应的播放方案
		String displayName = combo_display.getItem(combo_display.getSelectionIndex());
		com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
		Set<PlaySolution> solutions = display.getSolutions();

		String solutionNams[] = new String[solutions.size()];
		int index = 0;
		for(PlaySolution playSolution : solutions){
			solutionNams[index++] = playSolution.getName();
		}
		
		//绑定到播放方案组合框中
		combo_playSolutionName.setItems(solutionNams);
		if(solutionNams.length > 0)
			combo_playSolutionName.select(0);
	}
	
	/**
	 * 从数据库读取所有图片路径，加载到滚动面板中，供用户选择
	 */
	public void addImageToChoose(){
		Composite composite_pics = new Composite(sc_picsToChoose, SWT.NONE);
		sc_picsToChoose.setContent(composite_pics);
		//初始化滚动面板布局等每行显示4张图片
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 4;
//		composite_pics.setLayout(gridLayout);
		
		//图片宽度和高度
		int compositeWidth = sc_picsToChoose.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		//从数据库中查询所有图片信息
		List<Picture> listOfPics = pictureDao.queryAllPicture();
		
		//显示图片的标签
		final Label labels_pic[]= new Label[listOfPics.size()];
		
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.CENTER);
			//上下文菜单
			Menu contextMenu = new Menu(curShell, SWT.POP_UP);
			MenuItem addItem = new MenuItem(contextMenu, SWT.PUSH);
			addItem.setText("添加");

			labels_pic[i].setMenu(contextMenu);
			//添加图片信息到label上
			ImageData imageData = new ImageData(listOfPics.get(i).getPicPath());
			imageData = imageData.scaledTo(picWidth, picHeight);
			imageData = imageData.scaledTo(picWidth - 15, picHeight - 15);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			//设置图片大小 
			labels_pic[i].setBounds((i % 4) * picWidth, (i / 4) * picHeight, picWidth, picHeight);
			
			//将图片路径放到event.data中
			addItem.setData(listOfPics.get(i).getPicPath());
			
			//上下文菜单添加监听器
			addItem.addSelectionListener(new MenuItemListenerImpl());

			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					//滑动窗口获得焦点
					sc_picsToChoose.setFocus();					
					//设置图片背景色
					Label temp = (Label) e.widget;
					for(Label label : labels_pic){
						label.setBackground(null);
					}
					temp.setBackground(LABEL_SELECTED_COLOR);
				}
			});
		}
		composite_pics.setSize(composite_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite_pics.layout();
	}
	
	/**
	 * 显示已经添加到播放方案中的图片
	 */
	public void addSc_picsChosed(){
		//清空上一次生成的面板对象
		Control controls_temp[] = sc_picsChosed.getChildren();
		if(controls_temp != null && controls_temp.length != 0){
			Composite temp = (Composite) sc_picsChosed.getChildren()[0];
			temp.dispose();			
		}
		
		//开始重新生成新的面板和图片
		Composite composite_pics = new Composite(sc_picsChosed, SWT.NONE);
		sc_picsChosed.setContent(composite_pics);
		
		sc_picsChosed.setExpandHorizontal(true);
		sc_picsChosed.setExpandVertical(true);
		
		//注意判断是否有播放方案可加载
		if(combo_display.getItemCount() == 0 || combo_playSolutionName.getItemCount() == 0)
			return;
		
		//初始化滚动面板布局等每行显示4张图片
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 4;
//		gridLayout.makeColumnsEqualWidth = true;
		
//		composite_pics.setLayout(gridLayout);
		
		sc_picsChosed.setMinSize(sc_picsChosed.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		//图片宽度和高度
		int compositeWidth = sc_picsToChoose.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		
		//从数据库中查询所有图片信息
		//1.查询显示屏信息
		//2.查询显示屏对应的播放方案
		//3.根据播放方案查询对应的图片
		//注意播放方案可能重名
		com.gxf.beans.Display display = displayDao.queryDisplayByName(combo_display.getItem(combo_display.getSelectionIndex()));
		Set<PlaySolution> setOfPlaySolution = display.getSolutions();
		PlaySolution playSolutionSelected = null;
		//遍历集合，找到选中的播放方案
		for(Iterator<PlaySolution> it_playSolution = setOfPlaySolution.iterator(); it_playSolution.hasNext();){
			playSolutionSelected = it_playSolution.next();
			if(playSolutionSelected.getName().equals(combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex()))){
				break;
			}
		}		
		List<Picture> listOfPics = new ArrayList<Picture>(playSolutionSelected.getPictures());
		//对图片按播放顺序升序排列
		Collections.sort(listOfPics);
		
		//显示图片的标签
		final Label labels_pic[]= new Label[listOfPics.size()];
		
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.CENTER);
			
			//上下文菜单
			Menu contextMenu = new Menu(curShell, SWT.POP_UP);

			MenuItem delItem = new MenuItem(contextMenu, SWT.PUSH);
			delItem.setText("删除");
			
			MenuItem moveForward = new MenuItem(contextMenu, SWT.PUSH);
			moveForward.setText("前移");
			
			MenuItem moveBack = new MenuItem(contextMenu, SWT.PUSH);
			moveBack.setText("后移");
			
			labels_pic[i].setMenu(contextMenu);
			
			//在标签上显示图片
			ImageData imageData = new ImageData(listOfPics.get(i).getPicPath());
			labels_pic[i].setSize(picWidth, picHeight);
			imageData = imageData.scaledTo(picWidth - 15, picHeight - 15);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			labels_pic[i].setBounds((i % 4) * picWidth, (i / 4) * picHeight, picWidth, picHeight);

			//将图片路径放到event.data中
			delItem.setData(listOfPics.get(i).getPicPath());
			moveForward.setData(listOfPics.get(i));
			moveBack.setData(listOfPics.get(i));
			
			//上下文菜单添加监听器
			delItem.addSelectionListener(new MenuItemListenerImpl());
			moveForward.addSelectionListener(new MenuItemListenerImpl());
			moveBack.addSelectionListener(new MenuItemListenerImpl());
			
			//将图片路径放到label.data中
			labels_pic[i].setData(listOfPics.get(i).getPicPath());
			
			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					Label temp = (Label) e.widget;
					for(Label label : labels_pic){
						label.setBackground(null);
					}
					temp.setBackground(LABEL_SELECTED_COLOR);
					sc_picsChosed.setFocus();
					
					//获取图片对象和播放控制信息
					String picPath = (String) temp.getData();
					Picture picture = pictureDao.queryByPicPath(picPath);
					PlayControl playControl = picture.getPlayControl();
					showDisplayControl(playControl);
				}
			});
		}
		sc_picsChosed.setMinSize(composite_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		composite_pics.setSize(composite_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite_pics.layout();
	}
	
	/**
	 * MenuItem监听器
	 * @author Administrator
	 *
	 */
	class MenuItemListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.getText() == "添加"){						//添加图片到播放方案中
				//1.复制文件到播放方案文件夹中
				//2.向数据库中写入图片信息，控制信息
				//3.更新显示
				String scrFilePath = (String) item.getData();
				String curTime = util.getCurTime();
				String playSolutionName = combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex());
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				//保存图片的相对路径
				String relativePath = displayName + File.separator + playSolutionName
						+ File.separator + curTime + ".bmp";
				//图片绝对路径
				String dstFilePath = curProjectPath + File.separator + relativePath;
				
				//复制文件
				util.copyFile(scrFilePath, dstFilePath);
				
				//图片信息写入到数据库中
				PlaySolution playSolution = playSolutionDao.querySolutionByName(playSolutionName);
				Picture picture = new Picture();
				picture.setPicName(curTime + ".bmp");
				picture.setPicPath(relativePath);
				picture.setPlaySolution(playSolution);
				
				//获取面板上面播放控制信息
				PlayControl playControl = getPlayControl();
				picture.setPlayControl(playControl);
				
				pictureDao.addPicture(picture);
				
				//更新显示
				addSc_picsChosed();
				
				//提示用户添加成功
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("提示");
				messageBox.setMessage("添加图片成功!");
				messageBox.open();
			}
			else if(item.getText() == "删除"){				//从播放方案中红删除图片
				//1.从数据库中删除数据
				//2.删除文件
				//3.更新显示
				//从数据库中删除数据
				String picPath = (String) item.getData();
				pictureDao.deletePictureByPicPath(picPath);
				
				//删除文件
				File picFile = new File(picPath);
				picFile.delete();
				
				//更新显示，两个面板都要更新
				addImageToChoose();
				addSc_picsChosed();
			}
			else if(item.getText().equals("前移")){			//图片前移，即播放顺序前移
				//获取当前图片播放顺序
				//1.如果是第一个播放，返回，不做处理
				//2.如果不是第一个播放顺序，播放顺序减一，前一张图片播放顺序加一
				Picture curPicture = (Picture) item.getData();
				int playOrder = curPicture.getPlayOrder();
				
				//1.图片是第一张播放顺序
				if(playOrder == 1)
					return;
				//2.不是第一张播放的图片
				Picture prePic = pictureDao.queryPicByPlayOrder(playOrder - 1);
				prePic.setPlayOrder(playOrder);
				
				curPicture.setPlayOrder(playOrder - 1);
				
				pictureDao.updatePicture(curPicture);
				pictureDao.updatePicture(prePic);
				
				//更新显示
				addSc_picsChosed();
				
			}
			else if(item.getText().equals("后移")){			//图片后移，即播放顺序后移
				//获取当前图片播放顺序
				//1.如果是最后一张图片，返回，不做处理
				//2.如果不是最后一张图片，播放顺序加一，后一张图片播放顺序减一
				Picture curPicture = (Picture) item.getData();
				int playOrder = curPicture.getPlayOrder();
				
				//1.最后播放的一张图片
				int picCount = pictureDao.queryPictureCount();
				if(picCount == (playOrder - 1))
					return;
				
				//2.不是最后播放的一张图片
				Picture behindPic = pictureDao.queryPicByPlayOrder(playOrder + 1);
				behindPic.setPlayOrder(playOrder);
				
				curPicture.setPlayOrder(playOrder + 1);
				
				pictureDao.updatePicture(curPicture);
				pictureDao.updatePicture(behindPic);
				
				//更新显示
				addSc_picsChosed();
			}
		}
		
	}
	
	/**
	 * 获取面板上面控制播放信息
	 * @return
	 */
	private PlayControl getPlayControl(){
		PlayControl playControl = new PlayControl();
		String playType = combo_playType.getItem(combo_playType.getSelectionIndex());
		if(playType.equals("普通播放"))
			playControl.setPlayType(1);
		else
			playControl.setPlayType(2);
		int timeInterval = Integer.valueOf(txt_playInteraval.getText());
		playControl.setTimeInterval(timeInterval);
		
		//设置开始播放日期 格式 yyyy-mm-dd
		int year = this.dateTime_start.getYear();
		int month = this.dateTime_start.getMonth();
		int day = this.dateTime_start.getDay();
		
		String dateStr = year + "-" + month + "-" + day;
		playControl.setDateTimeStart(Date.valueOf(dateStr));
		
		//设置结束播放日期
		year = this.dateTime_end.getYear();
		month = this.dateTime_end.getMonth();
		day = this.dateTime_end.getDay();
		
		dateStr = year + "-" + month + "-" + day;
		playControl.setDateTimeEnd(Date.valueOf(dateStr));
		
		//设置开始播放时间 格式hh:mm:ss
		int hour = this.time_start.getHours();
		int minute = this.time_start.getMinutes();
		int second = this.time_start.getSeconds();
		
		String timeStr = hour + ":" + minute + ":" + second;
		playControl.setTimeStart(Time.valueOf(timeStr));
		
		//设置结束播放时间
		hour = this.time_end.getHours();
		minute = this.time_end.getMinutes();
		second = this.time_end.getSeconds();
		
		timeStr = hour + ":" + minute + ":" + second;
		playControl.setTimeEnd(Time.valueOf(timeStr));
		
		//获取播放星期
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < btn_weekdays.length; i++){
			if(btn_weekdays[i].getSelection())
				sb.append("1");
			else
				sb.append("0");				
		}
		
		playControl.setWeekdays(sb.toString());
		return playControl;
	}
	
	/**
	 * 显示每张图片的控制播放信息
	 * @param playControl
	 */
	private void showDisplayControl(PlayControl playControl){
		//刷新播放方式和时间间隔
		combo_playType.select(playControl.getPlayType() - 1);
		txt_playInteraval.setText(String.valueOf(playControl.getTimeInterval()));
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
				btn_weekdays[i].setSelection(true);
			else
				btn_weekdays[i].setSelection(false);
		}
	}
	
	/**
	 * 查询被选中图片的picPath
	 * @return
	 */
	private String selectedPicture(){
		Composite composite = (Composite) sc_picsChosed.getChildren()[0];
		Control[] labels = composite.getChildren();
		String picPath = "";
		
		for(Control control : labels){
			Label element = (Label)control;
			if(element.getBackground().equals(LABEL_SELECTED_COLOR)){
				picPath = (String) element.getData();
				return picPath;
			}//if
		}//for
		
		return picPath;
		
	}
	
	/**
	 * 更新图片的播放控制信息
	 * @param picPath
	 */
	private void updatePlayControl(String picPath){
		//获取面板上面的设置
		PlayControl playControlNew = getPlayControl();
		//获取以前的的设置，从数据库中读取
		Picture picture = pictureDao.queryByPicPath(picPath);
		PlayControl playControlOld = picture.getPlayControl();
		//把新的控制信息copy到旧的上面
		util.copyPlayControl(playControlNew, playControlOld);
		
		//更新数据库中的数据
		pictureDao.updatePicture(picture);
	}
	
	/**
	 * 返回单例模式实例
	 * @return
	 */
	public static SetPlaySolution getSetPlaySolution(){
		return setPlaySolution;
	}
}
