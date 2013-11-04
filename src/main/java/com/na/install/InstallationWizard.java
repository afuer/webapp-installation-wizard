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

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.na.install.dto.ConfigurationDto;
import com.na.install.dto.ParamDto;
import com.na.install.dto.RedirectDto;
import com.na.install.dto.SectionDto;
import com.na.install.integration.Integrator;

/**
 * @author marian
 * 
 */
@Path("initialization")
public class InstallationWizard {

	private static final int HTTP_PARTIAL_CONTENT = 206;

	/** Loads instance of Integrator using reflection. */
	public InstallationWizard() throws InstantiationException,
			IllegalAccessException {
		String packageToScan = "com.na.install";

		Set<Class<? extends Integrator>> implementations = new Reflections(
				packageToScan).getSubTypesOf(Integrator.class);
		if (implementations == null || implementations.size() == 0) {
			throw new RuntimeException(String.format(
					"Didn't find implementation of %s in package %s.",
					Integrator.class.getName(), packageToScan));
		}

		if (implementations.size() > 1) {
			log.warn("There are several implementations of "
					+ Integrator.class.getName());
		}
		this.integrator = Iterables.get(implementations, 0).newInstance();
	}

	public static final String PROPERTIES_SUBPATH = "META-INF"
			+ PropertiesHelper.SEPARATOR;
	public static final String PROPERTIES_FILE = "application.properties";
	/** Where the properties are saved. */
	public static final String PROPERTIES_RESOURCES = "classpath*:"
			+ PROPERTIES_SUBPATH + PROPERTIES_FILE;

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
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveConfig(ConfigurationDto newCfg)
			throws FileNotFoundException, IOException, URISyntaxException {
		if (propsHelepr.exist(getPathToProperties(), PROPERTIES_FILE)) {
			return createRedicrectResponse();
		}

		Properties props = new Properties();
		for (SectionDto section : newCfg.getSections()) {
			for (ParamDto param : section.getParams()) {
				if (StringUtils.isNotBlank(param.getName())) {
					String value = param.getValue();
					if (StringUtils.isNotBlank(value)) {
						props.setProperty(param.getName(), value);
						log.debug(String.format(
								"Saving property '%s' with value '%s'.",
								param.getName(), value));
					}
				}
			}
		}

		propsHelepr.saveProperties(props, getPathToProperties(),
				PROPERTIES_FILE);

		return createRedicrectResponse();
	}

	private Response createRedicrectResponse() {
		RedirectDto redirect = new RedirectDto();
		redirect.setPath(integrator.getUriForRedirection().toString());
		return Response.status(HTTP_PARTIAL_CONTENT).entity(redirect).build();
	}

	private String getPathToProperties() {
		return context.getRealPath("/") + "WEB-INF"
				+ PropertiesHelper.SEPARATOR + "classes"
				+ PropertiesHelper.SEPARATOR + PROPERTIES_SUBPATH;
	}

}
