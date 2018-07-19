package com.sts.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sts.demo.pojo.Author;
import com.sts.demo.service.AuthorService;

@Controller
@RequestMapping(value="/index")
public class IndexController {

	private static final Logger LOG = LogManager.getLogger(IndexController.class);
	
	@Autowired
	private AuthorService authorService;
	
//	@RequestMapping("/index")
//	public String index() {
//		return "index";
//	}
	
	@RequestMapping("/author")
	public String getAuthorInfo(ModelMap model) {
		List<Author> authorList = authorService.selectAllByList();
		model.addAttribute("authorList", authorList);
		return "index/authorList";
	}
	
	@RequestMapping(value="/showauthor", method = RequestMethod.GET)
	public String showAuthorByBootGrid() {
		return "index/bootgrid";
	}
	
	@RequestMapping(value="/authorBootGrid", method = RequestMethod.POST)
	@ResponseBody
	public String authorBootGrid(@RequestParam(value="current", required=true, defaultValue="1") Integer current,
			@RequestParam(value="rowCount", required=true, defaultValue="10") Integer rowCount,
			@RequestParam(value="searchPhrase", required=false) String searchPhrase,
			@RequestParam(value="sort[id]", required=false) String sortId, 
			@RequestParam(value="sort[age]", required=false) String sortAge,
			@RequestParam(value="paraid", required=false) String paraid, 
			@RequestParam(value="type", required=false) String type) {
		LOG.info("current="+current);
		LOG.info("rowCount="+rowCount);
		LOG.info("searchPhrase="+searchPhrase);
		LOG.info("sortId="+sortId);
		LOG.info("sortAge="+sortAge);
		LOG.info("paraid="+paraid);
		LOG.info("type="+type);
		
		JsonObject data = new JsonObject();
		rowCount = (rowCount != null && !(-1 == rowCount)) ? rowCount : 10;
		
		Map<String, Object> mapPara = new HashMap<String, Object>();
		
		int total = 0;
		total = this.authorService.selectAllCount();
		LOG.info("total="+total);
		
		int offset = 0;
		if (current > 0) {
			offset = (current - 1) * rowCount;
		}
		mapPara.put("offset", offset);
		mapPara.put("rowCount", rowCount);
		
		if (searchPhrase != "" && searchPhrase != null) {
			mapPara.put("searchPhrase", searchPhrase);
		}
					
		if (sortId != null) {
			mapPara.put("idSort", sortId);
		} else if (sortAge != null) {
			mapPara.put("ageSort", sortAge);
		}
		
		List<Map<String, Object>> authors = authorService.selectAuthorByBootGrid(mapPara);
		JsonArray array = new JsonArray();
		JsonObject jsonObj = null;	
		for (Map<String, Object> kv : authors) {
			jsonObj = new JsonObject();
			for (Map.Entry<String, Object> entry : kv.entrySet()) {
				jsonObj.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
			}
			array.add(jsonObj);
		}
		data.addProperty("current", current);
		data.addProperty("rowCount", rowCount);
		data.addProperty("total", total);
		data.add("rows", array);
		LOG.info("data="+data.toString());
		
		return data.toString();
	}
	
}
