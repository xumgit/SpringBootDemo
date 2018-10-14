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
import com.sts.demo.service.AuthorService;

@Controller
@RequestMapping(value="/angularjs")
public class AngularJSController {
	
	private static final Logger LOG = LogManager.getLogger(AngularJSController.class);
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(value="/index")
	public String index() {
		return "angularjs/index";
	}
	
	@RequestMapping(value="/getdata")
	@ResponseBody
	public String getData(@RequestParam(value="id", required=false) Integer id,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="age", required=false) Integer age) {
		String status = "{\"status\": \"success\"}";
		
		LOG.info("id="+id+",name="+name+",age="+age);
		
		return status;
	}
	
	@RequestMapping(value="/getAuthor")
	@ResponseBody
	public String getAuthor() {
		JsonObject data = new JsonObject();
		JsonArray array = new JsonArray();
		List<Map<String, Object>> authors = authorService.selectAll();
		JsonObject jsonObj = null;
		for (Map<String, Object> kv : authors) { 
			jsonObj = new JsonObject();
			for (Map.Entry<String, Object> entry : kv.entrySet()) {
				jsonObj.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
			}
			array.add(jsonObj);
		}		
		data.add("rows", array);
		
		return data.toString();
	}
	
	@RequestMapping(value="/login")
	public String login() {
		LOG.info("login");
		return "angularjs/login";
	}
	
}
