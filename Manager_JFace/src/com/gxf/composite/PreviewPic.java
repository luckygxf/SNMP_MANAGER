package com.gxf.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PreviewPic {
	private Display curDisplay;
	private Shell curShell;
	private Image imageToShwo;
	private ImageData imageData;
	
	
	public PreviewPic(){
		curDisplay = Display.getDefault();
		curShell = new Shell(curDisplay, SWT.NO_TRIM);
	}
	
	public void open(){
		
		
		curShell.setSize(512, 256);
		curShell.setText("Í¼Æ¬Ô¤ÀÀ");
		//ÉèÖÃshell¾ÓÖÐÏÔÊ¾
		setShellCenter();
		//ÏÔÊ¾Í¼Æ¬
		showImage();
		curShell.open();
		while(!curShell.isDisposed()){
			if(!curDisplay.readAndDispatch()){
				curDisplay.sleep();
			}
		}
	}
	
	public void setImageData(ImageData imageData){
		this.imageData = imageData;
	}
	
	public void showImage(){
		Composite composite = new Composite(curShell, SWT.NONE);
		composite.setBounds(0, 0, 512, 256);
		Canvas canvas = new Canvas(composite, SWT.NONE);
		//×¢²áÊó±ê¼àÌýÊÂ¼þ
		canvas.addMouseListener(new MouseListenerImpl());
		
		imageData = imageData.scaledTo(512, 256);
		imageToShwo =  new Image(curDisplay, imageData);
		canvas.setBounds(composite.getBounds());
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent arg0) {
				GC gc = arg0.gc;
				gc.drawImage(imageToShwo, 0, 0);
			}
		});
		
		canvas.redraw();
	}
	
	/**
	 * ÉèÖÃshell¾ÓÖÐ
	 */
	public void setShellCenter(){
		int width = curShell.getMonitor().getClientArea().width;
		int height = curShell.getMonitor().getClientArea().height;
		int x = curShell.getSize().x;
		int y = curShell.getSize().y;
		if (x > width) {
			curShell.getSize().x = width;
		}
		if (y > height) {
			curShell.getSize().y = height;
		}
		curShell.setLocation((width - x) / 2, (height - y) / 2 - 100);
	}
	
	class MouseListenerImpl implements MouseListener{

		
		//Ë«»÷ÍË³öÔ¤ÀÀ
		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
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
}
