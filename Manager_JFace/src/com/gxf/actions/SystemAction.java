package com.gxf.actions;

import org.eclipse.jface.action.Action;

import com.gxf.composite.SystemInfo;

/**
 * ����ϵͳ
 * @author Administrator
 *
 */
public class SystemAction extends Action {
	public SystemAction(){
		setText("����ϵͳ");
		setToolTipText("����ϵͳ");
	}

	@Override
	public void run() {
		SystemInfo systemInfo = new SystemInfo();
		systemInfo.showWindow();
	}

}
