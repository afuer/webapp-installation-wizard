/**
 * 
 */
package com.na.install;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Stateless service with properties related functionalities.
 * 
 * @author marian
 * 
 */
public class PropertiesHelper {
	
	/** System path separator. '/' or '\'. */
	public static final String SEPARATOR = System.getProperty("file.separator");
	
	/**
	 * Saves properties on given path
	 */
	public void saveProperties(Properties props, String path, String fileName) throws IOException,
			FileNotFoundException {
		
		path = standarizePath(path);
		
		InstallationWizard.log.debug("Real path: " + path);
		File directory = new File(path);
		if (!directory.exists()) {
			if (!directory.mkdir()) {
				throw new RuntimeException("The directory for the properties is not accessible: "
						+ path);
			}
		}
		
		props.store(new FileOutputStream(path + fileName), null);
	}
	
	private String standarizePath(String path) {
		path = path.trim();
		if (!path.endsWith(SEPARATOR)) {
			path += SEPARATOR;
		}
		return path;
	}
	
	/**
	 * 
	 * @param path
	 *            path to file
	 * @param fileName
	 *            the name of the file to be checked
	 */
	public boolean exist(String path, String fileName) {
		path = standarizePath(path);
		File file = new File(path + fileName);
		return file.exists();
	}
}
