package com.eversmann.framework.dd.template.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.eversmann.framework.dd.template.data.HelloDao;
import com.eversmann.framework.dd.template.domain.Hello;

@ContextConfiguration({"/META-INF/conf/spring/templateproject-service.xml","/META-INF/conf/spring/templateproject-data.xml"})
public class HelloDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private HelloDao helloDao;

	@Test
	public void testCrud() {
		Hello safa = new Hello("Safa");
		long id = helloDao.save(safa);
		assertEquals("No Hello saved", 6, countRowsInTable("HELLO"));
		Hello test = helloDao.getById(id);
		assertEquals("Hello not properly saved or retrieved", safa, test);
		safa.setName("Safa Hussain");
		helloDao.update(safa);
		test = helloDao.getById(id);
		assertEquals("Hello not properly updated", "Safa Hussain", test.getName());
		helloDao.delete(id);
		assertNull("Hello not deleted", helloDao.getById(id));
	}
		
	@Test
	public void testGetAll() {
		List<Hello> hellos = helloDao.getAll();
		assertEquals("Wrong number of Hellos found", 5, hellos.size());
	}
	
	@Test
	public void testFindMatching() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "Damien");
		List<Hello> hellos = helloDao.findMatching(params);
		assertEquals("Wrong number of Hellos found", 1, hellos.size());
		if (hellos.size()==1) {
			assertEquals("Wrong Hello returned", "Damien", hellos.get(0).getName());
		}
		params.clear();
		params.put("name", "Zelda");
		hellos = helloDao.findMatching(params);
		assertEquals("Hellos returned when none should be", 0, hellos.size());
	}
	
	@Test
	public void testFindLike() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "D%");
		List<Hello> hellos = helloDao.findLike(params);
		assertEquals("Wrong number of Hellos found", 2, hellos.size());
		if (hellos.size()==2) {
			Assert.assertTrue("Wrong Hellos returned", hellos.contains(new Hello("Damien")) && hellos.contains(new Hello("Diana")));
		}
		params.clear();
		params.put("name", "Z%");
		hellos = helloDao.findLike(params);
		assertEquals("Hellos returned when none should be", 0, hellos.size());
	}
	
}
