package com.gxf.beans;

import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


/**
 * ���ŷ���
 * @author Administrator
 *
 */
public class PlaySolution {
	private int id;
	private String name;
	private Date createTime;
	private Date updateTime;
	private int updateCount;
	private String comment;
	private Display display;
	private Set<Picture> pictures = new TreeSet<Picture>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Display getDisplay() {
		return display;
	}
	public void setDisplay(Display display) {
		this.display = display;
	}
	public Set<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}	
	
}
