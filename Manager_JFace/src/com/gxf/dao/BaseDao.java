package com.gxf.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class BaseDao {
	private static SessionFactory sessionFactory = null;
	
	/**
	 * ��ȡhibernate session
	 * @return
	 */
	public Session getSession(){
		SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        
        return session;
	}
	
	/**
	 * sessionFactory�����ܺ�ʱ�����õ���ģʽʵ��
	 * @return
	 */
	public SessionFactory getSessionFactory(){		
        if(sessionFactory == null){
        	Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        	
        return sessionFactory;
	}

}
