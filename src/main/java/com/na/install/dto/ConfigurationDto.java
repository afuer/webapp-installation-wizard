package com.na.install.dto;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement
public class ConfigurationDto implements Serializable {
	
	/** */
	private static final long serialVersionUID = 1L;
	
	private Collection<SectionDto> sections = Lists.newArrayList();

	/**
	 * @return the sections
	 */
	public Collection<SectionDto> getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(Collection<SectionDto> sections) {
		this.sections = sections;
	}

}
