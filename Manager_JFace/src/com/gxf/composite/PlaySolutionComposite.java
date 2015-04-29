package com.gxf.composite;

import java.io.File;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TableColumn;

import com.gxf.util.Util;

/**
 * ���ŷ����������
 * @author Administrator
 *
 */
public class PlaySolutionComposite extends Composite {
	//����Ͽؼ�
	private Table table_playSolution;	
	private ToolItem tltm_add;
	
	//������
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	
	public PlaySolutionComposite(Composite parent, int style) {
		super(parent, style);
		
		ViewForm viewForm = new ViewForm(this, SWT.NONE);
		viewForm.setBounds(10, 10, 520, 410);
		
		ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT);
		viewForm.setTopLeft(toolBar);
		//���
		tltm_add = new ToolItem(toolBar, SWT.NONE);
		tltm_add.setText("���");
		//����ͼ��
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "addDisplayIcon.png";		
		tltm_add.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//ɾ��
		ToolItem tltm_del = new ToolItem(toolBar, SWT.NONE);
		tltm_del.setText("ɾ��");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "delDisplayIcon.png";		
		tltm_del.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//����
		ToolItem tltm_moveDown = new ToolItem(toolBar, SWT.NONE);
		tltm_moveDown.setText("����");
		iconPath = curProjectPath + File.separator + "icons" + File.separator + "moveDown.png";		
		tltm_moveDown.setImage(new Image(Display.getDefault(), new ImageData(iconPath)));
		
		//����
		ToolItem tltm_moveUp = new ToolItem(toolBar, SWT.NONE);
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
		
		table_playSolution = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table_playSolution.setBounds(0, 0, 516, 376);
		table_playSolution.setHeaderVisible(true);
		table_playSolution.setLinesVisible(true);
		
		TableColumn tblclmn_nums = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_nums.setWidth(40);
		tblclmn_nums.setText("���");
		
		TableColumn tblclmn_displayName = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_displayName.setWidth(76);
		tblclmn_displayName.setText("��ʾ������");
		
		TableColumn tblclmn_displaySolutionName = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_displaySolutionName.setWidth(81);
		tblclmn_displaySolutionName.setText("���ŷ�����");
		
		TableColumn tblclmn_createTime = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_createTime.setWidth(62);
		tblclmn_createTime.setText("����ʱ��");
		
		TableColumn tblclmn_updateCount = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_updateCount.setWidth(62);
		tblclmn_updateCount.setText("�޸Ĵ���");
		
		TableColumn tblclmn_lastUpdateTime = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_lastUpdateTime.setWidth(86);
		tblclmn_lastUpdateTime.setText("����޸�");
		
		TableColumn tblclmn_comment = new TableColumn(table_playSolution, SWT.NONE);
		tblclmn_comment.setWidth(100);
		tblclmn_comment.setText("˵��");

		//��ʼ���ؼ����������ݵ��ؼ���
		init();
	}
	
	/**
	 * ��ʼ���ؼ�
	 */
	private void init(){
		//��ť��Ӽ����¼�
		tltm_add.addSelectionListener(new ButtonSelectionListener());
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
			
		}
		
		
		/**
		 * ����Ӳ��ŷ������
		 */
		private void addPlaySolution(){
			AddPlaySolution addComposite = new AddPlaySolution();
			addComposite.showWindow();
		}
	}
	
}
