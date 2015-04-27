package com.gxf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gxf.beans.PlaySolution;
import com.gxf.dao.BaseDao;
import com.gxf.dao.PlaySolutionDao;

public class PlaySolutionDaoImpl implements PlaySolutionDao {
	BaseDao baseDao = new BaseDao();
	
	/* (non-Javadoc)
	 * @see com.gxf.dao.PlaysolutionDao#querySolutionByNanme(java.lang.String)
	 */
	public PlaySolution querySolutionByNanme(String name){
		SessionFactory sessionFactory = baseDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "from PlaySolution s where s.name = ?";
		Query query = session.createQuery(hql);
		query.setString(0, name);
		List<PlaySolution> listOfPlaySolution = query.list();
		
		return listOfPlaySolution.get(0);
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.PlaySolutionDao#addPlaySolution(com.gxf.beans.PlaySolution)
	 */
	@Override
	public void addPlaySolution(PlaySolution playSolution) {
		SessionFactory sessionFactory = baseDao.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(playSolution);
		tx.commit();		
	}
}
