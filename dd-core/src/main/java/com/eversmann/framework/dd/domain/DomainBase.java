package com.eversmann.framework.dd.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

/**
 * A simple abstract base domain entity which provides identifier, version, created date, 
 * modified date, hashCode, equals and generic toString.  It requires the subclass to list 
 * its business keys (i.e. fields containing business data or "candidate keys")
 * 
 * @author dje
 *
 */
public abstract class DomainBase implements Auditable {
	private Long id;
	private Integer version;
	private Date created;
	private Date modified;
	
	/**
	 * Sets the id of this object
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the id of this object
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the persistence version of this object
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the persistence version of this object
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.domain.Auditable#getCreated()
	 */
	public Date getCreated() {
		return created;
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.domain.Auditable#setCreated(java.util.Date)
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.domain.Auditable#getModified()
	 */
	public Date getModified() {
		return modified;
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.domain.Auditable#setModified(java.util.Date)
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	/**
	 * Determines if this object has been persisted (i.e. has an assigned id)
	 * @return <code>true</code> if an id has been assigned
	 */
	public boolean isNew() {
		return (this.id == null);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		List<Object> businessKeys = getBusinessKeys();
		if (null == businessKeys) {
			return super.toString();
		}
		StringBuffer buf = new StringBuffer();
		buf.append(this.getClass().getSimpleName()).append('[');
		for (int i=0; i<businessKeys.size(); i++) {
			buf.append(ObjectUtils.toString(businessKeys.get(i), "null"));
			if (i<businessKeys.size()-1) buf.append(',');
		}
		buf.append(']');
		return buf.toString();
	}
	
	/**
	 * Returns a list of all of the fields of the object that contain actual business
	 * data (i.e. the "candidate keys") for the purposes of equality comparison and 
	 * hash code computation.  These keys are also used to create a rudimentary
	 * toString algorithm.
	 * 
	 * @return a list of the business keys for this object 
	 */
	public abstract List<Object> getBusinessKeys();
	
	/**
	 * Computes a hashCode using the business keys defined by the subclass
	 * 
	 * @return the computed hashCode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		if (null == getBusinessKeys()) {
			return super.hashCode();
		}
		
		final int prime = 31;
		int result = 1;
		for (Object o : getBusinessKeys()) {
			if (o == null) {
				result = prime * result;
			}
			if (o.getClass().isArray()) {
				result = prime * result + Arrays.hashCode((Object[])o);
			}
			else {
				result = prime * result + o.hashCode();
			}
		}
		return result;
	}

	/**
	 * Tests for equality using the business keys defined by the subclass.  After
	 * verifying the presence and type of the object, this performs an equality
	 * comparison of all the defined business keys of the two objects.
	 * 
	 * @param obj the object against which to test equality
	 * @return <code>true</code> if all of the business keys are equal, 
	 * 	<code>false</code> otherwise
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainBase other = (DomainBase) obj;
		return this.getBusinessKeys().equals(other.getBusinessKeys());
	}

}
