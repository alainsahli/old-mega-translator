package org.mimacom.mega.translator.service;

import java.io.File;

public interface TranslationFileService {

	/**
	 * Adds a new translation
	 * @param translationFile the translation file
	 * @param fileSetId the set ID to which the file belongs
	 */
	void addTranslationFile(File translationFile, String fileSetId);
}
