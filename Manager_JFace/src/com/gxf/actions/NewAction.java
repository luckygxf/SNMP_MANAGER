package com.gxf.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class NewAction extends Action {
	public NewAction(){
		setText("新建");
		setToolTipText("新建文件");
		setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\newActionIcon.png"));
	}
	
}
