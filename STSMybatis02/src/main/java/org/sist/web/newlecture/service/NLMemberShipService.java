package org.sist.web.newlecture.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.sist.web.newlecture.dao.NoticeDao;
import org.sist.web.newlecture.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NLMemberShipService implements MemberShipService{

   //@Autowired
   //private NoticeDao noticeDao;

   @Autowired(required = false)
   private SqlSession sqlSession;

   // 1. 트랜잭션 처리가 안된 메서드 -> @어노테이션 사용. 
   @Override
   //@Transactional(propagation = Propagation.REQUIRED)
   public void insertAndPointUpOfMember(Notice notice, String id) throws ClassNotFoundException, SQLException {

      NoticeDao mybatisNoticeDao = this.sqlSession.getMapper(NoticeDao.class);
      mybatisNoticeDao.insert(notice);

      //      notice.setTitle( notice.getTitle() + " - 2" );
      //      this.noticeDao.insert(notice); 

   } 

}
