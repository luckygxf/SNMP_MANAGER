package com.gxf.dao;

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
}
