package com.gxf.beans;

import java.util.HashSet;
import java.util.Set;

public class Display {
	private int id;
	private String name;
	private String type;
	private String comment;
	private PlaySolution curPlaySolution;												//当前播放方案
	private Set<PlaySolution> solutions = new HashSet<PlaySolution>();			//一个显示屏有多个播放方案
	private Set<Communication> communications = new HashSet<Communication>();
	
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
	

	public PlaySolution getCurPlaySolution() {
		return curPlaySolution;
	}
	public void setCurPlaySolution(PlaySolution curPlaySolution) {
		this.curPlaySolution = curPlaySolution;
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
	public Set<Communication> getCommunications() {
		return communications;
	}
	public void setCommunications(Set<Communication> communications) {
		this.communications = communications;
	}


	
}
