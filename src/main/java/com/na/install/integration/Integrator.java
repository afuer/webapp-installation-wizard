/**
 * 
 */
package com.na.install.integration;

import java.net.URI;

import com.na.install.dto.ConfigurationDto;

/**
 * The interface must be implemented in order to inject to the wizard project
 * specific settings. The implementation must be in the 'com.na.install'
 * package.
 * 
 * @author marian
 * 
 */
public interface Integrator {
	
	/**
	 * The returned URI will be used to redirect the user when he is accessing
	 * wizard resources and the application is already installed/initialized. A
	 * relative URI (from the location of Installation Wizard rest services) can
	 * be used.
	 */
	public URI getUriForRedirection();
	
	/**
	 * To determine which parameters should be configured in the wizard.
	 */
	public ConfigurationDto createConfigurationStructure();
}
