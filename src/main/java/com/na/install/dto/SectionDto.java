/**
 * Copyright (C) 2013 NetworkedAssets
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
