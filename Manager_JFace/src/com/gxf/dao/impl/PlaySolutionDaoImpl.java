package com.gxf.dao.impl;

import java.util.ArrayList;
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
	public PlaySolution querySolutionByName(String name){
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

	/* (non-Javadoc)
	 * @see com.gxf.dao.PlaySolutionDao#queryAllSolutions()
	 */
	@Override
	public List<PlaySolution> queryAllSolutions() {
		List<PlaySolution> playSolutions = new ArrayList<PlaySolution>();
		Session session = baseDao.getSession();
		Transaction tx =  session.beginTransaction();
		String hql = "from PlaySolution";
		Query query = session.createQuery(hql);
		playSolutions = query.list();
		tx.commit();
		session.close();
		
		return playSolutions;
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.PlaySolutionDao#deletePlaySolution(com.gxf.beans.PlaySolution)
	 */
	@Override
	public void deletePlaySolution(PlaySolution playSolution) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		session.delete(playSolution);
		session.getTransaction().commit();
		session.close();
		
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.PlaySolutionDao#deletePlaySolutinById(int)
	 */
	@Override
	public void deletePlaySolutinById(int playSolutionId) {
		Session session = baseDao.getSession();
		String hql = "delete from PlaySolution p where p.id = ?";
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString(0, String.valueOf(playSolutionId));
		query.executeUpdate();
		
		session.getTransaction().commit();
		session.close();
	}
}
