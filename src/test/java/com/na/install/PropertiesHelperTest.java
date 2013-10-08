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
package com.na.install;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class PropertiesHelperTest {
	
	@Test
	public void testSaveProperties() throws Exception {
		PropertiesHelper helper = new PropertiesHelper();
		final String fileName = "test.properties";
		Properties props = new Properties();
		final String key = "key";
		final String value = "value";
		props.setProperty(key, value);
		
		File file = new File(fileName);
		if (file.exists()) {
			assertTrue("The test cannot be executed when old properties are unremovable.",
					file.delete());
		}
		
		helper.saveProperties(props, ".", fileName);
		
		file = new File(fileName);
		assertTrue(file.exists());
		
		String propsContent = readFile(file).toString();
		assertTrue(propsContent.contains(key));
		assertTrue(propsContent.contains(value));
		
	}
	
	@Test
	public void testExist() {
		PropertiesHelper helper = new PropertiesHelper();
		assertTrue(helper.exist(".", "pom.xml"));
		assertFalse(helper.exist(".", "non-existing-file"));
	}
	
	private StringBuffer readFile(File file) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer propsContent = new StringBuffer();
		String line;
		
		while ((line = br.readLine()) != null) {
			propsContent.append(line);
		}
		br.close();
		return propsContent;
	}
	
}
