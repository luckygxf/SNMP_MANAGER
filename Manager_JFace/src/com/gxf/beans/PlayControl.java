package com.gxf.beans;

import java.sql.Date;
import java.sql.Time;


public class PlayControl implements Comparable{
	private int id;
	private int playType;										//1普通播放2定时播放
	private int timeInterval;
	private Date dateTimeStart;
	private Date dateTimeEnd;
	private Time timeStart;
	private Time timeEnd;
	private String weekdays;
	
	//图片名称
	private String picName;
	//播放顺序
	private int playOrder;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlayType() {
		return playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	public int getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	public Date getDateTimeStart() {
		return dateTimeStart;
	}
	public void setDateTimeStart(Date dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}
	public Date getDateTimeEnd() {
		return dateTimeEnd;
	}
	public void setDateTimeEnd(Date dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
	}
	public Time getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	public Time getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getWeekdays() {
		return weekdays;
	}
	public void setWeekdays(String weekdays) {
		this.weekdays = weekdays;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public int getPlayOrder() {
		return playOrder;
	}
	public void setPlayOrder(int playOrder) {
		this.playOrder = playOrder;
	}
	
	@Override
	public int compareTo(Object o) {
		PlayControl playControl = (PlayControl) o;
		if(playOrder > playControl.getPlayOrder())
			return 1;
		else if(playOrder < playControl.getPlayOrder())
			return -1;
		return 0;
	}
}
