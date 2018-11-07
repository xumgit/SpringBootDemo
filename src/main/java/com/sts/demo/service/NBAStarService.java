package com.sts.demo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sts.demo.dao.NBAStarMapper;

@Service(value="nbaStarService")
public class NBAStarService {

	@Resource
	private NBAStarMapper nBAStarMapper;
	
	public List<Map<String, Object>> selectAll() {
		List<Map<String, Object>> nbaStar = null;
		nbaStar = this.nBAStarMapper.selectAll();
		return nbaStar;
	}
	
}
