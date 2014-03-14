package com.eversmann.framework.dd.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eversmann.framework.dd.template.data.HelloDao;
import com.eversmann.framework.dd.template.domain.Hello;

/**
 * @author dje
 *
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService {
	
	@Autowired
	private HelloDao helloDao;

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.service.HelloService#findHellosByName(java.lang.String)
	 */
	@Override
	public List<Hello> findHellosByName(String nameStart) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", nameStart.trim()+"%");
		return helloDao.findLike(parameters);
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.service.HelloService#getHellos()
	 */
	@Override
	public List<Hello> getHellos() {
		return helloDao.getAll();
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.service.HelloService#getHello(java.lang.Long)
	 */
	@Override
	public Hello getHello(Long id) {
		return helloDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.service.HelloService#createHello(com.eversmann.framework.dd.template.domain.Hello)
	 */
	@Override
	public void createHello(Hello hello) {
		helloDao.save(hello);
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.service.HelloService#updateHello(com.eversmann.framework.dd.template.domain.Hello)
	 */
	@Override
	public void updateHello(Hello hello) {
		helloDao.update(hello);
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.service.HelloService#deleteHello(java.lang.Long)
	 */
	@Override
	public void deleteHello(Long id) {
		helloDao.delete(id);
	}

}
