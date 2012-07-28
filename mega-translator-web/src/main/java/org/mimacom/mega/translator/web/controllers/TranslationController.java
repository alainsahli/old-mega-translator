package org.mimacom.mega.translator.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Part;

@Controller
@RequestMapping("/translation")
public class TranslationController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handlePropertyFileUpload(@RequestParam("file") Part file) {
        if (!(file.getSize() == 0)) {
            return "File is not empty!";
        } else {
            return "File is empty!";
        }
    }
}
