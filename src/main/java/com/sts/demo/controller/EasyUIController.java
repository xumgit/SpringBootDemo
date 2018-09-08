package com.sts.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sts.demo.pojo.Author;
import com.sts.demo.service.AuthorService;

@Controller
@RequestMapping(value="/easyui")
public class EasyUIController {
	
	private static final Logger LOG = LogManager.getLogger(EasyUIController.class);
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(value="/index")
	public String index() {
		return "easyui/index";
	}
	
	@RequestMapping(value="/getAuthor")
	@ResponseBody
	public String getAuthor(@RequestParam(value="id", required=true, defaultValue="1") Integer id,
			@RequestParam(value="page", required=true, defaultValue="1") Integer page,
			@RequestParam(value="rows", required=true, defaultValue="5") Integer rows,
			@RequestParam(value="sort", required=false) String sort, 
			@RequestParam(value="order", required=false) String order) {
		LOG.info("id="+id+",page="+page+",rows="+rows+",sort="+sort+",order="+order);
		Map<String, Object> mapPara = new HashMap<String, Object>();
		int offset = 0;
		if (page > 0) {
			offset = (page - 1) * rows;
		}
		mapPara.put("offset", offset);
		mapPara.put("rowCount", rows);
		if (order != null) {
			if ("id".equalsIgnoreCase(sort)) {
				mapPara.put("idSort", order);
			} else if ("name".equalsIgnoreCase(sort)) {
				mapPara.put("nameSort", order);
			} else if ("age".equalsIgnoreCase(sort)) {
				mapPara.put("ageSort", order);
			} else if ("email".equalsIgnoreCase(sort)) {
				mapPara.put("emailSort", order);
			} else if ("country".equalsIgnoreCase(sort)) {
				mapPara.put("countrySort", order);
			} else if ("clone".equalsIgnoreCase(sort)) {
				mapPara.put("cloneSort", order);
			}
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
		
		return array.toString();
	}
	
}
