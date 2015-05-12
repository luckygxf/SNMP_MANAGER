package com.gxf.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.gxf.composite.SystemInfo;
import com.gxf.util.Util;

/**
 * 关于系统
 * 单例模式
 * @author Administrator
 *
 */
public class SystemAction extends Action {
	
	private Util util = new Util();
	private static SystemAction systemAction = new SystemAction();
	
	private SystemAction(){
		setText("关于系统");
		setToolTipText("关于系统");
		
//		String curProjectPath = util.getCurrentProjectPath();
//		String iconPath =  "icons" + File.separator + "info.png";
//		
//		ImageData iconImageData = new ImageData(iconPath);
//		
//		iconImageData = iconImageData.scaledTo(16, 16);
//		
//		Image iconImage = new Image(Display.getDefault(), iconImageData);
//		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
		
		ImageData iconImageData = ImageDescriptor.createFromFile(HelpAction.class, "info.png").getImageData();
//		
		iconImageData = iconImageData.scaledTo(16, 16);
//		
		Image iconImage = new Image(Display.getDefault(), iconImageData);
		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
	}

	@Override
	public void run() {
		SystemInfo systemInfo = SystemInfo.getSystemInfo();
		systemInfo.showWindow();
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static SystemAction getSystemAction(){
		return systemAction;
	}
}
