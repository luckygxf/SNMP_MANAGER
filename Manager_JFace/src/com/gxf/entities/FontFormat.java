package com.gxf.entities;
import java.awt.Color;


/**
 * ��װ����ĸ�ʽ
 * @author Administrator
 *
 */
public class FontFormat {
	private int size;					//�����С
	private String style;				//������ʽ
	private Color color;				//������ɫ
	
	public FontFormat() {
		super();
	}
	public FontFormat(int size, String style, Color color) {
		super();
		this.size = size;
		this.style = style;
		this.color = color;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}
