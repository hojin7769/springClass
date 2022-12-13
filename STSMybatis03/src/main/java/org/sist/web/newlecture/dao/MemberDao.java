package org.sist.web.newlecture.dao;

import java.sql.SQLException;


import org.sist.web.newlecture.vo.Member;

public interface MemberDao {
	
	
	 Member getMember(String id) throws ClassNotFoundException, SQLException;
	 int insert(Member member) throws ClassNotFoundException, SQLException;
	
}
