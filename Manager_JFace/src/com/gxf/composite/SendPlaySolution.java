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
	
	//���ֿؼ�
	private Label lb_showPlayStyle;
	private Text txt_timeInterval;
	private Button btn_sendSoluton;
	private Button btn_weekdays[];
	private List list_hostList;
	
	//�������ڿ���
	private DateTime dateTime_start;
	private DateTime dateTime_end;
	//����ʱ�����
	private DateTime time_start;
	private DateTime time_end;	
	//���ŷ�ʽ
	private Combo combo_playStyle;
	private Combo combo_solutions;
	
	//������
	private Util util = new Util();
	private SendPic sendPic = new SendPic();
	
	//��ǰshell
	public static Shell curShell;
	
	//������в��ŷ������ļ���
//	private final String DIC_NAME_PLAY_SOLUTIONS = "playSolutions";
	//��ʾѡ�в��ŷ�������ͼƬ
	private ScrolledComposite scrolledComposite_pics;
	private Label lb_chooseDisplay;
	private Combo combo_display;
//	private Composite composite_pics;
//	private Label labels_pic[];
	
	//���ݿ������
	private DisplayDao displayDao =  new DisplayDaoImpl();
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	
	public SendPlaySolution(Composite parent, int style) {
		super(parent, style);
		//��ȡ��ǰshell
		curShell = parent.getShell();
		
		//��ʾ���ŷ�ʽ��ǩ
//		lb_showPlayStyle = new Label(this, SWT.NONE);
//		lb_showPlayStyle.setBounds(10, 227, 54, 12);
//		lb_showPlayStyle.setText("���ŷ�ʽ");
//		
//		combo_playStyle = new Combo(this, SWT.NONE);
//		combo_playStyle.setBounds(109, 219, 86, 20);
//		
//		//��ʾ����ʱ������ǩ
//		Label lb_timeInterval = new Label(this, SWT.NONE);
//		lb_timeInterval.setBounds(241, 227, 82, 12);
//		lb_timeInterval.setText("����ʱ����");
//		
//		//����ʱ���������ı���,Ĭ��Ϊ1s
//		txt_timeInterval = new Text(this, SWT.BORDER);
//		txt_timeInterval.setBounds(329, 224, 35, 18);
//		txt_timeInterval.setText(String.valueOf(1));
//		
//		//��ʾ���ǩ
//		Label lb_second = new Label(this, SWT.NONE);
//		lb_second.setBounds(370, 227, 54, 12);
//		lb_second.setText("��");
//		
//		//��ʾ�������ڱ�ǩ
//		Label lb_playDate = new Label(this, SWT.NONE);
//		lb_playDate.setBounds(10, 254, 54, 12);
//		lb_playDate.setText("��������");
//		
//		//��ʾ����ǩ
//		Label lb_dateTo = new Label(this, SWT.NONE);
//		lb_dateTo.setBounds(241, 254, 54, 12);
//		lb_dateTo.setText("��");
//		
//		//��ʾ����ʱ���ǩ
//		Label lb_playTime = new Label(this, SWT.NONE);
//		lb_playTime.setBounds(10, 287, 54, 12);
//		lb_playTime.setText("����ʱ��");
//		
//		//��ʾ����ǩ
//		Label lb_timeTo = new Label(this, SWT.NONE);
//		lb_timeTo.setBounds(241, 287, 54, 12);
//		lb_timeTo.setText("��");
//		
//		Label lb_week = new Label(this, SWT.NONE);
//		lb_week.setBounds(10, 315, 54, 12);
//		lb_week.setText("����");
//		
//		//��ʼ�����ڸ�ѡ��
//		btn_weekdays = new Button[7];
//		for(int i = 0; i < btn_weekdays.length; i++){
//			btn_weekdays[i] = new Button(this, SWT.CHECK);
//		}		
//		
//		btn_weekdays[0].setBounds(109, 315, 57, 16);
//		btn_weekdays[0].setText("����һ");
//		
//		btn_weekdays[1].setText("���ڶ�");
//		btn_weekdays[1].setBounds(175, 315, 57, 16);
//
//		btn_weekdays[2].setText("������");
//		btn_weekdays[2].setBounds(238, 315, 57, 16);
//		
//		btn_weekdays[3].setText("������");
//		btn_weekdays[3].setBounds(301, 315, 57, 16);
//		
//		btn_weekdays[4].setText("������");
//		btn_weekdays[4].setBounds(109, 339, 57, 16);
//		
//		btn_weekdays[5].setText("������");
//		btn_weekdays[5].setBounds(175, 339, 57, 16);
//		
//		btn_weekdays[6].setText("������");
//		btn_weekdays[6].setBounds(238, 339, 57, 16);
//		
//		Label lb_hostList = new Label(this, SWT.NONE);
//		lb_hostList.setBounds(10, 362, 54, 12);
//		lb_hostList.setText("�����б�");
		
		//�����б�ؼ�
//		list_hostList = new List(this, SWT.BORDER);
//		list_hostList.setBounds(109, 357, 147, 68);
		
		btn_sendSoluton = new Button(this, SWT.NONE);
		btn_sendSoluton.setBounds(244, 451, 72, 22);
		btn_sendSoluton.setText("���Ͳ��ŷ���");
		
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
		
		//��ʾ���ŷ���
		Label lb_chooseSolution = new Label(this, SWT.NONE);
		lb_chooseSolution.setBounds(275, 9, 54, 12);
		lb_chooseSolution.setText("���ŷ���");
		
		//��ʾ���ŷ���
		combo_solutions = new Combo(this, SWT.NONE);
		combo_solutions.setBounds(374, 6, 143, 20);
		
		scrolledComposite_pics = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_pics.setBounds(0, 27, 590, 414);
		
		lb_chooseDisplay = new Label(this, SWT.NONE);
		lb_chooseDisplay.setBounds(22, 9, 54, 12);
		lb_chooseDisplay.setText("��ʾ��");
		
		combo_display = new Combo(this, SWT.NONE);
		combo_display.setBounds(109, 6, 143, 20);
		
//		scrolledComposite_pics.setLayout(new FillLayout());
//		scrolledComposite_pics.setExpandHorizontal(true);
//		scrolledComposite_pics.setExpandVertical(true);
		
		
		//�Կؼ����г�ʼ��
		init();
	}
	
	/**
	 * ��ʼ���ؼ�
	 */
	public void init(){
		//��ʼ�����ŷ�ʽ�ؼ�����
//		String playStyles[] = new String[]{"��ͨ����", "��ʱ����"};
//		combo_playStyle.setItems(playStyles);
//		combo_playStyle.select(0);
		btn_sendSoluton.addSelectionListener(new ButtonListenerImpl());
		
		//��ʼ����ʾ��
		String displayNames[] = getDisplayNames();
		if(displayNames.length != 0){
			combo_display.setItems(displayNames);			
			combo_display.select(0);
		}
		
		//��ʼ�����ŷ���
		String playSolutions[] = getSolutions();
		if(playSolutions != null && playSolutions.length != 0){
			combo_solutions.setItems(playSolutions);			
			combo_solutions.select(0);
		}
		
		//��ʼ�����ں�ʱ��
//		dateTime_start.setYear(1990);
//		dateTime_start.setMonth(7);
//		dateTime_start.setDay(6);
//		
//		time_start.setHours(0);
//		time_start.setMinutes(0);
//		time_start.setSeconds(0);				

		//����ͼƬ���������
		addPicsToScrollComposite();
		//��ʾ��combo��Ӽ�����
		combo_display.addSelectionListener(new ComboSelectionListener());
		//���ŷ���list��Ӽ�����
		combo_solutions.addSelectionListener(new ComboSelectionListener());
		
		//��ʼ�������б�
//		if(AllIP.IPS == null || AllIP.IPS.size() == 0)
//			new AllIP().getAllIP();
//		for(int i = 0; i < AllIP.IPS.size(); i++){
//			list_hostList.add(AllIP.IPS.get(i));
//		}
		
		//����ˢ�������Ĳ˵�
		Menu contextMenu = new Menu(curShell, SWT.TOP);
		MenuItem freshItem = new MenuItem(contextMenu, SWT.None);
		freshItem.setText("ˢ��");
		
		scrolledComposite_pics.setMenu(contextMenu);
		freshItem.addSelectionListener(new MenuItemSelectionImpl());
		
	}
	
	/**
	 * ��������¼�
	 * @author Administrator
	 *
	 */
	class ButtonListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == btn_sendSoluton){					//�������ͷ���
				sendPlaySolution();
			}
			
		}
		
	}
	
	/**
	 * ��ȡ�û�������
	 * @return
	 */
	public Config getConfig(){
		Config config = new Config();	
		String playStyle = combo_playStyle.getItem(combo_playStyle.getSelectionIndex());
		if(playStyle.equals("��ͨ����"))
			config.setPlayStyle(0);														//0--��ͨ����
		else
			config.setPlayStyle(1);
		config.setPlayTimeInterval(Integer.parseInt(txt_timeInterval.getText()));		//���ò���ʱ����
		//���ÿ�ʼ��������
		config.setYear_start(this.dateTime_start.getYear());
		config.setMonth_start(this.dateTime_start.getMonth());
		config.setDay_start(this.dateTime_start.getDay());
		//���ý�����������
		config.setYear_end(this.dateTime_end.getYear());
		config.setMonth_end(this.dateTime_end.getMonth());
		config.setDay_end(this.dateTime_end.getDay());
		//���ÿ�ʼ����ʱ��
		config.setHour_start(this.time_start.getHours());
		config.setMin_start(this.time_start.getMinutes());
		config.setSec_start(this.time_start.getSeconds());
		//���ý�������ʱ��
		config.setHour_end(this.time_end.getHours());
		config.setMin_end(this.time_end.getMinutes());
		config.setSec_end(this.time_end.getSeconds());
		//��ȡ����һ��������
		boolean weekdays[] = config.getWeekdays();
		for(int i = 0; i < weekdays.length; i++){
			weekdays[i] = btn_weekdays[i].getSelection();
		}
		
		return config;
	}
	
	/**
	 * ����config����config.xml
	 * @param config
	 */
