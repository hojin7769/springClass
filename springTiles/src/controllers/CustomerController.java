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
import newlecture.service.MemberShipService;
import newlecture.vo.Notice;

@Controller
@RequestMapping("/customer/*")
public class CustomerController {
	
	@Autowired
	private NoticeDao noticeDao = null;
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	@Autowired
	private MemberShipService memberShipSerivce;
	
	
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
		return "customer.noticeEdit";
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
		
		return "customer.noticeReg";
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
			//int count = this.noticeDao.insert(notice);
			this.memberShipSerivce.insertAndPointUpOfMember(notice, "hojin");
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
	
	
	//NoticeDetailController.java
	//2.
		
		@RequestMapping("noticeDetail.htm")
		public String noticeDetail(
				String seq,
				Model model
				) throws Exception {
				
				//1.조회수 1 증가 쿼리
				this.noticeDao.hitUp(seq); //추가
				//2. 해당 게시글 얻어오는 쿼리
				Notice notice = this.noticeDao.getNotice(seq);
				
				model.addAttribute("notice", notice);		
				return "customer.noticeDetail";
			}

		
		//격리 수준 테스트 용
		/*
		@RequestMapping("noticeDetail.htm")
		public String noticeDetail(
				String seq,
				Model model
				) throws Exception {
				
				// A 스레드를 사용해서 hitUp() 호출 1초 sleep
				// B 스레드를 사용해서 getHit() 호출
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(500); // 혹시 getHit 먼저 실행되더라도 ....
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						noticeDao.getHit(seq);
					}
				},"getHit Thread").start();
				
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						noticeDao.hitUp(seq);
						
					}
				},"hitUp Thread").start();
				
				
				//
				Notice notice = this.noticeDao.getNotice(seq);
				
				model.addAttribute("notice", notice);		
				return "noticeDetail.jsp";
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
		
		
		//총 레코드 수
		
		int rowCount = this.noticeDao.getCount(field, query);
		// 총 페이지수 
		int pageCount = (int)Math.ceil((double)rowCount/15);
		model.addAttribute("pageCount", pageCount);
		
		//return "notice.jsp";
		return "customer.notice"; //타일즈 설정에 맞춰서 뷰 -> 응답....
	}
	
	
	

}//class
