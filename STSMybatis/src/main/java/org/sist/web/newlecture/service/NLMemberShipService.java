 package org.sist.web.newlecture.service;

import java.sql.SQLException;

import org.sist.web.newlecture.dao.NoticeDao;
import org.sist.web.newlecture.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NLMemberShipService implements MemberShipService {
	
	@Autowired
	private NoticeDao noticeDao;
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAndPointUpOfMember(Notice notice, String id) throws ClassNotFoundException, SQLException {

			this.noticeDao.insert(notice);
			
			
			//notice.setTitle(notice.getTitle()+" -2 ");
			//this.noticeDao.insert(notice);
			
		
	}

}
