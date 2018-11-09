package com.sts.demo.controller;

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
import com.sts.demo.pojo.NBAStar;
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
	public String getData(@RequestParam(value="playerId", required=false, defaultValue="0") Integer playerId) {
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
	
}
