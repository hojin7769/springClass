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

import newlecture.dao.NoticeDao;
import newlecture.vo.Notice;

@Controller
@RequestMapping("/customer/*")
public class CustomerController {
	
	@Autowired
	private NoticeDao noticeDao = null;
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	
	
	@RequestMapping("download.htm")
	   public void download(
	         @RequestParam("p") String p
	         , @RequestParam("f") String f
	         , HttpServletRequest request
	         , HttpServletResponse response)
	               throws IOException {

	      /*파일 이름에 대한 인코딩 처리 추가*/      
	//System.out.println(f);
	      String fname =  f; //  new String(f.getBytes("ISO8859_1"), "UTF-8"); // f
	//System.out.println(fname);
	      response.setHeader("Content-Disposition",
	            "attachment;filename="+ new String(fname.getBytes(), "ISO8859_1"));      
	      /*파일 다운로드가 가능하도록 하기 위한 물리적 경로*/
	      String fullPath = request.getServletContext().getRealPath(   p + "/" + fname);
	      /*파일 다운로드에 대한 처리 과정 추가*/
	      FileInputStream fin = new FileInputStream(fullPath);
	      ServletOutputStream sout = response.getOutputStream(); // 응답 스트림
	      byte[] buf = new byte[1024];
	      int size = 0;
	      while((size = fin.read(buf, 0, 1024)) != -1) {
	         sout.write(buf, 0, size); 
	      }
	      fin.close();
	      sout.close();
	   } // method
	
	
	
	
	
	
	//noticeDel.htm?seq=5
	//삭제 GET 방식
	@RequestMapping(value = { "noticeDel.htm" },method = RequestMethod.GET)
	public String noticeDel(String seq , HttpServletRequest request
							,String filesrc) throws Exception {
		//seq 게시글이 첨부 파일이 있는 경우에는 첨부 파일 삭제
		
		
		String uploadPath = request.getServletContext().getRealPath("/customer/upload");
		File delOFile = new File(uploadPath,filesrc);
		if (delOFile.exists()) {
			delOFile.delete();
		}
		//
		
		int resultCount =  this.noticeDao.delete(seq);
		return "redirect:notice.htm";
	}
	
	
	
	//noticeEdit.htm?seq=5
	//수정 GET 방식
	@RequestMapping(value = { "noticeEdit.htm" },method = RequestMethod.GET)
	public String noticeEdit(
						String seq,
						Model model) throws Exception {
		Notice notice =  this.noticeDao.getNotice(seq);
		model.addAttribute("notice", notice);
		return "noticeEdit.jsp";
	}
	//수정 POST 방식
	@RequestMapping(value = {"noticeEdit.htm"},method = RequestMethod.POST )
	public String noticeEdit( Notice notice,
							@RequestParam("oFilesrc") String oFilesrc
							,HttpServletRequest request) throws Exception {
		CommonsMultipartFile multipartFile =  notice.getFile();
		String uploadPath = null;
		if (!multipartFile.isEmpty()) {  //수정 첨부파일 O
			//새로 첨부 파일 저장
			String originalFilename = multipartFile.getOriginalFilename();
			uploadPath = request.getServletContext().getRealPath("/customer/upload");
			System.out.println(">uploadPath : " + uploadPath);
			//파일이름 중복
			originalFilename = getFileNameCheck(uploadPath, originalFilename);
			File dest = new File(uploadPath, originalFilename);
			//2.파일 업로드 경로에 저장
			multipartFile.transferTo(dest);
			// 3. notice 객체 추가
			notice.setFilesrc(originalFilename);
			
			// 이전 파일이 존재 한다면 삭제
			File delOFile = new File(uploadPath,oFilesrc);
			if (delOFile.exists()) {
				delOFile.delete();
			}
		}else { // 없다면
			notice.setFilesrc(oFilesrc);
		}
		int resultCount = this.noticeDao.update(notice);
		
		
		//sendRedirect
		//redirect: 접두사 컨트롤러 메서드에서 다른 컨트롤러메서드를 호출
		return "redirect:noticeDetail.htm?seq="+notice.getSeq();
	}
	
