package com.gxf.dao;

import com.gxf.beans.Picture;

public interface PictureDao {
	
	/**
	 * 添加图片信息到数据库中
	 * @param picture
	 */
	public void addPicture(Picture picture);
}
