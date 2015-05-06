package com.gxf.dao;

import java.util.List;

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
	
	/**
	 * 查询所有的显示屏
	 * @return
	 */
	public List<Display> queryAllDisplay();
	
	/**
	 * 根据屏幕id删除屏幕
	 * @param displayId
	 */
	public void deleteDisplayById(int displayId);
	
	/**
	 * 根据名字查询显示屏
	 * @param name
	 * @return
	 */
	public Display queryDisplayByName(String name);
	
	/**
	 * 更新显示屏信息
	 * @param display
	 */
	public void updateDisplay(Display display);
	
	/**
	 * 根据id查询显示屏
	 * @param id
	 * @return
	 */
	public Display queryDisplayById(int id);
	
	/**
	 * 更新当前播放方案
	 * @param displayName
	 * @param curPlaySolutionName
	 */
	public void updateCurPlaySolution(String displayName, String curPlaySolutionName);
}
