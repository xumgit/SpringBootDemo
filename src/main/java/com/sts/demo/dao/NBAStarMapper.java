package com.sts.demo.dao;

import java.util.List;
import java.util.Map;

import com.sts.demo.pojo.NBAStar;

public interface NBAStarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NBAStar record);
    
    int insertMany(List<NBAStar> nbaStars);

    int insertSelective(NBAStar record);

    NBAStar selectByPrimaryKey(Integer id);
    
    List<Map<String, Object>> selectByPrimaryKeyAnother(Integer id);
    
    List<Map<String, Object>> selectAll();

    int updateByPrimaryKeySelective(NBAStar record);

    int updateByPrimaryKey(NBAStar record);
}