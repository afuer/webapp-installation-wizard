/**
 * 
 */
package com.na.install;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.na.install.dto.ConfigurationDto;
import com.na.install.dto.ParamDto;
import com.na.install.dto.SectionDto;
import com.na.install.integration.Integrator;

/**
 * @author marian
 * 
 */
@Path("initialization")
public class InstallationWizard {
	
	/** Loads instance of Integrator using reflection. */
	public InstallationWizard() throws InstantiationException, IllegalAccessException {
		String packageToScan = "com.na.install";
		
		Set<Class<? extends Integrator>> implementations = new Reflections(packageToScan)
				.getSubTypesOf(Integrator.class);
		if (implementations == null || implementations.size() == 0) {
			throw new RuntimeException(String.format("Didn't find implementation of %s in package %s.",
					Integrator.class.getName(), packageToScan));
		}
		
		if (implementations.size() > 1) {
			log.warn("There are several implementations of " + Integrator.class.getName());
		}
		this.integrator = Iterables.get(implementations, 0).newInstance();
	}
	
	public static final String PROPERTIES_SUBPATH = "META-INF" + PropertiesHelper.SEPARATOR;
	public static final String PROPERTIES_FILE = "application.properties";
	public static final String PROPERTIES_RESOURCES = "classpath*:" + PROPERTIES_SUBPATH
			+ PROPERTIES_FILE;
	
	static final Logger log = LoggerFactory.getLogger(InstallationWizard.class);
	
	private PropertiesHelper propsHelepr = new PropertiesHelper();
	private Integrator integrator;
	
	@Context
	ServletContext context;
	
	/**
	 * @throws URISyntaxException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response cfgStructure() throws URISyntaxException {
		if (propsHelepr.exist(getPathToProperties(), PROPERTIES_FILE)) {
			return createRedicrectResponse();
		}
		ConfigurationDto cfg = integrator.createConfigurationStructure();
		
		return Response.ok(cfg).build();
	}
	
	/**
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveConfig(ConfigurationDto newCfg) throws FileNotFoundException, IOException,
			URISyntaxException {
		if (propsHelepr.exist(getPathToProperties(), PROPERTIES_FILE)) {
			return createRedicrectResponse();
		}
		
		Properties props = new Properties();
		for (SectionDto section : newCfg.getSections()) {
			for (ParamDto param : section.getParams()) {
				props.setProperty(param.getName(), param.getValue());
				log.debug(String.format("Saving property %s with value %s.", param.getName(),
						param.getValue()));
			}
		}
		
		propsHelepr.saveProperties(props, getPathToProperties(), PROPERTIES_FILE);
		
		return Response.ok().build();
	}
	
	private Response createRedicrectResponse() {
		return Response.seeOther(integrator.getUriForRedirection()).build();
	}
	
	private String getPathToProperties() {
		return context.getRealPath("/") + "WEB-INF" + PropertiesHelper.SEPARATOR + "classes"
				+ PropertiesHelper.SEPARATOR + PROPERTIES_SUBPATH;
	}
	
}
