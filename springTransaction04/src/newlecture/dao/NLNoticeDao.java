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
import org.springframework.dao.DataAccessException;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import newlecture.vo.Notice;
//@Component
@Repository
public class NLNoticeDao implements NoticeDao{
	
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//???????????? ????????? 2??? ?????????
	/*
	@Autowired
	private PlatformTransactionManager transactionManager;
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	*/
	
	//???????????? ????????? ?????? ???????????? ????????? ????????? ?????? 3??? ?????????
	@Autowired
	private TransactionTemplate transactionTemplate;
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}



	//?????? ??? ??????????????? ????????? ?????? ???????????? ?????????
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT "
				+ " FROM NOTICES "
				+ " WHERE "+field+" LIKE :query";
		
		// SqlParameterSource ???????????? ??????
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("query", query);
		
		
		return this.jdbcTemplate.queryForInt(sql,paramSource);
		
	}
	
	
	
	// ??????????????? ?????? ???????????? ????????? List??? ???????????? ?????????
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
	
	
	
	//?????? ???????????? ??????
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		
		String sql = "DELETE NOTICES "
				   + " WHERE SEQ = :seq ";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("seq", seq);
		
		return this.jdbcTemplate.update(sql, paramSource);
	}
	
	
	//?????? ???????????? ??????
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		
		
		String sql = "UPDATE NOTICES "
				+ " SET TITLE = :title, CONTENT = :content, FILESRC = :filesrc "
				+ " WHERE SEQ = :seq ";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
		
		
		return this.jdbcTemplate.update(sql,paramSource );
	}
	
	//2 Notice.java ???????????????, ???????????? ???????????? ????????? ??? ...
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
	
	
	
	// ????????????????????? ??????????????? ?????? ??? ??? ??????.
	// ????????? ???????????? ??????
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)	//?????????
	public int insert(Notice notice) throws ClassNotFoundException, SQLException {
		//1.???????????? ?????? ?????? ??????
				String sql = "INSERT INTO NOTICES "
						+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
						+ " VALUES( "
						+ " (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), :title, :content, 'hojin', SYSDATE, 0, :filesrc)";
				
				SqlParameterSource paramSource = new BeanPropertySqlParameterSource(notice);
				 this.jdbcTemplate.update(sql ,paramSource);
				 
				//2. ????????? Point ??????
				sql = "UPDATE MEMBER "
					+ " SET POINT = POINT +1 "
					+ " WHERE ID = :id ";
				
				MapSqlParameterSource paramSource2 = new MapSqlParameterSource();
				paramSource2.addValue("id", "hojin");
				return this.jdbcTemplate.update(sql, paramSource2);
	}



	//1. ???????????? ????????? ?????? ????????? -> @???????????????
	
	
	
		
	
} // class
