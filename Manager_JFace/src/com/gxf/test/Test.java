package com.gxf.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.gxf.beans.Communication;
import com.gxf.beans.Display;
import com.gxf.beans.Picture;
import com.gxf.beans.PlaySolution;
import com.gxf.dao.CommunicationDao;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PictureDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.CommunicationDaoImpl;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PictureDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.SendPic;
import com.gxf.util.Util;

/**
 * ²âÊÔ±àÐ´µÄapi½Ó¿Ú
 * @author Administrator
 *
 */
public class Test {
	
	public static void main(String args[]){
		PictureDao picDao = new PictureDaoImpl();
		Picture pic = picDao.queryPicByPlayOrder(2);
		System.out.println(pic.getId());
	}
}
