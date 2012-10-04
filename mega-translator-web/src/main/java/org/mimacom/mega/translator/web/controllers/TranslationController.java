package org.mimacom.mega.translator.web.controllers;

import org.apache.catalina.core.ApplicationPart;
import org.mimacom.mega.translator.service.TranslationFileService;
import org.mimacom.mega.translator.service.internal.TranslationFileDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;

@Controller
@RequestMapping("/translation")
public class TranslationController {

	private final TranslationFileService translationFileService;

	@Inject
	public TranslationController(TranslationFileService translationFileService) {
		this.translationFileService = translationFileService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public TranslationFileDetails handlePropertyFileUpload(@RequestParam("file") Part file) {
		ApplicationPart applicationPart = null;
		if (file instanceof ApplicationPart) {
			 applicationPart = (ApplicationPart) file;
		} else {
			return new TranslationFileDetails(false, "wrong application server");
		}
		if (!(file.getSize() == 0)) {
			try {
				translationFileService.addTranslationFile(applicationPart.getFilename(), applicationPart.getInputStream());
			} catch (IOException e) {
				return new TranslationFileDetails(false, "error");
			}
			return new TranslationFileDetails(true, "File is not empty!");
		} else {
			return new TranslationFileDetails(true, "File is empty!");
		}
	}
}
