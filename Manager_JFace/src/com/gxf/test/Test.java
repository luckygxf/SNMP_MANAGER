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
 * 测试编写的api接口
 * @author Administrator
 *
 */
public class Test {
	
	public static void main(String args[]){
		CommunicationDao comuCommunicationDao  = new CommunicationDaoImpl();
		PlaySolutionDao playSolutionDao = new PlaySolutionDaoImpl();
		DisplayDao displayDao = new DisplayDaoImpl();
//		Communication communication = new Communication();
//		communication.setName("串口通信");
//		communication.setPort(5100);
//		comuCommunicationDao.addCommunication(communication);
		Display display = new Display();
		display.setName("孙文路");
		display.setComment("孙文路播放方案");
		display.setType("双色屏");
		display.setCurPlaySolution(playSolutionDao.querySolutionByNanme("火影忍者"));
		display.getSolutions().add(playSolutionDao.querySolutionByNanme("火影忍者"));
		display.getCommunications().add(comuCommunicationDao.queryCommunicatioByName("网络通讯"));
		
		displayDao.addDisplay(display);
	}
}
