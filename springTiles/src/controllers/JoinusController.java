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
@RequestMapping("/joinus/*")
public class JoinusController {
	
	@Autowired
	private MemberDao memberDao = null;
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}	
	
	
	
	
	
	//회원 가입(join) GET 방식
	@RequestMapping(value = { "join.htm" },method = RequestMethod.GET)
	public String join( ) throws Exception {
		return "joinus.join";
	}
	// 회원 가입(join) POST 방식
	@RequestMapping(value = {"join.htm"},method = RequestMethod.POST )
	public String join( Member member ) throws Exception {
		this.memberDao.insert(member);
		return "redirect:../index.htm";
	}
	
	
	//로그인 GET 방식
	@RequestMapping(value ={"login.htm"},method = RequestMethod.GET)
	public String login() throws Exception {
		return "joinus.login";
	}
	
		
}//class
