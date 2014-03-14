package com.eversmann.framework.dd.data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * An abstract generic implementation of {@link GenericDao}. By simply subclassing this implementation
 * and setting the generic types, a typed DAO is created.  No further coding is needed to implement the
 * specified methods.
 * @author dje
 *
 * @param <T> the entity type this DAO will access
 * @param <ID> the type of the identifier used by this DAO.  Must extend {@link java.io.Serializable}
 */
public abstract class GenericDaoImpl<T, ID extends Serializable> extends DaoBase implements GenericDao<T, ID> {
	private static Log LOG = LogFactory.getLog(GenericDaoImpl.class);
	
	private Class<T> persistentClass;
	
	/**
	 * Handles determining the subclasses generic types for passing on to the Hibernate session.
	 * 
	 * @throws IllegalArgumentException if the subclass is not parameterized (i.e. the generic types are not defined)
	 */
	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)getClass().getGenericSuperclass();
			persistentClass = (Class<T>)pt.getActualTypeArguments()[0];
			if (LOG.isDebugEnabled()) {
				LOG.debug(getClass() + ": persistent class is " + persistentClass);
			}
		}
		else {
			LOG.error(getClass() + " is not a parameterized type");
			throw new IllegalArgumentException("Not a parameterized type");
		}
	}
	
	/**
	 * Returns the entity type defined by the sub class
	 * @return the persistentClass
	 */
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#getById(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T getById(ID id) {
		return (T) getCurrentSession().get(persistentClass, id);
	}
	
	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#findMatching(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findMatching(Map<String, ? extends Object> fieldValues) {
		Criteria crit = getCurrentSession().createCriteria(persistentClass);
		String key;
		Object value;
		for (Entry<String, ? extends Object> entry : fieldValues.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value instanceof Collection) {
				crit.add(Restrictions.in(key, (Collection<? extends Object>)value));
			}
			else if (value.getClass().isArray()) {
				crit.add(Restrictions.in(key, (Object[]) value));
			}
			else {
				crit.add(Restrictions.eq(key, value));
			}
		}
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#findMatching(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findLike(Map<String, ? extends Object> fieldValues) {
		Criteria crit = getCurrentSession().createCriteria(persistentClass);
		String key;
		Object value;
		for (Entry<String, ? extends Object> entry : fieldValues.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value instanceof Collection || value.getClass().isArray()) {
				LOG.error("Argument may not be a Collection or Array");
				throw new IllegalArgumentException("Argument may not be a Collection or Array");
			}
			else {
				crit.add(Restrictions.ilike(key, value));
			}
		}
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Retrieving all " + persistentClass.getSimpleName() + " entities from persistence");
		}
		Criteria crit = getCurrentSession().createCriteria(persistentClass);
		return crit.list();
	}
	
	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#save(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public ID save(T o) {
		ID id = (ID) getCurrentSession().save(o);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Persisted " + persistentClass.getSimpleName() + " entity: " + o + "; Assigned ID is: " + id);
		}
		return id;
	}
	
	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#update(java.lang.Object)
	 */
	public void update(T o) {
		getCurrentSession().update(o);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Updated " + persistentClass.getSimpleName() + " entity: " + o);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(T o) {
		getCurrentSession().saveOrUpdate(o);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SaveOrUpdated " + persistentClass.getSimpleName() + " entity: " + o);
		}
	}	

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.template.data.GenericDao#delete(java.io.Serializable)
	 */
	public void delete(ID id) {
		T o = getById(id);
		if (LOG.isDebugEnabled()) {
			if (o == null) {
				LOG.debug("No " + persistentClass.getSimpleName() + " entity identified by " + id + " found.");
			}
			else {
				LOG.debug("Deleting " + persistentClass.getSimpleName() + " entity: " + o);
			}
		}
		getCurrentSession().delete(o);
	}

}
