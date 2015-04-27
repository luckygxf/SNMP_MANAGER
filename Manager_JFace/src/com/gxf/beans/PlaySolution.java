package com.gxf.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private Set<Display> displays = new HashSet<Display>();
	
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
	public Set<Display> getDisplays() {
		return displays;
	}
	public void setDisplays(Set<Display> displays) {
		this.displays = displays;
	}


	
	
}
