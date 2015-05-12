package com.gxf.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.gxf.composite.AddDisplay;
import com.gxf.composite.DirectionsForUseComposite;

public class AddDisplayAction extends Action {
	
	public AddDisplayAction(){
		setText("�����ʾ��");
		setToolTipText("�����ʾ��");
		
//		String curProjectPath = util.getCurrentProjectPath();
//		String iconPath =  "icons" + File.separator + "helpIcon.png";
//		
		ImageData iconImageData = ImageDescriptor.createFromFile(AddDisplayAction.class, "addDisplayBarIcon.png").getImageData();
//		
		iconImageData = iconImageData.scaledTo(16, 16);
//		
		Image iconImage = new Image(Display.getDefault(), iconImageData);
		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
	}
	
	@Override
	public void run(){
		//��ʾ�����ʾ����Ϣ
		AddDisplay addDisplay = AddDisplay.getAddDisplayComposite();
		addDisplay.show();
	}
}
