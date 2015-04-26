package com.gxf.entities;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.ImageData;

/**
 * ��������ͼƬ�༭��
 * @author Administrator
 *
 */
public class ImageItem {
	private int x;											//label������
	private int y;
	private Font font;										//����
	private Color wordColor;								//��ɫ
	private ImageData imageData;							//ͼ��
	private String words;									//����
	private int width;										//label��Ⱥ͸߶�	
	private int height;					
	
	private int fontNameIndex;								//�����ʽ��������������
	private int fontColorIndex;
	private int fontSizeIndex;
	
	private boolean isBold;									//�Ƿ�б�塢�Ӵ�
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
