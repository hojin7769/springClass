package newlecture.dao;

import java.sql.SQLException;
import java.util.List;

import newlecture.vo.Notice;

public interface NoticeDao {



	 int getCount(String field, String query) throws ClassNotFoundException, SQLException;

	 List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException;

	 int delete(String seq) throws ClassNotFoundException, SQLException;

	 int update(Notice notice) throws ClassNotFoundException, SQLException;

	 Notice getNotice(String seq) throws ClassNotFoundException, SQLException;

	 int insert(Notice n) throws ClassNotFoundException, SQLException;
	 
	 //트랜잭션 테스트 용도로 메서드 추가.
	 void insertAndPointUpOfMember(Notice notice , String id ) throws ClassNotFoundException, SQLException;
	 

} // class
