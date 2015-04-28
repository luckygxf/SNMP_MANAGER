package com.gxf.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.gxf.beans.Communication;
import com.gxf.composite.WordPicEditTool.ButtonSelectionListenerImp;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.util.Util;


public class HostListComposite extends Composite {
	//����ϵĿؼ�
	private static Table table_display;
	private Util util = new Util();
	private String curProjectPath = util.getCurrentProjectPath();
	private Display defaultDisplay = Display.getDefault();
	
	private ToolItem tltm_add;
	private ToolItem tltm_del;
	private ToolItem tltm_moveDown;
	private ToolItem tltm_moveUp;
	private ToolItem tltm_save;
	
	//��ʾ��DAO
	private static DisplayDao displayDao = new DisplayDaoImpl();
	//��ʾ���б������ݿ��ж�ȡ������
	
	private static List<com.gxf.beans.Display> listOfDisplay = new ArrayList<com.gxf.beans.Display>();
	
	public HostListComposite(Composite parent, int style) {
		super(parent, style);
		
		ViewForm viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setTopCenterSeparate(true);
		viewForm.setBounds(10, 10, 580, 413);
		
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT );
		viewForm.setTopLeft(toolBar);
		
		//���
		tltm_add = new ToolItem(toolBar, SWT.NONE);
		tltm_add.setText("���");
		//����ͼ��
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "addDisplayIcon.png";		
		tltm_add.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_del = new ToolItem(toolBar, SWT.NONE);
		tltm_del.setText("ɾ��");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "delDisplayIcon.png";		
		tltm_del.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_moveDown = new ToolItem(toolBar, SWT.NONE);
		tltm_moveDown.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveDown.png";		
		tltm_moveDown.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_moveUp = new ToolItem(toolBar, SWT.NONE);
		tltm_moveUp.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveUp.png";		
		tltm_moveUp.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		tltm_save = new ToolItem(toolBar, SWT.NONE);
		tltm_save.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "saveDisplay.png";		
		tltm_save.setImage(new Image(defaultDisplay, new ImageData(iconPath)));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(viewForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		viewForm.setContent(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		
		table_display = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.CENTER | SWT.CHECK);
		table_display.setBounds(0, 0, 566, 379);
		table_display.setHeaderVisible(true);
		table_display.setLinesVisible(true);
		
		TableColumn tblclmn_id = new TableColumn(table_display, SWT.NONE);
		tblclmn_id.setWidth(45);
		tblclmn_id.setText("���");
		
		TableColumn tblclmn_name = new TableColumn(table_display, SWT.NONE);
		tblclmn_name.setWidth(87);
		tblclmn_name.setText("����");
		
		TableColumn tblclmn_displayType = new TableColumn(table_display, SWT.NONE);
		tblclmn_displayType.setWidth(82);
		tblclmn_displayType.setText("����");
		
		TableColumn tblclmn_communicationType = new TableColumn(table_display, SWT.NONE);
		tblclmn_communicationType.setWidth(78);
		tblclmn_communicationType.setText("ͨ�ŷ�ʽ");
		
		TableColumn tblclmn_ip = new TableColumn(table_display, SWT.NONE);
		tblclmn_ip.setWidth(112);
		tblclmn_ip.setText("ip");
		
		TableColumn tblclmn_port = new TableColumn(table_display, SWT.NONE);
		tblclmn_port.setWidth(62);
		tblclmn_port.setText("�˿ں�");
		
		TableColumn tblclmn_comment = new TableColumn(table_display, SWT.NONE);
		tblclmn_comment.setWidth(135);
		tblclmn_comment.setText("\u8BF4\u660E");
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));	
		
		//��ʼ������
		init();
	}
	
	/**
	 * �Կؼ����г�ʼ������
	 */
	public void init(){
		//�������ݵ�table��
		refreshTable();
		
		//����ע�������
		tltm_moveDown.addSelectionListener(new ButtonListener());
		tltm_moveUp.addSelectionListener(new ButtonListener());
		//���ӱ����
		table_display.addListener(SWT.MeasureItem,  new Listener(){

			@Override
			public void handleEvent(Event event) {
				event.height = event.gc.getFontMetrics().getHeight() + 10;
				
			}
			
		});
		
		//��Ӽ�����
		tltm_add.addSelectionListener(new ButtonSelectionListener());
		tltm_del.addSelectionListener(new ButtonSelectionListener());
	}
	
	/**
	 * ����������
	 * @author Administrator
	 *
	 */
	class ButtonListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == tltm_moveUp){								//����
				int selectionIndex = table_display.getSelectionIndex();
				if(selectionIndex > 0)
					table_display.setSelection(selectionIndex - 1);
			}
			else if(e.getSource() == tltm_moveDown){						//����
				int selectionIndex = table_display.getSelectionIndex();
				if(selectionIndex > -1 && selectionIndex < table_display.getItemCount() - 1){
					table_display.setSelection(selectionIndex + 1);
				}
			}
			
		}
		
	}
	
	/**
	 * ���ñ�����ݿ��Ա༭
	 */
	private static void makeTableEditable(){
		//��ȡ���������
		TableItem tableItems[] = table_display.getItems();
		
		for(int i = 0; i < tableItems.length; i++){
			int columNums = 1;
			//��������
			final Text txt_displayName = new Text(table_display, SWT.NONE);
			final TableEditor editor_displayName = new TableEditor(table_display);
			editor_displayName.grabHorizontal = true;
			txt_displayName.setText(tableItems[i].getText(columNums));
			editor_displayName.setEditor(txt_displayName, tableItems[i], columNums);
			txt_displayName.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_displayName.getItem().setText(1, txt_displayName.getText());
					
				}
			});
			columNums++;
			//��������
			final Text txt_displayType = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayType = new TableEditor(table_display);
			editor_diplayType.grabHorizontal = true;
			txt_displayType.setText(tableItems[i].getText(columNums));
			editor_diplayType.setEditor(txt_displayType, tableItems[i], columNums);
			txt_displayType.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayType.getItem().setText(2, txt_displayType.getText());
					
				}
			});
			columNums++;
			
			//����ͨ�ŷ�ʽ
			final Combo combo_communityType = new Combo(table_display, SWT.NONE);
			combo_communityType.add("����ͨ��");
			combo_communityType.add("����ͨ��");
			final TableEditor editor_communityType = new TableEditor(table_display);
			combo_communityType.setText(tableItems[i].getText(columNums));
			editor_communityType.setEditor(combo_communityType, tableItems[i], columNums);
			editor_communityType.grabHorizontal = true;
			combo_communityType.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_communityType.getItem().setText(3, 
							combo_communityType.getItem(combo_communityType.getSelectionIndex()));
					
				}
			});
			columNums++;
			
			//����ip
			final Text txt_displayIp = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayIp = new TableEditor(table_display);
			editor_diplayIp.grabHorizontal = true;
			txt_displayIp.setText(tableItems[i].getText(columNums));
			editor_diplayIp.setEditor(txt_displayIp, tableItems[i], columNums);
			txt_displayIp.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayIp.getItem().setText(4, txt_displayIp.getText());
					
				}
			});
			columNums++;
			
			//���ö˿ں�
			final Text txt_displayPort = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayPort = new TableEditor(table_display);
			editor_diplayPort.grabHorizontal = true;
			txt_displayPort.setText(tableItems[i].getText(columNums));
			editor_diplayPort.setEditor(txt_displayPort, tableItems[i], columNums);
			txt_displayPort.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayPort.getItem().setText(5, txt_displayPort.getText());
					
				}
			});
			columNums++;
			
			//����˵��
			final Text txt_displayComment = new Text(table_display, SWT.NONE);
			final TableEditor editor_diplayComment = new TableEditor(table_display);
			editor_diplayComment.grabHorizontal = true;
			txt_displayComment.setText(tableItems[i].getText(columNums));
			editor_diplayComment.setEditor(txt_displayComment, tableItems[i], columNums);
			txt_displayComment.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent arg0) {
					editor_diplayComment.getItem().setText(6, txt_displayComment.getText());
					
				}
			});
		}
	}
	private void deleteDisplay(){
		
	}
	
	/**
	 * �����ʾ����Ϣ
	 */
	private void addDisplay(){
		AddDisplay addDisplay = new AddDisplay();
		addDisplay.show();
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
			if(e.getSource() == tltm_add){							//�����ʾ��
				addDisplay();
			}
			
		}
		
	}
	
	/**
	 * ˢ�±������
	 */
	public static void refreshTable(){
		listOfDisplay = displayDao.queryAllDisplay();

		//��table���������
		for(com.gxf.beans.Display display : listOfDisplay){
			Communication communication = display.getCommunication();
			
			TableItem item = new TableItem(table_display, SWT.NONE);
			String temp[] = new String[7];
			temp[0] = String.valueOf(display.getId());
			temp[1] = display.getName();
			temp[2] = display.getType();
			temp[3] = communication.getName();
			temp[4] = communication.getIp();
			temp[5] = String.valueOf(communication.getPort());
			temp[6] = display.getComment();
			
			item.setText(temp);
		}
		
		//�����Ա༭
		makeTableEditable();
	}
	
	/**
	 * ���������һ����¼
	 * @param display
	 */
	public static void addTableItem(com.gxf.beans.Display display){
		Communication communication = display.getCommunication();
		
		TableItem item = new TableItem(table_display, SWT.NONE);
		String temp[] = new String[7];
		temp[0] = String.valueOf(display.getId());
		temp[1] = display.getName();
		temp[2] = display.getType();
		temp[3] = communication.getName();
		temp[4] = communication.getIp();
		temp[5] = String.valueOf(communication.getPort());
		temp[6] = display.getComment();
		
		item.setText(temp);
		
		//�����Ա༭
		makeTableEditable();
	}
}
