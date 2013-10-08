WebApp Installation Wizard
==========================


1. Introduction
----------------

WebApp Installation Wizard is a library that provides an installation procedure for web applications.
There is a configurable wizard that allows to set textual properties which are then available in the real 
application as a properties file. Specifically, it can be used to handle data base connection strings or
credentials for administrator account. The WebApp Installation Wizard launches REST services that are available
only for the first execution of the real application. For the subsequent executions, there are redirections to
a configured URL, intentionally main URL of the real application.

2. Usage
----------------

In order to incorporate the WebApp Installation Wizard into an application a number of steps shall be performed:

 * Add dependency to the WebApp Installation Wizard. In the case of applications managed by Apache Maven following
 lines should be added to the pom.xml file:

		<dependency>
			<groupId>com.networkedassets</groupId>
			<artifactId>webapp-installation-wizard</artifactId>
			<version>0.1<version>
		</dependency>
	
 * Implement the com.na.install.integration.Integrator interface and put it into com.na.install package 
 (or one of its subpackages). The implementation can look as follows:
``` 
 public class InstallationWizardIntegration implements Integrator {
	
	static final String ADMIN_PASS = "admin.pass";
	static final String ADMIN_LOGIN = "admin.login";
	static final Logger log = LoggerFactory.getLogger(InstallationWizardIntegration.class);
	
	@Override
	public URI getUriForRedirection() {
		try {
			return new URI("../../admin/index.html");
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public ConfigurationDto createConfigurationStructure() {
		ConfigurationDto cfg = new ConfigurationDto();			
		SectionDto admin = new SectionDto("Administrator Account");
		admin.getParams().add(new ParamDto(ADMIN_LOGIN, "admin", true));
		admin.getParams().add(new ParamDto(ADMIN_PASS, "top-secret-password", true));
		cfg.getSections().add(admin);
		return cfg;
	}
	
 }
```
 * Configure the WebApp Installation Wizard servlet in your web.xml file:
``` 
   <servlet>
		<servlet-name>NetworkedAssets Installation Wizard</servlet-name>
		<servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
		<init-param>
			<param-name>com.sun.jersey.config.property.packages</param-name>
			<param-value>com.na.install</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>NetworkedAssets Installation Wizard</servlet-name>
		<url-pattern>/wizard/rest/*</url-pattern>
	</servlet-mapping>
```


There are some steps that are not mandatory but are recommended since they handle errors that may occur
when an application container tries to start services that depends on properties that should be set by the
WebApp Installation Wizard, e.g. a servlet uses data source that is defined during installation and hence
is not configured during the first start. The following optional steps prevent the not configured services 
from start:

 * Remove the servlets, context-params, listeners and filters that should not be started during first run from your 
 web.xml file.
  
 * Implement org.springframework.web.WebApplicationInitializer to start the removed from web.xml services
 dynamically (it's Servlet 3.0 feature):
```  
  public class CustomWebAppInitializer implements WebApplicationInitializer {

	static final Logger log = LoggerFactory.getLogger(CustomWebAppInitializer.class);
	private PropertiesWrapper propsWrapper = new PropertiesWrapper();

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		if (propsWrapper.propertiesExists()) {
			XmlWebApplicationContext rootContext = new XmlWebApplicationContext();

			//context-param
			rootContext.setConfigLocations(new String[] { "classpath*:SpringContext.xml" });

			//listeners
			servletContext.addListener(new org.springframework.web.context.ContextLoaderListener(
					rootContext));
			servletContext
					.addListener(new org.springframework.web.context.request.RequestContextListener());

			//servlet, REST services
			ServletRegistration.Dynamic rest = servletContext.addServlet("REST services",
					com.sun.jersey.spi.spring.container.servlet.SpringServlet.class);
			rest.setInitParameter("com.sun.jersey.config.property.packages", "com.na");

			//servlet-mapping
			rest.addMapping("/rest/*");

			//filter
			servletContext.addFilter("springSecurityFilterChain",
					org.springframework.web.filter.DelegatingFilterProxy.class)
					.addMappingForUrlPatterns(null, false, "/*");
		}
	}
 }
```
 * The CustomWebAppInitializer class uses a helper for properties related operations, i.e. the PropertiesWrapper:
``` 
 public class PropertiesWrapper {
	
	/** Additional, optional properties location */
	public static final String ALTERNATIVE_PROPERTIES_LOCATION = ""
			+ System.getProperty("catalina.base") + "/conf/application.properties";
			
	final static String PATH_TO_PROPERTIES = InstallationWizard.PROPERTIES_SUBPATH
			+ InstallationWizard.PROPERTIES_FILE;
			
	boolean propertiesExists() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(PATH_TO_PROPERTIES);
		if (inputStream != null) {
			return true;
		}
		
		File f = new File(ALTERNATIVE_PROPERTIES_LOCATION);
		return f.exists();
	}
	
	....
 }
```	
