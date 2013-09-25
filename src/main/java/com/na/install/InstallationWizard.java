/**
 * 
 */
package com.na.install;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.na.install.dto.ConfigurationDto;
import com.na.install.dto.ParamDto;
import com.na.install.dto.SectionDto;

/**
 * @author marian
 * 
 */
@Path("initialization")
public class InstallationWizard {
	
	public static final String PROPERTIES_SUBPATH = "META-INF" + PropertiesHelper.SEPARATOR;
	public static final String PROPERTIES_FILE = "application.properties";
	public static final String PROPERTIES_RESOURCES = "classpath*:" + PROPERTIES_SUBPATH
			+ PROPERTIES_FILE;
	
	static final Logger log = LoggerFactory.getLogger(InstallationWizard.class);
	
	private PropertiesHelper propsHelepr = new PropertiesHelper();
	
	@Context
	ServletContext context;
	
	/**
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigurationDto cfgStructure() {
		
		ConfigurationDto cfg = createConfigurationDto();
		
		return cfg;
	}
	
	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveConfig(ConfigurationDto newCfg) throws FileNotFoundException, IOException {
		
		Properties props = new Properties();
		for (SectionDto section : newCfg.getSections()) {
			for (ParamDto param : section.getParams()) {
				props.setProperty(param.getName(), param.getValue());
				log.debug(String.format("Saving property % with value %.", param.getName(),
						param.getValue()));
			}
		}
		
		propsHelepr.saveProperties(props, getPathToProperties(), PROPERTIES_FILE);
		
		// return Response.seeOther(uri).build();
		return Response.ok().build();
	}
	
	private String getPathToProperties() {
		return context.getRealPath("/") + "WEB-INF" + PropertiesHelper.SEPARATOR + "classes" + PropertiesHelper.SEPARATOR
				+ PROPERTIES_SUBPATH;
	}
	
	private ConfigurationDto createConfigurationDto() {
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
		return cfg;
	}
}
