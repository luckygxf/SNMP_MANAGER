package com.gxf.composite;

import java.io.File;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TableColumn;

import com.gxf.beans.PlaySolution;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.Util;
import org.eclipse.swt.custom.ScrolledComposite;

/**
 * ���ŷ����������
 * @author Administrator
 *
 */
public class PlaySolutionComposite extends Composite {
	//����Ͽؼ�
	private static Table table_playSolution;	
	private ToolItem tltm_add;
	private ToolItem tltm_moveDown;
	private ToolItem tltm_moveUp;
	private ToolItem tltm_del;
	
	//������
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	
	//���ݿ������
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	private static DisplayDao displayDao = new DisplayDaoImpl();
	
	public PlaySolutionComposite(Composite parent, int style) {
		super(parent, style);
		
		ViewForm viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setBounds(10, 10, 592, 453);
		
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
		viewForm.setTopLeft(toolBar);
		//���
		tltm_add = new ToolItem(toolBar, SWT.NONE);
		tltm_add.setText("���");
		//����ͼ��
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "addDisplayIcon.png";		
		tltm_add.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//ɾ��
		tltm_del = new ToolItem(toolBar, SWT.NONE);
		tltm_del.setText("ɾ��");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "delDisplayIcon.png";		
		tltm_del.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//����
		tltm_moveDown = new ToolItem(toolBar, SWT.NONE);
		tltm_moveDown.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveDown.png";		
		tltm_moveDown.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//����
		tltm_moveUp = new ToolItem(toolBar, SWT.NONE);
		tltm_moveUp.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveUp.png";		
		tltm_moveUp.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//����
		ToolItem tltm_save = new ToolItem(toolBar, SWT.NONE);
		tltm_save.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "saveDisplay.png";		
		tltm_save.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		Composite composite = new Composite(viewForm, SWT.NONE);
		viewForm.setContent(composite);
				
		table_playSolution = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
		table_playSolution.setLocation(0, 0);
		table_playSolution.setSize(589, 431);
		table_playSolution.setHeaderVisible(true);
		table_playSolution.setLinesVisible(true);
		
		TableColumn tblclmn_nums = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_nums.setWidth(40);
		tblclmn_nums.setText("���");
		
		TableColumn tblclmn_displaySolutionName = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_displaySolutionName.setWidth(81);
		tblclmn_displaySolutionName.setText("���ŷ�����");
		
		TableColumn tblclmn_displayName = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_displayName.setWidth(76);
		tblclmn_displayName.setText("��ʾ������");
		
		TableColumn tblclmn_createTime = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_createTime.setWidth(76);
		tblclmn_createTime.setText("����ʱ��");
		
		TableColumn tblclmn_updateCount = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_updateCount.setWidth(62);
		tblclmn_updateCount.setText("�޸Ĵ���");
		
		TableColumn tblclmn_lastUpdateTime = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_lastUpdateTime.setWidth(86);
		tblclmn_lastUpdateTime.setText("����޸�");
		
		TableColumn tblclmn_comment = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_comment.setWidth(98);
		tblclmn_comment.setText("˵��");
		
		//Ϊ�����������Ĳ˵�
		Menu contextMenu = new Menu(Display.getDefault().getShells()[0], SWT.POP_UP);
		//���ò˵�
		MenuItem queryDetailItem = new MenuItem(contextMenu, SWT.NONE);
		queryDetailItem.setText("����");
		//ˢ�²˵�
		MenuItem refreshItem = new MenuItem(contextMenu, SWT.NONE);
		refreshItem.setText("ˢ��");
		//��Ӽ�����
		queryDetailItem.addSelectionListener(new MenuItemListenerImpl());
		refreshItem.addSelectionListener(new MenuItemListenerImpl());
		
		table_playSolution.setMenu(contextMenu);

		//��ʼ���ؼ����������ݵ��ؼ���
		init();
	}
	
	/**
	 * ��ʼ���ؼ�
	 */
	private void init(){
		//��ť��Ӽ����¼�
		tltm_add.addSelectionListener(new ButtonSelectionListener());
		tltm_moveDown.addSelectionListener(new ButtonSelectionListener());
		tltm_moveUp.addSelectionListener(new ButtonSelectionListener());
		tltm_del.addSelectionListener(new ButtonSelectionListener());
		
		//��ʾ���ŷ�����Ϣ
		fillTable();
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
			if(e.getSource() == tltm_add){							//��Ӱ�ť
				addPlaySolution();
			}
			else if(e.getSource() == tltm_moveDown){				//���ư�ť
				moveDown();
			}
			else if(e.getSource() == tltm_moveUp){					//���ư�ť
				moveUp();
			}
			else if(e.getSource() == tltm_del){						//ɾ������
				deletePlaySolution();
			}
			
		}
		
		
		/**
		 * ����Ӳ��ŷ������
		 */
		private void addPlaySolution(){
			AddPlaySolution addComposite = new AddPlaySolution();
			addComposite.showWindow();
		}
		
		/**
		 * ����
		 */
		private void moveDown(){
			int tableSelectedIndex = table_playSolution.getSelectionIndex();
			if(tableSelectedIndex > -1 && tableSelectedIndex < table_playSolution.getItemCount() - 1){
				table_playSolution.setSelection(tableSelectedIndex + 1);
			} 
		}
		
