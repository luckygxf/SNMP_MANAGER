package com.gxf.actions;

import org.eclipse.jface.action.Action;

import com.gxf.composite.DirectionsForUseComposite;

/**
 * 帮助菜单
 * @author Administrator
 *
 */
public class HelpAction extends Action {
	public HelpAction(){
		setText("使用说明");
		setToolTipText("使用说明");
	}
	
	@Override
	public void run(){
		//打开说明文档窗口
		DirectionsForUseComposite directionsForUserComposite = new DirectionsForUseComposite();
		directionsForUserComposite.showWindow();
	}
}
