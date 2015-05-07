package com.gxf.composite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

import com.gxf.beans.Picture;
import com.gxf.beans.PlayControl;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.PictureDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.PictureDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.entities.ImageItem;
import com.gxf.util.Util;

public class WordPicEditTool extends ApplicationWindow {
	//�����ؼ�
	private Text txt_imageWidth;
	private Text txt_imageHeight;
	private Text txt_addWord;
	
	private Composite composite_operate;
	private Composite composite_show;
	private Canvas canvas_show;
	private Button btn_addWord;
	private Button btn_addImage;
	
	private Combo combo_wordStyles;
	private Combo combo_wordColor;
	private Combo combo_wordSize;
	private Button cbtn_wordBold;
	private Button cbtn_wordItalic;
	private Button btn_imageCreate;
	private Button btn_close;
	
	//������
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private final Display currenetDisplay;
	
	//�û��༭�����ֺ�ͼƬ
	private List<ImageItem> imageItems = new ArrayList<ImageItem>();
	private List<Label> labelItems = new ArrayList<Label>();
	
	private Group group;
	private org.eclipse.swt.widgets.List list_word;
	private Group group_1;
	private org.eclipse.swt.widgets.List list_image;
	private Button btn_delImage;
	private Button btn_deleteAllImage;
	private Button btn_deleteWord;
	private Button btn_deleteAllWord;
	private Shell curShell;
	
	//���ŷ�������
	public static String solutionPath;
	public static String playSolutionName;
	
	//���ݿ������
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	private PictureDao pictureDao = new PictureDaoImpl();
	
	//��ȡUpdatePlaySolution����ˢ�±��
	private UpdatePlaySolution updatePlaySolution;
	
	//�������ſ���
	public static PlayControl playControl;

	
	/**
	 * Create the application window.
	 */
	public WordPicEditTool() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		
		currenetDisplay = Display.getCurrent();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		{
			Label lb_imageWidth = new Label(container, SWT.NONE);
			lb_imageWidth.setBounds(30, 10, 54, 12);
			lb_imageWidth.setText("ͼ����");
		}
		{
			Label lb_height = new Label(container, SWT.NONE);
			lb_height.setText("ͼ��߶�");
			lb_height.setBounds(30, 39, 54, 12);
		}
		
		//ͼƬ��Ⱥ͸߶�Ĭ��Ϊ128 * 64�����ܱ༭
		txt_imageWidth = new Text(container, SWT.BORDER);
		txt_imageWidth.setBounds(90, 7, 70, 18);
		txt_imageWidth.setText("128");
		txt_imageWidth.setEnabled(false);
		
		txt_imageHeight = new Text(container, SWT.BORDER);
		txt_imageHeight.setBounds(90, 39, 70, 18);
		txt_imageHeight.setText("64");
		txt_imageHeight.setEnabled(false);
		
		composite_operate = new Composite(container, SWT.NONE);
		composite_operate.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		composite_operate.setLocation(193, 7);
		composite_operate.setSize(128, 64);
		
		composite_show = new Composite(container, SWT.NONE);
		composite_show.setBounds(443, 7, 128, 64);
		
		canvas_show = new Canvas(composite_show, SWT.NONE);
		canvas_show.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		canvas_show.setBounds(0, 0, 128, 64);
		
		Label lb_showIcon = new Label(container, SWT.NONE);
		lb_showIcon.setBounds(364, 39, 32, 12);
		lb_showIcon.setText("==>");
		
		Group group_wordStyle = new Group(container, SWT.NONE);
		group_wordStyle.setText("�����ʽ");
		group_wordStyle.setBounds(30, 266, 541, 139);
		
		Label lb_wordStyle = new Label(group_wordStyle, SWT.NONE);
		lb_wordStyle.setBounds(61, 29, 54, 12);
		lb_wordStyle.setText("����");
		
		combo_wordStyles = new Combo(group_wordStyle, SWT.NONE);
		combo_wordStyles.setBounds(121, 26, 86, 20);
		
		Label lb_wordSize = new Label(group_wordStyle, SWT.NONE);
		lb_wordSize.setBounds(271, 29, 54, 12);
		lb_wordSize.setText("�����С");
		
