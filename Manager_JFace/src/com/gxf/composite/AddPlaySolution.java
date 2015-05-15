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
	//���ֿؼ�
	private Text txt_displaySolutionName;
	private Text txt_comment;
	private Button btn_add;
	private Button btn_close;
	
	//���ֹ�����
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private Combo combo_display;
	private Shell curShell;
	
	
	//���ݿ������
	private DisplayDao displayDao = new DisplayDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	//����ģʽ  ʵ��
	private static AddPlaySolution addPlaySolution = new AddPlaySolution();
	
	/**
	 * ʹ�õ���ģʽ 
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
		lb_displayName.setText("��ʾ��");
		
		combo_display = new Combo(composite, SWT.NONE);
		combo_display.setBounds(84, 29, 109, 20);
		
		Label lb_playSolutionName = new Label(composite, SWT.NONE);
		lb_playSolutionName.setBounds(219, 32, 71, 12);
		lb_playSolutionName.setText("���ŷ�����");
		
		txt_displaySolutionName = new Text(composite, SWT.BORDER);
		txt_displaySolutionName.setBounds(296, 29, 115, 18);
		
		Label lb_comment = new Label(composite, SWT.NONE);
		lb_comment.setBounds(24, 75, 54, 12);
		lb_comment.setText("˵��");
		
		txt_comment = new Text(composite, SWT.BORDER);
		txt_comment.setBounds(84, 69, 109, 18);
		
		btn_add = new Button(composite, SWT.NONE);
		btn_add.setBounds(121, 120, 72, 22);
		btn_add.setText("���");
		
		btn_close = new Button(composite, SWT.NONE);
		btn_close.setBounds(219, 120, 72, 22);
		btn_close.setText("�ر�");
		
		//�������ݵ��ؼ���
		init();
		return container;
	}
	
	/**
	 * ��������ݵ��ؼ���
	 */
	private void init(){
		//�������ݵ���ʾ������
		List<com.gxf.beans.Display> displays = displayDao.queryAllDisplay();
		String displayNames[] = new String[displays.size()];
		for(int i = 0; i < displays.size(); i++){
			displayNames[i] = displays.get(i).getName();
		}
		combo_display.setItems(displayNames);
		combo_display.select(0);
		
		//Ϊ��ť��Ӽ����¼�
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
		newShell.setText("��Ӳ��ŷ���");
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
	 * ��ť������
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
			if(e.getSource() == btn_add){						//��Ӳ��ŷ���
				//��֤���ŷ�����Ϣ�Ƿ�����
				if(!validPlaySolution())
					return;
				addPlaySolution();
			}
			else if(e.getSource() == btn_close){				//�رյ�ǰ����
				curShell.dispose();
			}
			
		}
		
		/**
		 * ��Ӳ��ŷ���
		 */
		private void addPlaySolution(){
			//���û����ʾ����Ϣ
			if(combo_display.getItemCount() == 0)
			{
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("��ʾ");
				messageBox.setMessage("���������ʾ����Ϣ!");
				messageBox.open();
				
				return;
			}
			//���ŷ�������Ϊ��
			if(txt_displaySolutionName.getText() == null){
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("��ʾ");
				messageBox.setMessage("����д���ŷ�����!");
				messageBox.open();
				
				return;
			}
			//����Ҫ��ŵĲ��ŷ���
			String solutionName = txt_displaySolutionName.getText();
			PlaySolution playSolution = new PlaySolution();
			playSolution.setName(solutionName);
			playSolution.setCreateTime(new Date());
			playSolution.setUpdateTime(new Date());
			playSolution.setUpdateCount(0);
			playSolution.setComment(txt_comment.getText());
			//��ȡ��������ʾ��
			String displayName = combo_display.getItem(combo_display.getSelectionIndex());
			com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
			playSolution.setDisplay(display);
			
			//��ŵ����ݿ���
			playSolutionDao.addPlaySolution(playSolution);
			
			//�������ŷ����ļ���
			String playSolutionPath = curProjectPath + File.separator + displayName + File.separator 
					+  solutionName;
			File playSolutionFile = new File(playSolutionPath);
			playSolutionFile.mkdir();
			
			//��ԭ���Ĵ��ڱ����Ӽ�¼
			PlaySolutionComposite.addTableItem(displayName, solutionName);
			//��ʾ�����ɹ�
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("��ʾ");
			messageBox.setMessage("���ŷ��������ɹ�!");
			messageBox.open();
		}
		
	}

	/**
	 * ��ʾ����
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
	 * ��ȡ����ģʽ  ʵ��
	 * @return
	 */
	public static AddPlaySolution getAddPlaySolution(){
		return addPlaySolution;
	}
	
	/**
	 * ��֤���ŷ�����Ϣ�Ƿ�����
	 * �緽��������Ϊ�յ�
	 * @return
	 */
	private boolean validPlaySolution(){
		//û����ʾ����Ϣ
		if(combo_display.getItemCount() == 0){
			util.getMessageBox(curShell, "��ʾ", "���������ʾ����Ϣ!").open();
			return false;
		}
		
		//û����д���ŷ�����
		if(txt_displaySolutionName.getText() == null || txt_displaySolutionName.getText().equals("")){
			util.getMessageBox(curShell, "��ʾ", "���ŷ���������Ϊ��!").open();
			return false;
		}
		
		return true;
	}
}
