package com.eversmann.framework.dd.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A generic DAO specification for providing basic CRUD functionality along with parameterized "exact"
 * and "like" searching.
 * @author dje
 *
 * @param <T> the entity type this DAO will access
 * @param <ID> the type of the identifier used by this DAO.  Must extend {@link java.io.Serializable}
 */
public interface GenericDao <T, ID extends Serializable> {

	/**
	 * Retreives an entity by its identifier
	 * @param id the identifier of the entity being requested
	 * @return the entity referenced by the identifier
	 */
	public T getById(ID id);

	/**
	 * Retrieves a <code>List</code> of all entities accessed by this DAO
	 * @return all entities of type <code>T</code>
	 */
	public List<T> getAll();

	/**
	 * Retrieves a <code>List</code> of all entities accessed by this DAO matching the supplied parameters
	 * @param parameters a <code>Map</code> of parameter names to values used to limit this search.  If a parameter
	 * 	is a <code>Collection</code> or Array, "in" matching will be used, other types must match exactly.
	 * @return all entities of type <code>T</code> matching the parameters
	 */
	public List<T> findMatching(Map<String, ? extends Object> parameters);

	/**
	 * Retrieves a <code>List</code> of all entities accessed by this DAO matching the supplied parameters.  Matching is 
	 * done in a case insensitive manner and allows for the '%' wildcard in <code>String</code> parameters.
	 * @param parameters a <code>Map</code> of parameter names to values used to limit this search. Due to the 
	 * nature of 'like' mapping, parameters cannot be <code>Collection</code> or Array.
	 * @return all entities of type <code>T</code> matching the parameters
	 */
	public List<T> findLike(Map<String, ? extends Object> parameters);

	/**
	 * Persists the entity
	 * @param entity the entity to persist
	 * @return the identifier assigned upon persistence
	 */
	public ID save(T entity);

	/**
	 * Updates the entity
	 * @param entity the entity to update
	 */
	public void update(T entity);

	/**
	 * Updates the entity if it is already persisted or saves it if not.
	 * @param entity the entity to save or update
	 */
	public void saveOrUpdate(T entity);

	/**
	 * Deletes the entity identified from persistence
	 * @param id the identifier of the entity to delete
	 */
	public void delete(ID id);
	
}
