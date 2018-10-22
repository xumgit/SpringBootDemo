package com.sts.demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sts.demo.pojo.Author;
import com.sts.demo.service.AuthorService;

@Controller
@RequestMapping(value="/angularjs")
public class AngularJSController {
	
	private static final Logger LOG = LogManager.getLogger(AngularJSController.class);
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	DefaultKaptcha defaultKaptcha;
	
	@RequestMapping(value="/index")
	public String index() {
		return "angularjs/index";
	}
	
	@RequestMapping(value="/error")
	public String loginError() {
		return "angularjs/error";
	}
	
	@RequestMapping(value="/success")
	public String loginSuccess() {
		return "angularjs/bootstrapAndAngular";
	}
	
	@RequestMapping(value="/updatedata")
	@ResponseBody
	public String updateData(@RequestParam(value="id", required=false, defaultValue="0") Integer id,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="age", required=false) Integer age) {
		String status = "{\"status\": \"failed\"}";
		
		LOG.info("id="+id+",name="+name+",age="+age);
		
		int affectRow = -1;
		if (id != 0) {
			Author author = new Author();
			author.setId(id);
			if (name != null) {
				author.setName(name);
			}
			if (age != null) {
				author.setAge(age);
			}
			affectRow = authorService.updateByPrimaryKeySelective(author);
		}
		LOG.info("affectRow=" + affectRow);
		if (affectRow > 0) {
			status = "{\"status\": \"success\"}";
		} 
		
		return status;
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
	
	@RequestMapping(value="/defaultKaptcha")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) throws Exception {
		 byte[] captchaChallengeAsJpeg = null;  
	     ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();  
	     try {  
	         String createText = defaultKaptcha.createText();
	         httpServletRequest.getSession().setAttribute("vrifyCode", createText);
             BufferedImage challenge = defaultKaptcha.createImage(createText);
             ImageIO.write(challenge, "jpg", jpegOutputStream);
	     } catch (IllegalArgumentException e) {  
	         httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);  
	         return;  
	     } 
	   
	     captchaChallengeAsJpeg = jpegOutputStream.toByteArray();  
	     httpServletResponse.setHeader("Cache-Control", "no-store");  
	     httpServletResponse.setHeader("Pragma", "no-cache");  
	     httpServletResponse.setDateHeader("Expires", 0);  
	     httpServletResponse.setContentType("image/jpeg");  
	     ServletOutputStream responseOutputStream =  httpServletResponse.getOutputStream();  
	     responseOutputStream.write(captchaChallengeAsJpeg);  
	     responseOutputStream.flush();  
	     responseOutputStream.close();  
	}

	@RequestMapping(value="/imgverifyControllerDefaultKaptcha")
	@ResponseBody
	public String imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse){
		 String status = "{\"status\":\"success\"}";
		 String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");  
		 String userName = httpServletRequest.getParameter("userName");
		 String password = httpServletRequest.getParameter("password");
		 String parameter = httpServletRequest.getParameter("vrifyCode");
		 LOG.info("userName="+userName+",password="+password);
		 LOG.info("Session  vrifyCode=" + captchaId + ",form vrifyCode=" + parameter);
		 
		 if (!"admin".equalsIgnoreCase(userName) || !"123456".equalsIgnoreCase(password)
				 || (captchaId != null && !captchaId.equalsIgnoreCase(parameter))) {
				status = "{\"status\":\"failed\"}";
		 }
		 		
		 return status;
	}


	
}
