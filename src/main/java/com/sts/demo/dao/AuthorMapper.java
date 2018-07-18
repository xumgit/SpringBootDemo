package com.sts.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sts.demo.pojo.Author;

public interface AuthorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Author record);

    int insertSelective(Author record);

    Author selectByPrimaryKey(Integer id);
    
    List<Map<String, Object>> selectAll();
    
    List<Author> selectAllByList();
    
    List<Map<String, Object>> selectAuthorByBootGrid(Map<String, Object> mapPara);
    
    List<Author> selectByParameterOne(Author author);
    
    List<Author> selectByParameterTwo(@Param("name")String name, @Param("age")Integer age, @Param("country")String country);
    
    List<Author> selectByParameterThree(@Param("author")Author author, @Param("country")String country);
    
    int selectAllCount();

    int updateByPrimaryKeySelective(Author record);

    int updateByPrimaryKey(Author record);
    
    int batchUpdate(List<Author> authorList);
    
    int batchUpdateCaseWhen(List<Author> authorList);
}