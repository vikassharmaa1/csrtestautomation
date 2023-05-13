/**
 * 
 */
package com.csr.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * @author akaushi3
 *
 */
public class PropertiesOperations {

static Properties prop = new Properties();
static Properties properties = new Properties();	

	public static String getPropertyValueByKey(String key) {
		//1. load data from properties file
		String propFilePath = System.getProperty("user.dir")+"/src/test/resources/config.properties";
		FileInputStream fis;
		try {
			fis = new FileInputStream(propFilePath);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2. read data
		String value = prop.get(key).toString();
		
		if(StringUtils.isEmpty(value)) {
			try {		
				throw new Exception("Value is not specified for key: "+key + " in properties file.");
			}catch(Exception e) {}
		}
		
		return value;
	}
	
	public static String getXpathValueByKey(String key) {
		//1. load data from properties file
		String propFilePath = System.getProperty("user.dir")+"/src/test/resources/slotsXpath.properties";
		FileInputStream fis;
		try {
			fis = new FileInputStream(propFilePath);
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2. read data
		String value = properties.get(key).toString();
		
		if(StringUtils.isEmpty(value)) {
			try {		
				throw new Exception("Value is not specified for key: "+key + " in properties file.");
			}catch(Exception e) {}
		}
		
		return value;
	}
}
