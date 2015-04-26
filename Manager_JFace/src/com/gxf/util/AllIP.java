package com.gxf.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * 广播消息获取局域网所有主机IP
 * 思路
 * 一个线程不断发送消息,获取主机消息
 * 一个线程不断获取消息,从代理发出的消息
 * @author Administrator
 *
 */
public class AllIP {
	private static final String REQUEST = "TELL ME YOUR IP";				//请求字符串
	private final int PACKAGESIZE = 1024;							//数据包大小
	public static List<String> IPS = new ArrayList<String>();
	private static DatagramSocket datagramSocket;
	private static final int PORTNUM = 10000;						//端口号
	private static Util util = new Util();
	
	
	public static void main(String args[]) throws Exception{
		init();
//		Thread sendIpThread = new GetAllIP().new SendAllThread(REQUEST, datagramSocket);
//		sendIpThread.start();
//		sendIpThread.join();
		SendAllThread sendAllIP = new AllIP().new SendAllThread(REQUEST, datagramSocket);
		sendAllIP.run();
		Thread receiveIpThread = new AllIP().new ReceiveIPdata(IPS, datagramSocket);
		receiveIpThread.start();										//接收代理进程发回的ip数据包
		receiveIpThread.join();											//等待线程结束
		util.showListOfString(IPS); 									//输出局域网所有主机的IP
	}
	
	
	/**
	 * 对外提供的公开接口
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public List<String> getAllIP() {
		init();
		SendAllThread sendAllIP = new AllIP().new SendAllThread(REQUEST, datagramSocket);
		sendAllIP.run();
		Thread receiveIpThread = new AllIP().new ReceiveIPdata(IPS, datagramSocket);
		receiveIpThread.start();										//接收代理进程发回的ip数据包
		try {
			receiveIpThread.join();
		} catch (InterruptedException e) {
			System.out.println("UDP broadcast failed!");
			System.out.println(e.getMessage());
		}											//等待线程结束
		
		return IPS;
	}
	
	/**
	 * 广播消息
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
				byte arrayOfByte[] = message.getBytes();				//发送数据包
				InetAddress inetAddress;
				try {
//					Thread.sleep(1000);
					inetAddress = InetAddress.getByName("255.255.255.255");
					DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, 
					inetAddress, PORTNUM);
//					datagramSocket = new DatagramSocket();	//发送数据包socket
					datagramSocket.send(datagramPacket); 					//发送数据包
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					datagramSocket.close(); 								//关闭socket
				}
//			}//while
		}
		
		public void sendAllMessage(String message){
			byte arrayOfByte[] = message.getBytes();				//发送数据包
			int portNum = 20000;									//端口号
			InetAddress inetAddress;
			DatagramSocket datagramSocket = null;
			try {
				inetAddress = InetAddress.getByName("210.38.235.184");
				DatagramPacket datagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, 
				inetAddress, portNum);
				datagramSocket = new DatagramSocket(portNum);	//发送数据包socket
				datagramSocket.send(datagramPacket); 					//发送数据包
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
//				datagramSocket.close(); 								//关闭socket
			}
			
		}//send
	}
	
	/**
	 * 获取IP数据包
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
			}  	//接收socket
			while(true){
				byte arrayOfByte[] = new byte[PACKAGESIZE];
				try {
//					Thread.sleep(1000);
								//接收端口号 
					
					datagramSocket_rec.setSoTimeout(2000);  			//定时器10s
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
	 * 初始化系统需要的参数
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
