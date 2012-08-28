package org.mimacom.mega.translator.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranslationPropertyFile {


	private String fileName;
	private Map<String, String> properties = new LinkedHashMap<String, String>();

	public TranslationPropertyFile(String fileName, Map<String, String> properties) {
		this.fileName = fileName;
		this.properties = properties;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return fileName;
	}
}