	//등록 GET 방식
	@RequestMapping(value ={"noticeReg.htm"},method = RequestMethod.GET)
	public String noticeReg() throws Exception {
		
		return "noticeReg.jsp";
	}
	//Post
	//2
		@RequestMapping(value = {"noticeReg.htm"},method = RequestMethod.POST )
		public String noticeReg( Notice notice , HttpServletRequest request ) throws Exception {
			//첨부된 파일 유무를 확인해서...
			CommonsMultipartFile multipartFile =  notice.getFile();
			String uploadPath = null; // 업로드할 경로
			if (!multipartFile.isEmpty()) { // 첨부된 파일이 있을 경우
				String originalFilename = multipartFile.getOriginalFilename();
				uploadPath = request.getServletContext().getRealPath("/customer/upload");
				System.out.println(">uploadPath : " + uploadPath);
				//파일이름 중복
				originalFilename = getFileNameCheck(uploadPath, originalFilename);
				File dest = new File(uploadPath, originalFilename);
				//2.파일 업로드 경로에 저장
				multipartFile.transferTo(dest);
				// 3. notice 객체 추가
				notice.setFilesrc(originalFilename);
			}

			
			//
			int count = this.noticeDao.insert(notice);
			//
			
			//sendRedirect
			//redirect: 접두사 컨트롤러 메서드에서 다른 컨트롤러메서드를 호출
			return "redirect:notice.htm";
		}
		
		
		public String getFileNameCheck(String uploadPath 
		         , String originalFilename) {   
		      int index = 1;
		      while(true) {
		      File f = new File(uploadPath, originalFilename); // 파일객체
		      if( !f.exists()) return originalFilename;   
		      originalFilename = 
		            originalFilename.substring(0, originalFilename.length()-4)
		            +" - "
		            + (index++)
		            + originalFilename.substring(originalFilename.length()-4); 
		      } // while 문 닫기
		   }
	
	/*
	//1
	@RequestMapping(value = {"noticeReg.htm"},method = RequestMethod.POST )
	public String noticeReg(String title , String content) throws Exception {
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);

		int count = this.noticeDao.insert(notice);
		
		
		//sendRedirect
		//redirect: 접두사 컨트롤러 메서드에서 다른 컨트롤러메서드를 호출
		return "redirect:notice.htm";
	}
	
	*/
	
	
	
	//NoticeDetailController.java
	//2.
		@RequestMapping("noticeDetail.htm")
		public String noticeDetail(
				String seq,
				Model model
				) throws Exception {
				
				//1.조회수 1 증가 쿼리
				//2. 해당 게시글 얻어오는 쿼리
				Notice notice = this.noticeDao.getNotice(seq);
				
				model.addAttribute("notice", notice);		
				return "noticeDetail.jsp";
			}

	/*
	//1.
	@RequestMapping("/customer/noticeDetail.htm")
	public ModelAndView noticeDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			String seq = request.getParameter("seq");
			//1.조회수 1 증가 쿼리
			//2. 해당 게시글 얻어오는 쿼리
			Notice notice = this.noticeDao.getNotice(seq);
			
			
			
			ModelAndView mv = new ModelAndView();
			mv.addObject("notice", notice);		
			mv.setViewName("noticeDetail.jsp");
			return mv;
		}
	*/
	
	
	//NoticeController.java
	//3.
	@RequestMapping("notice.htm")
	public String notices(
					@RequestParam(value="pg",defaultValue = "1") int page ,
					@RequestParam(value="field",defaultValue = "title") String field,
					@RequestParam(value="query",defaultValue = "%%") String query,
					Model model ) throws Exception {
		
		//NoticeDao noticeDao = new NoticeDao();
		
		List<Notice> list = noticeDao.getNotices(page,field,"%"+query+"%");
		
		
		
		model.addAttribute("test", "Hello. Spring MVC(world)");
		model.addAttribute("list", list);  //requset.setAttribute("list",list);
		return "notice.jsp";
	}
	
	
	
	
	
	
	
	
	
	/*
	// 2 NoticeController.java
		@RequestMapping("/customer/notice.htm")
		public String notices(String pg ,String field,String query,Model model) throws Exception {
			
			
			
			int _page = 1;
			String _field = "title",_query = "%%";
			//
			if(pg !=null && !pg.equals("")) _page = Integer.parseInt(pg);
			
			if(field !=null && !field.equals("")) _field = field;
			
			if(query !=null && !query.equals("")) _query = "%"+query+"%";
			
			//NoticeDao noticeDao = new NoticeDao();
			
			List<Notice> list = noticeDao.getNotices(_page,_field,_query);
			
			
			
			model.addAttribute("test", "Hello. Spring MVC(world)");
			model.addAttribute("list", list);  //requset.setAttribute("list",list);
			return "notice.jsp";
		}
		*/
	
	/*1
	//NoticeController.java
	@RequestMapping("/customer/notice.htm")
	public ModelAndView notices(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
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
	*/
	
	
	
	
	

}
