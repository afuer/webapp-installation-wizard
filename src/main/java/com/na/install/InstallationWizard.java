/**
 * 
 */
package com.na.install;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.na.install.dto.ConfigurationDto;

/**
 * @author marian
 * 
 */
@Path("initialization")
public class InstallationWizard {
	
	public static final String SEPARATOR = System.getProperty("file.separator");
	public static final String PROPERTIES_SUBPATH = "META-INF" + SEPARATOR;
	public static final String PROPERTIES_FILE = "application.properties";
	public static final String PROPERTIES_RESOURCES = "classpath*:" + PROPERTIES_SUBPATH
			+ PROPERTIES_FILE;
	
	private static final Logger log = LoggerFactory.getLogger(InstallationWizard.class);
	
	@Context
	ServletContext context;
	
	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigurationDto cfgStructure() throws FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		prop.setProperty("bugzilla.db.driver", "com.mysql.jdbc.Driver");
		prop.setProperty("bugzilla.db.url", "jdbc:mysql://192.168.10.195:3306/bugs");
		prop.setProperty("bugzilla.db.username", "bugs");
		prop.setProperty("bugzilla.db.password", "netass#2011");
		
		String path = context.getRealPath("/") + "WEB-INF" + SEPARATOR + "classes" + SEPARATOR
				+ PROPERTIES_SUBPATH;
		log.info("Real path: " + path);
		
		File directory = new File(path);
		if (!directory.exists()) {
			if (!directory.mkdir()) {
				throw new RuntimeException("The directory for the properties is not accessible: "
						+ path);
			}
		}
		
		prop.store(new FileOutputStream(path + PROPERTIES_FILE), null);
		
		ConfigurationDto cfg = new ConfigurationDto();
		return cfg;
	}
}
