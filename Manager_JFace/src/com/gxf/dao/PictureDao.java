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
	
	/**
	 * 根据图片路径查询图片
	 * @param picPath
	 * @return
	 */
	public Picture queryByPicPath(String picPath);
	
	/**
	 * 更新picture,已测试
	 * @param picture
	 */
	public void updatePicture(Picture picture);
	
	/**
	 * 查询图片表中记录数
	 * @return
	 */
	public int queryPictureCount();
	
	/**
	 * 根据图片播放顺序查询图片信息
	 * @param playOrder
	 * @return
	 */
	public Picture queryPicByPlayOrder(int playOrder);
}