		combo_wordSize = new Combo(group_wordStyle, SWT.NONE);
		combo_wordSize.setBounds(345, 26, 86, 20);
		
		Label lb_wordColor = new Label(group_wordStyle, SWT.NONE);
		lb_wordColor.setBounds(61, 63, 54, 12);
		lb_wordColor.setText("������ɫ");
		
		combo_wordColor = new Combo(group_wordStyle, SWT.NONE);
		combo_wordColor.setBounds(121, 60, 86, 20);
		
		cbtn_wordItalic = new Button(group_wordStyle, SWT.CHECK);
		cbtn_wordItalic.setBounds(61, 98, 93, 16);
		cbtn_wordItalic.setText("б��");
		
		cbtn_wordBold = new Button(group_wordStyle, SWT.CHECK);
		cbtn_wordBold.setBounds(210, 98, 93, 16);
		cbtn_wordBold.setText("�Ӵ�");
		
		btn_imageCreate = new Button(container, SWT.NONE);
		btn_imageCreate.setBounds(193, 411, 96, 22);
		btn_imageCreate.setText("��ӵ����ŷ���");
		
		btn_close = new Button(container, SWT.NONE);
		btn_close.setBounds(324, 411, 72, 22);
		btn_close.setText("�ر�");
		
		group = new Group(container, SWT.NONE);
		group.setText("�������");
		group.setBounds(30, 94, 336, 149);
		
		btn_addWord = new Button(group, SWT.NONE);
		btn_addWord.setBounds(162, 25, 72, 22);
		btn_addWord.setText("�������>>");
		
		txt_addWord = new Text(group, SWT.BORDER);
		txt_addWord.setBounds(10, 27, 146, 65);
		
		list_word = new org.eclipse.swt.widgets.List(group, SWT.BORDER);
		list_word.setBounds(240, 25, 86, 114);
		
		btn_deleteWord = new Button(group, SWT.NONE);
		btn_deleteWord.setBounds(162, 66, 72, 22);
		btn_deleteWord.setText("<<ɾ������");
		
		btn_deleteAllWord = new Button(group, SWT.NONE);
		btn_deleteAllWord.setBounds(162, 117, 72, 22);
		btn_deleteAllWord.setText("<<ɾ������");
		
		group_1 = new Group(container, SWT.NONE);
		group_1.setText("���ͼƬ");
		group_1.setBounds(391, 94, 199, 149);
		
		btn_addImage = new Button(group_1, SWT.NONE);
		btn_addImage.setBounds(10, 24, 72, 22);
		btn_addImage.setText("���ͼƬ>>");
		
		list_image = new org.eclipse.swt.widgets.List(group_1, SWT.BORDER);
		list_image.setBounds(103, 25, 86, 114);
		
		btn_delImage = new Button(group_1, SWT.NONE);
		btn_delImage.setBounds(10, 68, 72, 22);
		btn_delImage.setText("<<ɾ��ͼƬ");
		
		btn_deleteAllImage = new Button(group_1, SWT.NONE);
		btn_deleteAllImage.setBounds(10, 117, 72, 22);
		btn_deleteAllImage.setText("<<ɾ������");
		
		//�Կؼ����г�ʼ�� 
		init();

