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
	
	/**
	 * ɾ��ͨ�ŷ�ʽ
	 * @param communication
	 */
	public void deleteCommunication(Communication communication);
	
	/**
	 * ����id��ѯͨ�ŷ�ʽ
	 * @param id
	 * @return
	 */
	public Communication queryCommunicationById(int id);
}
