package org.mimacom.mega.translator.service.internal;

import org.mimacom.component.glume.core.stereotype.Service;
import org.mimacom.mega.translator.service.TranslationFileService;

import java.io.File;

@Service
public class DefaultTranslationFileService implements TranslationFileService {

	@Override
	public void addTranslationFile(File translationFile, String fileSetId) {
	}
}
