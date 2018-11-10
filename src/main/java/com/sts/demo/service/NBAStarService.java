package com.sts.demo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sts.demo.dao.NBAStarMapper;
import com.sts.demo.pojo.NBAStar;

@Service(value="nbaStarService")
public class NBAStarService {

	@Resource
	private NBAStarMapper nBAStarMapper;
	
	public List<Map<String, Object>> selectAll() {
		List<Map<String, Object>> nbaStar = null;
		nbaStar = this.nBAStarMapper.selectAll();
		return nbaStar;
	}
	
	public List<Map<String, Object>> selectByPrimaryKeyAnother(Integer id) {
		List<Map<String, Object>> nbaStar = null;
		nbaStar = this.nBAStarMapper.selectByPrimaryKeyAnother(id);
		return nbaStar;
	}
	
	public int updateByPrimaryKeySelective(NBAStar nbaStar) {
		int affectRow = -1;
		affectRow = this.nBAStarMapper.updateByPrimaryKeySelective(nbaStar);
		return affectRow;
	}
	
	public int deleteByPrimaryKey(Integer id) {
		int affectRow = -1;
		affectRow = this.nBAStarMapper.deleteByPrimaryKey(id);
		return affectRow;
	}
	
	public int insertOneData(NBAStar nbaStar) {
		int affectRow = -1;
		affectRow = this.nBAStarMapper.insert(nbaStar);
		return affectRow;
	}
	
	public int insertManyData(List<NBAStar> nbaStars) {
		int affectRow = -1;
		affectRow = this.nBAStarMapper.insertMany(nbaStars);
		return affectRow;
	}
	
}
