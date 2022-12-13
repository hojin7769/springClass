package controllers.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import newlecture.dao.NoticeDao;
import newlecture.vo.Notice;

//@Component
@org.springframework.stereotype.Controller
@RequestMapping("/customer/notice.htm")
public class NoticeController implements Controller {
	//4
	@Autowired
	private NoticeDao noticeDao = null;
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	//4
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String p_page  = request.getParameter("pg");
		String p_field = request.getParameter("field");
		String p_query = request.getParameter("query");
		
		int page = 1;
		String field = "title",query = "%%";
		//
		if(p_page !=null && !p_page.equals("")) {
			page = Integer.parseInt(p_page);
		}
		
		if(p_field !=null && !p_field.equals("")) {
			field = p_field;
		}
		
		if(p_query !=null && !p_query.equals("")) {
			query = "%"+p_query+"%";
		}
		
		//
		//NoticeDao noticeDao = new NoticeDao();
		
		List<Notice> list = noticeDao.getNotices(page,field,query);
		
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("test", "Hello. Spring MVC(world)");
		mv.addObject("list", list);  //requset.setAttribute("list",list);
		mv.setViewName("notice.jsp");
		return mv;
	}
	
	
	
	/*
	//3
	//notice.htm?pg=1&field=title&query=test
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String p_page  = request.getParameter("pg");
		String p_field = request.getParameter("field");
		String p_query = request.getParameter("query");
		
		int page = 1;
		String field = "title",query = "%%";
		//
		if(p_page !=null && !p_page.equals("")) {
			page = Integer.parseInt(p_page);
		}
		
		if(p_field !=null && !p_field.equals("")) {
			field = p_field;
		}
		
		if(p_query !=null && !p_query.equals("")) {
			query = "%"+p_query+"%";
		}
		
		//
		NoticeDao noticeDao = new NoticeDao();
		
		List<Notice> list = noticeDao.getNotices(page,field,query);
		
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("test", "Hello. Spring MVC(world)");
		mv.addObject("list", list);  //requset.setAttribute("list",list);
		mv.setViewName("notice.jsp");
		return mv;
	}
	*/
	
	
	//2
/*
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		NoticeDao noticeDao = new NoticeDao();
		
		List<Notice> list = noticeDao.getNotices(1,"title","%%");
		
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("test", "Hello. Spring MVC(world)");
		mv.addObject("list", list);  //requset.setAttribute("list",list);
		mv.setViewName("notice.jsp");
		return mv;
	}
	*/
	
	
	
	
	//1
	/*
	 * @Override public ModelAndView handleRequest(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception {
	 * 
	 * 
	 * ModelAndView mv = new ModelAndView();
	 * 
	 * mv.addObject("test", "Hello. Spring MVC(world)");
	 * mv.setViewName("notice.jsp");
	 * 
	 * return mv; }
	 */
	
	
	

}//class
