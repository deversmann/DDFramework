package com.eversmann.framework.dd.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple base DAO that provides for an autowired {@link org.hibernate.SessionFactory} and
 * accessor methods. Also provides a convenience method to get the current
 * {@link org.hibernate.Session}.
 * 
 * @author dje
 * @see org.springframework.beans.factory.annotation.Autowired
 */
public class DaoBase {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Log LOG = LogFactory.getLog(DaoBase.class);

	/**
	 * Gets the autowired {@link org.hibernate.SessionFactory}
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * Gets the current {@link org.hibernate.Session} from the {@link org.hibernate.SessionFactory}
	 * @return the current session
	 */
	public Session getCurrentSession() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Setting session factory for " + getClass().getName());
		}
		return sessionFactory.getCurrentSession();
	}

}
