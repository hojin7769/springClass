package controllers.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import newlecture.dao.NoticeDao;
import newlecture.vo.Notice;

public class NoticeDetailController implements Controller {
	
	private NoticeDao noticeDao = null;
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String seq = request.getParameter("seq");
		//1.조회수 1 증가 쿼리
		//2. 해당 게시글 얻어오는 쿼리
		Notice notice = this.noticeDao.getNotice(seq);
		
		
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("notice", notice);		
		mv.setViewName("noticeDetail.jsp");
		return mv;
	}

}
