package com.gxf.actions;

import org.eclipse.jface.action.Action;

import com.gxf.main.Main;


public class ExitAction extends Action{
	public ExitAction(){
		super();
		setText("退出");
		setToolTipText("退出系统");
	}

	@Override
	public void run() {
		
		super.run();
		Main.getMain().close();						//关闭窗口
	}
	
	
}
