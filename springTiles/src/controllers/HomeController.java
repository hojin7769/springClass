package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import newlecture.dao.MemberDao;
import newlecture.dao.NoticeDao;
import newlecture.vo.Member;
import newlecture.vo.Notice;

@Controller
@RequestMapping("/")
public class HomeController {

	@RequestMapping( "index.htm")
	public String home( ) throws Exception {
		return "home.index";
	}
		
}//class
