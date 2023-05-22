package com.ruppyrup.springclean.whatfactories;


public class FrenchTranslator implements Translator {
  @Override
  public String translate(final String input) {
    return "French translation of \"" + input + "\" using class = " + this;
  }
}
