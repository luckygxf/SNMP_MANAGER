package com.gxf.composite;

import java.io.File;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 使用说明
 * 单例模式
 * @author Administrator
 *
 */
public class DirectionsForUseComposite extends ApplicationWindow {
	private Util util = new Util();
	private Shell curShell;
	private static DirectionsForUseComposite directionsForUseComposite = new DirectionsForUseComposite();
	
	/**
	 * Create the application window.
	 */
	private DirectionsForUseComposite() {
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
		composite.setBounds(0, 0, 442, 269);
		
		Composite composite_icon = new Composite(composite, SWT.NONE);
		composite_icon.setBounds(0, 0, 139, 269);
		
		Canvas canvas = new Canvas(composite_icon, SWT.NONE);
		canvas.setBounds(0, 0, 139, 269);
		
		String iconPath = util.getCurrentProjectPath() + File.separator + "icons" + File.separator
									+ "logo.png";
		ImageData imageData = new ImageData(iconPath);
		imageData = imageData.scaledTo(139, 170);
		final Image image = new Image(Display.getDefault(), imageData);
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				gc.drawImage(image, 0, 35);
				
			}
		});
		Composite composite_content = new Composite(composite, SWT.NONE);
		composite_content.setBounds(139, 0, 303, 269);
		
		Label lblNewLabel = new Label(composite_content, SWT.NONE);
//		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setFont(SWTResourceManager.getFont("宋体", 10, SWT.NORMAL));
		lblNewLabel.setBounds(0, 10, 303, 259);
		String content = "说明文档\n\n";
		content += "1.新建显示屏，如显示屏名称，通信方式等\n\n";
		content += "2.新建播放方案，如播放方案名称，显示屏设置等\n\n";
		content += "3.从已有图片中选择，或者编辑图片到播放方案中\n\n";
		content += "4.发送播放方案到显示屏显示\n\n";
		content += "更多信息参考README";
		
		lblNewLabel.setText(content);

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
//			DirectionsForUseComposite window = new DirectionsForUseComposite();
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
		newShell.setText("帮助");
		String iconPath = util.getCurrentProjectPath() + File.separator +
				 			"icons" + File.separator + "helpIcon.png";
		ImageData imageData = new ImageData(iconPath);
		Image image = new Image(Display.getDefault(), imageData);
		newShell.setImage(image);
		
		//设置当前shell
		curShell = newShell;
		
		curShell.addKeyListener(new KeyListenerImpl());
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
	
	/**
	 * 提供对外访问的接口
	 */
	public void showWindow(){
		try {
			DirectionsForUseComposite window = DirectionsForUseComposite.getDirectionsForUseComposite();
			window.setBlockOnOpen(true);
			window.open();
//			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回单例实例
	 * @return
	 */
	public static DirectionsForUseComposite getDirectionsForUseComposite(){
		return directionsForUseComposite;
	}
	
	/**
	 * 按键监听器，这里主要是为了监听esc退出全屏显示
	 * @author Administrator
	 *
	 */
	class KeyListenerImpl implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.keyCode == SWT.ESC)
			{
				//退出全屏，设置前面shell显示出来
				curShell.dispose();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
