package com.gxf.dao;

import com.gxf.beans.Communication;

public interface CommunicationDao {
	
	/**
	 * ���ͨ�ŷ�ʽ
	 * @param communication
	 */
	public void addCommunication(Communication communication);
	
	/**
	 * �������ֲ���ͨ�ŷ�ʽ
	 * @param name
	 * @return
	 */
	public Communication queryCommunicatioByName(String name);
}
