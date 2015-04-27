package com.gxf.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gxf.beans.Display;
import com.gxf.dao.BaseDao;
import com.gxf.dao.DisplayDao;

public class DisplayDaoImpl implements DisplayDao {
	private BaseDao baseDao = new BaseDao();
	
	@Override
	public void addDisplay(Display display) {
		SessionFactory sessionFactory = baseDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(display);
		tx.commit();
		session.close();
	}

}
