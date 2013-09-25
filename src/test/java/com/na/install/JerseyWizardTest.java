/**
 * 
 */
package com.na.install;

import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.na.install.dto.ConfigurationDto;
import com.na.install.dto.ParamDto;
import com.na.install.dto.SectionDto;
import com.sun.jersey.test.framework.JerseyTest;

/**
 * @author marian
 * 
 */
public class JerseyWizardTest extends JerseyTest {
	
	/** */
	public JerseyWizardTest() {
		super("com.na.install");
		RestAssured.port = 9998;
	}
	
	@BeforeClass
	public static void beforeClass() throws IOException {
		File f = new File("WEB-INF");
		if(!f.exists()) {
			f.mkdir();
			f = new File("WEB-INF/classes");
			f.mkdir();
			return;
		}
		
		f = new File("WEB-INF/classes/META-INF");
		if(f.exists()) {
			FileUtils.deleteDirectory(f);
		}
	}
	
	/** Test getting configuration structure. */
	@Test
	public void testGetCfg() throws Exception {
		
		/* @formatter:off */
		RestAssured
			.expect()
				.statusCode(200)
				.body("sections.name", equalTo("Bugzilla data source"))
				.body("sections.params[0].name", equalTo("bugzilla.db.driver"))
			.given()
				.header("Accept", MediaType.APPLICATION_JSON)
			.when()
				.get("/initialization");
		/* @formatter:on */
		
		ConfigurationDto cfg = createConfig();
		
		/* @formatter:off */
		RestAssured
			.expect()
				.statusCode(200)
			.given()
				.contentType(MediaType.APPLICATION_JSON)
				.body(cfg)
			.when()
				.put("/initialization");
		
		RestAssured
			.expect()
				.statusCode(404)
			.given()
				.header("Accept", MediaType.APPLICATION_JSON)
			.when()
				.get("/initialization");
		/* @formatter:on */
	}

	private ConfigurationDto createConfig() {
		ConfigurationDto cfg = new ConfigurationDto();
		SectionDto section = new SectionDto("Section 1");
		section.getParams().add(new ParamDto("param.one", "value", false));
		cfg.getSections().add(section);
		return cfg;
	}
}
