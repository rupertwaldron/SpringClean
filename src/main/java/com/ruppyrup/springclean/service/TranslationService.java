package com.ruppyrup.springclean.service;

import com.ruppyrup.springclean.whatfactories.Translator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final Translator translator;

    public TranslationService(Translator translator) {
        this.translator = translator;
    }

    @Cacheable(value = "translations", key = "#input")
    public String translate(String input) {
        return translator.translate(input);
    }
}
