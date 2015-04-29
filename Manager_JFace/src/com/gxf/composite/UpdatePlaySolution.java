package com.gxf.composite;

import java.io.File;
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
import org.eclipse.swt.widgets.Shell;

import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.Util;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;

/**
 * ��Ӳ��ŷ�������
 * @author Administrator
 *
 */
public class UpdatePlaySolution extends ApplicationWindow {
	//������
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private Text txt_playSolutinName;
	private Combo combo_display;
	
	private Button btn_editPic;
	
	//���ݿ������
	private DisplayDao displayDao = new DisplayDaoImpl();
	
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
		lb_display.setText("��ʾ��");
		
		//��ʾ��
		combo_display = new Combo(composite_head, SWT.NONE);
		combo_display.setBounds(130, 3, 86, 20);
		
		Label lb_playSolution = new Label(composite_head, SWT.NONE);
		lb_playSolution.setBounds(260, 6, 70, 12);
		lb_playSolution.setText("���ŷ�����");
		
		txt_playSolutinName = new Text(composite_head, SWT.BORDER);
		txt_playSolutinName.setBounds(370, 3, 91, 20);
		
		Group group_picsToChoose = new Group(container, SWT.NONE);
		group_picsToChoose.setText("��ѡͼƬ");
		group_picsToChoose.setBounds(10, 30, 574, 147);
		
		ScrolledComposite sc_picsToChoose = new ScrolledComposite(group_picsToChoose, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsToChoose.setBounds(10, 20, 554, 117);
		sc_picsToChoose.setExpandHorizontal(true);
		sc_picsToChoose.setExpandVertical(true);
		
		Group group_picsChosed = new Group(container, SWT.NONE);
		group_picsChosed.setText("�����ͼƬ");
		group_picsChosed.setBounds(10, 183, 574, 147);
		
		ScrolledComposite sc_picsChosed = new ScrolledComposite(group_picsChosed, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsChosed.setBounds(10, 20, 554, 117);
		sc_picsChosed.setExpandHorizontal(true);
		sc_picsChosed.setExpandVertical(true);
		
		Group group_playSettings = new Group(container, SWT.NONE);
		group_playSettings.setText("��������");
		group_playSettings.setBounds(10, 354, 574, 160);
		
		Composite composite_settings = new Composite(group_playSettings, SWT.NONE);
		composite_settings.setBounds(10, 20, 554, 130);
		
		//�༭ͼƬ��ť
		btn_editPic = new Button(container, SWT.NONE);
		btn_editPic.setBounds(131, 524, 72, 22);
		btn_editPic.setText("�༭ͼƬ");
		
		Button btn_addSolution = new Button(container, SWT.NONE);
		btn_addSolution.setBounds(243, 524, 72, 22);
		btn_addSolution.setText("���");
		
		Button btn_close = new Button(container, SWT.NONE);
		btn_close.setBounds(359, 524, 72, 22);
		btn_close.setText("�ر�");
		
		//�������ݵ��ؼ���
		init();

		return container;
	}
	
	/**
	 * �������ݵ��ؼ���
	 */
	private void init(){
		//������ʾ�����ֵ��б���
		List<com.gxf.beans.Display> listOfDisplay = displayDao.queryAllDisplay();
		String displayItems[] = new String[listOfDisplay.size()];
		for(int i = 0; i < displayItems.length; i++)
			displayItems[i] = listOfDisplay.get(i).getName();
		combo_display.setItems(displayItems);
		combo_display.select(0);
		
		//Ϊ��ťע�������
		btn_editPic.addSelectionListener(new ButtonSelectionListener());
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
		newShell.setText("��Ӳ��ŷ���");
		String logoPath = curProjectPath + File.separator + "icons" + File.separator 
							+ "addDisplayIcon.png";
		newShell.setImage(new Image(Display.getDefault(), new ImageData(logoPath)));
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(608, 611);
	}
	
	/**
	 * ��ť������
	 * @author Administrator
	 *
	 */
	class ButtonSelectionListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == btn_editPic){							//�༭ͼƬ��ť,������ͼƬ�༭��
				WordPicEditTool wordPicEditTool = new WordPicEditTool();
//				WordPicEditTool.solutionName = 
				wordPicEditTool.open();
			}
			
		}
		
	}
}
