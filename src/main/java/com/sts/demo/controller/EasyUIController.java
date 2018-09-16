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
	public String getAuthor(@RequestParam(value="id", required=false, defaultValue="1") Integer id,
			@RequestParam(value="page", required=true, defaultValue="1") Integer page,
			@RequestParam(value="rows", required=true, defaultValue="5") Integer rows,
			@RequestParam(value="searchPhrase", required=false) String searchPhrase,
			@RequestParam(value="sort", required=false) String sort, 
			@RequestParam(value="order", required=false) String order) {
		LOG.info("id="+id+",page="+page+",rows="+rows+",sort="+sort+",order="+order+",searchPhrase="+searchPhrase);
		Map<String, Object> mapPara = new HashMap<String, Object>();
		int offset = 0;
		if (page > 0) {
			offset = (page - 1) * rows;
		}
		mapPara.put("offset", offset);
		mapPara.put("rowCount", rows);
		
		if (searchPhrase != "" && searchPhrase != null) {
			mapPara.put("searchPhrase", searchPhrase);
		}
		
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
		JsonObject data = new JsonObject();
		int total = 0;
		total = this.authorService.selectAllCount();
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
		
		data.addProperty("total", total);
		data.add("rows", array);
		//data.add("footer", array);
		
		return data.toString();
	}
	
	@RequestMapping(value="/dsiplayAuthor")
	public String displayAuthorInfo(ModelMap model,
			@RequestParam(value="authorId", required=false) Integer authorId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="age", required=false) Integer age,
			@RequestParam(value="email", required=false) String email, 
			@RequestParam(value="country", required=false) String country) {
		
		model.addAttribute("authorId", authorId);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("email", email);
		model.addAttribute("country", country);
		
		return "easyui/displayAuthor";
	}
	
	@RequestMapping(value="/dsiplayAuthorAgain")
	public String displayAuthorInfoAgain(ModelMap model,
			@RequestParam(value="authorId", required=false) Integer authorId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="age", required=false) Integer age,
			@RequestParam(value="email", required=false) String email, 
			@RequestParam(value="country", required=false) String country) {
		
		model.addAttribute("authorId", authorId);
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("email", email);
		model.addAttribute("country", country);
		
		return "easyui/displayAuthorAgain";
	}
	
	@RequestMapping(value="/saveAuthor")
	@ResponseBody
	public String saveAuthorInfo(@RequestParam(value="authorId", required=false) Integer authorId,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="age", required=false) Integer age,
			@RequestParam(value="email", required=false) String email, 
			@RequestParam(value="country", required=false) String country) {
		String status = "{\"status\": \"failed\"}";
		Author author = new Author();
		if (authorId != null) {
			author.setId(authorId);
		} else {
			return status;
		}
		if (name != null) {
			author.setName(name);
		}
		if (age != null) {
			author.setAge(age);
		}
		if (email != null) {
			author.setEmail(email);
		}
		if (country != null) {
			author.setCountry(country);
		}
		
		int affectRow = this.authorService.updateByPrimaryKeySelective(author);
		if (affectRow > 0) {
			status = "{\"status\": \"success\"}";
		}
		
		return status;
	}
	
}
