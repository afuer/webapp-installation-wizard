/**
 * 
 */
package com.na.install.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author marian
 *
 */
public class ParamDtoTest {
	
	@Test
	public void testGetValue() throws Exception {
		 ParamDto param = new ParamDto(null, "default", false, "value");
		 
		 assertEquals("value", param.getValue());
		 
		 param.setValue("");
		 assertEquals("default", param.getValue());
		 
		 param.setValue(null);
		 assertEquals("default", param.getValue());
	}
	
}
