package com.gxf.dao;

import java.util.List;

import com.gxf.beans.Display;

/**
 * ��Ļ����DAO 
 * @author Administrator
 *
 */
public interface DisplayDao {
	
	/**
	 * �����Ļ�����ݿ�
	 * @param display
	 */
	public void addDisplay(Display display);
	
	/**
	 * ��ѯ���е���ʾ��
	 * @return
	 */
	public List<Display> queryAllDisplay();
	
	/**
	 * ������Ļidɾ����Ļ
	 * @param displayId
	 */
	public void deleteDisplayById(int displayId);
	
	/**
	 * �������ֲ�ѯ��ʾ��
	 * @param name
	 * @return
	 */
	public Display queryDisplayByName(String name);
}