		return container;
	}
	
	/**
	 * �Կؼ����г�ʼ��
	 */
	public void init(){
		//���ñ���ɫ
		composite_operate.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		composite_operate.setBackgroundMode(SWT.INHERIT_FORCE);
		composite_show.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		
		//���������ʽ
		List<String> fontsInOS = util.getFontsInOS();
		for(int i = 0; i < fontsInOS.size(); i++)
			combo_wordStyles.add(fontsInOS.get(i));
		combo_wordStyles.select(79);
		
		//�����С
		for(int i = 11; i < 48; i++){
			combo_wordSize.add(String.valueOf(i));
		}
		combo_wordSize.select(4);
		
		//������ɫ
		String arrayOfFontColor[] = {"��ɫ", "��ɫ", "��ɫ"};
		for(int i = 0; i < arrayOfFontColor.length; i++){
			combo_wordColor.add(arrayOfFontColor[i]);
		}
		combo_wordColor.select(0);
		
		//��ťע�������
		btn_addWord.addSelectionListener(new ButtonSelectionListenerImp());
		
		//Ϊչʾ�õĻ���ע������¼�
		canvas_show.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawInCanva(e);
				
			}
		});
		
		
		//list_word��Ӽ����¼�
		list_word.addSelectionListener(new WordsListenerImpl());
		//ɾ��������Ӽ����¼�
		btn_deleteWord.addSelectionListener(new ButtonSelectionListenerImp());
		//ɾ��������Ӽ����¼�
		btn_deleteAllWord.addSelectionListener(new ButtonSelectionListenerImp());
		//���ͼƬ��Ӽ����¼�
		btn_addImage.addSelectionListener(new ButtonSelectionListenerImp());
		//ɾ��ͼƬ��Ӽ����¼�
		btn_delImage.addSelectionListener(new ButtonSelectionListenerImp());
		//ɾ������ͼƬ
		btn_deleteAllImage.addSelectionListener(new ButtonSelectionListenerImp());
		//��ӵ����ŷ���
		btn_imageCreate.addSelectionListener(new ButtonSelectionListenerImp());
		//�رմ���
		btn_close.addSelectionListener(new ButtonSelectionListenerImp());
		//˫������Ԥ��
		canvas_show.addMouseListener(new MouseListenerImplForPreviewImage());
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
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
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
//	public static void main(String args[]) {
//		try {
//			WordPicEditTool window = new WordPicEditTool();
//			window.setBlockOnOpen(true);
//			window.open();
//			Display.getCurrent().dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	//�ṩ�ӿڶ��ⲿ����
	public void openEditWindow(){
		try {
			WordPicEditTool window = new WordPicEditTool();
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
		newShell.setText("����ͼ��༭��");
		//���ó���logo
		String iconPath = curProjectPath + File.separator + "icons" + File.separator + "editPicIcon.jpg";
		ImageData imageData = new ImageData(iconPath);
		Image image = new Image(currenetDisplay, imageData);
		newShell.setImage(image);
		
		curShell = newShell;
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(625, 539);
	}
	
	/**
	 * ��ť������
	 * @author Administrator
	 *
	 */
	class ButtonSelectionListenerImp extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == btn_addWord){							//�������
				if(txt_addWord.getText() == ""){
					MessageBox messageBox = new MessageBox(curShell, SWT.OK);
					messageBox.setText("��ʾ");
					messageBox.setMessage("������Ҫ��ӵ�����");
					messageBox.open();
					return;
				}	
				addWord();
			}
			else if(e.getSource() == btn_deleteWord){					//ɾ������
				int index = list_word.getSelectionIndex();
				if(index == -1){
					MessageBox messageBox = new MessageBox(curShell, SWT.OK);
					messageBox.setText("��ʾ");
					messageBox.setMessage("��ѡ��Ҫɾ��������");
					messageBox.open();
					return;
				}				
				deleteWorld(list_word.getItem(index));
			}
			else if(e.getSource() == btn_deleteAllWord){				//ɾ����������
				deleteAllWord();
			}
			else if(e.getSource() == btn_addImage){						//���ͼƬ
				addImage();
			}
			else if(e.getSource() == btn_delImage){						//ɾ��ͼƬ
				int index = list_image.getSelectionIndex();
				
				if(index == -1){
					MessageBox messageBox = new MessageBox(curShell, SWT.OK);
					messageBox.setText("��ʾ");
					messageBox.setMessage("��ѡ��Ҫɾ����ͼƬ");
					messageBox.open();
					return;
				}
				
				deleteImage(list_image.getItem(list_image.getSelectionIndex()));
			}
			else if(e.getSource() == btn_deleteAllImage){				//ɾ������ͼƬ
				deleteAllImage();
			}
			else if(e.getSource() == btn_imageCreate){					//��ӵ����ŷ���
				saveToPlaySolution();
				//ˢ��ǰ�洰����ʾ
				updatePlaySolution.addImageToChoose();
				updatePlaySolution.addSc_picsChosed();
				
				MessageBox messageBox = new MessageBox(curShell, SWT.OK);
				messageBox.setText("��ʾ");
				messageBox.setMessage("ͼƬ��ӳɹ�!");
				messageBox.open();
			}
			else if(e.getSource() == btn_close){						//�رմ���
				closeWindow();
			}
		}		
	}
	
	/**
	 * �������
	 */
	private void addWord(){
		//��ȡҪ��ӵ�����
		String string_add = txt_addWord.getText();		
		//����imageItem
		getImageItem();			
		//���lable
		addLabel();
		
		//�����б������
		list_word.add(string_add);
	}
	
	/**
	 * �ƶ���ǩ������
	 * @author Administrator
	 *
	 */
	class LabelMoveListenerImpl implements MouseListener, MouseMoveListener{
		private Label label = null;
		private int x;
		private int y;
		
		@Override
		public void mouseMove(MouseEvent e) {
			Point point = Display.getCurrent().map(label, label.getParent(), e.x, e.y);
			Rectangle bounds = label.getBounds();
			label.setBounds(point.x - x, point.y - y,  bounds.width, bounds.height);
			
			//label��list����������������imageitem
			int index_label = labelItems.indexOf(label);
			
			ImageItem imageItem = imageItems.get(index_label);
			imageItem.setX(point.x - x);
			imageItem.setY(point.y - y);
			imageItem.setWidth(bounds.width);
			imageItem.setHeight(bounds.height);
			
			//���»�����ʾ
			canvas_show.redraw();
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDown(MouseEvent e) {
			label = (Label)e.getSource();
			x = e.x;
			y = e.y;
			//����ע������ƶ������¼�
			label.addMouseMoveListener(this);
		}

		@Override
		public void mouseUp(MouseEvent e) {
			if(e.button == 1){							//������
				label.removeMouseMoveListener(this);
			}
			
		}
		
	}
	
	/**
	 * ��ȡ�ַ�����font��ʽ�µ����س���
	 * @param string
	 * @param font
	 * @return
	 */
	public int getStringWidth(String string, Font font){   
	    int width = 0;   

	    Label label = new Label(Display.getCurrent().getActiveShell(), SWT.NONE);   
	    label.setFont(font);   
	    GC gc = new GC(label);   
	    for(int i=0;i<string.length();i++){   
	          char c = string.charAt(i);   
	          width += gc.getAdvanceWidth(c);   
	    }   
	    gc.dispose();   
	    label.dispose();
	    return width;   
	}
	
	/**
	 * ��ȡ������������ʽ������, �ŵ�list��
	 */
	public void getImageItem(){
		ImageItem imageItem = new ImageItem();
		//���imageItemName����ͼƬ������
		imageItem.setImageItemName(txt_addWord.getText());
		String style = combo_wordStyles.getItem(combo_wordStyles.getSelectionIndex());
		imageItem.setFontNameIndex(combo_wordStyles.getSelectionIndex());
		int height = Integer.valueOf(combo_wordSize.getItem(combo_wordSize.getSelectionIndex()));
		String color_str = combo_wordColor.getItem(combo_wordColor.getSelectionIndex());
		Color color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		
		if(color_str.equals("��ɫ"))
			color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		else if(color_str.equals("��ɫ"))
			color = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
		else if(color_str.equals("��ɫ"))
			color = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
		imageItem.setFontColorIndex(combo_wordColor.getSelectionIndex());
		imageItem.setFontSizeIndex(combo_wordSize.getSelectionIndex());
		//����ָб��ʹ���
		int fontStyle = 0;
		if(cbtn_wordBold.getSelection())
		{
			fontStyle |= SWT.BOLD;
			imageItem.setBold(true);
		}
		if(cbtn_wordItalic.getSelection())
		{
			fontStyle |= SWT.ITALIC;
			imageItem.setItalic(true);
		}
		
		Font font = new Font(Display.getDefault(), style, height, SWT.NONE | fontStyle);
		
		imageItem.setFont(font);
		imageItem.setWordColor(color);
		imageItem.setWords(txt_addWord.getText());
		
		int widthLable = getStringWidth(imageItem.getWords(), font);
		Label temp = new Label(composite_operate, fontStyle);
		temp.setText(txt_addWord.getText());
		temp.setForeground(color);
		temp.setFont(font);
		GC gc = new GC(temp);
		int heightLable = gc.getFontMetrics().getHeight();

		temp.dispose();
		
		imageItem.setWidth(widthLable);
		imageItem.setHeight(heightLable);
		
		//��ӵ�list��
		imageItems.add(imageItem);
	}
	
	/**
	 * ����±�ǩ��list��
	 */
	public void addLabel(){
		ImageItem imageItem = imageItems.get(imageItems.size() - 1);
		
		Label label = new Label(composite_operate, SWT.NONE);
		//���ñ�ǩ��ʾ��ɫ
		label.setForeground(imageItem.getWordColor());
		label.setText(imageItem.getWords());
		label.setFont(imageItem.getFont());
		label.setBounds(imageItem.getX(), imageItem.getY(), imageItem.getWidth(), imageItem.getHeight());
		
		//��ӵ�list��
		labelItems.add(label);
		//ע������¼�
		label.addMouseListener(new LabelMoveListenerImpl());
		//���»�����ʾ
		canvas_show.redraw();
		
	}
	
	/**
	 * ����imageItems��canvas�л����ֺ�ͼƬ
	 */
	public void drawInCanva(PaintEvent e){
		//��ȡgc�û�������
		GC gc = e.gc;
		//һ��һ����
		for(int i = 0; i < imageItems.size(); i++){
			ImageItem imageItem = imageItems.get(i);
			//��ʾ����
			if(imageItem.getImageData() == null){
				gc.setFont(imageItem.getFont());
				gc.setForeground(imageItem.getWordColor());
				gc.drawString(imageItem.getWords(), imageItem.getX(), imageItem.getY(), true);
//				gc.dispose();
			}
			else{						//��ʾͼƬ
				Image image = new Image(Display.getDefault(), imageItem.getImageData());
				gc.drawImage(image, imageItem.getX(), imageItem.getY());
//				gc.dispose();
			}
		}
	}
	
	/**
	 * �ַ���Ӽ����¼�
	 * @author Administrator
	 *
	 */
	class WordsListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			//ѡ�е�index
			int index = list_word.getSelectionIndex();
			//ѡ�е�����
			String word = list_word.getItem(index);
			txt_addWord.setText(word);
			
			ImageItem imageItem = imageItems.get(index);
			combo_wordStyles.select(imageItem.getFontNameIndex());
			combo_wordColor.select(imageItem.getFontColorIndex());
			combo_wordSize.select(imageItem.getFontSizeIndex());			
	
			cbtn_wordBold.setSelection(imageItem.isBold());
			cbtn_wordItalic.setSelection(imageItem.isItalic());
		}
		
	}
	
	/**
	 * ɾ������
	 * @param index
	 */
	private void deleteWorld(String imageItemName){
		int imageItemIndex = getImageItemIndex(imageItemName);
		imageItems.remove(imageItemIndex);
		Label label = labelItems.get(imageItemIndex);
		labelItems.remove(imageItemIndex);
		label.dispose();
		list_word.remove(list_word.getSelectionIndex());
//		txt_addWord.setText("");
		canvas_show.redraw();
	}
	
	/**
	 * ɾ�����е�����
	 */
	private void deleteAllWord(){
		Iterator<Label> it_lb = labelItems.iterator();
		for(Iterator<ImageItem> it = imageItems.iterator(); it.hasNext();){
			ImageItem imageItem = it.next();
			Label label = it_lb.next();
			if(imageItem.getImageData() == null){
				it.remove();
				it_lb.remove();
				label.dispose();
			}//if
			
		}
		list_word.removeAll();
		txt_addWord.setText("");
		canvas_show.redraw();
	}
	
	/**
	 * ����ļ�
	 */
	private void addImage(){
		FileDialog imageDialog =  new FileDialog(curShell, SWT.OPEN);
		imageDialog.setFilterExtensions(new String[]{"*.bmp"});
		
		String imagePath = imageDialog.open();

		if(imagePath != null){
			ImageData imageData = new ImageData(imagePath);
			ImageItem imageItem = new ImageItem();
			imageItem.setImageData(imageData);
			Label label =  new Label(composite_operate, SWT.NONE);
			label.setImage(new Image(Display.getDefault(), imageData));
			label.setBounds(0, 0, imageData.width, imageData.height);
			imageItem.setWidth(imageData.width);
			imageItem.setHeight(imageData.height);
			//�򼯺������Ԫ��
			imageItems.add(imageItem);
			labelItems.add(label);
			
			//��Ӽ����¼�
			label.addMouseListener(new LabelMoveListenerImpl());
			//��list������ļ���
			int lastFileSeparatporIndex = imagePath.lastIndexOf(File.separator);
			String imageName = imagePath.substring(lastFileSeparatporIndex + 1, imagePath.length());
			//���imageItemName�������ֺ�ͼƬ
			imageItem.setImageItemName(imageName);
			list_image.add(imageName);
			//ˢ�»�����ʾ
			canvas_show.redraw();
		}
	}
	
	/**
	 * ɾ��ͼƬ
	 */
	private void deleteImage(String imageItemName){
		int imageItemIndex = getImageItemIndex(imageItemName);
		
		imageItems.remove(imageItemIndex);
		Label label = labelItems.get(imageItemIndex);
		labelItems.remove(imageItemIndex);
		label.dispose();
		list_image.remove(list_image.getSelectionIndex());
		canvas_show.redraw();
	}
	
	/**
	 * ����imageItemName������imageItems������
	 * @param imageItemName
	 * @return
	 */
	private int getImageItemIndex(String imageItemName){
		int index = -1;
		for(int i = 0; i < imageItems.size(); i++){
			ImageItem imageItem = imageItems.get(i);
			if(imageItem.getImageItemName().equals(imageItemName))
			{
				index = i;
				break;
			}//if
		}//for
		
		return index;
	}
	
	/**
	 * ɾ��������ӵ�image
	 */
	private void deleteAllImage(){
		Iterator<Label> it_lb = labelItems.iterator();
		for(Iterator<ImageItem> it = imageItems.iterator(); it.hasNext();){
			ImageItem imageItem = it.next();
			Label label = it_lb.next();
			if(imageItem.getImageData() != null){
				it.remove();
				it_lb.remove();
				label.dispose();
			}//if
			
		}
		list_image.removeAll();

		canvas_show.redraw();
	}
	
	/**
	 * ���浽���ŷ���·����
	 * @param solutionName
	 */
	private void saveToPlaySolution(){
		GC gc = new GC(canvas_show);
		Image image = new Image(Display.getCurrent(), canvas_show.getSize().x, canvas_show.getSize().y);
		gc.copyArea(image, 0, 0);
		ImageData imageData = image.getImageData();
		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = new ImageData[]{imageData};
		
		//��ȡ��ǰϵͳʱ��,��������ʱ��
		String date_str = util.getCurTime();
		//����ͼƬ·��
		String imageName = date_str + ".bmp";
		String imagePath = solutionPath + File.separator + imageName;
		
		//ͼƬ��Ϣ���浽���ݿ���
		Picture picture = new Picture();
		picture.setPicName(imageName);
		picture.setPicPath(imagePath);
//		System.out.println("imagePath = " + imagePath);
		PlaySolution playSolution = playSolutionDao.querySolutionByNanme(WordPicEditTool.playSolutionName);
		//���ò��ŷ����Ϳ�����Ϣ
		picture.setPlaySolution(playSolution);		
		PlayControl playControl = new PlayControl();
		util.copyPlayControl(WordPicEditTool.playControl, playControl);
		picture.setPlayControl(playControl);
		
		pictureDao.addPicture(picture);
		
		//����ͼƬ
		imageLoader.save(imagePath, SWT.IMAGE_BMP);		
	}
	
	/**
	 * �رմ���
	 */
	public void closeWindow(){
		curShell.dispose();
	}
	
	/**
	 * ˫��Ԥ��ͼƬ
	 * @author Administrator
	 *
	 */
	class MouseListenerImplForPreviewImage implements MouseListener{
		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
			GC gc = new GC(canvas_show);
			Image image = new Image(Display.getCurrent(), canvas_show.getSize().x, canvas_show.getSize().y);
			gc.copyArea(image, 0, 0);
			ImageData imageData = image.getImageData();

			PreviewPic previewPic = new PreviewPic();
			previewPic.setImageData(imageData);
			previewPic.open();
			
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseUp(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * ��UpdatePlaySolutionע��
	 * @param updatePlaySolution
	 */
	public void addUpdatePlaySolution(UpdatePlaySolution updatePlaySolution){
		this.updatePlaySolution = updatePlaySolution;
	}
	
}
