package com.ruppyrup.springclean.whatfactories;


public class SpanishTranslator implements Translator {
  @Override
  public String translate(final String input) {
    return "Spanish translation of \"" + input + "\" using class = " + this;
  }
}
