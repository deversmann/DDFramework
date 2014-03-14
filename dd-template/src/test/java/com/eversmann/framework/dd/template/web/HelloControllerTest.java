package com.eversmann.framework.dd.template.web;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.eversmann.framework.dd.template.domain.Hello;
import com.eversmann.framework.dd.template.service.HelloService;
import com.eversmann.framework.dd.template.web.HelloController;
import com.eversmann.framework.dd.template.web.form.validator.HelloFormValidator;

public class HelloControllerTest {
	private HelloService mockService;
	private HelloController helloController;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private HandlerAdapter handler;
	
	private Hello testHello;
	
	@Before
	public void setUp() {
		mockService = createMock(HelloService.class);
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		request.setSession(session);
		handler = new AnnotationMethodHandlerAdapter();
		helloController = new HelloController();
		ReflectionTestUtils.setField(helloController, "helloService", mockService);
		ReflectionTestUtils.setField(helloController, "validator", new HelloFormValidator());

		testHello = new Hello();
		Date now = new Date();
		testHello.setCreated(now);
		testHello.setModified(now);
		testHello.setVersion(0);
	}
	
	@Test
	public void testList() throws Exception {
		testHello.setId(1L);
		List<Hello> list = new ArrayList<Hello>();
		list.add(testHello);
		expect(mockService.getHellos()).andReturn(list);
		replay(mockService);
		request.setRequestURI("/hello/list");
		final ModelAndView mav = handler.handle(request, response, helloController);
		verify(mockService);
		assertViewName(mav, "helloList");
		assertModelAttributeValue(mav, "helloList", list);
	}

	@Test
	public void testSearch() throws Exception {
		testHello.setId(1L);
		List<Hello> list = new ArrayList<Hello>();
		list.add(testHello);
		expect(mockService.findHellosByName("W")).andReturn(list);
		replay(mockService);
		request.setRequestURI("/hello/search");
		request.setParameter("name", "W");
		final ModelAndView mav = handler.handle(request, response, helloController);
		verify(mockService);
		assertViewName(mav, "helloList");
		assertModelAttributeValue(mav, "helloList", list);
	}

	@Test
	public void testNew() throws Exception {
		request.setRequestURI("/hello/new");
		final ModelAndView mav = handler.handle(request, response, helloController);
		assertViewName(mav, "helloEdit");
		assertModelAttributeValue(mav, "helloNew", new Hello());
	}
	
	@Test
	public void testSave() throws Exception {
		mockService.createHello(new Hello());
		replay(mockService);
		request.setRequestURI("/hello/save");
		request.addParameter("name", "World");
		session.setAttribute("helloNew", new Hello());
		final ModelAndView mav = handler.handle(request, response, helloController);
		assertViewName(mav,"redirect:list");
		verify(mockService);
	}

	@Test
	public void testSaveError() throws Exception {
		replay(mockService);
		request.setRequestURI("/hello/save");
		request.addParameter("name", "");
		session.setAttribute("helloNew", new Hello());
		final ModelAndView mav = handler.handle(request, response, helloController);
		final BindingResult errors = assertAndReturnModelAttributeOfType(mav, BindingResult.MODEL_KEY_PREFIX+"helloNew", BindingResult.class);
		assertTrue("Should have been an error", errors.hasErrors());
		assertViewName(mav,"helloEdit");
		verify(mockService);
	}
	
	@Test
	public void testEdit() throws Exception {
		testHello.setId(1L);
		expect(mockService.getHello(1L)).andReturn(testHello);
		replay(mockService);
		request.setRequestURI("/hello/edit");
		request.setParameter("id", "1");
		final ModelAndView mav = handler.handle(request, response, helloController);
		assertViewName(mav, "helloEdit");
		assertModelAttributeValue(mav, "helloEdit", testHello);
		verify(mockService);
	}
	
	@Test
	public void testUpdate() throws Exception {
		testHello.setId(1L);
		mockService.updateHello(testHello);
		replay(mockService);
		request.setRequestURI("/hello/update");
		request.addParameter("name", "World");
		session.setAttribute("helloEdit", testHello);
		final ModelAndView mav = handler.handle(request, response, helloController);
		assertViewName(mav,"redirect:list");
		verify(mockService);
	}

	@Test
	public void testUpdateError() throws Exception {
		testHello.setId(1L);
		replay(mockService);
		request.setRequestURI("/hello/update");
		request.addParameter("name", "");
		session.setAttribute("helloEdit", testHello);
		final ModelAndView mav = handler.handle(request, response, helloController);
		final BindingResult errors = assertAndReturnModelAttributeOfType(mav, BindingResult.MODEL_KEY_PREFIX+"helloEdit", BindingResult.class);
		assertTrue("Should have been an error", errors.hasErrors());
		assertViewName(mav,"helloEdit");
		verify(mockService);
	}
	
	@Test
	public void testDelete() throws Exception {
		mockService.deleteHello(1L);
		replay(mockService);
		request.setRequestURI("/hello/delete");
		request.setParameter("id", "1");
		final ModelAndView mav = handler.handle(request, response, helloController);
		assertViewName(mav, "redirect:list");
		verify(mockService);
	}
}
