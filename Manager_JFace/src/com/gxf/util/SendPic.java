package com.gxf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/*******************************************************************************************
 * �Զ���Э��                                                                                                                                                                                                                  *
 *                    																	   *
 * ����������																			   *
 * (image_start)(image_file_name)(image_file_name_end)(image_file_length)(image)(image_end)*
 * 																						   *
 * *****************************************************************************************/

/**
 * ͨ���Զ���Э�鷢��ͼƬ
 * ��Ϊ����ͼƬ�Ŀͻ���
 * @author Administrator
 *
 */
public class SendPic {
	private Socket socket;
	private OutputStream picOutputStream;
	private InputStream picInputStream;
	private Util util = new Util();
	private String dstIp;								//Ŀ������IP
	
	//Э���е������ֶ�
	//image_start
	private final String IMAGE_START = "image:";	
	//image_file_name_end
	private final String IMAGE_FILE_NAME_END = "?";
	//image_end
	private final String MESSGE_END = "over";
	
	//���ս��̶˿ں�16201
	private final int port = 16201;
	
	//���뷽ʽ
    private final String DEFAULT_ENCODE = "UTF-8";
    private final String ISO_ENCODE = "ISO-8859-1";
	
	public SendPic(){
		
	}
	
	/**
	 * ��ʼ���׽���
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
	 * ����ִ��ָ��ͼƬ
	 * @param picPath
	 */
	public void send(String ip, String picPath){
		this.dstIp = ip;
		init();
		File picFile = new File(picPath);
		try {
			picOutputStream = socket.getOutputStream();							//��socket��д�����ݵ������
			picInputStream = new FileInputStream(picFile);			//ͨ���ļ���������ȡͼƬ����
			
			//�ȷ���image_start
			picOutputStream.write(IMAGE_START.getBytes());
			
			//�����ļ���image_file_name
			String fileName = picFile.getName();								//��ȡ�ļ���			
			picOutputStream.write(fileName.getBytes(DEFAULT_ENCODE));
						
			
			//�����ļ����ֽ�����image_file_name_end
			picOutputStream.write(IMAGE_FILE_NAME_END.getBytes());
			
			//�������ݳ���
			long picLength = picFile.length();			
			byte bytesOfPicLength[] = util.longToBytes(picLength);				
			picOutputStream.write(bytesOfPicLength);
			
			//����ͼƬ����
			int length = 0;
			byte bytes[] = new byte[1024];
			long zipSize = 0;
			
			while((length = picInputStream.read(bytes)) > 0){
				picOutputStream.write(bytes, 0, length);
				zipSize += length;
			} 
			System.out.println("zipSize = " + zipSize);
			
			//������Ϣ������־message_end
			picOutputStream.write(MESSGE_END.getBytes());
			picOutputStream.flush();
			
			//�ر�socket outputstream
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
	 * �ر�socket��������������ͷ���Դ
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
