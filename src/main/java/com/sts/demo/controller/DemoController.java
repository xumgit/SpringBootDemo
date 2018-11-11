package com.sts.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sts.demo.pojo.NBAStar;
import com.sts.demo.service.NBAStarService;

@Controller
@RequestMapping(value="/demo")
public class DemoController {
	
	private static final Logger LOG = LogManager.getLogger(DemoController.class);
	private static Map<String, String> testPage = new HashMap<String, String>();
	static {
		testPage.put("ajax", "demo/test/ajax");
		testPage.put("binding", "demo/test/binding");
		testPage.put("directive", "demo/test/directive");
		testPage.put("event", "demo/test/event");
		testPage.put("expression", "demo/test/expression");
		testPage.put("filter", "demo/test/filter");
		testPage.put("form", "demo/test/form");
		testPage.put("http", "demo/test/http");
		testPage.put("injector", "demo/test/injector");
		testPage.put("model", "demo/test/model");
		testPage.put("module", "demo/test/module");
		testPage.put("promise", "demo/test/promise");
		testPage.put("scope", "demo/test/scope");
		testPage.put("select", "demo/test/select");
		testPage.put("service", "demo/test/service");
	}
	@Autowired
	private NBAStarService nbaStarService;
	
	@RequestMapping(value="/index")
	public String index() {
		return "demo/index";
	}
	
	@RequestMapping(value="/list")
	public String list() {
		return "demo/list";
	}
	
	@RequestMapping(value="/view")
	public String view() {
		return "demo/view";
	}
	
	@RequestMapping(value="/add")
	public String add() {
		return "demo/add";
	}
	
	@RequestMapping(value="/edit")
	public String edit() {
		return "demo/edit";
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public String update(@RequestParam(value="id", required=false, defaultValue="-1") Integer id,
			@RequestParam(value="number", required=false) Integer number,
			@RequestParam(value="thumb", required=false) String thumb,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="votes", required=false) Integer votes,
			@RequestParam(value="position", required=false) String position,
			@RequestParam(value="team", required=false) String team) {
		String status = "{\"status\":\"failed\"}";
		LOG.info("id:" + id + ",number:" + number + ",votes:" + votes + ",team:" + team);
		LOG.info("thumb:" + thumb + ",name:" + name + ",position:" + position);
		
		if (id != -1) {
			NBAStar nbaStar = new NBAStar();
			nbaStar.setId(id);
			if (number != null) {
				nbaStar.setNumber(number);
			}
			if (thumb != null) {
				nbaStar.setThumb(thumb);
			}
			if (name != null) {
				nbaStar.setName(name);
			}
			if (votes != null) {
				nbaStar.setVotes(votes);
			}
			if (position != null) {
				nbaStar.setPosition(position);
			}
			if (team != null) {
				nbaStar.setTeam(team);
			}
			int affectRow = nbaStarService.updateByPrimaryKeySelective(nbaStar);
			if (affectRow > 0) {
				status = "{\"status\":\"success\"}";
			}
		} else {
			LOG.error("id have some error");
		}
		
		return status;
	}
	
	@RequestMapping(value="/getData")
	@ResponseBody
	public String getData(@RequestParam(value="playerId", required=false, defaultValue="-1") Integer playerId) {
		LOG.info("playerId:" + playerId);
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
	
	@RequestMapping(value="/getOneData")
	@ResponseBody
	public String getOneDataById(@RequestParam(value="playerId", required=false, defaultValue="-1") Integer playerId) {
		JsonArray array = new JsonArray();
		LOG.info("playerId:" + playerId);
		
		if (playerId != -1) {
			List<Map<String, Object>> nbaStar = null;
			nbaStar = nbaStarService.selectByPrimaryKeyAnother(playerId);
			JsonObject jsonObj = null;
			for (Map<String, Object> kv : nbaStar) { 
				jsonObj = new JsonObject();
				for (Map.Entry<String, Object> entry : kv.entrySet()) {
					jsonObj.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
				}
				array.add(jsonObj);
			}
		}
		
		LOG.info("array:" + array.toString());
		return array.toString();
	}
	
	@RequestMapping(value="/deleteOneData")
	@ResponseBody
	public String deleteOneDataById(@RequestParam(value="playerId", required=false, defaultValue="-1") Integer playerId) {
		String status = "{\"status\":\"success\"}";
		
		if (playerId != -1) {
			int affectRow = nbaStarService.deleteByPrimaryKey(playerId);
			//int affectRow = 1; // just test, not need delete data
			if (affectRow < 0) {
				status = "{\"status\":\"failed\"}";
			} 
		} else {
			status = "{\"status\":\"failed\"}";
		}
		
		return status;
	}
	
	@RequestMapping(value="/addManyData")
	@ResponseBody
	public String addManyData(@RequestBody List<NBAStar> nbaStars) {
		String status = "{\"status\":\"failed\"}";
		
		if (nbaStars != null) {
			
			int affectRow = nbaStarService.insertManyData(nbaStars);
			if (affectRow > 0) {
				status = "{\"status\":\"success\"}";
			}
		} else {
			LOG.error("nbaStars have some error");
		}
		
		return status;
	}
	
	@RequestMapping(value="/addOneData")
	@ResponseBody
	public String addOneData(@RequestBody NBAStar nbaStar) {
		String status = "{\"status\":\"failed\"}";
		
		if (nbaStar != null) {
			LOG.info("id:" + nbaStar.getId() + ",name:" + nbaStar.getName());
			int affectRow = nbaStarService.insertOneData(nbaStar);
			if (affectRow > 0) {
				status = "{\"status\":\"success\"}";
			}
		} else {
			LOG.error("nbaStar have some error");
		}
		
		return status;
	}
	
	@RequestMapping(value="/angularTest")
	public String angularTest(@RequestParam(value="test", required=false, defaultValue="index") String test) {
		String url = "";
		LOG.info("test:" + test);
		
		if (testPage != null && testPage.containsKey(test)) {
			url = testPage.get(test);
		} else {
			url = "demo/test/index";
		}
		
		return url;
	}
	
}
