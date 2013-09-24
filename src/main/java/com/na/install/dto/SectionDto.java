/**
 * 
 */
package com.na.install.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A collection of conceptually similar parameters.
 * 
 * @author marian
 * 
 */
public class SectionDto {
	
	private String name;
	
	private Collection<ParamDto> params = new ArrayList<ParamDto>();
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the params
	 */
	public Collection<ParamDto> getParams() {
		return params;
	}
	
	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(List<ParamDto> params) {
		this.params = params;
	}
}
