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
package com.na.install;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.na.install.dto.ConfigurationDto;
import com.na.install.dto.ParamDto;
import com.na.install.dto.SectionDto;
import com.na.install.integration.Integrator;

/**
 * @author marian
 * 
 */
public class DummyIntegrator implements Integrator {
	
	static final Logger log = LoggerFactory.getLogger(DummyIntegrator.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.na.install.integration.Integrator#getUriForRedirection()
	 */
	public URI getUriForRedirection() {
		try {
			return new URI("../install.html");
		} catch (URISyntaxException e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.na.install.integration.Integrator#createConfigurationStructure()
	 */
	public ConfigurationDto createConfigurationStructure() {
		ConfigurationDto cfg = new ConfigurationDto();
		SectionDto section = new SectionDto("Bugzilla data source");
		section.getParams().add(new ParamDto("bugzilla.db.driver", "com.mysql.jdbc.Driver", false));
		section.getParams().add(
				new ParamDto("bugzilla.db.url", "jdbc:mysql://192.168.10.195:3306/bugs", false));
		section.getParams().add(new ParamDto("bugzilla.db.username", "bugs", false));
		section.getParams().add(new ParamDto("bugzilla.db.password", "", false));
		section.getParams()
				.add(new ParamDto("bugzilla.datasource.jndi", "java:comp/env/jdbc/mysql/bugzilla",
						false));
		cfg.getSections().add(section);
		return cfg;
	}
	
}
