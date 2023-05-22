package com.ruppyrup.springclean.controllers;


import com.ruppyrup.springclean.dto.TranslationRequest;
import com.ruppyrup.springclean.whatfactories.Translator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/translate")
public class TranslationController {

  private final Translator translator;

  public TranslationController(Translator translator) {
    this.translator = translator;
  }

  @PostMapping
  public String translate(@RequestBody TranslationRequest request) {
    String inputToTranslate = request.text();
    log.info("Translation request " + request);
    return translator.translate(inputToTranslate);
  }

}
