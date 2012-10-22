package org.mimacom.mega.translator.service;

import org.mimacom.mega.translator.service.dto.PropertyFileData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TranslationFileService {

	/**
	 * Adds a new translation
	 * @param fileName
	 * @param translationFileInputStream
	 */
	PropertyFileData addTranslationFile(String fileName, InputStream translationFileInputStream) throws IOException;
}
