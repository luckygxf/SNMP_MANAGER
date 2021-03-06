package com.gxf.dao;

import com.gxf.beans.Communication;

public interface CommunicationDao {
	
	/**
	 * 添加通信方式
	 * @param communication
	 */
	public void addCommunication(Communication communication);
	
	/**
	 * 根据名字查找通信方式
	 * @param name
	 * @return
	 */
	public Communication queryCommunicatioByName(String name);
	
	/**
	 * 删除通信方式
	 * @param communication
	 */
	public void deleteCommunication(Communication communication);
	
	/**
	 * 根据id查询通信方式
	 * @param id
	 * @return
	 */
	public Communication queryCommunicationById(int id);
}
