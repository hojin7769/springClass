package newlecture.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import newlecture.vo.Member;
@Repository
public class MemberDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//getMember()는 id 해당하는 회원 정보를 반환하는
	public Member getMember(String id) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM MEMBER "
				+ " WHERE ID = ? ";
		
		
		
		
		return this.jdbcTemplate.queryForObject(sql, new Object[] {id}
		,ParameterizedBeanPropertyRowMapper.newInstance(Member.class));
	}
	
	
	//1.
	/*
	public Member getMember(String id) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM MEMBER "
				+ " WHERE ID = ? ";
		
		
		//1.JDBC 드라이버 로딩.
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		//2.Connection 객체 얻어오는 코딩
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"SCOTT", "tiger");
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, id);
		ResultSet rs = st.executeQuery();
		
		Member member = null;
		
		if(rs.next())
		{
			member = new Member();
			member.setId(rs.getString("id")); 	//
			member.setPwd(rs.getString("pwd"));
			member.setName(rs.getString("name"));
			member.setGender(rs.getString("gender"));
			member.setBirth(rs.getString("birth"));
			member.setIs_lunar(rs.getString("is_lunar")); 	//
			member.setCphone(rs.getString("cphone"));	//	
			member.setEmail(rs.getString("email"));
			member.setHabit(rs.getString("habit"));
			member.setRegdate(rs.getDate("regdate"));	//
		}
		
		rs.close();
		st.close();
		con.close();
		
		return member;
	}
	*/
	
	//2. 회원 가입
	public int insert(Member member) throws ClassNotFoundException, SQLException
	{
		String sql = "INSERT INTO "
				+ " MEMBER(ID, PWD, NAME, GENDER, BIRTH, IS_LUNAR, CPHONE, EMAIL, HABIT, REGDATE) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";

		return this.jdbcTemplate.update(sql
				, member.getId(), member.getPwd(),member.getName()
				,member.getGender(),member.getBirth(),member.getIs_lunar()
				,member.getCphone(),member.getEmail(),member.getHabit());
	}
	
	
	//1.
	/*
	public int insert(Member member) throws ClassNotFoundException, SQLException
	{
		String sql = "INSERT INTO "
				+ " MEMBER(ID, PWD, NAME, GENDER, BIRTH, IS_LUNAR, CPHONE, EMAIL, HABIT, REGDATE) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, member.getId());//
		st.setString(2, member.getPwd());
		st.setString(3, member.getName());
		st.setString(4, member.getGender());
		st.setString(5, member.getBirth());
		st.setString(6, member.getIs_lunar());//
		st.setString(7, member.getCphone());//
		st.setString(8, member.getEmail());
		st.setString(9, member.getHabit());
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}
	*/
}
