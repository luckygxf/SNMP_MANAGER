package com.gxf.dao;

import com.gxf.beans.Display;

/**
 * 屏幕管理DAO 
 * @author Administrator
 *
 */
public interface DisplayDao {
	
	/**
	 * 添加屏幕到数据库
	 * @param display
	 */
	public void addDisplay(Display display);
}
