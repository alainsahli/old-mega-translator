package org.mimacom.mega.translator.service;

import java.io.IOException;
import java.io.InputStream;

public interface TranslationFileService {

	/**
	 * Adds a new translation
	 * @param fileName
	 * @param translationFileInputStream
	 */
	String addTranslationFile(String fileName, InputStream translationFileInputStream) throws IOException;
}
