package com.codegen.jet.dolphins.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;

/**
 * load properties from resource or system
 */
public class PropertiesHelper {

	Properties p;

	public PropertiesHelper(Properties p) {
		this.p = p;
	}

	
	public Properties getProperties() {
		return p;
	}
	
	public String getProperty(String key, String defaultValue) {
		String value = null;
		if(value == null || "".equals(value.trim())) {
			value = getProperties().getProperty(key);
		}
		return value == null || "".equals(value.trim()) ? defaultValue : value;
	}
	
	public String getProperty(String key) {
		return getProperty(key,null);
	}
	
	public String getRequiredProperty(String key) {
		String value = getProperty(key);
		if(value == null || "".equals(value.trim())) {
			throw new IllegalStateException("required property is blank by key="+key);
		}
		return value;
	}

	public String getNullIfBlank(String key) {
		String value = getProperty(key);
		if(value == null || "".equals(value.trim())) {
			return null;
		}
		return value;
	}
	
	public PropertiesHelper setProperty(String key,String value) {
		p.setProperty(key, value);
		return this;
	}

	public void clear() {
		p.clear();
	}

	public Set<Entry<Object, Object>> entrySet() {
		return p.entrySet();
	}

	public Enumeration<?> propertyNames() {
		return p.propertyNames();
	}


	/**
	 * load properties from resource
	 * @param properties
	 * @param resourceNames
	 * @return
	 * @throws IOException
	 */
	public static String[] loadAllPropertiesFromClassLoader(Properties properties,String... resourceNames) throws IOException {
		List successLoadProperties = new ArrayList();
		for(String resourceName : resourceNames) {
			Enumeration urls = ReadProperties.class.getClassLoader().getResources(resourceName);
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				successLoadProperties.add(url.getFile());
				InputStream input = null;
				try {
					URLConnection con = url.openConnection();
					con.setUseCaches(false);
					input = con.getInputStream();
					if(resourceName.endsWith(".xml")){
						properties.loadFromXML(input);
					}else {
						properties.load(input);
					}
				}
				finally {
					if (input != null) {
						input.close();
					}
				}
			}
		}
		return (String[])successLoadProperties.toArray(new String[0]);
	}

}
