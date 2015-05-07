package com.gxf.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.gxf.beans.Picture;
import com.gxf.beans.PlayControl;
import com.gxf.entities.DisplayDevice;
import com.gxf.entities.FontFormat;

/**
 * �����࣬�ṩһЩ���÷���
 * @author Administrator
 *
 */
public class Util {
	
	/**
	 * ����б��е��ַ���
	 * @param listOfString
	 */
	public void showListOfString(List<String> listOfString){
		for(int i = 0; i < listOfString.size(); i++)
			System.out.println(listOfString.get(i));
	}
	
	/**
	 * ���������ʽҪ����Ļ������������ͼƬ
	 * @param content
	 * @param fontFormat
	 * @param displayDevice
	 */
	public void createImage(String content, FontFormat fontFormat, DisplayDevice displayDevice){
		String curPath = System.getProperty("user.dir");										//��ǰ����·����ΪͼƬ·��
        String imageName = "\\image.jpg";
        String imagePath = curPath + imageName;
        File image = new File(imagePath);  
        int width = displayDevice.getWidth();													//��Ļ��Ⱥ͸߶�
        int height = displayDevice.getHeight();        
        
        Font font = new Font(fontFormat.getStyle(), Font.BOLD, fontFormat.getSize()); 			//������
        
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   		//�����������пɷ���ͼ�����ݻ�������
        Graphics2D g2 = (Graphics2D)bi.getGraphics();   										//�ṩ�Լ�����״������ת������ɫ������ı����ָ�Ϊ���ӵĿ���
        g2.setBackground(Color.black);   
        g2.clearRect(0, 0, width, height);   
        g2.setColor(fontFormat.getColor()); 													//����������ɫ
        g2.setFont(font);
//        g2.setPaint(Color.white);   
           
        FontRenderContext context = g2.getFontRenderContext();   								//��ȷ�����ı��������Ϣ����
        Rectangle2D bounds = font.getStringBounds(content, context);   							//��ͨ��λ�� (x,y) �ͳߴ� (w x h) ����ľ���
        double x = (width - bounds.getWidth()) / 2;   
        double y = (height - bounds.getHeight()) / 2;	  										//�� double ���ȷ��ش�����εĸ߶� 
        double ascent = -bounds.getY();   														//�� double ���ȷ��ش���������Ͻǵ� Y ���ꡣ 
        double baseY = y + ascent;   
           
        g2.drawString(content, (int)x, (int)baseY);   
           
        try {
			ImageIO.write(bi, "jpg", image);													//���������д��ͼ��ĳ�����
			
		} catch (IOException e) {
			System.out.println("create image failed!");
			e.printStackTrace();
		}   
	}
	
	/**
	 * ��ȡ��Ƭ������
	 * @param filePath
	 * @return
	 */
	public int [][]getImageRGB(String filePath){
		int pixel[][] = null;						//��������
		File fileToDecode = new File(filePath);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(fileToDecode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		
		pixel = new int[width][height];				//��������ռ�
		for(int i = 0; i < width; i++){
			for(int j = 0 ; j < height; j++){
				pixel[i][j] = bufferedImage.getRGB(i, j) & 0xFFFFFF;
//				System.out.println(pixel[i][j]);
			}
		}//for
		
		return pixel;
	}
	
	/**
	 * ��ȡ��Ƭ������
	 * @param filePath
	 * @return
	 */
	public byte [][]getImageRGBToByteArray(String filePath){
		byte pixel[][] = null;						//��������
		File fileToDecode = new File(filePath);
		BufferedImage bufferedImage = null;
		try{
			bufferedImage = ImageIO.read(fileToDecode);
		} catch(IOException e){
			e.printStackTrace();
		}
		
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
//		System.out.println("width = " + width);
//		System.out.println("height = " + height);
		pixel = new byte[height][width / 8];
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width / 8; j++){
				for(int z = 0; z <= 7; z++)
				{
					int pixel_local = bufferedImage.getRGB(j * 8 + z, i);
					if((pixel_local & 0xFFFFFF) != 0x000000){
//						pixel[j][i] |= (1 << (7 - z));
						pixel[i][j] |= (1 << (7 - z));//?���滹������
					}//if
				}//for
			}//for
		}//for
		
