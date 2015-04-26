package com.gxf.composite;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.gxf.entities.DisplayDevice;
import com.gxf.entities.FontFormat;
import com.gxf.util.AllIP;
import com.gxf.util.SendPic;
import com.gxf.util.Util;

public class SendMessageComposite extends Composite {
	private int txtHeight = 20;
	private int txtWidth = 120;
	private int labelHeight = 30;
	private int labelWidth = 50;
	private int comboWidth = 120;
	private int comboHeight = 30;
	private int btnWidth = 70;
	private int btnHeight = 30;
	
	//说明后面控件的标签
	private Label lb_sendContent;
	private Label lb_fontStyle;
	private Label lb_fontSize;
	private Label lb_fontColor;
	private Label lb_deviceWidth;
	private Label lb_deviceHeight;
	private Label lb_preViewPic;
	private Label lb_hostList ;
	
	//字体下拉列表控件
	private Text txt_sendContent;
	private Combo combo_fontStyle ;
	private Combo combo_fontSize ;
	private Combo combo_fontColor ;
	
	//屏幕高度和宽度文本框
	private Text txt_deviceWidth ;
	private Text txt_deviceHeight ;
	
	private Label lb_pic ;
	private List listOfHost;
	
	//生成图片按钮和发送图片按钮
	private Button btn_createImage;
	private Button btn_sendImage;
	
	//工具类
	private Util util = new Util();
	private SendPic sendPic = new SendPic();
	private AllIP ipTool = new AllIP();
	
	public SendMessageComposite(Composite parent, int style) {
		super(parent, style);

		lb_sendContent = new Label(this, SWT.NONE);
		lb_fontStyle = new Label(this, SWT.NONE);
		lb_fontSize = new Label(this, SWT.NONE);
		lb_fontColor = new Label(this, SWT.NONE);
		lb_deviceWidth = new Label(this, SWT.NONE);
		lb_deviceHeight = new Label(this, SWT.NONE);
		lb_preViewPic = new Label(this, SWT.NONE);
		lb_hostList = new Label(this, SWT.NONE);
		
		txt_sendContent = new Text(this, SWT.NONE);
		combo_fontStyle = new Combo(this, SWT.DROP_DOWN);
		combo_fontSize = new Combo(this, SWT.DROP_DOWN);
		combo_fontColor = new Combo(this, SWT.DROP_DOWN);
		
		txt_deviceWidth = new Text(this, SWT.NONE);
		txt_deviceHeight = new Text(this, SWT.NONE);
		
		lb_pic = new Label(this, SWT.NONE);
		listOfHost = new List(this, SWT.SINGLE);
		
		btn_createImage = new Button(this, SWT.PUSH);
		btn_sendImage = new Button(this, SWT.PUSH);
		
		
		//设置发送内容
		lb_sendContent.setText("发送内容");
		lb_sendContent.setBounds(5, 10, labelWidth, labelHeight);		
		txt_sendContent.setBounds(60, 10, txtWidth, txtHeight);
		txt_sendContent.setText("你好，世界!");
		
		//设置字体格式
		lb_fontStyle.setText("字体格式");
		lb_fontStyle.setBounds(5, 45, labelWidth, labelHeight);		
		combo_fontStyle.setBounds(60, 45, comboWidth, comboHeight);
		
		//设置字体大小
		lb_fontSize.setText("字体大小");
		lb_fontSize.setBounds(300, 45, labelWidth, labelHeight);		
		combo_fontSize.setBounds(355, 45, comboWidth, comboHeight);
		
		//设置字体颜色
		lb_fontColor.setText("字体颜色");
		lb_fontColor.setBounds(5, 85, labelWidth, labelHeight);		
		combo_fontColor.setBounds(60, 85, comboWidth, comboHeight);
		
		//设置屏幕高度
		lb_deviceWidth.setText("屏幕宽度");
		lb_deviceWidth.setBounds(5, 125, labelWidth, labelHeight);
		txt_deviceWidth.setBounds(60, 125, txtWidth, txtHeight);
		txt_deviceWidth.setText("120");
		
		//设置屏幕宽度
		lb_deviceHeight.setText("屏幕高度");
		lb_deviceHeight.setBounds(300, 125, labelWidth, labelHeight);
		txt_deviceHeight.setBounds(355, 125, txtWidth, txtHeight);
		txt_deviceHeight.setText("80");
		
		//设置图片预览
		lb_preViewPic.setText("图片预览");
		lb_preViewPic.setBounds(5, 165, labelWidth, labelHeight);
		lb_pic.setText("No Image Create!");
		lb_pic.setBounds(60, 165, txtWidth, labelHeight * 3);
		
		//设置主机列表
		lb_hostList.setText("主机列表");
		lb_hostList.setBounds(300, 165, labelWidth, labelHeight);
		listOfHost.setBounds(355, 165, txtWidth, labelHeight * 3);
		
		//设置生成图片和发送图片按钮
		btn_createImage.setText("生成图片");
		btn_createImage.setBounds(160, 350, btnWidth, btnHeight);
		
		btn_sendImage.setText("发送图片");
		btn_sendImage.setBounds(300, 350, btnWidth, btnHeight);
		
		//绑定数据到控件上
		init();
	}

