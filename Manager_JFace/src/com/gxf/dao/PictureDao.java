package com.gxf.dao;

import java.util.List;

import com.gxf.beans.Picture;

public interface PictureDao {
	
	/**
	 * ���ͼƬ��Ϣ�����ݿ���
	 * @param picture
	 */
	public void addPicture(Picture picture);
	
	/**
	 * ��ѯ���е�ͼƬ��Ϣ
	 * @return
	 */
	public List<Picture> queryAllPicture();
	
	/**
	 * ����picPathɾ��ͼƬ
	 * @param picPath
	 */
	public void deletePictureByPicPath(String picPath);
	
	/**
	 * ����ͼƬ·����ѯͼƬ
	 * @param picPath
	 * @return
	 */
	public Picture queryByPicPath(String picPath);
	
	/**
	 * ����picture,�Ѳ���
	 * @param picture
	 */
	public void updatePicture(Picture picture);
}
