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

    @Cacheable(value = "translations", key = "#input + '_' + #language") // just using language for caching
    public String translate(String input, String language) {
        return translator.translate(input);
    }
}
