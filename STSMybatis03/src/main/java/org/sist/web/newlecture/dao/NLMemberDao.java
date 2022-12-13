package org.sist.web.newlecture.dao;

import java.sql.SQLException;

import org.sist.web.newlecture.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
@Component
public class NLMemberDao implements MemberDao{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//getMember()는 id 해당하는 회원 정보를 반환하는
	public Member getMember(String id) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM MEMBER "
				+ " WHERE ID = :id ";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", id);

		return this.jdbcTemplate.queryForObject(sql, paramSource
		,ParameterizedBeanPropertyRowMapper.newInstance(Member.class));
	}
	
	
	
	//2. 회원 가입
	public int insert(Member member) throws ClassNotFoundException, SQLException
	{
		String sql = "INSERT INTO "
				+ " MEMBER(ID, PWD, NAME, GENDER, BIRTH, IS_LUNAR, CPHONE, EMAIL, HABIT, REGDATE) "
				+ " VALUES( :id, :pwd, :name, :gender, :birth, :is_lunar, :cphone, :email, :habit, SYSDATE) ";

		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(member);
		
		
		
		return this.jdbcTemplate.update(sql,paramSource);
	}
	
	
	
}
