package com.sts.demo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sts.demo.dao.AuthorMapper;
import com.sts.demo.pojo.Author;

@Service(value="authorService")
public class AuthorService {
	
	private static final String KEY = "author";
	private static final Logger LOG = LogManager.getLogger(Author.class);
	
	@Resource
	private AuthorMapper authorMapper;
	
	public List<Map<String, Object>> selectAll() {
		List<Map<String, Object>> author = null;
		author = this.authorMapper.selectAll();
		return author;
	}
	
	public List<Author> selectAllByList() {
		List<Author> author = null;
		author = this.authorMapper.selectAllByList();
		return author;
	}
	
	public List<Map<String, Object>> selectAuthorByBootGrid(Map<String, Object> mapPara) {
		List<Map<String, Object>> author = null;
		author = this.authorMapper.selectAuthorByBootGrid(mapPara);
		return author;
	}
	
	public List<Author> selectByParameterOne(Author author) {
		List<Author> authorList = null;
		authorList = this.authorMapper.selectByParameterOne(author);
		return authorList;
	}
	
	public List<Author> selectByParameterTwo(String name, Integer age, String country) {
		List<Author> authorList = null;
		authorList = this.authorMapper.selectByParameterTwo(name,age,country);
		return authorList;
	}
	
	public List<Author> selectByParameterThree(Author author, String country) {
		List<Author> authorList = null;
		authorList = this.authorMapper.selectByParameterThree(author,country);
		return authorList;
	}
	
	public int selectAllCount() {
		int count = 0;
		count = this.authorMapper.selectAllCount();
		return count;
	}
	
	public int updateByPrimaryKeySelective(Author author) {
		int affectRow = -1;
		affectRow = this.authorMapper.updateByPrimaryKeySelective(author);
		return affectRow;
	}
	
	public int batchUpdate(List<Author> authorList) {
		int affectRow = -1;
		affectRow = this.authorMapper.batchUpdate(authorList);
		return affectRow;
	}
	
	public int batchUpdateCaseWhen(List<Author> authorList) {
		int affectRow = -1;
		affectRow = this.authorMapper.batchUpdateCaseWhen(authorList);
		return affectRow;
	}
	
}
