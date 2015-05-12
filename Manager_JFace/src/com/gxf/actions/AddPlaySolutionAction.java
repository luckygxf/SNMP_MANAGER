package com.gxf.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import com.gxf.composite.AddPlaySolution;


public class AddPlaySolutionAction extends Action {
	public AddPlaySolutionAction(){
		setText("��Ӳ��ŷ���");
		setToolTipText("��Ӳ��ŷ���");
		
//		String curProjectPath = util.getCurrentProjectPath();
//		String iconPath =  "icons" + File.separator + "helpIcon.png";
//		
		ImageData iconImageData = ImageDescriptor.createFromFile(AddPlaySolutionAction.class, "addPlaySolutionBarIcon.png").getImageData();
//		
		iconImageData = iconImageData.scaledTo(16, 16);
//		
		Image iconImage = new Image(Display.getDefault(), iconImageData);
		setImageDescriptor(ImageDescriptor.createFromImage(iconImage));
	}
	
	@Override
	public void run(){
		//��ʾ��Ӳ��ŷ������
		AddPlaySolution addComposite = AddPlaySolution.getAddPlaySolution();
		addComposite.showWindow();
	}
}