	/**
	 * 绑定数据到控件上
	 */
	public void init(){
		//绑定操作系统支持的字体格式到下拉列表
		java.util.List<String> fontsInOS = util.getFontsInOS();
		for(int i = 0; i < fontsInOS.size(); i++)
			combo_fontStyle.add(fontsInOS.get(i));
		combo_fontStyle.select(79);
		
		//字体大小
		for(int i = 11; i < 48; i++){
			combo_fontSize.add(String.valueOf(i));
		}
		combo_fontSize.select(8);
		//字体颜色
		String arrayOfFontColor[] = {"白色", "红色", "绿色", "蓝色"};
		for(int i = 0; i < arrayOfFontColor.length; i++){
			combo_fontColor.add(arrayOfFontColor[i]);
		}
		combo_fontColor.select(0);
		//只有主机IPS为空时才重新加载
		if(AllIP.IPS == null || AllIP.IPS.size() == 0){
			//获取局域网所有主机ip
			ipTool.getAllIP();			
		}
		for(int i = 0; i < AllIP.IPS.size(); i++)
			listOfHost.add(AllIP.IPS.get(i));
		
		
		//为按钮添加监控事件
		btn_createImage.addSelectionListener(new ButtonSelectionListener());
		btn_sendImage.addSelectionListener(new ButtonSelectionListener());
	}
	
	class ButtonSelectionListener implements SelectionListener{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
//			System.out.println("widgetDefaultSelected");
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if(arg0.getSource() == btn_createImage)
				createImage(arg0.display);
			if(arg0.getSource() == btn_sendImage)
				sendImage(arg0.display);
			
		}
		/**
		 * 生成图片
		 */
		public void createImage(Display display){
			//封装好FontFormat和DisplayDevice
			FontFormat fontFormat = new FontFormat();
			DisplayDevice displayDevice = new DisplayDevice();
			
			String fontStyle = combo_fontStyle.getItem(combo_fontStyle.getSelectionIndex());
			String fontSize = combo_fontSize.getItem(combo_fontSize.getSelectionIndex());
			String fontColor = combo_fontColor.getItem(combo_fontColor.getSelectionIndex());
			
			fontFormat.setStyle(fontStyle);
			fontFormat.setSize(Integer.parseInt(fontSize));
			if(fontColor.equals("红色"))
				fontFormat.setColor(Color.RED);
			else if(fontColor.equals("绿色"))
				fontFormat.setColor(Color.GREEN);
			else if(fontColor.equals("白色"))
				fontFormat.setColor(Color.WHITE);
			else
				fontFormat.setColor(Color.BLUE);
			
			int width = Integer.parseInt(txt_deviceWidth.getText().toString());
			int height = Integer.parseInt(txt_deviceWidth.getText().toString());
			
			displayDevice.setWidth(width);
			displayDevice.setHeight(height);
			
			//获取要生成图片的文字
			String content = txt_sendContent.getText().toString();
			
			util.createImage(content, fontFormat, displayDevice);				//生成图片
				        
	        String imagePath = util.getImagePath();
//	        System.out.println("imagePath = " + imagePath);
	        //提示图片生成成功
	        MessageBox messageBox = new MessageBox(display.getShells()[0], SWT.YES);
	        messageBox.setText("提示");
	        messageBox.setMessage("图片生成成功!");
	        messageBox.open();
	        
	        Image image = new Image(display, imagePath);
	        lb_pic.setImage(image);
		}
		
		/**
		 * 发送图片
		 * 需要目标ip, 图片路径 + 名称
		 */
		public void sendImage(Display display){
			String ip[] = listOfHost.getSelection();					//获取选中的ip
			String imagePath = util.getImagePath();						//获取要发送的图片的路径+名字
			
			MessageBox messageBox = new MessageBox(display.getShells()[0], SWT.YES);
			if(ip == null || ip.length == 0){
				messageBox.setMessage("请选择目标主机!");
			}
			
			//发送图片
			else{
				sendPic.send(ip[0], imagePath);
				messageBox.setMessage("图片发送成功!");
			}
		}
		
	}
	
	
}
