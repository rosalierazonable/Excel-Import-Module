package com.rosalieraz.importmodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "directory")
public class AppConfigReader {

	private String filepath;
	private String imagepath; 
	private String logpath;
	private String processedpath;
	private String root;

	
	/**
	 * Get File directory path set in the application property file
	 * @return files directory path
	 */
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	
	/**
	 * Get Images directory path set in the application property file
	 * @return images directory path
	 */
	
	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	
	/**
	 * Get Logs directory path set in the application property file
	 * @return logs directory path
	 */
	
	public String getLogpath() {
		return logpath;
	}

	public void setLogpath(String logpath) {
		this.logpath = logpath;
	}

	
	/**
	 * Get Processed directory path set in the application property file
	 * @return processed directory path
	 */
	
	public String getProcessedpath() {
		return processedpath;
	}

	public void setProcessedpath(String processedpath) {
		this.processedpath = processedpath;
	}


	/**
	 * Get Root directory path set in the application properties
	 * @return root directory
	 */
	
	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
}
