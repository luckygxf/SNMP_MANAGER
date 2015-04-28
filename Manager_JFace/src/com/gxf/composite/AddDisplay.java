package com.gxf.composite;

import java.io.File;
import java.util.ArrayList;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.gxf.beans.Communication;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.CommunicationDao;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.CommunicationDaoImpl;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.Util;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;

/**
 * �����ʾ����Ϣ
 * @author Administrator
 *
 */
public class AddDisplay extends ApplicationWindow {	
	//����ϵĿؼ�
	private Text txt_displayName;
	private Text txt_displayType;
	private Text txt_ip;
	private Text txt_port;
	private Text txt_comment;
	private Combo combo_commType;
	private Composite composite_solutions;
	private Button btn_add;
	private Button btn_close;
	
	private Button cbnSolutions[];
	
	//������
	private Util util = new Util();
	private String curProjectPath = util.getCurrentProjectPath();
	
	private Shell curShell;
	
	//���ݿ������
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	private List<PlaySolution> listOfSolution = new ArrayList<PlaySolution>();
	private CommunicationDao communicatioDao = new CommunicationDaoImpl();
	private DisplayDao displayDao = new DisplayDaoImpl();

	/**
	 * Create the application window.
	 */
	public AddDisplay() {
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
		
		Group group_basicInfo = new Group(container, SWT.NONE);
		group_basicInfo.setText("��ʾ��������Ϣ");
		group_basicInfo.setBounds(10, 0, 493, 107);
		
		Label lb_displayName = new Label(group_basicInfo, SWT.NONE);
		lb_displayName.setBounds(10, 24, 54, 12);
		lb_displayName.setText("����");
		
		txt_displayName = new Text(group_basicInfo, SWT.BORDER);
		txt_displayName.setBounds(83, 21, 86, 18);
		
		Label lb_displayType = new Label(group_basicInfo, SWT.NONE);
		lb_displayType.setBounds(255, 24, 54, 12);
		lb_displayType.setText("����");
		
		txt_displayType = new Text(group_basicInfo, SWT.BORDER);
		txt_displayType.setText("");
		txt_displayType.setBounds(347, 21, 86, 18);
		
		Label lb_displayCommType = new Label(group_basicInfo, SWT.NONE);
		lb_displayCommType.setBounds(10, 50, 54, 12);
		lb_displayCommType.setText("ͨ�ŷ�ʽ");
		
		combo_commType = new Combo(group_basicInfo, SWT.NONE);
		combo_commType.setBounds(83, 45, 86, 20);
		
		Label lb_ip = new Label(group_basicInfo, SWT.NONE);
		lb_ip.setBounds(255, 50, 54, 12);
		lb_ip.setText("ip");
		
		txt_ip = new Text(group_basicInfo, SWT.BORDER);
		txt_ip.setText("");
		txt_ip.setBounds(347, 44, 86, 18);
		
		Label lb_port = new Label(group_basicInfo, SWT.NONE);
		lb_port.setBounds(10, 77, 54, 12);
		lb_port.setText("�˿�");
		
		txt_port = new Text(group_basicInfo, SWT.BORDER);
		txt_port.setBounds(83, 71, 86, 18);
		
		Label lb_comment = new Label(group_basicInfo, SWT.NONE);
		lb_comment.setBounds(255, 77, 54, 12);
		lb_comment.setText("˵��");
		
		txt_comment = new Text(group_basicInfo, SWT.BORDER);
		txt_comment.setText("");
		txt_comment.setBounds(347, 71, 86, 18);
		
		Group group_solutions = new Group(container, SWT.NONE);
		group_solutions.setText("���ò��ŷ���");
		group_solutions.setBounds(10, 122, 493, 101);
		
		ScrolledComposite sc_solutions = new ScrolledComposite(group_solutions, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_solutions.setBounds(10, 21, 473, 68);
		sc_solutions.setExpandHorizontal(true);
		sc_solutions.setExpandVertical(true);
		
		//���ò��ŷ������
		composite_solutions = new Composite(sc_solutions, SWT.NONE);
		sc_solutions.setContent(composite_solutions);
		sc_solutions.setMinSize(composite_solutions.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 6;
		composite_solutions.setLayout(gridLayout);
		
		
		btn_add = new Button(container, SWT.NONE);
		btn_add.setBounds(161, 263, 72, 22);
		btn_add.setText("���");
		
		btn_close = new Button(container, SWT.NONE);
		btn_close.setBounds(257, 263, 72, 22);
		btn_close.setText("�ر�");
		
		//��ʼ������
		init();

		return container;
	}
	
	/**
	 * �Կؼ����г�ʼ�� 
	 */
	private void init(){
		//Ϊͨ�ŷ�ʽ�������
		String commTypes[] = new String[]{"����ͨ��", "����ͨ��"};
		combo_commType.setItems(commTypes);
		combo_commType.select(0);
		
		//���ز��ŷ���
		listOfSolution = playSolutionDao.queryAllSolutions();
		System.out.println("size = " + listOfSolution.size());
		cbnSolutions = new Button[listOfSolution.size()];
		for(int i = 0; i < cbnSolutions.length; i++){
			cbnSolutions[i] = new Button(composite_solutions, SWT.CHECK);
			cbnSolutions[i].setText(listOfSolution.get(i).getName());
		}
		composite_solutions.layout();
		
		//�԰�����Ӽ�����
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
//			AddDisplay window = new AddDisplay();
//			window.setBlockOnOpen(true);
//			window.open();
//			Display.getCurrent().dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * ��ʾ����
	 */
	public void show(){
		try {
			AddDisplay window = new AddDisplay();
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
		newShell.setText("�����ʾ����Ϣ");
		String iconPath = curProjectPath + File.separator + "icons" + File.separator
							+ "addDisplayIcon.png";
		newShell.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		curShell = newShell;
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(521, 371);
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
			if(e.getSource() == btn_add){							//��Ӳ�������Ϣ��ť
				addDisplay();
			}
			else if(e.getSource() == btn_close){					//�رմ���
				curShell.dispose();
			}
		}
		
		/**
		 * ��Ӳ�����Ļ��Ϣ
		 */
		private void addDisplay(){
			com.gxf.beans.Display display = new com.gxf.beans.Display();
			
			//���������������ʾ��
			if(displayDao.queryDisplayByName(txt_displayName.getText()) != null){
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("��ʾ");
				messageBox.setMessage("�������������ͬ����ʾ��!");
				messageBox.open();
				return;
			}
			//name and type
			display.setName(txt_displayName.getText());
			display.setType(txt_displayType.getText());
			
			//��ȡ���õĲ��ŷ�����Ϣ,��ӵ���ʾ����
			for(int i = 0; i < cbnSolutions.length; i++){
				if(cbnSolutions[i].getSelection()){
					PlaySolution temp = playSolutionDao.querySolutionByNanme(cbnSolutions[i].getText());
					display.getSolutions().add(temp);
				}
			}
			//��װͨ�ŷ�ʽ
			Communication communication = new Communication();
			communication.setName(combo_commType.getItem(combo_commType.getSelectionIndex()));
			communication.setIp(txt_ip.getText());
			if(txt_port.getText() == "")
				communication.setPort(0);
			else
				communication.setPort(Integer.valueOf(txt_port.getText()));
			//�����ݿ������ͨ�ŷ�ʽ��¼
			communicatioDao.addCommunication(communication);
			display.setCommunication(communication);
			
			//commment�ֶ�
			display.setComment(txt_comment.getText());
			
			displayDao.addDisplay(display);
			
			//ˢ��ǰ�������ʾ
			HostListComposite.addTableItem(display);
			
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("��ʾ");
			messageBox.setMessage("��ʾ����Ϣ��ӳɹ�!");
			messageBox.open();
		}
	}
}
