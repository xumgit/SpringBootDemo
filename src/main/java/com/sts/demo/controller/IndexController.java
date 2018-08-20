package com.sts.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
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
	
	@RequestMapping("/index")
	public String index() {
		try {
			String root = ResourceUtils.getURL("classpath:").getPath();
			LOG.info("root="+root);
			File pathFile = new File(root);
			if(!pathFile.exists()) {
				pathFile = new File("");
			}
			LOG.info("pathFile="+pathFile.getAbsolutePath());
			File upload = new File(pathFile.getAbsolutePath(),"static/upload/");
			if(!upload.exists()) {
				upload.mkdirs();
			}
			LOG.info("upload url="+upload.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index/index";
	}
	
	@RequestMapping(value="/saveAuthor")
	@ResponseBody
	public String saveAuthor(@RequestParam(value="id", required=true) Integer id,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="age", required=false) Integer age, 
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="country", required=false) String country, 
			@RequestParam(value="clone", required=false) String clone) {
		String status = "{\"status\":\"success\"}";
		Author author = new Author();
		if (id != null) {author.setId(id);}
		if (name != null) {author.setName(name);}
		if (age != null) {author.setAge(age);}
		if (email != null) {author.setEmail(email);}
		if (country != null) {author.setCountry(country);}
		if (clone != null) {author.setClone(clone);}
		int affectRow = authorService.updateByPrimaryKeySelective(author);
		if (affectRow > 0) {
			status = "{\"status\":\"success\"}";
		} else {
			status = "{\"status\":\"failed\"}";
		}
		return status;
	}
	
	@RequestMapping(value="/getAuthor", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthor() {
		StringBuilder data = new StringBuilder();
		data.append("{\"data\":");
		data.append("[");
		
		List<Author> authorList = authorService.selectAllByList();
		int authorSize = authorList.size();
		StringBuilder authorItem = new StringBuilder();
		if (authorSize > 0) {			
			for (int i = 0; i < authorSize; i++) {
				authorItem.append("{");
				authorItem.append("\"id\":\"" + authorList.get(i).getId() + "\",");
				authorItem.append("\"name\":\"" + authorList.get(i).getName() + "\",");
				authorItem.append("\"age\":\"" + authorList.get(i).getAge() + "\",");
				authorItem.append("\"email\":\"" + authorList.get(i).getEmail() + "\",");
				authorItem.append("\"country\":\"" + authorList.get(i).getCountry() + "\",");
				authorItem.append("\"clone\":\"" + authorList.get(i).getClone() + "\"");
				authorItem.append("},");
			}
		}
		String authorItemString = authorItem.toString();
		if (authorItemString.length() > 0) {
			authorItemString = authorItemString.substring(0, authorItemString.length() - 1);
		}
		data.append(authorItemString);
		
		data.append("]");
		data.append("}");
		return data.toString();
	}
	
	@RequestMapping(value="/author")
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
			String dataTest = "[";
			dataTest += "{\"country\":\"China\",\"name\":\"aa\",\"clone\":\"\",\"id\":\"1\",\"age\":\"17\",\"email\":\"ww@178.com\"},";
			dataTest += "{\"country\":\"China\",\"name\":\"bb\",\"clone\":\"\",\"id\":\"2\",\"age\":\"27\",\"email\":\"ww@278.com\"},";
			dataTest += "{\"country\":\"China\",\"name\":\"cc\",\"clone\":\"\",\"id\":\"3\",\"age\":\"37\",\"email\":\"ww@378.com\"}";
			dataTest += "]";
			jsonObj.addProperty("add", dataTest);
			array.add(jsonObj);
		}
		data.addProperty("current", current);
		data.addProperty("rowCount", rowCount);
		data.addProperty("total", total);
		data.add("rows", array);
		
		LOG.info("data="+data.toString());
		
		return data.toString();
	}
	
	@RequestMapping(value="/detect")
	public String detectTest() {
		return "index/detect";
	}
	
}
