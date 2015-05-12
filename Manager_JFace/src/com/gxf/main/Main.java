package com.gxf.main;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.action.Action;

import com.gxf.actions.AddDisplayAction;
import com.gxf.actions.AddPlaySolutionAction;
import com.gxf.actions.ExitAction;
import com.gxf.actions.HelpAction;
import com.gxf.actions.SystemAction;
import com.gxf.composite.DisplayComposite;
import com.gxf.composite.PlaySolutionComposite;
import com.gxf.composite.QueryPlaySolution;
import com.gxf.composite.SendMessageComposite;
import com.gxf.composite.SendPlaySolution;
import com.gxf.util.Util;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class Main extends ApplicationWindow {
	
	//菜单对应的ACTION
	private Action exitAction = ExitAction.getExitAction();
	private Action systemAction = SystemAction.getSystemAction();
	private Action helpAction = HelpAction.getHelpAction();
	private Action addDisplayAction = new AddDisplayAction();
	private Action addPlaySolutionAction = new AddPlaySolutionAction();
	
	//工具类
	private Util util = new Util();
	private static Main app;
	
	//按钮
	private Button btn_sendSolution;
	private Button btn_hostList;
	private Button btn_sendMessage;
	private Button btn_manageSolution;
	private Button btn_querySolution;
	
	//工作区间tabfolder
	private CTabFolder tabFolder_workspace;
	
	//工作面板
	private Composite composite_playSoltion;
	private Composite composite_sendMessage;
	private Composite composite_hostList;
	private Composite composite_solutionList;
	private Composite composite_queryPlaySolution;
	/**
	 * Create the application window.
	 */
	public Main() {
		super(null);
		setShellStyle(SWT.MIN);
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();											//添加菜单栏
		addStatusLine();
		app = this;
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(final Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		//获取当前项目路径
		String projectPath = util.getCurrentProjectPath();
		
		Composite composite_left = new Composite(container, SWT.NONE);
		composite_left.setBounds(0, 10, 139, 518);
		
		//查询播放方案
		Label lb_querySolution = new Label(composite_left, SWT.NONE);
		lb_querySolution.setBounds(0, 10, 45, 34);
		lb_querySolution.setImage(new Image(Display.getDefault(), new ImageData(projectPath + "\\icons\\query.png")));
		
		btn_querySolution = new Button(composite_left, SWT.NONE);
		btn_querySolution.setText("查询播放方案");
		btn_querySolution.setBounds(44, 10, 95, 34);

		//显示屏管理
		Label lb_hostlist = new Label(composite_left, SWT.NONE);
		lb_hostlist.setBounds(0, 134, 45, 34);
		lb_hostlist.setImage(new Image(Display.getDefault(), new ImageData(projectPath + "\\icons\\manageDisplay.png")));
		
		btn_hostList = new Button(composite_left, SWT.NONE);		
		btn_hostList.setBounds(44, 134, 95, 34);
		btn_hostList.setText("显示屏管理");

		//发送消息按钮
		Label label = new Label(composite_left, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(projectPath + "\\icons\\sendMessage.png"));
		label.setBounds(0, 185, 45, 34);
		label.setVisible(false);
		
		btn_sendMessage = new Button(composite_left, SWT.NONE);
		btn_sendMessage.setVisible(false);
		
		btn_sendMessage.setText("发送消息");
		btn_sendMessage.setBounds(44, 185, 95, 34);
		
		//发送播放方案
		Label lb_sendSolution = new Label(composite_left, SWT.NONE);
		lb_sendSolution.setImage(new Image(Display.getDefault(), new ImageData(projectPath + "\\icons\\sendPlaySolution.png")));
		lb_sendSolution.setBounds(0, 54, 45, 34);
		
		btn_sendSolution = new Button(composite_left, SWT.NONE);
		btn_sendSolution.setText("发送播放方案");
		btn_sendSolution.setBounds(44, 54, 95, 34);
		
		Label lb_updateSolution = new Label(composite_left, SWT.NONE);
		lb_updateSolution.setBounds(0, 94, 45, 34);
		lb_updateSolution.setImage(new Image(Display.getDefault(), new ImageData(projectPath + "\\icons\\update.png")));
		
		btn_manageSolution = new Button(composite_left, SWT.NONE);
		btn_manageSolution.setText("播放方案管理");
		btn_manageSolution.setBounds(44, 94, 95, 34);
		
		Composite composite_right = new Composite(container, SWT.NONE);
		composite_right.setBounds(145, 10, 600, 518);
		
		//工作区的tabFolder
		tabFolder_workspace = new CTabFolder(composite_right, SWT.NONE);
		tabFolder_workspace.setBounds(0, 10, 597, 507);
		tabFolder_workspace.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		
		//对控件进行初始化
		init();
		return container;
	}

	/**
	 * 初始化控件，如控件大小，添加监听事件
	 */
	public void init(){
		//为发送播放方案注册监听器
		btn_sendSolution.addSelectionListener(new ButtonListenerImpl());
		btn_sendMessage.addSelectionListener(new ButtonListenerImpl());
		btn_hostList.addSelectionListener(new ButtonListenerImpl());
		btn_manageSolution.addSelectionListener(new ButtonListenerImpl());
		btn_querySolution.addSelectionListener(new ButtonListenerImpl());
		
		//初始化显示发送播放方案面面板
		Composite composite_queryPlaySolution = new QueryPlaySolution(tabFolder_workspace, SWT.NONE);
		addTabItem("查询播放方案", composite_queryPlaySolution);
		
		
	}
	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {						
		MenuManager menuManager = new MenuManager("menu");					//添加菜单栏内容
		
		MenuManager fileMenuManager = new MenuManager("文件");
		MenuManager systemMenuManager = new MenuManager("系统");
		MenuManager helpMenuManager = new MenuManager("帮助");
		
		menuManager.add(fileMenuManager);
		menuManager.add(systemMenuManager);
		menuManager.add(helpMenuManager);
		
		fileMenuManager.add(exitAction);
		systemMenuManager.add(systemAction);
		helpMenuManager.add(helpAction);		
		
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {						
		ToolBarManager toolBarManager = new ToolBarManager(SWT.NONE);				//添加工具栏
		
		//添加工具栏按钮 
		toolBarManager.add(addDisplayAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(addPlaySolutionAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(helpAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(systemAction);
		toolBarManager.add(new Separator());
		toolBarManager.add(exitAction);
		
		return toolBarManager;
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
			Main window = new Main();
			window.setBlockOnOpen(true);
			window.open();
			if(Display.getCurrent() != null)
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
		//设置程序logo
		String projectPath = util.getCurrentProjectPath();
		String logoPath = projectPath + "\\icons\\" + "logo.png";
		ImageData logoImageData = new ImageData(logoPath);
		Image logoImage = new Image(newShell.getDisplay(), logoImageData);
		newShell.setImage(logoImage);
		super.configureShell(newShell);
		//设置程序标题
		newShell.setText("LED管理程序--by GXF");
		
		//为关闭窗口注册监听器
		newShell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				//终止当前正字运行的jvm
				System.exit(0);
				
			}
		});

	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(756, 624);
	}
	
	/**
	 * 返回窗体对象
	 * @return
	 */
	public static Main getMain(){
		return app;
	}
	
	/**
	 * 按钮事件监听器
	 * @author Administrator
	 *
	 */
	class ButtonListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			
			if(e.getSource() == btn_sendSolution){					//发送播放方案
				//先查找tabitem,如果没有再添加
				int index = getTabItemIndex("发送播放方案");
				//如果没有，创建新的tabitem
				if(index == -1){					
					composite_playSoltion = new SendPlaySolution(tabFolder_workspace, SWT.NONE);
					addTabItem("发送播放方案", composite_playSoltion);
				}
				else{												//如果已经出现过
					tabFolder_workspace.setSelection(index);
				}			
				
			}
			else if(e.getSource() == btn_sendMessage){				//发送消息	
				//先查找tabitem,如果没有再添加
				int index = getTabItemIndex("发送消息");
				//如果没有，创建新的tabitem
				if(index == -1){					
					composite_sendMessage = new SendMessageComposite(tabFolder_workspace, SWT.NONE);
					addTabItem("发送消息", composite_sendMessage);
				}
				else{												//如果已经出现过
					tabFolder_workspace.setSelection(index);
				}
			}
			else if(e.getSource() == btn_hostList){					//屏幕管理列表	
				//先查找tabitem,如果没有再添加
				int index = getTabItemIndex("显示屏管理");
				//如果没有，创建新的tabitem
				if(index == -1){					
					composite_hostList = new DisplayComposite(tabFolder_workspace, SWT.NONE);
					addTabItem("显示屏管理", composite_hostList);
				}
				else{												//如果已经出现过
					tabFolder_workspace.setSelection(index);
				}
			}
			else if(e.getSource() == btn_manageSolution){			//播放方案管理
				//先查找tabitem,如果没有再添加
				int index = getTabItemIndex("播放方案管理");
				//如果没有，创建新的tabitem
				if(index == -1){					
					composite_solutionList = new PlaySolutionComposite(tabFolder_workspace, SWT.NONE);
					addTabItem("播放方案管理", composite_solutionList);
				}
				else{												//如果已经出现过
					tabFolder_workspace.setSelection(index);
				}
			}
			else if(e.getSource() == btn_querySolution){			//查询播放方案
				//先查找tabitem,如果没有再添加
				int index = getTabItemIndex("查询播放方案");
				//如果没有，创建新的tabitem
				if(index == -1){		
					composite_queryPlaySolution = new QueryPlaySolution(tabFolder_workspace, SWT.NONE);
					addTabItem("查询播放方案", composite_queryPlaySolution);
				}
				else{												//如果已经出现过
					tabFolder_workspace.setSelection(index);
				}
			}
		}
		
	}
	
	/**
	 * 添加tabitem
	 * @param tabItemName
	 */
	private void addTabItem(String tabItemName, Composite composite){
		CTabItem ctabItems[] = tabFolder_workspace.getItems();
		
		CTabItem ctabItem = new CTabItem(tabFolder_workspace, SWT.CLOSE);
		ctabItem.setText(tabItemName);
		tabFolder_workspace.setSelection(ctabItems.length);						//选中了，才会显示tab页中的内容
		ctabItem.setControl(composite);

	}
	
	/**
	 * 查询tab分页在tabFolder中的索引
	 * 没有出现过返回-1
	 * @param tabItemName
	 * @return
	 */
	private int getTabItemIndex(String tabItemName){
		int index = 0;
		CTabItem ctabItems[] = tabFolder_workspace.getItems();
		boolean isExist = false;
		for(CTabItem element : ctabItems){
			if(element.getText().equals(tabItemName))
			{
				isExist = true;
				break;
			}
			index ++;
		}//for
		
		return isExist ? index : -1;
	}
}
