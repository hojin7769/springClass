package org.sist.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping( "index.htm")
	public String home( ) throws Exception {
		return "home.index";
	}
		
}//class
