package org.mimacom.mega.translator.web.controllers;

import org.mimacom.mega.translator.service.TranslationFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.Part;

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
		if (!(file.getSize() == 0)) {
            return "File is not empty!";
        } else {
            return "File is empty!";
        }
    }
}
