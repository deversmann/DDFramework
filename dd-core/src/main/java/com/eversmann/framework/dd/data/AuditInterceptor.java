package com.eversmann.framework.dd.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.eversmann.framework.dd.domain.Auditable;

/**
 * This Hibernate interceptor provides for the automatic update of the <code>created</code> 
 * and <code>modified</code> properties of classes that implement the 
 * {@link com.eversmann.framework.dd.domain.Auditable} interface upon save or update. The save 
 * method first checks that the dates have not already been set.
 *  
 * @author dje
 *
 */
public class AuditInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 5505037031236391123L;
	private static Log LOG = LogFactory.getLog(AuditInterceptor.class);

	/* (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		
		if (entity instanceof Auditable) {
			for (int i=0; i<propertyNames.length; i++) {
				if ("modified".equals(propertyNames[i])) {
					currentState[i] = new Date();
					if (LOG.isDebugEnabled()) {
						LOG.debug("Setting 'modified' property to current date on " + entity);
					}
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		boolean stateModified = false;
		
		if (entity instanceof Auditable) {
			for (int i=0; i<propertyNames.length; i++) {
				if ("created".equals(propertyNames[i])) {
					if (state[i] == null) {
						state[i] = new Date();
						stateModified = true;
						if (LOG.isDebugEnabled()) {
							LOG.debug("Setting 'created' property to current date on " + entity);
						}
					}
				}
				else if ("modified".equals(propertyNames[i])) {
					if (state[i] == null) {
						state[i] = new Date();
						stateModified = true;
						if (LOG.isDebugEnabled()) {
							LOG.debug("Setting 'modified' property to current date on " + entity);
						}
					}
				}
			}
		}
		return stateModified;
	}

}
