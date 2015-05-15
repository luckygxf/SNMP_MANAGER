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
 * ��Ӳ��ŷ�������
 * @author Administrator
 *
 */
public class SetPlaySolution extends ApplicationWindow {
	//������
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private Shell curShell;
	
	//�ؼ���
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
	
	
	//���ݿ������
	private DisplayDao displayDao = new DisplayDaoImpl();
	private PictureDao pictureDao = new PictureDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	//��ʾ���Ͳ��ŷ���
	public static String displayName;
	public static String playSolutionName;
	
	//��ǰ���ڶ���
	private SetPlaySolution curUpdatePlaySolution;
	//labelѡ�б���ɫ
	private final Color LABEL_SELECTED_COLOR =  Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN);
	//�����Ĳ˵�
//	private Menu contextMenu;
	
	//����ģʽ ʵ��
	private static SetPlaySolution setPlaySolution = new SetPlaySolution();
	
	/**
	 * Create the application window.
	 */
	private SetPlaySolution() {
		super(null);
		//��ȡ��ǰ����
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
		lb_display.setText("��ʾ��");
		
		//��ʾ��
		combo_display = new Combo(composite_head, SWT.NONE);
		combo_display.setBounds(130, 3, 122, 20);
		
		Label lb_playSolution = new Label(composite_head, SWT.NONE);
		lb_playSolution.setBounds(318, 6, 70, 12);
		lb_playSolution.setText("���ŷ���");
		
		combo_playSolutionName = new Combo(composite_head, SWT.NONE);
		combo_playSolutionName.setBounds(394, 3, 122, 20);
		
		Group group_picsToChoose = new Group(container, SWT.NONE);
		group_picsToChoose.setText("��ѡͼƬ");
		group_picsToChoose.setBounds(10, 30, 574, 159);
		
		sc_picsToChoose = new ScrolledComposite(group_picsToChoose, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsToChoose.setBounds(10, 20, 554, 129);
//		sc_picsToChoose.setExpandHorizontal(true);
//		sc_picsToChoose.setExpandVertical(true);
		
		Group group_picsChosed = new Group(container, SWT.NONE);
		group_picsChosed.setText("�����ͼƬ");
		group_picsChosed.setBounds(10, 195, 574, 172);
		
		sc_picsChosed = new ScrolledComposite(group_picsChosed, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc_picsChosed.setBounds(10, 20, 554, 142);
//		sc_picsChosed.setExpandHorizontal(true);
//		sc_picsChosed.setExpandVertical(true);
		
		Group group_playSettings = new Group(container, SWT.NONE);
		group_playSettings.setText("��������");
		group_playSettings.setBounds(10, 373, 574, 171);
		
		composite_settings = new Composite(group_playSettings, SWT.NONE);
		composite_settings.setBounds(10, 20, 554, 140);
		
		Label lb_playType = new Label(composite_settings, SWT.NONE);
		lb_playType.setBounds(25, 10, 54, 12);
		lb_playType.setText("���ŷ�ʽ");
		
		combo_playType = new Combo(composite_settings, SWT.NONE);
		combo_playType.setBounds(85, 7, 86, 20);
		
		Label lb_playTimeInterval = new Label(composite_settings, SWT.NONE);
		lb_playTimeInterval.setBounds(248, 10, 86, 12);
		lb_playTimeInterval.setText("����ʱ����");
		
		txt_playInteraval = new Text(composite_settings, SWT.BORDER);
		txt_playInteraval.setBounds(372, 7, 44, 18);
		
		Label lb_secondIcon = new Label(composite_settings, SWT.NONE);
		lb_secondIcon.setBounds(435, 10, 44, 12);
		lb_secondIcon.setText("��");
		
		Label lb_playDate = new Label(composite_settings, SWT.NONE);
		lb_playDate.setBounds(25, 43, 54, 12);
		lb_playDate.setText("��������");
		
		dateTime_start = new DateTime(composite_settings, SWT.BORDER);
		dateTime_start.setBounds(85, 35, 84, 20);
		
		Label lb_toIcon = new Label(composite_settings, SWT.NONE);
		lb_toIcon.setBounds(248, 43, 54, 12);
		lb_toIcon.setText("��");
		
		dateTime_end = new DateTime(composite_settings, SWT.BORDER);
		dateTime_end.setBounds(372, 35, 84, 20);
		
		Label lb_playTime = new Label(composite_settings, SWT.NONE);
		lb_playTime.setBounds(25, 69, 54, 12);
		lb_playTime.setText("����ʱ��");
		
		time_start = new DateTime(composite_settings, SWT.BORDER | SWT.TIME);
		time_start.setBounds(85, 61, 84, 20);
		
		Label lb_toIcon1 = new Label(composite_settings, SWT.NONE);
		lb_toIcon1.setBounds(248, 69, 54, 12);
		lb_toIcon1.setText("��");
		
		time_end = new DateTime(composite_settings, SWT.BORDER | SWT.TIME);
		time_end.setBounds(372, 61, 84, 20);
		
		Label lb_week = new Label(composite_settings, SWT.NONE);
		lb_week.setBounds(25, 95, 54, 12);
		lb_week.setText("����");
		
		//�༭ͼƬ��ť
		btn_editPic = new Button(container, SWT.NONE);
		btn_editPic.setBounds(132, 550, 72, 22);
		btn_editPic.setText("�༭ͼƬ");
		//���ð�ť
		btn_setPlayControl = new Button(container, SWT.NONE);
		btn_setPlayControl.setBounds(244, 550, 72, 22);
		btn_setPlayControl.setText("����");
		
		btn_close = new Button(container, SWT.NONE);
		btn_close.setBounds(360, 550, 72, 22);
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

		
		//Ϊ��ťע�������
		btn_editPic.addSelectionListener(new ButtonSelectionListener());
		btn_close.addSelectionListener(new ButtonSelectionListener());
		btn_setPlayControl.addSelectionListener(new ButtonSelectionListener());
		
		queryDisplayNamesToCombo();
		
		//���ô�ǰһ��ҳ�洫�����Ĳ��ŷ���
		for(int i = 0; i < combo_playSolutionName.getItemCount(); i++){
			String itemString = combo_playSolutionName.getItem(i);
			if(itemString.equals(SetPlaySolution.playSolutionName)){
				combo_playSolutionName.select(i);
				break;
			}
		}
		
		//��ʼ�����ڸ�ѡ��
		btn_weekdays = new Button[7];
		for(int i = 0; i < btn_weekdays.length; i++){
			btn_weekdays[i] = new Button(composite_settings, SWT.CHECK);
			btn_weekdays[i].setSelection(true);
		}		
		
		btn_weekdays[0].setBounds(85, 95, 57, 16);
		btn_weekdays[0].setText("����һ");
		
		btn_weekdays[1].setText("���ڶ�");
		btn_weekdays[1].setBounds(141, 95, 57, 16);
		
		btn_weekdays[2].setText("������");
		btn_weekdays[2].setBounds(207, 95, 57, 16);
		
		btn_weekdays[3].setText("������");
		btn_weekdays[3].setBounds(273, 95, 57, 16);
		
		btn_weekdays[4].setText("������");
		btn_weekdays[4].setBounds(85, 119, 57, 16);
		
		btn_weekdays[5].setText("������");
		btn_weekdays[5].setBounds(141, 119, 57, 16);
		
		btn_weekdays[6].setText("������");
		btn_weekdays[6].setBounds(207, 119, 57, 16);
		
		//�������ݵ����ŷ�ʽ��Ͽ���
		String playStyles[] = new String[]{"��ͨ����", "��ʱ����"};
		combo_playType.setItems(playStyles);
		combo_playType.select(0);
		
		//����Ĭ�ϲ���ʱ����
		txt_playInteraval.setText(String.valueOf(1));
		
		//��ʼ�����ں�ʱ��
		dateTime_end.setYear(3000);
		dateTime_end.setMonth(7);
		dateTime_end.setDay(6);
		
		time_start.setHours(0);
		time_start.setMinutes(0);
		time_start.setSeconds(0);
		
		time_end.setHours(23);
		time_end.setMinutes(59);
		time_end.setSeconds(59);
		
		//��ʾ��������ͼƬ�����û�ѡ��
		addImageToChoose();
		//��ʾ���ŷ�����������ͼƬ
		addSc_picsChosed();
		
		//��Ӽ����¼�
		combo_display.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				queryDisplayNamesToCombo();
				//���¼��ز��ŷ��������ͼƬ
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
				//���¼��ز��ŷ��������ͼƬ
				addSc_picsChosed();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//��ʼ�������Ĳ˵�
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
	
	//�����ṩ��ʾ���ڵĽӿ�
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
		newShell.setText("���ò��ŷ���");
		String logoPath = curProjectPath + File.separator + "icons" + File.separator 
							+ "setting.png";
		newShell.setImage(new Image(Display.getDefault(), new ImageData(logoPath)));
		
		//��ȡ��ǰshell
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
				WordPicEditTool wordPicEditTool = WordPicEditTool.getWordPicEditTool();
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				String playSolutionName = combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex());
				String playSolutionPath = curProjectPath + File.separator + 
											displayName + File.separator + playSolutionName;
				//��ͼƬ�༭��������ʾ�����ƺͲ��ŷ�������
				WordPicEditTool.solutionPath = playSolutionPath;
				WordPicEditTool.playSolutionName = playSolutionName;
				WordPicEditTool.displayName = displayName;
				//�򲥷ſ�����Ϣ�����ݸ�����ͼƬ�༭��
				WordPicEditTool.playControl = getPlayControl();
				//��༭��ע��
				wordPicEditTool.addUpdatePlaySolution(curUpdatePlaySolution);
				wordPicEditTool.open();
			}
			else if(e.getSource() == btn_close){						//�رմ���
				curShell.dispose();			
			}
			else if(e.getSource() == btn_setPlayControl){				//���ð�ť
				String picPath = selectedPicture();
				
				if(picPath.length() == 0){								//û��ѡ��Ҫ���õ�ͼƬ
					MessageBox messageBox = new MessageBox(curShell);
					messageBox.setText("��ʾ");
					messageBox.setMessage("��ѡ��Ҫ���õ�ͼƬ!");
					messageBox.open();
					return;
				}
				//����ͼƬ������Ϣ
				updatePlayControl(picPath);
				
				//��ʾ���³ɹ�
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("��ʾ");
				messageBox.setMessage("���óɹ�!");
				messageBox.open();
				
			}
		}
		
	}
	
	/**
	 * ��ѯ���ŷ���������Ͽ���
	 */
	private void queryDisplayNamesToCombo(){
		//��ѯ��ʾ����Ӧ�Ĳ��ŷ���
		String displayName = combo_display.getItem(combo_display.getSelectionIndex());
		com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
		Set<PlaySolution> solutions = display.getSolutions();

		String solutionNams[] = new String[solutions.size()];
		int index = 0;
		for(PlaySolution playSolution : solutions){
			solutionNams[index++] = playSolution.getName();
		}
		
		//�󶨵����ŷ�����Ͽ���
		combo_playSolutionName.setItems(solutionNams);
		if(solutionNams.length > 0)
			combo_playSolutionName.select(0);
	}
	
	/**
	 * �����ݿ��ȡ����ͼƬ·�������ص���������У����û�ѡ��
	 */
	public void addImageToChoose(){
		Composite composite_pics = new Composite(sc_picsToChoose, SWT.NONE);
		sc_picsToChoose.setContent(composite_pics);
		//��ʼ��������岼�ֵ�ÿ����ʾ4��ͼƬ
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 4;
//		composite_pics.setLayout(gridLayout);
		
		//ͼƬ��Ⱥ͸߶�
		int compositeWidth = sc_picsToChoose.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		//�����ݿ��в�ѯ����ͼƬ��Ϣ
		List<Picture> listOfPics = pictureDao.queryAllPicture();
		
		//��ʾͼƬ�ı�ǩ
		final Label labels_pic[]= new Label[listOfPics.size()];
		
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.CENTER);
			//�����Ĳ˵�
			Menu contextMenu = new Menu(curShell, SWT.POP_UP);
			MenuItem addItem = new MenuItem(contextMenu, SWT.PUSH);
			addItem.setText("���");

			labels_pic[i].setMenu(contextMenu);
			//���ͼƬ��Ϣ��label��
			ImageData imageData = new ImageData(listOfPics.get(i).getPicPath());
			imageData = imageData.scaledTo(picWidth, picHeight);
			imageData = imageData.scaledTo(picWidth - 15, picHeight - 15);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			//����ͼƬ��С 
			labels_pic[i].setBounds((i % 4) * picWidth, (i / 4) * picHeight, picWidth, picHeight);
			
			//��ͼƬ·���ŵ�event.data��
			addItem.setData(listOfPics.get(i).getPicPath());
			
			//�����Ĳ˵���Ӽ�����
			addItem.addSelectionListener(new MenuItemListenerImpl());

			labels_pic[i].addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event e) {
					//�������ڻ�ý���
					sc_picsToChoose.setFocus();					
					//����ͼƬ����ɫ
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
	 * ��ʾ�Ѿ���ӵ����ŷ����е�ͼƬ
	 */
	public void addSc_picsChosed(){
		//�����һ�����ɵ�������
		Control controls_temp[] = sc_picsChosed.getChildren();
		if(controls_temp != null && controls_temp.length != 0){
			Composite temp = (Composite) sc_picsChosed.getChildren()[0];
			temp.dispose();			
		}
		
		//��ʼ���������µ�����ͼƬ
		Composite composite_pics = new Composite(sc_picsChosed, SWT.NONE);
		sc_picsChosed.setContent(composite_pics);
		
		sc_picsChosed.setExpandHorizontal(true);
		sc_picsChosed.setExpandVertical(true);
		
		//ע���ж��Ƿ��в��ŷ����ɼ���
		if(combo_display.getItemCount() == 0 || combo_playSolutionName.getItemCount() == 0)
			return;
		
		//��ʼ��������岼�ֵ�ÿ����ʾ4��ͼƬ
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 4;
//		gridLayout.makeColumnsEqualWidth = true;
		
//		composite_pics.setLayout(gridLayout);
		
		sc_picsChosed.setMinSize(sc_picsChosed.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		//ͼƬ��Ⱥ͸߶�
		int compositeWidth = sc_picsToChoose.getBounds().width;
		int picWidth = (compositeWidth / 4 - 8) ;
		int picHeight = 80;
		
		
		//�����ݿ��в�ѯ����ͼƬ��Ϣ
		//1.��ѯ��ʾ����Ϣ
		//2.��ѯ��ʾ����Ӧ�Ĳ��ŷ���
		//3.���ݲ��ŷ�����ѯ��Ӧ��ͼƬ
		//ע�ⲥ�ŷ�����������
		com.gxf.beans.Display display = displayDao.queryDisplayByName(combo_display.getItem(combo_display.getSelectionIndex()));
		Set<PlaySolution> setOfPlaySolution = display.getSolutions();
		PlaySolution playSolutionSelected = null;
		//�������ϣ��ҵ�ѡ�еĲ��ŷ���
		for(Iterator<PlaySolution> it_playSolution = setOfPlaySolution.iterator(); it_playSolution.hasNext();){
			playSolutionSelected = it_playSolution.next();
			if(playSolutionSelected.getName().equals(combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex()))){
				break;
			}
		}		
		List<Picture> listOfPics = new ArrayList<Picture>(playSolutionSelected.getPictures());
		//��ͼƬ������˳����������
		Collections.sort(listOfPics);
		
		//��ʾͼƬ�ı�ǩ
		final Label labels_pic[]= new Label[listOfPics.size()];
		
		for(int i = 0; i < labels_pic.length; i++){
			labels_pic[i] = new Label(composite_pics, SWT.CENTER);
			
			//�����Ĳ˵�
			Menu contextMenu = new Menu(curShell, SWT.POP_UP);

			MenuItem delItem = new MenuItem(contextMenu, SWT.PUSH);
			delItem.setText("ɾ��");
			
			MenuItem moveForward = new MenuItem(contextMenu, SWT.PUSH);
			moveForward.setText("ǰ��");
			
			MenuItem moveBack = new MenuItem(contextMenu, SWT.PUSH);
			moveBack.setText("����");
			
			labels_pic[i].setMenu(contextMenu);
			
			//�ڱ�ǩ����ʾͼƬ
			ImageData imageData = new ImageData(listOfPics.get(i).getPicPath());
			labels_pic[i].setSize(picWidth, picHeight);
			imageData = imageData.scaledTo(picWidth - 15, picHeight - 15);
			Image image = new Image(Display.getDefault(), imageData);
			labels_pic[i].setImage(image);
			labels_pic[i].setBounds((i % 4) * picWidth, (i / 4) * picHeight, picWidth, picHeight);

			//��ͼƬ·���ŵ�event.data��
			delItem.setData(listOfPics.get(i).getPicPath());
			moveForward.setData(listOfPics.get(i));
			moveBack.setData(listOfPics.get(i));
			
			//�����Ĳ˵���Ӽ�����
			delItem.addSelectionListener(new MenuItemListenerImpl());
			moveForward.addSelectionListener(new MenuItemListenerImpl());
			moveBack.addSelectionListener(new MenuItemListenerImpl());
			
			//��ͼƬ·���ŵ�label.data��
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
					
					//��ȡͼƬ����Ͳ��ſ�����Ϣ
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
	 * MenuItem������
	 * @author Administrator
	 *
	 */
	class MenuItemListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.getText() == "���"){						//���ͼƬ�����ŷ�����
				//1.�����ļ������ŷ����ļ�����
				//2.�����ݿ���д��ͼƬ��Ϣ��������Ϣ
				//3.������ʾ
				String scrFilePath = (String) item.getData();
				String curTime = util.getCurTime();
				String playSolutionName = combo_playSolutionName.getItem(combo_playSolutionName.getSelectionIndex());
				String displayName = combo_display.getItem(combo_display.getSelectionIndex());
				//����ͼƬ�����·��
				String relativePath = displayName + File.separator + playSolutionName
						+ File.separator + curTime + ".bmp";
				//ͼƬ����·��
				String dstFilePath = curProjectPath + File.separator + relativePath;
				
				//�����ļ�
				util.copyFile(scrFilePath, dstFilePath);
				
				//ͼƬ��Ϣд�뵽���ݿ���
				PlaySolution playSolution = playSolutionDao.querySolutionByName(playSolutionName);
				Picture picture = new Picture();
				picture.setPicName(curTime + ".bmp");
				picture.setPicPath(relativePath);
				picture.setPlaySolution(playSolution);
				
				//��ȡ������沥�ſ�����Ϣ
				PlayControl playControl = getPlayControl();
				picture.setPlayControl(playControl);
				
				pictureDao.addPicture(picture);
				
				//������ʾ
				addSc_picsChosed();
				
				//��ʾ�û���ӳɹ�
				MessageBox messageBox = new MessageBox(curShell);
				messageBox.setText("��ʾ");
				messageBox.setMessage("���ͼƬ�ɹ�!");
				messageBox.open();
			}
			else if(item.getText() == "ɾ��"){				//�Ӳ��ŷ����к�ɾ��ͼƬ
				//1.�����ݿ���ɾ������
				//2.ɾ���ļ�
				//3.������ʾ
				//�����ݿ���ɾ������
				String picPath = (String) item.getData();
				pictureDao.deletePictureByPicPath(picPath);
				
				//ɾ���ļ�
				File picFile = new File(picPath);
				picFile.delete();
				
				//������ʾ��������嶼Ҫ����
				addImageToChoose();
				addSc_picsChosed();
			}
			else if(item.getText().equals("ǰ��")){			//ͼƬǰ�ƣ�������˳��ǰ��
				//��ȡ��ǰͼƬ����˳��
				//1.����ǵ�һ�����ţ����أ���������
				//2.������ǵ�һ������˳�򣬲���˳���һ��ǰһ��ͼƬ����˳���һ
				Picture curPicture = (Picture) item.getData();
				int playOrder = curPicture.getPlayOrder();
				
				//1.ͼƬ�ǵ�һ�Ų���˳��
				if(playOrder == 1)
					return;
				//2.���ǵ�һ�Ų��ŵ�ͼƬ
				Picture prePic = pictureDao.queryPicByPlayOrder(playOrder - 1);
				prePic.setPlayOrder(playOrder);
				
				curPicture.setPlayOrder(playOrder - 1);
				
				pictureDao.updatePicture(curPicture);
				pictureDao.updatePicture(prePic);
				
				//������ʾ
				addSc_picsChosed();
				
			}
			else if(item.getText().equals("����")){			//ͼƬ���ƣ�������˳�����
				//��ȡ��ǰͼƬ����˳��
				//1.��������һ��ͼƬ�����أ���������
				//2.����������һ��ͼƬ������˳���һ����һ��ͼƬ����˳���һ
				Picture curPicture = (Picture) item.getData();
				int playOrder = curPicture.getPlayOrder();
				
				//1.��󲥷ŵ�һ��ͼƬ
				int picCount = pictureDao.queryPictureCount();
				if(picCount == (playOrder - 1))
					return;
				
				//2.������󲥷ŵ�һ��ͼƬ
				Picture behindPic = pictureDao.queryPicByPlayOrder(playOrder + 1);
				behindPic.setPlayOrder(playOrder);
				
				curPicture.setPlayOrder(playOrder + 1);
				
				pictureDao.updatePicture(curPicture);
				pictureDao.updatePicture(behindPic);
				
				//������ʾ
				addSc_picsChosed();
			}
		}
		
	}
	
	/**
	 * ��ȡ���������Ʋ�����Ϣ
	 * @return
	 */
	private PlayControl getPlayControl(){
		PlayControl playControl = new PlayControl();
		String playType = combo_playType.getItem(combo_playType.getSelectionIndex());
		if(playType.equals("��ͨ����"))
			playControl.setPlayType(1);
		else
			playControl.setPlayType(2);
		int timeInterval = Integer.valueOf(txt_playInteraval.getText());
		playControl.setTimeInterval(timeInterval);
		
		//���ÿ�ʼ�������� ��ʽ yyyy-mm-dd
		int year = this.dateTime_start.getYear();
		int month = this.dateTime_start.getMonth();
		int day = this.dateTime_start.getDay();
		
		String dateStr = year + "-" + month + "-" + day;
		playControl.setDateTimeStart(Date.valueOf(dateStr));
		
		//���ý�����������
		year = this.dateTime_end.getYear();
		month = this.dateTime_end.getMonth();
		day = this.dateTime_end.getDay();
		
		dateStr = year + "-" + month + "-" + day;
		playControl.setDateTimeEnd(Date.valueOf(dateStr));
		
		//���ÿ�ʼ����ʱ�� ��ʽhh:mm:ss
		int hour = this.time_start.getHours();
		int minute = this.time_start.getMinutes();
		int second = this.time_start.getSeconds();
		
		String timeStr = hour + ":" + minute + ":" + second;
		playControl.setTimeStart(Time.valueOf(timeStr));
		
		//���ý�������ʱ��
		hour = this.time_end.getHours();
		minute = this.time_end.getMinutes();
		second = this.time_end.getSeconds();
		
		timeStr = hour + ":" + minute + ":" + second;
		playControl.setTimeEnd(Time.valueOf(timeStr));
		
		//��ȡ��������
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
	 * ��ʾÿ��ͼƬ�Ŀ��Ʋ�����Ϣ
	 * @param playControl
	 */
	private void showDisplayControl(PlayControl playControl){
		//ˢ�²��ŷ�ʽ��ʱ����
		combo_playType.select(playControl.getPlayType() - 1);
		txt_playInteraval.setText(String.valueOf(playControl.getTimeInterval()));
		//ˢ�²��ſ�ʼ����
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(playControl.getDateTimeStart());
		dateTime_start.setYear(calendar.get(Calendar.YEAR));
		dateTime_start.setMonth(calendar.get(Calendar.MONTH + 1));
		dateTime_start.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		
		//���Ž�������
		calendar.setTime(playControl.getDateTimeEnd());
		dateTime_end.setYear(calendar.get(Calendar.YEAR));
		dateTime_end.setMonth(calendar.get(Calendar.MONTH + 1));
		dateTime_end.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		
		//���ſ�ʼʱ��
		calendar.setTime(playControl.getTimeStart());
		time_start.setHours(calendar.get(Calendar.HOUR_OF_DAY));
		time_start.setMinutes(calendar.get(Calendar.MINUTE));
		time_start.setSeconds(calendar.get(Calendar.SECOND));
		
		//���Ž���ʱ��
		calendar.setTime(playControl.getTimeEnd());
		time_end.setHours(calendar.get(Calendar.HOUR_OF_DAY));
		time_end.setMinutes(calendar.get(Calendar.MINUTE));
		time_end.setSeconds(calendar.get(Calendar.SECOND));
		
		//��������
		String weekdays = playControl.getWeekdays();
		for(int i = 0; i < weekdays.length(); i++){
			if(weekdays.charAt(i) == '1')
				btn_weekdays[i].setSelection(true);
			else
				btn_weekdays[i].setSelection(false);
		}
	}
	
	/**
	 * ��ѯ��ѡ��ͼƬ��picPath
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
	 * ����ͼƬ�Ĳ��ſ�����Ϣ
	 * @param picPath
	 */
	private void updatePlayControl(String picPath){
		//��ȡ������������
		PlayControl playControlNew = getPlayControl();
		//��ȡ��ǰ�ĵ����ã������ݿ��ж�ȡ
		Picture picture = pictureDao.queryByPicPath(picPath);
		PlayControl playControlOld = picture.getPlayControl();
		//���µĿ�����Ϣcopy���ɵ�����
		util.copyPlayControl(playControlNew, playControlOld);
		
		//�������ݿ��е�����
		pictureDao.updatePicture(picture);
	}
	
	/**
	 * ���ص���ģʽʵ��
	 * @return
	 */
	public static SetPlaySolution getSetPlaySolution(){
		return setPlaySolution;
	}
}
