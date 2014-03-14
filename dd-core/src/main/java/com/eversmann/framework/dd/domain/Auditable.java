package com.eversmann.framework.dd.domain;

import java.util.Date;

/**
 * Defines an entity that tracks created and modified dates.
 * @author dje
 *
 */
public interface Auditable {

	/**
	 * Gets the created date
	 * @return the created date
	 */
	public Date getCreated();

	/**
	 * Sets the created date
	 * @param created the created date to set
	 */
	public void setCreated(Date created);

	/**
	 * Gets the modified date
	 * @return the modified date
	 */
	public Date getModified();

	/**
	 * Sets the modified date
	 * @param modified the modified date to set
	 */
	public void setModified(Date modified);

}
