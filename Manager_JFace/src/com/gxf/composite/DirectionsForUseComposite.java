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
 * ʹ��˵��
 * ����ģʽ
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
		lblNewLabel.setFont(SWTResourceManager.getFont("����", 10, SWT.NORMAL));
		lblNewLabel.setBounds(0, 10, 303, 259);
		String content = "˵���ĵ�\n\n";
		content += "1.�½���ʾ��������ʾ�����ƣ�ͨ�ŷ�ʽ��\n\n";
		content += "2.�½����ŷ������粥�ŷ������ƣ���ʾ�����õ�\n\n";
		content += "3.������ͼƬ��ѡ�񣬻��߱༭ͼƬ�����ŷ�����\n\n";
		content += "4.���Ͳ��ŷ�������ʾ����ʾ\n\n";
		content += "������Ϣ�ο�README";
		
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
		newShell.setText("����");
		String iconPath = util.getCurrentProjectPath() + File.separator +
				 			"icons" + File.separator + "helpIcon.png";
		ImageData imageData = new ImageData(iconPath);
		Image image = new Image(Display.getDefault(), imageData);
		newShell.setImage(image);
		
		//���õ�ǰshell
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
	 * �ṩ������ʵĽӿ�
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
	 * ���ص���ʵ��
	 * @return
	 */
	public static DirectionsForUseComposite getDirectionsForUseComposite(){
		return directionsForUseComposite;
	}
	
	/**
	 * ������������������Ҫ��Ϊ�˼���esc�˳�ȫ����ʾ
	 * @author Administrator
	 *
	 */
	class KeyListenerImpl implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.keyCode == SWT.ESC)
			{
				//�˳�ȫ��������ǰ��shell��ʾ����
				curShell.dispose();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
