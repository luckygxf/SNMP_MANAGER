package com.gxf.actions;

import org.eclipse.jface.action.Action;

import com.gxf.composite.DirectionsForUseComposite;

/**
 * �����˵�
 * @author Administrator
 *
 */
public class HelpAction extends Action {
	public HelpAction(){
		setText("ʹ��˵��");
		setToolTipText("ʹ��˵��");
	}
	
	@Override
	public void run(){
		//��˵���ĵ�����
		DirectionsForUseComposite directionsForUserComposite = new DirectionsForUseComposite();
		directionsForUserComposite.showWindow();
	}
}
