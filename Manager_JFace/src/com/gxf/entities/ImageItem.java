package com.gxf.entities;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.ImageData;

/**
 * 用于文字图片编辑器
 * @author Administrator
 *
 */
public class ImageItem {
	private int x;											//label的坐标
	private int y;
	private Font font;										//字体
	private Color wordColor;								//颜色
	private ImageData imageData;							//图像
	private String words;									//文字
	private int width;										//label宽度和高度	
	private int height;					
	
	private int fontNameIndex;								//字体格式在下拉框中索引
	private int fontColorIndex;
	private int fontSizeIndex;
	
	private boolean isBold;									//是否斜体、加粗
	private boolean isItalic;
	
	private String imageItemName;
	
	public String getImageItemName() {
		return imageItemName;
	}
	public void setImageItemName(String imageItemName) {
		this.imageItemName = imageItemName;
	}
	public boolean isBold() {
		return isBold;
	}
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}
	public boolean isItalic() {
		return isItalic;
	}
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}
	public int getFontNameIndex() {
		return fontNameIndex;
	}
	public void setFontNameIndex(int fontNameIndex) {
		this.fontNameIndex = fontNameIndex;
	}
	public int getFontColorIndex() {
		return fontColorIndex;
	}
	public void setFontColorIndex(int fontColorIndex) {
		this.fontColorIndex = fontColorIndex;
	}
	public int getFontSizeIndex() {
		return fontSizeIndex;
	}
	public void setFontSizeIndex(int fontSizeIndex) {
		this.fontSizeIndex = fontSizeIndex;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getWordColor() {
		return wordColor;
	}
	public void setWordColor(Color wordColor) {
		this.wordColor = wordColor;
	}
	public ImageData getImageData() {
		return imageData;
	}
	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
