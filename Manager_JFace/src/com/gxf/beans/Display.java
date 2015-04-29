package com.gxf.beans;

import java.util.HashSet;
import java.util.Set;

public class Display {
	private int id;
	private String name;
	private String type;
	private String comment;
	private String curPlaySolutionName;													//当前播放方案
	private Set<PlaySolution> solutions = new HashSet<PlaySolution>();					//一个显示屏有多个播放方案
	private Communication communication;												//通信方式
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getCurPlaySolutionName() {
		return curPlaySolutionName;
	}
	public void setCurPlaySolutionName(String curPlaySolutionName) {
		this.curPlaySolutionName = curPlaySolutionName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Set<PlaySolution> getSolutions() {
		return solutions;
	}
	public void setSolutions(Set<PlaySolution> solutions) {
		this.solutions = solutions;
	}
	public Communication getCommunication() {
		return communication;
	}
	public void setCommunication(Communication communication) {
		this.communication = communication;
	}



	
}
