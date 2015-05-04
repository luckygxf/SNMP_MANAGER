package com.gxf.dao;

import java.util.List;

import com.gxf.beans.Picture;

public interface PictureDao {
	
	/**
	 * 添加图片信息到数据库中
	 * @param picture
	 */
	public void addPicture(Picture picture);
	
	/**
	 * 查询所有的图片信息
	 * @return
	 */
	public List<Picture> queryAllPicture();
	
	/**
	 * 根据picPath删除图片
	 * @param picPath
	 */
	public void deletePictureByPicPath(String picPath);
}
