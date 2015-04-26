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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

import com.gxf.entities.DisplayDevice;
import com.gxf.entities.FontFormat;

/**
 * 工具类，提供一些常用方法
 * @author Administrator
 *
 */
public class Util {
	
	/**
	 * 输出列表中的字符串
	 * @param listOfString
	 */
	public void showListOfString(List<String> listOfString){
		for(int i = 0; i < listOfString.size(); i++)
			System.out.println(listOfString.get(i));
	}
	
	/**
	 * 根据字体格式要求、屏幕属性生成文字图片
	 * @param content
	 * @param fontFormat
	 * @param displayDevice
	 */
	public void createImage(String content, FontFormat fontFormat, DisplayDevice displayDevice){
		String curPath = System.getProperty("user.dir");										//当前工作路径作为图片路径
        String imageName = "\\image.jpg";
        String imagePath = curPath + imageName;
        File image = new File(imagePath);  
        int width = displayDevice.getWidth();													//屏幕宽度和高度
        int height = displayDevice.getHeight();        
        
        Font font = new Font(fontFormat.getStyle(), Font.BOLD, fontFormat.getSize()); 			//字体类
        
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);   		//子类描述具有可访问图像数据缓冲区的
        Graphics2D g2 = (Graphics2D)bi.getGraphics();   										//提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
        g2.setBackground(Color.black);   
        g2.clearRect(0, 0, width, height);   
        g2.setColor(fontFormat.getColor()); 													//设置字体颜色
        g2.setFont(font);
//        g2.setPaint(Color.white);   
           
        FontRenderContext context = g2.getFontRenderContext();   								//正确测量文本所需的信息容器
        Rectangle2D bounds = font.getStringBounds(content, context);   							//述通过位置 (x,y) 和尺寸 (w x h) 定义的矩形
        double x = (width - bounds.getWidth()) / 2;   
        double y = (height - bounds.getHeight()) / 2;	  										//以 double 精度返回窗体矩形的高度 
        double ascent = -bounds.getY();   														//以 double 精度返回窗体矩形左上角的 Y 坐标。 
        double baseY = y + ascent;   
           
        g2.drawString(content, (int)x, (int)baseY);   
           
        try {
			ImageIO.write(bi, "jpg", image);													//用来编码和写入图像的抽象超类
			
		} catch (IOException e) {
			System.out.println("create image failed!");
			e.printStackTrace();
		}   
	}
	
	/**
	 * 获取相片的像素
	 * @param filePath
	 * @return
	 */
	public int [][]getImageRGB(String filePath){
		int pixel[][] = null;						//保存像素
		File fileToDecode = new File(filePath);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(fileToDecode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		
		pixel = new int[width][height];				//分配数组空间
		for(int i = 0; i < width; i++){
			for(int j = 0 ; j < height; j++){
				pixel[i][j] = bufferedImage.getRGB(i, j) & 0xFFFFFF;
//				System.out.println(pixel[i][j]);
			}
		}//for
		
		return pixel;
	}
	
	/**
	 * 获取相片的像素
	 * @param filePath
	 * @return
	 */
	public byte [][]getImageRGBToByteArray(String filePath){
		byte pixel[][] = null;						//保存像素
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
						pixel[i][j] |= (1 << (7 - z));//?上面还是下面
					}//if
				}//for
			}//for
		}//for
		
		return pixel;
	}
	
	/**
	 * 获取文字图片的路径
	 * @return
	 */
	public String getImagePath(){
		String curPath = System.getProperty("user.dir");					//当前工作路径作为图片路径
        String imageName = "\\image.jpg";
        String imagePath = curPath + imageName;
        
        return imagePath;
	}
	
	/**
	 * 获取操作系统支持的字体格式
	 * @return
	 */
	public List<String> getFontsInOS(){
		List<String> list = new ArrayList<String>();
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		for (int i = 0; i < fonts.length; i++) {
			list.add(fonts[i].getFamily());// 获取字体
		}
		List<String> array = removeDeuplicate(list);// 去除重复
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = array.get(i).toString();
		}
		// 按首字母排序开始
		Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
		Arrays.sort(result, com);
		List<String> font = new ArrayList<String>();
		for (String i : result) {
			font.add(i);
		}
		// 按首字母排序结束
		return font;
	}
	
	/**
	 * 去除重复的字体格式
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
	 * 将long类型数据转换成8个字节的byte数组
	 * java 中long占8个字节
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
     * 获取当前工程目录
     * @return
     */
    public String getCurrentProjectPath(){
    	String curPath = System.getProperty("user.dir");
    	
    	return curPath;
    }
    
    /**
     * 根据config生成config.xml,放到播放方案下面
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
        //向播放方案下面添加配置文件
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
     * 根据播放方案名字压缩文件
     * @param solutionName
     */
    public void compressPlaySoution(String solutionName) {
		String dicPath = getCurrentProjectPath() + File.separator + "playSolutions" + File.separator + solutionName;
		String targetPath = dicPath +  ".zip";
		
		File file = new File(dicPath) ;												// 定义要压缩的文件夹  
        File zipFile = new File(targetPath) ;										// 定义压缩文件名称  
        InputStream input = null ;  												// 定义文件输入流  
        ZipOutputStream zipOut = null ; 											// 声明压缩流对象  
        try{
        	zipOut = new ZipOutputStream(new FileOutputStream(zipFile)) ;  
            zipOut.setComment("compressed by GXF") ;  								// 设置注释  
            int temp = 0 ;  
            if(file.isDirectory()){ 												// 判断是否是文件夹  
                File lists[] = file.listFiles() ;   								// 列出全部文件  
                for(int i=0;i<lists.length;i++){  
                    input = new FileInputStream(lists[i]) ; 						// 定义文件的输入流  
                    zipOut.putNextEntry(new ZipEntry(file.getName()  
                        +File.separator+lists[i].getName())) ;  					// 设置ZipEntry对象  
                    while((temp=input.read())!=-1){ 								// 读取内容  
                        zipOut.write(temp) ;    									// 压缩输出  
                    }  
                    input.close() ; 												// 关闭输入流  
                }  
            }  
            zipOut.close() ;    													// 关闭输出流 
        }catch(Exception e){
        	e.printStackTrace();
        }        
	}
    
    /**
     * 判断文件是否已经存在
     * @param filePath
     * @return
     */
    public boolean isFileExist(String filePath){
    	File file = new File(filePath);
    	
    	return file.exists();
    }
    
   
    
}
