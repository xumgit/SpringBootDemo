package com.sts.demo.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sts.demo.service.NBAStarService;

@Controller
@RequestMapping(value="/demo")
public class DemoController {
	
	private static final Logger LOG = LogManager.getLogger(DemoController.class);
	
	@Autowired
	private NBAStarService nbaStarService;
	
	@RequestMapping(value="/index")
	public String index() {
		return "demo/index";
	}
	
	@RequestMapping(value="/getData")
	@ResponseBody
	public String getData() {
		JsonObject data = new JsonObject();
		JsonArray array = new JsonArray();
		List<Map<String, Object>> nbaStar = null;
		nbaStar = nbaStarService.selectAll();
		JsonObject jsonObj = null;
		for (Map<String, Object> kv : nbaStar) { 
			jsonObj = new JsonObject();
			for (Map.Entry<String, Object> entry : kv.entrySet()) {
				jsonObj.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
			}
			array.add(jsonObj);
		}
		data.add("rows", array);
		
		LOG.info("data:" + data.toString());
		return data.toString();
	}
	
}
