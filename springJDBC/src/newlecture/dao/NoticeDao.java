package newlecture.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import newlecture.vo.Notice;
//@Component
@Repository
public class NoticeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	//검색 된 공지사항의 게시글 수를 반환하는 메서드
	//2.
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT "
				+ " FROM NOTICES "
				+ " WHERE "+field+" LIKE ?";
		//조회 :query()  어러개의 행, queryForObject() 한개의 행 , queryForList() 한개컬럼
		
		return this.jdbcTemplate.queryForInt(sql, new Object[] {"%"+query+"%"});
		
	}
	
	
	/*
	//1
	public int getCount(String field, String query) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT COUNT(*) CNT "
				+ " FROM NOTICES "
				+ " WHERE "+field+" LIKE ?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int cnt = rs.getInt("cnt");
		
		rs.close();
		st.close();
		con.close();
		
		return cnt;
	}
	*/
	
	// 공지사항의 해당 페이지의 목록을 List로 반환하는 메서드
	//2. JdbcTemplet
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
				+ "		  WHERE "+field+" LIKE ? "
						+ "ORDER BY REGDATE DESC "
				  + " ) N "
				+ " ) "
				+ "WHERE NUM BETWEEN ? AND ?" ;
		
		// 조회 : query()     / queryForList() / queryForObject()
		//        여러개의 행   컬럼 1개	        한행
		
	List<Notice> list = this.jdbcTemplate.query(sql
					, new Object[] {"%"+query+"%",srow,erow }, 
					new BeanPropertyRowMapper<Notice>(Notice.class));
		
		return list;
	}
	
	
	//1.
	/*
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
				+ "		  WHERE "+field+" LIKE ? "
						+ "ORDER BY REGDATE DESC "
				  + " ) N "
				+ " ) "
				+ "WHERE NUM BETWEEN ? AND ?" ;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");

		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1,query);
		st.setInt(2, srow);
		st.setInt(3, erow);
		
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>();
		
		while(rs.next()){
			Notice n = new Notice();
			n.setSeq(rs.getString("seq"));
			n.setTitle(rs.getString("title"));
			n.setWriter(rs.getString("writer"));
			n.setRegdate(rs.getDate("regdate"));
			n.setHit(rs.getInt("hit"));
			n.setContent(rs.getString("content"));
			n.setFilesrc(rs.getString("filesrc")); //fileSrc -> filesrc
			
			list.add(n);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
	*/
	
	//해당 공지사항 삭제
	//2.
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		
		String sql = "DELETE NOTICES "
				   + " WHERE SEQ = ? ";
		return this.jdbcTemplate.update(sql, seq);
	}
	
	//1.
	/*
	public int delete(String seq) throws ClassNotFoundException, SQLException
	{
		
		String sql = "DELETE NOTICES "
				   + " WHERE SEQ = ? ";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
		
		PreparedStatement st = con.prepareStatement(sql);	
		st.setString(1, seq);
		
		int af = st.executeUpdate();
		
		return af;
	}
	*/
	//해당 공지사항 수정
	//2.
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		
		
		String sql = "UPDATE NOTICES "
				+ " SET TITLE = ?, CONTENT = ?, FILESRC = ? "
				+ " WHERE SEQ = ? ";
		return this.jdbcTemplate.update(sql,notice.getTitle(),notice.getContent(),notice.getFilesrc(),notice.getSeq() );
	}
	//1.
	/*
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
		
		
		String sql = "UPDATE NOTICES "
				+ " SET TITLE = ?, CONTENT = ?, FILESRC = ? "
				+ " WHERE SEQ = ? ";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, notice.getTitle());
		st.setString(2, notice.getContent());
		st.setString(3, notice.getFilesrc());
		st.setString(4, notice.getSeq());		
		
		int af = st.executeUpdate();
		
		return af;
	}
	*/
	
	//해당 공지사항을 상세보기 NOTICE 객체로 반환하는 메서드
	/*
	//3. Notice.java 필드명하고, 테이블의 컬럼명이 동일하지 않을 경우 - 강제로 n 만들기
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM NOTICES "
				+ " WHERE SEQ = ? ";
		
		Notice n =  this.jdbcTemplate.queryForObject(sql , new Object[] {seq}
					,new RowMapper<Notice>() {

						@Override
						public Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
							
							Notice n = new Notice();
							n.setSeq(rs.getString("seq"));
							n.setTitle(rs.getString("title"));
							n.setWriter(rs.getString("writer"));
							n.setRegdate(rs.getDate("regdate"));
							n.setHit(rs.getInt("hit"));
							n.setContent(rs.getString("content"));
							n.setFilesrc(rs.getString("filesrc"));
							
							return n;
						}
			
		});

		return n;
	}
	*/
	//2 Notice.java 필드명하고, 테이블의 컬럼명이 동일할 때 ...
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM NOTICES "
				+ " WHERE SEQ = ? ";
		
		Notice n =  this.jdbcTemplate.queryForObject(sql , new Object[] {seq}
					,ParameterizedBeanPropertyRowMapper.newInstance( Notice.class ) );

		return n;
	}
	
	//1
	/*
	public Notice getNotice(String seq) throws ClassNotFoundException, SQLException
	{
		String sql = "SELECT * "
				+ " FROM NOTICES "
				+ " WHERE SEQ = "+seq;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
	
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
		
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(sql);
		rs.next();
		
		Notice n = new Notice();
		n.setSeq(rs.getString("seq"));
		n.setTitle(rs.getString("title"));
		n.setWriter(rs.getString("writer"));
		n.setRegdate(rs.getDate("regdate"));
		n.setHit(rs.getInt("hit"));
		n.setContent(rs.getString("content"));
		n.setFilesrc(rs.getString("filesrc"));
		
		rs.close();
		st.close();
		con.close();
		
		return n;
	}
	*/
	
	
	// 로그인해야지만 공지사항을 작성 할 수 있다.
	// 새로운 공지사항 작성
	//4.
	/*
	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		
		
		return this.jdbcTemplate.update( new PreparedStatementSetter() {	
			
			
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				String sql = "INSERT INTO NOTICES "
						+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
						+ " VALUES( "
						+ " (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'hojin', SYSDATE, 0, ?)";
				pstmt.setString(1, n.getTitle());
				pstmt.setString(2, n.getContent());
				pstmt.setString(3, n.getFilesrc());
				
			}
		});
		
	}
	*/
	//3.
	/*
	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		
		
		
		return this.jdbcTemplate.update( new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO NOTICES "
						+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
						+ " VALUES( "
						+ " (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'hojin', SYSDATE, 0, ?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, n.getTitle());
				pstmt.setString(2, n.getContent());
				pstmt.setString(3, n.getFilesrc());
				
				return pstmt;
			}
		});
	}
	*/
	
	//2.
	
	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		
		String sql = "INSERT INTO NOTICES "
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
				+ " VALUES( "
				+ " (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'hojin', SYSDATE, 0, ?)";
		
		//I,U,D : update();
		return this.jdbcTemplate.update(sql , n.getTitle() , n.getContent() , n.getFilesrc());
	}
	
	
	/*1.
	public int insert(Notice n) throws ClassNotFoundException, SQLException {
		
		String sql = "INSERT INTO NOTICES "
				+ " (SEQ, TITLE, CONTENT, WRITER, REGDATE, HIT, FILESRC) "
				+ " VALUES( "
				+ " (SELECT MAX(TO_NUMBER(SEQ))+1 FROM NOTICES), ?, ?, 'hojin', SYSDATE, 0, ?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				"scott", "tiger");
	
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, n.getTitle());
		st.setString(2, n.getContent());
		st.setString(3, n.getFilesrc());
		
		int af = st.executeUpdate();
		
		st.close();
		con.close();
		
		return af;
	}
	*/
} // class
