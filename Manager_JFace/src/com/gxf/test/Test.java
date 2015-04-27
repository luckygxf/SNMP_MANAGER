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
import com.gxf.beans.PlaySolution;
import com.gxf.dao.CommunicationDao;
import com.gxf.dao.DisplayDao;
import com.gxf.dao.PlaySolutionDao;
import com.gxf.dao.impl.CommunicationDaoImpl;
import com.gxf.dao.impl.DisplayDaoImpl;
import com.gxf.dao.impl.PlaySolutionDaoImpl;
import com.gxf.util.SendPic;
import com.gxf.util.Util;

/**
 * ���Ա�д��api�ӿ�
 * @author Administrator
 *
 */
public class Test {
	
	public static void main(String args[]){
		CommunicationDao comuCommunicationDao  = new CommunicationDaoImpl();
		PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
		DisplayDao displayDao = new DisplayDaoImpl();
//		Communication communication = new Communication();
//		communication.setName("����ͨ��");
//		communication.setPort(5100);
//		comuCommunicationDao.addCommunication(communication);
		Display display = new Display();
		display.setName("����·");
		display.setComment("����·���ŷ���");
		display.setType("˫ɫ��");
		display.setCurPlaySolution(playSolutionDao.querySolutionByNanme("��Ӱ����"));
		display.getSolutions().add(playSolutionDao.querySolutionByNanme("��Ӱ����"));
		display.getCommunications().add(comuCommunicationDao.queryCommunicatioByName("����ͨѶ"));
		
		displayDao.addDisplay(display);
	}
}
