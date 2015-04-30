package com.gxf.dao.impl;

import org.hibernate.Session;

import com.gxf.beans.Picture;
import com.gxf.dao.BaseDao;
import com.gxf.dao.PictureDao;

public class PictureDaoImpl implements PictureDao {
	private BaseDao baseDao = new BaseDao();
	
	/* (non-Javadoc)
	 * @see com.gxf.dao.PictureDao#addPicture(com.gxf.beans.Picture)
	 */
	@Override
	public void addPicture(Picture picture) {
		Session session = baseDao.getSession();
		
		session.beginTransaction();
		session.save(picture);
		session.getTransaction().commit();
		
		session.close();
	}

}
