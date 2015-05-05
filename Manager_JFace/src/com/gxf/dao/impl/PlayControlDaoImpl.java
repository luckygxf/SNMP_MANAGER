package com.gxf.dao.impl;

import org.hibernate.Session;

import com.gxf.beans.PlayControl;
import com.gxf.dao.BaseDao;
import com.gxf.dao.PlayControlDao;

public class PlayControlDaoImpl implements PlayControlDao{
	
	BaseDao baseDao = new BaseDao();
	
	/* (non-Javadoc)
	 * @see com.gxf.dao.PlayControlDao#deletePlayControl(com.gxf.beans.PlayControl)
	 */
	@Override
	public void deletePlayControl(PlayControl playControl) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		session.delete(playControl);
		
		session.getTransaction().commit();
		
		session.close();
	}

}
