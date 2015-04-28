package com.gxf.entities;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

/**
 * 表格中添加的控件,用于删除
 * @author Administrator
 *
 */
public class TableItem {
	private Text txt_displayName;
	private Text txt_displayType;
	private Combo combo_communityType;
	private Text txt_displayIp;
	private Text txt_displayPort;
	private Text txt_displayComment;
	
	public Text getTxt_displayName() {
		return txt_displayName;
	}
	public void setTxt_displayName(Text txt_displayName) {
		this.txt_displayName = txt_displayName;
	}
	public Text getTxt_displayType() {
		return txt_displayType;
	}
	public void setTxt_displayType(Text txt_displayType) {
		this.txt_displayType = txt_displayType;
	}
	public Combo getCombo_communityType() {
		return combo_communityType;
	}
	public void setCombo_communityType(Combo combo_communityType) {
		this.combo_communityType = combo_communityType;
	}
	public Text getTxt_displayIp() {
		return txt_displayIp;
	}
	public void setTxt_displayIp(Text txt_displayIp) {
		this.txt_displayIp = txt_displayIp;
	}
	public Text getTxt_displayPort() {
		return txt_displayPort;
	}
	public void setTxt_displayPort(Text txt_displayPort) {
		this.txt_displayPort = txt_displayPort;
	}
	public Text getTxt_displayComment() {
		return txt_displayComment;
	}
	public void setTxt_displayComment(Text txt_displayComment) {
		this.txt_displayComment = txt_displayComment;
	}
	
	/**
	 * 释放控件
	 */
	public void dispose(){
		txt_displayName.dispose();
		txt_displayName.dispose();
		txt_displayType.dispose();
		combo_communityType.dispose();
		txt_displayIp.dispose();
		txt_displayPort.dispose();
		txt_displayComment.dispose();
	}
	
	
}
