package com.gxf.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.gxf.main.Main;
import com.gxf.util.Util;


/**
 * 退出系统菜单
 * @author Administrator
 *
 */
public class ExitAction extends Action{
	private Util util = new Util();
	private static ExitAction exitAction = new ExitAction();
	
	private ExitAction(){
		super();
		setText("退出");
		setToolTipText("退出系统");
		
//		String curProjectPath = util.getCurrentProjectPath();
//		String iconPath = "icons" + File.separator + "exit.png";
//		
//		ImageData iconImageData = new ImageData(iconPath);
//		
//		iconImageData = iconImageData.scaledTo(16, 16);
//		
//		Image iconImage = new Image(Display.getDefault(), iconImageData);
//		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
		
		ImageData iconImageData = ImageDescriptor.createFromFile(HelpAction.class, "exit.png").getImageData();
//		
		iconImageData = iconImageData.scaledTo(16, 16);
//		
		Image iconImage = new Image(Display.getDefault(), iconImageData);
		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
	}

	@Override
	public void run() {
		System.exit(0);
	}
	
	public static ExitAction getExitAction(){
		return exitAction;
	}
}
