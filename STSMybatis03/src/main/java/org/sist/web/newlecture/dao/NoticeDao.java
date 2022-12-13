package org.sist.web.newlecture.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.sist.web.newlecture.vo.Notice;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface NoticeDao {

	@Select("SELECT COUNT(*) CNT  FROM NOTICES  WHERE ${ field } LIKE '%${ query }%'")
	   public int getCount(
	         @Param("field")   String field
	         , @Param("query") String query
	         ) throws ClassNotFoundException, SQLException; 
	   
	   @Select(
	         "SELECT * FROM  "+
	         "  (  SELECT ROWNUM NUM, N.*  "+
	         "     FROM  "+
	         "      ( "+
	         "          SELECT * FROM NOTICES "+
	         "          WHERE ${ field } LIKE '%${ query }%'"+
	         "          ORDER BY REGDATE DESC "+
	         "       ) N"+
	         "  ) "+
	          " WHERE NUM BETWEEN (1 + (#{ page }-1)*15) AND (15 + (#{ page }-1)*15) "           
	         )
	   public List<Notice> getNotices(
	         @Param("page") int page
	         ,@Param("field") String field
	         ,@Param("query") String query) throws ClassNotFoundException, SQLException;
	   
	   @Delete(" DELETE        FROM NOTICES           WHERE SEQ= #{ seq }")
	   public int delete(String seq) throws ClassNotFoundException, SQLException;
	   
	   @Update("UPDATE NOTICES SET TITLE=#{title}, CONTENT=#{content},"  
	         + " FILESRC=#{filesrc} WHERE SEQ=#{seq}")
	   public int update(Notice notice) throws ClassNotFoundException, SQLException;    
	   
	   @Select("SELECT * FROM NOTICES WHERE SEQ= #{seq}")
	   public Notice getNotice(String seq) throws ClassNotFoundException, SQLException;   
	   
	   @SelectKey(before=true, keyProperty="seq", resultType=String.class
	         , statement=" SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES")
	   @Insert(" INSERT INTO NOTICES(SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC)     VALUES(  #{seq}   , #{title}, #{content}, #{writer} , SYSDATE, 0            , #{filesrc, javaType=String,  jdbcType=VARCHAR} )")
	   public int insert(Notice notice) throws ClassNotFoundException, SQLException;
	   
	   //  트랜잭션 테스트용도의 메서드 추가
	   // 기능 = 새글쓰기 + 포인트 증가
	    //public void insertAndPointUpOfMember(Notice notice, String id) throws ClassNotFoundException, SQLException;
	   
	   // 트랜잭션 격리성 테스트 용도로 메서드 2개 추가
	   @Update("update notices  set hit = hit + 1    where seq = #{ seq }")
	   public void hitUp(String seq);
	   @Select("select hit   from notices   where seq = #{ seq }")
	   public int getHit(String seq);
	

	 
	 
	 
	 

} // class
