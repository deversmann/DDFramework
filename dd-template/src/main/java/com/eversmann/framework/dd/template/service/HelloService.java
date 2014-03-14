package com.eversmann.framework.dd.template.service;

import java.util.List;

import com.eversmann.framework.dd.template.domain.Hello;

/**
 * @author dje
 *
 */
public interface HelloService {

	/**
	 * @param nameStart
	 * @return
	 */
	public List<Hello> findHellosByName(String nameStart);
	
	/**
	 * @return
	 */
	public List<Hello> getHellos();
	
	/**
	 * @param id
	 * @return
	 */
	public Hello getHello(Long id);
	
	/**
	 * @param hello
	 */
	public void createHello(Hello hello);
	
	/**
	 * @param hello
	 */
	public void updateHello(Hello hello);
	
	/**
	 * @param id
	 */
	public void deleteHello(Long id);
	
}
