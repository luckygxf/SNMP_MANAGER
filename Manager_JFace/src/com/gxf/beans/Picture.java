package com.gxf.beans;


public class Picture implements Comparable<Picture>{
	private int id;
	private String picName;
	private String picPath;
	private PlaySolution playSolution;
	private PlayControl playControl;
	private int playOrder;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public PlaySolution getPlaySolution() {
		return playSolution;
	}
	public void setPlaySolution(PlaySolution playSolution) {
		this.playSolution = playSolution;
	}
	public PlayControl getPlayControl() {
		return playControl;
	}
	public void setPlayControl(PlayControl playControl) {
		this.playControl = playControl;
	}
	@Override
	public int compareTo(Picture o) {
		if(this.playOrder < o.playOrder)
			return -1;
		else if(this.playOrder > o.playOrder)
			return 1;
		else
			return 0;
	}
	public int getPlayOrder() {
		return playOrder;
	}
	public void setPlayOrder(int playOrder) {
		this.playOrder = playOrder;
	}
	
}
