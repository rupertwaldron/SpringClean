package com.ruppyrup.springclean.controllers;


import com.ruppyrup.springclean.dto.TranslationRequest;
import com.ruppyrup.springclean.service.TranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/translate")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public String translate(@RequestBody TranslationRequest request) {
        long start = System.nanoTime();
        String inputToTranslate = request.text();
        log.info("Translation request " + request);
        String translatedText = translationService.translate(inputToTranslate, request.language());
        return translatedText + " translated in " + (System.nanoTime() - start) / 1000 + "uSec";
    }

}
