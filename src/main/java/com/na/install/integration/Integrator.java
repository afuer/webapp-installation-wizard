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
