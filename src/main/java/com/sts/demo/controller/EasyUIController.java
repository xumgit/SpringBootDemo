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
	
	@RequestMapping(value="/propertygrid")
	@ResponseBody
	public String getPropertyGridData() { 				
		String data = "{\"total\":4,\"rows\":[\n" + 
				"    {\"name\":\"Name\",\"value\":\"Bill Smith\",\"group\":\"ID Settings\",\"editor\":\"text\"},\n" + 
				"    {\"name\":\"Address\",\"value\":\"\",\"group\":\"ID Settings\",\"editor\":\"text\"},\n" + 
				"    {\"name\":\"SSN\",\"value\":\"123-456-7890\",\"group\":\"ID Settings\",\"editor\":\"text\"},\n" + 
				"    {\"name\":\"Email\",\"value\":\"bill@gmail.com\",\"group\":\"Marketing Settings\",\"editor\":{\n" + 
				"        \"type\":\"validatebox\",\n" + 
				"        \"options\":{\n" + 
				"            \"validType\":\"email\"\n" + 
				"        }\n" + 
				"    }}\n" + 
				"]}";
		
		return data;
	}
	
	@RequestMapping(value="/dataList")
	@ResponseBody
	public String getDatalistData() { 				
		String data = "[{" + 
				"		\"name\": \"Name\"," + 
				"		\"value\": \"Bill Smith\"," + 
				"		\"group\": \"ID Settings\"," + 
				"		\"editor\": \"text\"" + 
				"	}," + 
				"	{" + 
				"		\"name\": \"Address\"," + 
				"		\"value\": \"ZhongShanRoad\"," + 
				"		\"group\": \"ID Settings\"," + 
				"		\"editor\": \"text\"" + 
				"	}," + 
				"	{" + 
				"		\"name\": \"SSN\"," + 
				"		\"value\": \"123-456-7890\"," + 
				"		\"group\": \"ID Settings\"," + 
				"		\"editor\": \"text\"" + 
				"	}," + 
				"	{" + 
				"		\"name\": \"Email\"," + 
				"		\"value\": \"123@456.com\"," + 
				"		\"group\": \"Marketing Settings\"," + 
				"		\"editor\": \"text\"" + 
				"	}" + 
				"]";
		
		return data;
	}
	
	@RequestMapping(value="/treeList")
	@ResponseBody
	public String getTreelistData() { 				
		String data = "[{" + 
				"	\"id\":1," + 
				"	\"name\":\"C\"," + 
				"	\"size\":\"\"," + 
				"	\"date\":\"02/19/2010\"," + 
				"	\"children\":[{" + 
				"		\"id\":2," + 
				"		\"name\":\"Program Files\"," + 
				"		\"size\":\"120 MB\"," + 
				"		\"date\":\"03/20/2010\"," + 
				"		\"children\":[{" + 
				"			\"id\":21,\n" + 
				"			\"name\":\"Java\"," + 
				"			\"size\":\"\"," + 
				"			\"date\":\"01/13/2010\"," + 
				"			\"state\":\"closed\"," + 
				"			\"children\":[{" + 
				"				\"id\":211," + 
				"				\"name\":\"java.exe\"," + 
				"				\"size\":\"142 KB\"," + 
				"				\"date\":\"01/13/2010\"" + 
				"			},{" + 
				"				\"id\":212," + 
				"				\"name\":\"jawt.dll\"," + 
				"				\"size\":\"5 KB\"," + 
				"				\"date\":\"01/13/2010\"" + 
				"			}]" + 
				"		},{" + 
				"			\"id\":22," + 
				"			\"name\":\"MySQL\"," + 
				"			\"size\":\"\"," + 
				"			\"date\":\"01/13/2010\"," + 
				"			\"state\":\"closed\"," + 
				"			\"children\":[{" + 
				"				\"id\":221," + 
				"				\"name\":\"my.ini\"," + 
				"				\"size\":\"10 KB\"," + 
				"				\"date\":\"02/26/2009\"" + 
				"			},{" + 
				"				\"id\":222," + 
				"				\"name\":\"my-huge.ini\"," + 
				"				\"size\":\"5 KB\"," + 
				"				\"date\":\"02/26/2009\"" + 
				"			},{" + 
				"				\"id\":223," + 
				"				\"name\":\"my-large.ini\"," + 
				"				\"size\":\"5 KB\"," + 
				"				\"date\":\"02/26/2009\"" + 
				"			}]" + 
				"		}]" + 
				"	},{" + 
				"		\"id\":3," + 
				"		\"name\":\"eclipse\"," + 
				"		\"size\":\"\"," + 
				"		\"date\":\"01/20/2010\"," + 
				"		\"children\":[{" + 
				"			\"id\":31," + 
				"			\"name\":\"eclipse.exe\"," + 
				"			\"size\":\"56 KB\"," + 
				"			\"date\":\"05/19/2009\"" + 
				"		},{" + 
				"			\"id\":32," + 
				"			\"name\":\"eclipse.ini\"," + 
				"			\"size\":\"1 KB\"," + 
				"			\"date\":\"04/20/2010\"" + 
				"		},{" + 
				"			\"id\":33," + 
				"			\"name\":\"notice.html\"," + 
				"			\"size\":\"7 KB\"," + 
				"			\"date\":\"03/17/2005\"" + 
				"		}]" + 
				"	}]" + 
				"}]";
		
		return data;
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
	
	@RequestMapping(value="/authorInfo")
	@ResponseBody
	public String getAuthorInfo(@RequestParam(value="testid", required=false) Integer testid,
			@RequestParam(value="testlanguage", required=false) String testlanguage) {
		LOG.info("testid="+testid+",testlanguage="+testlanguage);

		JsonArray array = new JsonArray();
		Map<String, Object> mapPara = new HashMap<String, Object>();
		List<Map<String, Object>> authors = authorService.selectAuthorByBootGrid(mapPara);
		JsonObject jsonObj = null;
		for (Map<String, Object> kv : authors) { 
			jsonObj = new JsonObject();
			for (Map.Entry<String, Object> entry : kv.entrySet()) {
				jsonObj.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
			}
			array.add(jsonObj);
		}

		LOG.info("array="+array.toString());

		return array.toString();
	}

	@RequestMapping(value="/form")
	@ResponseBody
	public String handleFormData(@RequestParam(value="name", required=false) String name,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="subject", required=false) String subject,
			@RequestParam(value="message", required=false) String message) {
		String status = "{\"status\": \"success\"}";

		LOG.info("name="+name+",email="+email);
		LOG.info("subject="+subject+",message="+message);

		return status;
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
