package com.gxf.composite;

import java.io.File;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.gxf.util.Util;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Canvas;

public class SystemInfo extends ApplicationWindow {
	private Util util = new Util();
	private Shell curShell;
	
	private static SystemInfo systemInfo = new SystemInfo();
	
	/**
	 * Create the application window.
	 */
	private SystemInfo() {
		super(null);
		setShellStyle(SWT.CLOSE | SWT.TITLE);
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
		
		Composite composite = new Composite(container, SWT.INHERIT_FORCE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		composite.setBounds(0, 0, 442, 269);
		
		Composite composite_pic = new Composite(composite, SWT.NONE);
		composite_pic.setBounds(0, 0, 139, 269);
		
		Canvas canvas = new Canvas(composite_pic, SWT.NONE);
		canvas.setBounds(0, 0, 139, 269);
		
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				String picPath = util.getCurrentProjectPath() + File.separator + "icons" + File.separator
									+ "systemInfoBackground.jpg";
				
				ImageData imageData = new ImageData(picPath);
				imageData = imageData.scaledTo(139, 269);
				Image image = new Image(Display.getDefault(), imageData);
				
				gc.drawImage(image, 0, 0);
			}
		});
		
		
		Composite composite_sysInfo = new Composite(composite, SWT.NONE);
		composite_sysInfo.setBackground(SWTResourceManager.getColor(30, 144, 255));
		composite_sysInfo.setBounds(140, 0, 302, 269);
		
		Label lblNewLabel = new Label(composite_sysInfo, SWT.CENTER);
		lblNewLabel.setBackground(SWTResourceManager.getColor(30, 144, 255));
		lblNewLabel.setFont(SWTResourceManager.getFont("宋体", 14, SWT.BOLD));
		lblNewLabel.setBounds(43, 10, 124, 24);
		lblNewLabel.setText("LED播放器");
		
		Label label = new Label(composite_sysInfo, SWT.CENTER);
		label.setText("Developed By GXF!");
		label.setFont(SWTResourceManager.getFont("宋体", 14, SWT.BOLD));
		label.setBackground(SWTResourceManager.getColor(30, 144, 255));
		label.setBounds(43, 50, 198, 24);
		
		Label label_1 = new Label(composite_sysInfo, SWT.CENTER);
		label_1.setText("版本1.0");
		label_1.setFont(SWTResourceManager.getFont("宋体", 14, SWT.BOLD));
		label_1.setBackground(SWTResourceManager.getColor(30, 144, 255));
		label_1.setBounds(43, 97, 115, 24);
		
		Label lblCopyright = new Label(composite_sysInfo, SWT.CENTER);
		lblCopyright.setText("CopyRight@2015");
		lblCopyright.setFont(SWTResourceManager.getFont("宋体", 14, SWT.BOLD));
		lblCopyright.setBackground(SWTResourceManager.getColor(30, 144, 255));
		lblCopyright.setBounds(41, 186, 184, 24);
		
		//濞ｈ濮炴Η鐘崇垼閸滃矂鏁惄妯兼磧閸氼兛绨ㄦ禒锟�
		composite.addKeyListener(new KeyListenerImpl());
		composite.addMouseListener(new MouseListenerImp());
		
		//濞ｈ濮瀔eylistener and mouselistener
		composite.addMouseListener(new MouseListenerImp());

		return container;
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
		return null;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			SystemInfo window = new SystemInfo();
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
		newShell.setBackground(SWTResourceManager.getColor(0, 128, 0));
		//鐠佸墽鐤嗙粙瀣碍logo
		String projectPath = util.getCurrentProjectPath();
		String logoPath = projectPath + "\\icons\\" + "logo.png";
		ImageData logoImageData = new ImageData(logoPath);
		Image logoImage = new Image(newShell.getDisplay(), logoImageData);
		newShell.setImage(logoImage);
		super.configureShell(newShell);
		//鐠佸墽鐤嗙粙瀣碍閺嶅洭顣�
		newShell.setText("LED显示屏播放器--by GXF");
		
		curShell = newShell;
	}
	
	/**
	 * 鐎电懓顦婚幓鎰返閹恒儱褰�
	 */
	public void showWindow(){
		try {
			SystemInfo window = SystemInfo.getSystemInfo();
			window.setBlockOnOpen(true);
			window.open();
//			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
	
	/**
	 * 閹稿鏁惄鎴濇儔閸ｎ煉绱濇潻娆撳櫡娑撴槒顩﹂弰顖欒礋娴滃棛娲冮崥鐞玸c闁拷鍤崗銊ョ潌閺勫墽銇�
	 * @author Administrator
	 *
	 */
	class KeyListenerImpl implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.keyCode == SWT.ESC)
			{
				//闁拷鍤崗銊ョ潌閿涘矁顔曠純顔煎闂堫暀hell閺勫墽銇氶崙鐑樻降
				curShell.dispose();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * 姒х姵鐖ｉ惄鎴濇儔閸ｏ拷
	 * @author Administrator
	 *
	 */
	class MouseListenerImp implements MouseListener{
		
		//閸欏苯鍤Η鐘崇垼闁拷鍤崗銊ョ潌
		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
			//闁拷鍤崗銊ョ潌閿涘矁顔曠純顔煎闂堫暀hell閺勫墽銇氶崙鐑樻降
			curShell.dispose();
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
	 * 鏉╂柨娲栭崡鏇氱伐鐎圭偘绶�
	 * @return
	 */
	public static SystemInfo getSystemInfo(){
		return systemInfo;
	}
}
