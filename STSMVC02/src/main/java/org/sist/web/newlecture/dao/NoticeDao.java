package org.sist.web.newlecture.dao;

import java.sql.SQLException;
import java.util.List;

import org.sist.web.newlecture.vo.Notice;

public interface NoticeDao {



	 int getCount(String field, String query) throws ClassNotFoundException, SQLException;

	 List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;

	 int delete(String seq) throws ClassNotFoundException, SQLException;

	 int update(Notice notice) throws ClassNotFoundException, SQLException;

	 Notice getNotice(String seq) throws ClassNotFoundException, SQLException;

	 int insert(Notice n) throws ClassNotFoundException, SQLException;
	 
	 //격리성 연습을 위해 아래 메소드를 추가
	 public void hitUp(String seq);
	 public int getHit(String seq);
	 
	 
	 
	 

} // class
