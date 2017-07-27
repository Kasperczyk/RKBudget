package de.kasperczyk.rkbudget.language;

import java.util.Arrays;

public enum Language {

    ENGLISH("en"),
    GERMAN("de");
//    SPANISH("es"),
//    DUTCH("nl");

    private static final String MESSAGE_PREFIX = "language_supportedLanguage_";

    private String countryCode;

    Language(String countryCode) {
        this.countryCode = countryCode;
    }

    public static Language valueBy(String countryCode) {
        return Arrays.stream(values())
                .filter(language -> language.getCountryCode().equals(countryCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Language with country code " +
                        countryCode + " not found"));
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getMessageKey() {
        return MESSAGE_PREFIX + countryCode;
    }
}
