package com.gxf.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * �㲥��Ϣ��ȡ��������������IP
 * ˼·
 * һ���̲߳��Ϸ�����Ϣ,��ȡ������Ϣ
 * һ���̲߳��ϻ�ȡ��Ϣ,�Ӵ���������Ϣ
 * @author Administrator
 *
 */
public class AllIP {
	private static final String REQUEST = "TELL ME YOUR IP";				//�����ַ���
	private final int PACKAGESIZE = 1024;							//���ݰ���С
	public static List<String> IPS = new ArrayList<String>();
	private static DatagramSocket datagramSocket;
	private static final int PORTNUM = 10000;						//�˿ں�
	private static Util util = new Util();
	
	
	public static void main(String args[]) throws Exception{
		init();
//		Thread sendIpThread = new GetAllIP().new SendAllThread(REQUEST, datagramSocket);
//		sendIpThread.start();
//		sendIpThread.join();
		SendAllThread sendAllIP = new AllIP().new SendAllThread(REQUEST, datagramSocket);
		sendAllIP.run();
		Thread receiveIpThread = new AllIP().new ReceiveIPdata(IPS, datagramSocket);
		receiveIpThread.start();										//���մ�����̷��ص�ip���ݰ�
		receiveIpThread.join();											//�ȴ��߳̽���
		util.showListOfString(IPS); 									//�������������������IP
	}
	
	
	/**
	 * �����ṩ�Ĺ����ӿ�
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public List<String> getAllIP() {
		init();
		SendAllThread sendAllIP = new AllIP().new SendAllThread(REQUEST, datagramSocket);
		sendAllIP.run();
		Thread receiveIpThread = new AllIP().new ReceiveIPdata(IPS, datagramSocket);
		receiveIpThread.start();										//���մ�����̷��ص�ip���ݰ�
		try {
			receiveIpThread.join();
		} catch (InterruptedException e) {
			System.out.println("UDP broadcast failed!");
			System.out.println(e.getMessage());
		}											//�ȴ��߳̽���
		
		return IPS;
	}
	
	/**
	 * �㲥��Ϣ
	 * @author Administrator
	 *
	 */
	class SendAllThread{
		String message;
		DatagramSocket datagramSocket = null;
		
		public SendAllThread(String message, DatagramSocket datagramSocket){
			this.message = message;
			this.datagramSocket = datagramSocket;
		}
		
		public void run(){
//			while(true){
				byte arrayOfByte[] = message.getBytes();				//�������ݰ�
				InetAddress inetAddress;
				try {
//					Thread.sleep(1000);
					inetAddress = InetAddress.getByName("255.255.255.255");
					DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, 
					inetAddress, PORTNUM);
//					datagramSocket = new DatagramSocket();	//�������ݰ�socket
					datagramSocket.send(datagramPacket); 					//�������ݰ�
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					datagramSocket.close(); 								//�ر�socket
				}
//			}//while
		}
		
		public void sendAllMessage(String message){
			byte arrayOfByte[] = message.getBytes();				//�������ݰ�
			int portNum = 20000;									//�˿ں�
			InetAddress inetAddress;
			DatagramSocket datagramSocket = null;
			try {
				inetAddress = InetAddress.getByName("210.38.235.184");
				DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, 
				inetAddress, portNum);
				datagramSocket = new DatagramSocket(portNum);	//�������ݰ�socket
				datagramSocket.send(datagramPacket); 					//�������ݰ�
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
//				datagramSocket.close(); 								//�ر�socket
			}
			
		}//send
	}
	
	/**
	 * ��ȡIP���ݰ�
	 * @author Administrator
	 *
	 */
	private class ReceiveIPdata extends Thread{
		List<String> IPS = null;
		DatagramSocket datagramSocket_rec = null;
		
		public ReceiveIPdata(List<String> IPS, DatagramSocket datagramSocket_rec){
			this.IPS = IPS;
			this.datagramSocket_rec = datagramSocket_rec;
		}
		public void run(){
			int portNum = 10000;
			try {
				datagramSocket_rec = new DatagramSocket(portNum);
			} catch (SocketException e1) {
				System.out.println(e1.getMessage());
//				e1.printStackTrace();
			}  	//����socket
			while(true){
				byte arrayOfByte[] = new byte[PACKAGESIZE];
				try {
//					Thread.sleep(1000);
								//���ն˿ں� 
					
					datagramSocket_rec.setSoTimeout(2000);  			//��ʱ��10s
					DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length);
					datagramSocket_rec.receive(datagramPacket);
					
					String data_str = new String(datagramPacket.getData(),0, datagramPacket.getLength());
//					System.out.println(data_str);
					if(data_str.length() != 0)
						IPS.add(data_str);
//					datagramSocket_rec.close();
				} catch (Exception e) {
//					System.out.println(e.getMessage());
					datagramSocket_rec.close();
					System.out.println("receive socket time out! in AllIP.ReceiveIPdata()");
					break;
//					e.printStackTrace();
				} finally{
					
				}
			}
		}
	}
	
	/**
	 * ��ʼ��ϵͳ��Ҫ�Ĳ���
	 */
	public static void init(){
		IPS = new ArrayList<String>();
		try {
			datagramSocket = new DatagramSocket(PORTNUM);
		} catch (SocketException e) {
			e.printStackTrace();
			System.out.println("socket init failed!");
		}
	}
}
