package org.mimacom.mega.translator.service.internal;

import org.mimacom.mega.translator.service.dto.PropertyFileData;

public class TranslationFileDetails {
	private boolean success;
	private boolean main;
	private String message;
	private String language;
	private String toTranslatePrefix;
	private String fileName;

	public TranslationFileDetails() {
		// Empty constructor
	}

	public TranslationFileDetails(PropertyFileData propertyFileData) {
		this.main = propertyFileData.isMain();
		this.language = propertyFileData.getLanguage();
		this.fileName = propertyFileData.getFileName();
		this.toTranslatePrefix = propertyFileData.getPrefix();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getToTranslatePrefix() {
		return toTranslatePrefix;
	}

	public void setToTranslatePrefix(String toTranslatePrefix) {
		this.toTranslatePrefix = toTranslatePrefix;
	}
}
