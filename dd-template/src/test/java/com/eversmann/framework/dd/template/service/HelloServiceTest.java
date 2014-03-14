package com.eversmann.framework.dd.template.service;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.eversmann.framework.dd.template.data.HelloDao;
import com.eversmann.framework.dd.template.domain.Hello;
import com.eversmann.framework.dd.template.service.HelloServiceImpl;

public class HelloServiceTest {
	private HelloDao mockDao;
	private HelloServiceImpl helloService;
	private Hello testHello;
	
	@Before
	public void setUp() {
		helloService = new HelloServiceImpl();
		mockDao = createMock(HelloDao.class);
		ReflectionTestUtils.setField(helloService, "helloDao", mockDao);
		testHello = new Hello();
		Date now = new Date();
		testHello.setCreated(now);
		testHello.setModified(now);
		testHello.setVersion(0);	
	}
	
	@Test
	public void testCreateHello() {
		expect(mockDao.save(testHello)).andReturn(1L);
		replay(mockDao);
		helloService.createHello(testHello);
		verify(mockDao);
	}

	@Test
	public void testGetHello() {
		testHello.setId(1L);
		expect(mockDao.getById(1L)).andReturn(testHello);
		replay(mockDao);
		Hello test = helloService.getHello(1L);
		assertEquals("Hello not returned as expected", testHello, test);
		verify(mockDao);
	}
	
	@Test
	public void testDeleteHello() {
		mockDao.delete(1L);
		replay(mockDao);
		helloService.deleteHello(1L);
		verify(mockDao);
	}
	
	@Test
	public void testUpdateHello() {
		testHello.setId(1L);
		mockDao.update(testHello);
		replay(mockDao);
		helloService.updateHello(testHello);
		verify(mockDao);
	}
		
	@Test
	public void testFindHellosByName() {
		testHello.setId(1L);
		List<Hello> list = new ArrayList<Hello>();
		list.add(testHello);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", "W%");
		expect(mockDao.findLike(parameters)).andReturn(list);
		replay(mockDao);
		List<Hello> test = helloService.findHellosByName("W");
		assertEquals("Hello list not returned as expected", list, test);
		verify(mockDao);
	}

	@Test
	public void testGetHellos() {
		testHello.setId(1L);
		List<Hello> list = new ArrayList<Hello>();
		list.add(testHello);
		expect(mockDao.getAll()).andReturn(list);
		replay(mockDao);
		List<Hello> test = helloService.getHellos();
		assertEquals("Hello list not returned as expected", list, test);
		verify(mockDao);
	}
}