		/**
		 * ����
		 */
		private void moveUp(){
			int tableSelectedIndex = table_playSolution.getSelectionIndex();
			if(tableSelectedIndex > 0){
				table_playSolution.setSelection(tableSelectedIndex - 1);
			}
		}
		
		/**
		 * ɾ�����ŷ���
		 */
		private void deletePlaySolution(){
			if(!isSelected()){							//û��ѡ�в��ŷ���,��ʾ�û�ѡ�񲥷ŷ���
				MessageBox messageBox = new MessageBox(Display.getDefault().getShells()[0]);
				messageBox.setText("��ʾ");
				messageBox.setMessage("��ѡ��Ҫɾ���Ĳ��ŷ���!");
				messageBox.open();
				return;
			}
			
			TableItem tableItems[] = table_playSolution.getItems();
			//�����������
			for(int i = 0; i < tableItems.length; i++){
				if(!tableItems[i].getChecked())				//û��ѡ�е���ֱ������
					continue;
				int itemIndex = table_playSolution.indexOf(tableItems[i]);
				if(itemIndex < -1)							//��������������
					continue;
				//����ѡ�е���
				String playSolutionId_str = tableItems[i].getText(0);		//��ȡҪɾ���Ĳ��ŷ�����id
				int playSolutionId = Integer.valueOf(playSolutionId_str);
				//�����ݿ���ɾ�����ŷ���
				playSolutionDao.deletePlaySolutinById(playSolutionId);
				//ɾ�����ŷ����ļ���
				String displayName = tableItems[i].getText(2);
				String playSolutionName = tableItems[i].getText(1);
				String solutionPath = curProjectPath + File.separator + displayName + File.separator + playSolutionName;
				File solutionFile = new File(solutionPath);
				solutionFile.delete();
				//����ʾ�ı����ɾ��ѡ�е���
				table_playSolution.remove(itemIndex);
			}
			
		}
		
		/**
		 * �ж��Ƿ��в��ŷ���ѡ��,����ֻǰ��ĸ�ѡ��
		 * @return
		 */
		private boolean isSelected(){
			boolean isSelected = false;
			TableItem tableItems[] = table_playSolution.getItems();
			
			//�������б��Ԫ
			for(TableItem tableItem : tableItems){
				if(!tableItem.getChecked())
					continue;
				int itemIndex = table_playSolution.indexOf(tableItem);
				if(itemIndex < 0 )
					continue;
				isSelected = true;
				break;
			}
			
			return isSelected;
		}
	}
	
	/**
	 * �����ݿ������в��ŷ�����ʾ����
	 */
	private void fillTable(){
		List<PlaySolution> listOfPlaySolution = playSolutionDao.queryAllSolutions();
		
		for(PlaySolution playSolution : listOfPlaySolution){
			TableItem tableItem = new TableItem(table_playSolution, SWT.NONE);
			String itemText[] = new String[7];
			itemText[0] = String.valueOf(playSolution.getId());
			itemText[1] = playSolution.getName();
			itemText[2] = playSolution.getDisplay().getName();
			itemText[3] = String.valueOf(playSolution.getCreateTime());
			itemText[4] = String.valueOf(playSolution.getUpdateCount());
			itemText[5] = String.valueOf(playSolution.getUpdateTime());
			itemText[6] = playSolution.getComment() == null ? "" : playSolution.getComment();
			
			tableItem.setText(itemText);
		}
	}
	
	/**
	 * ��Ӳ��ŷ����������
	 */
	public static void addTableItem(String displayName, String playSolutionName){
		com.gxf.beans.Display display = displayDao.queryDisplayByName(displayName);
		PlaySolution playSolution = new PlaySolution();
		
		for(PlaySolution temp : display.getSolutions()){
			if(temp.getName().equals(playSolutionName)){
				playSolution = temp;
				break;
			}
		}
		
		//��ӱ������
		TableItem tableItem = new TableItem(table_playSolution, SWT.NONE);
		String itemText[] = new String[7];
		itemText[0] = String.valueOf(playSolution.getId());
		itemText[1] = playSolution.getName();
		itemText[2] = playSolution.getDisplay().getName();
		itemText[3] = String.valueOf(playSolution.getCreateTime());
		itemText[4] = String.valueOf(playSolution.getUpdateCount());
		itemText[5] = String.valueOf(playSolution.getUpdateTime());
		itemText[6] = playSolution.getComment() == null ? "" : playSolution.getComment();
		
		tableItem.setText(itemText);
	}
	
	/**
	 * �����Ĳ˵�������
	 * @author Administrator
	 *
	 */
	class MenuItemListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			MenuItem item = (MenuItem) e.getSource();
			if(item.getText() == "����"){						//�鿴���ŷ�����ϸ��Ϣ
				SetPlaySolution updatePlaySolution = new SetPlaySolution();
				TableItem tableItemSelectedIndex = table_playSolution.getItem(table_playSolution.getSelectionIndex());
				String displayName = tableItemSelectedIndex.getText(2);
				String playSolutionName = tableItemSelectedIndex.getText(1);
				
				SetPlaySolution.displayName = displayName;
				SetPlaySolution.playSolutionName = playSolutionName;
				
				updatePlaySolution.showWindow();
			}else if(item.getText() == "ˢ��"){
				//ˢ�±������
				refreshTable();
			}
		}
		
	}	
	
	/**
	 * ˢ�±������
	 */
	private void refreshTable(){
		table_playSolution.removeAll();
		fillTable();
	}
}
