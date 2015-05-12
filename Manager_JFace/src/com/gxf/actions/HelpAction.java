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
 * �����˵�
 * ���õ������ģʽ
 * @author Administrator
 *
 */
public class HelpAction extends Action {
	private Util util = new Util();
	private static HelpAction helpAction = new HelpAction();
	
	private HelpAction(){
		setText("ʹ��˵��");
		setToolTipText("ʹ��˵��");
		
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
		//��˵���ĵ�����
		DirectionsForUseComposite directionsForUserComposite = DirectionsForUseComposite.getDirectionsForUseComposite();
		directionsForUserComposite.showWindow();
	}
	
	/**
	 * ��ȡʵ��
	 * @return
	 */
	public static HelpAction getHelpAction(){
		return helpAction;
	}
}
