package com.gxf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gxf.beans.Display;
import com.gxf.dao.BaseDao;
import com.gxf.dao.DisplayDao;

public class DisplayDaoImpl implements DisplayDao {
	
	private BaseDao baseDao = new BaseDao();
	
	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#addDisplay(com.gxf.beans.Display)
	 */
	@Override
	public void addDisplay(Display display) {
		SessionFactory sessionFactory = baseDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(display);
		tx.commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#queryAllDisplay()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Display> queryAllDisplay() {
		List<Display> listOfDisplay = new ArrayList<Display>();
		Session session = baseDao.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Display";
		Query query = session.createQuery(hql);
		listOfDisplay = query.list();
		tx.commit();
		session.close();
		
		return listOfDisplay;
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#deleteDisplayById(int)
	 */
	@Override
	public void deleteDisplayById(int displayId) {
		Session session = baseDao.getSession();
		String hql = "delete Display d where d.id = ?";
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString(0, String.valueOf(displayId));
		query.executeUpdate();
		
		tx.commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#queryDisplayByName(java.lang.String)
	 */
	@Override
	public Display queryDisplayByName(String name) {
		Session session = baseDao.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Display d where d.name = ?";
		Query query = session.createQuery(hql);
		query.setString(0, name);
		List<Display> listOfDisplays = query.list();
		tx.commit();
		session.close();
		
		return listOfDisplays.get(0);
	}
	
}
