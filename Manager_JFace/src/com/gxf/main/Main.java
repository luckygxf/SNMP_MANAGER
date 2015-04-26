package com.gxf.main;

import org.eclipse.jface.action.MenuManager;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.jface.action.Action;

import com.gxf.actions.EditAction;
import com.gxf.actions.FileAction;
import com.gxf.actions.HelpAction;
import com.gxf.actions.SystemAction;
import com.gxf.actions.ToolAction;
import com.gxf.composite.HostListComposite;
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
	private Action fileAction = new FileAction();
	private Action editAction = new EditAction();
	private Action toolAction = new ToolAction();
	private Action systemAction = new SystemAction();
	private Action helpAction = new HelpAction();
	
	//������
	private Util util = new Util();
	private static Main app;
	
	//��ť
	private Button btn_sendSolution;
	private Button btn_hostList;
	private Button btn_sendMessage;
	
	//��������tabfolder
	private CTabFolder tabFolder_workspace;
	
	

	/**
	 * Create the application window.
	 */
	public Main() {
		super(null);
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();											//���Ӳ˵���
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
		//��ȡ��ǰ��Ŀ·��
		String projectPath = util.getCurrentProjectPath();
		
		Composite composite_left = new Composite(container, SWT.NONE);
		composite_left.setBounds(0, 10, 139, 518);
		
		Label lb_hostlist = new Label(composite_left, SWT.NONE);
		lb_hostlist.setImage(SWTResourceManager.getImage(projectPath + "\\icons\\hostlist.png"));
		lb_hostlist.setBounds(0, 10, 45, 34);
		
		//�����б�����������
		btn_hostList = new Button(composite_left, SWT.NONE);
		
		btn_hostList.setBounds(44, 10, 95, 34);
		btn_hostList.setText("�����б�");
		
		Label label = new Label(composite_left, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(projectPath + "\\icons\\sendMessage.png"));
		label.setBounds(0, 46, 45, 34);
		
		//������Ϣ��ť
		btn_sendMessage = new Button(composite_left, SWT.NONE);
		
		btn_sendMessage.setText("������Ϣ");
		btn_sendMessage.setBounds(44, 46, 95, 34);
		
		//���Ͳ��ŷ���
		Label lb_sendSolution = new Label(composite_left, SWT.NONE);
		lb_sendSolution.setImage(SWTResourceManager.getImage(projectPath + "\\icons\\settingIcon.png"));
		lb_sendSolution.setBounds(0, 86, 45, 34);
		
		btn_sendSolution = new Button(composite_left, SWT.NONE);
		btn_sendSolution.setText("���Ͳ��ŷ���");
		btn_sendSolution.setBounds(44, 86, 95, 34);
		
		Composite composite_right = new Composite(container, SWT.NONE);
		composite_right.setBounds(145, 10, 600, 518);
		
		//��������tabFolder
		tabFolder_workspace = new CTabFolder(composite_right, SWT.NONE);
		tabFolder_workspace.setBounds(0, 10, 597, 507);
		tabFolder_workspace.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tabFolder_workspace.setMaximizeVisible(true);
		tabFolder_workspace.setMinimizeVisible(true);		
		
		//�Կؼ����г�ʼ��
		init();
		return container;
	}

	/**
	 * ��ʼ���ؼ�����ؼ���С�����Ӽ����¼�
	 */
	public void init(){
		//Ϊ���Ͳ��ŷ���ע�������
		btn_sendSolution.addSelectionListener(new ButtonListenerImpl());
		btn_sendMessage.addSelectionListener(new ButtonListenerImpl());
		btn_hostList.addSelectionListener(new ButtonListenerImpl());
	}
	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {						
		MenuManager menuManager = new MenuManager("menu");					//���Ӳ˵�������
		
		MenuManager fileMenuManager = new MenuManager("�ļ�");
		MenuManager editMenuManager = new MenuManager("�༭");
		MenuManager toolMenuManager = new MenuManager("����");
		MenuManager systemMenuManager = new MenuManager("ϵͳ");
		MenuManager helpMenuManager = new MenuManager("����");
		
		menuManager.add(fileMenuManager);
		menuManager.add(editMenuManager);
		menuManager.add(toolMenuManager);
		menuManager.add(systemMenuManager);
		menuManager.add(helpMenuManager);

		fileMenuManager.add(fileAction);
		editMenuManager.add(editAction);
		toolMenuManager.add(toolAction);
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
		ToolBarManager toolBarManager = new ToolBarManager(SWT.NONE);				//���ӹ�����

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
		//���ó���logo
		String projectPath = util.getCurrentProjectPath();
		String logoPath = projectPath + "\\icons\\" + "logo.png";
		ImageData logoImageData = new ImageData(logoPath);
		Image logoImage = new Image(newShell.getDisplay(), logoImageData);
		newShell.setImage(logoImage);
		super.configureShell(newShell);
		//���ó������
		newShell.setText("LED��������--by GXF");

	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(756, 624);
	}
	
	/**
	 * ���ش������
	 * @return
	 */
	public static Main getMain(){
		return app;
	}
	
	/**
	 * ��ť�¼�������
	 * @author Administrator
	 *
	 */
	class ButtonListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			
			if(e.getSource() == btn_sendSolution){					//���Ͳ��ŷ���
				Composite composite_playSoltion = new SendPlaySolution(tabFolder_workspace, SWT.NONE);
				addTabItem("���Ͳ��ŷ���", composite_playSoltion);
			}
			else if(e.getSource() == btn_sendMessage){				//������Ϣ					
				Composite composite_sendMessage = new SendMessageComposite(tabFolder_workspace, SWT.NONE);
				addTabItem("������Ϣ", composite_sendMessage);
			}
			else if(e.getSource() == btn_hostList){					//�����б�	
				Composite composite_hostList = new HostListComposite(tabFolder_workspace, SWT.NONE);
				addTabItem("�����б�", composite_hostList);
			}
		}
		
	}
	
	/**
	 * ����tabitem
	 * @param tabItemName
	 */
	private void addTabItem(String tabItemName, Composite composite){
		CTabItem ctabItems[] = tabFolder_workspace.getItems();
		int index = 0;
		boolean isExist = false;
		for(CTabItem element : ctabItems){
			if(element.getText().equals(tabItemName))
			{
				isExist = true;
				break;
			}
			index ++;
		}//for
		
		index = isExist ? index : index++;
		//tabҲû�г���
		if(isExist == false){			
			CTabItem ctabItem = new CTabItem(tabFolder_workspace, SWT.CLOSE);
			ctabItem.setText(tabItemName);
			tabFolder_workspace.setSelection(index);
			ctabItem.setControl(composite);
		}		
		//tabҳ����
		if(isExist){
			tabFolder_workspace.setSelection(index);
		}
		tabFolder_workspace.redraw();
	}
	
}