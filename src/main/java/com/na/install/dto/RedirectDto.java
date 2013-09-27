package com.na.install.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RedirectDto {
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
