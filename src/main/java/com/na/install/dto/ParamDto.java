/**
 * 
 */
package com.na.install.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * A parameter to be set by the user during installation.
 * 
 * @author marian
 * 
 */
public class ParamDto {
	
	/** */
	public ParamDto() {
		
	}
	
	/** */
	public ParamDto(String name) {
		this();
		setName(name);
	}
	
	/** */
	public ParamDto(String name, String defaultValue, boolean mandatory) {
		this(name);
		setDefaultValue(defaultValue);
		setMandatory(mandatory);
	}
	
	/** */
	public ParamDto(String name, String defaultValue, boolean mandatory, String value) {
		this(name, defaultValue, mandatory);
		setValue(value);
	}
	
	private String name;
	private String defaultValue;
	private boolean mandatory;
	private String value;
	
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
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	
	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		return value;
	}
	
	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
