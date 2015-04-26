package com.gxf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/*******************************************************************************************
 * 自定义协议                                                                                                                                                                                                                  *
 *                    																	   *
 * 完整的数据																			   *
 * (image_start)(image_file_name)(image_file_name_end)(image_file_length)(image)(image_end)*
 * 																						   *
 * *****************************************************************************************/

/**
 * 通过自定义协议发送图片
 * 作为发送图片的客户端
 * @author Administrator
 *
 */
public class SendPic {
	private Socket socket;
	private OutputStream picOutputStream;
	private InputStream picInputStream;
	private Util util = new Util();
	private String dstIp;								//目标主机IP
	
	//协议中的三个字段
	//image_start
	private final String IMAGE_START = "image:";	
	//image_file_name_end
	private final String IMAGE_FILE_NAME_END = "?";
	//image_end
	private final String MESSGE_END = "over";
	
	//接收进程端口号16201
	private final int port = 16201;
	
	//编码方式
    private final String DEFAULT_ENCODE = "UTF-8";
    private final String ISO_ENCODE = "ISO-8859-1";
	
	public SendPic(){
		
	}
	
	/**
	 * 初始化套接字
	 */
	public void init(){
		try {
			socket = new Socket(this.dstIp, port);
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送执行指定图片
	 * @param picPath
	 */
	public void send(String ip, String picPath){
		this.dstIp = ip;
		init();
		File picFile = new File(picPath);
		try {
			picOutputStream = socket.getOutputStream();							//向socket中写入数据的输出流
			picInputStream = new FileInputStream(picFile);			//通过文件输入流获取图片内容
			
			//先发送image_start
			picOutputStream.write(IMAGE_START.getBytes());
			
			//发送文件名image_file_name
			String fileName = picFile.getName();								//获取文件名			
			picOutputStream.write(fileName.getBytes(DEFAULT_ENCODE));
						
			
			//发送文件名字结束符image_file_name_end
			picOutputStream.write(IMAGE_FILE_NAME_END.getBytes());
			
			//发送数据长度
			long picLength = picFile.length();			
			byte bytesOfPicLength[] = util.longToBytes(picLength);				
			picOutputStream.write(bytesOfPicLength);
			
			//发送图片内容
			int length = 0;
			byte bytes[] = new byte[1024];
			long zipSize = 0;
			
			while((length = picInputStream.read(bytes)) > 0){
				picOutputStream.write(bytes, 0, length);
				zipSize += length;
			} 
			System.out.println("zipSize = " + zipSize);
			
			//发送消息结束标志message_end
			picOutputStream.write(MESSGE_END.getBytes());
			picOutputStream.flush();
			
			//关闭socket outputstream
			close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				picInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭socket和输入输出流，释放资源
	 */
	private void close(){
		try {
			socket.close();
			picOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
