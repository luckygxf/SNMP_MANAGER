package com.gxf.dao;

import java.util.List;

import com.gxf.beans.PlaySolution;

public interface PlaySolutionDao {
	
	/**
	 * ���ݲ��ŷ������ֲ�ѯ���ŷ���
	 * @param name
	 * @return
	 */
	public PlaySolution querySolutionByName(String name);
	
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
	
	/**
	 * ɾ�����ŷ���
	 * @param playSolution
	 */
	public void deletePlaySolution(PlaySolution playSolution);
	
	/**
	 * ���ݲ��ŷ���IDɾ�����ŷ���
	 * @param playSolutionId
	 */
	public void deletePlaySolutinById(int playSolutionId);
}
