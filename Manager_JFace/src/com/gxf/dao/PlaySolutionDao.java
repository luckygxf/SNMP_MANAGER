package com.gxf.dao;

import com.gxf.beans.PlaySolution;

public interface PlaySolutionDao {
	
	/**
	 * 根据播放方案名字查询播放方案
	 * @param name
	 * @return
	 */
	public PlaySolution querySolutionByNanme(String name);
	
	/**
	 * 添加播放方案
	 * @param playSolution
	 */
	public void addPlaySolution(PlaySolution playSolution);
}
