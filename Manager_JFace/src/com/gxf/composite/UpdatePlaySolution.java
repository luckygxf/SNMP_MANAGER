package com.gxf.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.gxf.beans.Picture;
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
public class UpdatePlaySolution extends ApplicationWindow {
	//工具类
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	
	//控件类
	private Combo combo_display;
	private Combo combo_playSolutionName;
	private Button btn_weekdays[];
	private Composite composite_settings;
	private Combo combo_playType;
	private DateTime dateTime_start;
	private DateTime time_start;
	private ScrolledComposite sc_picsToChoose;
	private ScrolledComposite sc_picsChosed;
	private Text txt_playInteraval;
	private Button btn_editPic;
	
	
	//数据库访问类
	private DisplayDao displayDao = new DisplayDaoImpl();
	private PictureDao pictureDao = new PictureDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	/**
	 * Create the application window.
	 */
	public UpdatePlaySolution() {
		super(null);
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
		sc_picsToChoose.setExpandHorizontal(true);
		sc_picsToChoose.setExpandVertical(true);
		
		Group group_picsChosed = new Group(container, SWT.NONE);
		group_picsChosed.setText("已添加图片");
		group_picsChosed.setBounds(10, 195, 574, 172);
		
		sc_picsChosed = new ScrolledComposite(group_picsChosed, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsChosed.setBounds(10, 20, 554, 142);
		sc_picsChosed.setExpandHorizontal(true);
		sc_picsChosed.setExpandVertical(true);
		
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
		
		DateTime dateTime_end = new DateTime(composite_settings, SWT.BORDER);
		dateTime_end.setBounds(372, 35, 84, 20);
		
		Label lb_playTime = new Label(composite_settings, SWT.NONE);
		lb_playTime.setBounds(25, 69, 54, 12);
		lb_playTime.setText("播放时间");
		
		time_start = new DateTime(composite_settings, SWT.BORDER | SWT.TIME);
		time_start.setBounds(85, 61, 84, 20);
		
		Label lb_toIcon1 = new Label(composite_settings, SWT.NONE);
		lb_toIcon1.setBounds(248, 69, 54, 12);
		lb_toIcon1.setText("到");
		
		DateTime time_end = new DateTime(composite_settings, SWT.BORDER | SWT.TIME);
		time_end.setBounds(372, 61, 84, 20);
		
		Label lb_week = new Label(composite_settings, SWT.NONE);
		lb_week.setBounds(25, 95, 54, 12);
		lb_week.setText("星期");
		
		//编辑图片按钮
		btn_editPic = new Button(container, SWT.NONE);
		btn_editPic.setBounds(132, 550, 72, 22);
		btn_editPic.setText("编辑图片");
		
		Button btn_addSolution = new Button(container, SWT.NONE);
		btn_addSolution.setBounds(244, 550, 72, 22);
		btn_addSolution.setText("添加");
		
		Button btn_close = new Button(container, SWT.NONE);
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
		combo_display.select(0);
		
		//为按钮注册监听器
		btn_editPic.addSelectionListener(new ButtonSelectionListener());

		queryDisplayNamesToCombo();
		
		//初始化星期控件
		//初始化星期复选框
		btn_weekdays = new Button[7];
		for(int i = 0; i < btn_weekdays.length; i++){
			btn_weekdays[i] = new Button(composite_settings, SWT.CHECK);
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
		dateTime_start.setYear(1990);
		dateTime_start.setMonth(7);
		dateTime_start.setDay(6);
		
		time_start.setHours(0);
		time_start.setMinutes(0);
		time_start.setSeconds(0);
		
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
	public static void main(String args[]) {
		try {
			UpdatePlaySolution window = new UpdatePlaySolution();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
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
							+ "addDisplayIcon.png";
		newShell.setImage(new Image(Display.getDefault(), new ImageData(logoPath)));
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
				WordPicEditTool wordPicEditTool = new WordPicEditTool();
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				String playSolutionName = combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex());
				String playSolutionPath = curProjectPath + File.separator + 
											displayName + File.separator + playSolutionName;
				WordPicEditTool.solutionPath = playSolutionPath;
				WordPicEditTool.playSolutionName = playSolutionName;
				wordPicEditTool.open();
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
//		System.out.println("solutions.size() = " + solutions.size());
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
	private void addImageToChoose(){
		Composite composite_pics = new Composite(sc_picsToChoose, SWT.NONE);
		sc_picsToChoose.setContent(composite_pics);
		//初始化滚动面板布局等每行显示4张图片
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite_pics.setLayout(gridLayout);
		
		//图片宽度和高度
		int compositeWidth = sc_picsToChoose.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		//从数据库中查询所有图片信息
		List<Picture> listOfPics = pictureDao.queryAllPicture();
		
		//显示图片的标签
		Label labels_pic[]= new Label[listOfPics.size()];
		
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.NONE);
			ImageData imageData = new ImageData(listOfPics.get(i).getPicPath());
			imageData = imageData.scaledTo(picWidth, picHeight);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					sc_picsToChoose.setFocus();
					
				}
			});
		}
		composite_pics.setSize(composite_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite_pics.layout();
	}
	
	/**
	 * 显示已经添加到播放方案中的图片
	 */
	private void addSc_picsChosed(){
		Composite composite_pics = new Composite(sc_picsChosed, SWT.NONE);
		sc_picsChosed.setContent(composite_pics);
		//初始化滚动面板布局等每行显示4张图片
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite_pics.setLayout(gridLayout);
		
		//图片宽度和高度
		int compositeWidth = sc_picsToChoose.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		//从数据库中查询所有图片信息
		PlaySolution playSolution = playSolutionDao.querySolutionByNanme(combo_playSolutionName.getItem(
										combo_playSolutionName.getSelectionIndex()));
		List<Picture> listOfPics = new ArrayList<Picture>(playSolution.getPictures());
		
		//显示图片的标签
		Label labels_pic[]= new Label[listOfPics.size()];
		
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.NONE);
			ImageData imageData = new ImageData(listOfPics.get(i).getPicPath());
			imageData = imageData.scaledTo(picWidth, picHeight);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					sc_picsToChoose.setFocus();
					
				}
			});
		}
		composite_pics.setSize(composite_pics.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		composite_pics.layout();
	}
	


}
