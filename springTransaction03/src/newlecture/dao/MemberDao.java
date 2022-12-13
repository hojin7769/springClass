package newlecture.dao;

import java.sql.SQLException;


import newlecture.vo.Member;

public interface MemberDao {
	
	
	 Member getMember(String id) throws ClassNotFoundException, SQLException;
	 int insert(Member member) throws ClassNotFoundException, SQLException;
	
}
