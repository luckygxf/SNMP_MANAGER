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
	//申明控件
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
	
	//工具类
	private Util util = new Util();
	private final String curProjectPath = util.getCurrentProjectPath();
	private final Display currenetDisplay;
	
	//用户编辑的文字和图片
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
	
	//播放方案名称
	public static String solutionPath;
	public static String playSolutionName;
	
	//数据库访问类
	private PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
	private PictureDao pictureDao = new PictureDaoImpl();
	
	//获取UpdatePlaySolution用于刷新表格
	private UpdatePlaySolution updatePlaySolution;
	
	//方案播放控制
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
			lb_imageWidth.setText("图像宽度");
		}
		{
			Label lb_height = new Label(container, SWT.NONE);
			lb_height.setText("图像高度");
			lb_height.setBounds(30, 39, 54, 12);
		}
		
		//图片宽度和高度默认为128 * 64，不能编辑
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
		group_wordStyle.setText("字体格式");
		group_wordStyle.setBounds(30, 266, 541, 139);
		
		Label lb_wordStyle = new Label(group_wordStyle, SWT.NONE);
		lb_wordStyle.setBounds(61, 29, 54, 12);
		lb_wordStyle.setText("字体");
		
		combo_wordStyles = new Combo(group_wordStyle, SWT.NONE);
		combo_wordStyles.setBounds(121, 26, 86, 20);
		
		Label lb_wordSize = new Label(group_wordStyle, SWT.NONE);
		lb_wordSize.setBounds(271, 29, 54, 12);
		lb_wordSize.setText("字体大小");
		
		combo_wordSize = new Combo(group_wordStyle, SWT.NONE);
		combo_wordSize.setBounds(345, 26, 86, 20);
		
		Label lb_wordColor = new Label(group_wordStyle, SWT.NONE);
		lb_wordColor.setBounds(61, 63, 54, 12);
		lb_wordColor.setText("字体颜色");
		
		combo_wordColor = new Combo(group_wordStyle, SWT.NONE);
		combo_wordColor.setBounds(121, 60, 86, 20);
		
		cbtn_wordItalic = new Button(group_wordStyle, SWT.CHECK);
		cbtn_wordItalic.setBounds(61, 98, 93, 16);
		cbtn_wordItalic.setText("斜体");
		
		cbtn_wordBold = new Button(group_wordStyle, SWT.CHECK);
		cbtn_wordBold.setBounds(210, 98, 93, 16);
		cbtn_wordBold.setText("加粗");
		
		btn_imageCreate = new Button(container, SWT.NONE);
		btn_imageCreate.setBounds(193, 411, 96, 22);
		btn_imageCreate.setText("添加到播放方案");
		
		btn_close = new Button(container, SWT.NONE);
		btn_close.setBounds(324, 411, 72, 22);
		btn_close.setText("关闭");
		
		group = new Group(container, SWT.NONE);
		group.setText("添加文字");
		group.setBounds(30, 94, 336, 149);
		
		btn_addWord = new Button(group, SWT.NONE);
		btn_addWord.setBounds(162, 25, 72, 22);
		btn_addWord.setText("添加文字>>");
		
		txt_addWord = new Text(group, SWT.BORDER);
		txt_addWord.setBounds(10, 27, 146, 65);
		
		list_word = new org.eclipse.swt.widgets.List(group, SWT.BORDER);
		list_word.setBounds(240, 25, 86, 114);
		
		btn_deleteWord = new Button(group, SWT.NONE);
		btn_deleteWord.setBounds(162, 66, 72, 22);
		btn_deleteWord.setText("<<删除文字");
		
		btn_deleteAllWord = new Button(group, SWT.NONE);
		btn_deleteAllWord.setBounds(162, 117, 72, 22);
		btn_deleteAllWord.setText("<<删除所有");
		
		group_1 = new Group(container, SWT.NONE);
		group_1.setText("添加图片");
		group_1.setBounds(391, 94, 199, 149);
		
		btn_addImage = new Button(group_1, SWT.NONE);
		btn_addImage.setBounds(10, 24, 72, 22);
		btn_addImage.setText("添加图片>>");
		
		list_image = new org.eclipse.swt.widgets.List(group_1, SWT.BORDER);
		list_image.setBounds(103, 25, 86, 114);
		
		btn_delImage = new Button(group_1, SWT.NONE);
		btn_delImage.setBounds(10, 68, 72, 22);
		btn_delImage.setText("<<删除图片");
		
		btn_deleteAllImage = new Button(group_1, SWT.NONE);
		btn_deleteAllImage.setBounds(10, 117, 72, 22);
		btn_deleteAllImage.setText("<<删除所有");
		
		//对控件进行初始化 
		init();

		return container;
	}
	
	/**
	 * 对控件进行初始化
	 */
	public void init(){
		//设置背景色
		composite_operate.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		composite_operate.setBackgroundMode(SWT.INHERIT_FORCE);
		composite_show.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		
		//加载字体格式
		List<String> fontsInOS = util.getFontsInOS();
		for(int i = 0; i < fontsInOS.size(); i++)
			combo_wordStyles.add(fontsInOS.get(i));
		combo_wordStyles.select(79);
		
		//字体大小
		for(int i = 11; i < 48; i++){
			combo_wordSize.add(String.valueOf(i));
		}
		combo_wordSize.select(4);
		
		//字体颜色
		String arrayOfFontColor[] = {"红色", "绿色", "蓝色"};
		for(int i = 0; i < arrayOfFontColor.length; i++){
			combo_wordColor.add(arrayOfFontColor[i]);
		}
		combo_wordColor.select(0);
		
		//按钮注册监听器
		btn_addWord.addSelectionListener(new ButtonSelectionListenerImp());
		
		//为展示用的画布注册监听事件
		canvas_show.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawInCanva(e);
				
			}
		});
		
		
		//list_word添加监听事件
		list_word.addSelectionListener(new WordsListenerImpl());
		//删除文字添加监听事件
		btn_deleteWord.addSelectionListener(new ButtonSelectionListenerImp());
		//删除所有添加监听事件
		btn_deleteAllWord.addSelectionListener(new ButtonSelectionListenerImp());
		//添加图片添加监听事件
		btn_addImage.addSelectionListener(new ButtonSelectionListenerImp());
		//删除图片添加监听事件
		btn_delImage.addSelectionListener(new ButtonSelectionListenerImp());
		//删除所有图片
		btn_deleteAllImage.addSelectionListener(new ButtonSelectionListenerImp());
		//添加到播放方案
		btn_imageCreate.addSelectionListener(new ButtonSelectionListenerImp());
		//关闭窗口
		btn_close.addSelectionListener(new ButtonSelectionListenerImp());
		//双击画布预览
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
	//提供接口对外部调用
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
		newShell.setText("文字图像编辑器");
		//设置程序logo
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
	 * 按钮监听器
	 * @author Administrator
	 *
	 */
	class ButtonSelectionListenerImp extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			if(e.getSource() == btn_addWord){							//添加文字
				if(txt_addWord.getText() == ""){
					MessageBox messageBox = new MessageBox(curShell, SWT.OK);
					messageBox.setText("提示");
					messageBox.setMessage("请输入要添加的文字");
					messageBox.open();
					return;
				}	
				addWord();
			}
			else if(e.getSource() == btn_deleteWord){					//删除文字
				int index = list_word.getSelectionIndex();
				if(index == -1){
					MessageBox messageBox = new MessageBox(curShell, SWT.OK);
					messageBox.setText("提示");
					messageBox.setMessage("请选择要删除的文字");
					messageBox.open();
					return;
				}				
				deleteWorld(list_word.getItem(index));
			}
			else if(e.getSource() == btn_deleteAllWord){				//删除所有文字
				deleteAllWord();
			}
			else if(e.getSource() == btn_addImage){						//添加图片
				addImage();
			}
			else if(e.getSource() == btn_delImage){						//删除图片
				int index = list_image.getSelectionIndex();
				
				if(index == -1){
					MessageBox messageBox = new MessageBox(curShell, SWT.OK);
					messageBox.setText("提示");
					messageBox.setMessage("请选择要删除的图片");
					messageBox.open();
					return;
				}
				
				deleteImage(list_image.getItem(list_image.getSelectionIndex()));
			}
			else if(e.getSource() == btn_deleteAllImage){				//删除所有图片
				deleteAllImage();
			}
			else if(e.getSource() == btn_imageCreate){					//添加到播放方案
				saveToPlaySolution();
				//刷新前面窗口显示
				updatePlaySolution.addImageToChoose();
				updatePlaySolution.addSc_picsChosed();
				
				MessageBox messageBox = new MessageBox(curShell, SWT.OK);
				messageBox.setText("提示");
				messageBox.setMessage("图片添加成功!");
				messageBox.open();
			}
			else if(e.getSource() == btn_close){						//关闭窗口
				closeWindow();
			}
		}		
	}
	
	/**
	 * 添加文字
	 */
	private void addWord(){
		//获取要添加的文字
		String string_add = txt_addWord.getText();		
		//设置imageItem
		getImageItem();			
		//添加lable
		addLabel();
		
		//文字列表中添加
		list_word.add(string_add);
	}
	
	/**
	 * 移动标签监听器
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
			
			//label在list中索引，用来查找imageitem
			int index_label = labelItems.indexOf(label);
			
			ImageItem imageItem = imageItems.get(index_label);
			imageItem.setX(point.x - x);
			imageItem.setY(point.y - y);
			imageItem.setWidth(bounds.width);
			imageItem.setHeight(bounds.height);
			
			//更新画布显示
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
			//这里注册鼠标移动监听事件
			label.addMouseMoveListener(this);
		}

		@Override
		public void mouseUp(MouseEvent e) {
			if(e.button == 1){							//鼠标左键
				label.removeMouseMoveListener(this);
			}
			
		}
		
	}
	
	/**
	 * 获取字符串在font格式下的像素长度
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
	 * 获取面板上面字体格式的设置, 放到list中
	 */
	public void getImageItem(){
		ImageItem imageItem = new ImageItem();
		//添加imageItemName区分图片和文字
		imageItem.setImageItemName(txt_addWord.getText());
		String style = combo_wordStyles.getItem(combo_wordStyles.getSelectionIndex());
		imageItem.setFontNameIndex(combo_wordStyles.getSelectionIndex());
		int height = Integer.valueOf(combo_wordSize.getItem(combo_wordSize.getSelectionIndex()));
		String color_str = combo_wordColor.getItem(combo_wordColor.getSelectionIndex());
		Color color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		
		if(color_str.equals("红色"))
			color = Display.getDefault().getSystemColor(SWT.COLOR_RED);
		else if(color_str.equals("绿色"))
			color = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
		else if(color_str.equals("蓝色"))
			color = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
		imageItem.setFontColorIndex(combo_wordColor.getSelectionIndex());
		imageItem.setFontSizeIndex(combo_wordSize.getSelectionIndex());
		//这里指斜体和粗体
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
		
		//添加到list中
		imageItems.add(imageItem);
	}
	
	/**
	 * 添加新标签到list中
	 */
	public void addLabel(){
		ImageItem imageItem = imageItems.get(imageItems.size() - 1);
		
		Label label = new Label(composite_operate, SWT.NONE);
		//设置标签显示颜色
		label.setForeground(imageItem.getWordColor());
		label.setText(imageItem.getWords());
		label.setFont(imageItem.getFont());
		label.setBounds(imageItem.getX(), imageItem.getY(), imageItem.getWidth(), imageItem.getHeight());
		
		//添加到list中
		labelItems.add(label);
		//注册监听事件
		label.addMouseListener(new LabelMoveListenerImpl());
		//更新画布显示
		canvas_show.redraw();
		
	}
	
	/**
	 * 根据imageItems在canvas中画文字和图片
	 */
	public void drawInCanva(PaintEvent e){
		//获取gc用户画内容
		GC gc = e.gc;
		//一个一个画
		for(int i = 0; i < imageItems.size(); i++){
			ImageItem imageItem = imageItems.get(i);
			//显示文字
			if(imageItem.getImageData() == null){
				gc.setFont(imageItem.getFont());
				gc.setForeground(imageItem.getWordColor());
				gc.drawString(imageItem.getWords(), imageItem.getX(), imageItem.getY(), true);
//				gc.dispose();
			}
			else{						//显示图片
				Image image = new Image(Display.getDefault(), imageItem.getImageData());
				gc.drawImage(image, imageItem.getX(), imageItem.getY());
//				gc.dispose();
			}
		}
	}
	
	/**
	 * 字符添加监听事件
	 * @author Administrator
	 *
	 */
	class WordsListenerImpl extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent e) {
			//选中的index
			int index = list_word.getSelectionIndex();
			//选中的文字
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
	 * 删除文字
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
	 * 删除所有的文字
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
	 * 添加文件
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
			//向集合中添加元素
			imageItems.add(imageItem);
			labelItems.add(label);
			
			//添加监听事件
			label.addMouseListener(new LabelMoveListenerImpl());
			//向list中添加文件名
			int lastFileSeparatporIndex = imagePath.lastIndexOf(File.separator);
			String imageName = imagePath.substring(lastFileSeparatporIndex + 1, imagePath.length());
			//添加imageItemName区分文字和图片
			imageItem.setImageItemName(imageName);
			list_image.add(imageName);
			//刷新画布显示
			canvas_show.redraw();
		}
	}
	
	/**
	 * 删除图片
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
	 * 根据imageItemName查找在imageItems中索引
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
	 * 删除所有添加的image
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
	 * 保存到播放方案路径下
	 * @param solutionName
	 */
	private void saveToPlaySolution(){
		GC gc = new GC(canvas_show);
		Image image = new Image(Display.getCurrent(), canvas_show.getSize().x, canvas_show.getSize().y);
		gc.copyArea(image, 0, 0);
		ImageData imageData = image.getImageData();
		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = new ImageData[]{imageData};
		
		//获取当前系统时间,用于命名时间
		String date_str = util.getCurTime();
		//设置图片路径
		String imageName = date_str + ".bmp";
		String imagePath = solutionPath + File.separator + imageName;
		
		//图片信息保存到数据库中
		Picture picture = new Picture();
		picture.setPicName(imageName);
		picture.setPicPath(imagePath);
//		System.out.println("imagePath = " + imagePath);
		PlaySolution playSolution = playSolutionDao.querySolutionByNanme(WordPicEditTool.playSolutionName);
		//设置播放方案和控制信息
		picture.setPlaySolution(playSolution);		
		PlayControl playControl = new PlayControl();
		util.copyPlayControl(WordPicEditTool.playControl, playControl);
		picture.setPlayControl(playControl);
		
		pictureDao.addPicture(picture);
		
		//保存图片
		imageLoader.save(imagePath, SWT.IMAGE_BMP);		
	}
	
	/**
	 * 关闭窗口
	 */
	public void closeWindow(){
		curShell.dispose();
	}
	
	/**
	 * 双击预览图片
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
	 * 将UpdatePlaySolution注册
	 * @param updatePlaySolution
	 */
	public void addUpdatePlaySolution(UpdatePlaySolution updatePlaySolution){
		this.updatePlaySolution = updatePlaySolution;
	}
	
}
