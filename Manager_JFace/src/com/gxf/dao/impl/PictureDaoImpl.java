package com.gxf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
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

	/* (non-Javadoc)
	 * @see com.gxf.dao.PictureDao#queryAllPicture()
	 */
	@Override
	public List<Picture> queryAllPicture() {
		List<Picture> listOfPicture = new ArrayList<Picture>();
		Session session = baseDao.getSession();
		session.beginTransaction();
		String hql = "from Picture";
		Query query = session.createQuery(hql);
		listOfPicture = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return listOfPicture;
	}

}
