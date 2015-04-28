package com.gxf.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class BaseDao {
	private static SessionFactory sessionFactory = null;
	
	/**
	 * 获取hibernate session
	 * @return
	 */
	public Session getSession(){
		SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        
        return session;
	}
	
	/**
	 * sessionFactory创建很耗时，采用单列模式实现
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
