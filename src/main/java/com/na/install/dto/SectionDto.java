/**
 * 
 */
package com.na.install.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A collection of conceptually similar parameters.
 * 
 * @author marian
 * 
 */
@XmlRootElement
public class SectionDto {
	
	/** */
	public SectionDto() {
		
	}
	
	/** */
	public SectionDto(String name) {
		this();
		setName(name);
	}
	
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
	public void setParams(Collection<ParamDto> params) {
		this.params = params;
	}
}
