package com.sts.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sts.demo.pojo.Author;
import com.sts.demo.service.AuthorService;

@Controller
@RequestMapping(value="/index")
public class IndexController {

	@Autowired
	private AuthorService authorService;
	
//	@RequestMapping("/index")
//	public String index() {
//		return "index";
//	}
	
	@RequestMapping("/author")
	public String getAuthorInfo(ModelMap model) {
		List<Author> authorList = authorService.selectAllByList();
		model.addAttribute("authorList", authorList);
		return "index/authorList";
	}
	
}