//	public void createConfigXml(String solutionName, Config config){
////		util.createConfigXml(solutionName, config);
//	}
	
	/**
	 * ���Ͳ��ŷ���
	 */
	public void sendPlaySolution(){
		//���Ͳ��ŷ�������
		//0.�ж�Զ�������Ƿ�ɴ�
		//1.����xml�����ļ�
		//2.ѹ��ͼƬ��xml�ļ�
		//3.����ѹ���ļ�
		//4.���õ�ǰ���ŷ�����д�����ݿ� 		
		
		//��ȡ��Ӧ����ʾ���Ͳ��ŷ���ʵ��
		com.gxf.beans.Display display = displayDao.queryDisplayByName(combo_display.getItem(combo_display.getSelectionIndex()));
		
		//Ŀ������IP
		String ip = display.getCommunication().getIp();
		
		//���Զ���������ɴ�
		if(!util.validConnection(ip, 16202)){
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("��ʾ");
			messageBox.setMessage("Զ������û�п���������û����������Ӧ�ó���!");
			messageBox.open();
			return ;
		}

		//���û�п��Է��͵Ĳ��ŷ���
		if(combo_display.getItemCount() == 0|| combo_solutions.getItemCount() == 0)
		{
			MessageBox messageBox = new MessageBox(curShell);
			messageBox.setText("��ʾ");
			messageBox.setMessage("û�п��Է��͵Ĳ��ŷ���!");
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
		
		
		//����xml�����ļ�
		util.createConfigXml(display, playSolution);
		//ѹ��ͼƬ
		util.compressPlaySoution(display.getName(), playSolution.getName());
		//���ͽ������
		String solutionPath = util.getCurrentProjectPath() + File.separator + display.getName() 
				+ File.separator + display.getName() +  "+" + playSolution.getName() + ".zip";
		System.out.println("ip = " + ip);
		
		sendPic.send(ip, solutionPath);
		//���õ�ǰ���ŷ�����д�뵽���ݿ���
		displayDao.updateCurPlaySolution(display.getName(), playSolution.getName());
		//��ʾ���ͳɹ�
		MessageBox messageBox = new MessageBox(curShell);
		messageBox.setText("���ͷ���");
		messageBox.setMessage("���Ͳ��ŷ����ɹ�!");
		messageBox.open();
	}
	
	/**
	 * ��ȡ���еĲ��ŷ���
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
	 * ���ز��ŷ�����ͼƬ�����������
	 * @param solutionName
	 */
	public void addPicsToScrollComposite(){
		//���û�в��ŷ���ֱ�ӷ���
		Control controls[] = scrolledComposite_pics.getChildren();
		
		if(controls != null && controls.length != 0)
			controls[0].dispose();
		
		if(combo_display.getItemCount() == 0 || combo_solutions.getItemCount() == 0)
			return;
		
		
		//��ȡѡ�е���ʾ���Ͳ��ŷ�����
		String displayName = combo_display.getItem(combo_display.getSelectionIndex());
		String playSolutionName = combo_solutions.getItem(combo_solutions.getSelectionIndex());
		
		//��ʾ���ŷ�����ͼƬ
		Composite composite_pics = new Composite(scrolledComposite_pics, SWT.NONE);
		scrolledComposite_pics.setContent(composite_pics);
		//��ʼ��������岼�ֵ�ÿ����ʾ4��ͼƬ
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		composite_pics.setLayout(gridLayout);
		
		String curProjectPath = util.getCurrentProjectPath();
		String solutionPath = curProjectPath + File.separator + displayName 
							+ File.separator  + playSolutionName;
		File solutionPathFile = new File(solutionPath);
		//��ȡ����ͼƬ�ļ�
		File pics[] = solutionPathFile.listFiles(new PicFilenameFilter());
		int compositeWidth = scrolledComposite_pics.getBounds().width;
		//ͼƬ��Ⱥ͸߶�
		int picWidth = compositeWidth / 4 - 8;
		int picHeight = 100;
		String picPath[] = new String[pics.length];
		//��ʾͼƬ�ı�ǩ
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
	 * �����ݿ��ж�ȡ��ʾ������
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
	 * �б������
	 * @author Administrator
	 *
	 */
	class ComboSelectionListener extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == combo_display){														//��ʾ���ı�
				//���û����ʾ����Ϣ�����ŷ���Ӧ����Ϊ��
				if(combo_display.getItemCount() == 0)
				{
					combo_solutions.removeAll();
					addPicsToScrollComposite();
					return;
				}
				//��ȡ��ʾ������
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
				
				//ˢ��ͼƬ��ʾ
				addPicsToScrollComposite();
			}
			else if(e.getSource() == combo_solutions){												//���ŷ����ı�
				//ˢ��ͼƬ��ʾ
				addPicsToScrollComposite();
			}
		}
		
	}
	
	/**
	 * �˵�ѡ�������
	 * @author Administrator
	 *
	 */
	class MenuItemSelectionImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.getText().equals("ˢ��")){								//ˢ��
				init();
			}
		}
		
	}
}
