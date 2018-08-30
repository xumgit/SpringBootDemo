package com.sts.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/freemarker")
public class FreeMarkerController {

	@RequestMapping(value="/index")
	public String index(Map<String, Object> map) {
		map.put("name", "Test");
		map.put("sex", 1); 
		List<Map<String, Object>> friends = new ArrayList<Map<String, Object>>();
        Map<String, Object> friend = new HashMap<String, Object>();
        friend.put("name", "xbq");
        friend.put("age", 22);
        friends.add(friend);
        friend = new HashMap<String, Object>();
        friend.put("name", "July");
        friend.put("age", 18);
        friends.add(friend);
        map.put("friends", friends);
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("name", "test");
        m.put("age", 19);
        m.put("height", "175cm");
        map.put("m", m);
        
        return "freemarker/index";
	}
	
	@RequestMapping(value="/test")
	public String test(Map<String, Object> map) {	
		return "freemarker/test";
	}
	
}
