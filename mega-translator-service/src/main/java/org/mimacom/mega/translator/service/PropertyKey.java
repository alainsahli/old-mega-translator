package org.mimacom.mega.translator.service;

public enum PropertyKey {
	TRANSLATION_FILE_STORE("translation.files.store");

	private final String key;

	PropertyKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