		return pixel;
	}
	
	/**
	 * ��ȡ����ͼƬ��·��
	 * @return
	 */
	public String getImagePath(){
		String curPath = System.getProperty("user.dir");					//��ǰ����·����ΪͼƬ·��
        String imageName = "\\image.jpg";
        String imagePath = curPath + imageName;
        
        return imagePath;
	}
	
	/**
	 * ��ȡ����ϵͳ֧�ֵ������ʽ
	 * @return
	 */
	public List<String> getFontsInOS(){
		List<String> list = new ArrayList<String>();
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		for (int i = 0; i < fonts.length; i++) {
			list.add(fonts[i].getFamily());// ��ȡ����
		}
		List<String> array = removeDeuplicate(list);// ȥ���ظ�
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = array.get(i).toString();
		}
		// ������ĸ����ʼ
		Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
		Arrays.sort(result, com);
		List<String> font = new ArrayList<String>();
		for (String i : result) {
			font.add(i);
		}
		// ������ĸ�������
		return font;
	}
	
	/**
	 * ȥ���ظ��������ʽ
	 * @param arlList
	 * @return
	 */
	public List<String> removeDeuplicate(List<String> arlList) {
		HashSet<String> h = new HashSet<String>(arlList);
		arlList.clear();
		arlList.addAll(h);
		List<String> list = new ArrayList<String>();
		list = arlList;
		return list;
	}
	
	/**
	 * ��long��������ת����8���ֽڵ�byte����
	 * java ��longռ8���ֽ�
	 * @return
	 */
	public byte[] longToBytes(long data){
		byte result[] = new byte[8];
		
		result[7] = (byte)(data & 0xff);
		result[6] = (byte)((data >>> 8) & 0xff);
		result[5] = (byte)((data >>> 16) & 0xff);
		result[4] = (byte)((data >>> 24) & 0xff);
		result[3] = (byte)((data >>> 32) & 0xff);
		result[2] = (byte)((data >>> 40) & 0xff);
		result[1] = (byte)((data >>> 48) & 0xff);
		result[0] = (byte)((data >>> 40) & 0xff);
		
		return result;
	}
	
	/**
     * ��ȡ��ǰ����Ŀ¼
     * @return
     */
    public String getCurrentProjectPath(){
    	String curPath = System.getProperty("user.dir");
    	
    	return curPath;
    }
    
    /**
     * ����config����config.xml,�ŵ����ŷ�������
     * @param config
     */
    public void createConfigXml(String solutionName, Config configInfo){    	
    	Document document = DocumentHelper.createDocument();
    	
        Element config = document.addElement("config");
        //style
        Element style = config.addElement("style");
        style.addText(String.valueOf(configInfo.getPlayStyle()));
        //timeInterval
        Element timeInterval = config.addElement("timeInterval");
        timeInterval.addText(String.valueOf(configInfo.getPlayTimeInterval()));
        //date_start
        Element date_start = config.addElement("date_start");
        Element day = date_start.addElement("day_start");
        day.addText(String.valueOf(configInfo.getDay_start()));
        Element month = date_start.addElement("month_start");
        month.addText(String.valueOf(configInfo.getMonth_start()));
        Element year = date_start.addElement("year_start");
        year.addText(String.valueOf(configInfo.getYear_start()));
        
        //date_end
        date_start = config.addElement("date_end");
        day = date_start.addElement("day_end");
        day.addText(String.valueOf(configInfo.getDay_end()));
        month = date_start.addElement("month_end");
        month.addText(String.valueOf(configInfo.getMonth_end()));
        year = date_start.addElement("year_end");
        year.addText(String.valueOf(configInfo.getYear_end()));
        
        //time_start
        Element time_start = config.addElement("time_start");
        Element second = time_start.addElement("second_start");
        second.addText(String.valueOf(configInfo.getSec_start()));
        Element minute = time_start.addElement("minute_start");
        minute.addText(String.valueOf(configInfo.getMin_start()));
        Element hour = time_start.addElement("hour_start");
        hour.addText(String.valueOf(configInfo.getHour_start()));
        
        //time_end
        time_start = config.addElement("time_end");
        second = time_start.addElement("second_end");
        second.addText(String.valueOf(configInfo.getSec_end()));
        minute = time_start.addElement("minute_end");
        minute.addText(String.valueOf(configInfo.getMin_end()));
        hour = time_start.addElement("hour_end");
        hour.addText(String.valueOf(configInfo.getHour_end()));
        
        //weekdays
        Element weekdays = config.addElement("weekdays");
        boolean weeks[] = configInfo.getWeekdays();
        for(int i = 0; i < weeks.length; i++){
        	String elementName = "weekday" + i;
        	Element element = weekdays.addElement(elementName);
        	String elementText = String.valueOf(weeks[i] ? 1 : 0);
        	element.addText(elementText);
        }
        //�򲥷ŷ���������������ļ�
        String dic = getCurrentProjectPath() + File.separator + "playSolutions" + File.separator + solutionName;
        String configFilePath = dic + File.separator + solutionName + ".xml";
//        FileWriter out;
//		try {
//			out = new FileWriter(new File(configFilePath));
//			document.write(out);
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}        
		
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        File file = new File(configFilePath);
        
        try {
        	XMLWriter writer = new  XMLWriter(new FileOutputStream(file), format);
			writer.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
    
    /**
     * ���ݲ��ŷ�������ѹ���ļ�
     * ѹ���ļ���ΪdiplayName + playSolutinName,�����е�+���ȹ���.zip
     * @param solutionName
     */
    public void compressPlaySoution(String displayName, String solutionName) {
		String dicPath = getCurrentProjectPath() + File.separator + displayName + File.separator  + solutionName;
		System.out.println("dicPath = " + dicPath);
		String targetPath = getCurrentProjectPath() + File.separator + displayName + File.separator + displayName + "+" + solutionName +  ".zip";
		
		File file = new File(dicPath) ;												// ����Ҫѹ�����ļ���  
        File zipFile = new File(targetPath) ;										// ����ѹ���ļ�����  
        InputStream input = null ;  												// �����ļ�������  
        ZipOutputStream zipOut = null ; 											// ����ѹ��������  
        try{
        	zipOut = new ZipOutputStream(new FileOutputStream(zipFile)) ;  
            zipOut.setComment("compressed by GXF") ;  								// ����ע��  
            int temp = 0 ;  
            if(file.isDirectory()){ 												// �ж��Ƿ����ļ���  
                File lists[] = file.listFiles() ;   								// �г�ȫ���ļ�  
                for(int i=0;i<lists.length;i++){  
                    input = new FileInputStream(lists[i]) ; 						// �����ļ���������  
                    zipOut.putNextEntry(new ZipEntry(file.getName()  
                        +File.separator+lists[i].getName())) ;  					// ����ZipEntry����  
                    while((temp=input.read())!=-1){ 								// ��ȡ����  
                        zipOut.write(temp) ;    									// ѹ�����  
                    }  
                    input.close() ; 												// �ر�������  
                }  
            }  
            zipOut.close() ;    													// �ر������ 
        }catch(Exception e){
        	e.printStackTrace();
        }        
	}
    
    /**
     * �ж��ļ��Ƿ��Ѿ�����
     * @param filePath
     * @return
     */
    public boolean isFileExist(String filePath){
    	File file = new File(filePath);
    	
    	return file.exists();
    }
    
    /**
     * �����ļ�
     * @param srcFile
     * @param dstFile
     */
    public void copyFile(String srcFilePath, String dstFilePath){
    	File srcFile = new File(srcFilePath);
    	File dstFile = new File(dstFilePath);
    	
    	//Դ�ļ�������
    	if(!srcFile.exists()){
    		System.out.println(srcFilePath + " is not exist!");
    		return;
    		
    	}
    	//�ֽ���������
    	byte buffer[] = new byte[1024];
    	
    	try {
			FileInputStream srcIS = new FileInputStream(srcFile);
			FileOutputStream dstOS = new FileOutputStream(dstFile);
			
			int read = srcIS.read(buffer);
			
			//�����д
			while(read != -1){
				dstOS.write(buffer, 0 , read);
				read = srcIS.read(buffer);
			}
			
			//�ر����������
	    	srcIS.close();
	    	dstOS.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    
    /**
     * ��ȡ��ǰϵͳʱ�䣬�����ļ���������ȷ������
     * @return
     */
    public String getCurTime(){
    	//ͼƬ������ʱ�������ȷ�����룬ȷ�����ظ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		Date date = new Date();
		String date_str = sdf.format(date);
		
		return date_str;
    }
    
    /**
     * ���ݿ�����Ϣ����xml�ļ�
     * @param listOfPicture
     */
    public void createConfigXml(com.gxf.beans.Display display, com.gxf.beans.PlaySolution playSolution){
    	//��ȡ����·�������ͼƬ
    	List<Picture> listOfPicture = new ArrayList<Picture>(playSolution.getPictures());
    	//û��ͼƬ
    	if(listOfPicture == null || listOfPicture.size() == 0)
    		return;
    	//�����ɸ��ڵ�pictures
    	Document document = DocumentHelper.createDocument();
    	Element pictures = document.addElement("pictures");
    	
    	//����xml����
    	for(int i = 0; i < listOfPicture.size(); i++){
    		Picture pictureBean = (Picture) listOfPicture.get(i);
    		PlayControl playControlBean = pictureBean.getPlayControl();
    		
    		//����picture�ڵ�
    		Element picture = pictures.addElement("picture");
    		picture.addAttribute("name", pictureBean.getPicName());
    		
    		//����type�ڵ�
    		Element type = picture.addElement("type");
    		type.addText(String.valueOf(playControlBean.getPlayType()));
    		//����timeInterval�ڵ�
    		Element timeInterval = picture.addElement("timeInterval");
    		timeInterval.addText(String.valueOf(playControlBean.getTimeInterval()));
    		
    		//����date_start�ڵ�
    		Element date_start  = picture.addElement("date_start");
    		date_start.addText(playControlBean.getDateTimeStart().toString());
    		
    		//����date_end�ڵ�
    		Element date_end = picture.addElement("date_end");
    		date_end.addText(playControlBean.getDateTimeEnd().toString());
    		
    		//����time_start�ڵ�
    		Element time_start = picture.addElement("time_start");
    		time_start.addText(playControlBean.getTimeStart().toString());
    		
    		//����time_end�ڵ�
    		Element time_end = picture.addElement("time_end");
    		time_end.addText(playControlBean.getTimeEnd().toString());
    		
    		//����weekdays�ڵ�
    		Element weekdays = picture.addElement("weekdays");
    		weekdays.addText(playControlBean.getWeekdays());
    	}//for
    	
        //�򲥷ŷ���������������ļ�
        String dic = getCurrentProjectPath() + File.separator + File.separator + display.getName() + File.separator + 
        									playSolution.getName();
        String configFilePath = dic + File.separator + playSolution.getName() + ".xml";
//        FileWriter out;
//		try {
//			out = new FileWriter(new File(configFilePath));
//			document.write(out);
//			out.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}        
		
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        File file = new File(configFilePath);
        
        try {
        	XMLWriter writer = new  XMLWriter(new FileOutputStream(file), format);
			writer.write(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * �ݹ�ɾ���ļ�
     * @param fileToDelete
     */
    public  void deleteFile(File fileToDelete){
		File filesInFile[] = fileToDelete.listFiles();
		if(filesInFile == null)						//�����ļ���ֱ��ɾ��
			fileToDelete.delete();
		else{										//���ļ���
			for(File elementFile : filesInFile){	//�ݹ�ɾ�������ļ�
				deleteFile(elementFile);
			}//for
			
			//ɾ���ļ�
			fileToDelete.delete();
		}
	}
    
    /**
     * �����������Ʋ���ʵ��
     * @param src
     * @param dst
     */
    public void copyPlayControl(PlayControl src, PlayControl dst){
    	dst.setPlayType(src.getPlayType());
    	dst.setTimeInterval(src.getTimeInterval());
    	dst.setDateTimeStart(src.getDateTimeStart());
    	dst.setDateTimeEnd(src.getDateTimeEnd());
    	dst.setTimeStart(src.getTimeStart());
    	dst.setTimeEnd(src.getTimeEnd());
    	dst.setWeekdays(src.getWeekdays());
    }
}
