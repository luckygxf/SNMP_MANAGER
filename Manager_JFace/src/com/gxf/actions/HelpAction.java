package com.gxf.actions;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.gxf.composite.DirectionsForUseComposite;
import com.gxf.util.Util;


/**
 * 帮助菜单
 * 采用单例设计模式
 * @author Administrator
 *
 */
public class HelpAction extends Action {
	private Util util = new Util();
	private static HelpAction helpAction = new HelpAction();
	
	private HelpAction(){
		setText("使用说明");
		setToolTipText("使用说明");
		
//		String curProjectPath = util.getCurrentProjectPath();
//		String iconPath =  "icons" + File.separator + "helpIcon.png";
//		
		ImageData iconImageData = ImageDescriptor.createFromFile(HelpAction.class, "helpIcon.png").getImageData();
//		
		iconImageData = iconImageData.scaledTo(16, 16);
//		
		Image iconImage = new Image(Display.getDefault(), iconImageData);
		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
	}
	
	@Override
	public void run(){
		//打开说明文档窗口
		DirectionsForUseComposite directionsForUserComposite = DirectionsForUseComposite.getDirectionsForUseComposite();
		directionsForUserComposite.showWindow();
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static HelpAction getHelpAction(){
		return helpAction;
	}
}
