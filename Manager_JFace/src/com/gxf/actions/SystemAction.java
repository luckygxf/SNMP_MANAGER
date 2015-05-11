package com.gxf.actions;

import org.eclipse.jface.action.Action;

import com.gxf.composite.SystemInfo;

/**
 * 关于系统
 * @author Administrator
 *
 */
public class SystemAction extends Action {
	public SystemAction(){
		setText("关于系统");
		setToolTipText("关于系统");
	}

	@Override
	public void run() {
		SystemInfo systemInfo = new SystemInfo();
		systemInfo.showWindow();
	}

}
