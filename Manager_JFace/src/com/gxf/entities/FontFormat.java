package com.gxf.entities;
import java.awt.Color;


/**
 * 封装字体的格式
 * @author Administrator
 *
 */
public class FontFormat {
	private int size;					//字体大小
	private String style;				//字体样式
	private Color color;				//字体颜色
	
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
