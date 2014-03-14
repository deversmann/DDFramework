package com.eversmann.framework.dd.template.domain;

import java.util.ArrayList;
import java.util.List;

import com.eversmann.framework.dd.domain.DomainBase;


/**
 * @author dje
 *
 */
public class Hello extends DomainBase {
	private String name;
	
	/**
	 * 
	 */
	public Hello() {
		setName("World");
	}
	
	/**
	 * @param name
	 */
	public Hello(String name) {
		this.name = name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.eversmann.framework.dd.domain.DomainBase#getBusinessKeys()
	 */
	@Override
	public List<Object> getBusinessKeys() {
		List<Object> keys = new ArrayList<Object>();
		keys.add(getName());
		return keys;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
