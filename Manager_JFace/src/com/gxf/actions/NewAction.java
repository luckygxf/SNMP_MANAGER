package com.gxf.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

public class NewAction extends Action {
	public NewAction(){
		setText("�½�");
		setToolTipText("�½��ļ�");
		setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class, "icons\\newActionIcon.png"));
	}
	
}
