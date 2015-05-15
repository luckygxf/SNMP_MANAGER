package com.gxf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.gxf.beans.Picture;
import com.gxf.beans.PlayControl;
import com.gxf.dao.BaseDao;
import com.gxf.dao.PictureDao;
import com.gxf.dao.PlayControlDao;

public class PictureDaoImpl implements PictureDao {
	
	private BaseDao baseDao = new BaseDao();
	private PlayControlDao playControlDao = new PlayControlDaoImpl();
	
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

	/* (non-Javadoc)
	 * @see com.gxf.dao.PictureDao#deletePictureByPicPath(java.lang.String)
	 */
	@Override
	public void deletePictureByPicPath(String picPath) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		//先删除对应的控制信息
		String hql_query = "from Picture p where p.picPath = ?";
		Query queryControl = session.createQuery(hql_query);
		queryControl.setString(0, picPath);
		Picture picture = (Picture) queryControl.list().get(0);
		PlayControl playControl = picture.getPlayControl();
		playControlDao.deletePlayControl(playControl);
		
		//删除图片
		String hql = "delete Picture p where p.picPath = ?";
		Query query = session.createQuery(hql);
		query.setString(0, picPath);
		query.executeUpdate();
		
		//提交事务，关闭session
		session.getTransaction().commit();
		session.close();
		
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.PictureDao#queryByPicPath(java.lang.String)
	 */
	@Override
	public Picture queryByPicPath(String picPath) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		
		String hql = "from Picture p where p.picPath = ?";
		Query query = session.createQuery(hql);
		query.setString(0, picPath);
		List<Picture> listOfPicture = query.list();		
		Picture picture = listOfPicture.get(0);
		
		session.getTransaction().commit();
		session.close();
		
		return picture;
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.PictureDao#updatePicture(com.gxf.beans.Picture)
	 */
	@Override
	public void updatePicture(Picture picture) {
		Session session = baseDao.getSession();
		session.beginTransaction();
		
		session.update(picture);
		
		session.getTransaction().commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.gxf.dao.PictureDao#queryPictureCount()
	 */
	@Override
	public int queryPictureCount() {
		//获取session 开启事务
		Session session = baseDao.getSession();
		session.beginTransaction();
		
		//执行查询
		String hql = "select count(*) from Picture";
		Query query = session.createQuery(hql);
		Object count = query.uniqueResult();
		
		//提交事务 关闭session
		session.getTransaction().commit();
		session.close();
		
		
		//返回查询结果
		if(count == null)
			return 0;
		else
			return ((Long)count).intValue();
	}

	@Override
	public Picture queryPicByPlayOrder(int playOrder) {
		//获取session 开启事务
		Session session = baseDao.getSession();
		session.beginTransaction();
		
		//执行查询
		String hql = "from Picture p where p.playOrder =:playOrder";
		Query query = session.createQuery(hql);
		query.setParameter("playOrder", playOrder);
		List<Picture> listOfPic = query.list();
		Picture picture = listOfPic.get(0);
		
		//提交事务 关闭session
		session.getTransaction().commit();
		session.close();
		
		//返回查询结果
		return picture;
	}

}
