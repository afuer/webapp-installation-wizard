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
