package org.mimacom.mega.translator.service.internal;

public class TranslationFileDetails {
	private boolean success;
	private String message;
	private String language;
	private String toTranslatePrefix;
	private String hashkey;
	private boolean master;

	public TranslationFileDetails(boolean success, String message) {
		this.message = message;
		this.success = success;
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
}
