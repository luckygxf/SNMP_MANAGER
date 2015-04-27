package com.gxf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gxf.beans.Communication;
import com.gxf.dao.BaseDao;
import com.gxf.dao.CommunicationDao;

public class CommunicationDaoImpl implements CommunicationDao {
	private BaseDao baseDao = new BaseDao();
	
	/* (non-Javadoc)
	 * @see com.gxf.dao.CommunicationDao#addCommunication(com.gxf.beans.Communication)
	 */
	@Override
	public void addCommunication(Communication communication) {
		SessionFactory sessionFactory = baseDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(communication);
		tx.commit();
		
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.CommunicationDao#queryCommunicatioByName(java.lang.String)
	 */
	@Override
	public Communication queryCommunicatioByName(String name) {		
		Communication result = null;
		SessionFactory sessionFactory = baseDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "from Communication c where c.name = ?";
		Query query = session.createQuery(hql);
		query.setString(0, name);
		List<Communication> listOfCommunication = query.list();
		
		result = listOfCommunication.get(0);
		return result;
	}

}
