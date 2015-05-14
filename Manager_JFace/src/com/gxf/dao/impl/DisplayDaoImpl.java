package com.gxf.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gxf.beans.Display;
import com.gxf.beans.Picture;
import com.gxf.beans.PlayControl;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.BaseDao;
import com.gxf.dao.CommunicationDao;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlayControlDao;

public class DisplayDaoImpl implements DisplayDao {
	
	private BaseDao baseDao = new BaseDao();
	private CommunicationDao communicationDao = new CommunicationDaoImpl();
	private PlayControlDao playControlDaol = new PlayControlDaoImpl();
	
	
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
		//获取display对象
		Display display = (Display)session.get(Display.class, displayId);
		
		//删除对应的播放方案-->图片-->控制信息
		Set<PlaySolution> listOfPlaySolution = display.getSolutions(); 
		for(Iterator<PlaySolution> it_playSolution = listOfPlaySolution.iterator(); it_playSolution.hasNext(); ){
			PlaySolution playSolution = it_playSolution.next();
			Set<Picture> setOfPicture = playSolution.getPictures();
			//获取对应的图片信息
			for(Iterator<Picture> it_picture = setOfPicture.iterator(); it_picture.hasNext();){
				Picture picture = it_picture.next();
				//获取要删除的播放控制信息
				PlayControl playControlToDelete = picture.getPlayControl();
				playControlDaol.deletePlayControl(playControlToDelete);
			}
		}
		
		
		int communicationId = display.getCommunication().getId();
		
		String hql = "delete Display d where d.id = ?";
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString(0, String.valueOf(displayId));
		query.executeUpdate();
		tx.commit();
		session.close();
		//删除对应的通信方式
		communicationDao.deleteCommunication(communicationDao.queryCommunicationById(communicationId));
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
		if(listOfDisplays == null || listOfDisplays.size() == 0)
			return null;
		return listOfDisplays.get(0);
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#updateDisplay(com.gxf.beans.Display)
	 */
	@Override
	public void updateDisplay(Display display) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		session.update(display);
		session.getTransaction().commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#queryDisplayById(int)
	 */
	@Override
	public Display queryDisplayById(int id) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		Display display = (Display)session.get(Display.class, id);
		session.getTransaction().commit();
		session.close();
		
		return display;
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.DisplayDao#updateCurPlaySolution(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateCurPlaySolution(String displayName,
			String curPlaySolutionName) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		String hql = "update Display d set d.curPlaySolutionName = ? where d.name = ?";
		Query query = session.createQuery(hql);
		query.setString(0, curPlaySolutionName);
		query.setString(1, displayName);
		
		query.executeUpdate();
		session.getTransaction().commit();
		
		session.close();
	}
	
}
