package newlecture.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import newlecture.vo.Notice;
//@Component
@Repository
public class NLNoticeDao implements NoticeDao{
	
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	//검색 된 공지사항의 게시글 수를 반환하는 메서드
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT "
				+ " FROM NOTICES "
				+ " WHERE "+field+" LIKE :query";
		
		// SqlParameterSource 파라미터 사용
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("query", query);
		
		
		return this.jdbcTemplate.queryForInt(sql,paramSource);
		
	}
	
	
	
	// 공지사항의 해당 페이지의 목록을 List로 반환하는 메서드
	public List<Notice> getNotices(int page, String field, String query) throws ClassNotFoundException, SQLException
	{					
		
		int srow = 1 + (page-1)*15; // 1, 16, 31, 46, 61, ... an = a1 + (n-1)*d
		int erow = 15 + (page-1)*15; //15, 30, 45, 60, 75, ...
		
		String sql = "SELECT * "
				+ " FROM "
				+ 	"(SELECT ROWNUM NUM, N.* "
				+ "	   FROM "
				+ "      (SELECT * "
				+ "       FROM NOTICES "
				+ "		  WHERE "+field+" LIKE :query "
						+ "ORDER BY REGDATE DESC "
				  + " ) N "
				+ " ) "
				+ "WHERE NUM BETWEEN :srow AND :erow " ;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("query", "%"+query+"%");
		params.put("srow", srow);
		params.put("erow", erow);
		
		
	List<Notice> list = this.jdbcTemplate.query(sql
												,params  
												,new BeanPropertyRowMapper<Notice>(Notice.class));
		
		return list;
	}
	
	
	
	//해당 공지사항 삭제
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		
		String sql = "DELETE NOTICES "
				   + " WHERE SEQ = :seq ";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seq", seq);
		
		return this.jdbcTemplate.update(sql, paramSource);
	}
	
	
	//해당 공지사항 수정
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		
		
		String sql = "UPDATE NOTICES "
				+ " SET TITLE = :title, CONTENT = :content, FILESRC = :filesrc "
				+ " WHERE SEQ = :seq ";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		
		
		return this.jdbcTemplate.update(sql,paramSource );
	}
	
	//2 Notice.java 필드명하고, 테이블의 컬럼명이 동일할 때 ...
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM NOTICES "
				+ " WHERE SEQ = :seq ";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seq", seq);
		
		Notice n =  this.jdbcTemplate.queryForObject(sql ,paramSource
					,ParameterizedBeanPropertyRowMapper.newInstance( Notice.class ) );

		return n;
	}
	
	
	
	// 로그인해야지만 공지사항을 작성 할 수 있다.
	// 새로운 공지사항 작성
	
	
	public int insert(Notice notice) throws ClassNotFoundException, SQLException {
		
		String sql = "INSERT INTO NOTICES "
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
				+ " VALUES( "
				+ " (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), :title, :content, 'hojin', SYSDATE, 0, :filesrc)";
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		return this.jdbcTemplate.update(sql ,paramSource);
	}
	
} // class
