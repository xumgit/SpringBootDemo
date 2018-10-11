package com.sts.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sts.demo.service.RedisService;

@Controller
@RequestMapping(value="/session")
public class SessionController {

	private static final Logger LOG = LogManager.getLogger(SessionController.class);
	
	@Autowired
	RedisService redisService;
	
	@RequestMapping(value="/index")
    public String index(HttpServletRequest request, ModelMap model){
        HttpSession session = request.getSession();
        String strSessionId = session.getId();
        int iPort = request.getServerPort();
        Object obj = session.getAttribute("session");
        if (obj == null) {
        	session.setAttribute("session", "sessionTest");
        } 
        LOG.info("strSessionId="+strSessionId);
        LOG.info("iPort="+iPort);
        LOG.info("obj="+obj);
        
        redisService.setStr("sessionId", strSessionId);
        
        model.addAttribute("strSessionId", strSessionId);
        model.addAttribute("iPort", iPort);
        model.addAttribute("obj", obj);
        
        return "session/index";
    }
	
}
