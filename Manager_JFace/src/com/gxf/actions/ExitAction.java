package com.gxf.actions;

import org.eclipse.jface.action.Action;

import com.gxf.main.Main;


public class ExitAction extends Action{
	public ExitAction(){
		super();
		setText("�˳�");
		setToolTipText("�˳�ϵͳ");
	}

	@Override
	public void run() {
		
		super.run();
		Main.getMain().close();						//�رմ���
	}
	
	
}
