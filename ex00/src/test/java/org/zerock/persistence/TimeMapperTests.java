package org.zerock.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;
import org.zerock.sample.SampleTests;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	
	@Setter(onMethod_ = { @Autowired } )
	private TimeMapper timeMapper;
	/*
	@Test
	public void testGetTime() {
		log.info(timeMapper.getClass().getName());
		log.info(timeMapper.getTime());
		//INFO : org.zerock.persistence.TimeMapperTests - com.sun.proxy.$Proxy22
		//INFO : org.zerock.persistence.TimeMapperTests - 2021-08-09 12:16:43
		
	}
	*/
	
	@Test
	public void testGetTiemXML() {
		log.info("> getTimeXML() + TimeMapper.xml <");
		log.info(timeMapper.getTimeXML());
		//INFO : org.zerock.persistence.TimeMapperTests - > getTimeXML() + TimeMapper.xml <
		//INFO : org.zerock.persistence.TimeMapperTests - 2021-08-14 12:24:48
		
	}

}
