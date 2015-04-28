package com.gxf.dao;

import java.util.List;

import com.gxf.beans.PlaySolution;

public interface PlaySolutionDao {
	
	/**
	 * ���ݲ��ŷ������ֲ�ѯ���ŷ���
	 * @param name
	 * @return
	 */
	public PlaySolution querySolutionByNanme(String name);
	
	/**
	 * ��Ӳ��ŷ���
	 * @param playSolution
	 */
	public void addPlaySolution(PlaySolution playSolution);
	
	/**
	 * ��ѯ���еĲ��ŷ���
	 * @return
	 */
	public List<PlaySolution> queryAllSolutions();
}
