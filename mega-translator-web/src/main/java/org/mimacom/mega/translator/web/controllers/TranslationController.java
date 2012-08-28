package org.mimacom.mega.translator.web.controllers;

import org.apache.catalina.core.ApplicationPart;
import org.mimacom.mega.translator.service.TranslationFileService;
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
	public String handlePropertyFileUpload(@RequestParam("file") Part file, @RequestParam("fileSetId") String fileSetId) {
		ApplicationPart applicationPart = null;
		if (file instanceof ApplicationPart) {
			 applicationPart = (ApplicationPart) file;
		} else {
			return "wrong application server";
		}
		if (!(file.getSize() == 0)) {
			try {
				translationFileService.addTranslationFile(applicationPart.getFilename(), applicationPart.getInputStream());
			} catch (IOException e) {
				return "error";
			}
			return "File is not empty!";
		} else {
			return "File is empty!";
		}
	}
}
