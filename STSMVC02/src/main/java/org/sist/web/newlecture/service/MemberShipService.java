package org.sist.web.newlecture.service;

import java.sql.SQLException;

import org.sist.web.newlecture.vo.Notice;
//@Transactional
public interface MemberShipService {
	
	//트랜잭션 테스트 용도로 메서드 추가.
	//@Transactional
	void insertAndPointUpOfMember(Notice notice , String id ) throws ClassNotFoundException, SQLException;

}
