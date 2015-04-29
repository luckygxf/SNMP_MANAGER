package com.gxf.beans;

import java.util.HashSet;
import java.util.Set;

public class Display {
	private int id;
	private String name;
	private String type;
	private String comment;
	private String curPlaySolutionName;													//��ǰ���ŷ���
	private Set<PlaySolution> solutions = new HashSet<PlaySolution>();					//һ����ʾ���ж�����ŷ���
	private Communication communication;												//ͨ�ŷ�ʽ
	
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
