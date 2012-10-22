package org.mimacom.mega.translator.domain;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class TranslationPropertyFile {


	private String fileName;
	private Locale locale;
	private String prefix;
	private boolean main;
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
